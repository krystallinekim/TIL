# Agent

LLM이 스스로 생각하고, 주어진 **도구(Tools)**를 활용하여 사용자의 복잡한 요청을 해결하도록 만드는 기능이다. 

Agent는 사용자의 요청을 이해하고, 목표 달성을 위한 계획을 세우며, 필요한 도구를 사용하여 중간 단계를 실행하고, 최종적으로 답변을 생성하는 과정을 자율적으로 수행함

## 핵심 구성 요소

1.  **LLM**: Agent의 두뇌 역할을 하는 언어 모델.

2.  **Prompt (프롬프트)**: Agent의 역할, 성격, 목표, 도구 사용법 등을 정의하는 지시문. 
    
    `MessagesPlaceholder`를 사용해 대화 기록(`chat_history`)과 Agent의 중간 생각 과정(`agent_scratchpad`)을 동적으로 삽입할 수 있다.

3.  **Tools (도구)**: Agent가 목표를 달성하기 위해 사용할 수 있는 함수나 서비스

    웹 검색, 데이터베이스 조회, 계산기, 문서 검색 등 다양한 기능을 도구로 제공할 수 있다.

4.  **Executor**: Agent와 도구를 연결하고, Agent의 실행 계획을 실제로 수행하는 실행기
    대화 기록(Memory)을 관리하고, 실행 과정을 로깅하는 기능도 포함한다.

## Tools

### 웹 검색

LLM은 최신 정보나 특정 도메인에 대한 지식이 부족할 수 있고, 이러한 한계를 보완하기 위해 Agent에 웹 검색 도구를 결합할 수 있다.

`TavilySearch`와 같은 도구를 Agent에 제공하면, Agent는 필요하다고 판단될 때 스스로 웹을 검색하여 최신 정보를 바탕으로 답변을 생성한다.

```python
from langchain_tavily import TavilySearch

tools = [TavilySearch()] 
```

### RAG

Agent에 RAG(검색 증강 생성) 기술을 접목하여, 특정 문서나 데이터베이스의 내용을 기반으로 답변하게 할 수 있다.

`create_retriever_tool`을 사용하여 VectorStore에서 문서를 검색하는 도구를 만들고, 이를 Agent에 제공

이렇게 하면 Agent는 사용자의 질문이 특정 문서와 관련이 있다고 판단될 때, 해당 문서를 검색(`retriever` 도구 사용)하고 그 내용을 바탕으로 답변을 생성한다.

```python
from langchain.tools.retriever import create_retriever_tool

retriever = vectorstore.as_retriever()

# retriever를 Agent가 사용할 수 있는 도구로 변환
rag_tool = create_retriever_tool(
    retriever,
    name="pdf_search",
    description="PDF 문서에서 질문과 관련된 내용을 검색합니다." # Agent가 언제 이 도구를 쓸지 판단하는 기준
)

tools = [rag_tool]
```

## 출력 파서

Agent의 최종 출력을 일정한 형식(예: JSON, Pydantic 객체)으로 구조화해야 할 때가 있을 수 있음.

LLM에 `.with_structured_output()` 메서드를 사용하거나, 체인의 마지막에 출력 파서를 연결하여 Agent의 응답을 원하는 형태로 제어할 수 있다.

`pydantic`모듈을 사용해 결과를 항목별로 사용하면서, 각 항목 별로 설명도 작성 가능함

```python
from pydantic import BaseModel, Field
from langchain_core.runnables import RunnableLambda

# 1. 원하는 출력 형태를 Pydantic 모델로 정의
class Classification(BaseModel):
    sentiment: str = Field(description='작성된 글의 감정')
    aggressiveness: int = Field(description='얼마나 공격적인지를 1~10점으로 판단')
    remarks: str = Field(description='특이사항 요약')

# 2. LLM에 구조화된 출력을 요청하도록 설정
structured_llm = llm.with_structured_output(Classification)
```

## 실제 Agent 구현 예시

`./실습/agent.py` 파일은 RAG(문서 검색), 웹 검색, 대화 기록(Memory) 기능을 모두 통합한 학습 보조 챗봇 에이전트의 구체적인 구현 사례임.

### 주요 구성 요소

1.  **LLM**
    ```py
    llm = ChatOpenAI(
        model='gpt-4.1-nano', 
        temperature=0,
        streaming=True,
        callbacks=[StreamingStdOutCallbackHandler()]
    )
    ```
    agent를 이용하면 답변이 한번에 생성되어 streaming이 불가하기 때문에, `StreamingStdOutCallbackHandler`를 이용해 답변을 토큰별로 생성

2.  **도구 (Tools)**:

    - **RAG 도구 (`rag_tool`)**: `create_retriever_tool`을 사용하여 로컬 경로(TIL 안의 모든 md 파일)에 있는 학습 자료들을 기반으로 답변을 검색

        - 사용자가 학습한 내용과 관련된 질문을 할 때 우선적으로 사용된다.

        - 학습 자료들은 `MarkdownTextSplitter`과 `Chroma`를 이용해 벡터스토어에 저장하고, 벡터스토어를 디스크에 저장해 로딩 시간을 줄임.

    - **웹 검색 도구 (`web_search`)**: `TavilySearch`를 사용하여 최신 정보나 학습 자료에 없는 내용을 검색

3.  **프롬프트 (Prompt)**:

    - `system_message`를 통해 에이전트의 정체성(학습 조교 어시스턴트)과 행동 규칙을 명확하게 정의
  
    - **도구 사용 논리**:
        1.  학습 관련 질문 시 `rag_tool`를 먼저 사용
        2.  `rag_tool` 결과의 관련성이 낮으면 `web_search`를 사용
        3.  학습과 관련 없는 질문은 바로 `web_search`를 사용
        4.  웹 검색 시 출처를 명시하도록 지시
    
    - `MessagesPlaceholder`를 사용하여 `chat_history`(대화 기록)와 `agent_scratchpad`(에이전트의 생각 과정)를 동적으로 관리

4.  **메모리 (Memory)**:
    - `ConversationBufferMemory`를 사용하여 사용자와의 대화 내용을 기억

        `chat_history`에 대화 내용을 저장하고, 이를 프롬프트에 그대로 넣는다.

        이를 통해 에이전트는 이전 대화의 맥락을 이해하고 답변을 생성

5.  **실행기**:
    
    - `create_openai_tools_agent`로 위에서 정의한 LLM, 도구, 프롬프트를 결합하여 에이전트를 생성
    
    - `AgentExecutor`는 생성된 에이전트를 실제로 실행하고, 메모리를 연결하여 대화가 유지되도록 한다.
