# **요약(Summarization)**

문서의 양이 LLM의 Context Window를 넘어갈 때, 문서를 요약하는 방법

## Stuff

-   가장 간단한 방법. 모든 문서를 하나로 묶어 LLM에 전달하여 요약을 요청
-   문서의 양이 적을 때만 사용 가능

```python
from langchain.chains.combine_documents import create_stuff_documents_chain
from langchain_core.prompts import ChatPromptTemplate

prompt = ChatPromptTemplate.from_messages([
    ('system', '아래 내용을 정확하게 요약해 줘. 

{context}')
])

chain = create_stuff_documents_chain(llm, prompt)
res = chain.invoke({'context': docs})
```

## Map-Reduce

-   문서를 여러 개의 작은 조각(Chunk)으로 나누어 처리하는 방식
-   **Map**: 각 조각을 개별적으로 요약
-   **Reduce**: 개별 요약본들을 다시 하나로 합쳐 최종 요약본을 생성

### LangGraph를 이용한 Map-Reduce

LangGraph를 사용하면 Map-Reduce 과정을 체계적인 그래프로 구현할 수 있다.

#### **State 정의**

-   `OverallState`: 전체 요약 프로세스의 상태를 관리. 문서 조각, 중간 요약본, 최종 요약본 등을 포함
-   `SummaryState`: 개별 문서 조각을 요약할 때 사용되는 상태

```python
class OverallState(TypedDict):
    contents: List[str]
    summaries: Annotated[list, operator.add]
    collapsed_summaries: List[Document]
    final_summary: str
    
class SummaryState(TypedDict):
    content: str
```

#### **Node 정의**

-   `generate_summary`: 각 문서 조각을 요약 (Map)
-   `collect_summaries`: 생성된 요약들을 하나로 모음
-   `collapse_summaries`: 요약본들의 크기가 여전히 클 경우, 재귀적으로 다시 요약 (Reduce)
-   `generate_final_summary`: 최종 요약본을 생성

```python
# Map: 각 문서 조각을 요약
async def generate_summary(state: SummaryState):
    prompt = map_prompt.invoke({'context': state['content']})
    res = await llm.ainvoke(prompt)
    return {'summaries': [res.content]}

# Reduce: 요약본들을 다시 요약
async def collapse_summaries(state: OverallState):
    # ... (요약본들을 다시 나누고, 각각을 다시 요약)
    return {'collapsed_summaries': results}

# 최종 요약
async def generate_final_summary(state: OverallState):
    response = await _reduce(state["collapsed_summaries"])
    return {"final_summary": response}
```

#### **Graph 생성 및 실행**

-   정의된 State와 Node들을 바탕으로 Graph를 구성
-   `add_conditional_edges`를 사용하여, 요약본의 크기에 따라 재귀적으로 `collapse_summaries`를 호출할지, 아니면 `generate_final_summary`로 갈지를 결정

```python
builder = StateGraph(OverallState)

builder.add_node("generate_summary", generate_summary)
builder.add_node("collect_summaries", collect_summaries)
builder.add_node("collapse_summaries", collapse_summaries)
builder.add_node("generate_final_summary", generate_final_summary)

# Edge 연결
builder.add_conditional_edges(START, map_summaries, ["generate_summary"])
builder.add_edge("generate_summary", "collect_summaries")
builder.add_conditional_edges("collect_summaries", should_collapse)
builder.add_conditional_edges("collapse_summaries", should_collapse)
builder.add_edge("generate_final_summary", END)

graph = builder.compile()
```

-   `astream()`을 통해 그래프를 실행하면, 각 단계의 결과가 스트리밍 방식으로 반환됨

```python
async for step in graph.astream(
    {'contents': ...},
    {'recursion_limit': 10}
):
    print(step)
```
