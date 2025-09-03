# RAG

Retrieval Augmented Generation, 검색 증강 생성

LLM에게 외부 데이터를 context로 제공해 답변의 정확성, 신뢰도를 높이는 기술

## 웹 검색

LLM이 최신 정보나 특정 도메인에 대한 정보를 알지 못하는 한계를 보완하기 위해 웹 검색 기능을 연동할 수 있다.

### Tavily

LLM이 사용하기에 최적화된 웹 검색 도구. 질문에 맞는 웹 검색 결과 n개를 검색해서 보여준다.

사람이 보기 쉽게 검색 결과를 시각화해서 보여주는 것이 아니라, LLM이 보기 쉽게 url, title, content를 반환함

또, 얼마나 질문과 연관된 답변인지 알려주는 `score`도 반환한다. 

```py
from langchain_tavily import TavilySearch

search_tool = TavilySearch(
    max_results=5,
    topic="general",  # general/news/finance
    search_depth="basic",  # basic/advance
    # time_range="day",  # 데이터 최신 기준, None/day/week/month/year
    # include_domains=None,
    # exclude_domains=None  # 특정 도메인 포함/제외
)

search_tool.invoke({'query': '서울고속버스터미널 근처 맛집'})
```
다양한 hyperparameter를 지원한다. 

### 검색 결과를 활용한 Chain

`TavilySearch()` 또한 runnable이기 때문에, Chain에 넣을 수 있다.

즉, 질문에 대한 검색 결과를 바탕으로 LLM이 답변을 생성하게 할 수 있다.

```py
from langchain_core.runnables import RunnableLambda, RunnablePassthrough

chain = (
    {
        'query': RunnablePassthrough(),
        'search_results': search_tool | RunnableLambda(
            lambda x: '\\n'.join([f'-{r['title']}: {r['content']}' for r in x['results']])
        )
    }
    | prompt 
    | llm 
    | StrOutputParser()
)

chain.invoke('제로페이 가능한 고속터미널 맛집 알려줘')
```
다만, 이 기능은 agent에 통합되어 굳이 체인에 넣을 필요 없이, agent의 tools에 `TavilySearch()`를 넣는 것으로 구현 가능함

## RAG

### 사전 처리

RAG를 사용하기 위해서는 문서를 벡터 형태로 변환하여 데이터베이스에 저장해야 함

1.  **문서 불러오기(Loading)**: PDF, CSV, 웹페이지 등 다양한 소스로부터 문서를 불러온다.
    ```py
    from langchain_community.document_loaders import PyMuPDFLoader

    loader = PyMuPDFLoader('../data/spri.pdf')
    docs = loader.load()
    ```
    `PyMuPDFLoader()`, `CSVLoader()`, `WebBaseLoader()` 등등, 불러올 문서의 소스에 따라 사용할 모듈이 달라짐

2.  **텍스트 분할(Splitting)**: 불러온 문서를 LLM이 처리하기 좋은 크기의 청크(Chunk)로 나눈다.
    ```py
    from langchain_text_splitters import RecursiveCharacterTextSplitter

    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=500,  # 글자 수
        chunk_overlap=50  # 두 청크 사이에 겹치는 글자 수를 50개
    )

    docs_splitted = text_splitter.split_documents(docs)
    ```
    `RecursiveCharacterTextSplitter()`는 일반적인 텍스트에 권장되는 방식으로, 청크가 `chunk_size`에 도달할 때까지 문자 목록을 자른다.

    `chunk_overlap`은 글의 연속성을 유지하기 위해, 앞뒤로 50개 문자를 일부러 중첩한다.

    이 외에도 다양한 splitting 모듈이 있음

3.  **임베딩 (Embedding)**: 각 텍스트 청크를 수치 벡터로 변환한다.

    수치 벡터는 청크 내용을 컴퓨터가 계산할 수 있게 숫자로 변환한 것.

    청크 내용 간의 거리를 측정해 문서 간의 유사성을 검색할 수 있게 된다.
    ```py
    from langchain_openai import OpenAIEmbeddings

    embedding = OpenAIEmbeddings()
    ```
    보통 밑의 벡터 저장과 함께 진행된다.

4.  **벡터 저장 (VectorStore)**: 변환된 벡터를 검색이 용이하도록 벡터 데이터베이스에 저장한다.
    
    ```py
    from langchain_community.vectorstores import FAISS

    vectorstore = FAISS.from_documents(documents=docs_splitted, embedding=embedding)
    ```
    `Chroma`, `FAISS` 등 다양한 벡터 데이터베이스 형태가 있다.

사전 처리 단계에서는 LLM이 관여하지 않는다. 기계적인 작업임

### 검색 및 생성

사용자 질문이 들어왔을 때, 관련성 높은 문서를 검색하고 이를 LLM에 전달하여 답변을 생성하는 과정

1.  **사용자 질문 (Query)**: 사용자가 질문을 입력함

2.  **검색 (Retrieve)**: VectorStore를 `retriever`로 변환하고, 사용자 질문과 유사도가 높은 문서 청크를 검색
    이 때, 사용자 질문을 그대로 retriever에 전달해 줘야 함 -> `RunnablePassthrough()` 사용

3.  **LLM 전달**: 검색된 문서(Context)와 사용자 질문(Query)을 프롬프트에 함께 넣어 LLM에게 전달

4.  **최종 답변**: LLM이 최종 답변을 반환

사실상 이 과정은 모두 한 체인 내에 존재해, 한번에 순서대로 작동함

```py
from langchain_core.runnables import RunnablePassthrough

# Retriever 생성
retriever = vectorstore.as_retriever()

# 맞춤 프롬프트 생성
prompt = hub.pull('rlm/rag-prompt')

# LLM 설정
llm = ChatOpenAI(model='gpt-4.1-nano')

# RAG Chain
chain = (
    {'context': retriever, 'question': RunnablePassthrough()}
    | prompt
    | llm
    | StrOutputParser()
)

chain.invoke('삼성전자 관련 소식을 요약해줘')
```