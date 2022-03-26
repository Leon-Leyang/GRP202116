from PIL import Image

import uuid
import torchvision.transforms as tf

from Models.Model import Model

# Model for keypoint labeling task
class KpLabModel(Model):

    def __init__(self, modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath, threashold):
        super().__init__(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath)

        # Preprocessing operations
        self.transforms = tf.Compose([
            tf.ToTensor()])

        # Initialize preprocess object
        self.preprocess = self.Preprocess(self.transforms)

        # Threshold to filter result
        self.threashold = threashold

    def predict(self, imgPath):
        super().predict()

        img = Image.open(imgPath)

        # Get the width and height of the image
        imgWidth, imgHeight = img.size

        imgVec = self.preprocess(img).unsqueeze(0).to(self.device)
        modelOutput = self.model(imgVec)

        for item in modelOutput:
            if(item['scores'] > self.threashold):
                keypoints = item['keypoints'].cpu().detach().numpy().squeeze()
                for idx, keypoint in enumerate(keypoints):
                    label = self.labels[idx]
                    x = keypoint[0]
                    y = keypoint[1]
                    xPix = (x / imgWidth) * 100
                    yPix = (y / imgHeight) * 100
                    value = {}
                    value['keypointlabels'] = [label]
                    value['x'] = xPix
                    value['y'] = yPix
                    # TODO: Set the width as defined in the frontend
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
    modelPath = '../../ml/models/KeypointLabeling/keypointrcnn.pth'
    modelVersion = 'one'
    modelRoot = '../../ml/models/KeypointLabeling'
    fromName = 'kp-1'
    toName = 'img-1'
    toolType = 'keypointlabels'
    labelsPath = '../../ml/resources/cocoKp.txt'
    threashold = 0.9

    imgPath = '../../ml/resources/kid.jpg'

    kpLabModel = KpLabModel(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath, threashold)

    predictionItem = kpLabModel.predict(imgPath)
    print(predictionItem)