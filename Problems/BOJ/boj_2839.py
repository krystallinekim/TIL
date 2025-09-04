N = int(input())
bag = 0

while N > 15:
    N -= 5
    bag += 1

if N in [11, 13, 15]:
    print(3 + bag)
elif N in [12, 14]:
    print(4 + bag)
elif N in [3, 5]:
    print(1)
elif N in [6, 8, 10]:
    print(2)
elif N in [9]:
    print(3)
else:
    print(-1)