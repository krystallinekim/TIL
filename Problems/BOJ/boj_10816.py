n = int(input())
cards = map(int, input().split())
m = int(input())
nums = map(int, input().split())

d = {}
for key in cards:
    d[key] = d.get(key, 0) + 1

ans = []
for num in nums:
    ans.append(str(d.get(num, 0)))

print(' '.join(ans))