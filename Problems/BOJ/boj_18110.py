n = int(input())
if n:
    lst = []

    for _ in range(n):
        lst.append(int(input()))

    lst.sort()
    cut = int(n * 0.15 + 0.5)
    if cut:
        ans = sum(lst[cut:-cut]) / (n - 2 * cut)
    else:
        ans = sum(lst) / n
    print(int(ans + 0.5))
else:
    print(0)