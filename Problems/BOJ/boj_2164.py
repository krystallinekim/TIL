n = int(input())

from collections import deque

cards = deque(range(1, n+1))

while len(cards) != 1:
    cards.popleft()
    cards.rotate(-1)

print(cards[0])