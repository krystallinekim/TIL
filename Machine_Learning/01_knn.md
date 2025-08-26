# KNN
k-nearest neighbors

목표 점과 가장 가까운 k개의 점을 보고, 목표를 분류

```py
from sklearn.neighbors import KNeighborsClassifier

kn = KNeighborsClassifier()
X = df_fish[['length', 'weight']]
y = df_fish['class']
kn.fit(X, y)
```
보통 fit -> 학습한다는 뜻

모든 머신러닝에서 필요한건 X, y

- X: 입력(input)
- y: 타겟(target)
