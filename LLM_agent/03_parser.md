# Output Parsers

LLM(거대 언어 모델)의 출력을 사용하기 쉬운 구조화된 형태로 변환하는 역할


1.  **구조화**: LLM의 자유로운 텍스트 출력을 원하는 형식으로 변환하여 후속 처리 및 개발을 용이하게 함
2.  **일관성**: 항상 일관된 출력 형식을 보장
3.  **유연성**: Json, list, dict 등 다양한 형식의 파서를 기본으로 제공합니다.

## StrOutputParser

가장 기본적인 파서

결과물 중 content만을 str 형식으로 반환해준다.

```py
from langchain_core.output_parsers import StrOutputParser

chain = prompt | llm | StrOutputParser()
res = chain.invoke({<내용>})
```
따로 `.content`를 쓰지 않아도 보기 편하게 출력됨

## PydanticOutputParser

**Pydantic** 모델을 사용하여 LLM의 출력을 Python 객체로 파싱함.

### Pydantic

파이썬에서, 데이터 검증을 위한 라이브러리

LLM이 Json 형태의 데이터를 만들어 줬다 생각해 보자.
```py
data = {
    "id": 1,
    "name": "neo",
    "is_active": True
}
```
일반적으로 이런 데이터들이 정상적으로 들어가 있는지 확인하려면 복잡하게 하드코딩을 해야 한다. 그것도 모든 경우의 수를 다 고려해서

```py
from pydantic import BaseModel

class User(BaseModel):
    id: int
    name: str
    is_active: bool = True
```
pydantic은 데이터 검증을 위해 파이썬의 부모-자식 클래스, 클래스-인스턴스 관계를 이용한다.

`User`라는 기본 모델에 각 데이터가 갖는 데이터타입, 기본값 등을 설정함 - SQL에서 컬럼마다 데이터타입 설정하던 걸 생각하면 됨

```py
u1 = User(id=1, name='neo')
print(u1)
# >> id=1 name='neo' is_active=True

u2 = User(id='aaa', name='smith')
# >> ValidationError: 1 validation error for User
```
id에 str가 들어가면 바로 에러가 난다. 단, 변환 가능한 숫자(`'2'`)면 알아서 변환도 해줌

`.model_dump()`를 사용하면, python 객체를 한번에 딕셔너리로 변환도 해준다 -> `'id': 1, 'name': neo, 'is_active': True`

딕셔너리로 변환하는건 나중에 Json으로 변환하기 쉽기 때문임

#### `Field()`

단순히 데이터 타입 저장만 하는 게 아니라, 더 자세한 조건도 지정할 수 있다.

```py
from pydantic import Field

class Order(BaseModel):
    quantity: int = Field(gt=0, description='주문 수량 0 초과')
    price: float = Field(ge=100, description='최소 단가는 100원 이상')
```
이 때, 조건은 `gt`(초과), `ge`(이상), `lt`(미만), `le`(이하) 등 숫자 관련된 조건도 있고, `min_length`, `max_length`처럼 글자 관련된 조건도 있다. 이건 필요할 때 적당히 넣어주면 됨

또, 각 필드에 대한 설명을 `description`으로 제공할 수 있는데, LLM은 이걸 읽고 더 정확한 결과를 생성할 수 있다. 조건보다, 설명에 대한 중요성이 더 커지는 중

```py
o1 = Order(quantity=3, price=300)
o2 = Order(quantity=-1, price=30)  
#>> ValidationError: 2 validation errors for Order
```
o1은 에러가 안난다. 검증된 데이터라는 것

반면, o2는 조건을 맞추지 못해 에러가 나는 것을 볼 수 있다.

#### `field_validator()`

`Field()`보다 더 복잡한 제약조건을 걸고 싶을 때 사용한다.

```py
from pydantic import field_validator

class Account(BaseModel):
    username: str
    password: str 
    
    @field_validator('password')
    def check_password_length(cls, v):
        if len(v) < 8:
            raise ValueError('비밀번호는 8자 이상이어야 합니다')  # raise: 에러를 내다
        return v
```
`v`는 value로, password의 길이가 8자 미만이면 에러를 내겠다는 것

```py
a = Account(username='bob', password='short')
# >> ValidationError: 1 validation error for Account
b = Account(username='chris', password='loooooong')
```
`Field()`는 사전에 정해진 조건밖에 걸지 못하지만, `field_validation()`은 함수로 자유로운 제약조건을 걸 수 있다.

### 실제 사용

답변의 형태를 pydantic으로 고정하고, LLM에게 이대로 답변을 달라고 넘기면 validation을 통과한 답변만 돌려주게 된다.

이제 굳이 프롬프트에 어떤어떤 형태로 답변을 줘 라고 써줄 필요가 없다

```python
from pydantic import BaseModel, Field
from langchain_core.output_parsers import PydanticOutputParser

class EmailSummary(BaseModel):
    person: str = Field(description='메일 보낸 사람')
    email: str = Field(description='메일 보낸 사람의 메일 주소')
    subject: str = Field(description='메일 제목')
    summary: str = Field(description='메일 본문 요약')
    date: str = Field(description='메일에 언급된 미팅 날짜와 시간')

parser = PydanticOutputParser(pydantic_object=EmailSummary)

prompt = PromptTemplate.from_template(
    "이메일 내용을 요약해줘.\n{format_instructions}\n\n{email_content}"
).partial(format_instructions=parser.get_format_instructions())

chain = prompt | llm | parser
res = chain.invoke({"email_content": "..."})

print(res.model_dump())
# >> {{'date': '2024년 1월 15일 오전 10시'}, ...}}
```
결과물이 dict 형태로 나오고, 양식이 항상 일정하게 된다.

## CommaSeparatedListOutputParser

LLM의 응답을 쉼표로 구분하여 list로 변환하는 파서

```python
from langchain_core.output_parsers import CommaSeparatedListOutputParser

parser = CommaSeparatedListOutputParser()

prompt = PromptTemplate.from_template(
    "'{topic}'에 대한 5가지 예시를 들어줘.\n{format_instructions}"
).partial(format_instructions=parser.get_format_instructions())

chain = prompt | llm | parser
res = chain.invoke({"topic": "대한민국의 국민주식"})

print(res)
# >> ['삼성전자', 'SK하이닉스', 'LG전자', '현대차', 'POSCO']
```
결과물이 csv로 변환하기 쉽게 list로 나온다.

## StructuredOutputParser

`ResponseSchema`를 통해 원하는 출력 구조를 정의하여 딕셔너리(`dict`) 형태로 결과를 받음

좀 멍청한 모델에도 적용할 수 있다.
```python
from langchain.output_parsers import ResponseSchema, StructuredOutputParser

response_schemas = [
    ResponseSchema(name='answer', description='사용자의 질문에 대한 답변'),
    ResponseSchema(name='source', description='답변에 사용된 출처')
]
parser = StructuredOutputParser.from_response_schemas(response_schemas)

chain = prompt | llm | parser
res = chain.invoke({"question": "처서는 몇월 몇일인가?"})

print(res)
# >>{'answer': '...', 'source': '...'}
```

## 기타 주요 파서

-   **`PandasDataFrameOutputParser`**
    
    LLM이 생성한 쿼리를 실제 Pandas DataFrame에 실행하여 결과를 반환.
    
    ```python
    parser = PandasDataFrameOutputParser(dataframe=titanic_df)
    query = "age 컬럼의 평균을 구해줘"
    # >> LLM은 "mean:age"와 같은 문자열을 생성하고, 파서는 이를 실행
    ```
    
    단, 컬럼 조회, 간단한 집계 등에만 사용될 수 있다. 복잡한 작업은 잘 못함

-   **`DatetimeOutputParser`**

    날짜/시간과 관련된 텍스트를 Python의 `datetime` 객체로 변환

    ```python
    parser = DatetimeOutputParser(format='%Y-%m-%d')
    # >> LLM이 "2024-08-23"과 같은 문자열을 생성하면, 파서는 datetime 객체로 변환
    ```

-   **`EnumOutputParser`**
    
    Enum, 즉 Enumerate(열거)를 해주는 파서.

    미리 정의된 `Enum` 클래스의 멤버 중 하나를 선택하도록 강제하여, 객관식 답변을 유도
    ```python
    from enum import Enum
    class Colors(Enum):
        RED = "빨간색"
        BLUE = "파란색"
    
    parser = EnumOutputParser(enum=Colors)
    # >> LLM이 "빨간색"이라고 답하면, 파서는 Colors.RED를 반환
    ```

