'''
Author: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Last Modified: Sunday, 27th March 2022 10:31
Modified By: Leyang Hu (scylh6@nottingham.edu.cn)
-----
Brief: Base class for preprocessing which is initialized with function that does the real preprocessing job
'''

# Class for preprocessing
# Initialized with function that does the real preprocessing job
class Preprocess():
    def __init__(self, func):
        self.func = func

    def __call__(self, data):
        return self.func(data)