apart = [[0] * 15 for _ in range(15)]

for i in range(15):
    for j in range(0, 15):
        if j == 0:
            apart[i][0] = 1
        if i == 0:
            apart[0][j] = j + 1
        else:
            apart[i][j] = apart[i-1][j] + apart[i][j-1]

T = int(input())
for _ in range(T):
    k = int(input())
    n = int(input())

    print(apart[k][n-1])
