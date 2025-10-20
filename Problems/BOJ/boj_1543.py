doc = input()
find = input()

s, e = 0, len(find)
cnt = 0
while True:
    print(doc[s:e])
    if doc[s:e] == find:
        cnt += 1
        s += len(find)
        e += len(find)
    else:
        s += 1
        e += 1    
    
    if e > len(doc):
        break
    
print(cnt)
