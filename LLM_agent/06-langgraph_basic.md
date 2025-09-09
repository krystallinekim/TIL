# LangGraph

- [Langchain Academy](https://langchain-ai.github.io/langgraph/concepts/why-langgraph/)
- [Official Documentation](https://python.langchain.com/docs/how_to/)

## 기본 개념

상태(State)를 기반으로 LLM 워크플로우를 구축하기 위한 프레임워크

순환, 조건을 포함하는 복잡한 로직을 에이전트처럼 구현할 수 있게 해준다.

그래프 구조(Node / Edge)를 사용하여 챗봇이나 에이전트의 흐름을 정의

### **State**

그래프의 전체 상태를 나타내는 데이터 구조. 각 노드(Node)가 데이터를 공유하고 업데이트하는 중앙 허브 역할을 한다.
    - 상태 정의 시 `TypedDict`를 사용하면 가볍게 타입 힌트만 제공할 수 있고, `Pydantic` 모델을 사용하면 데이터 유효성 검증(validation)까지 포함하는 더 엄격한 상태 관리가 가능하다.

```py
class State(TypedDict):
    graph_state: str
    history: list
```

### **Node**

그래프의 각 단계를 수행하는 함수 또는 실행 가능한 객체. 각 노드는 현재 `State`를 입력으로 받아 비즈니스 로직을 수행하고, 수정된 `State`의 일부 또는 전체를 반환한다.

```py
# 일반 노드
def node_1(state: State):
    new_str = state['graph_state'] + ' I am '
    state['history'].append('node1')
    return {'graph_state': new_str, 'history': state['history']}

# 조건부 분기 노드
def decide_mood(state: State) -> Literal["node_2", "node_3"]:
    if len(state["graph_state"]) % 2:
        return "node_2"
    else:
        return "node_3"
```

### **Edge**

노드 간의 제어 흐름을 정의하며, 다음에 실행될 노드를 지정함

- **`START`** / **`.set_entry_point()`**: 그래프의 시작 노드를 지정합니다.
- **`add_edge()`**: 한 노드에서 다른 노드로의 **무조건적인** 연결을 추가합니다.
- **`add_conditional_edges()`**: 특정 노드의 실행 결과(State)를 바탕으로, **조건에 따라** 다음에 실행될 노드를 동적으로 결정
- **`END`** / **`.set_finish_point()`**: 그래프의 종료 지점을 지정합니다.

```py
from langgraph.graph import StateGraph, START, END

builder = StateGraph(State)

# Node 생성
builder.add_node("node_1", node_1)
builder.add_node("node_2", node_2)
builder.add_node("node_3", node_3)

# Edge 생성 = 그래프 그리기
builder.add_edge(START, "node_1")
builder.add_conditional_edges("node_1", decide_mood)  # 분기 노드
builder.add_edge("node_2", END)
builder.add_edge("node_3", END)
```

### 그래프 정의 및 실행

그래프를 정의한 후에는 `compile()` 메서드를 호출하여 실행 가능한 객체로 만들고, `invoke()`를 통해 실행
```py
graph = builder.compile()
graph.invoke({"graph_state": "안녕", "history": []})
```

그래프의 구조도 확인할 수 있다.
```py
from IPython.display import Image, display

display(Image(graph.get_graph().draw_mermaid_png()))
```


## 대화 기록 누적

LangGraph를 사용하면 대화의 상태(State)를 체계적으로 관리하며 챗봇을 쉽게 만들 수 있다.

State는 그래프의 노드(Node) 사이를 흐르는 데이터의 역할을 하며, 이를 통해 원하는 기능을 명확하게 구현할 수 있다.

### 메모리

`MessagesState`와 `MemorySaver`를 사용하여 대화 기록을 관리하는 챗봇을 만들 수 있다. 

각 대화는 고유한 `thread_id`를 통해 구분되어, 여러 사용자와의 동시적인 대화나 여러 개의 대화 주제를 독립적으로 관리할 수 있다.

- **`MessagesState`**: LangGraph의 내장 상태(State) 중 하나로, `TypedDict` 형태로 `messages` 키를 가지고 있다.
  - 노드 간에 대화 메시지 목록이 이 상태를 통해 전달되고 업데이트됨.

- **`MemorySaver`**: 대화 내용을 인-메모리(in-memory)에 저장하여, 이전 대화를 기억하게 해주는 역할

```py
from langgraph.checkpoint.memory import MemorySaver

memory = MemorySaver()

graph = builder.compile(checkpointer=memory)
```
메모리를 설정 후, 그래프 컴파일 시 `Checkpointer`를 연결하여 대화 기록을 저장할 수 있게 한다.

```py
config = {'configurable': {'thread_id': 'user_123'}}
graph.invoke({'messages': [HumanMessage(content='Hi, I am Bob.')]}, config=config)
```
실행 시에는 `thread_id`로 대화 채널을 구분할 수 있다. 동일한 thread_id를 사용하면 이전 대화 내용이 이어지는 식.

보통 `thread_id`에는 UUID같은걸 써서 채팅방마다 랜덤한 16진수 문자를 설정함

### 프롬프트 템플릿

프롬프트 템플릿을 사용해 챗봇에게 시스템 레벨의 지시를 내려 역할(페르소나)을 부여할 수도 있고, 대화 기록 전체를 프롬프트에 집어넣을 수도 있다.

```py
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

prompt_template = ChatPromptTemplate.from_messages([
    ('system', '너는 유능한 어시스턴트야. 너의 능력을 최대한 활용해서 답을 해봐'),
    MessagesPlaceholder(variable_name='messages'),
])
```
이 때 `MessagesPlaceholder`에는 실제 대화 기록이 동적으로 삽입되게 된다.

실제 노드를 돌리면, 
```py
def simple_node(state: MessagesState):
    chain = prompt_template | llm
    res = chain.invoke(state)
    return {'messages': res}
```
state 딕셔너리에 'messages' 키를 가지고 있으므로, `prompt_template.invoke(state)` 호출 시 `MessagesPlaceholder`에 대화 내용이 채워지게 된다.

### 대화 기록 관리 

대화가 길어지면 LLM의 컨텍스트 윈도우를 초과하여 에러가 발생할 수 있다.

이럴 때 기존 대화 기록을 적당히 관리해서 채팅을 유지할 수 있다.

과거 기록을 잘라서 LLM에게 제공하거나(`trim_messages`), 과거 기록을 요약, 정리해서 LLM에게 제공하는 방식 등등이 존재

#### **`trim_messages`**

```py
from langchain_core.messages import trim_messages

trimmer = trim_messages(
    strategy='last',        # 'last': 최근 메시지를 남김. 'first': 오래된 메시지를 남김.
    max_tokens=200,         # 허용할 최대 토큰 수
    token_counter=llm,      # 사용하는 모델에 맞춰 토큰 수 계산
    include_system=True,    # 시스템 메시지는 항상 포함
    allow_partial=False,    # 메시지 중간이 잘리지 않도록 함
    start_on='human',       # 잘린 메시지 목록이 항상 사용자 메시지로 시작하도록 보장
)
```
`trim_messages`는 전체 대화 기록을 적당한 길이로 잘라, 필요한 부분만 LLM의 프롬프트에 집어넣는 방식

실제 전체 대화 기록은 `state`에 계속 유지되지만, LLM은 일부분만 읽게 됨

```py
def simple_node(state: Mystate):
    # LLM에 전달하기 전에 대화 기록을 적절한 길이로 자른다
    trimmed_messages = trimmer.invoke(state['messages'])
    
    # 잘린 메시지를 포함하여 새로운 state를 구성
    new_state = state.copy()
    new_state['messages'] = trimmed_messages
    
    # 새로운 state를 바탕으로 답변을 생성
    chain = prompt_template | llm
    res = chain.invoke(new_state)
    
    # LLM의 응답은 전체 대화 기록에 추가되어야 하므로, 원래 state에서 업데이트
    return {'messages': res}
```

## 라우터

LLM의 응답 내용에 따라 다음 작업을 동적으로 분기하는 개념. 

- **`.bind_tools()`**: LLM에 사용할 도구를 바인딩하여, LLM이 함수 호출(Tool Call) 형식으로 응답을 생성할 수 있게 함

```py
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model='gpt-4.1')
llm_with_tools = llm.bind_tools([tool])
```

- **`ToolNode`**: LangGraph에서 제공하는 미리 정의된 노드로, LLM이 요청한 도구를 실제로 실행하고 그 결과를 반환하는 역할을 한다.

- **`tools_condition`**: 미리 정의된 분기 함수로, LLM의 마지막 응답에 `tool_calls`가 포함되어 있는지 여부를 확인
    - `tool_calls`가 있으면: `ToolNode`로 라우팅하여 도구를 실행
    - `tool_calls`가 없으면: `END`로 라우팅하여 그래프를 종료하고 사용자에게 최종 답변을 반환

```python
from langgraph.prebuilt import ToolNode, tools_condition

# 그래프 빌드
builder = StateGraph(MessagesState)
builder.add_node("llm_node", llm_node)
builder.add_node("tools", ToolNode([tool])) # 도구 실행 노드

builder.set_entry_point("llm_node")

# llm_node의 결과(tool_calls 유무)에 따라 'tools' 노드로 갈지, 끝낼지 분기
builder.add_conditional_edges(
    "llm_node",
    tools_condition,
    {END: END, 'tools': 'tools'}
)

# 도구 실행 후 종료
builder.add_edge("tools", END) 
graph = builder.compile()
```

### ReAct

라우터 개념의 확장형으로, **(Reason -> Act -> Observe)** 패턴을 의미한다.

에이전트가 여러 도구를 선택적으로 사용할 수 있게 함

- **ReAct 루프(Loop) 구현**:

  1.  **Reason (판단)**: `assistant` 노드에서 LLM이 현재 상태와 사용자 요청을 보고 어떤 행동을 할지 결정(도구 사용 or 답변)
  2.  **Act (행동)**: `tools_condition`에 의해 `tools` 노드로 라우팅되면, `ToolNode`가 LLM이 요청한 도구를 실행
  3.  **Observe (관찰)**: `tools` 노드의 실행 결과를 다시 `assistant` 노드로 전달하는 엣지(`builder.add_edge('tools', 'assistant')`)를 추가하여 루프를 만든다. LLM은 도구 실행 결과를 보고 다음 판단(Reason)을 이어감

### Observe

Observe 단계를 통해, 에이전트는 도구 사용 시 발생하는 오류를 스스로 인지하고 대처할 수 있게 된다.

1. 도구가 정수(`int`)를 인자로 기대하는데 소수(`float`)가 입력되면 `ToolNode`에서 유효성 검사 오류가 발생함.

2. 이 오류 메시지는 루프를 통해 다시 LLM(`assistant` 노드)에게 전달된다.

3. LLM은 이 오류를 **관찰(Observe)**하고, 사용자에게 문제를 설명하며 처리 방법을 묻거나, 사전 정의된 규칙에 따라 자동으로 문제를 해결해 나간다.

```python
builder = StateGraph(MessagesState)
builder.add_node("assistant", assistant_node) # Reason
builder.add_node("tools", ToolNode(tools))    # Act

builder.set_entry_point("assistant")
builder.add_conditional_edges(
    "assistant",
    tools_condition,
)
# 도구 실행 결과를 다시 assistant에게 전달 (Observe)
builder.add_edge("tools", "assistant") # Loop

# 메모리 추가하여 컴파일
memory = MemorySaver()
graph = builder.compile(checkpointer=memory)
```
따로 설정하지 않은 경우, 오류가 생기면 사용자에게 문제가 발생함을 알리고, 왜 문제가 생겼는지도 알려준다.