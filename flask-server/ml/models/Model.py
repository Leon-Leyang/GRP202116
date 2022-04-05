'''
Author: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Last Modified: Saturday, 26th March 2022 18:56
Modified By: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Brief: Base script class containing the basic methods for predicting and training
'''

import torch
import sys

class Model():

    def __init__(self, modelPath, modelRoot, labelsPath, modelVersion=None, fromName=None, toName=None, toolType=None):
        # Add the model root path to environment variable path
        sys.path.append(modelRoot)

        # Load the model from the file
        self.model = torch.load(modelPath)

        # Flash the model to GPU if available
        self.device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
        self.model = self.model.to(self.device)

        self.modelVersion = modelVersion
        self.fromName = fromName
        self.toName = toName
        self.type = toolType

        # Initialize list to store label names
        with open(labelsPath) as f:
            self.labels = [line.strip() for line in f.readlines()]

        self.predictionItem = {}

    # Fill in the basic information of a prediction item
    # Specific result items shall be appended to predictionItem['result'] in corresponding subclasses
    def predict(self, dataPath):
        # Set the model to evaluation mode
        self.model.eval()

        self.predictionItem['model_version'] = self.modelVersion

        result = []
        self.predictionItem['result'] = result


    def train(self, datas, annotations, savePath):
        # Set the model to training mode
        self.model.train()