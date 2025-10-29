# 그래프 탐색

그래프의 완전 탐색 - 그래프의 모든 노드를 전부 방문하되, 한 정점을 2번 방문하지 않음

방문지 집합/리스트를 만들어서 방문 여부를 표현함

시간복잡도상 `visited_list[3] == True`, `3 in visited_set` 둘 다 $O(1)$

## DFS, 깊이 우선 탐색

풀이 방식은 딱 2개(스택/재귀)

그래프/2차원 리스트는 문제가 요구하는 것에 따라 달라짐

### 그래프

- 인접 행렬 / 인접 리스트 2가지 표현법

### 2차원 리스트

- 델타탐색 사용해 이동
- 
### 스택 방식 풀이

인접행렬/리스트 제작이 완료된 상태에서,

1. visited(set/list)
2. stack(1)
    - 첫 노드를 넣어둠
    - 스택이 빌 때까지 반복할 예정
3. current 변수

- 1회차

    1. current = stack.pop() = 1
    2. visited에 1을 넣음
    3. 갈 수 있고(1번 노드의 인접행렬에 2, 3이 써져 있음 -> 인접행렬을 선형탐색), 아직 가지 않은 노드(visited에 없음)를 스택에 넣기 -> 2, 3

    - visited = 1
    - stack = 2, 3

- 2회차

    1. current = stack.pop() = 3
    2. visited에 3을 넣음
    3. 갈 수 있고(3 인접행렬에는 1, 7), 아직 가지 않은 노드 스택에 넣기 -> 7

    - visited = 1, 3
    - stack = 2, 7

- 3회차

    1. current = stack.pop() = 7
    2. visited에 7이 들어감
    3. 7 인접에는 3, 6, 아직 가지 않은 6이 스택에 들어감

    - visited = 1, 3, 7
    - stack = 2, 6

- 4회차

    1. current = stack.pop() = 6
    2. visited에 6
    3. 6 인접에는 4, 5, 7 -> 4, 5가 스택에

    - visited = 1, 3, 7, 6
    - stack = 2, 4, 5

- 5회차
  
    1. current = stack.pop() = 5
    2. visited에 5
    3. 5 인접에는 2, 6 -> 둘다 visited에 있어서 스택에 추가 X

    - visited = 1, 3, 7, 6, 5
    - stack = 2, 4

- 6회차

    1. current = stack.pop() = 4
    2. visited에 4
    3. 4 인접에는 2, 6 -> 둘다 visited에 있어서 추가 X

    - visited = 1, 3, 7, 6, 5, 4
    - stack = 2

- 7회차

    1. current = stack.pop() = 2
    2. visited에 2
    3. 2 인접에는 1, 4, 5 -> 셋 다 visited -> 추가X

    - visited = 1, 3, 7, 6, 5, 4, 2
    - stack = 

스택 방식에서, 경로는 [1, 3, 7, 6, 5, 4, 2] 순으로 이동

```py
# 세팅
stack = [1]                     # 방문예정지(스택)
visited = set()                 # 방문기록지(집합)
trail = []                      # 궤적을 담아줄 리스트

# 그래프 탐색
while stack:                        # 스택이 빌 때까지
    cur = stack.pop()               # 방문과
    if cur not in visited:
        trail.append(cur)
    visited.add(cur)

    for nxt in range(V+1):          # 탐색 반복
        if adj_matrix[cur][nxt] and nxt not in visited:
            stack.append(nxt)

print(trail)
# >> [1, 3, 7, 6, 5, 2, 4]
```

인접 리스트의 경우, 인접행렬을 선형탐색 할 필요 없이 `adj_lst[current]` 자체가 갈 수 있는 리스트 그 자체임

```py
# 그래프 탐색
while stack:                        # 스택이 빌 때까지
    cur = stack.pop()               # 방문과
    if cur not in visited:
        trail.append(cur)
    visited.add(cur)

    for nxt in adj_lst[cur]:        # 탐색 반복
        if nxt not in visited:
            stack.append(nxt)

print(trail)
# >> [1, 3, 7, 6, 5, 2, 4]
```


### 재귀 방식 풀이

스택에 넣는 대신, 함수를 사용

시스템 스택을 이용함

```py
# 세팅
visited = set()
trail = []

# 그래프 탐색
def DFS(cur):
    if cur not in visited:
        trail.append(cur)
    visited.add(cur)

    for nxt in range(V+1):
        if adj_matrix[cur][nxt] and nxt not in visited:
            DFS(nxt)

DFS(1)

print(trail)
# >> [1, 2, 4, 6, 5, 7, 3]
```
DFS(1) -> **DFS(2)**, DFS(3)

- 재귀횟수(n)이 0일 때, DFS(3)부터 안했다고 기록하고 넘어감(시스템 스택에)
- DFS(2)가 먼저 실행

DFS(2) -> **DFS(4)**, DFS(5)

DFS(4) -> **DFS(6)**

DFS(6) -> *DFS(4)*, **DFS(5)**, DFS(7)

DFS(5) -> *DFS(2)*, *DFS(6)*, **DFS(7)**

DFS(7) -> **DFS(3)**, *DFS(6)*

DFS(3) -> *DFS(1)*, *DFS(7)*

- 함수에서 return이 찍힘
- 안돌리고 시스템 스택에 쌓여있는거가 이제 실행됨

DFS(7) -> *DFS(3)*, *DFS(6)*

DFS(5) -> *DFS(2)*, *DFS(6)*, *DFS(7)*

DFS(3) -> *DFS(1)*, *DFS(7)*

경로를 보면, [1, 2, 4, 6, 5, 7, 3] 순으로 이동

스택에서 더 큰 노드쪽을 먼저 탐색한다면, 재귀에서는 작은 노드부터 탐색함

## BFS, 너비 우선 탐색

다 똑같은데, 스택 대신에 큐(deque)를 사용함

1. 1번 노드 탐색 -> 2, 3이 큐에 들어감 (2, 3) => 2, 3은 거리가 1
2. 큐에서는 선입선출, 2가 먼저 큐에서 빠지고, 2번 탐색 -> 4, 5 추가 (3, 4, 5)
3. 3번이 빠지고, 7이 추가 (4, 5, 7) => 2, 3부터 거리가 1 = 1부터 거리가 2
4. 4가 빠지고, 6 추가 (5, 7, 6) => 6은 거리가 3임

```py
from collections import deque

# 세팅
queue = deque([1])
visited = set()
trail = []

# 그래프 탐색
while queue:
    cur = queue.popleft()
    if cur not in visited:
        trail.append(cur)
    visited.add(cur)

    for nxt in range(V+1):
        if adj_matrix[cur][nxt] and nxt not in visited:
            queue.append(nxt)

print(trail)
# >> [1, 2, 3, 4, 5, 7, 6]
```
BFS는 결국 그래프에서 거리를 계산할 때 주로 쓰인다

## 이차원 리스트에서의 탐색

### DFS

visited, stack, cur_r, cur_c가 필요함

stack에는 첫 좌표를 먼저 넣음((0, 0) 튜플)

visited에는 tuple 형태로 좌표를 찍는다

인접행렬을 선형탐색하는게 아니라, 사방탐색(델타탐색)을 이용해서 탐색

갈 수 없는 조건에 문제 조건 + 맵 밖이 추가됨

스택에 가능한 위치들을 하나씩 추가해가는 구조

### BFS

최단 거리를 구하는 알고리즘

똑같이 visited, cur_r, cur_c, deque 필요

일차원에서는 그냥 set나 list(bool)로 visited를 만드는게 가능하지만, 이차원에서는 visited 형태가 이차원 리스트여야 함

- dist 배열 방식

기존 맵을 그대로 사용하는데, 맵에 기존 거리 + 1

## 위상 정렬

순서가 있는 작업을 올바른 순서로 나열하는 알고리즘
