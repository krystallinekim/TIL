n, k = map(int, input().split())
temp = list(map(int, input().split()))

tmp = max_num = sum(temp[:k])

for i in range(n-k):
    tmp += temp[i+k] - temp[i]
    max_num = max(max_num, tmp)

print(max_num)