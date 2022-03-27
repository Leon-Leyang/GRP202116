from torch.utils.data import Dataset
from PIL import Image

class ImgClsDataset(Dataset):
        def __init__(self, annotations, preprocess, labels):
            self.annotations = annotations

            # TODO: Get datas through annotations
            # self.datas = getDatasByAnnos(annotations)
            self.datas = [
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/golden-retriever-1.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/golden-retriever-2.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/golden-retriever-3.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/golden-retriever-4.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/golden-retriever-5.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/persian-cat-1.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/persian-cat-2.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/persian-cat-3.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/persian-cat-4.jpg'},
                {'url': 'C:/Users/Leon/Desktop/GRP/GRP202116/flask-server/ml/models/persian-cat-5.jpg'},
            ]

            self.preprocess = preprocess

            self.labels = labels

        def __len__(self):
            return len(self.datas)

        def __getitem__(self, index):
            imgPath = self.datas[index]['url']
            img = self.preprocess(Image.open(imgPath))
            labelName = self.annotations[index]['result'][0]['value']['choices'][0]
            label = self.labels.index(labelName)
            return img, label


def getDatasByAnnos(annotations):
    pass

if __name__ == '__main__':
    annotations = {'model_version': 'one', 'result': [{'id': '3d5091ef-2376-4842-bc61-d53804131134', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['golden retriever']}}]}
    print(annotations['result'][0]['value']['choices'][0])