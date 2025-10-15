N = int(input())
for _ in range(N):
    ps = input()
    
    is_vps = True
    while True:
        after = ps.replace('()', '')
        if after == '':
            break
        if ps == after:
            is_vps = False
            break
        ps = after
    
    print('YES' if is_vps else 'NO')