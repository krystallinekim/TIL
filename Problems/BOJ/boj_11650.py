N = int(input())
nums = []

for i in range(N):
    nums.append(list(map(int, input().split())))
    
nums.sort()

for num in nums:
    print(num[0], num[1])