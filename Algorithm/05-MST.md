# MST
최소신장트리, Minimum Spanning Tree

그래프에서 먼저 트리를 골라옴

트리를 골라온다는건 전체 그래프에서 전체 노드(V개)에 따른 간선(V-1개)을 트리 형태가 되도록 선택해야 한다는 것

최소신장 = 간선의 가중치의 합이 최소가 되게 선택하는 경우

즉, 노드는 전부 고르되, 간선은 가중치의 합이 최소가 되게 선택하는 트리가 MST

구현방법은 2가지 정도 있음 - 크루스칼/프림

둘다 시간복잡도도 똑같아서 택1하면 됨

## 프림 알고리즘

그리디 알고리즘에 속함

> 현재 상황에서 최선을 선택하다보면 전체의 최선이 되는 것 -> 부분 최적의 결과가 전체의 최적이 되는 것임

1. distance 배열을 만든다
    목표 노드까지 도달하는데 필요한 거리, 일단 무한대로 초기화 [∞, ∞, ∞, ∞, ∞]
2. 첫번째 노드(0번)은 시작점, distance는 0 -> [0, ∞, ∞, ∞, ∞]
3. 시작점 -> 이어진 노드의 간선 중, 이동가능한 간선에 거리를 최신화 -> [0, 2, 10, ∞, ∞]
4. 0번 기준으로, 방문하지 않고, 거리가 최소인 노드(1번)로 이동, 거리정보를 최소값으로 갱신 -> [0, 2, 10, 8, 1]
5. 0-1 연결 기준으로, 방문하지 않고, 거리가 최소인 노드(4번)으로 이동, 거리정보 갱신 -> [0, 2, 3, 5, 1]
6. 0-1-4 기준으로 방문하지 않고, 거리가 최소인 노드(2번)으로 이동

- 여기서 distance 배열에서 최소값을 고를 때 heap으로 구함(시간절약부분)
- 시작점이 어디던지 상관없이, 항상 MST는 똑같이 나온다.

```py
n = 5
edges = [[5,2,6],[3,1,2],[2,3,5],[2,4,6]]	

from heapq import heappush, heappop

# dist를 전부 ∞로 통일
inf = float('inf')
dist = [inf] * (n+1)

# 그래프의 인접 리스트
adj = [[] * (n+1) for _ in range(n+1)]
for x, y, w in edges:
    adj[x].append((y, w))
    adj[y].append((x, w))

# 1번 노드에서 시작함
heap = [(0,1)]
dist[1] = 0
visited = set()

while heap:
    w, cur = heappop(heap)
    
    # heap에 다른데서 구한 긴 길이의 간선이 있을 때 계산하지 않고 넘어감
    if w > dist[cur]:
        continue
    
    # visited에 지금 찍은 노드를 추가
    visited.add(cur)
    
    # 인접리스트에서
    for y, w in adj[cur]:
        # 간선에 연결된 노드가 간적 없고 / 가중치가 기존 거리보다 짧을 경우
        if y not in visited and dist[y] > w:
            # 길이를 최신화하고
            dist[y] = w        
            # heap에 (가중치, 노드)를 추가함
            heappush(heap, (w, y))
            # 그럼 다음 heappop시 가장 가중치가 짧은 노드를 계산

print(dist[1:])
```
## 다익스트라 알고리즘
= 최단 거리 알고리즘

시작점 -> 종점까지의 최단 거리를 찾는 알고리즘

기본적으로, 간선의 가중치가 양수여야 함

프림이랑 거의 똑같은데, 가중치를 최신화하는게 아니라 가중치를 더해줌

가중치가 음수라면, 그리디 알고리즘으로 잡아낼 수가 없어진다.

이럴때는 벨만-포드같은걸 써주면 됨


## 유니온-파인드 알고리즘

여러 노드가 존재할 때, 두 노드가 같은 집합에 속해 있는지 확인하고(Find 연산),

다른 집합에 속해 있는 두 노드를 하나의 집합으로 묶어주는(Union 연산) 알고리즘이다.

두 노드가 한 트리에 있는지 없는지 확인한다는 건 결국, 그래프에서 사이클이 생기지 않게 이어주는 것

1. Make-Set 연산 (초기상태 세팅)

    - 우선 각각의 노드들의 대표는 자기 자신이라고 가정한다.


    - P(Parent) 배열은 각각 노드들의 대표 노드가 어떤 노드인지를 나타낸다.
        ```py
        parent = list(range(n + 1))
        ```

2. Union 연산 (부모-자식 관계 생성)
    - 노드의 관계를 이어준다.
    - C의 부모 노드가 A가 된다.
        ```
        union(x, y) ⇒ p[y] = x
        ```
    - 이후 트리의 랭크를 기반으로 Union을 한다면 더욱 최적화 할 수 있다.
    - 트리 랭크는 작은걸 큰거 아래에 붙이는게 맞음
    ```py

    def union(x, y):
        x_root, y_root = find(x), find(y)

        if x_root == y_root:
            return False

        # union by rank
        if rank[x_root] > rank[y_root]:
            parent[y_root] = x_root
        elif rank[x_root] < rank[y_root]:
            parent[x_root] = y_root
        else:
            parent[x_root] = y_root
            rank[y_root] += 1  # 랭크가 같은 것끼리 합한 후에는 랭크 + 1을 해준다.

        return True
    ```
    - 단, x의 부모노드와 y의 부모노드가 같다면 union하면 안됨 -> Find 연산을 돌려야 함

3. Find-Set 연산 
    - D의 부모 노드는 B이며 B의 부모 노드는 A를 재귀적으로 찾아가는 과정
    - 이후 Path Compression (경로압축) 과정을 거쳐 더욱 최적화 할 수 있다.(트리 차수를 줄여줌)
    ```py
    def find(x):
        if x != parent[x]:
            return find(parent[x]) # 일반적인 경우
            parent[x] = find(parent[x])  # path compression

        return parent[x]
    ```
    - 여기서 find 결과가 같다면 서로 union 불가임

## 크루스칼 알고리즘

분리집합 알고리즘을 응용한 최소신장트리 구현

시간복잡도 : $O(ElogE)$ - E는 간선의 개수

순수하게 간선 정보만 가지고 사이클이 생기는지 안생기는지 확인

1. 간선 가중치가 낮은 순서로 정렬
2. 사이클이 생기지 않도록 하나씩 뽑되, 트리를 구성할 수 있게 되는 순간 중단한다.

    그래서 노드가 4개라면 3개의 간선까지만 뽑으면 된다.

    사이클이 생기지 않는걸 유니온-파인드 알고리즘으로 판별

```py
import sys
input = sys.stdin.readline

def find(x):
    if x != parent[x]:
        parent[x] = find(parent[x])  # path compression

    return parent[x]


def union(x, y):
    x_root, y_root = find(x), find(y)

    if x_root == y_root:
        return False

    # union by rank
    if rank[x_root] > rank[y_root]:
        parent[y_root] = x_root
    elif rank[x_root] < rank[y_root]:
        parent[x_root] = y_root
    else:
        parent[x_root] = y_root
        rank[y_root] += 1  # 랭크가 같은 것끼리 합한 후에는 랭크 + 1을 해준다.

    return True

n, m = map(int, input().split())
parent = list(range(n + 1))
rank = [0] * (n + 1)
edges = []
total, counts = 0, 0

for _ in range(m):
    a, b, c = map(int, input().split())  # 정점a, 정점b, 가중치c
    edges.append((c, a, b))  # 가중치를 먼저 넣어서, 가중치 기준 정렬 되도록 함

edges.sort()  # 크루스칼 알고리즘을 위한 가중치 기준 정렬

for c, a, b in edges:
    if union(a, b):  # 간선을 이으면(같은 집합에 넣으면)
        total += c  # 가중치 더하기
        counts += 1

        if counts == n - 1:
            break  # 트리의 특성 상, 간선의 개수는 (정점 - 1) 이므로 이에 도달하면 종료

print(total)
```