N = int(input())
sizes = list(map(int, input().split()))
T, P = map(int, input().split())

Tbundle = 0

for size in sizes:
    Tbundle += (size // T) + 1
    if size % T == 0:
        Tbundle -= 1

print(Tbundle)

Pbundle, Psingle = divmod(N, P)
print(Pbundle, Psingle)