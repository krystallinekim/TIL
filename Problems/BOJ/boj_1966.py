from collections import deque
import sys

total = int(sys.stdin.readline())

for _ in range(total):
    n, m = map(int, input().split())
    priority = list(map(int, input().split()))

    queue = deque([(i, v) for i, v in enumerate(priority)])
    count = 0

    while queue:
        now = queue.popleft()
        if any (now[1] < q[1] for q in queue):
            queue.append(now)
        else:
            count += 1
            if now[0] == m:
                print(count)
                break