K = int(input())
lst = []

for i in range(K):
    x = int(input())
    if x:
        lst.append(x)
    else:
        lst.pop()

print(sum(lst))