# EDA(탐색적 데이터 분석) 종합 가이드

## EDA의 정의와 목적

탐색적 데이터 분석(EDA)은 분석 초기 단계에서 데이터를 다양한 관점에서 관찰하고 이해하는 과정임.

가설 수립, 데이터 품질 검사, 변수 간 관계 파악 등을 통해 데이터가 가진 구조적인 특징과 인사이트를 발견하는 것을 목적으로 함.

### 데이터 품질의 6가지 차원

- **완전성 (Completeness)**: 결측값이 얼마나 적은가?

- **정확성 (Accuracy)**: 실제 값과 얼마나 일치하는가?

- **일관성 (Consistency)**: 데이터가 모순되지 않는가?

- **유효성 (Validity)**: 정의된 규칙에 맞는가?

- **적시성 (Timeliness)**: 데이터가 최신인가?

- **유일성 (Uniqueness)**: 중복이 없는가?


## 1단계: 데이터 프로파일링 및 기초 분석

데이터의 전반적인 상태를 파악하고 잠재적인 문제를 식별하는 단계임.

### 데이터 품질 리포트 생성

- **기본 정보**: 데이터의 행/열 개수, 메모리 사용량, 변수 타입(수치형/범주형) 등을 확인.

- **결측값 분석**: 각 변수의 결측값 개수와 비율을 계산하여 데이터의 완전성을 평가.

- **수치형 변수 품질 분석**: 각 변수의 평균, 표준편차, 왜도, 첨도 등을 계산하여 분포의 특징을 파악하고, IQR 기법 등으로 이상값 비율을 확인.

```python
# 데이터 기본 정보 확인
print(df.info())

# 결측값 확인
print(df.isnull().sum())

# 수치형 데이터 요약 통계
print(df.describe())
```

### 시각적 품질 진단

- **분포 시각화**: 히스토그램, 박스플롯 등을 사용해 개별 변수의 분포, 중심 경향성, 치우침, 이상값 존재 여부를 시각적으로 확인.

- **상관관계 분석**: 히트맵(Heatmap)을 통해 변수 간의 상관관계를 파악. 이를 통해 변수 간 다중공선성 문제나 예상치 못한 강한 관계를 발견할 수 있음.

```python
import seaborn as sns
import matplotlib.pyplot as plt

# 특정 변수의 분포 시각화 (히스토그램 + KDE)
sns.histplot(data=df, x='age', kde=True)
plt.title('나이 분포')
plt.show()

# 변수 간 상관관계 히트맵
correlation_matrix = df.corr(numeric_only=True)
sns.heatmap(correlation_matrix, annot=True, cmap='coolwarm')
plt.title('상관관계 히트맵')
plt.show()
```

### 규칙 기반 검증

- **비즈니스 규칙 검증**: 도메인 지식에 기반한 규칙을 설정하고 데이터가 이를 만족하는지 검증. (예: 나이는 0 이상, 특정 카테고리 값은 0 또는 1만 가능)

- **논리적 일관성 검증**: 변수 간의 상식적인 관계가 데이터에서도 나타나는지 확인. (예: 범죄율과 주택 가격은 음의 상관관계를 가질 것으로 예상)

```python
# 규칙 1: 방 개수(RM)는 1개 이상이어야 함
invalid_rooms = df[df['RM'] < 1]
if len(invalid_rooms) > 0:
    print(f"방 개수가 1개 미만인 오류 데이터: {len(invalid_rooms)}건")
else:
    print("방 개수 규칙 만족")

# 규칙 2: 찰스강 변수(CHAS)는 0 또는 1만 가능
invalid_chas = df[~df['CHAS'].isin([0, 1])]
if len(invalid_chas) > 0:
    print(f"CHAS 변수 오류 데이터: {len(invalid_chas)}건")
```


## 2단계: 결측값 심층 분석 및 처리

결측값은 분석 결과의 왜곡을 초래할 수 있으므로, 발생 원인을 파악하고 적절히 처리하는 것이 중요함.

### 결측 메커니즘의 이해

- **MCAR (완전 무작위 결측)**: 결측이 다른 어떤 변수와도 관련 없이 무작위로 발생. (예: 시스템 오류)

- **MAR (조건부 무작위 결측)**: 결측이 특정 변수의 값에 따라 조건부로 발생. (예: 고연령층에서 만족도 조사를 더 많이 거부함)

- **MNAR (비무작위 결측)**: 결측값 자체가 의미를 가지는 경우. (예: 고소득자가 소득 정보를 의도적으로 누락함)

```python
# 구매액 분위별로 나이의 결측률이 다른지 확인 (MAR 패턴 추정)
df['spending_quartile'] = pd.qcut(df['avg_order_value'], q=4, labels=False)
missing_rate_by_spending = df.groupby('spending_quartile')['age'].apply(lambda x: x.isnull().mean())
print(missing_rate_by_spending)
```

### 결측 패턴 분석

- **결측값 히트맵/상관관계 분석**: 어떤 변수들에서 결측이 함께 발생하는지, 특정 변수의 값이 다른 변수의 결측과 상관관계가 있는지 시각적으로 분석하여 결측 메커니즘(주로 MAR)을 추정함.

### 결측값 대체(Imputation) 기법 비교

- **단순 대체 (Simple Imputation)**: 평균, 중앙값, 최빈값 등 단일 값으로 모든 결측을 대체. 간단하지만 데이터의 분포를 왜곡할 수 있음.

- **KNN Imputer**: 결측값을 가진 데이터와 가장 가까운 K개의 이웃 데이터들의 평균으로 대체. 데이터의 지역적 패턴을 반영할 수 있음.

- **MICE (Iterative Imputer)**: 다른 변수들을 사용해 회귀 모델을 만들어 결측값을 예측하는 방식. 변수 간의 복잡한 관계를 반영하여 정교한 대체가 가능함.

**대체 품질 평가**: 원본 데이터의 분포(평균, 표준편차)와 변수 간 상관관계를 얼마나 잘 보존하는지를 기준으로 최적의 대체 기법을 선택함.

```python
# 방법 1: 전체 평균으로 대체
df['age'].fillna(df['age'].mean(), inplace=True)

# 방법 2: 더 정교하게 성별, 객실 등급별 평균으로 대체
group_mean_age = df.groupby(['sex', 'pclass'])['age'].transform('mean')
df['age'].fillna(group_mean_age, inplace=True)
```


## 3단계: 이상값 탐지 및 처리

이상값은 모델의 성능을 저하시키고 분석 결과를 왜곡할 수 있으므로, 종합적인 탐지 및 상황에 맞는 처리가 필요함.

### 종합 이상값 탐지 전략

- **일변량 탐지 (IQR)**: 개별 변수 내에서 통계적 범위를 벗어나는 값을 탐지.

- **다변량 탐지**: 여러 변수를 동시에 고려하여 비정상적인 데이터 포인트를 탐지.

    - **마할라노비스 거리**: 변수 간 상관관계를 고려한 거리 측정법. 데이터가 정규분포를 따를 때 효과적.

    - **Isolation Forest**: 데이터를 얼마나 쉽게 고립시킬 수 있는지를 기준으로 이상값을 탐지. 복잡한 데이터 구조에서 효과적.

- **비즈니스 규칙 기반 탐지**: 도메인 지식에 기반하여 논리적으로 불가능한 값을 탐지. (예: 나이 > 130)

**최종 판정**: 여러 탐지 기법에서 공통적으로 지목되거나, 비즈니스 규칙을 명백히 위반하는 데이터를 최종 이상값으로 선정함.

```python
# 'fare' 변수에서 IQR을 이용한 이상치 탐지
Q1 = df['fare'].quantile(0.25)
Q3 = df['fare'].quantile(0.75)
IQR = Q3 - Q1
lower_bound = Q1 - 1.5 * IQR
upper_bound = Q3 + 1.5 * IQR

outliers = df[(df['fare'] < lower_bound) | (df['fare'] > upper_bound)]
print(f"탐지된 이상치 개수: {len(outliers)}")
```

### 이상값 해석 및 처리 전략

- **이상값 특성 분석**: 정상 데이터 그룹과 이상값 그룹의 통계량을 비교하여 이상값의 특징을 파악.

- **이상값 분류 및 맞춤형 처리**:

    - **데이터 오류**: 명백한 오류(예: 나이=999)는 결측 처리 후 재대체하거나 제거.

    - **유효한 극단값 (VIP 고객 등)**: 제거하지 않고 별도 세그먼트로 분리하여 특별 관리하거나, 분석 목적에 따라 윈저화(Winsorization, 특정 백분위수로 값을 제한) 기법을 적용.

    - **휴면 고객**: 특정 기간 동안 활동이 없는 고객은 별도로 '휴면' 상태로 플래그(Flag)하여 관리.

**처리 전후 비교**: 이상값 처리 후 데이터의 분포와 통계량이 어떻게 안정화되었는지 비교하여 처리 효과를 검증함.

```python
# 방법 1: 윈저화 (상위 95% 값으로 제한)
upper_limit = df['fare'].quantile(0.95)
df_winsorized = df.copy()
df_winsorized['fare'] = df_winsorized['fare'].clip(upper=upper_limit)

# 방법 2: 로그 변환 (데이터 분포를 정규분포에 가깝게 만들어 이상치 영향 축소)
import numpy as np
df_log_transformed = df.copy()
df_log_transformed['fare'] = np.log1p(df_log_transformed['fare'])
```
