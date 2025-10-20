import sys

n = int(sys.stdin.readline())

cnt = [0] * 10001

for _ in range(n):
    num = int(sys.stdin.readline())
    cnt[num] += 1

ans = []
for idx, n in enumerate(cnt):
    for _ in range(n):
        print(idx)