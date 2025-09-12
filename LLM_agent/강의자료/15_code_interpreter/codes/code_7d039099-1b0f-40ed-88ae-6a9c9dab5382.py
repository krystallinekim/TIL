"""
[USER QUESTION]
전체 인원의 비만도를 계산 후, 가장 비만도가 높은 사람이 키가 10cm정도 더 커진다고 할 때, 이 사람의 예측 몸무게와 그 때의 비만도를 같이 알려줘
"""

import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression

# 데이터셋을 pandas DataFrame으로 변환
data = [(1, 150, 45), (2, 152, 48), (3, 154, 49), (4, 156, 50), (5, 158, 52), 
        (6, 160, 54), (7, 161, 55), (8, 162, 56), (9, 163, 57), (10, 164, 58),
        (11, 165, 59), (12, 166, 60), (13, 167, 61), (14, 168, 62), (15, 169, 63),
        (16, 170, 64), (17, 171, 65), (18, 172, 66), (19, 173, 67), (20, 174, 68),
        (21, 175, 70), (22, 176, 71), (23, 177, 72), (24, 178, 73), (25, 179, 74),
        (26, 180, 75), (27, 181, 77), (28, 182, 78), (29, 183, 79), (30, 185, 82)]

# 컬럼 이름 지정
df = pd.DataFrame(data, columns=['ID', 'Height', 'Weight'])

# 비만도(BMI) 계산
df['BMI'] = df['Weight'] / (df['Height'] / 100) ** 2

# 가장 비만도가 높은 사람 찾기
max_bmi_person = df.loc[df['BMI'].idxmax()]

# 키가 10cm 더 커진다고 가정
new_height = max_bmi_person['Height'] + 10

# Linear Regression 모델을 사용하여 예측 몸무게 계산
X = df[['Height']]
y = df['Weight']
model = LinearRegression().fit(X, y)

# 새로운 키에 대한 예측 몸무게
predicted_weight = model.predict(np.array([[new_height]]))[0]

# 새로운 비만도 계산
new_bmi = predicted_weight / (new_height / 100) ** 2

# 결과 출력
print(f"가장 비만도가 높은 사람의 ID: {max_bmi_person['ID']}")
print(f"새로운 키: {new_height} cm")
print(f"예측 몸무게: {predicted_weight:.2f} kg")
print(f"새로운 비만도: {new_bmi:.2f}")
