# 회귀분석

회귀 = 예측

무언가를 기준으로 다음 값을 예측하겠다는 것

## 과대적합, 과소적합

```py
model.score(X_train, y_train)  # 훈련 점수
model.score(X_test, y_test)  # 테스트 점수
```
훈련 점수와 테스트 점수를 비교해서 적합 여부를 확인할 수 있다.

테스트 점수와 훈련 점수가 비슷하게 높은 경우, 성공적으로 머신러닝을 돌린 것

- 과대적합(Overfitting)

  - 훈련 점수 >> 테스트 점수

- 과소적합(Underfitting)

  - 테스트 점수 >> 훈련 점수

  - 전체적으로 점수가 낮음

## KNN Regression

K-Nearest Neighbors, 가장 가까운 k개를 가지고 예측함

```py
from sklearn.neighbors import KNeighborsRegressor

knr = KNeighborsRegressor(n_neighbors=5)
knr.fit(X_train, y_train)
print(knr.score(X_test, y_test))
# >> 0.992809406101064
```
KNN회귀의 hyperparameter(내가 정할 수 있는 변수)는 `n_neighbors=5`(가까운 이웃을 몇개까지 볼지)

기본값이 5개고, 보통 홀수 단위로 돌린다.

훈련 데이터와 가장 가까운 이웃 k개를 기준으로, 그 평균값을 테스트 데이터의 예상값으로 정함

- 테스트 데이터가 훈련 데이터의 최대/최소값을 넘어가는 순간부터는 예상값이 전부 똑같아진다.


## 선형회귀분석

Linear Regression

훈련 데이터와 가장 거리가 가까운 직선을 긋는다.

직선 : `y = ax + b`이므로, a(기울기)와 b(y절편)만 구하면 예측 가능

```py
from sklearn.linear_model import LinearRegression

lr = LinearRegression()
lr.fit(X_train, y_train)

print(f'y = {lr.coef_[0]}x + {lr.intercept_}')
# >> y = 39.01714496363019x + -709.0186449535474
print('Train score:', lr.score(X_train, y_train))
# >> Train score: 0.9398463339976041
print('Test score:', lr.score(X_test, y_test))
# >> Test score: 0.824750312331356
```
`.coef_`로 계수들(기울기)의 리스트(선형은 1개)를 구할 수 있고, `.intercept_`로 y절편을 가져옴

다만, 선형회귀분석이 잘 맞는 경우는 많지 않음(과소적합)

또, 무조건 1차식밖에 만들지 못해 예측값이 음수가 나올 경우도 생긴다.

## 다항회귀분석

선형회귀분석의 과소적합 문제를 해결하기 위해 모델의 복잡도를 올리는 방법

직선이 아니라 곡선으로 표시하고, 이 때 X의 차수를 올리는 방식 -> `y = ax^2 + bx + c`

```py
X_train_poly = np.column_stack((X_train ** 2, X_train))
X_test_poly = np.column_stack((X_test ** 2, X_test))

lr = LinearRegression()
lr.fit(X_train_poly, y_train)

print(f'y = {lr.coef_[0]}x^2 + {lr.coef_[1]}x + {lr.intercept_}')
# >> y = 1.0143321093767301x^2 + -21.55792497883735x + 116.05021078278264
print('Train score:', lr.score(X_train_poly, y_train))
# >> Train score: 0.9706807451768623
print('Test score:', lr.score(X_test_poly, y_test)) 
# >> Test score: 0.9775935108325122
```
새로 x^2 항을 추가해서 컬럼 2개에 대해 확인하는 방식

`.coef_`에 계수들이 리스트 형태로 들어가 있다.

이차식 뿐만이 아니라, 삼차, 사차 등 여러 개를 넣을 수도 있다.

## 다중회귀분석

다항회귀분석이 한 가지 변수에 대해 다항식을 만든다면, 다중회귀분석은 변수 여러 개의 관계를 이용해 분석함

변수 x1, x2가 있을 때 `y = ax1 + bx2 + cx1^2 + dx2^2 + ex1x2`처럼, `ex1x2` 항을 이용함.

### Polynomial

PolynomialFeatures 모듈: 원본, 제곱, 서로 곱한 값을 알아서 계산해주는 모듈

bias(절편)는 보통 빼고 생각한다.

```py
from sklearn.preprocessing import PolynomialFeatures

poly = PolynomialFeatures(include_bias=False)
poly.fit([[2, 3, 5]])
print(poly.transform([[2, 3, 5]]))
# >> [[ 2.,  3.,  5.,  4.,  6., 10.,  9., 15., 25.]]
```
sklearn 모델 자체가 fit을 해야 기타 동작을 할 수 있게 만들어서, transform 하려면 무조건 fit부터 해야함

`fit_transform`을 사용하면 한번에 되긴 함

### 다중회귀분석

```py
poly = PolynomialFeatures(degree=2, include_bias=False)
X_train_poly = poly.fit_transform(X_train)
X_test_poly = poly.fit_transform(X_test)

lr_p = LinearRegression()
lr_p.fit(X_train_poly, y_train)
print('Train score:',lr_p.score(X_train_poly, y_train))
# >> Train score: 0.9903183436982124
print('Test score:',lr_p.score(X_test_poly, y_test))
# >> Test score: 0.9714559911594145
```
다중회귀분석에서의 hyperparameter는 `degree=2`

새로운 변수의 최대 차수를 뜻한다. 

### 과대적합 문제

`degree`를 키우면 키울수록 모델의 복잡도가 늘어나, 훈련 데이터셋에 훨씬 잘 맞게 된다. 거의 1에 가깝게 맞출 수 있다.

```py
poly_over = PolynomialFeatures(degree=5, include_bias=False)
X_train_poly_over = poly_over.fit_transform(X_train)
X_test_poly_over = poly_over.fit_transform(X_test)

lr_p5 = LinearRegression()
lr_p5.fit(X_train_poly_over, y_train)
print('Train score:',lr_p5.score(X_train_poly_over, y_train))
# >> Train score: 0.9999999999996176
print('Test score:',lr_p5.score(X_test_poly_over, y_test))
# >> Test score: -144.40585108215134
```

반대로 복잡도가 늘어나 테스트 점수가 바닥을 치게 됨

실습 데이터에서 degree를 4만 줘도 테스트 점수가 음수가 되고, degree=5는 테스트 점수가 -144.4가 나온다.

그래프를 그린다고 생각하면 모든 훈련 데이터를 지나가는 그래프를 그려버린 셈임

### 규제

과대적합 문제를 해결하기 위한 수단

계수에 제한을 걸어서 과대적합이 되지 않게 규제함

#### **릿지(Ridge)**

계수를 제곱한 값을 기준으로 규제

문제가 있는 계수를 크게 줄여서, 영향력을 줄이는 방식

```py
from sklearn.linear_model import Ridge

ridge = Ridge(alpha=0.1)
ridge.fit(X_train_scaled, y_train)
print('Train score:',ridge.score(X_train_scaled, y_train))
# >> Train score: 0.9903815817570368
print('Test score:',ridge.score(X_test_scaled, y_test))
# >> Test score: 0.9827976465386983
```
여기서 사용한 데이터는 degree=5인 55개 항이 있는 데이터이지만, 훈련과 테스트 점수가 비슷하게 높게 나오는 것을 볼 수 있다.

릿지의 hyperparameter는 `alpha=1`

알파값이 0이면 계수를 줄이는 강도가 0이라 선형회귀와 똑같다.

알파값이 커질수록 계수를 세게 줄여서 결국 모든 데이터의 평균을 지나는 수평선이 됨

알파값의 최적값은 직접 몇개를 넣어서 점수가 가장 높은 값을 찾아야 한다.

#### **라쏘(LASSO)**

절대값 기준으로 규제

덜 중요한 계수를 아예 0으로 날려버리는 방식

```py
from sklearn.linear_model import Lasso

lasso = Lasso(alpha=10)
lasso.fit(X_train_scaled, y_train)
print('Train score:',lasso.score(X_train_scaled, y_train))
# >> Train score: 0.9888067471131867
print('Test score:',lasso.score(X_test_scaled, y_test))
# >> Test score: 0.9824470598706695
```

라쏘도 점수가 잘 나오는 것을 볼 수 있다.

hyperparameter는 릿지와 똑같이 `alpha`를 이용한다. 하는 역할도 똑같음

라쏘는 자동으로 필요없는 변수를 선택하고, 그 변수를 제거하는 과정을 거친다. 선택하는 과정에 모든 변수를 다 봐야 해서, 조금 더 연산력이 필요함