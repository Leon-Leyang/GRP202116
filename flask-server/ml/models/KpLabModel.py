'''
Author: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Last Modified: Saturday, 26th March 2022 18:56
Modified By: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Brief: Script class for keypoint labeling which overrides the methods for predicting
'''

from PIL import Image

import uuid
import torchvision.transforms as tf
import torch
import numpy as np

from ml import Preprocess
from ml.models.Model import Model

# Model for keypoint labeling task
class KpLabModel(Model):

    def __init__(self, modelPath, modelRoot, labelsPath, modelVersion=None, fromName=None, toName=None, toolType=None,
                 threshold=0.9):
        super().__init__(modelPath, modelRoot, labelsPath, modelVersion, fromName, toName, toolType)

        # Preprocessing operations
        self.transforms = tf.Compose([
            tf.ToTensor()])

        # Initialize preprocess object
        self.preprocess = Preprocess(self.transforms)

        # Threshold to filter result
        self.threshold = threshold

    def predict(self, imgPath):
        super().predict(imgPath)

        img = Image.open(imgPath)

        # Get the width and height of the image
        imgWidth, imgHeight = img.size

        imgVec = self.preprocess(img).unsqueeze(0).to(self.device)
        modelOutput = self.model(imgVec)

        for item in modelOutput:
            if(torch.max(item['scores']) > self.threshold):
                keypoints = item['keypoints'].cpu().detach().numpy().squeeze()
                for idx, keypoint in enumerate(keypoints):
                    label = self.labels[idx]
                    if(isinstance(keypoint[0], np.ndarray)):
                        x = keypoint[0][0]
                    else:
                        x = keypoint[0]
                    if (isinstance(keypoint[1], np.ndarray)):
                        y = keypoint[1][0]
                    else:
                        y = keypoint[0]
                    xPix = (x / imgWidth) * 100
                    yPix = (y / imgHeight) * 100
                    value = {}
                    value['keypointlabels'] = [label]
                    value['x'] = xPix
                    value['y'] = yPix
                    value['width'] = 0.3

                    resultItem = {}
                    resultItem['id'] = str(uuid.uuid4())
                    resultItem['from_name'] = self.fromName
                    resultItem['to_name'] = self.toName
                    resultItem['type'] = self.type
                    resultItem['value'] = value

                    self.predictionItem['result'].append(resultItem)

        return self.predictionItem


if __name__ == '__main__':
    modelPath = '../../../ml/models/KeypointLabeling/keypointrcnn.pth'
    modelVersion = 'one'
    modelRoot = '../../../ml/models/KeypointLabeling'
    fromName = 'kp-1'
    toName = 'img-1'
    toolType = 'keypointlabels'
    labelsPath = '../../../ml/resources/cocoKp.txt'
    threshold = 0.9

    imgPath = '../../../ml/resources/kid.jpg'

    kpLabModel = KpLabModel(modelPath, modelRoot, labelsPath, modelVersion, fromName, toName, toolType, threshold)

    predictionItem = kpLabModel.predict(imgPath)
    print(predictionItem)