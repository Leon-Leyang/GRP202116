'''
Author: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Last Modified: Saturday, 26th March 2022 18:56
Modified By: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Brief: Class that controls the parsing of config and running and training of different models
'''

import json
from xml.dom.minidom import parseString, Node
from importlib import import_module

from ml.models.SemSegMaskModel import SemSegMaskModel
from ml.models.ImgClsModel import ImgClsModel
from ml.models.KpLabModel import KpLabModel
from ml.models.NERModel import NERModel
from ml.models.ObjDecBBoxModel import ObjDecBBoxModel
from ml.models.TextClsModel import TextClsModel


class ModelDriver():
    TOOL_TAGS = ['Labels', 'Choices', 'BrushLabels', 'RectangleLabels', 'KeyPointLabels']

    def __init__(self):
        pass

    @staticmethod
    # Parse the config of a project to get information(i.e. fromName, toName and type)
    def parse_config(configs):
        # Denotes the finish of parsing config
        parseDone = False
        fromName = ''
        toName = ''
        toolType = ''

        # Retrieves nodes rooted at a given node recursively to extract information(i.e. fromName, toName and type)
        def extractInfo(parentNode):
            nonlocal parseDone, fromName, toName, toolType

            childNodes = parentNode.childNodes

            for childNode in childNodes:
                if (childNode.nodeType == Node.ELEMENT_NODE):
                    if (childNode.tagName in ModelDriver.TOOL_TAGS):
                        fromName = childNode.getAttribute("name")
                        toName = childNode.getAttribute("toName")
                        toolType = str(childNode.tagName).lower()
                        parseDone = True
                        break

            if (parseDone == False):
                if (len(childNodes) != 0):
                    for childNode in childNodes:
                        extractInfo(childNode)
            else:
                parseDone = False

        # Construct dom tree from config
        DOMTree = parseString(configs)

        # Get the root element of the dom tree
        rootElement = DOMTree.documentElement

        extractInfo(rootElement)

        return fromName, toName, toolType

    @staticmethod
    # Run model on a single data and update its predictions
    def run_model_on_data(script_type, data, configs, model_path, model_root, labelsPath, model_version, params):

        from_name, to_name, tool_type = ModelDriver.parse_config(configs)

        if script_type == 'Image Classification':
            print(from_name)
            print(to_name)
            print(tool_type)
            model = ImgClsModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                                json.loads(params['mean']), json.loads(params['std']), json.loads(params['imgSize']))
        elif script_type == 'Object Detection':
            model = ObjDecBBoxModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                                    json.loads(params['threshold']))
        elif script_type == 'Keypoint Labeling':
            model = KpLabModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                               json.loads(params['threshold']))
        elif script_type == 'Semantic Segmentation Mask':
            model = SemSegMaskModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                                    json.loads(params['mean']), json.loads(params['std']))
        elif script_type == 'Text Classification':
            model = TextClsModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                                 params['vocabPath'], json.loads(params['tokenNum']), json.loads(params['sequenceLen']))
        elif script_type == 'Named Entity Recognition':
            model = NERModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                             json.loads(params['sequenceLen']))
        elif script_type == 'Customization':
            moduleName = 'ml.models.' + params['scriptName']
            module = import_module(moduleName)
            model = module.CustomModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type)
        else:
            print("model undefined")

        prediction_item = model.predict(data)

        print(prediction_item)
        return prediction_item

    @staticmethod
    # Run model on a list of data
    def run_model_on_data_set(script_type, data_set, configs, model_path, model_root, labelsPath, model_version, params):
        for data in data_set:
            ModelDriver.run_model_on_data(script_type, data, configs, model_path, model_root, labelsPath,
                                          model_version, params)

    @staticmethod
    def train_model_on_data_set(script_type, datas, annotations, model_path, model_root, labelsPath, params):
        if script_type == 'Image Classification':

            model = ImgClsModel(model_path, model_root, labelsPath, mean=json.loads(params['mean']), std=json.loads(params['std']),
                                imgSize=json.loads(params['imgSize']))
            accuracy, trainNum = model.train(datas, annotations, params['savePath'], json.loads(params['epochNum']), json.loads(params['trainFrac']), json.loads(params['batchSize']), params['shuffle']=='True', json.loads(params['workerNum']), json.loads(params['learningRate']), params['lossFunc'], params['optimizer'])
        elif script_type == 'Customization':
            moduleName = 'ml.models.' + params['scriptName']
            module = import_module(moduleName)
            model = module.CustomModel(model_path, model_root, labelsPath)
            accuracy, trainNum = model.train(datas, annotations, params['savePath'])
        else:
            print("model undefined")
            return

        return accuracy, trainNum


if __name__ == '__main__':
    datas = [
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/golden-retriever-1.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/golden-retriever-2.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/golden-retriever-3.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/golden-retriever-4.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/golden-retriever-5.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/persian-cat-1.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/persian-cat-2.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/persian-cat-3.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/persian-cat-4.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/ml/resources/persian-cat-5.jpg'},
    ]

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

    model_path = '../ml/models/ImageClassification/resnet101.pth'
    model_root = './'
    labelsPath = '../ml/resources/ImageNetClasses.txt'

    mean = [0.485, 0.456, 0.406]
    std = [0.229, 0.224, 0.225]
    imgSize = 224

    savePath = 'C:/Users/Leon/Desktop'

    epochNum = 10
    trainFrac = 0.1
    batchSize = 1
    shuffle = False
    workerNum = 1
    learningRate = 0.001
    lossFunc = 'Cross Entropy'
    optimizer = 'Adam'

    scriptName = 'CustomModel'


    # params = {
    #     'mean': mean,
    #     'std': std,
    #     'imgSize': imgSize,
    #     'epochNum': epochNum,
    #     'trainFrac': trainFrac,
    #     'batchSize': batchSize,
    #     'shuffle': shuffle,
    #     'workerNum': workerNum,
    #     'learningRate': learningRate,
    #     'lossFunc': lossFunc,
    #     'optimizer': optimizer,
    #     'savePath': savePath
    # }

    params = {
        'scriptName' : 'CustomModel',
        'savePath': savePath
    }

    # ModelDriver.train_model_on_data_set('Image Classification', datas, annotations, model_path, model_root, labelsPath, params)
    ModelDriver.train_model_on_data_set('Custom', datas, annotations, model_path, model_root, labelsPath, params)
