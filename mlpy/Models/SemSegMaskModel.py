from PIL import Image
from label_studio_converter import brush

import uuid
import torch
import torchvision.transforms as tf
import numpy as np

from Models.Model import Model

class SemSegMaskModel(Model):
    def __init__(self, modelPath, modelVersion, fromName, toName, type, index2nameMap):
        super().__init__(modelPath, modelVersion, fromName, toName, type)

        # Map to store class index with its name
        self.index2nameMap = index2nameMap

        # Preprocessing operations
        self.transforms = tf.Compose([
            tf.ToTensor(),
            tf.Normalize(mean=[0.485, 0.456, 0.406],
                         std=[0.229, 0.224, 0.225])])

    def predict(self, imgPath):
        super().predict()

        img = Image.open(imgPath)

        imgVec = self.transforms(img).unsqueeze(0).to(self.device)
        modelOutput = self.model(imgVec)['out']

        # Matrix of the most likely class index
        classMatrix = torch.argmax(modelOutput.squeeze(), dim=0).detach().cpu().numpy()

        # Generate resultItem for each class in the image and append to predictionItem['result']
        for classIndex in np.unique(classMatrix):
            # Generate a mask according to the given class index
            mask = np.where(classMatrix == classIndex, 255, 0)

            rle = brush.mask2rle(mask.astype(np.uint8))

            value = {}
            value['format'] = 'rle'
            value['brushlabels'] = [self.index2nameMap[classIndex]]
            value['rle'] = rle

            resultItem = {}
            resultItem['id'] = str(uuid.uuid4())
            resultItem['from_name'] = self.fromName
            resultItem['to_name'] = self.toName
            resultItem['type'] = self.type
            resultItem['value'] = value
            self.predictionItem['result'].append(resultItem)

        return self.predictionItem



if __name__ == '__main__':
    modelPath = '../../ml/models/fcn/fcn.pth'
    modelVersion = 'one'
    fromName = 'tag'
    toName = 'image'
    type = 'brushlabels'
    index2nameMap = {
        0: 'Background',
        1: 'Aeroplane',
        2: 'Bicycle',
        3: 'Bird',
        4: 'Boat',
        5: 'Bottle',
        6: 'Bus',
        7: 'Car',
        8: 'Cat',
        9: 'Chair',
        10: 'Cow',
        11: 'Dining table',
        12: 'Dog',
        13: 'Horse',
        14: 'Motorbike',
        15: 'Person',
        16: 'Potted plant',
        17: 'Sheep',
        18: 'Sofa',
        19: 'Train',
        20: 'Tv/monitor'
    }
    imgPath = '../puppy.webp'


    semSegMaskModel = SemSegMaskModel(modelPath, modelVersion, fromName, toName, type, index2nameMap)
    predictionItem = semSegMaskModel.predict(imgPath)
    print(predictionItem)