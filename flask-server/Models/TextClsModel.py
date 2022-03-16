import numpy as np
from keras.preprocessing.text import Tokenizer
from keras.preprocessing import sequence

import uuid
import torch
import pandas as pd
import sys

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

            self.sequenceLen = sequenceLen

        # Function to convert given text into index sequence of constant length
        def __call__(self, data):
            sequences = self.tokenizer.texts_to_sequences([data])
            seq = sequence.pad_sequences(sequences, maxlen=self.sequenceLen)[0]
            return seq


    def __init__(self, modelPath, modelVersion, fromName, toName, toolType, labels, textsPath, tokenNum, sequenceLen):
        super().__init__(modelPath, modelVersion, fromName, toName, toolType, labels)

        # Initialize the preprocess object
        self.preprocess = self.Preprocess(textsPath, tokenNum, sequenceLen)



    def predict(self, textPath):
        super().predict()

        with open(textPath, 'r') as f:
            text = f.read()

        sequence = torch.from_numpy(np.expand_dims(self.preprocess(text), axis=0)).to(self.device)
        print(sequence)
        modelOutput = self.model(sequence)
        print(modelOutput)

        # value = {}
        # value['choices'] = [labels[index]]
        # resultItem = {}
        # resultItem['id'] = str(uuid.uuid4())
        # resultItem['from_name'] = self.fromName
        # resultItem['to_name'] = self.toName
        # resultItem['type'] = self.type
        # resultItem['value'] = value
        #
        # self.predictionItem['result'].append(resultItem)

        return self.predictionItem


if __name__ == '__main__':
    modelPath = '../../ml/models/lstm/lstm.pth'
    modelVersion = 'one'
    fromName = 'choice'
    toName = 'image'
    toolType = 'choices'
    labels = []
    textsPath = './vocab.csv'
    tokenNum = 1000
    sequenceLen = 20

    sys.path.append('C:/Users/Leon/Desktop/Text-Classification-LSTMs-PyTorch')
    textClsModel = TextClsModel(modelPath, modelVersion, fromName, toName, toolType, labels, textsPath, tokenNum, sequenceLen)

    textPath = './test.txt'
    textClsModel.predict(textPath)
    # with open('../../ml/models/resnet101/ImageNetClasses.txt') as f:
    #     labels = [line.strip() for line in f.readlines()]
    #
    # imgPath = '../puppy.webp'
    #
    # imgClsModel = ImgClsModel(modelPath, modelVersion, fromName, toName, toolType, labels)
    #
    # predictionItem = imgClsModel.predict(imgPath)
    # print(predictionItem)







# import torch
# import torch.nn as nn
# import torch.nn.functional as F
#
#
# class TweetClassifier(nn.ModuleList):
#
#     def __init__(self):
#         super(TweetClassifier, self).__init__()
#
#         self.batch_size = 64
#         self.hidden_dim = 128
#         self.LSTM_layers = 2
#         self.input_size = 1000  # embedding dimension
#
#         self.dropout = nn.Dropout(0.5)
#         self.embedding = nn.Embedding(self.input_size, self.hidden_dim, padding_idx=0)
#         self.lstm = nn.LSTM(input_size=self.hidden_dim, hidden_size=self.hidden_dim, num_layers=self.LSTM_layers,
#                             batch_first=True)
#         self.fc1 = nn.Linear(in_features=self.hidden_dim, out_features=257)
#         self.fc2 = nn.Linear(257, 1)
#
#     def forward(self, x):
#         h = torch.zeros((self.LSTM_layers, x.size(0), self.hidden_dim))
#         c = torch.zeros((self.LSTM_layers, x.size(0), self.hidden_dim))
#
#         torch.nn.init.xavier_normal_(h)
#         torch.nn.init.xavier_normal_(c)
#
#         out = self.embedding(x)
#         out, (hidden, cell) = self.lstm(out, (h, c))
#         out = self.dropout(out)
#         out = torch.relu_(self.fc1(out[:, -1, :]))
#         out = self.dropout(out)
#         out = torch.sigmoid(self.fc2(out))
#
#         return out
#
# model = TweetClassifier()
# model.load_state_dict(torch.load('./stat.pth'))
# torch.save(model, './lstm.pth')