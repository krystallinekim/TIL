# LLM과 데이터베이스(SQL) 연결

사용자가 질문을 하면, 질문에 맞는 SQL 쿼리를 생성하고, DB에서 실행해 그 결과를 보고 답변을 생성하는 식

## 데이터베이스 연결

Langchain의 `SQLDatabase`를 사용하여 SQL 데이터베이스에 연결할 수 있다.

연결 URI를 설정하고, SQL 서버가 돌아가고 있다면 연결 가능

```python
from langchain_community.utilities import SQLDatabase

# PostgreSQL 연결 정보
POSTGRES_USER = os.getenv('POSTGRES_USER')
POSTGRES_PASSWORD = os.getenv('POSTGRES_PASSWORD')
POSTGRES_DB = os.getenv('POSTGRES_DB')

URI = f'postgresql://{POSTGRES_USER}:{POSTGRES_PASSWORD}@localhost:5432/{POSTGRES_DB}'
db = SQLDatabase.from_uri(URI)

# 연결된 DB의 종류와 테이블 목록 확인
print(db.dialect)
print(db.get_usable_table_names())
```
DB에 쿼리를 보내면, DB에서 그 쿼리의 결과를 반환해준다.

### Langgraph 파이프라인

1.  `query_prompt_template`과 LLM을 사용하여 `state['question']`으로부터 SQL 쿼리를 생성하고 `{'sql': ...}`을 반환한다. 

    `with_structured_output`을 사용하여 LLM의 출력을 원하는 형태(e.g., `QueryOutput`)로 강제

2.  `QuerySQLDatabaseTool(db=db)`를 사용하여 `state['sql']`을 실행하고 `{'result': ...}`를 반환

3.  `state`에 담긴 질문, SQL, 결과를 모두 컨텍스트로 활용하여 최종 자연어 답변을 생성하고 `{'answer': ...}`를 반환

추가로, 사용자의 질문과 테이블 정보를 이용해 질문에 대한 적당한 답변을 데이터베이스에서 구할 수 있는지 확인하는 노드를 추가할 수도 있다.

이 경우, `.add_conditional_edges()`를 사용해 답변할 수 있는 경우와 없는 경우로 나눠주는것도 좋음

## Human-in-the-Loop (사용자 개입)

데이터베이스에 직접적인 영향을 줄 수 있는 쿼리(e.g., `UPDATE`, `DELETE`) 실행 전에 사용자에게 승인을 받는 안전장치를 추가할 수 있다.

- **`interrupt_before`**: `compile()` 시 이 인자를 설정하면 특정 노드 실행 직전에 그래프가 멈춘다.

- **`checkpointer`**: `MemorySaver`와 같은 체크포인터를 사용하여 중단된 상태를 저장하고, 다시 시작할 때 이어서 실행할 수 있게 할 수 있다.
 
```python
from langgraph.checkpoint.memory import MemorySaver

memory = MemorySaver()

# execute_sql 노드 실행 전에 중단
graph = builder.compile(checkpointer=memory, interrupt_before=['execute_sql'])

# 실행 (interrupt가 발생할 때까지)
config = {'configurable': {'thread_id': 'abc123'}}
graph.stream({'question': '직원 정보 알려줘'}, config=config)

# --- execute_sql 실행 전에 중단 ---

# 사용자에게 승인 여부 확인
user_approval = input("계속 하시겠습니까? (y/n)")

# 중단된 지점부터 이어서 실행됨
if user_approval == 'y':
    graph.stream(None, config=config)

```

## 좀 더 자율적인 에이전트

정해진 파이프라인을 따르는 대신, LLM이 스스로 상황을 판단하여 필요한 도구를 선택하고 실행하는 자율적인 에이전트를 만들 수도 있다.

- **`SQLDatabaseToolkit`**: `sql_db_list_tables`(테이블 목록), `sql_db_schema`(스키마 조회), `sql_db_query`(쿼리 실행) 등 DB 상호작용에 필요한 모든 도구를 묶어서 제공

- **`create_react_agent`**: LLM, 도구(toolkit), 프롬프트를 받아 ReAct (Reason + Act) 패턴의 에이전트를 생성합니다. 이 에이전트는 "어떤 테이블이 있지?" -> `sql_db_list_tables` 실행 -> "이 테이블의 스키마는?" -> `sql_db_schema` 실행 -> "이제 쿼리를 만들어야겠다" -> `sql_db_query` 실행 과 같은 추론 과정을 거칩니다.

```python
from langchain_community.agent_toolkits import SQLDatabaseToolkit
from langgraph.prebuilt import create_react_agent

toolkit = SQLDatabaseToolkit(db=db, llm=llm)
tools = toolkit.get_tools()

agent_executor = create_react_agent(llm, tools, prompt=system_message)

# 실행
agent_executor.stream({'messages': [HumanMessage(content=q)]})
```
자동으로 결과를 낸다