import torch
from transformers import BertTokenizerFast
from torch import cuda
import pandas as pd
import re

ids_to_labels = {0: 'O', 1: 'B-geo', 2: 'B-gpe', 3: 'B-per', 4: 'I-geo', 5: 'B-org', 6: 'I-org', 7: 'B-tim', 8: 'I-per', 9: 'I-gpe', 10: 'I-tim'}

tokenizer = BertTokenizerFast.from_pretrained('bert-base-uncased')

MAX_LEN = 128

device = 'cuda' if cuda.is_available() else 'cpu'
model = torch.load('./bert.pth').to(device)

sentence = "@HuggingFace is a company based in New York, but is also has employees working in Paris"

inputs = tokenizer(sentence.split(),
                    is_split_into_words=True,
                    return_offsets_mapping=True,
                    padding='max_length',
                    truncation=True,
                    max_length=MAX_LEN,
                    return_tensors="pt")

# move to gpu
ids = inputs["input_ids"].to(device)
mask = inputs["attention_mask"].to(device)
# forward pass
outputs = model(ids, attention_mask=mask)
logits = outputs[0]

active_logits = logits.view(-1, model.num_labels) # shape (batch_size * seq_len, num_labels)
flattened_predictions = torch.argmax(active_logits, axis=1) # shape (batch_size*seq_len,) - predictions at the token level

tokens = tokenizer.convert_ids_to_tokens(ids.squeeze().tolist())
token_predictions = [ids_to_labels[i] for i in flattened_predictions.cpu().numpy()]
wp_preds = list(zip(tokens, token_predictions)) # list of tuples. Each tuple = (wordpiece, prediction)

prediction = []
for token_pred, mapping in zip(wp_preds, inputs["offset_mapping"].squeeze().tolist()):
  #only predictions on first word pieces are important
  if mapping[0] == 0 and mapping[1] != 0:
    prediction.append(token_pred[1])
  else:
    continue

print(sentence.split())
print(prediction)
