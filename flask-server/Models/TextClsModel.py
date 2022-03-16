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


    def __init__(self, modelPath, modelVersion, modelRoot, fromName, toName, toolType, labels, textsPath, tokenNum, sequenceLen):
        super().__init__(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labels)

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
    modelRoot = 'C:/Users/Leon/Desktop/Text-Classification-LSTMs-PyTorch'
    fromName = 'choice'
    toName = 'image'
    toolType = 'choices'
    labels = []
    textsPath = './vocab.csv'
    tokenNum = 1000
    sequenceLen = 20

    textClsModel = TextClsModel(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labels, textsPath, tokenNum, sequenceLen)

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