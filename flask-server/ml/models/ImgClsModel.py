from PIL import Image
from torch.utils.data import DataLoader, random_split
from torch.optim import *
from torch.autograd import Variable

import uuid
import torch
import torchvision.transforms as tf
import torch.nn as nn

from ml import Preprocess
from ml.models.Model import Model
from ml.ImgClsDataset import ImgClsDataset

# Model for image classification task
class ImgClsModel(Model):

    def __init__(self, modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath, mean, std, imgSize):
        super().__init__(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath)

        # Preprocessing operations
        self.transforms = tf.Compose([
            tf.Resize(256),
            tf.CenterCrop(imgSize),
            tf.ToTensor(),
            tf.Normalize(mean=mean,
                         std=std)])

        # Initialize preprocess object
        self.preprocess = Preprocess(self.transforms)


    def predict(self, imgPath):
        super().predict()

        img = Image.open(imgPath)

        imgVec = self.preprocess(img).unsqueeze(0).to(self.device)
        modelOutput = self.model(imgVec)

        # Get the index of the most likely class
        index = torch.max(modelOutput, 1)[1][0]

        # Get the percentage of the prediction
        percentage = (torch.nn.functional.softmax(modelOutput, dim=1)[0] * 100)[index].item()

        value = {}
        value['choices'] = [self.labels[index]]
        resultItem = {}
        resultItem['id'] = str(uuid.uuid4())
        resultItem['from_name'] = self.fromName
        resultItem['to_name'] = self.toName
        resultItem['type'] = self.type
        resultItem['value'] = value

        self.predictionItem['result'].append(resultItem)

        return self.predictionItem




    def train(self, epochNum, splitParam, batchSize, shuffle, workerNum, learningRate, lossFunc, optimizer):
        # Function to test the model with the test dataset and print the accuracy for the test images
        def testAccuracy():

            self.model.eval()
            accuracy = 0.0
            total = 0.0

            with torch.no_grad():
                for data in self.testLoader:
                    images, truths = data
                    images = images.to(self.device)
                    truths = truths.to(self.device)
                    # print('truths: ' + self.labels[truths[0]])

                    # run the model on the test set to predict labels
                    outputs = self.model(images)
                    # the label with the highest energy will be our prediction
                    _, predicted = torch.max(outputs.data, 1)
                    # print('predictions: ' + self.labels[predicted[0]] + '\n')
                    total += truths.size(0)
                    accuracy += (predicted == truths).sum().item()

            # compute the accuracy over all test images
            accuracy = (100 * accuracy / total)
            return (accuracy)

        def getAnnotations():
            pass


        super().train()

        #TODO: Get annotations from database
        # annotations = getAnnotations()
        annotations = [
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['golden retriever']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['golden retriever']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['golden retriever']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['golden retriever']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['golden retriever']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['Persian cat']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['Persian cat']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['Persian cat']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['Persian cat']}}]},
            {'result': [{'id': '6c3606b2-bfce-48c2-b66d-6d58998efaf1', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['Persian cat']}}]}
        ]

        imgClsSet = ImgClsDataset(annotations, self.preprocess, self.labels)
        trainNum = round(len(imgClsSet) * splitParam)
        testNum = len(imgClsSet) - trainNum
        trainSet, testSet = random_split(imgClsSet, lengths=[trainNum, testNum])
        self.trainLoader = DataLoader(trainSet, batch_size=batchSize, shuffle=shuffle, num_workers=workerNum)
        self.testLoader = DataLoader(testSet, batch_size=batchSize, shuffle=shuffle, num_workers=workerNum)

        # Initialize loss function
        if(lossFunc == 'Cross Entropy'):
            loss_fn = nn.CrossEntropyLoss()
        elif(lossFunc == 'NLL'):
            loss_fn = nn.NLLLoss()

        # Initialize the optimizer
        if (optimizer == 'SGD'):
            optimizer = SGD(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'ASGD'):
            optimizer = ASGD(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'Rprop'):
            optimizer = Rprop(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'Adagrad'):
            optimizer = Adagrad(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'Adadelta'):
            optimizer = Adadelta(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'RMSprop'):
            optimizer = RMSprop(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'Adam'):
            optimizer = Adam(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'Adamax'):
            optimizer = Adamax(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'SparseAdam'):
            optimizer = SparseAdam(self.model.parameters(), lr=learningRate)
        elif (optimizer == 'LBFGS'):
            optimizer = LBFGS(self.model.parameters(), lr=learningRate)


        for epoch in range(epochNum):  # loop over the dataset multiple times

            for i, (images, truths) in enumerate(self.trainLoader, 0):

                # get the inputs
                images = Variable(images.to(self.device))
                truths = Variable(truths.to(self.device))

                # zero the parameter gradients
                optimizer.zero_grad()
                # predict classes using images from the training set
                outputs = self.model(images)
                # compute the loss based on model output and real labels
                loss = loss_fn(outputs, truths)
                # backpropagate the loss
                loss.backward()
                # adjust parameters based on the calculated gradients
                optimizer.step()

            # Compute and print the average accuracy fo this epoch when tested over all 10000 test images
            accuracy = testAccuracy()
            print('For epoch', epoch + 1, 'the test accuracy over the whole test set is %d %%' % (accuracy))





if __name__ == '__main__':
    modelPath = '../../../ml/models/ImageClassification/resnet101.pth'
    modelVersion = 'one'
    modelRoot = './'
    fromName = 'choice'
    toName = 'image'
    toolType = 'choices'
    labelsPath = '../../../ml/resources/ImageNetClasses.txt'

    mean = [0.485, 0.456, 0.406]
    std = [0.229, 0.224, 0.225]
    imgSize = 224

    imgPath = '../../../ml/resources/puppy.webp'
    # imgPath = './persian-cat-5.jpg'

    imgClsModel = ImgClsModel(modelPath, modelVersion, modelRoot, fromName, toName, toolType, labelsPath, mean, std, imgSize)

    # predictionItem = imgClsModel.predict(imgPath)
    # print(predictionItem)

    imgClsModel.train(10, 0.1, 1, False, 1, 0.001, 'Cross Entropy', 'Adam')