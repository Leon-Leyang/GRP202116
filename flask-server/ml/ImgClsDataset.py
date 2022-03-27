from torch.utils.data import Dataset

class ImgClsDataset(Dataset):
        def __init__(self, annotations):
            self.annotations = annotations
            # TODO: Get datas through annotations
            self.datas = getDatasByAnnos(annotations)

        def __len__(self):
            return len(self.datas)

        def __getitem__(self, index):
            imgPath = self.datas[index]['url']
            img = self.preprocess


def getDatasByAnnos(annotations):
    pass