import torch


class Model():
    def __init__(self, modelPath, modelVersion, fromName, toName, toolType):
        # Load the model from the file
        self.model = torch.load(modelPath)

        # Flash the model to GPU if available
        self.device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
        self.model = self.model.to(self.device)

        # Set the model to evaluation mode
        self.model.eval()

        self.modelVersion = modelVersion
        self.fromName = fromName
        self.toName = toName
        self.type = toolType

        self.predictionItem = {}

    # Fill in the basic information of a prediction item
    # Specific result items shall be appended to predictionItem['result'] in corresponding subclasses
    def predict(self):

        self.predictionItem['model_version'] = self.modelVersion

        result = []
        self.predictionItem['result'] = result
