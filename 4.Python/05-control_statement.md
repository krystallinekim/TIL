# 제어문

코드를 위에서 아래로 순차적으로 수행하는 것 뿐이 아니라, 코드를 선택적으로 실행(조건)하고 특정 부분을 계속 실행(반복)하고 싶을 때가 있다.

이걸 코드 실행의 순차적인 흐름을 제어한다고 하고, 이 흐름을 제어하기 위한 문구를 control statement라고 한다.

## Condition(조건)

참/거짓을 판단할 필요가 있을 때, `if`구문을 이용한다.

```py
if <조건1>:
    <조건1이 True일 경우 실행할 코드블럭>
elif <조건2>:
    <조건1이 False, 조건2가 True일 경우 실행할 코드블럭>
else:
    <위의 조건이 모두 False일 경우 실행할 코드블럭>
```

가장 중요한 건 코드블럭 앞에 들여쓰기를 해야 한다는 점이다.

들여쓰기가 없으면 코드블럭이라고 인식을 못함

### `if`

```py
num = 2
if num % 2:
    print('odd')
else:
    print('even')
# >> 'even'
```

- if 뒤의 조건이 True라면 아래의 코드블럭을 실행하고, False라면 실행하지 않고 넘어간다.

    else는 위의 조건이 전부 False일 때 else 밑의 코드블럭을 실행하게 된다.

- 조건에는 Bool Type가 True/False이기만 하면 되기 때문에, `[]`, `0`, `1`, `num % 2`(계산식) 도 들어갈 수 있다.

### `elif`

```py
score = 75
if score > 90:
    print('A')
elif score > 70:
    print('B')
elif score > 50:
    print('C')
else:
    print('F')
# >> 'B'
```

- 조건이 2개가 넘어갈 경우는 `elif`를 이용한다.

- 기본적으로 위에서부터 계산하고, 한 가지 조건에 들어가면 나머지는 스킵하기 때문에 가장 위에 가장 작은 범위의 조건을 걸어주어야 한다.


### 조건 표현식

```py
# 조건 표현식
<true_value> if <조건식> else <false_value>

# if로 나타냈을 경우
if <조건식>:
    <true_value>
else:
    <false_value>
```
보통, 조건식을 쓸 때 if-else 하나씩만 존재하고, 맞을경우/틀릴경우 결과 하나씩만 출력하고 싶은 경우가 매우 많다.

이걸 간단하게 줄인 게 조건 표현식

조건 표현식을 3항 연산자라고 부르기도 한다.

- 1항: `a = not True`에서 `not`에는 `True`라는 한가지 항만 필요함

- 2항: `b = 1 + 2`에서 `+`에는 `1`, `2`라는 2가지 항이 필요함

- 3항: `<true_value>`, `<조건식>`, `<false_value>` 총 3가지 항이 필요

조건표현식은 연산자이기 때문에, 나온 답 (true_value / false_value) 둘 중 하나는 어딘가에 저장할 수도 있다.

```py
num = -10
value = num if num >= 0 else -num
print(value)
# >> 10
```

## Iteration(반복)

반복에는 크게 2가지 방법이 있음.

`while`은 조건을 직접 걸어주는 반복이고, `for`은 알아서 조건을 지정해주는 방식이다.

`while`은 수동변속, `for`를 오토라고 생각하면 편함

### `While`

```PY
while <조건식>:
    <코드블럭>
```
`while`는 조건식이 참인 경우 코드블럭 파트를 반복적으로 실행한다.

조건식을 잘못 설정할 경우, 무한 루프에 빠지기도 함.

```py
while True:
    print('loop')
# 컴퓨터가 멈출 때까지 'loop'만 계속 출력한다.
```

보통 조건을 설정할 변수를 하나 만들거나 한다.

```py
n = 0
while n < 5:
    print(n)
    n += 1
# >> 0 1 2 3 4
```
위 코드를 한줄씩 정리해 보면, 

- n = 0, n이 5보다 작으므로 n을 출력하고 1을 더해서 저장함
- n = 1, n이 5보다 ...
- ...
- n = 5, n이 5보다 작지 않기 때문에 반복문을 종료


시작할 때 몇번 돌지 정해져 있지 않고, 가변적으로 조절할 수 있다.

```py
user_input = ''

# 사용자가 정답을 맞추기 전에는 While이 끝나지 않는다
while user_input != '1q2w3e4r!':
    print('암호를 입력하세요')
    user_input = input('암호를 입력하세요')
    print(user_input)

print('정답')
```

### `for ... in`

while과 다르게, 반복문을 시작할 때 임시 변수를 정해놓고 시작한다.

```py
for <임시변수> in <컨테이너>:
    <코드블럭>
```

- 보통 <컨테이너> 위치에는 시퀀싱 가능한 컨테이너, str이나 list, range가 주로 들어감

    시퀀싱 가능한 컨테이너에서 **순서를 지켜서** 하나씩 임시변수에 저장하고, 임시변수에 더이상 저장할 수 있는 값이 없을 때 반복문을 중단시킴

    특히, range는 n번 돌리고 싶을 때 `for _ in range(n)` 식으로 사용하는 경우가 많다.(`_`는 이 임시변수를 코드블럭에 사용하지 않겠다는 표시)

- 대신 for는 시작할 때 최대 몇번 돌지가 정해지기 때문에, while처럼 조건이 맞을 때까지 계속 돌린다는 식으로 코드를 짤 수 없다.


#### 순회

`for`은 str이나 list에서 순회를 돌릴 때 유용하게 사용할 수 있다.

`while`처럼 idx값을 따로 설정할 필요 없이, 바로 원소를 꺼내서 작업할 수 있다.

```py
nums = [1, 2, 3, 4, 5]
idx = 0

while idx < len(nums):
    nums[idx] *= 10
    idx += 1

print(nums)
# >> [10, 20, 30, 40, 50]
```
`while`에서는, 인덱스를 통해 리스트의 원소에 접근 가능함
```py
nums = [1, 2, 3, 4, 5]

for num in nums:
    num *= 10

print(nums)
# >> [1, 2, 3, 4, 5]
```

그런데, `for`에서 리스트의 원소를 가지고 순회를 돌리면 원본 데이터에 접근이 불가능함

위 코드에서 `num`은 임시변수라서 원본 데이터의 복사본이기 때문

```py
nums = [1, 2, 3, 4, 5]

for i in range(len(nums)):
    nums[i] *= 10  # for문으로 인덱스에 접근해서 수정할 수 있다

print(nums)
# >> [10, 20, 30, 40, 50]
```

`while`에서 했던 것 처럼, 인덱스를 통하면 원본 데이터에 바로 접근이 가능하다.

딕셔너리도 하나씩 꺼내서 순회를 돌릴 수 있다(딕셔너리가 그러라고 만든게 아니긴 함)

딕셔너리에서 순회를 돌리면 key만 나온다. value는 그 key를 통해 접근
```py
grades = {'john':  80, 'eric': 90}

for key in grades:
    print(key)
# >> john eric

for key in grades:
    print(grades[key])
# >> 80 90
```
`.items()`를 통해 key와 value에 한번에 접근이 가능하긴 하다.

`.items()`가 리스트 안에 키와 밸류를 튜플로 묶어놓은 형태라는걸 기억하자

```py
grades = {'john':  80, 'eric': 90}

for key, val in grades.items():
    print(key, val)
# >> john 90 eric 80

for item in grades.items():
    print(item)
# >> ('john', 90) ('eric', 80)
```

#### `enumerate()`

리스트의 인덱스와 값을 같이 빼주는 내장함수

```py
members = ['민수', '영희', '철수']
print(list(enumerate(numbers)))
# >> [(0, '민수'), (1, '영희'), (2, '철수')]
```
`.items()`처럼, 인덱스와 값을 다른 변수에 빼서 for문으로 뽑아낼 수 있다.
```py
members = ['민수', '영희', '철수']

for i, member in members:
    print(i, member)
# >> 0 민수 1 영희 2 철수
```

#### **List Comprehension**

빈 리스트에 규칙을 가지고 반복해서 추가할 일이 매우 많이 생긴다.

```py
cubic_list = []

for i in range(3):
    cubic_list.append((i + 1) ** 3)

print(cubic_list)
# >> [1, 8, 27]
```

이걸 간단하게 한 줄로 표시해 코드를 줄일 수 있다.

```py
cubic_list = [(i + 1) ** 3 for i in range(3)]
print(cubic_list)
# >> [1, 8, 27]
```

리스트에 `(i + 1) ** 3`을 반복적으로 채우는데, 반복 횟수는 `range(3)`이고, `i`는 `range(3)`의 요소라는 뜻이다.

0이나 기본값으로 배열을 채울 때 잘 쓴다.

코드블럭의 로직이 간단할 때만 사용 가능하고, 그걸 좀 간단하게 채웠다고 생각하면 편하다.

간단한 2차원 배열을 만들 때도 쓸 수 있다..

```py
board = [[0] * 10 for _ in range(10)]
print(board)
# 10 * 10짜리 0으로 채워진 2차원 리스트가 나온다.
```
좀 더 빡세게 쓰면 다음과 같다.
```py
board = [[0 for _ in range(10)] for _ in range(10)]
```

list comprehension에서는, 뒤에 간단한 if문을 넣을 수 있다.

```py
print([x for x in range(1, 30) if x % 2])
# >> [1, 3, 5, 7, 9, ... , 29]
```

#### Dictionary Comprehension

list comprehension과 비슷하다.

```py
cubic_dict = {}

for i in range(1, 4):
    cubic_dict[i] = i ** 3

print(cubic_dict)
# >> {1: 1, 2: 8, 3: 27}
```
이것과 같은 작동을 하는게 다음과 같다.
```py
cubic_dict = {i: i ** 3 for i in range(1, 4)}
print(cubic_dict)
# >> {1: 1, 2: 8, 3: 27}
```
사실 딕셔너리를 만들 때는 그냥 일반식으로 하는 경우가 많음

### 반복 제어

반복 제어는 주로 if문과 반복문을 조합해 사용하는 경우가 많다.

특정 조건 하에 반복을 중단하거나 하고싶을 때 사용함.

#### `break`

반복문을 끊어버리고 다음으로 넘어간다.

break가 등장하면, 남은 반복 횟수나, 반복문 안에 남은 코드를 전부 무시하고 반복문 다음 코드를 실행하게 된다.

`while True:`같은 무한반복도 중간에 끊을 수 있다.

```py
n = 0
while True:
    print(n)
    n += 1
    if n == 3:
        break
# >> 0 1 2
```

for문에서도 break를 사용할 수 있다.

#### `continue`

반복문은 유지하지만, 현재 회차를 넘기고 다음 회차로 넘어간다.

```py
for i in range(6):
    if i % 2 == 0:
        continue
    print(i)
# >> 1 3 5
```
continue가 등장할 경우, 반복문 안에 남은 코드는 전부 무시하지만, 반복문의 다음 회차로 넘어가서 돌리기 시작한다.

#### `pass`

아무것도 하지 않는다.

if문이나 반복문 등, 코드블럭이 있다면 어디서든지 사용 가능하다.

들여쓰기를 해서 코드블럭이 생성된 경우, 만약 아무것도 코드블럭에 없다면 에러가 난다. 이럴 경우, `pass`는 자리를 채우는 용도로 사용할 수 있다.

주로 코드 구성을 짤 때, 미리 자리를 채워놓기 위해 쓴다. 완성된 코드에서는 볼 일이 없는게 맞음

#### `else`

반복문을 전부 실행한다면 실행됨

만약 반복문 안에 break가 있고, 그게 실행되지 않았을 경우에 else가 실행된다.

break가 없는 구문에서는 항상 실행되므로 의미없는 구문이 된다.

```py
numbers = [1, 3, 7, 9]

for num in numbers:
    if num == 4:
        print('True')
        break
else:
    print('False')

# >> False
```