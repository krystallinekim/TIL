N = int(input())
score = list(map(int, input().split()))
avg_now = sum(score)/N
max_score = max(score)
avg_new = sum(map(lambda x: x * 100 / max_score, score)) / N
print(avg_new)