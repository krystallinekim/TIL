# 데이터 관리

## 데이터 로딩

### CSV

#### Pandas를 이용한 CSV 로딩

`pandas` 라이브러리의 `read_csv` 함수를 사용하여 CSV 파일을 DataFrame으로 불러올 수 있다.

```python
import pandas as pd

# df = pd.read_csv('./namsan.csv', encoding='EUC-KR', low_memory=False)
df = pd.read_csv('./namsan.csv', encoding='EUC-KR', dtype={
    'ISBN': str,
    '세트 ISBN': str,
    '주제분류번호': str,
})
```

- **`encoding`**
    파일의 인코딩 방식을 지정한다.
    
    한글이 포함된 경우 `'EUC-KR'`이나 `'utf-8'` 등을 정확히 명시해야 함

- **`dtype`**
    
    특정 열의 데이터 타입을 명시적으로 지정
    
    숫자 형태이지만 문자열로 다뤄야 하는 전화번호나, ISBN 같은 경우 유용

- **`low_memory=False`**
    
    대용량 파일을 다룰 때 DtypeWarning을 방지하고, 전체 파일을 한 번에 읽어와 메모리에 올림

#### DataFrame을 CSV로 저장

`to_csv` 메서드를 사용하여 DataFrame을 CSV 파일로 저장할 수 있다.

```python
df.to_csv('./test.csv', encoding='utf-8', index=False)
```

- **`index=False`**: DataFrame의 인덱스를 파일에 포함하지 않도록 설정함.

### 웹 API (JSON)

웹 API를 통해 받은 JSON 형식의 데이터는 Python에서 다루기 위해 파싱(Parsing) 과정이 필요하다.

파싱은 해석, 분석이라고 이해하면 편함.

- **`json.loads()` (파싱)**: JSON 형식의 문자열(`str`)을 Python 딕셔너리(`dict`)로 변환

- **`json.dumps()` (직렬화)**: Python 딕셔너리(`dict`)를 JSON 형식의 문자열(`str`)로 변환

```python
import json

# Data Parsing [json -> dict]
json_data = '{"a": 1, "b": 2}'
dict_data = json.loads(json_data)

# Data Serializing [dict -> json]
dict_data = {'a': 1, 'b': 2}
json_data = json.dumps(dict_data)
```

## 데이터 정제

불필요한 데이터를 삭제하거나 잘못된 데이터를 수정하여 데이터의 품질을 높이는 과정

### 불필요한 Column 삭제

`drop` 메서드를 사용하여 특정 열을 삭제할 수 있다.

```python
df.drop(['부가기호', 'Unnamed: 13', '주제분류번호'], axis=1, inplace=True)
```

- **`axis=1`**: 열을 기준으로 삭제하도록 지정함

- **`inplace=True`**: 원본 DataFrame을 직접 수정함.
    
    기본값은 `False`이며, False일 경우 새로운 DataFrame을 반환한다. 

### 결측값(NaN) 처리

`dropna` 메서드를 사용하여 결측값이 포함된 행이나 열을 삭제할 수 있다.

- **`how=`**

    - `any`: 열에 결측값이 하나라도 있으면 해당 열을 삭제한다. 기본값

    - `all'`: 열의 모든 값이 결측값일 경우에만 해당 열을 삭제