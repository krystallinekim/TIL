# 범용 이상치 탐지 함수

import numpy as np
import pandas as pd
from scipy.spatial import distance
from scipy.stats import chi2
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import IsolationForest

def outlier_detection(df: pd.DataFrame, chi_q=0.999, iso_contamination='auto', final_threshold=2):
    """ 범용 이상치 탐지 함수 """
    
    if df.isna().values.any():
        print('결측치 확인, 제거 후 다시 실행해주세요')
        return
    
    print('=== 종합 이상값 탐지 ===')
    df_copy = df.copy()
    numeric_data = df_copy.select_dtypes(include=['number'])
    
    # 1. IQR 이상값(일(단)변량 이상값 탐지) - 변수 하나를 가지고 확인
        # 특정 컬럼 안에서 다른 값들에 비해 값이 이상한 걸 탐지함
    print('\n1. 일변량 이상값 탐지(IQR법)')
    univariate_outliers = pd.DataFrame(index=df_copy.index)
    
    for col in numeric_data.columns:
        Q1 = df_copy[col].quantile(0.25)
        Q3 = df_copy[col].quantile(0.75)
        IQR = Q3 - Q1
        lower_bound = Q1 - 1.5 * IQR
        upper_bound = Q3 + 1.5 * IQR
        
        outliers_mask = (df_copy[col] < lower_bound) | (df_copy[col] > upper_bound)
        univariate_outliers[col] = outliers_mask
        
        outlier_count = outliers_mask.sum()
        if outlier_count:
            print(f'  {col}: {outlier_count}개 ({outlier_count/len(df_copy) * 100:.1f}%)')

    # 2. 마할라노비스 거리 기반 다변량 이상값
        # 변수 간의 스케일 차이와 상관관계까지 고려해 이상값을 확인
        # 특정 컬럼이 이상한게 아니라, 종합적으로 데이터 행 하나하나가 이상한가를 보여준다
        # 데이터가 정규분포를 따를 때 유용함(데이터가 대부분 정상으로 보이지만, 이상값을 찾고 싶을 때 사용) - 대놓고 이상한 데이터가 한가득일 때는 제대로 작동 안함. 
    print('\n2. 다변량 이상값 탐지(마할라노비스 거리)')
    
    # 변수 간 스케일 차이 변환
        # standard scaler - 모든 데이터를 표준편차 1, 평균 0으로 바꿔버림
    scaler = StandardScaler()
    scaled_df = pd.DataFrame(
        scaler.fit_transform(numeric_data),
        columns=numeric_data.columns,
        index=numeric_data.index,
    )
    # 데이터 평균 벡터
    mean = scaled_df.mean().values
    # 공분산 행렬
    cov_matrix = np.cov(scaled_df, rowvar=False)
    # 공분산 행렬의 역행렬
    inv_cov_matrix = np.linalg.pinv(cov_matrix)
    
    # 마할라노비스 거리 계산
    mahalanobis_dist = scaled_df.apply(lambda row: distance.mahalanobis(row, mean, inv_cov_matrix), axis=1)
    
    # 이상치 기준점(threshold) 지정 (카이제곱 분포 -> 정상값을 몇 퍼센트(95% / 99% / 99.9%)까지 인정할 수 있는가)
    threshold = chi2.ppf(chi_q, len(numeric_data.columns)) ** 0.5
    mahalanobis_outliers = mahalanobis_dist > threshold
    print(f'  임계값: {threshold:.2f}')
    print(f'  마할라노비스 거리 이상값: {mahalanobis_outliers.sum()}개 ({mahalanobis_outliers.mean() * 100:.1f}%)')
    
    # 3. Isolation Forest 기반 다변량 이상값
        # 랜덤한 기준으로 구분하는 독립된 Tree 여러개를 만들어서 얼마나 빨리 고립되는지를 확인하는 과정
        # 데이터 간의 관계가 복잡해 이상치가 복잡하게 숨어있을 때 이용함 - 매우 공격적으로 이상치를 검출, 대놓고 수상한 데이터들을 잘 골라준다
    print('\n3. 다변량 이상값 탐지(Isolation Forest)')
    # contamination = 전체에서 얼마나 이상값 비율이 있을것인가 예측. 'auto'로 알아서 고르라 할 수 있다.
    iso_forest = IsolationForest(contamination=iso_contamination, random_state=42)
    iso_outliers = iso_forest.fit_predict(scaled_df) == -1
    iso_scores = iso_forest.score_samples(scaled_df)
    print(f'  Isolation Forest 이상값: {iso_outliers.sum()}개 ({iso_outliers.mean() * 100:.1f}%)')    
    
    # 4. 비즈니스 규칙(특화) 이상값
        # 굳이 복잡한 방법을 쓰지 말고, 각 컬럼별로 이상값 기준을 정해준 뒤 그 기준에 맞지 않으면 이상값으로 판정(사람은 130살 이상 살기 힘듦)
        # 각 데이터마다 전용 기준이 필요함
        
    # print('\n4. 비즈니스 규칙 기반 이상값 탐지')
    # business_outliers = (
    #     (df['age'] >130) |
    #     (df['days_since_last_purchase'] < 0) |
    #     (df['avg_order_value'] < 0)
    # )
    # print(f'  비즈니스 규칙 이상값: {business_outliers.sum()}개 ({business_outliers.mean() * 100:.1f}%)')
    
    # 종합 판정
    outlier_summary = pd.DataFrame({
        'IQR': univariate_outliers.sum(axis=1) > 0,
        '마할라노비스 거리': mahalanobis_outliers,
        'Isolation Forest': iso_outliers,
        # '비즈니스': business_outliers,
    })
    
    outlier_summary['총이상값수'] = outlier_summary.sum(axis=1)
    outlier_final = outlier_summary['총이상값수'] >= final_threshold
    print(f'\n == 최종 이상값: {outlier_final.sum()}개 ({outlier_final.mean() * 100:.1f}%)')
    
    return outlier_summary