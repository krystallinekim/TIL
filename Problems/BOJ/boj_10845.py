from collections import deque
import sys

n = int(sys.stdin.readline())
queue = deque()

for _ in range(n):
    order = sys.stdin.readline().rstrip()
    key = order[:2]
    if key == 'pu':
        num = int(order.split()[1])
        queue.append(num)
    if key == 'po':
        if queue:
            print(queue.popleft())
        else:
            print(-1)
    if key == 'si':
        print(len(queue))
    if key == 'em':
        if queue:
            print(0)
        else:
            print(1)
    if key == 'fr':
        if queue:
            print(queue[0])
        else:
            print(-1)
    if key == 'ba':
        if queue:
            print(queue[-1])
        else:
            print(-1)
