# 순열과 조합

## 순열

n개 데이터 중 m개를 뽑아 줄을 세우는 모든 경우의 수

### for 반복문으로 구현
```py
arr = ['A', 'B', 'C']

for i in range(3):
    for j in range(3):
        for k in range(3):
            if i != j and j != k and k != i:
                print([arr[i], arr[j], arr[k]])
```
시간복잡도가 깔끔하게 $O(N^n)$이 된다.

### 재귀함수를 통한 구현

따로 사용했는지를 확인하기 위해 check 배열을 하나 만들어줘서 관리하면 된다.

```py
arr = ['A', 'B', 'C']  # 재료 리스트
sel = [0, 0, 0]  # 인형뽑기 selection
check = [0, 0, 0]  # 뽑을지 말지 결정하는 리스트


def perm(depth):  # 각자 뎁스에서는? 
    if depth == 3:  # 최고 뎁스에 도달했으면? # if depth == len(sel)
        print(sel)  # print
        return

    for i in range(3):  # 3번의 화살표 떨어트릴 기회
        if not check[i]:  # 각 기회 안에서 check를 보고 멈출 수 있는지 보고?
            check[i] = 1  # 멈출 수 있다면 if 통과했으니까 자리 차지했다고 표시하고
            sel[depth] = arr[i]  # 화살표가 떨어진 위치의 재료리스트
            perm(depth+1)
            check[i] = 0  # 돌아나오면서 다시 다음을 위해 초기화!! (중요)

perm(0)
```

check 배열에는 앞줄에서 채워둔 값이 있는지를 확인하게 된다.

앞쪽부터 차례대로 채워가면서 되는지 안되는지 확인하는거에 가까움. 이걸 재귀함수가 하나씩 깊이를 늘려가면서 푸는 것처럼 구현

## 조합

n개 데이터 중 m개를 뽑는 모든 경우의 수

칸막이처럼 생각할 수 있음

### for 반복문으로 구현
```py
# 그냥 조합 2개뽑기
arr = ['A', 'B', 'C']
for i in range(3):
    for j in range(i+1, 3):
        print(arr[i], arr[j])

# 중복조합 2개뽑기
arr = ['A', 'B', 'C']
for i in range(3):
    for j in range(i, 3):
        print(arr[i], arr[j])
```

### 재귀함수를 통한 구현

```py
# 5C3
arr = ['A', 'B', 'C', 'D', 'E']
sel = [0, 0, 0]

def combination(idx, sidx):
    if sidx == 3:  # sel 길이 범위를 벗어나면 sel이 확정됐다는 소리니까 print
        print(sel)
        return

    if idx == 5:  # 얘도 벗어나지 않아야 함
        return

    sel[sidx] = arr[idx]  # sidx가 가리키는 위치에 idx가 가리키는 재료를 넣음
    combination(idx+1, sidx+1)  # 첫번째로는 두개의 화살표가 동시에 오른쪽으로 가보고
    combination(idx+1, sidx)  # 두번째로는 arr 쪽 화살표만 혼자 가봄.


combination(0, 0)
```

## 백트래킹

깊이 우선 탐색을 기반으로 한 알고리즘으로 경로 탐색 중 유효하지 않은 경로에 대한 탐색을 중단하는 알고리즘이다.

유효한 경로까지 되돌아가 다른 경로를 탐색한다.

유효하지 않은 경로는 탐색하지 않기 때문에 효율적으로 탐색할 수 있는 알고리즘이다.

순열에서 check 배열을 이용해 불가능한 경우의 수를 체크하지 않고 넘어가던게 백트래킹 기법

## N-Queen 문제

1. depth로 내려가면서, 좌대각/우대각에 퀸이 있으면 패스함
    
    현재깊이(2) - 체크할 깊이(0, 1) 빼서 그만큼 체크배열에서 차이가 있는 자리에 퀸이 있는지 확인
    ```py
    T = int(input())

    for tc in range(1, T+1):
        N = int(input())
        arr = [0] * N
        answer = 0

        def deploy_queens(current_depth):
            global answer
            if current_depth == N:
                answer += 1
                return

            for i in range(N):
                arr[current_depth] = i
                for check_depth in range(current_depth):
                    if arr[check_depth] == arr[current_depth]:  # 열 검사부터
                        break
                    if (current_depth - check_depth) == abs(arr[current_depth] - arr[check_depth]):  # 대각선 검사
                        break
                else:
                    deploy_queens(current_depth+1)

        deploy_queens(0)
        print('#{} {}'.format(tc, answer))
    ```
2. 좌우는 depth에서 애초에 체크해주므로, 상하 / 좌대각 / 우대각 3가지만 체크하면 됨

    좌대각의 칸들은 인덱스 차이가 같고, 우대각 칸들은 인덱스 합이 같은 성질을 이용함

    심지어 인덱스 차가 마이너스여도 알아서 파이썬 리스트에서 뒤로 돌아가서 체크해줌 -> 좌대각/우대각 체크리스트의 길이가 같아짐
    ```py
    import sys
    input = sys.stdin.readline

    N = int(input())
    ans = 0

    check_vertical = [False] * N                            # 상하 체크
    check_diagonal_left = [False] * (2 * N - 1)             # 왼쪽 대각선 체크
    check_diagonal_right = [False] * (2 * N - 1)            # 오른쪽 대각선 체크

    def n_queen(depth):
        global ans

        if depth == N:                                      # 기저 사례: depth가 N까지 도달하면 성공!
            ans += 1                                        # 정답에 1 추가하고 리턴
            return

        for i in range(N):                                  # 해당 depth에서 하나씩 둬보며
            if check_vertical[i] or check_diagonal_left[depth - i] or check_diagonal_right[depth + i]:
                continue                                    # 상하 or 왼쪽 대각선 or 오른쪽 대각선에 퀸이 있다면 패스

            check_vertical[i] = True                        # 없다면 각각 체크하고
            check_diagonal_left[depth - i] = True
            check_diagonal_right[depth + i] = True

            n_queen(depth + 1)                              # 다음 depth로 이동

            check_vertical[i] = False                       # depth에서 나올 때 체크 풀어주기(중요)
            check_diagonal_left[depth - i] = False
            check_diagonal_right[depth + i] = False

    n_queen(0)                                              # 항상 0번 depth에서 시작

    print(ans)
    ```