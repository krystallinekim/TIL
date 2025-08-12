# 변수

변수는 어떤 객체를 **저장**하는데 사용하는 이름 

- 코드블럭에 'apple'이라고 써놓으면 메모리에 올라감 > 휘발성, 다시 사용할 수 없다.

- x라는 변수에 저장(`x = `)해주면 x라는 변수에 'apple'이라는 객체를 넣어줌 > 이제는 휘발성이 아니고, 언제나 참조할 수 있다. 

박스를 생각하면 편하다. 

- x라는 이름이 붙은 박스에 'apple'이라는 물건을 넣어두는 것, 안에 있는 물건을 바꿀 수도 있고, 박스를 다른 박스에 넣을 수도 있다.

**변수 저장은 `=`을 통해 함**

- `type()`를 쓰면 해당 객체의 데이터 타입을 확인할 수 있다.

```py
x = 'apple'
type(x)
# >> str
```


## 변수 연산

객체를 이용한 연산은 모두 변수로 대체해도 진행할 수 있다.

```py
x, y = 1, 2
print(x + y)
# >> 3
print(x - y)
# >> -1
```

str끼리의 연산도 가능함

```py
a = 'Hello'
b = 'Python'

print(a + b)
# >> 'HelloPython`

print(a * 3)
# >> 'HelloHelloHello'
```
## 변수 할당

```py
x = y = 1
```
한 번에 여러 변수에 같은 객체를 할당할 수 있다.

`=` 오른쪽의 객체를 왼쪽 변수에 할당, `y = 1` 다음에 `x = y` 진행

`x = y = 10 = 1` 이건 `10 = 1`을 진행하는거라 에러남

```py
x, y = 1, 2
```
여러 변수에 여러 객체를 한 번에 할당할 수도 있다.

대신 이러면 개수를 정확하게 맞춰줘야 함
```py
x, y = 1
# TypeError: cannot unpack non-iterable int object

x, y = 1, 2, 3
# ValueError: too many values to unpack (expected 2)
```
### 변수명

글자인데, 따옴표가 없으면 전부 변수명으로 인식한다. 위 경우는 x라는 변수에 apple이라는 변수를 저장하려 했는데, apple이라는 변수가 기존에 정의되지 않아 에러가 나는 것.

```py
x = apple
# NameError: name 'apple' is not defined
```

변수명으로 사용할 수 없는 이름들은 다음과 같다.

- 식별자: 이미 파이썬에서 키워드로 쓰려고 정해놓은 문자들

    False, None, True, and, as, if, elif, else, import, ...

    식별자는 실제로 코드에 변수명으로 적용하려 하면 아예 에러가 난다.

    ```py
    import = 'hi'
    print(import)
    # SyntaxError: invalid syntax
    ```

- 내장함수, 모듈 등

    print(), abs(), bool(), len(), ...

    내장함수명들은 코드에 적용하면 에러는 나지 않지만, 대신 앞으로 그 파일에서는 해당 내장함수를 이용할 수 없어진다.

    ```py
    abs = 'hi'
    print(abs)
    # >> 'hi'
    
    abs(-4)
    # TypeError: 'str' object is not callable
    ```
    생각해 보면 당연하다. abs라는 변수명은 이제 함수가 아니라 변수명으로 정의했기 때문에, `'hi'(-4)`를 한 것과 다르지 않음.

변수명은 가능한 한 명확하고 안에 뭐가 들어있는지 알기 쉽게 작성하는 게 맞다.

- 좋은 예시: `char`, `is_odd`, `numbers`, ...

- 나쁜 예시: `a`, `c`, `xx`, ...