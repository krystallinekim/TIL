n, k = map(int, input().split())
ans = []
circle = list(range(1, n+1))

i = k - 1
while circle:
    ans.append(circle[i])
    circle.remove(circle[i])
    i += (k - 1)
    l = len(circle)
    
    while i >= l and l != 0:
        i -= l

print(f'<{', '.join(map(str, ans))}>')