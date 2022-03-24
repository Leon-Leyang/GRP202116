import sys

import numpy as np
from keras.preprocessing.text import Tokenizer
from keras.preprocessing import sequence

import uuid
import torch
import pandas as pd

from Models.Model import Model

# Model for image classification task
class TextClsModel(Model):
    # Class for preprocessing
    class Preprocess():
        def __init__(self, textsPath, tokenNum, sequenceLen):
            # Read the whole dataset(without label)
            textsFile = pd.read_csv(textsPath, header=None)
            texts = textsFile.iloc[:, 0]
            # Build the tokenizer
            self.tokenizer = Tokenizer(num_words=tokenNum)
            self.tokenizer.fit_on_texts(texts)

            # Initialize the maximum sequence length
            self.sequenceLen = sequenceLen

        # Function to convert given text into index sequence of constant length
        def __call__(self, data):
            sequences = self.tokenizer.texts_to_sequences([data])
            seq = sequence.pad_sequences(sequences, maxlen=self.sequenceLen)[0]
            return seq


    def __init__(self, modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath, vocabPath, tokenNum, sequenceLen):
        super().__init__(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath)

        # Initialize the preprocess object
        self.preprocess = self.Preprocess(vocabPath, tokenNum, sequenceLen)



    def predict(self, textPath):
        super().predict()

        with open(textPath, 'r') as f:
            text = f.read()

        sequence = torch.from_numpy(np.expand_dims(self.preprocess(text), axis=0)).to(self.device)
        modelOutput = self.model(sequence)
        index = round(modelOutput.item())

        value = {}
        value['choices'] = [self.labels[index]]
        resultItem = {}
        resultItem['id'] = str(uuid.uuid4())
        resultItem['from_name'] = self.fromName
        resultItem['to_name'] = self.toName
        resultItem['type'] = self.type
        resultItem['value'] = value

        self.predictionItem['result'].append(resultItem)

        return self.predictionItem


if __name__ == '__main__':
    modelPath = '../../ml/models/TextClassification/lstm.pth'
    modelVersion = 'one'
    modelRoot = '../../ml/models/TextClassification'
    fromName = 'sentiment'
    toName = 'text'
    toolType = 'choices'
    labelsPath = '../../ml/resources/textCls.txt'

    vocabPath = '../../ml/resources/vocab.csv'
    tokenNum = 1000
    sequenceLen = 20

    textClsModel = TextClsModel(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath, vocabPath, tokenNum, sequenceLen)

    textPath = '../../ml/resources/tweets.txt'
    predictionItem = textClsModel.predict(textPath)
    print(predictionItem)