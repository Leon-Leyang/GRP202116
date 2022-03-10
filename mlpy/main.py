import pickle
from xml.dom.minidom import parseString, Node
from PIL import Image
from label_studio_converter import brush
import torch
import torchvision.transforms as tf
import numpy as np
import matplotlib.pyplot as plt
import uuid

class ModelDriver():
    TOOL_TAGS = ['Labels', 'Choices', 'BrushLabels']

    def __init__(self):
        pass

    # Parse the config of a project to get information(i.e. fromName, toName and type)
    def parseConfig(self):
        configs = """<View>
                        <Image name="image" value="$image" zoom="true"/>
                        <BrushLabels name="label" toName="image">
                            <Label value="Airplane" background="rgba(255, 0, 0, 0.7)"/>
                            <Label value="Car" background="rgba(0, 0, 255, 0.7)"/>
                        </BrushLabels>
                    </View>"""
        # """
        #                 <View>
        #                   <Text name="text" value="$text"/>
        #                   <View style="box-shadow: 2px 2px 5px #999;
        #                                padding: 20px; margin-top: 2em;
        #                                border-radius: 5px;">
        #                     <Header value="Choose text sentiment"/>
        #                     <Choices name="sentiment" toName="text"
        #                              choice="single" showInLine="true">
        #                       <Choice value="Positive"/>
        #                       <Choice value="Negative"/>
        #                       <Choice value="Neutral"/>
        #                     </Choices>
        #                   </View>
        #                 </View>"""



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
                    if (childNode.tagName in self.TOOL_TAGS):
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


    def runModel(self):
        predictions = []
        result = []

        fromName, toName, toolType = self.parseConfig()

        # Map to store class index with its name
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

        model = torch.load('../ml/models/fcn/fcn.pth')
        model.eval()

        img = Image.open('./puppy.webp')

        transforms = tf.Compose([
                 # tf.Resize(256),
                 # tf.CenterCrop(224),
                 tf.ToTensor(),
                 tf.Normalize(mean = [0.485, 0.456, 0.406],
                             std = [0.229, 0.224, 0.225])])

        if(torch.cuda.is_available()):
            imgVec = transforms(img).unsqueeze(0).to('cuda')
            modelOutput = model.to('cuda')(imgVec)['out']
        else:
            imgVec = transforms(img).unsqueeze(0)
            modelOutput = model(imgVec)['out']

        output = torch.argmax(modelOutput.squeeze(), dim=0).detach().cpu().numpy()
        # print('output: ' + str(output))
        print('classIndexes: ' + str(np.unique(output)))


        # Generate resultItem for each class in the image
        for classIndex in np.unique(output):
            outputCopy = np.where(output==classIndex, 255, 0)

            rle = brush.mask2rle(outputCopy.astype(np.uint8))
            # print('rle: ' + str(rle))

            value = {}
            value['format'] = 'rle'
            value['brushlabels'] = [index2nameMap[classIndex]]
            value['rle'] = rle

            resultItem = {}
            resultItem['id'] = str(uuid.uuid4())
            resultItem['from_name'] = fromName
            resultItem['to_name'] = toName
            resultItem['type'] = toolType
            resultItem['value'] = value
            result.append(resultItem)

        predictionItem = {}
        predictionItem['result'] = result

        predictions.append(predictionItem)
        print(predictions)









        # For development checking
        # rle2image = brush.decode_rle(rle)
        # img = rle2image.reshape((734, 1100, 4))
        # print(img.shape)
        # plt.imshow(img)
        # plt.show()

        # For development checking
        # rgb = self.decode_segmap(output)
        # plt.imshow(rgb)
        # plt.show()













    # For development checking
    def decode_segmap(self,image, nc=21):

        label_colors = np.array([(0, 0, 0),  # 0=background
                                 # 1=aeroplane, 2=bicycle, 3=bird, 4=boat, 5=bottle
                                 (128, 0, 0), (0, 128, 0), (128, 128, 0), (0, 0, 128), (128, 0, 128),
                                 # 6=bus, 7=car, 8=cat, 9=chair, 10=cow
                                 (0, 128, 128), (128, 128, 128), (64, 0, 0), (192, 0, 0), (64, 128, 0),
                                 # 11=dining table, 12=dog, 13=horse, 14=motorbike, 15=person
                                 (192, 128, 0), (64, 0, 128), (192, 0, 128), (64, 128, 128), (192, 128, 128),
                                 # 16=potted plant, 17=sheep, 18=sofa, 19=train, 20=tv/monitor
                                 (0, 64, 0), (128, 64, 0), (0, 192, 0), (128, 192, 0), (0, 64, 128)])

        r = np.zeros_like(image).astype(np.uint8)
        g = np.zeros_like(image).astype(np.uint8)
        b = np.zeros_like(image).astype(np.uint8)

        for l in range(nc):
            idx = image == l
            r[idx] = label_colors[l, 0]
            g[idx] = label_colors[l, 1]
            b[idx] = label_colors[l, 2]


        rgb = np.stack([r, g, b], axis=2)
        return rgb








if __name__ == '__main__':
    modelDriver = ModelDriver()
    # fromName, toName, type = modelDriver.parseConfig()
    # print('fromName: ' + fromName)
    # print('toName: ' + toName)
    # print('type: ' + type)

    modelDriver.runModel()
