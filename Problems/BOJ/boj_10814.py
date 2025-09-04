N = int(input())
members = []
for _ in range(N):
    member = input().split()
    member[0] = int(member[0])
    members.append(member)
members.sort(key=lambda x: x[0])
for member in members:
    print(member[0], member[1])