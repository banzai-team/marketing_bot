from pydantic import BaseModel

class InputBase(BaseModel):
    messages: list[str]
    id_sequence: int
    is_operator: bool
    text: str

class OutputBase(BaseModel):
    offer_confidence: bool
    sentimemt_loggit: float
    stop_topics: list[str]

