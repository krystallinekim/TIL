

## RAG 결합

RAG는 외부 문서나 데이터를 참조하여 LLM이 더 정확하고 풍부한 답변을 생성하도록 하는 기술입니다. LangGraph를 사용하면 RAG의 각 단계(검색, 생성 등)를 독립적인 노드로 정의하여 전체 파이프라인을 명확하고 직관적으로 구축할 수 있습니다.

### RAG 파이프라인

RAG는 보통 **Load -> Split -> Embed -> Store**의 데이터 준비 과정을 거칩니다. 이 과정을 통해 질문과 관련된 문서를 효율적으로 찾아낼 수 있는 벡터 데이터베이스를 구축합니다.

1.  **Loader**: 웹 페이지, PDF, 텍스트 파일 등 다양한 소스에서 원본 문서를 불러옵니다.
2.  **Splitter**: 불러온 문서를 LLM의 컨텍스트에 들어갈 수 있는 적절한 크기의 조각(chunk)으로 나눕니다. 의미 있는 단위로 나누는 것이 중요합니다.
3.  **Embedding**: 각 조각을 고차원 벡터 공간의 한 점으로 매핑(임베딩)합니다. 텍스트의 의미를 벡터로 변환하는 과정입니다.
4.  **Vectorstore**: 변환된 벡터를 저장하고, 특정 벡터와 유사한 벡터를 빠르게 검색(Similarity Search)할 수 있는 데이터베이스를 구축합니다.

### RAG를 위한 LangGraph 상태 및 노드 정의

RAG 파이프라인을 LangGraph로 구현하기 위해, 질문(`question`), 검색된 문서(`context`), 최종 답변(`answer`)을 포함하는 새로운 커스텀 상태를 정의합니다.

```python
from typing_extensions import TypedDict, List
from langchain_core.documents import Document

# RAG 파이프라인의 데이터 흐름을 정의하는 상태
class State(TypedDict):
    question: str
    context: List[Document]
    answer: str

# [노드 1] 검색(Retrieve): 질문과 관련된 문서를 Vectorstore에서 검색
def retrieve(state: State):
    print("---RETRIEVING DOCUMENTS---")
    # k=4: 가장 유사한 문서 4개를 가져옴
    retrieved_docs = vectorstore.similarity_search(state['question'], k=4)
    return {'context': retrieved_docs}

# [노드 2] 생성(Generate): 검색된 문서를 바탕으로 LLM이 답변 생성
def generate(state: State):
    print("---GENERATING ANSWER---")
    # RAG 전용 프롬프트를 사용하여 질문과 검색된 문맥(context)을 함께 전달
    # prompt = hub.pull('rlm/rag-prompt')
    question_with_context = prompt.invoke({'question': state['question'], 'context': state['context']})
    
    llm = ChatOpenAI(model='gpt-4.1', temperature=0)
    res = llm.invoke(question_with_context)
    return {'answer': res.content}
```

### RAG 그래프 구축

`retrieve` 노드와 `generate` 노드를 순서대로 연결하여 RAG 그래프를 완성합니다. 이 그래프는 사용자의 질문을 받아 문서를 검색하고, 검색된 내용을 바탕으로 답변을 생성하는 전체 과정을 자동화합니다.

```python
from langgraph.graph import START, END, StateGraph

builder = StateGraph(State)

# 노드 추가
builder.add_node('retrieve', retrieve)
builder.add_node('generate', generate)

# 엣지(흐름) 연결
builder.add_edge(START, 'retrieve')           # 시작하면 retrieve 노드 실행
builder.add_edge('retrieve', 'generate')      # retrieve가 끝나면 generate 노드 실행
builder.add_edge('generate', END)             # generate가 끝나면 그래프 종료

# 그래프 컴파일
graph = builder.compile()

# 그래프 실행 및 결과 확인
final_state = graph.invoke({'question': '에이전트 시스템에 대해 알려줘'})
print(final_state['answer'])
```

## RAG 파이프라인 개선

기본적인 RAG 파이프라인에 몇 가지 단계를 추가하여 성능을 높일 수 있습니다. 예를 들어, 문서에 메타데이터를 추가하여 필터링하거나, 사용자의 질문을 분석하여 더 효과적인 검색 쿼리를 생성할 수 있습니다.

### 1. 메타데이터를 활용한 필터링

문서 조각(chunk)에 메타데이터를 추가하면, 검색 시 특정 조건을 만족하는 문서만 필터링할 수 있습니다. 예를 들어, 문서의 특정 섹션(`beginning`, `middle`, `end` 등)을 메타데이터로 지정하면 해당 부분에서만 검색을 수행하여 정확도를 높일 수 있습니다.

```python
# 문서 조각(splitted_docs)에 메타데이터 추가
third = len(splitted_docs) // 3
for idx, doc in enumerate(splitted_docs):
    if idx < third:
        doc.metadata['section'] = 'beginning'
    elif idx < third * 2:
        doc.metadata['section'] = 'middle'
    else:
        doc.metadata['section'] = 'end'

# 메타데이터가 추가된 벡터스토어 생성
vectorstore = FAISS.from_documents(splitted_docs, embedding=embedding)
```

### 2. 쿼리 분석 및 변환 (Query Analysis)

사용자의 질문이 모호하거나 "문서 앞부분에 있는..."과 같이 특정 부분을 암시하는 경우, LLM을 사용하여 질문을 분석하고 검색에 더 적합한 구조화된 쿼리로 변환할 수 있습니다.

이를 위해 `with_structured_output`을 사용하여 LLM이 특정 스키마(예: `Search` 클래스)에 맞는 결과물을 생성하도록 강제합니다.

```python
from typing import Literal
from typing_extensions import Annotated, TypedDict
from langchain_core.documents import Document

# 1. 검색 쿼리를 위한 구조 정의 (TypedDict)
class Search(TypedDict):
    """Vectorstore 검색을 위한 쿼리 구조"""
    query: Annotated[str, ..., '실행할 검색 쿼리']
    section: Annotated[
        Literal['beginning', 'middle', 'end'],
        ...,
        '검색할 문서 섹션'
    ]

# 2. 새로운 파이프라인 상태 정의
class MyState(TypedDict):
    question: str
    query: Search  # 분석된 쿼리가 저장될 필드
    context: List[Document]
    answer: str

# 3. 쿼리 분석 노드
def analyze_query(state: MyState):
    """사용자의 질문을 분석하여 구조화된 쿼리(Search)로 변환"""
    # LLM이 Search 스키마에 맞춰 답변을 생성하도록 설정
    s_llm = llm.with_structured_output(Search)
    # 예: "문서 앞쪽의 LLM 작업 분배 내용이 뭐야?" -> {'query': 'LLM 작업 분배', 'section': 'beginning'}
    query = s_llm.invoke(state['question'])
    return {'query': query}

# 4. 수정된 검색 노드
def retrieve(state: MyState):
    """분석된 쿼리를 사용하여 메타데이터 필터링과 함께 검색 수행"""
    query = state['query']
    retrieved_docs = vectorstore.similarity_search(
        query['query'],
        # filter 함수를 사용하여 메타데이터의 section이 쿼리의 section과 일치하는 문서만 선택
        filter=lambda metadata: metadata.get('section') == query['section']
    )
    return {'context': retrieved_docs}
```

### 3. 개선된 RAG 그래프 구축

새로운 `analyze_query` 노드를 파이프라인의 시작 부분에 추가합니다. 이제 그래프는 **질문 분석 -> 필터링된 검색 -> 답변 생성**의 흐름으로 동작합니다.

```python
builder = StateGraph(MyState)

# 노드 추가
builder.add_node('analyze_query', analyze_query)
builder.add_node('retrieve', retrieve)
builder.add_node('generate', generate) # generate 노드는 이전과 동일

# 엣지 연결
builder.add_edge(START, 'analyze_query')
builder.add_edge('analyze_query', 'retrieve')
builder.add_edge('retrieve', 'generate')
builder.add_edge('generate', END)

graph = builder.compile()

# 실행
# "문서 앞쪽에 있는..." 이라는 모호한 질문도 처리 가능
graph.invoke({'question': '그그 문서 앞쪽에 LLM 작ㅇ업 분배인가 하는 그게 뭐야'})
```

이처럼 LangGraph를 사용하면 RAG 파이프라인의 각 단계를 모듈화하여 유연하게 확장하고 개선할 수 있습니다.


### 대화형 RAG (Conversational RAG)

RAG 파이프라인을 대화형으로 확장하여 사용자와의 지속적인 상호작용을 지원할 수 있습니다.

#### 1. RAG에 챗봇 연결

기존 RAG 파이프라인을 챗봇처럼 사용하기 위해, 검색(Retrieve) 단계를 LLM이 필요할 때 호출하는 '도구(Tool)'로 만듭니다. 이를 통해 LLM은 사용자의 질문을 이해하고, 정보가 부족하다고 판단되면 스스로 검색 도구를 사용하여 문서를 찾은 뒤 답변을 생성합니다.

-   **Tool 기반 RAG**: `retrieve` 함수를 `@tool` 데코레이터를 사용하여 LangChain 툴로 정의합니다.
-   **대화 상태 관리**: `MessagesState`를 사용하여 전체 대화 기록을 관리합니다.
-   **조건부 라우팅**: `llm.bind_tools()`를 통해 LLM이 사용자 질문에 바로 답할지, 아니면 `retrieve` 툴을 호출할지 결정하도록 합니다. `tools_condition`은 이 결정에 따라 다음 단계를 동적으로 선택합니다.

```python
from langchain_core.tools import tool
from langgraph.prebuilt import ToolNode, tools_condition
from langgraph.graph import StateGraph, MessagesState, START, END
from langchain_core.messages import SystemMessage

# 1. 검색 함수를 Tool로 정의
@tool
def retrieve(query: str):
    '''Retrieve information related to a query.'''
    # vectorstore는 미리 정의되어 있다고 가정
    docs = vectorstore.similarity_search(query, k=3)
    return docs

# 2. LLM이 Tool을 호출할지, 그냥 답할지 결정하는 노드
def query_or_respond(state: MessagesState):
    llm_with_tools = llm.bind_tools([retrieve])
    res = llm_with_tools.invoke(state['messages'])
    return {'messages': [res]}

# 3. 검색된 내용을 바탕으로 최종 답변을 생성하는 노드
def generate(state: MessagesState):
    # state['messages']에서 가장 최근의 tool message를 찾음
    tool_messages = [msg for msg in reversed(state['messages']) if msg.type == 'tool']
    # tool call이 여러개일 수 있으므로, content를 모두 합침
    docs_content = "\n\n".join(str(doc.content) for doc in tool_messages[0].content)
    
    system_message_content = (
        "You are an assistant for question-answering tasks. "
        "Use the following pieces of retrieved context to answer the question. "
        f"\n\n{docs_content}"
    )
    
    # 기존 대화 내용과 합쳐서 프롬프트 구성
    # tool_calls를 포함한 AI 메시지는 제외
    conversation_messages = [
        message
        for message in state["messages"]
        if not (message.type == "ai" and message.tool_calls)
    ]
    prompt = [SystemMessage(content=system_message_content)] + conversation_messages
    
    response = llm.invoke(prompt)
    return {"messages": [response]}

# 4. 그래프 구성
builder = StateGraph(MessagesState)
builder.add_node('query_or_respond', query_or_respond)
builder.add_node('tools', ToolNode([retrieve]))
builder.add_node('generate', generate)

builder.add_edge(START, 'query_or_respond')
builder.add_conditional_edges(
    'query_or_respond',
    tools_condition, # LLM의 tool_calls 유무에 따라 분기
    {END: END, 'tools': 'tools'}
)
builder.add_edge('tools', 'generate')
builder.add_edge('generate', END)

graph = builder.compile()
```

#### 2. 벡터스토어 교체 (클라우드 기반: Pinecone)

로컬에 저장되는 `FAISS` 대신, `Pinecone`과 같은 클라우드 기반 벡터스토어를 사용하면 데이터를 영구적으로 저장하고 여러 환경에서 공유할 수 있습니다. Pinecone은 대규모 벡터 검색을 위해 설계된 관리형 데이터베이스로, 한 번 데이터를 저장해두면 애플리케이션을 재시작하거나 다른 서버에서 실행해도 동일한 데이터에 접근할 수 있어 편리합니다.

-   **영속성 및 확장성**: 데이터를 한 번만 업로드하면 되며, 데이터가 늘어나도 안정적인 성능을 제공합니다.
-   **사용법**:
    -   `PineconeVectorStore.from_documents()`: 문서를 처음 임베딩하여 Pinecone에 저장할 때 사용합니다. 이 작업은 비용이 발생하며, 실행마다 데이터가 추가되므로 주의
    -   `PineconeVectorStore.from_existing_index()`: 이미 생성된 인덱스를 불러와 사용합니다. 실제 애플리케이션에서는 이 함수를 통해 벡터스토어에 연결합니다.

```python
import os
from pinecone import Pinecone
from langchain_pinecone import PineconeVectorStore
from langchain_openai import OpenAIEmbeddings

# Pinecone 초기화
# PINECONE_API_KEY 환경변수 필요
pc = Pinecone()
index_name = 'my-rag-index' # Pinecone에서 생성한 인덱스 이름
embedding = OpenAIEmbeddings(model='text-embedding-3-small')

# ---
# 최초 1회 실행 ---
# 문서를 임베딩하여 Pinecone에 저장
# from_documents는 비용이 발생하며, 실행마다 데이터가 추가되므로 주의
# PineconeVectorStore.from_documents(
#     splitted_docs, 
#     index_name=index_name, 
#     embedding=embedding
# )

# ---
# 이후 사용 ---
# 기존에 저장된 인덱스를 불러와 vectorstore 객체로 사용
vectorstore = PineconeVectorStore.from_existing_index(
    index_name=index_name, 
    embedding=embedding
)

# 이제 이 vectorstore 객체를 retrieve 툴에서 사용하면 됩니다.
```
