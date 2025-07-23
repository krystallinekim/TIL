# 1. 분해합 구하는 함수
def digit_sum(numbers):
    for num in str(numbers):
        numbers += int(num)
    return numbers

# 2. 분해합이 N과 같은 수 찾기
def generator(N):
    for num in range(1,N):
        if digit_sum(num) == N:
            return num
    return 0

N = int(input())
print(generator(N))