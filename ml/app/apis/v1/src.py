from fastapi import UploadFile, File, status
from fastapi.routing import APIRouter

from app.apis.v1.model import InputBase, OutputBase
from app.config import FRAUD_THRESHOLD
from app.core.toxisity_checker import toxisity_checker
from app.core.stop_topics_checker import stop_topics_checker
from app.core.classification import classification_model


router = APIRouter(prefix="/v1")


@router.post('/base_process',
             description='Процессинг входного потока сообщений',
             tags=['Inference endpoints'],
             status_code=status.HTTP_200_OK,
             response_model=OutputBase)
def process_base(input_: InputBase) -> OutputBase:
    stop_topics = set()
    dialogue = '. '.join(input_.messages)
    for message in input_.messages:
        stop_topics.update(toxisity_checker.classify(message))
        stop_topics.update(stop_topics_checker.classify(message))
    classes = classification_model.predict(dialogue)[0]
    if classes[2] >= FRAUD_THRESHOLD:
        stop_topics.update('Найдены стоп темы, которые мы не можем точно определить')
        sentimemt_loggit = -5.
        offer_confidence = False
    else:
        class_ = classes[:2].argmax()
        if class_ == 0:
            offer_confidence=True
            sentimemt_loggit = float(classes[:2][class_]*5.)
        else:
            offer_confidence=False
            sentimemt_loggit = float(-classes[:2][class_]*5.)
    return OutputBase(offer_confidence=offer_confidence, sentimemt_loggit=sentimemt_loggit, stop_topics=list(stop_topics))
