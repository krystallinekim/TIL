# 기초 알고리즘

## 완전탐색 알고리즘
brute force

컴퓨터 연산량을 믿고 다 때려넣는 방법

초당 1억번 정도 연산이 가능해서 사실 그냥 이걸로 푸는게 편할수도 있다

다만, N번 계산하는 반복문을 한번만 중첩해도 시간복잡도는 $O(N^2)$

N이 10만정도 되고부터는 이 방법으로 안풀리기 시작함

## 투 포인터 알고리즘

start, end 포인터(left, right도 가능) 2개를 두고, 이걸 이동시켜가면서 계산함

예를 들어, 합이 5 이상인 경우의 수를 찾을 때, 합이 5보다 작으면 end를 옮기면서 합을 늘리고, 5를 넘어가면 start 포인터를 옮기면서 합을 줄여가는 것

포인터가 뒤로 안돌아가서 시간복잡도가 $O(N)$

같은 문제를 조합으로 풀면 경우의 수를 모두 계산해야 해서 $O(N^2)$

```py
nums = [1, 2, 3, 4, 2, 5, 3, 1, 1, 2]

s = e = 0                       # 좌/우 포인터
tmp = 0                         # 임시합을 담아줄 변수
cnt = 0                         # 경우의 수를 세어줄 변수

while True:
    if tmp < 5:                 # 임시합이 5보다 작은데
        if e >= 10:             # 더 더해줄 숫자가 없다면 로직 종료
            break
        else:                   # 있다면
            tmp += nums[e]      # e포인터 숫자 더해주고
            e += 1              # e포인터 옮기기

    elif tmp > 5:               # 임시합이 5보다 크다면
        tmp -= nums[s]          # s포인터 숫자 빼주고
        s += 1                  # s포인터 옮기기

    else:                       # 임시합이 5와 동일하다면
        cnt += 1                # 숫자 세주고
        tmp -= nums[s]          # s포인터 숫자 빼주고
        s += 1                  # s포인터 옮기기

print(cnt)
```
코드 보면서 직접 포인터 옮겨가면서 푸는게 편함

## 누적합 알고리즘

### 구간합 문제

### 슬라이딩 윈도우

```py
nums = [3, 5, 1, 4, 2]

tmp = max_num = sum(nums[:3])       # 첫 번째 구간합 구하기

for idx in range(2):                # 첫 번째는 이미 구했으므로
    tmp += nums[idx+3] - nums[idx]  # 새로운 구간합은 양 옆에서 빼고 더해주면 된다(슬라이딩 윈도우)
    max_num = max(max_num, tmp)     # 갱신

print(max_num)
```

### 누적합 알고리즘

처음부터 i번째 숫자까지 더한 구간합을 이용함
```py
nums = [3, 5, 1, 4, 2]

acc_nums = [0]
for num in nums:
    acc_nums.append(acc_nums[-1]+num)

print(acc_nums)
# [0, 3, 8, 9, 13, 15]
```
## 함수와 재귀함수

- 파이썬의 LEGB

    `global`, `nonlocal` -> 함수 안에 함수를 넣어야 할 때, 변수 재할당 시 사용 -> 많이 헷갈림

- set의 id 자르기
    
    이건 나중에 dfs같은데서 씀

### 메모이제이션

계산한 결과값을 캐싱해서 시간복잡도를 크게 개선

### 이진탐색 알고리즘

**정렬된** 배열에서 원하는 데이터를 찾아내는 알고리즘

left, right, mid(l+r//2)

원하는 데이터가 l-m / m-r 사이 어디에 있는지 보고, 나머지 반대를 날림

이제는 m이 left, r은 그대로 -> 다시 mid를 구하고, 원하는 데이터와 비교하고, 나머지를 날림

이걸 반복하다보면 계속 구간이 줄어들면서 검색, 언젠가 같아지면 결론

선형탐색에서, $O(N)$ -> 10개의 데이터를 10번 보는것

이진탐색에서는 10개의 데이터를 3번만 봐도 확인 가능함 -> $O(logN)$(엄밀히 말해서 $O(log_2N)$)

- 일단 정렬된 결과에서만 사용 가능 -> 선형탐색에서는 $O(N)$, 정렬($O(NlogN)$) + 이진($O(logN)$)

    -> 기본적으로 정렬된 것(시간, 높이, 길이, ...)이 존재함 -> 여기에 이진검색을 걸어버리면 된다.(나무자르기 문제같은거)

재귀함수 스타일로 풀던가
```py
nums = [1, 2, 3, 4, 5, 6, 7, 8, 9]

def binary_search(low, high, target):
    if low > high:                                      # 탐색 배열의 길이가 0보다 작아지면 탐색 종료(실패)
        return '찾지 못함'

    mid = (low + high) // 2                             # 배열의 가운데 수 집기

    if target == nums[mid]:                             # 일치하면 탐색 종료(성공)
        return mid
    elif target < nums[mid]:                            # 오른쪽 절반 덜기
        return binary_search(low, mid-1, target)
    elif target > nums[mid]:                            # 왼쪽 절반 덜기
        return binary_search(mid+1, high, target)

print(binary_search(0, len(nums)-1, 7))
```

while 반복문으로 풀면 된다
```py
nums = [1, 2, 3, 4, 5, 6, 7, 8, 9]

def binary_search(low, high, target):

    while low <= high:                  # 탐색 배열의 길이가 0보다 작아지면 탐색 종료(실패)
        mid = (low + high) // 2         # 배열의 가운데 수 집기

        if target == nums[mid]:         # 일치하면 탐색 종료(성공)
            return mid
        elif target < nums[mid]:        # 오른쪽 절반 덜기
            high = mid-1
        elif target > nums[mid]:        # 왼쪽 절반 덜기
            low = mid+1

    return '찾지 못함'

print(binary_search(0, len(nums)-1, 7))

```



## 정렬 알고리즘
안정 정렬 <-> 불안정 정렬

안정은 정렬했을 때 순서가 유지되는 정렬, 불안정은 순서가 유지되지 않는 정렬

버블, 카운팅, 병합 정렬은 안정 정렬, 선택, 퀵 정렬은 불안정 정렬

파이썬에서 `sort(key=lambda)`를 이용하면, 해당 함수에 대해 정렬 후 나머지는 **안정 정렬**을 한다

### 버블 정렬

### 카운팅 정렬

사실 집계만 해도 정렬이 된다

여기까지 하면 불안정 정렬

누적합 이용 -> 순서대로 뽑아서 안정 정렬이 되게 할 수 있다

누적합 = 몇번째 숫자인지

## 스택/큐/데크

### stack

선입후출, 쌓인 무더기라고 생각할 수 있음

나중에 들어온 데이터가 가장 먼저 빠짐

리스트에서 `.append()`로 넣고, `.pop()`으로 빼면 된다 - 그냥 리스트로 구현이 쉬움

둘다 $O(1)$이므로, 매우 빠름

### queue

선입선출, 대기줄이라고 생각하면 편함

먼저 들어온 데이터가 먼저 빠짐

똑같이 $O(1)$인 `.append()`로 넣지만, `.pop(0)`은 $O(N)$의 시간복잡도를 가진다

### deque

deque는 큐를 구현하기 위한 클래스

double-linked list -> 배열의 양 끝에서 다 데이터 산입/추출 가능

대신 탐색이 $O(N)$이 걸린다

데이터를 뺄 때 `.popleft()`로 뺄 수 있음