'''
Author: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Last Modified: Saturday, 11th April 2022 11:16
Modified By: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Brief: Script class for image classification which overrides the methods for predicting and training and corresponding dataset class used for training
'''

from PIL import Image
from torch.utils.data import DataLoader, random_split
from torch.optim import *
from torch.autograd import Variable
from torch.utils.data import Dataset

import uuid
import ast
import torch
import torchvision.transforms as tf
import torch.nn as nn

from ml import Preprocess
from ml.models.Model import Model

# Model for image classification task
class ImgClsModel(Model):

    def __init__(self, modelPath, modelRoot, labelsPath, modelVersion=None, fromName=None, toName=None, toolType=None,
                 mean=[0.5, 0.5, 0.5], std=[0.5, 0.5, 0.5], imgSize=224):
        super().__init__(modelPath, modelRoot, labelsPath, modelVersion, fromName, toName, toolType)

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
        super().predict(imgPath)

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




    def train(self, datas, annotations, savePath, epochNum, trainFrac, batchSize, shuffle, workerNum, learningRate, lossFunc, optimizer):
        # Function to test the model with the test dataset and print the accuracy for the test images
        def testAccuracy():

            self.model.eval()
            accuracy = 0.0
            total = 0.0

            with torch.no_grad():
                for data in self.testLoader:
                    images, truths = data

                    images, truths = images.to(self.device), truths.to(self.device)

                    # run the model on the test set to predict labels
                    outputs = self.model(images)

                    # the label with the highest energy will be the prediction
                    _, predicted = torch.max(outputs.data, 1)

                    total += truths.size(0)
                    accuracy += (predicted == truths).sum().item()

            # compute the accuracy over all test images
            accuracy = (100 * accuracy / total)
            return accuracy

        super().train(datas, annotations, savePath)

        imgClsSet = ImgClsDataset(datas, annotations, self.preprocess, self.labels)
        trainNum = round(len(imgClsSet) * trainFrac)
        testNum = len(imgClsSet) - trainNum
        trainSet, testSet = random_split(imgClsSet, lengths=[trainNum, testNum])
        self.trainLoader = DataLoader(trainSet, batch_size=batchSize, shuffle=shuffle, num_workers=workerNum)
        self.testLoader = DataLoader(testSet, batch_size=batchSize, shuffle=shuffle, num_workers=workerNum)

        # Initialize loss function
        if(lossFunc == 'CrossEntropyLoss'):
            loss_fn = nn.CrossEntropyLoss()
        elif(lossFunc == 'NLLLoss'):
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

        bestAccuracy = 0.0

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

            if accuracy > bestAccuracy:
                torch.save(self.model, savePath)
                bestAccuracy = accuracy

        return bestAccuracy, trainNum

class ImgClsDataset(Dataset):
    def __init__(self, datas, annotations, preprocess, labels):
        self.datas = datas
        self.annotations = annotations

        self.preprocess = preprocess

        self.labels = labels

    def __len__(self):
        return len(self.datas)

    def __getitem__(self, index):
        imgPath = self.datas[index]['url']
        img = self.preprocess(Image.open(imgPath))
        labelName = ast.literal_eval(self.annotations[index]['result'])[0]['value']['choices'][0]
        label = self.labels.index(labelName)
        return img, label



if __name__ == '__main__':
    modelPath = 'C:/Users/Leon/Desktop/dog-cat-classification/model.pth'
    modelVersion = 'one'
    modelRoot = 'C:/Users/Leon/Desktop/dog-cat-classification'
    fromName = 'choice'
    toName = 'image'
    toolType = 'choices'
    labelsPath = 'C:/Users/Leon/Desktop/dog-cat-classification/class.txt'

    mean = [0.485, 0.456, 0.406]
    std = [0.229, 0.224, 0.225]
    imgSize = 224

    imgPath = 'C:/Users/Leon/Desktop/train/dog.401.jpg'
    # imgPath = './persian-cat-5.jpg'

    imgClsModel = ImgClsModel(modelPath, modelRoot, labelsPath, modelVersion, fromName, toName, toolType, mean, std,
                              imgSize)

    predictionItem = imgClsModel.predict(imgPath)
    print(predictionItem)

    # imgClsModel.train(10, 0.1, 1, False, 1, 0.001, 'Cross Entropy', 'Adam')