# **코드 실행기(Code Interpreter)**

LangGraph를 사용하여 사용자의 질문에 답변하기 위해 동적으로 코드를 생성하고 실행하는 에이전트를 구축하는 방법

## 기본 구조

1.  **코드 생성**: 사용자의 질문과 제공된 데이터를 기반으로 LLM이 Python 코드를 생성
2.  **코드 실행**: 생성된 코드를 Python REPL(Read-Eval-Print Loop) 환경에서 실행
3.  **결과 종합**: 코드 실행 결과를 바탕으로 LLM이 최종 답변을 생성
4.  **코드 저장**: 생성된 코드를 파일로 저장하여 보관

### State 정의

그래프의 전체 흐름에서 사용될 데이터 구조를 정의. `MessagesState`를 상속받아 대화 기록을 관리할 수 있다.

```python
from langgraph.graph import MessagesState
from typing_extensions import Any

class State(MessagesState):
    question: str  # 사용자 질문
    dataset: Any   # 데이터셋
    code: str      # 생성된 Python 코드
    result: str    # 코드 실행 결과
    answer: str    # 최종 답변
```

### Node 정의

각 단계에서 수행될 작업들을 함수로 정의

-   `generate_code`: 질문과 데이터를 받아 코드를 생성
-   `execute_code`: 생성된 코드를 실행하고 결과를 반환
-   `generate_answer`: 질문, 코드, 실행 결과를 종합하여 자연어 답변을 생성
-   `save_code`: 생성된 코드를 파일로 저장

```python
# 코드 생성 노드
def generate_code(state: State):
    # ... (LLM을 사용하여 코드 생성)
    return {'code': res['code']}

# 코드 실행 노드
def execute_code(state: State):
    repl = PythonREPL()
    result = repl.run(state['code'])
    return {'result': result.strip()}

# 답변 생성 노드
def generate_answer(state: State):
    # ... (LLM을 사용하여 최종 답변 생성)
    return {'answer': res}

# 코드 저장 노드
def save_code(state: State):
    # ... (코드를 파일에 저장)
    return {}
```

### Graph 생성 및 실행

정의된 Node들을 순서대로 연결하여 그래프를 구성

-   `builder.add_sequence()`: 노드들을 순차적으로 실행하도록 간단하게 설정

```python
from langgraph.graph import StateGraph, START, END

builder = StateGraph(State)

# 노드들을 순서대로 실행하도록 설정
builder.add_sequence([generate_code, execute_code, save_code, generate_answer])

# 시작과 끝을 연결
builder.add_edge(START, 'generate_code')
builder.add_edge('generate_answer', END)

graph = builder.compile()
```

-   `graph.stream()`을 통해 그래프를 실행하고, 각 단계의 결과를 실시간으로 확인

```python
config = {'configurable': {'thread_id': '1'}}

for step in graph.stream({
    'question': '...',
    'dataset': { ... }
}, config):
    print(step)
```
