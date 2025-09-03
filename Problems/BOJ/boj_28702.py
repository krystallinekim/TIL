n1 = input()
n2 = input()
n3 = input()

def fizzbuzz(ans):
    if ans % 3 == 0 and ans % 5 == 0:
        return 'FizzBuzz'
    elif ans % 3 == 0:
        return 'Fizz'
    elif ans % 5 == 0:
        return 'Buzz'
    else:
        return ans

if n3.isnumeric():
    ans = int(n3) + 1
elif n2.isnumeric():
    ans = int(n2) + 2
elif n1.isnumeric():
    ans = int(n1) + 3
    
print(fizzbuzz(ans))