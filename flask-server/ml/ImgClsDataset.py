from torch.utils.data import Dataset
from PIL import Image

class ImgClsDataset(Dataset):
        def __init__(self, datas, annotations, preprocess, labels):
            self.datas = datas
            self.annotations = annotations

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


if __name__ == '__main__':
    annotations = {'model_version': 'one', 'result': [{'id': '3d5091ef-2376-4842-bc61-d53804131134', 'from_name': 'choice', 'to_name': 'image', 'type': 'choices', 'value': {'choices': ['golden retriever']}}]}
    print(annotations['result'][0]['value']['choices'][0])