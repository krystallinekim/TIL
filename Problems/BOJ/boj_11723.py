import sys

m = int(input())

s = 0

for _ in range(m):
    cal = sys.stdin.readline()
    
    if cal[:2] == 'ad':
        num = 1 << int(cal.split()[1])-1
        s |= num
    if cal[:2] == 're':
        num = 1 << int(cal.split()[1])-1
        s &= ~num
    if cal[:2] == 'ch':
        num = 1 << int(cal.split()[1])-1
        print(1) if s & num else print(0)
    if cal[:2] == 'to':
        num = 1 << int(cal.split()[1])-1
        s ^= num
    if cal[:2] == 'al':
        s = (1 << 21) -1
    if cal[:2] == 'em':
        s = 0