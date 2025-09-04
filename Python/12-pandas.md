# Pandas 핵심 정리

이 문서는 `09_pandas1.ipynb`, `09_pandas2.ipynb`, `09_pandas3.ipynb` 파일의 내용을 바탕으로 Pandas 라이브러리의 핵심 기능을 정리합니다.

## Pandas 기본

### Pandas란?

- Python에서 사용하는 데이터 분석 라이브러리입니다.

- 표(테이블) 형태의 데이터를 다루는 데 특화되어 있으며, R의 `data.frame`과 유사한 `DataFrame` 객체를 제공합니다.

- CSV, Excel, SQL 데이터베이스 등 다양한 소스에서 데이터를 쉽게 가져올 수 있습니다.

- 데이터 정제, 변환, 분석, 시각화에 필요한 강력한 기능들을 제공합니다.

### 자료구조

#### `Series`

- 1차원 배열과 유사한 구조로, 각 데이터에 **인덱스(index)** 라는 레이블이 붙어 있습니다.

- 딕셔너리나 리스트를 사용하여 생성할 수 있습니다.

```python
import pandas as pd

# 리스트로 Series 생성
s = pd.Series([1, 3, 5, 7, 9], index=['a', 'b', 'c', 'd', 'e'])

# 딕셔너리로 Series 생성
d = {'a': 1, 'b': 2, 'c': 3}
s3 = pd.Series(d)
```

#### `DataFrame`

- 2차원 테이블 형태의 자료구조로, 행(row)과 열(column)에 모두 인덱스가 있습니다.

- 여러 개의 `Series`가 모여서 `DataFrame`을 구성한다고 볼 수 있습니다.

- 딕셔너리나 리스트의 리스트를 사용하여 생성할 수 있습니다.

```python
# 딕셔너리로 DataFrame 생성
data = {
    '이름': ['김철수', '이영희', '박민수'],
    '나이': [25, 28, 22],
}
df = pd.DataFrame(data)
```

## 데이터 선택 및 조작

### 데이터 선택

#### 열(Column) 선택

- `[]` 또는 `.`을 사용하여 특정 열을 선택할 수 있습니다.

- 하나를 선택하면 `Series`, 여러 개를 선택하면 `DataFrame`이 반환됩니다.

```python
# 단일 열 선택 (Series)
df['이름']
df.이름

# 다중 열 선택 (DataFrame)
df[['이름', '나이']]
```

#### 행(Row) 선택: `loc`와 `iloc`

- **`loc`**: 레이블(인덱스 이름) 기반으로 데이터를 선택합니다.

- **`iloc`**: 정수 위치(position) 기반으로 데이터를 선택합니다.

```python
# loc 예제 (인덱스 '이름'으로 설정)
df_set = df.set_index('이름')
df_set.loc['김철수']  # '김철수' 행 선택
df_set.loc[['김철수', '이영희'], '나이'] # 여러 행, 특정 열 선택

# iloc 예제
df.iloc[0]  # 첫 번째 행 선택
df.iloc[0:2] # 0, 1번 행 선택
df.iloc[[0, 2], [0, 1]] # 0, 2번 행과 0, 1번 열 선택
```

#### 조건부 필터링 (Boolean Indexing)

- 특정 조건을 만족하는 행만 선택합니다.

- `&`(AND), `|`(OR) 연산자를 사용하여 여러 조건을 조합할 수 있습니다.

```python
# 나이가 25세 이상인 데이터
df[df['나이'] >= 25]

# 나이가 25세 이상이고, 이름이 '김철수'인 데이터
df[(df['나이'] >= 25) & (df['이름'] == '김철수')]

# query 메서드 사용
df.query('나이 >= 25 and 이름 == "김철수"')

# isin 메서드 사용
df[df['이름'].isin(['김철수', '박민수'])]
```

### 데이터 조작

#### 열 추가 및 삭제

```python
# 열 추가
df['거주지'] = ['서울', '부산', '인천']

# 열 삭제
df.drop('거주지', axis=1, inplace=True) # axis=1: 열 방향, inplace=True: 원본 변경
```

#### 데이터 정렬

- `sort_values()`: 특정 열의 값을 기준으로 정렬합니다.

- `sort_index()`: 인덱스를 기준으로 정렬합니다.

```python
# '나이' 열을 기준으로 내림차순 정렬
df.sort_values(by='나이', ascending=False)
```

#### 데이터 타입 변환

- `astype()`: 데이터 타입을 변경합니다.

- `pd.to_numeric()`: 숫자형으로 변경 (오류 처리 기능 포함).

- `pd.to_datetime()`: 날짜/시간 타입으로 변경.

```python
df['나이'] = df['나이'].astype(str)
df['점수'] = pd.to_numeric(df['점수'], errors='coerce') # 숫자로 변환 불가 시 NaT으로 처리
```

## 데이터 집계 및 그룹화

### 기술 통계

- `describe()`: 숫자형 데이터의 주요 통계량(개수, 평균, 표준편차, 최소/최대값 등)을 요약합니다.

- `value_counts()`: 범주형 데이터의 빈도를 계산합니다.

### 그룹화: `groupby()`

- 특정 열을 기준으로 데이터를 그룹으로 묶어 집계 연산을 수행합니다.

- `agg()` (aggregate) 메서드를 사용하여 여러 집계 함수를 동시에 적용할 수 있습니다.

```python
# 성별에 따른 나이와 점수의 평균
df.groupby('성별')[['나이', '점수']].mean()

# 여러 집계 함수 적용
df.groupby('성별').agg({
    '나이': ['mean', 'min', 'max'],
    '점수': ['mean', 'std']
})
```

### 피벗 테이블: `pivot_table()`

- `groupby`와 유사하지만, 데이터를 표 형태로 재구성하여 가독성을 높입니다.

- `index`, `columns`, `values`, `aggfunc` 파라미터를 사용합니다.

```python
# 지역과 카테고리별 매출액 합계
pd.pivot_table(df, values='매출액', index='지역', columns='카테고리', aggfunc='sum')
```

## 결측치 처리

- `isna()` 또는 `isnull()`: 결측치 여부를 확인합니다 (True/False).

- `dropna()`: 결측치가 포함된 행 또는 열을 제거합니다.

- `fillna()`: 결측치를 특정 값으로 채웁니다.

```python
# 결측치 확인
df.isna().sum()

# 결측치 제거
df.dropna()

# 결측치 채우기
df['나이'].fillna(df['나이'].mean()) # 나이 평균으로 채우기
df.fillna(method='ffill') # 앞 데이터로 채우기 (forward fill)
```

## 시계열 데이터 처리

### 시계열 데이터 생성 및 변환

- `pd.to_datetime()`: 문자열을 날짜/시간 타입으로 변환합니다.

- `pd.date_range()`: 특정 기간의 날짜/시간 인덱스를 생성합니다.

### 시계열 데이터 인덱싱

- 날짜/시간 인덱스를 사용하여 특정 기간의 데이터를 쉽게 선택할 수 있습니다.

```python
# 인덱스가 날짜/시간 타입일 경우
df['2023'] # 2023년 데이터
df['2023-01'] # 2023년 1월 데이터
df['2023-01-01':'2023-01-15'] # 특정 기간 데이터
```

### 리샘플링: `resample()`

- 시계열 데이터의 시간 간격을 변경합니다. (예: 일별 -> 월별)

- `sum()`, `mean()` 등 집계 함수와 함께 사용됩니다.

```python
# 월별 매출액 합계
df['매출액'].resample('M').sum() # 'M': 월말 기준
```

### 이동창: `rolling()`

- 일정 기간(window) 동안의 데이터를 그룹으로 묶어 통계량을 계산합니다. (예: 이동 평균)

```python
# 20일 이동 평균
df['종가'].rolling(window=20).mean()
```

### 시계열 분해

- `statsmodels` 라이브러리를 사용하여 시계열 데이터를 **추세(Trend)**, **계절성(Seasonality)**, **잔차(Residual)**로 분해할 수 있습니다.

```python
from statsmodels.tsa.seasonal import seasonal_decompose

result = seasonal_decompose(df['매출액'], model='additive', period=7)
result.plot()
```
