import sys

n = int(sys.stdin.readline())

nums = []
for _ in range(n):
    nums.append(int(sys.stdin.readline()))
    
for num in sorted(nums):
    print(num)