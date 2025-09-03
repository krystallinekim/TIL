N = int(input())

count = 1

for i in range(1,N+1):
    if count >= N:
        print(i)
        break
    count += 6 * i