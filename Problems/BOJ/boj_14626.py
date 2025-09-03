isbn = input()
missing_idx = isbn.find('*')
isbn_sum = 0

for i in range(13):
    if isbn[i] == '*':
        continue
    isbn_sum += int(isbn[i]) * (i % 2 * 2 + 1) 

for k in range(10):
    if (isbn_sum + k * (missing_idx % 2 * 2 + 1)) % 10 == 0:
        print(k)
        break