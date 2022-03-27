# Class for preprocessing
# Initialized with function that does the real preprocessing job
class Preprocess():
    def __init__(self, func):
        self.func = func

    def __call__(self, data):
        return self.func(data)