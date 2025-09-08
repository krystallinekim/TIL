# LangGraph

## 기본 개념

LangGraph는 상태(State)를 기반으로 LLM 워크플로우를 구축하기 위한 프레임워크입니다. 순환(cycle)을 포함하는 복잡한 로직을 에이전트처럼 구현할 수 있게 해줍니다. 핵심 아이디어는 그래프(Graph)를 사용하여 챗봇이나 에이전트의 흐름을 정의하는 것입니다.

- **State**: 그래프의 전체 상태를 나타내는 데이터 구조입니다. 각 노드(Node)가 데이터를 공유하고 업데이트하는 중앙 허브 역할을 합니다.
    - 상태 정의 시 `TypedDict`를 사용하면 가볍게 타입 힌트만 제공할 수 있고, `Pydantic` 모델을 사용하면 데이터 유효성 검증(validation)까지 포함하는 더 엄격한 상태 관리가 가능합니다.
- **Node**: 그래프의 각 단계를 수행하는 함수 또는 실행 가능한 객체입니다. 각 노드는 현재 `State`를 입력으로 받아 비즈니스 로직을 수행하고, 수정된 `State`의 일부 또는 전체를 반환합니다.
- **Edge**: 노드 간의 제어 흐름을 정의하며, 다음에 실행될 노드를 지정합니다.
    - **`set_entry_point()`**: 그래프의 시작 노드를 지정합니다.
    - **`add_edge()`**: 한 노드에서 다른 노드로의 **무조건적인** 연결을 추가합니다.
    - **`add_conditional_edges()`**: 특정 노드의 실행 결과(State)를 바탕으로, **조건에 따라** 다음에 실행될 노드를 동적으로 결정합니다. 이것이 LangGraph로 복잡한 분기 로직을 구현하는 핵심 기능입니다.
    - **`set_finish_point()`**: 그래프의 종료 지점을 지정합니다.

그래프를 정의한 후에는 `compile()` 메서드를 호출하여 실행 가능한 객체로 만들고, `invoke()`를 통해 실행합니다.

```python
# 1. 상태 정의 (TypedDict 사용)
class State(TypedDict):
    graph_state: str
    history: list

# 2. 노드 함수 정의
def node_1(state: State):
    # ... 상태 수정 로직 ...
    return {"graph_state": new_str, "history": state["history"]}

# 3. 조건부 엣지에 사용될 분기 함수
def decide_mood(state: State) -> Literal["node_2", "node_3"]:
    if len(state["graph_state"]) % 2:
        return "node_2"
    else:
        return "node_3"

# 4. 그래프 빌드
builder = StateGraph(State)
builder.add_node("node_1", node_1)
builder.add_node("node_2", node_2)
builder.add_node("node_3", node_3)

builder.set_entry_point("node_1")
builder.add_conditional_edges("node_1", decide_mood) # node_1 다음에 decide_mood 결과에 따라 분기
builder.add_edge("node_2", END)
builder.add_edge("node_3", END)

graph = builder.compile()
graph.invoke({"graph_state": "안녕", "history": []})
```

## 체인

LangGraph에서 대화 기록을 `State`로 사용하여 간단한 챗봇 체인을 만들 수 있습니다.

- **`MessagesState`**: 대화 기록을 관리하기 위한 `State`입니다. `Annotated`와 `add_messages` 헬퍼 함수를 함께 사용하면, 매번 `state['messages'] + new_messages`와 같은 번거로운 작업을 하지 않아도 새로운 메시지가 상태에 자동으로 누적되도록 할 수 있어 편리합니다.

```python
from typing import Annotated
from langgraph.graph.message import add_messages
from langchain_core.messages import AnyMessage

class MessagesState(TypedDict):
    # messages 키에 담기는 리스트에 add_messages가 적용되어 메시지가 자동으로 추가됨
    messages: Annotated[list[AnyMessage], add_messages]

# LLM을 호출하는 노드
def llm_node(state: MessagesState):
    response = llm_with_tools.invoke(state["messages"])
    return {"messages": response}

# 그래프 빌드
builder = StateGraph(MessagesState)
builder.add_node("llm_node", llm_node)
builder.set_entry_point("llm_node")
builder.add_edge("llm_node", END)
graph = builder.compile()
```
이 구조는 사용자 입력을 받아 LLM이 답변하고, 이 대화 과정이 `messages` 상태에 자동으로 누적되는 간단한 체인을 형성합니다.

## 라우터

LLM의 응답 내용에 따라 다음 작업을 동적으로 분기하는 **라우터**를 구현할 수 있습니다. 이는 LLM이 스스로 판단하여 흐름을 제어하는 에이전트의 핵심적인 부분입니다.

- **`llm.bind_tools()`**: LLM에 사용할 도구를 바인딩하여, LLM이 함수 호출(Tool Call) 형식으로 응답을 생성할 수 있게 합니다.
- **`ToolNode`**: LangGraph에서 제공하는 미리 정의된 노드로, LLM이 요청한 도구를 실제로 실행하고 그 결과를 반환하는 역할을 합니다.
- **`tools_condition`**: 미리 정의된 분기 함수로, LLM의 마지막 응답에 `tool_calls`가 포함되어 있는지 여부를 확인합니다.
    - `tool_calls`가 있으면: `ToolNode`로 라우팅하여 도구를 실행합니다.
    - `tool_calls`가 없으면: `END`로 라우팅하여 그래프를 종료하고 사용자에게 최종 답변을 반환합니다.

```python
from langgraph.prebuilt import ToolNode, tools_condition

# 그래프 빌드
builder = StateGraph(MessagesState)
builder.add_node("llm_node", llm_node)
builder.add_node("tools", ToolNode([multiply])) # 도구 실행 노드

builder.set_entry_point("llm_node")

# llm_node의 결과(tool_calls 유무)에 따라 'tools' 노드로 갈지, 끝낼지 분기
builder.add_conditional_edges(
    "llm_node",
    tools_condition,
)
builder.add_edge("tools", END) # 도구 실행 후 종료
graph = builder.compile()
```

## 에이전트

라우터 개념을 확장하여 **ReAct(Reason -> Act -> Observe)** 패턴을 구현하는 완전한 에이전트를 만들 수 있습니다. 에이전트는 여러 도구를 사용하고, 대화 내용을 기억하여 맥락에 맞는 답변을 생성합니다.

- **ReAct 루프(Loop) 구현**:
    1.  **Reason (판단)**: `assistant` 노드에서 LLM이 현재 상태와 사용자 요청을 보고 어떤 행동을 할지 결정합니다. (도구 사용 or 답변)
    2.  **Act (행동)**: `tools_condition`에 의해 `tools` 노드로 라우팅되면, `ToolNode`가 LLM이 요청한 도구를 실행합니다.
    3.  **Observe (관찰)**: `tools` 노드의 실행 결과를 다시 `assistant` 노드로 전달하는 엣지(`builder.add_edge('tools', 'assistant')`)를 추가하여 루프를 만듭니다. LLM은 도구 실행 결과를 보고 다음 판단(Reason)을 이어갑니다.

- **메모리 추가**: `MemorySaver`를 `checkpointer`로 설정하여 그래프를 컴파일하면 대화 기록을 외부(메모리, DB 등)에 저장하고 불러올 수 있습니다. 이를 통해 에이전트는 이전 대화의 맥락을 기억하는 장기 기억을 갖게 됩니다.
- **스레드 ID**: `invoke` 시 `config={"configurable": {"thread_id": "user_123"}}`를 전달하여 각 사용자 또는 세션별로 대화 기록을 분리하여 관리할 수 있습니다.

### 에이전트의 오류 처리 및 자가 수정
LangGraph 에이전트는 도구 사용 시 발생하는 오류를 스스로 인지하고 대처할 수 있습니다. 예를 들어, 도구가 정수(`int`)를 인자로 기대하는데 소수(`float`)가 입력되면 `ToolNode`에서 유효성 검사 오류가 발생합니다. 이 오류 메시지는 루프를 통해 다시 LLM(`assistant` 노드)에게 전달됩니다. LLM은 이 오류를 **관찰(Observe)**하고, 사용자에게 문제를 설명하며 어떻게 처리할지 되묻는 방식으로 상황을 해결해 나갑니다. 이는 에이전트가 더 똑똑하고 안정적으로 작동하게 만드는 중요한 기능입니다.

```python
# 그래프 빌드
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

# 스레드 ID를 사용하여 대화 실행
config = {"configurable": {"thread_id": "1"}}
graph.invoke({"messages": ["3에 4를 더해."]}, config=config)
# 이전 대화(결과: 7)를 기억하고 다음 계산을 수행
graph.invoke({"messages": ["거기다 2를 곱해."]}, config=config)
```
