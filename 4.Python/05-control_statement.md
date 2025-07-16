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

임시변수를 활용

for <임시변수> in <컨테이너>:
    <코드블럭>

시작할 때 몇번 돌지가 정해짐 -> 될 때 까지 돌아간다는게 불가

### 반복 제어

#### break
