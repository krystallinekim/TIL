import math

N = int(input())
numbers = list(map(int, input().split()))

prime_count = 0

for num in numbers:
    if num == 1:
        continue
    for divnum in range(2, round(math.sqrt(num))+1):
        if num % divnum == 0:
            break
    else: prime_count += 1
        
print(prime_count)