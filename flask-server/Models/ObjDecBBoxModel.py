from PIL import Image

import uuid
import torch
import cv2
import torchvision.transforms as tf
import numpy as np

from Models.Model import Model

# Model for image classification task
class ObjDecBBoxModel(Model):
    def __init__(self, modelPath, modelVersion, fromName, toName, toolType, labels, threashold):
        super().__init__(modelPath, modelVersion, fromName, toName, toolType)

        # List to store class index with its name
        self.labels = labels

        # Preprocessing operations
        self.transforms = tf.Compose([
            tf.ToTensor()
        ])

        # Threshold to filter result
        self.threashold = threashold

    def predict(self, imgPath):
        super().predict()

        img = Image.open(imgPath)

        # For development checking
        orig = (np.array(img)).copy()
        orig = cv2.cvtColor(orig, cv2.COLOR_RGB2BGR)

        COLORS = np.random.uniform(0, 255, size=(len(self.labels), 3))

        imgVec = self.transforms(img).unsqueeze(0).to(self.device)
        modelOutput = self.model(imgVec)[0]

        for i in range(len(modelOutput['boxes'])):
            index = int(modelOutput["labels"][i])
            confidence = modelOutput['scores'][i]
            if(confidence > self.threashold):
                box = modelOutput["boxes"][i].detach().cpu().numpy()
                (startX, startY, endX, endY) = box.astype("int")

                label = self.labels[index]

                cv2.rectangle(orig, (startX, startY), (endX, endY),
                              COLORS[index], 2)
                y = startY - 15 if startY - 15 > 15 else startY + 15
                cv2.putText(orig, label, (startX, y),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.5, COLORS[index], 2)

        cv2.imshow("Output", orig)
        cv2.waitKey(0)


        # Get the index of the most likely class
        # index = torch.max(modelOutput, 1)[1][0]

        # Get the percentage of the prediction
        # percentage = (torch.nn.functional.softmax(modelOutput, dim=1)[0] * 100)[index].item()
        #
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
        #
        # return self.predictionItem


if __name__ == '__main__':
    modelPath = '../../ml/models/frcnn-resnet/frcnn-resnet.pth'
    modelVersion = 'one'
    fromName = 'label'
    toName = 'image'
    toolType = 'rectanglelabels'

    labels = ['__background__', 'person', 'bicycle', 'car', 'motorcycle', 'airplane', 'bus', 'train', 'truck', 'boat', 'traffic light', 'fire hydrant', 'N/A', 'stop sign', 'parking meter', 'bench', 'bird', 'cat', 'dog', 'horse', 'sheep', 'cow', 'elephant', 'bear', 'zebra', 'giraffe', 'N/A', 'backpack', 'umbrella', 'N/A', 'N/A', 'handbag', 'tie', 'suitcase', 'frisbee', 'skis', 'snowboard', 'sports ball', 'kite', 'baseball bat', 'baseball glove', 'skateboard', 'surfboard', 'tennis racket', 'bottle', 'N/A', 'wine glass', 'cup', 'fork', 'knife', 'spoon', 'bowl', 'banana', 'apple', 'sandwich', 'orange', 'broccoli', 'carrot', 'hot dog', 'pizza', 'donut', 'cake', 'chair', 'couch', 'potted plant', 'bed', 'N/A', 'dining table', 'N/A', 'N/A', 'toilet', 'N/A', 'tv', 'laptop', 'mouse', 'remote', 'keyboard', 'cell phone', 'microwave', 'oven', 'toaster', 'sink', 'refrigerator', 'N/A', 'book', 'clock', 'vase', 'scissors', 'teddy bear', 'hair drier', 'toothbrush']

    threashold = 0.75

    imgPath = '../puppy.webp'

    objDecBBoxModel = ObjDecBBoxModel(modelPath, modelVersion, fromName, toName, toolType, labels, threashold)

    predictionItem = objDecBBoxModel.predict(imgPath)
    # print(predictionItem)