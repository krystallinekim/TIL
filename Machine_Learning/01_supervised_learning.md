# 지도학습

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
```py
from sklearn.model_selection import train_test_split

train_X, test_X, train_y, test_y = train_test_split(X, y)
```






## KNN Regression


## Linear Regression

### 다항회귀