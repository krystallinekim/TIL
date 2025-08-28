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

## 로지스틱 회귀

이름은 회귀지만, 분류분석에 속함

데이터를 보고, 특정 분류에 속하는 확률이 몇%인지를 계산하는 분석법

**KNN 분류**에서는 가까운 점이 A/B/B 일 경우 66%의 확률로 목표가 B일 것이라고 예측하는 형태 -> 특정 분류일 확률이라기엔 미묘함.

다양한 input에 대해, 각 input값마다 계수를 곱하고 절편을 더한 값(다항식의 결과값)을 `Z값`이라고 하고, 

Z값을 확률로 바꿔서 판단하자는 것이 **로지스틱 회귀**

### 이진분류

분류 대상이 2개만 있을 때, 분류 대상 하나가 맞을 확률(틀릴 확률)만 구하면 분류할 수 있다.

하나로 분류될 확률이 p라면, 나머지 하나로 분류될 확률은 1-p로 쉽게 계산할 수 있다.

```py
from sklearn.linear_model import LogisticRegression

lr = LogisticRegression()
lr.fit(X_train_bs, y_train_bs)
lr.predict_proba(X_train_bs[:1])
# >> [[0.99760007 0.00239993]]
```

**시그모이드 함수** 

아무리 x가 커지거나 작아져도 y값이 항상 0에서 1 사이에 있도록 확률로 만들어주는 함수

$$
\sigma(z) = \frac{1}{1 + e^{-z}}
$$
- $z$ : 입력 값
- $K$ : 클래스 개수

```py
def sigmoid(x):
    return 1 / (1 + np.exp(-x))
```
x값이 매우 큰 값일 때 y값이 1에 가까워지고, 매우 작을 때는 0에 가까워진다. x값이 0일 때 y값은 0.5

이진분류에서는 Z값에 시그모이드 함수를 적용해 나온 값을 특정 분류가 맞을 확률으로 사용하게 된다.

### 다중분류

분류 대상이 2개를 넘어가면, 이진분류처럼 시그모이드로 하나의 확률을 구하고, 나머지 확률을 1에서 빼는 식으로 결정할 수가 없어진다.

이럴때 사용하는 함수는 softmax 함수

**softmax 함수**

총합이 1이 되도록 Z값들을 더해서 비율로 만들어주는 함수

$$
\text{softmax}(z_i) = \frac{e^{z_i}}{\sum_{j=1}^{K} e^{z_j}}
$$
- $z$ : 입력 값
- $K$ : 클래스 개수
- $z_i$ : $i$ 번째 클래스의 점수

```py
def softmax(Z):
    e_Z = np.exp(Z - np.max(Z))
    return e_Z / e_Z.sum()
```
일반적으로 더하는 방식이 아니라, $e^Z$들의 총합에 대한 비율로 계산함

가장 비율이 높은 경우가 분류에 적합하다고 판단하는 방식

```py
from sklearn.linear_model import LogisticRegression

lr = LogisticRegression(C=20, max_iter=1000)
lr.fit(X_train, y_train)
```
다중분류일 경우, `C`(규제)와 `max_iter`(최대 반복 횟수)를 정해주어야 한다.

반복적으로 계산을 돌려서 최적값을 찾는 방식이기 때문

## 결정 트리 분류

그래프 = 점(node)과 점 사이의 연결선(edge)을 포함한 자료 구조 -> 연결된 노드 간의 관계를 표현할 수 있다. 

트리 = 어디에서도 순회하지 않는 그래프 -> 특정 노드를 루트 노드로 정하면, 모든 노드를 부모-자식 관계로 표현할 수 있다.

결정 트리는 이런 트리 구조로 전체 데이터에 대해 스무고개를 돌려서 전체 데이터를 분류하는 게 목적인 분석 방법

```py
from sklearn.tree import DecisionTreeClassifier

dtc = DecisionTreeClassifier(max_depth=5, random_state=42)
dtc.fit(X_train, y_train)
```
fit을 돌리면, 데이터에 특정 기준을 적용하고, 그렇게 분류된 데이터에 대해 불순률을 계산하고, 아직 불순률이 있다면 다른 기준을 적용해 또 나누는 작업을 반복하게 된다.

이 때, 머신러닝은 이런 기준들을 계속 랜덤하게 적용하게 됨

결정트리 분류에서 `hyperparameter`는 `max_depth`이다. 최대 depth의 크기로, 루트에서 몇단계까지 내려가는 것 까지 볼 수 있는지 정해주는 것

**불순률**

일반적으로, gini 불순도를 사용해 분포가 섞여 있음을 나타내게 된다.

$$
Gini(t) = 1 - \sum_{i=1}^{C} p_i^2
$$
- $C$ : 클래스 개수
- $p_i$ : 노드 $t$ 에서 클래스 $i$ 가 차지하는 비율

이진 분류일 경우, C=2이므로

$$
Gini(t) = 1 - (p_0^2 + p_1^2)
$$

처럼 만들 수 있다.

- gini = 0.5 : 비율이 1:1, 하나도 분류가 안된것
- gini ≈ 0   : 비율이 한쪽으로 몰림. 분류가 꽤 잘 된 것
- gini = 0   : 클래스 하나만 남아서 분류가 끝난 것

