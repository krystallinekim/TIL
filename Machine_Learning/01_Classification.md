# 분류분석

## KNN Classification

**k-nearest neighbors**

목표 점과 가장 가까운 k개의 점을 본다는 것

```py
from sklearn.neighbors import KNeighborsClassifier

kn = KNeighborsClassifier()
X = df_fish[['length', 'weight']]
y = df_fish['class']
kn.fit(X, y)
```

머신러닝에서 fit = 학습을 돌린다

- X: 입력(input), 분류에 기준으로 하고싶은 특성들
  - 생선데이터의 무게특성, 길이특성

- y: 타겟(target), 분류명
  - 0/1, 도미/빙어

이렇게 입력을 통해 타겟을 분류하는 것이 목적

### Train-Test Split

가진 전체 데이터를 가지고 학습을 돌리면, 내 모델이 정확한지 평가가 불가능함

-> 전체 데이터를 나눠서 일부는 학습하고, 일부는 평가에 사용한다

나누는 기준을 인덱스로 잘라버리면, 데이터가 정렬되었을 경우 분류에 매우 큰 문제가 생김 (**샘플링 편향**)

이럴 때 사용하는게 train-test split 모듈

```py
from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, y)
```

기본적으로 랜덤하게 훈련 데이터 3 : 테스트 데이터 1로 나눠줌.

```py
kn.fit(X_train, y_train)
kn.score(X_test, y_test)
```

훈련 데이터로 학습을 돌리고, 테스트 데이터로 평가하면 됨


### 스케일링

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