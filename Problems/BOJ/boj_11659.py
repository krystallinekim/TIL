import sys

n, m = map(int, sys.stdin.readline().split())
nums = list(map(int, sys.stdin.readline().split()))

s = [0]
for num in nums:
    s.append(s[-1] + num)

for _ in range(m):
    i, j = map(int, sys.stdin.readline().split())
    print(s[j] - s[i-1])