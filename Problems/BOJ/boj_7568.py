n = int(input())
bigs = []

for _ in range(n):
    bigs.append(list(map(int, input().split())))

ans = [1] * n
weight = [x[0] for x in bigs]
height = [x[1] for x in bigs]
for i in range(len(bigs)):
    for j in range(len(bigs)):
        if weight[j] > weight[i] and height[j] > height[i]:
            ans[i] += 1

print(*ans)