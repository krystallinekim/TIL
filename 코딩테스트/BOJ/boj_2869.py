import math

climb, drop, height = map(int, input().split())

day = math.ceil((height - climb)/(climb - drop))+1
print(day)