import logging

from app.core.utils import transliterate


MATCHING_DICT = {
    'мошеничество': {'мошенник', 'украсть', 'украли', 'кража', 'обман', 'обманул', 'обманули'},
}


class StopTopicsChecker:

	def __init__(self):
		pass

	def _preprocess(self, input_text: str) -> set:
		words_set = set(transliterate(input_text).lower().split())
		return words_set

	def classify(self, input_text: str) -> list[str]:
		words_set = self._preprocess(input_text)
		result = []
		for key, value in MATCHING_DICT.items():
			if words_set.intersection(value):
				result.append(key)
		return result

stop_topics_checker = StopTopicsChecker()
logging.info('Stop topics model loaded')
