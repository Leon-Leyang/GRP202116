import torch

class Model():
    def __init__(self, modelPath, modelVersion, fromName, toName, type):
        # Load the model from the file
        self.model = torch.load(modelPath)

        # Transfer the model to GPU if available
        self.device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
        self.model = self.model.to(self.device)

        self.model.eval()

        self.modelVersion = modelVersion
        self.fromName = fromName
        self.toName = toName
        self.type = type

        self.predictionItem = {}


    # Fill in the basic information of a prediction item
    # Specific result items shall be appended to predictionItem['result'] in corresponding subclasses
    def predict(self):

        self.predictionItem['model_version'] = self.modelVersion

        result = []
        self.predictionItem['result'] = result