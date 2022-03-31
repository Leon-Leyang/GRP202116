from xml.dom.minidom import parseString, Node
from importlib import import_module

from ml import *
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

    def run_model_on_data(project_type, data, configs, model_path, model_root, labelsPath, model_version, **kwargs):

        from_name, to_name, tool_type = ModelDriver.parse_config(configs)

        if project_type == 'Image Classification':
            model = ImgClsModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                                kwargs['mean'], kwargs['std'], kwargs['imgSize'])
        elif project_type == 'Object Detection':
            model = ObjDecBBoxModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                                    kwargs['threshold'])
        elif project_type == 'Keypoint Labeling':
            model = KpLabModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                               kwargs['threshold'])
        elif project_type == 'Semantic Segmentation Mask':
            model = SemSegMaskModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                                    kwargs['mean'], kwargs['std'])
        elif project_type == 'Text Classification':
            model = TextClsModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                                 kwargs['vocabPath'], kwargs['tokenNum'], kwargs['sequenceLen'])
        elif project_type == 'Named Entity Recognition':
            model = NERModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type,
                             kwargs['sequenceLen'])
        elif project_type == 'Custom':
            moduleName = 'ml.models.' + kwargs['scriptName']
            module = import_module(moduleName)
            model = module.CustomModel(model_path, model_root, labelsPath, model_version, from_name, to_name, tool_type)
        else:
            print("model undefined")

        prediction_item = model.predict(data)
        # TODO: Merge predictionItems with the same model version together

        print(prediction_item)
        return prediction_item

    @staticmethod
    # Run model on a list of data
    def run_model_on_data_set(project_type, data_set, configs, model_path, model_root, labelsPath, model_version,
                              **kwargs):
        for data in data_set:
            ModelDriver.run_model_on_data(project_type, data, configs, model_path, model_root, labelsPath,
                                          model_version, **kwargs)

    @staticmethod
    def train_model_on_data_set(project_type, datas, annotations, model_path, model_root, labelsPath, savePath, **kwargs):
        if project_type == 'Image Classification':
            model = ImgClsModel(model_path, model_root, labelsPath, mean=kwargs['mean'], std=kwargs['std'],
                                imgSize=kwargs['imgSize'])
            accuracy = model.train(datas, annotations, savePath, kwargs['epochNum'], kwargs['trainFrac'], kwargs['batchSize'], kwargs['shuffle'], kwargs['workerNum'], kwargs['learningRate'], kwargs['lossFunc'], kwargs['optimizer'])
        elif project_type == 'Custom':
            moduleName = 'ml.models.' + kwargs['scriptName']
            module = import_module(moduleName)
            model = module.CustomModel(model_path, model_root, labelsPath)
            accuracy = model.train(datas, annotations, savePath)
        else:
            print("model undefined")
            return

        return accuracy




if __name__ == '__main__':
    # configs = """<View>
    #                                 <Image name="image" value="$image" zoom="true"/>
    #                                 <BrushLabels name="tag" toName="image">
    #                                     <Label value="Airplane" background="rgba(255, 0, 0, 0.7)"/>
    #                                     <Label value="Car" background="rgba(0, 0, 255, 0.7)"/>
    #                                 </BrushLabels>
    #                             </View>"""
    #
    # predictions = []  # this is not necessary in here
    # model_path = '../ml/models/SemanticSegmentation/fcn.pth'
    # model_version = 'undefined'
    # model_root = './'
    # labelsPath = '../ml/resources/voc2007.txt'
    # mean = [0.485, 0.456, 0.406]
    # std = [0.229, 0.224, 0.225]
    #
    # ModelDriver.run_model_on_data('Semantic Segmentation Mask', '../ml/resources/puppy.webp', configs, model_path, model_root, labelsPath, model_version, mean=mean, std=std)

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

    ModelDriver.train_model_on_data_set('Image Classification', datas, annotations, model_path, model_root, labelsPath, savePath, mean=mean, std=std, imgSize=imgSize, epochNum=epochNum, trainFrac=trainFrac, batchSize=batchSize, shuffle=shuffle, workerNum=workerNum, learningRate=learningRate, lossFunc=lossFunc, optimizer=optimizer)
    ModelDriver.train_model_on_data_set('Custom', datas, annotations, model_path, model_root, labelsPath, savePath, scriptName=scriptName)
