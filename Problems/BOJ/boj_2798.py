n, m = map(int, input().split())
cards = list(map(int, input().split()))

max_num = 0

for i in range(0, n-2):
    for j in range(i+1, n-1):
        for k in range(j+1, n):
            card3 =  cards[i] + cards[j] + cards[k]
            if card3 <= m:
                max_num = max(max_num, card3)
                
print(max_num)