import sys

n, m = map(int, sys.stdin.readline().split())

dms = set()
bms = set()

for _ in range(n):
    dms.add(sys.stdin.readline().split()[0])
    
for _ in range(m):
    bms.add(sys.stdin.readline().split()[0])

dbj = dms & bms

print(len(dbj))
for name in sorted(list(dbj)):
    print(name)