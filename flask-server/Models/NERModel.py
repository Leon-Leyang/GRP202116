from transformers import BertTokenizerFast
from spacy.tokens import Doc
from spacy.vocab import Vocab
from spacy.training import biluo_tags_to_offsets, iob_to_biluo

import uuid
import torch

from Models.Model import Model

# Model for named entity recognition task
class NERModel(Model):
    # Class for preprocessing
    class Preprocess():
        def __init__(self, sequenceLen):
            # Initialize the maximum sequence length
            self.sequenceLen = sequenceLen

            # Initialize the tokenizer
            self.tokenizer = BertTokenizerFast.from_pretrained('bert-base-uncased')

        # Function to convert given text into index sequence of constant length
        def __call__(self, data):
            seq = self.tokenizer(data.split(),
                               is_split_into_words=True,
                               return_offsets_mapping=True,
                               padding='max_length',
                               truncation=True,
                               max_length=self.sequenceLen,
                               return_tensors="pt")
            return seq


    def __init__(self, modelPath, modelVersion, modelRoot, fromName, toName, toolType, labels, sequenceLen):
        super().__init__(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labels)

        # Initialize the preprocess object
        self.preprocess = self.Preprocess(sequenceLen)


    def predict(self, textPath):
        super().predict()

        with open(textPath, 'r') as f:
            text = f.read()

        # Preprocess the text and get needed input for the model
        sequence = self.preprocess(text)
        ids = sequence["input_ids"].to(self.device)
        mask = sequence["attention_mask"].to(self.device)

        modelOutput = self.model(ids, attention_mask=mask)

        # Get prediction in BIO form
        logits = modelOutput[0]
        active_logits = logits.view(-1, self.model.num_labels)  # shape (batch_size * seq_len, num_labels)
        flattened_predictions = torch.argmax(active_logits,axis=1)  # shape (batch_size*seq_len,) - predictions at the token level
        tokens = self.preprocess.tokenizer.convert_ids_to_tokens(ids.squeeze().tolist())
        token_predictions = [self.labels[i] for i in flattened_predictions.cpu().numpy()]
        wp_preds = list(zip(tokens, token_predictions))  # list of tuples. Each tuple = (wordpiece, prediction)
        predBIO = []
        for token_pred, mapping in zip(wp_preds, sequence["offset_mapping"].squeeze().tolist()):
            # only predictions on first word pieces are important
            if mapping[0] == 0 and mapping[1] != 0:
                predBIO.append(token_pred[1])
            else:
                continue

        # Convert BIO form prediction to offset form
        doc = Doc(Vocab(), words=text.split())
        predBILUO = iob_to_biluo(predBIO)
        offsets = biluo_tags_to_offsets(doc, predBILUO)

        for item in offsets:
            value = {}
            start, end, label = item
            value['text'] = text[start:end]
            value['start'] = start
            value['end'] = end
            value['labels'] = [label]

            resultItem = {}
            resultItem['id'] = str(uuid.uuid4())
            resultItem['from_name'] = self.fromName
            resultItem['to_name'] = self.toName
            resultItem['type'] = self.type
            resultItem['value'] = value

            self.predictionItem['result'].append(resultItem)

        return self.predictionItem


if __name__ == '__main__':
    modelPath = '../../ml/models/bert/bert.pth'
    modelVersion = 'one'
    modelRoot = 'C:/Users/Leon/Desktop/Named-Entity-Recognition'
    fromName = 'label'
    toName = 'text'
    toolType = 'labels'
    labels = ['O', 'B-geo', 'B-gpe', 'B-per', 'I-geo', 'B-org', 'I-org', 'B-tim', 'I-per', 'I-gpe', 'I-tim']
    sequenceLen = 128

    nERModel = NERModel(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labels, sequenceLen)

    textPath = '../ner.txt'
    predictionItem = nERModel.predict(textPath)
    print(predictionItem)