n = int(input())
time = []
for _ in range(n):
    start, end = map(int, input().split())
    time.append((start, end))

time.sort(key=lambda x: (x[1],x[0]))
now = 0
cnt = 0
for start, end in time:
    if start >= now:
        now = end
        cnt += 1

print(cnt)