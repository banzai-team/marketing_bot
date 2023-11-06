import logging

import torch
from transformers import AutoTokenizer, AutoModelForSequenceClassification

from app.config import TOXISITY_MODEL_PATH
from app.core.utils import transliterate


class ToxisityChecker:

	def __init__(self, path: str = ''):
		"""
		non-toxic: 'Норма'
		insult: 'Оскорбление'
		obscenity: 'Непристойность'
		threat: 'Угроза'
		dangerous: 'Запрещенное высказывание'
		"""
		self.device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
		try:
			self.tokenizer = AutoTokenizer.from_pretrained(path)
			self.model = AutoModelForSequenceClassification.from_pretrained(path).to(self.device)
		except Exception as e:
			logging.error(f'Model loading failed', exc_info=e)

	def _forward(self, input_text: str):
		with torch.no_grad():
			inputs = self.tokenizer(input_text, return_tensors='pt', truncation=True, padding=True).to(self.device)
			logits = torch.sigmoid(self.model(**inputs).logits).cpu().numpy()
		if isinstance(input_text, str):
			logits = logits[0]
		return logits

	def classify(self, text: str, threshold: float = 0.7) -> list[str]:
		input_text = transliterate(text).lower()
		logits = self._forward(input_text)
		# formats of classes
		filthy_language = max(logits[1], logits[2])
		provocative_statement = max(logits[3], logits[4])
		result = []
		if filthy_language >= threshold:
			result.append("Мат или запрещенная лексика")
		if provocative_statement >= threshold:
			result.append("Провокационное высказываение")
		return result


toxisity_checker = ToxisityChecker(path = TOXISITY_MODEL_PATH)
logging.info('Toxisity model loaded')
