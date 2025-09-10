from typing_extensions import Annotated, TypedDict
from typing import Literal
from langgraph.graph import MessagesState


class QueryOutput(TypedDict):
    '''Generate SQL query'''
    query: Annotated[str, ..., '문법적으로 올바른 SQL 쿼리']

class QueryAnalysis(TypedDict):
    '''Analyze query as it fits database'''
    analysis: Annotated[  # 질문 분석 결과
        Literal['Yes', 'No'],
        ...,
        'analyze result'
    ]

class State(MessagesState):
    question: str  # 사용자의 질문
    analysis: str  # 질문분석결과
    sql: str  # 여기에 SQL문
    result: str  # SQL 쿼리를 돌린 결과
    answer: str  # 최종 결과물
