L = int(input())

words = input()
base = '_abcdefghijklmnopqrstuvwxyz'
ans = 0
r = 31
M = 1234567891

for i in range(L):
    ans += (base.find(words[i])) * (r ** i)
    
print(ans % M)