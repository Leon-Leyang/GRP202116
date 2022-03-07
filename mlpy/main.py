from xml.dom.minidom import parseString, Node, Element

class ModelDriver():
    TOOL_TAGS = ['Labels', 'Choices']

    def __init__(self):
        pass

    # Parse the config of a project to get information(i.e. fromName, toName and type)
    def parseConfig(self):
        configs = """
                        <View>
                          <Text name="text" value="$text"/>
                          <View style="box-shadow: 2px 2px 5px #999;
                                       padding: 20px; margin-top: 2em;
                                       border-radius: 5px;">
                            <Header value="Choose text sentiment"/>
                            <Choices name="sentiment" toName="text"
                                     choice="single" showInLine="true">
                              <Choice value="Positive"/>
                              <Choice value="Negative"/>
                              <Choice value="Neutral"/>
                            </Choices>
                          </View>
                        </View>"""

        # Denotes the finish of parsing config
        parseDone = False
        fromName = ''
        toName = ''
        type = ''

        # Retrieves nodes rooted at a given node recursively to extract information(i.e. fromName, toName and type)
        def extractInfo(parentNode):
            nonlocal parseDone, fromName, toName, type

            childNodes = parentNode.childNodes

            for childNode in childNodes:
                if (childNode.nodeType == Node.ELEMENT_NODE):
                    if (childNode.tagName in self.TOOL_TAGS):
                        fromName = childNode.getAttribute("name")
                        toName = childNode.getAttribute("toName")
                        type = str(childNode.tagName).lower()
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

        return fromName, toName, type


















if __name__ == '__main__':
    modelDriver = ModelDriver()
    fromName, toName, type = modelDriver.parseConfig()