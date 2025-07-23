N, M = map(int, input().split())

matrix_A = []
for _ in range(N):
    row = list(map(int, input().split()))
    matrix_A.append(row)
matrix_B = []
for _ in range(N):
    row = list(map(int, input().split()))
    matrix_B.append(row)

matrix_sum = [[0] * M for _ in range(N)]
for i in range(N):
    for j in range(M):
        matrix_sum[i][j] = matrix_A[i][j] + matrix_B[i][j]
        
for row in matrix_sum:
    print(*row)
