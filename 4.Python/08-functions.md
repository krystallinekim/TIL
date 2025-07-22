# Function

함수는 특정한 기능을 하는 코드의 묶음을 말한다.

이름 뒤에 `()`가 붙으면 함수이다.

ex) `print(xxx)`, `.append(xxx)`, ...

이렇게, 파이썬의 기본기능으로 개발자가 선정한 함수들을 내장함수라고 한다.

지금까지 써오던 `print()`, `range()`, `.sort()`, ... 등등이 있다.

## 선언

```py
def function(parameter):
    <body>
    return <output>
```
- `def` 키워드로 시작한다.

- 들여쓰기(4)로 함수의 body(코드블럭)을 작성한다.

    - 함수에 대한 설명을 Docstring으로 할 수 있다.

- 함수 동작 후, 전달할 결과값을 `return`으로 표시한다.

- `함수명()`으로 함수를 호출할 수 있다.

## Output

함수가 반환하는 값을 Output이라 하고, 코드블럭 내에서 `return`으로 표시한다.

- 단, 함수의 output은 값 하나만 반환한다.

    ```py
    def rectangle(width, height):
        area = width * height
        perimeter = (width + height) * 2
        return area, perimeter
    ```

- 이런 함수에서, area와 perimeter 두 개의 값을 반환하는 것처럼 보인다.

- 그러나, 실제로 함수를 돌려보면 결과는 (area, perimeter)처럼 튜플 하나로 반환해주고, 내부 값을 쓰려면 인덱싱이나 값을 따로 받아주는 과정이 필요하다.

함수가 return되거나, 함수가 종료되면 함수를 호출한 곳으로 돌아가 다시 계산하게 된다.

- 그래서 실제로 함수 하나에서 실행되는 return은 하나일 수밖에 없다.

    ```py
    def i_loop():
        while True:
            return 1
    ```

- 여기서 while문은 무한루프를 돌리게 된다. 그러나, return을 만나면 함수가 멈추기 때문에, 실제로 함수를 출력하면 결과값이 1 하나만 나오게 된다.

함수에서  return 뒤에 아무것도 쓰지 않거나 return이 없다면, None을 출력한다.

- 주피터 노트북에서, `print()` 없이 결과가 나오던 코드들이 있다.

    주피터가 마지막으로 실행한 코드의 아웃풋을 보여주려고 하기 때문에, 자동으로 보이던 것

    함수를 썼는데, 결과가 안나오던 것들은 함수에 return이 없기 때문에 나오지 않던 것이다.

    - 주로 결과를 보이려는 함수가 아니라, 원본 데이터를 바꾸려고 하는 함수들에서 보인다.

        `.append()`, `.sort()`, `.remove()` 등등이 아웃풋이 존재하지 않는(None인) 함수들이다.


## Input

```py
def func(x):
    return x + 2

func(2)
# >> 4
```

### `parameter`

`x`는 매개변수 `parameter`라고 한다.

값이 정해지진 않았지만, 함수 내부에서 활용할 수 있는 변수이다

함수 정의 부분에서 씀

### `argument`

`2`는 전달인자 `argument`라고 한다.

실제로 함수에 전달되는 값이다

함수 호출 시 씀

### 위치인자

인자를 여러 개 줄 때, 인자의 구분을 위치에 따라서 한다.

```py
def func(x, y):
    return x - y

func(1, 2)
# >> -1
func(2, 1)
# >> 1
```
### 기본인자

함수 정의 시, 기본값을 지정해 호출 시 인자 수가 적어도 호출 될 수 있다.

일단 기본인자값을 매개변수에 넣어놓고, 실제 변수값을 지정하는 식

```py
def my_sum(a, b=0):
    # b = 0
    # a = 3
    # b = 5
    return a + b

my_sum(3)
# >> 3
my_sum(3, 5)
# >> 8
```

기본인자값을 가지는 인자 뒤에 일반 인자를 넣는 건 안된다.

```py
def greeting(name='john', age):
    return f'{name}은 {age}살입니다.'

greeting(20)
# >> SyntaxError: parameter without a default follows parameter with a default
```

- 컴퓨터가 `20`이 name을 비워놓고 age만 쓴 것인지, name에 썼는데 age를 안 쓴 것인지 구분 불가능함

기본인자는 여러개 와도 됨

### 키워드 인자

함수 호출 시, 직접 변수명을 지정해 전달할 수 있다.

```py
def greeting(age, name, address, major):
    return f'{name}은 {age}살입니다. 전공은 {major}, 주소는 {address}입니다.'

greeting(30, 'mary', major='design', address='선릉')
# >> 'mary은 30살입니다. 전공은 design, 주소는 선릉입니다.'
```
키워드 인자를 쓰고, 뒤에 위치인자를 넣어주는 건 불가능하다.

```py
greeting(major='경영', age=24, '서울', '철수')
# >> SyntaxError: positional argument follows keyword argument
```
- 기본인자 뒤에 일반인자를 넣었던 것처럼, 컴퓨터가 '서울', '철수' 를 어디에 넣어야 할지 모르기 때문

### 가변인자

함수 설명에 `*args`라고 나오는 부분

print()를 쓸 때, 아무리 많은 값을 `,`로 구분해 넣어도 전부 출력해줬다.

개수가 정해지지 않은 임의의 인자를 받을 때, 함수 정의 시 `*args`를 넣어 나머지를 전부 받아준다.

```py
def my_max(*nums):
    max_num = nums[0]
    
    for num in nums:
        if num > max_num:
            max_num = num

    return max_num

my_max([1, 2, 3, 4, 3, 2, 1])
# >> 4
```
이 때, 받은 가변인자는 **튜플 형태**로 들어온다. -> 인덱싱 가능하고, for문 등에 돌릴 수 있다는 것


### 가변 키워드 인자

함수 설명에 `**kwargs`라고 나오는 부분

가변인자가 `tuple`형태로 처리되는 것처럼, 이건 `dict` 형태로 처리된다.

쓸일이 잘 없다


## Scope

스코프는 **공간**이라고 이해하는 쪽이 좋다.

함수를 하나 만들면, 그 함수 내부의 `local scope`(지역 스코프)라는 공간과, 그 함수 외부의 `global scope`(전역 스코프)라는 공간으로 나뉘게 된다.

지역 스코프에 있는 변수를 지역변수, 전역 스코프에 있는 변수가 전역변수가 됨
```py
# global
x = 'global'

def func():
    # local
    print(x)
    # >> global
    x = 'local'

func()
# >> local
print(x)
# >> global
```
전역변수는 코드 어디서나 정의되어 참조 가능하지만, 지역변수는 딱 함수 내부에서만 참조가 가능하다.

x를 `'local'`로 지역 스코프에서 정의하더라도, 함수가 끝나면 지역스코프가 사라져 다시 x는 `'global'`로 정의된다.

스코프의 지역/전역 관계는 파이썬에서 딱 함수에서만 정의된다.

```py
n = 100

for n in range(3):
    pass

print(n)
# >> 2
```
for문의 임시변수는 실제로 n값에 0, 1, 2를 저장하기 때문에, 맨 처음의 `n = 100`은 덮어씌워진다.

### 변수의 수명(lifecycle)

변수의 이름에는 각각 수명이 존재한다.

- built-in: 파이썬이 실행되고 나서부터, 영원히 유지

    주로 파이썬 기본 함수들, `print()`, `sum()`, `max()` 등등

- global: 모듈이 호출된 시점 / 이름이 선언된 시점부터, 파이썬을 끄기 전까지 유지

    일반적인 우리가 지정하는 변수명 / 함수명 등등

- local: 함수가 호출된 시점 생성되고, 함수가 종료될 때까지 유지

    함수에서 임시로 사용하게 될 변수들

### 이름 검색 규칙

파이썬에서 사용하는 이름(식별자)들은 전부 namespace에 저장된다.

이름을 검색할 때, 작은 범위부터 큰 범위 순으로 찾아가게 된다.

Local(함수 안) -> Enclosed(상위함수) -> Global(전역) -> Built-in(기초)

enclosed는 함수 안에 함수가 정의될 때 상위 함수

이제 왜 변수명으로 print, sum 등을 사용하면 안되는지 알 수 있다.

이미 전역변수로 print를 썼다면, built-in에 있는 print를 더이상 불러오지 않기 때문

## 재귀함수

함수 내부에서 자신을 호출하는 함수

- 팩토리얼 계산

- 피보나치 수열


주피터 노트북에서, 재귀함수는 자체적으로 1000줄에서 끊어버린다.

### 재귀함수와 반복문

간단한 재귀함수들은 사실 그냥 반복문으로 나타낼 수도 있다.




재귀함수를 주로 사용하는건 미로찾기 알고리즘 같은데서 많이 씀


## 함수 응용

### map(function, iterable)

> 순회 가능한 데이터 구조의 모든 요소에 function을 적용 후, 그 결과를 돌려준다.

이 때, return을 `map_object`형태로 한다. 리스트랑 비슷하게 적용 가능함

```py
s = '12345'

l = []
for char in s:
    l.append(int(char))

print(l)
# >> [1, 2, 3, 4, 5]

list(map(int, '12345'))
# >> [1, 2, 3, 4, 5]
```

여기서 적용할 함수에는 파이썬 기본 함수도 가능하지만, 직접 만든 함수도 넣을 수 있다.

```py
def cube(n):
    return n ** 3

numbers = [1, 2, 3]
list(map(cube, numbers))
# >> [1, 8, 27]
```

`map()`을 가장 많이 사용할 곳은 입력값 정리할 때 사용한다.

입력값을 스페이스로 구분해서 주는 곳도 많기 때문.

```py
numbers = ['1', '2', '3']

list(map(int,numbers))
# >> [1, 2, 3]
```


### `filter(function, iterable)`

> iterable에서 function의 반환된 결과가 `True` 인 것들만 구성하여 반환합니다.

즉, 결과값이 `True`인 원본 데이터들을 반환한다는 것.

`map()`처럼, 결과값은 `filter_object`로 나와서 따로 리스트로 만들어 줘야 볼 수 있다.

이 때 필터를 적용할 function은 결과값이 True / False로만 나오는 함수여야 한다.

```py
def is_odd(n):
    return n % 2 == 1

list(filter(is_odd, range(10)))
# >> [1, 3, 5, 7, 9]
```

필터를 잘 활용하면 SQL에서 하던 데이터 필터링을 파이썬에서도 할 수 있다.

```py
members = [
    {'name': 'aaa', 'age': 20, 'gender': 'F'},
    {'name': 'bbb', 'age': 15, 'gender': 'M'},
    {'name': 'ccc', 'age': 24, 'gender': 'M'},
    {'name': 'ddd', 'age': 11, 'gender': 'F'},
    {'name': 'eee', 'age': 65, 'gender': 'M'},
    {'name': 'fff', 'age': 34, 'gender': 'F'},
    {'name': 'ggg', 'age': 8, 'gender': 'F'},
    {'name': 'hhh', 'age': 28, 'gender': 'M'},
]

def is_male(member):
    return member['gender'] == 'M'

list(filter(is_male, members))
# >> [{'name': 'bbb', 'age': 15, 'gender': 'M'},
#     {'name': 'ccc', 'age': 24, 'gender': 'M'},
#     {'name': 'eee', 'age': 65, 'gender': 'M'},
#     {'name': 'hhh', 'age': 28, 'gender': 'M'}]
```

map, filter 함수에서, 함수의 인자, 즉 값으로 다른 함수가 들어가는 것을 볼 수 있다.

이것은 함수가 1급 객체(First Class Object)이기 때문임

1. 변수에 저장할 수 있는가
    ```py
    a = int
    print(a('123'))
    ```
2. 함수에 인자로 넣을수 있는가
    ```py
    map(int, '123')
    ```
3. 함수의 return 값으로 나올 수 있는가
    ```py
    def a():
        return int
    a()('123')
    ```

### lambda

map이나 filter에서, 한번만 쓰는 임시 함수를 만들어서 정의하는게 너무 귀찮다.

단순한 역할을 하는 함수에 대해, 이름을 정의하지 않고 쓰자고 만든 함수가 lambda함수

만드는 방법은 다음과 같다.

```py
def func(x):
    return x + 1
func(9)
# >> 10
```
이런 원본 함수가 있을 때,

1. def를 빼고, lambda라고 쓴다
    ```py
    lambda func(x):
        return x + 1
    ```
2. 이름과 소괄호를 지우고, 콜론 뒤 엔터도 지운다(코드블록을 없앤다)
    ```py
    lambda x: return x + 1
    ```
3. return을 지운다
    ```py
    lambda x: x + 1
    ```
4. 함수를 실행한다
    ```py
    (lambda x: x + 1)(9)
    # >> 10
    ```

람다함수는 이름이 없는 것 뿐이지, 함수의 모든 걸 할수 있다. 

특히, 인자로 함수가 들어가야 할 때 간단한 함수를 직접 작성해 편리하게 이용할 수 있음.

```py
def is_odd(n):
    return n % 2 == 1

list(filter(is_odd, range(10)))
# >> [1, 3, 5, 7, 9]

list(filter(lambda n: n % 2 == 1, range(10)))
# >> [1, 3, 5, 7, 9]
```