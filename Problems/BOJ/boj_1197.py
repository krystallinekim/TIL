v, e = map(int, input().split())
edges = []
for _ in range(e):
    x, y, w = map(int, input().split())
    edges.append([x, y, w])

edges.sort(key=lambda x: x[2])
cnt = 0
p = list(range(v+1))
rank = [0] * (v+1)
answer = 0

def find(x):
    if p[x] != x:
        p[x] = find(p[x])
    return p[x]

def union(x, y):
    root_x, root_y = find(x), find(y)

    if root_x == root_y:
        return False

    if rank[root_x] < rank[root_y]:
        p[root_x] = root_y
    elif rank[root_x] > rank[root_y]:
        p[root_y] = root_x
    else:
        p[root_x] = root_y
        rank[root_y] += 1

    return True

for x, y, w in edges:
    if union(x, y):
        answer += w
        cnt += 1
        
    if cnt == e-1:
        break

print(answer)