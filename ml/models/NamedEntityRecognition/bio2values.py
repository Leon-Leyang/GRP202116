from spacy.tokens import Doc
from spacy.vocab import Vocab
from spacy.training import biluo_tags_to_offsets, iob_to_biluo









if __name__ == '__main__':
    doc = Doc(Vocab(), words=['@HuggingFace', 'is', 'a', 'company', 'based', 'in', 'New', 'York,', 'but', 'is', 'also', 'has', 'employees', 'working', 'in', 'Paris'])
    tags = ['B-org', 'O', 'O', 'O', 'O', 'O', 'B-geo', 'I-geo', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'B-geo']
    tags = iob_to_biluo(tags)
    offsets = biluo_tags_to_offsets(doc, tags)
    print(offsets)