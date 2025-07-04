# 구글 스프레드시트

액셀과 비교해 몇가지 차이가 있다

|액셀|스프레드시트
|---|---
|오프라인에서만 사용이 가능|온라인일때만 사용 가능
|파일을 공유하려면 저장 후 파일채로 보내야 함|바로 온라인 상에서 공유 가능 -> 협업이 매우 쉽다
|더 복잡한 통계 함수가 사용 가능함|
| |범위에서 행/열 전체를 지정 가능함|
| |실시간 온라인 데이터 연동이 가능한 함수가 사용 가능 (googlefinance, googletranslate 등)
| |요즘은 AI연계가 좋아져서 데이터를 그대로 피벗테이블로 만들어주는 기능도 있다

## 스프레드시트의 한계
- 데이터의 처리가 복잡하다
- 쿼리로 하면 간단하더라


## 기본 조작


### 데이터의 형식

크게 텍스트와 숫자로 나눌 수 있다

`=TYPE()`함수에서 1은 숫자, 2는 텍스트

**텍스트**
- 왼쪽으로 정렬
- 기본적으로 숫자가 아닌 모든 것
- =TEXT(숫자,서식) #서식은 따옴표 안에 패턴을 지정

**숫자**
- 오른쪽 정렬
- 안에 날짜, 통화, 퍼센트 등이 포함되어 있다
- 숫자끼리는 서로 계산할 수 있다

### 절대/상대참조 `$`

일반적으로 스프레드시트의 참조는 상대참조로 설정

덕분에 복사/붙여넣기 시 알아서 참조 범위를 바꿔준다

절대참조를 이용하면 특정 값/행/열을 고정 > 그 부분은 복사해도 참조 위치가 바뀌지 않는다

`F4`를 통해 편하게 이용 가능

## 핵심 함수

### 기본 통계 함수

```bash
평균: =AVERAGE(범위)
합계: =SUM(범위)
개수: =COUNT(범위) / =COUNTA(범위) # COUNTA는 텍스트 개수를 셀 수 있음
최댓값/최솟값: =MAX(범위) / =MIN(범위)
중간값: =MEDIAN(범위)
```

- 범위를 계산할 때 엑셀과는 다르게 C2:C처럼 열 전체를 더할 수도 있다

### 조건부 함수

```bash
조건부 판단: =IF(조건, 참일때값, 거짓일때값)
중첩 조건:  =IFS(조건1, 값1, 조건2, 값2, TRUE, 기본값)
조건부 개수: =COUNTIF(범위, 조건)
조건부 합계: =SUMIF(범위, 조건, 합계범위)
복합 조건: =COUNTIFS(범위1, 조건1, 범위2, 조건2)
```

### 텍스트 처리
```bash
텍스트 결합: =CONCATENATE(A2,B2) 또는 =A2&B2
왼쪽 / 오른쪽 문자: =LEFT(텍스트, 글자수) / RIGHT(텍스트, 글자수)
중간 문자: =MID(텍스트, 시작위치, 글자수)
```

### 기타
```bash
고유값: =UNIQUE(범위) # 결과물이 행/열로 나오기 때문에, if함수와 조합해서 계산하는 것도 가능함
배열 간 계산: =ARRAYFORMULA(계산) # 범위 단위의 계산을 할 수 있게 해준다
```

## 데이터의 정제

1. 데이터 중복의 삭제
    - countif 등을 이용해 카테고리 내에 중복된 게 있는지 확인 등
        ```bash
        =if(countif(C2:C,C2)>1,"중복있음","")
        ```
    - 스프레드시트 기본 기능인 데이터 - 데이터 정리 - 중복항목 삭제 등으로 한번에 가능
        

1. 공백 제거

    - 공백이 있는 경우 자꾸 에러가 난다
        ```bash
        빈 셀 확인: =ISBLANK(셀)
        빈 셀 개수: =COUNTBLANK(범위)
        ```
    - if문으로 공백이 있을 경우 "익명", "공백" 등으로 교체도 가능(새로운 테이블을 만들어 버릴 경우) `=if(isblank(C2),"익명",C2)`


1. 데이터 정제

    - 보통 들어오는 데이터는 무질서한 경우가 많다
        ```bash
        텍스트 정제: =SUBSTITUTE(원본, "찾을텍스트", "바꿀텍스트")
        숫자 변환: =VALUE(SUBSTITUTE(SUBSTITUTE(E2,"₩",""),",",""))
        형식 통일(전화번호): =LEFT(J2,3)&"-"&MID(J2,4,4)&"-"&RIGHT(J2,4)
        ```
    - 이걸 `substitute()`로 하나하나 바꾸거나 `left`/`mid`/`right` 등을 써서 정리하는데는 한계가 있음
    - 보통 정규표현식 등 이용 (LLM한테 정제를 시키고 정리하면 편하다)



1. 필터

    - 데이터를 특정 카테고리 기준으로 정렬
    - 원본 스프레드시트에서 필터를 걸어서 보면 모두가 필터가 걸린채로 보임
    - 필터 보기를 통해 특정 시점에서의 필터링된 결과를 보여줄 수 있다
    - 필터 함수
        ```bash
        동적 필터: =FILTER(범위, 조건)
        복합 조건: =FILTER(A2:D8, (B2:B8="조건1")*(C2:C8="조건2"))
        ```
    - 실질적으로 SQL이나 파이썬에서는 필터 함수를 이용해 분류하게 된다.

1. 이상값 탐지

    - 평균값에서 많이 튀는 값을 찾음 -> 표준편차 사용
    - 데이터에서 평균과 표준편차, 그리고 각 데이터에 대한 Z-Score(편차/표준편차)를 구함
    - 데이터에서, Z점수의 절댓값이 2(신뢰구간 약 95%)나 2.5(신뢰구간 약 99%)보다 큰 값을 정제
    ```bash
    Z-Score 계산: =(개별값-평균)/표준편차
    이상값 판정: =IF(ABS(Z점수)>2.5,"이상값","정상")
    ```

1. 이상값 처리
    - 이상값 행 삭제 #진짜 값이 튀었을 때 위험함
    - 평균값으로 대체 #이미 오염되었을 수 있는 데이터를 그대로 사용한다는 약점
    - 중간값으로 대체 #데이터 오염의 위험을 최소화
    - 윈저화(Winsorizing) #데이터의 극단값을 잘라내는 대신, 일정 경계값으로 대체
        ```bash
        =PERCENTILE(범위, 퍼센트) #전체 값 중에서 n% 위치에 있는 값을 반환
        ```
    - 등등등

### 데이터 정제 -> 예측 모델을 만든다 = 결국 머신러닝이 하는 일

## 에러 처리 및 검증

`#N/A!` # 값이 없을 때 생김
- `=IFERROR(VLOOKUP(...),"값없음")`

`#DIV/0!` # 빈칸, 혹은 0으로 나누려고 시도할 때 생김

- 데이터에서 빈칸이 생기는 경우가 자주 있으므로 볼 일이 많다
- `=IF(B1=0,"계산불가",A1/B1)`

`#REF!` # 참조 위치가 잘못되었을 경우 생김

`#NAME?` # 함수명을 잘못 입력했을 때, 혹은 값 앞에 =가 잘못 들어가면 생김


### 데이터 검증

원본 데이터 백업 및 정제 과정의 문서화는 필수
- 데이터 시트 그대로 쓰는 것이 아닌, 시트 복사를 적극적으로 이용

계산 결과 검증

---

# [스프레드시트 기초](https://docs.google.com/spreadsheets/d/1l-SyxknefP-Q5HGLKcNUxFW2119xtt59KtYfkyoNJPY/edit?gid=644782730#gid=644782730)