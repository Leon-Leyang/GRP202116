from xml.dom.minidom import parseString, Node
from Models import *

class ModelDriver():
    TOOL_TAGS = ['Labels', 'Choices', 'BrushLabels']

    def __init__(self):
        pass

    @staticmethod
    # Parse the config of a project to get information(i.e. fromName, toName and type)
    def parseConfig(configs):
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

            if(parseDone == False):
                if(len(childNodes) != 0):
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
    def runModelOnData(type, data):
        # TODO: Get configs from database
        configs = """<View>
                                <Image name="image" value="$image" zoom="true"/>
                                <BrushLabels name="tag" toName="image">
                                    <Label value="Airplane" background="rgba(255, 0, 0, 0.7)"/>
                                    <Label value="Car" background="rgba(0, 0, 255, 0.7)"/>
                                </BrushLabels>
                            </View>"""

        # TODO: Get pre-existing predictions from database
        predictions = []

        # TODO: Get modelVersion from database
        modelPath = '../ml/models/fcn/fcn.pth'

        # TODO: Get modelVersion from database
        modelVersion = 'undefined'

        fromName, toName, toolType = ModelDriver.parseConfig(configs)

        if(type == 'Semantic Segmentation Mask'):
            # TODO: Get index2nameMap from database
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

            model = SemSegMaskModel(modelPath, modelVersion, fromName, toName, type, index2nameMap)
        else:
            pass

        predictionItem = model.predict(data)
        # TODO: Merge predictionItems with the same model version together
        predictions.append(predictionItem)

        print(predictions)

        # TODO: Save new predictions in database


    @staticmethod
    # Run model on a list of data
    def runModelOnDatas(type, datas):
        for data in datas:
            ModelDriver.runModelOnData(type, data)


if __name__ == '__main__':
    ModelDriver.runModelOnData('Semantic Segmentation Mask', './puppy.webp')
