from fastapi import UploadFile, File, status
from fastapi.routing import APIRouter

from app.apis.v1.model import InputBase, OutputBase
from app.config import *
from app.core.toxisity_checker import toxisity_checker
from app.core.stop_topics_checker import stop_topics_checker


router = APIRouter(prefix="/v1")


@router.post('/base_process',
             description='Процессинг входного потока сообщений',
             tags=['Inference endpoints'],
             status_code=status.HTTP_200_OK,
             response_model=OutputBase)
def process_base(input_: InputBase) -> OutputBase:
    stop_topics = set()
    for message in input_.messages:
        stop_topics.update(toxisity_checker.classify(message))
        stop_topics.update(stop_topics_checker.classify(message))
    return OutputBase(offer_confidence=True, sentimemt_loggit=4., stop_topics=list(stop_topics))
