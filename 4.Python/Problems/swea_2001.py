T = int(input())

for t in range(T):
    
    N, M = map(int, input().split())

    board = [[0] * N for _ in range(N)]

    for i in range(N):
        board[i] = list(map(int, input().split()))

    ans = 0
    for i in range(N - M + 1):
        for j in range(N - M + 1):
            board_sum = 0
            for k in range(M):
                for l in range(M):
                    board_sum += board[i+k][j+l]
            if board_sum > ans:
                ans = board_sum

    print(f'#{t} {ans}')