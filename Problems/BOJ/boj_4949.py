while True:
    word = input()
    
    if word == '.':
        break
   
    check = ''
    for w in word:
        if w in ['(', ')', '[', ']']:
            check += w

    is_balanced = True
    while True:
        tmp = check.replace('()', '').replace('[]', '')
        if tmp == '':
            break
        elif tmp == check:
            is_balanced = False
            break
        else:
            check = tmp
            
    print('yes' if is_balanced else 'no')
        
