num = input()
while int(num):
    if num == num[::-1]:
        print('yes')
    else:
        print('no')
    num = input()