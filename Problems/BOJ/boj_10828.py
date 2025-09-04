import sys

n = int(sys.stdin.readline())
stack = []

for _ in range(n):
    order = sys.stdin.readline().rstrip()
    key = order[:2]
    if key == 'pu':
        num = int(order.split()[1])
        stack.append(num)
    if key == 'po':
        if stack:
            print(stack.pop())
        else:
            print(-1)
    if key == 'si':
        print(len(stack))
    if key == 'em':
        if stack:
            print(0)
        else:
            print(1)
    if key == 'to':
        if stack:
            print(stack[-1])
        else:
            print(-1)