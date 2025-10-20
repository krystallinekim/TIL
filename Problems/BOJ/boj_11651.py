import sys
input = sys.stdin.readline

n = int(input())
coords = []

for i in range(n):
    coords.append(list(map(int, input().split())))

coords.sort(key=lambda x: (x[1], x[0]))

for coord in coords:
    print(coord[0], coord[1])