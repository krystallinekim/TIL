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

## 교차검증(Cross Validation)

지금까지 모델을 평가할 때, 테스트 점수가 높고 낮음에 따라 모델을 선택해 왔었음.

그런데, 테스트 점수를 이용해 모델을 평가한다면, 또 다른 형태의 과대적합이 아닐까? 하는 의심이 생긴다.

테스트 점수는 최종적으로 모델을 검증하는 수단이지, 모델을 테스트 점수에 맞추면 안된다는 이야기

데이터를 훈련/테스트로 나눠서 테스트 셋에 모델을 맞추는 것이 아니라, 훈련/검증/테스트 3가지로 나눠서 훈련/검증으로 모델링을 만들고, 테스트 데이터로 모델을 평가하자는 것

### k-Fold 교차검증

전체 데이터에서 테스트셋을 빼놓고, 나머지 데이터를 k개로 나눠서 일부는 훈련, 일부는 검증에 사용함

k개로 나눠진 데이터중 훈련/검증에 사용되는 부분을 다르게 하면서 훈련-검증 과정을 반복함

검증으로 빠졌던 데이터도 훈련에 이용할 수 있다는 점이 장점

```py
from sklearn.model_selection import cross_validate

dt = DecisionTreeClassifier(random_state=42)
dt.fit(X_train, y_train)

scores = cross_validate(dt, X_train, y_train)
print('평균 k-Fold점수:', np.mean(scores['test_score']))
```
이제 테스트 score가 아니라 평균 kfold 점수를 이용해서 모델을 평가하고, 가장 좋은 점수가 나온 경우에 test를 돌려서 최종적인 결론을 내면 된다

### Grid Search

사실 hyperparameter는 매우 많다 - 결정트리만 해도 `min_samples_split`, `max_depth`, `min_impurity_decrease` 등등등 선택 가능한 게 많다

수많은 하이퍼 파라미터에 대해 하나하나 for문을 돌려가는건 말이 안됨 - **Grid Search**를 이용함

1. 하이퍼파라미터 선택
    ```py
    from sklearn.model_selection import GridSearchCV

    params = {
        'min_impurity_decrease': np.arange(0.0001, 0.001, 0.0001),  
        'max_depth': range(5, 21),  
        'min_samples_split': range(2, 100, 10),  
    }
    # 선택할 모델(dtc) / param_grid: 돌려볼 파라미터들(params) / n_jobs: 사용할 최대 CPU 개수(-1: 모두)
    gs = GridSearchCV(
        DecisionTreeClassifier(random_state=42), 
        param_grid=params, 
        n_jobs=-1
    )
    ```

    어떤 하이퍼파라미터를 쓸 지 정도는 직접 정해줘야 함

2. 그리드서치 실행

    ```py
    gs.fit(X_train, y_train)
    ```

3. 최적 조합을 찾고, 객체에 저장함

    ```py
    print(gs.best_params_)
    # >> {'max_depth': 14, 'min_impurity_decrease': np.float64(0.0004), 'min_samples_split': 12}
    print(np.max(gs.cv_results_['mean_test_score']))
    # >> 0.8683865773302731
    ```

    `.best_params_`로 가장 좋은 파라미터 조합을 뽑을 수 있다
    
    k-Fold는 gridsearch 안에서 진행해서 이미 평균 kFold 점수를 구함 - `.cv_results_`에서 볼 수 있다

4. 최상의 매개변수에서, 테스트 세트를 활용해 최종 모델을 평가함

    ```py
    dt_best = gs.best_estimator_
    print(f'Test score: {dt_best.score(X_test, y_test)}')
    ```
    `.best_estimator_` 안에 최종 모델이 저장됨

    아껴놓은 테스트 데이터를 써서 모델이 얼마나 잘 만들어졌는지 평가하면 된다. 테스트 데이터는 지금까지 모델과 아무 관련이 없었음

### Randimized Search

Grid Search는 모든 후보 하이퍼파라미터에 대해 조합을 탐색하기 때문에 연산량이 매우 커진다.

RSCV는 지정된 범위에 대해 임의로 샘플링해 돌려 상대적으로 빠르고 효율적이 된다.

이 때 파라미터 선정은 일반적으로 `scipy.stats`의 `uniform`, `randint`를 이용함

```py
from scipy.stats import uniform, randint
params = {
    'min_impurity_decrease': uniform(0.0001, 0.001),
    'max_depth': randint(10, 50),
    'min_samples_split': randint(2, 25),
    'min_samples_leaf': randint(1, 25),
}

from sklearn.model_selection import RandomizedSearchCV
gs = RandomizedSearchCV(
    DecisionTreeClassifier(random_state=42),
    param_distributions=params,
    n_iter=1000,
    n_jobs=-1,
    random_state=42
)

gs.fit(X_train, y_train)
```
`uniform`, `randint`가 지정된 범위 안에서 임의로 실수/정수를 샘플링하고, 임의로 정해진 하이퍼파라미터 조합에 대해 각각 평가함

