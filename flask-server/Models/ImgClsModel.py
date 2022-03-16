from PIL import Image

import uuid
import torch
import torchvision.transforms as tf

from Models.Model import Model

# Model for image classification task
class ImgClsModel(Model):
    def __init__(self, modelPath, modelVersion, fromName, toName, toolType, labels):
        super().__init__(modelPath, modelVersion, fromName, toName, toolType, labels)

        # Preprocessing operations
        self.transforms = tf.Compose([
            tf.Resize(256),
            # TODO: Get the image size for center crop from user
            tf.CenterCrop(224),
            tf.ToTensor(),
            tf.Normalize(mean=[0.485, 0.456, 0.406],
                         std=[0.229, 0.224, 0.225])])

    def predict(self, imgPath):
        super().predict()

        img = Image.open(imgPath)

        imgVec = self.transforms(img).unsqueeze(0).to(self.device)
        modelOutput = self.model(imgVec)

        # Get the index of the most likely class
        index = torch.max(modelOutput, 1)[1][0]

        # Get the percentage of the prediction
        percentage = (torch.nn.functional.softmax(modelOutput, dim=1)[0] * 100)[index].item()

        value = {}
        value['choices'] = [labels[index]]
        resultItem = {}
        resultItem['id'] = str(uuid.uuid4())
        resultItem['from_name'] = self.fromName
        resultItem['to_name'] = self.toName
        resultItem['type'] = self.type
        resultItem['value'] = value

        self.predictionItem['result'].append(resultItem)

        return self.predictionItem


if __name__ == '__main__':
    modelPath = '../../ml/models/resnet101/resnet101.pth'
    modelVersion = 'one'
    fromName = 'choice'
    toName = 'image'
    toolType = 'choices'

    with open('../../ml/models/resnet101/ImageNetClasses.txt') as f:
        labels = [line.strip() for line in f.readlines()]

    imgPath = '../puppy.webp'

    imgClsModel = ImgClsModel(modelPath, modelVersion, fromName, toName, toolType, labels)

    predictionItem = imgClsModel.predict(imgPath)
    print(predictionItem)