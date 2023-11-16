from transformers import pipeline

from app.config import SENTIMENT_MODEL_PATH


class SentimentClassificationModel:

    def __init__(self, path):
        self.model = pipeline("text-classification", model=path, top_k=3)

    def predict(self, text):
        return self.model(text)


sentiment_classification_model = SentimentClassificationModel(path=SENTIMENT_MODEL_PATH)
