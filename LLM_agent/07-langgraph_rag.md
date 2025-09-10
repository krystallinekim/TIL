# LangGraph와 RAG 결합

## 기본적인 RAG 파이프라인

가장 기본적인 RAG는 **데이터 준비** 과정과 **실행** 과정으로 나뉜다.

### 데이터 준비

먼저 외부 데이터를 LLM이 참조할 수 있는 형태로 가공하여 데이터베이스에 저장. 이 과정은 보통 **Load -> Split -> Embed -> Store** 순서로 진행된다.

1.  **`Loader`**: 웹 페이지, PDF, 텍스트 파일 등 다양한 소스에서 원본 문서를 불러온다.
2.  **`Splitter`**: 불러온 문서를 LLM의 컨텍스트 길이에 맞는 적절한 크기의 조각(chunk)으로 나눈다.
3.  **`Embedding`**: 각 문서 조각을 고차원 벡터 공간의 한 점으로 매핑(임베딩). 이 과정은 텍스트의 의미를 숫자 벡터로 변환.
4.  **`Vectorstore`**: 변환된 벡터를 저장하고, 특정 벡터와 유사한 벡터를 빠르게 검색(Similarity Search)할 수 있는 벡터 데이터베이스를 구축.

여기까지는 LLM과 상관 없다.

### 상태(State) 및 노드(Node) 정의

RAG 파이프라인의 데이터 흐름을 관리할 **상태(State)** 객체를 정의

```python
from typing import List
from typing_extensions import TypedDict
from langchain_core.documents import Document

# RAG 파이프라인의 데이터 흐름을 정의하는 상태
class State(TypedDict):
    question: str
    context: List[Document]
    answer: str
```
이 때, 각 요소의 타입은 실제로 결과가 나오는 대로 작성하면 된다.

- 벡터스토어를 이용할 경우, 결과는 Document 타입 문서들이 리스트 안에 묶여 나와 `List[Document]`
- 특정 결과값만 넣고 싶다면, `Annotated`나 `Literal`같은 방법을 쓰면 좋음

```py
# [노드 1] 검색(Retrieve): 질문과 관련된 문서를 Vectorstore에서 검색
def retrieve(state: State):
    print("---문서 검색---")
    # k=4: 질문과 가장 유사한 문서 4개를 가져옴
    retrieved_docs = vectorstore.similarity_search(state['question'], k=4)
    return {'context': retrieved_docs}

# [노드 2] 생성(Generate): 검색된 문서를 바탕으로 LLM이 답변 생성
def generate(state: State):
    print("---답변 생성---")
    # RAG 전용 프롬프트를 사용하여 질문과 검색된 문맥(context)을 함께 전달
    
    question_with_context = prompt.invoke({'question': state['question'], 'context': state['context']})
    
    # LLM을 통해 답변 생성
    llm = ChatOpenAI(model='gpt-4.1', temperature=0)
    response = llm.invoke(question_with_context)
    return {'answer': response.content}
```

### RAG 그래프 구축

`retrieve` 노드와 `generate` 노드를 순서대로 연결하여 RAG 그래프를 완성.

```python
from langgraph.graph import START, END, StateGraph

builder = StateGraph(State)

# 노드 추가
builder.add_node("retrieve", retrieve)
builder.add_node("generate", generate)

# 엣지(흐름) 연결
builder.add_edge(START, "retrieve")         # 시작하면 retrieve 노드 실행
builder.add_edge("retrieve", "generate")    # retrieve가 끝나면 generate 노드 실행
builder.add_edge("generate", END)           # generate가 끝나면 그래프 종료

# 그래프 컴파일
graph = builder.compile()

# 그래프 실행 및 결과 확인
final_state = graph.invoke({'question': '에이전트 시스템에 대해 알려줘'})
print(final_state['answer'])
```

## RAG 파이프라인 개선

가장 기본적인 RAG 파이프라인은 사용자의 모호한 질문이나 검색 품질 저하로 인해 정확한 답변을 생성하지 못할 수 있다

### 쿼리 분석 및 변환 (Query Analysis)

사용자의 질문("문서 앞부분에 있는 그 내용이 뭐였지?")처럼 모호하거나 특정 조건을 포함하는 경우, **LLM을 사용해 질문 자체를 분석**하여 검색에 더 적합한 구조화된 쿼리로 변환할 수 있다.

`with_structured_output` 기능을 사용하면, LLM이 지정된 Pydantic 모델이나 TypedDict와 같은 특정 스키마에 맞는 결과물을 생성하도록 강제할 수도 있다.

```python
from typing import Literal
from langchain_core.pydantic_v1 import BaseModel, Field

# 1. 검색 쿼리를 위한 구조 정의 (Pydantic 모델)
class Search(BaseModel):
    """Vectorstore 검색을 위한 쿼리 구조"""
    query: str = Field(description="실행할 검색 쿼리")
    section: Literal['beginning', 'middle', 'end'] = Field(description="검색할 문서의 섹션")

# 2. 쿼리 분석 노드
def analyze_query(state: State):
    """사용자의 질문을 분석하여 구조화된 쿼리(Search)로 변환"""
    print("---쿼리 분석---")
    # LLM이 Search 스키마에 맞춰 답변을 생성하도록 설정
    structured_llm = llm.with_structured_output(Search)
    
    # 예: "문서 앞쪽의 LLM 작업 분배 내용이 뭐야?" -> Search(query='LLM 작업 분배', section='beginning')
    analyzed_query = structured_llm.invoke(state['question'])
    
    # 상태에 분석된 쿼리 저장
    return {'query': analyzed_query}
```

이제, 새로운 `analyze_query` 노드를 파이프라인의 시작 부분에 추가.

그래프는 **질문 분석 → 필터링된 검색 → 답변 생성**의 흐름으로 동작하여 더 정확한 결과를 도출할 수 있게 된다.

```python
# 새로운 상태 정의
class AdvancedState(State):
    query: Search # 분석된 쿼리 저장

# 그래프 빌더 초기화
builder = StateGraph(AdvancedState)

# 노드 추가
builder.add_node("analyze_query", analyze_query)
builder.add_node("retrieve", retrieve_with_filter) # 개선된 검색 노드 사용
builder.add_node("generate", generate)

# 엣지 연결
builder.add_edge(START, "analyze_query")
builder.add_edge("analyze_query", "retrieve")
builder.add_edge("retrieve", "generate")
builder.add_edge("generate", END)

graph = builder.compile()
```

### 메타데이터를 활용한 필터링

문서 조각(chunk)에 **메타데이터**를 추가하면, 검색 시 특정 조건을 만족하는 문서만 필터링할 수 있다. 예를 들어, 문서의 위치(`beginning`, `middle`, `end`)를 메타데이터로 지정하면 해당 부분에서만 검색을 수행하여 정확도를 높일 수 있다.

```python
# [데이터 준비 단계] 문서 조각(splitted_docs)에 메타데이터 추가
third = len(splitted_docs) // 3
for i, doc in enumerate(splitted_docs):
    if i < third:
        doc.metadata['section'] = 'beginning'
    elif i < 2 * third:
        doc.metadata['section'] = 'middle'
    else:
        doc.metadata['section'] = 'end'

# [개선된 검색 노드]
def retrieve_with_filter(state: State):
    """분석된 쿼리를 사용하여 메타데이터 필터링과 함께 검색 수행"""
    print("---필터링된 검색---")
    query_info = state['query']
    
    # vectorstore의 search 함수에 메타데이터 필터링 옵션 추가
    retrieved_docs = vectorstore.similarity_search(
        query_info.query,
        filter={'section': query_info.section} # section이 일치하는 문서만 검색
    )
    return {'context': retrieved_docs}
```

## 대화형 RAG (Conversational RAG)

RAG 파이프라인을 챗봇처럼 사용자와 지속적으로 상호작용하도록 확장. 핵심 아이디어는 **검색(Retrieve) 단계를 LLM이 필요할 때만 호출하는 '도구(Tool)'로 만드는 것**.

-   **Tool 기반 RAG**: `retrieve` 함수를 `@tool` 데코레이터를 사용하여 LangChain 툴로 변환.
-   **대화 상태 관리**: `MessagesState`를 사용하여 전체 대화 기록(사용자 질문, AI 답변, Tool 호출 결과)을 관리.
-   **조건부 라우팅**: `llm.bind_tools()`를 통해 LLM이 사용자 질문에 바로 답할지, 아니면 `retrieve` 툴을 호출할지 결정하도록 함. `tools_condition`은 이 결정에 따라 다음 단계를 동적으로 선택.

```python
from langchain_core.tools import tool
from langgraph.prebuilt import ToolNode, tools_condition
from langgraph.graph import StateGraph, MessagesState, START, END

# 1. 검색 함수를 Tool로 정의
@tool
def retrieve(query: str):
    """'query'와 관련된 정보를 검색합니다."""
    return vectorstore.similarity_search(query, k=3)

# LLM에 Tool 바인딩
llm_with_tools = llm.bind_tools([retrieve])

# 2. 그래프 상태: MessagesState 사용
# MessagesState는 'messages' 키에 메시지 리스트를 저장
builder = StateGraph(MessagesState)

# 3. 노드 정의
# (1) LLM이 Tool을 호출할지, 그냥 답할지 결정하는 노드
def call_model(state: MessagesState):
    print("---모델 호출---")
    response = llm_with_tools.invoke(state['messages'])
    return {'messages': [response]}

# (2) Tool을 실행하는 노드
tool_node = ToolNode([retrieve])

# 4. 그래프 구성
builder.add_node("call_model", call_model)
builder.add_node("call_tool", tool_node)

builder.add_edge(START, "call_model")

# 5. 조건부 엣지: Tool 호출 여부에 따라 분기
builder.add_conditional_edges(
    "call_model",
    tools_condition, # LLM의 tool_calls 유무에 따라 분기
    {
        "tools": "call_tool", # Tool을 호출해야 하면 call_tool 노드로
        END: END              # Tool 호출이 필요 없으면 종료
    }
)

# Tool 실행 후 다시 모델을 호출하여 최종 답변 생성
builder.add_edge("call_tool", "call_model")

graph = builder.compile()
```