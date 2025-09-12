"""
[USER QUESTION]
몸무게가 80kg면, 키는 몇 cm일까?
"""

import numpy as np
import pandas as pd
from sklearn.linear_model import LinearRegression

# 데이터셋 준비
data = [
    (1, 150, 45), (2, 152, 48), (3, 154, 49), (4, 156, 50), (5, 158, 52),
    (6, 160, 54), (7, 161, 55), (8, 162, 56), (9, 163, 57), (10, 164, 58),
    (11, 165, 59), (12, 166, 60), (13, 167, 61), (14, 168, 62), (15, 169, 63),
    (16, 170, 64), (17, 171, 65), (18, 172, 66), (19, 173, 67), (20, 174, 68),
    (21, 175, 70), (22, 176, 71), (23, 177, 72), (24, 178, 73), (25, 179, 74),
    (26, 180, 75), (27, 181, 77), (28, 182, 78), (29, 183, 79), (30, 185, 82)
]

# 데이터프레임으로 변환
columns = ['Index', 'Height', 'Weight']
df = pd.DataFrame(data, columns=columns)

# 독립 변수와 종속 변수 설정
X = df[['Weight']]
y = df['Height']

# 선형 회귀 모델 생성 및 학습
model = LinearRegression()
model.fit(X, y)

# 몸무게가 80kg일 때 키 예측
weight_to_predict = np.array([[80]])
predicted_height = model.predict(weight_to_predict)

print(f"몸무게가 80kg일 때 예상 키는 {predicted_height[0]:.2f} cm 입니다.")