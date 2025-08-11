# Numpy

## 데이터 분석 개요

데이터를 수집·가공·분석해 의미 있는 정보와 인사이트를 도출하는 과정

### 프로세스

1. 문제 정의
1. 데이터 수집
1. 데이터 전처리 (정제, 결측치 처리, 형식 변환)
1. 탐색적 데이터 분석 (EDA)
1. 모델링 및 분석
1. 결과 해석 및 시각화

- 활용 분야:
  - 비즈니스 의사결정
  - 과학 연구
  - 추천 시스템
  - 이미지/음성 처리 등


### 파이썬의 데이터 분석 라이브러리

- NumPy: 수치 계산, 다차원 배열
- Pandas: 데이터 조작 및 분석
- Matplotlib/Seaborn: 시각화
- SciPy: 과학 계산
- Scikit-learn(sklearn): 머신러닝


## Numpy 개요

- **Numerical Python**의 약자.
- 고성능 수치 계산 및 배열 연산 지원.
- 순수 Python 리스트보다 빠르고 메모리 효율적.
- 선형대수, 통계, 난수 생성 등 다양한 수학 기능 포함.
- **핵심 데이터 구조**: `ndarray` (다차원 배열)
  - 모든 요소는 같은 자료형
  - 인덱싱, 슬라이싱, 브로드캐스팅 지원


### 기본

 `np.array()` : 파이썬 리스트/튜플을 Numpy 배열로 변환.

배열 속성:
- `.shape` : 배열의 차원 크기
- `.dtype` : 데이터 타입
- `.ndim` : 차원 수

```python
a = np.array([[1, 2], [3, 4]])

print(a.shape, a.ndim, a.dtype)
# >> (2, 2) 2 int64
```

---

### 배열 생성 함수
`np.zeros((m, n))` : 0으로 채운 배열

`np.ones((m, n))` : 1로 채운 배열

`np.eye(n)` : 단위 행렬

`np.arange(start, stop, step)` : 지정 범위의 1D 배열

`np.linspace(start, stop, num)` : 균등 간격의 값 생성

```python
print(np.zeros((2, 3)))
# >>
# [[0. 0. 0.]
#  [0. 0. 0.]]

print(np.arange(0, 10, 2))
# >>
# [0 2 4 6 8]
```

---

### 배열 연산
기본 사칙연산은 요소별 수행.

`**` : 거듭제곱

브로드캐스팅으로 서로 다른 크기 배열 연산 가능.

```python
x = np.array([1, 2, 3])
y = np.array([10, 20, 30])

print(x + y)
# >> [11 22 33]
```

---

### 브로드캐스팅

작은 배열을 큰 배열의 모양에 맞춰 자동 확장.

차원 규칙:
  1. 차원 수 맞추기 (앞에 1 추가)
  2. 각 축 크기 동일 or 1

```python
a = np.array([[1], [2], [3]])
b = np.array([10, 20, 30])

print(a + b)
# >>
# [[11 21 31]
#  [12 22 32]
#  [13 23 33]]
```

---

### 인덱싱 & 슬라이싱
2D 배열: `arr[row, col]`

`:` 로 범위 지정 가능.

`:` 단독 사용 시 해당 축 전체 선택.

```python
arr = np.array([[1, 2, 3], [4, 5, 6]])

print(arr[0, 1])  # 특정 요소
# >> 2

print(arr[:, 1])  # 특정 열
# >> [2 5]
```

---

### 불리언 인덱싱

조건에 맞는 요소 필터링 가능.

```python
arr = np.array([1, 2, 3, 4, 5])

print(arr[arr > 3])
# >> [4 5]
```

---

### Fancy 인덱싱

인덱스 배열을 사용해 특정 위치 선택.

```python
arr = np.array([10, 20, 30, 40])

print(arr[[0, 2]])
# >> [10 30]
```

---

### 배열 형태 변형

`reshape(m, n)` : 모양 변경

`.T` : 전치(행↔열)

`flatten()` : 1차원으로 변환

```python
a = np.arange(6).reshape(2, 3)

print(a)
# >>
# [[0 1 2]
#  [3 4 5]]

print(a.T)
# >>
# [[0 3]
#  [1 4]
#  [2 5]]
```

---

### 배열 결합 & 분할


결합: `np.vstack`, `np.hstack`

분할: `np.vsplit`, `np.hsplit`

```python
a = np.array([[1, 2], [3, 4]])
b = np.array([[5, 6]])

print(np.vstack((a, b)))
# >>
# [[1 2]
#  [3 4]
#  [5 6]]
```

---

### 수학 함수

`np.sqrt()` : 제곱근

`np.exp()` : 지수함수

`np.log()` : 자연로그

```python
print(np.sqrt([1, 4, 9]))
# >> [1. 2. 3.]
```

---

### 통계 함수

`np.sum()`, `np.mean()`, `np.std()`, `np.var()`, 등등등

`axis` 지정 가능:
  - `axis=0` : 축 0(row)끼리 묶어서 계산
  - `axis=1` : 축 1(col)끼리 묶어서 계산

```python
data = np.array([[1, 2], [3, 4]])

print(np.mean(data, axis=0))
# >> [2. 3.]
```

---

### 정렬 & 순위

`np.sort()` : 정렬된 복사본

`np.argsort()` : 정렬 인덱스

```python
arr = np.array([3, 1, 2])

print(np.sort(arr))
# >> [1 2 3]
```

---

### 선형대수

`np.dot(a, b)` : 행렬 곱

`np.linalg.det(a)` : 행렬식

`np.linalg.inv(a)` : 역행렬

`np.linalg.eig(a)` : 고유값, 고유벡터

```python
a = np.array([[1, 2], [3, 4]])

print(np.linalg.inv(a))
# >>
# [[-2.   1. ]
#  [ 1.5 -0.5]]
```

---

### 난수 생성

`np.random.rand()` : 균등분포

`np.random.randn()` : 정규분포

`np.random.randint()` : 정수 난수

`np.random.seed(n)` : 시드 고정

```python
np.random.seed(0)

print(np.random.randint(1, 10, size=5))
# >> [6 1 4 4 8]
```
