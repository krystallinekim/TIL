nums = list(map(int, input().split()))

for i in range(1, min(nums)+1):
    if nums[0] % i == 0 and nums[1] % i == 0:
        gcd = i
        
print(gcd)

print(nums[0] * nums[1] // gcd) 