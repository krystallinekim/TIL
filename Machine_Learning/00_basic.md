# 머신러닝 기초

## Train-Test Split

가진 전체 데이터를 가지고 학습을 돌리면, 내 모델이 정확한지 평가가 불가능함

-> 전체 데이터를 나눠서 일부는 학습하고, 일부는 평가에 사용한다

나누는 기준을 인덱스로 잘라버리면, 데이터가 정렬되었을 경우 분류에 매우 큰 문제가 생김 (**샘플링 편향**)

이럴 때 사용하는게 train-test split 모듈

```py
from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)
```
기본적으로 랜덤하게 훈련 데이터 3 : 테스트 데이터 1로 나눠준다.

`random_state`로 랜덤성을 고정할 수 있다.

## 스케일링

일반적으로 X에 들어가는 입력 데이터에는 다양한 데이터가 들어가짐(무게, 길이 등등)

이 모든 데이터의 스케일이 같을 거라고 생각할 수 없다.

일반적으로, 데이터를 정규화(평균=0, 표준편차=1)해 데이터의 분포로 입력 데이터를 바꿈

```py
mean = np.mean(X_train, axis=0)
std = np.std(X_train, axis=0)
X_train_scaled = (X_train - mean) / std
```
테스트 데이터도 정규화 할 때 훈련 데이터의 평균과 표준편차를 이용함

모든 기준이 동일하게 들어가야 의미가 있기 때문

데이터가 클 경우, 간단하게 모듈로 스케일링도 가능함
```py
from sklearn.preprocessing import StandardScaler

ss = StandardScaler()
ss.fit(X_train)
X_train_scaled = ss.transform(X_train)
X_test_scaled = ss.transform(X_test)
```
sklearn답게 `fit_transform`이 있지만, 훈련 셋 기준으로 스케일링 해야 하기 때문에 훈련 셋 기준으로 fit하고 훈련셋과 테스트셋을 변환해줌


## 과대적합, 과소적합

```py
model.score(X_train, y_train)  # 훈련 점수
model.score(X_test, y_test)  # 테스트 점수
```
훈련 점수와 테스트 점수를 비교해서 적합 여부를 확인할 수 있다.

테스트 점수와 훈련 점수가 비슷하게 높을 경우, 성공적으로 머신러닝을 돌린 것

- 과대적합(Overfitting)

  - 훈련 점수 >> 테스트 점수

- 과소적합(Underfitting)

  - 테스트 점수 >> 훈련 점수

  - 전체적으로 점수가 낮음

## 데이터 전처리

### 라벨 인코딩

범주형 데이터를 카테고리별로 0, 1, 2처럼 숫자로 변환함

(+) : 카테고리 숫자가 늘어나도 차원이 늘지 않음

(-) : 0, 1, 2를 숫자로 인식하는 경우(KNN, 거리로 인식), 범주형 데이터에서 순서가 생겨버린다.

```py
from sklearn.preprocessing import LabelEncoder
```

### One-Hot Encoder

범주형 데이터를 T/F로 이루어진 여러개의 컬럼으로 변환함

(+) : 범주형 데이터를 0, 1, 2처럼 바꿔버리면 크기에 순서가 생겨서 해석에 문제가 생김

(-) : 카테고리가 많아질수록 차원이 늘어남

라벨 인코딩과 정확히 장/단점이 반대이다. 전체 컬럼의 숫자나 데이터 분석 방법 등을 보고, 적절한 것을 선택하면 됨

- 판다스의 `get_dummies` 기능을 이용해 간단하게 처리할 수 있다.
    ```py
    titanic_encoded = pd.get_dummies(titanic, columns=['sex'])
    ```
    sex_male, sex_female 컬럼에 True/False가 저장되는 걸 볼 수 있음.

    판다스 기능이기 때문에 자동으로 DataFrame으로 반환된다.

- `sklearn`의 `OneHotEncoder` 모듈을 사용할 수도 있다.

    ```py
    from sklearn.preprocessing import OneHotEncoder

    encoder = OneHotEncoder(sparse_output=False)
    encoded = encoder.fit_transform(titanic[['sex']])

    encoded_titanic = pd.DataFrame(encoded, columns=encoder.get_feature_names_out(['sex']))
    titanic_encoded = pd.concat([titanic.drop(columns=['sex']), encoded_titanic], axis=1)
    ```
    0, 1로 저장됨. `sparse_output`을 써서 numpy 배열로 반환할 수 있다. (기본값은 `scipy.sparse.csr_matrix` 형태)

    다른 sklearn 전처리를 연속적으로 사용하기엔 `OneHotEncoder`쪽이 유리하다

### MICE

자동으로 다른 특성들을 사용해서 결측치를 채워줌

```py
from sklearn.experimental import enable_iterative_imputer
from sklearn.impute import IterativeImputer

imputer = IterativeImputer(random_state=42, max_iter=10)
titanic_noempty = titanic_encoded.copy()
titanic_noempty['age'] = imputer.fit_transform(titanic_encoded)
```

