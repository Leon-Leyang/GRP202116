from PIL import Image

import uuid
import torchvision.transforms as tf

from Models.Model import Model

# Model for image classification task
class ObjDecBBoxModel(Model):
    def __init__(self, modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath, threashold):
        super().__init__(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath)

        # Preprocessing operations
        self.transforms = tf.Compose([
            tf.ToTensor()
        ])

        # Threshold to filter result
        self.threashold = threashold

    def predict(self, imgPath):
        super().predict()

        img = Image.open(imgPath)

        # Get the width and height of the image
        imgWidth, imgHeight = img.size

        imgVec = self.transforms(img).unsqueeze(0).to(self.device)
        modelOutput = self.model(imgVec)[0]

        for i in range(len(modelOutput['boxes'])):
            index = int(modelOutput["labels"][i])
            confidence = modelOutput['scores'][i]
            if(confidence > self.threashold):
                # Get the bounding box
                box = modelOutput["boxes"][i].detach().cpu().numpy()
                (startX, startY, endX, endY) = box.astype("int")

                # Get the relative x, y, width and height
                xPix = (startX / imgWidth) * 100
                yPix = (startY / imgHeight) * 100
                widthPix = (endX - startX) / imgWidth * 100
                heightPix = (endY - startY) / imgHeight * 100

                value = {}
                value['rectanglelabels'] = [self.labels[index]]
                value['width'] = widthPix
                value['height'] = heightPix
                value['x'] = xPix
                value['y'] = yPix

                resultItem = {}
                resultItem['id'] = str(uuid.uuid4())
                resultItem['from_name'] = self.fromName
                resultItem['to_name'] = self.toName
                resultItem['type'] = self.type
                resultItem['value'] = value

                self.predictionItem['result'].append(resultItem)

        return self.predictionItem


if __name__ == '__main__':
    modelPath = '../../ml/models/frcnn-resnet/frcnn-resnet.pth'
    modelVersion = 'one'
    modelRoot = './'
    fromName = 'label'
    toName = 'image'
    toolType = 'rectanglelabels'
    labelsPath = '../coco.txt'

    threashold = 0.75

    imgPath = '../puppy.webp'

    objDecBBoxModel = ObjDecBBoxModel(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath, threashold)

    predictionItem = objDecBBoxModel.predict(imgPath)
    print(predictionItem)