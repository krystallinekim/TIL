import sys

n = int(sys.stdin.readline())
if n == 0:
    print(0)
    sys.exit()

lst = [int(sys.stdin.readline()) for _ in range(n)]
lst.sort()

cut = int(n * 0.15 + 0.5)
if cut:
    ans = sum(lst[cut:-cut]) / (n - 2 * cut)
else:
    ans = sum(lst) / n

print(int(ans + 0.5))