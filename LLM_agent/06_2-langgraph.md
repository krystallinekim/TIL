# 챗봇 구축 심화

## LangGraph를 이용한 대화형 챗봇

LangGraph를 사용하면 대화의 상태(State)를 체계적으로 관리하며 챗봇을 쉽게 만들 수 있습니다. 상태는 그래프의 노드(Node) 사이를 흐르는 데이터의 역할을 합니다. 이를 통해 대화 기록 저장, 특정 페르소나 부여, 컨텍스트 윈도우 관리 등의 기능을 명확하게 구현할 수 있습니다.

### 기본 챗봇 구조와 메모리

`MessagesState`와 `MemorySaver`를 사용하여 대화 기록을 관리하는 챗봇을 만들 수 있습니다. 각 대화는 고유한 `thread_id`를 통해 구분되어, 여러 사용자와의 동시적인 대화나 여러 개의 대화 주제를 독립적으로 관리할 수 있습니다.

- **`MessagesState`**: LangGraph의 내장 상태(State) 중 하나로, `TypedDict` 형태로 `messages` 키를 가지고 있습니다. 노드 간에 대화 메시지 목록이 이 상태를 통해 전달되고 업데이트됩니다.
- **`MemorySaver`**: 대화 내용을 인-메모리(in-memory)에 저장하여, 이전 대화를 기억하게 해주는 역할을 합니다. `checkpointer`에 연결되며, 프로토타이핑에 유용합니다. 실제 프로덕션 환경에서는 Redis나 데이터베이스 기반의 영구적인 Checkpointer 사용이 권장됩니다.

```python
from langgraph.checkpoint.memory import MemorySaver
from langgraph.graph import START, END, MessagesState, StateGraph
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model='gpt-4.1-nano')

# 그래프 빌더 정의. 어떤 상태 스키마를 사용할지 지정합니다.
builder = StateGraph(state_schema=MessagesState)

# LLM을 호출하는 간단한 노드. 상태를 입력받아 처리 후, 변경된 상태를 반환합니다.
def simple_node(state: MessagesState):
    res = llm.invoke(state['messages'])
    return {'messages': res} # 변경된 부분만 반환하면 LangGraph가 상태를 업데이트합니다.

# 노드와 엣지(흐름) 추가
builder.add_node('simple_node', simple_node) # 노드 등록
builder.add_edge(START, 'simple_node')      # 시작점에서 'simple_node'로 이동
builder.add_edge('simple_node', END)        # 'simple_node'가 끝나면 종료

# 메모리 설정
memory = MemorySaver()

# 그래프 컴파일. Checkpointer를 연결하여 대화 기록을 저장할 수 있게 합니다.
graph = builder.compile(checkpointer=memory)

# 실행 (thread_id로 대화 채널을 구분)
# 동일한 thread_id를 사용하면 이전 대화 내용이 이어집니다.
config = {'configurable': {'thread_id': 'user_123'}}
graph.invoke({'messages': [HumanMessage(content='Hi, I am Bob.')]}, config=config)
```

### 프롬프트 템플릿으로 페르소나 부여하기

`ChatPromptTemplate`과 `MessagesPlaceholder`를 사용하면 챗봇에게 시스템 레벨의 지시(예: "해적처럼 말해")를 내려 역할을 부여할 수 있습니다.

- **`MessagesPlaceholder`**: 프롬프트 템플릿 내에서 실제 대화 기록이 동적으로 삽입될 위치를 지정합니다. 이를 통해 시스템 메시지와 대화 기록을 결합하여 LLM에 전달할 수 있습니다.

```python
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

prompt_template = ChatPromptTemplate.from_messages([
    ('system', '너는 해적처럼 말해야 해. 대항해시대의 해적을 최대한 따라해 봐.'),
    MessagesPlaceholder(variable_name='messages'),
])

# 노드에서 프롬프트와 LLM을 파이프라인(|)으로 연결
def simple_node(state: MessagesState):
    # state 딕셔너리가 'messages' 키를 가지고 있으므로,
    # prompt_template.invoke(state) 호출 시 MessagesPlaceholder가 채워집니다.
    chain = prompt_template | llm
    res = chain.invoke(state)
    return {'messages': res}
```

### 대화 기록 관리 (`trim_messages`)

대화가 길어지면 LLM의 최대 컨텍스트 길이를 초과하여 에러가 발생할 수 있습니다. `trim_messages`를 사용하면 토큰 수를 기준으로 메시지를 동적으로 잘라내어 이 문제를 효과적으로 방지할 수 있습니다.

```python
from langchain_core.messages import trim_messages

trimmer = trim_messages(
    strategy='last',        # 'last': 최근 메시지를 남김. 'first': 오래된 메시지를 남김.
    max_tokens=200,         # 허용할 최대 토큰 수
    token_counter=llm,      # 사용하는 모델에 맞춰 토큰 수 계산
    include_system=True,    # 시스템 메시지는 항상 포함
    allow_partial=False,    # 메시지 중간이 잘리지 않도록 함
    start_on='human',       # 잘린 메시지 목록이 항상 사용자 메시지로 시작하도록 보장
)

# 노드 안에서 LLM 호출 전에 메시지를 자름
def simple_node(state: Mystate):
    # LLM에 전달하기 전에 대화 기록을 적절한 길이로 자릅니다.
    trimmed_messages = trimmer.invoke(state['messages'])
    
    # 잘린 메시지를 포함하여 새로운 state를 구성합니다.
    new_state = state.copy()
    new_state['messages'] = trimmed_messages
    
    chain = prompt_template | llm
    res = chain.invoke(new_state)
    
    # LLM의 응답은 전체 대화 기록에 추가되어야 하므로, 원래 state에서 업데이트합니다.
    return {'messages': res}
```

## LangGraph와 RAG(Retrieval-Augmented Generation) 결합

RAG는 외부 문서나 데이터를 참조하여 LLM이 더 정확하고 풍부한 답변을 생성하도록 하는 기술입니다. LangGraph를 사용하면 RAG의 각 단계(검색, 생성 등)를 독립적인 노드로 정의하여 전체 파이프라인을 명확하고 직관적으로 구축할 수 있습니다.

### RAG 파이프라인

RAG는 보통 **Load -> Split -> Embed -> Store**의 데이터 준비 과정을 거칩니다. 이 과정을 통해 질문과 관련된 문서를 효율적으로 찾아낼 수 있는 벡터 데이터베이스를 구축합니다.

1.  **Loader**: 웹 페이지, PDF, 텍스트 파일 등 다양한 소스에서 원본 문서를 불러옵니다.
2.  **Splitter**: 불러온 문서를 LLM의 컨텍스트에 들어갈 수 있는 적절한 크기의 조각(chunk)으로 나눕니다. 의미 있는 단위로 나누는 것이 중요합니다.
3.  **Embedding**: 각 조각을 고차원 벡터 공간의 한 점으로 매핑(임베딩)합니다. 텍스트의 의미를 벡터로 변환하는 과정입니다.
4.  **Vectorstore**: 변환된 벡터를 저장하고, 특정 벡터와 유사한 벡터를 빠르게 검색(Similarity Search)할 수 있는 데이터베이스를 구축합니다.

```python
# 1. Loader (웹 페이지)
from langchain_community.document_loaders import WebBaseLoader
loader = WebBaseLoader(web_paths=('https://lilianweng.github.io/posts/2023-06-23-agent/',))
docs = loader.load()

# 2. Splitter
from langchain_text_splitters import RecursiveCharacterTextSplitter
splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200) # overlap으로 chunk 간의 문맥 유지
splitted_docs = splitter.split_documents(docs)

# 3. Embedding Model
from langchain_openai import OpenAIEmbeddings
embedding = OpenAIEmbeddings(model='text-embedding-3-small')

# 4. Vectorstore (FAISS - In-memory vectorstore)
from langchain_community.vectorstores import FAISS
vectorstore = FAISS.from_documents(splitted_docs, embedding=embedding)
```

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
