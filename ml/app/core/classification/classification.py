import torch
from transformers import AutoTokenizer, AutoModel
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
import numpy as np

import pickle
import re
import logging

from app.config import EMBEDDER_MODEL_PATH, CLASSIFICATION_MODEL_PATH


class ClassificationModel:

    def __init__(self, embedder_path, cl_path):
        self.tokenizer = AutoTokenizer.from_pretrained(embedder_path)
        self.model = AutoModel.from_pretrained(embedder_path)
        with open(cl_path, 'rb') as file:
            cl_model = pickle.load(file)
        self.cl_model = cl_model

    def embed_bert_cls(self, text):
        embeddings_list = []
        for sentence in self.split_sentences(text):
            if len(sentence) >= 10:
                t = self.tokenizer(sentence, padding=True, truncation=True, return_tensors='pt')
                with torch.no_grad():
                    model_output = self.model(**{k: v.to(self.model.device) for k, v in t.items()})
                embeddings = model_output.last_hidden_state[:, 0, :]
                embeddings = torch.nn.functional.normalize(embeddings)
                embeddings_list.append(embeddings)
        try:
            sum_tensor = torch.stack(embeddings_list).sum(dim=0)
            sum_tensor = torch.nn.functional.normalize(sum_tensor)
        except:
            sum_tensor = torch.tensor([[0]])
        return sum_tensor.cpu().numpy()

    def split_sentences(self, text):
        sentences = re.split(r'(?<!\w\.\w.)(?<![A-Z][a-z]\.)(?<=\.|\?)\s', text)
        for sentence in sentences:
            if len(sentence) <= 5:
                sentences.remove(sentence)
        return sentences

    def predict(self, text):
        vector = self.embed_bert_cls(text)
        if not vector.any():
            return torch.tensor([[1,0,0]])
        else:
            return self.cl_model.predict_proba(vector)

classification_model = ClassificationModel(embedder_path=EMBEDDER_MODEL_PATH, cl_path=CLASSIFICATION_MODEL_PATH)
logging.info('Toxisity model loaded')
