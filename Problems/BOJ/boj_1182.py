n, s = map(int, input().split())
nums = list(map(int, input().split()))

ans = 0

for i in range(1, 1 << n):
    sum_nums = 0
    for j in range(n):
        if i & (1 << j):
            sum_nums += nums[j]
    if sum_nums == s:
       ans += 1

print(ans) 
