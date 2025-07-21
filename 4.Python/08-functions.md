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
