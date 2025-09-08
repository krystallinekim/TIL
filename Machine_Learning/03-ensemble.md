# 앙상블

여러 개의 약한 개별 모델을 조합(ensemble)해서 하나의 예측 모델을 만드는 방법

정형 데이터 (Structured Data) 기준으로는 가장 뛰어난 성과를 내는 알고리즘

대부분의 앙상블 학습은 **트리** 기반이다

## 트리 구조

그래프 = 점(node)과 점 사이의 연결선(edge)을 포함한 자료 구조 -> 연결된 노드 간의 관계를 표현할 수 있다. 

트리 = 어디에서도 순회하지 않는 그래프 -> 특정 노드를 루트 노드로 정하면, 모든 노드를 부모-자식 관계로 표현할 수 있다.

## Random Forest

### Decision Tree

결정 트리는 트리 구조로 전체 데이터에 대해 스무고개를 돌려서 전체 데이터를 분류하는 게 목적인 분석 방법

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

### Random Forest

결정 트리 구조를 여러개 돌려서 랜덤 '포레스트' 분석

1. 결정트리를 랜덤하게 만들어 트리로 숲을 만듦

2. 각 결정트리의 예측을 종합해 최종 예측을 만듦

- 트리 하나를 이용했을 때보다 과대적합(overfitting)에 더 안전하다 

```py
from sklearn.ensemble import RandomForestClassifier

rf = RandomForestClassifier(n_jobs=-1, random_state=42)
rf.fit(X_train, y_train)
print(rf.feature_importances_)
# >> [0.23167441 0.50039841 0.26792718]
```
`.feature_importances_`는 각 피쳐별로 피쳐가 사용된 비율을 보여줌.

결정트리와 똑같이 `max_depth`를 설정하지 않을 경우, 과대적합 위험이 있다.

#### bootstrap sampling

데이터가 1000개면, 각 트리마다 1000개 데이터를 **복원추출**함. 이때 중복을 허용(같은 데이터가 뽑힐 수 있다)

즉, 각 트리가 원본 데이터의 무작위 표본을 학습해 트리마다 분산이 달라진다는 것

#### 분석 종류에 따른 특성 선택

노드 분할시, 분류/회귀분석에 따라 특성 선택방식이 다름(선택도 가능함)

- 분류: 전체 특성 중 루트(특성 개수)개의 특성을 선택함

- 회귀: 모든 특성을 다 씀

#### 결과 종합

- 분류: 각 트리 예측 중 가장 많은 클래스를 최종 예측으로 선택

- 회귀: 각 트리가 예측한 값의 평균

## Extra Tree

- 랜덤 포레스트와 매우 유사함 - 랜덤성이 더 크다

- 부트스트랩 샘플링(복원추출)을 사용하지 않고, 전체 훈련세트를 그대로 사용 / 특성도 무작위로 선택함

- 노드 분할 시 최적 노드(불순도/정보이득)를 찾는게 아니라 무작위로 분할

  - 성능이 낮아질 수 있지만, 많은 트리를 앙상블해서 과대적합을 막음

**데이터 노이즈가 많은 경우 사용**

- 데이터 노이즈: 패턴과 상관없는 불필요, 잘못된 신호 / 미묘하게 다른 값이라 이상치에도 잡히지 않는 데이터

```py
from sklearn.ensemble import ExtraTreesClassifier

et = ExtraTreesClassifier(n_jobs=-1, random_state=42)
et.fit(X_train, y_train)
print('Test score:', et.score(X_test, y_test))
# >> Test score: 0.8861538461538462
```

## Gradient Boosting

- 약한 모델(depth가 낮은 결정트리)을 여러개 차례대로 학습
- 앞 모델에서 틀린 부분을 뒤 모델에서 보완 - Boosting(강화)
- 오차를 줄이기 위해 경사하강법의 아이디어를 적용함 - Gradient Descent(경사하강법)

1. 약한 첫번째 결정트리 학습 - 기본 예측값 생성

2. 잔차(residual)를 계산: 답-예측

3. 잔차를 예측할 새로운 트리 학습 / 예측값 업데이트

4. 반복 - 오차가 줄어든다

```py
from sklearn.ensemble import GradientBoostingClassifier

gb = GradientBoostingClassifier(random_state=42)
gb.fit(X_train, y_train)
print('train score:', np.mean(scores['train_score']))
# >> train score: 0.8881086892152563
print('test score :', np.mean(scores['test_score']))
# >> test score: 0.8669230769230769
print(gb.feature_importances_)
# >> [0.11949946 0.74871836 0.13178218]
```
Extra tree나 Random Forest와 달리, 따로 파라미터를 설정 안했는데 Train-Test score가 비슷하게 나온다.

### 장단점

(+) 비선형/복잡한 데이터에서 예측이 뛰어나고, 과적합을 방지하기 위한 여러 규제도 있음
  - 전용 별도 라이브러리(XGBoost, LightGBM, CatBoost 등) 존재 - 성능이 좋다

(-) 학습 속도가 느림 
  - 항상 순서대로 작동해야 해서 -> n_jobs가 없음(병렬화 불가능/동시에 작업 불가)

(-) 하이퍼파라미터가 좀 많아서 튜닝이 필요

(-) 데이터 노이즈에 민감한 편


### 히스토그램 기반 GB


히스토그램 = 데이터를 계급으로 나눈다

- 입력 특성들을 256개 구간으로 나눔
- 노드 분할 시 최적분할을 가장 빠르게 찾을 수 있다
  - 특정 기준을 잡을 때, 미리 구간별로 계산을 조금 해놔서 구간 경계값을 기준으로 쓴다
- 데이터 숫자가 매우 커져도 구간은 256개이므로, 데이터 숫자가 많아져도 계산이 빠름
- 성능 좋은 GB 모델은 히스토그램 기반으로 돌리는 경우가 많다(XGBoost, LightGBM)
- 자동으로 결측치도 채워줌


```py
from sklearn.ensemble import HistGradientBoostingClassifier

hgb = HistGradientBoostingClassifier()
hgb.fit(X_train, y_train)
print('Test score:', hgb.score(X_test, y_test))
# >> Test score: 0.8723076923076923
```

돌려보면 일반 GB에 비해 실행 시간이 매우 짧아진 것을 볼 수 있음