from fastapi import UploadFile, File, status
from fastapi.routing import APIRouter

from app.apis.v1.model import InputBase, OutputBase
from app.config import *


router = APIRouter(prefix="/v1")


@router.post('/base_process',
             description='Процессинг входного потока сообщений',
             tags=['Inference endpoints'],
             status_code=status.HTTP_200_OK,
             response_model=OutputBase)
def process_base(input_: InputBase) -> OutputBase:
    return OutputBase(offer_confidence=True, sentimemt_loggit=4., stop_topics=['Тема петух'])
