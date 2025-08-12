# Str

> 문자열은 변경할 수 없고(immutable), 순서가 있고(ordered), 순회 가능하다.(iterable)

`dir(str)`로 모든 메서드 목록을, `help(str)`로 각 메서드에 대한 설명을 볼 수 있다.

## 문자열 조회/탐색

### `.find(x)`

> 문자열에서 x의 첫 번째 위치(인덱스)를 반환함.

이 때, x는 한 글자 말고도 부분 문자열도 가능함.

만약 문자열 안에 x가 없다면, -1을 반환함

```py
a = 'apple'
a.find('p'), a.find('pl'), a.find('z')
# >> (1, 2, -1)
```

### `.index(x)`

> `.find()`와 완벽하게 같은 기능을 하지만, 문자열 안에 x가 없다면, 에러를 낸다.

```py
a = 'apple'
a.index('p'), a.index('pl')
# >> (1, 2)
a.index('z')
# ValueError: substring not found
```

### `.startswith(x)`, `.endswith(x)`

> 문자열이 x로 시작하거나 끝난다면 True, 그렇지 않다면 False를 반환함

```py
a = 'hello python!'
a.startswith('hell'), a.endswith('python')
# >> (True, False)
```


### 기타 검증 관련 메서드

`.is`가 접두사면 반드시 `T/F`로 결과가 나온다.

글자 관련 메서드들
- `.isalpha()`
    
    문자열이 (숫자가 아닌)글자로만 이루어져 있는가?

-  `.isspace()`

    문자열이 공백으로(\n같은건 아예 문자로 치지 않음) 이루어져 있는가?

-  `.isupper()`
    
    문자열이 대문자로만 이루어져 있는가?

-  `.istitle()`
 
    문자열이 타이틀 형식(문자열 처음과 띄어쓰기 뒤가 모두 대문자)으로 이루어져 있는가? 

-  `.islower()`
    
    문자열이 소문자로만 이루어져 있는가?

숫자 관련 메서드들

- `.isdecimal()`

    문자열이 0~9까지의 수로만 이루어져 있는가?

- `.isdigit()`

    문자열이 숫자(③, ³ 등등, 숫자가 들어간 글자들)로 이루어져 있는가?

- `.isnumeric()`

    문자열을 수로 볼 수 있는가?

    로마자, 분수 같은것도 포함

숫자 관련 메서드들은 decimal < digit < numeric 순으로 범위가 넓어진다

## 문자열 변경

string은 immutable이다. 함수로 str의 내용을 바꾸는 건 불가능하기 때문에, 따로 변수에 저장하지 않으면 변경한 내용은 바로 사라진다.

즉, 아무리 method를 걸어도 원본은 그대로 원래 변수에 존재함

### ★`.replace(old, new[, count])`

설명에서 [] 안쪽은 선택사항임

> `old` 글자를 `new`로 전부 바꾸되, `count`가 있다면 그 개수만큼만 변경

```py
a = 'yaya!'
b = 'woooowoo'

a.replace('y','h')
# >> 'haha!'

b.replace('o','',2)
# >> 'woowoo`
```
여기서 a와 b를 찍어보면 원래 값 그대로 나올 것

### `.strip([chars])`

```py
a = '    hello    '

a.strip()
# >> 'hello'

a.lstrip()
# >> 'hello    '
```
> 양쪽, 혹은 한쪽 공백을 전부 제거함

안에 `chars`를 지정하면 그 문자를 제거한다.

### ★★`.split([chars])`

> 문자열을 특정 단위(기본값은 띄어쓰기)로 나눠서 리스트로 반환함

없는 문자를 세퍼레이터로 쓰면 통째로 리스트에 넣어준다.
```py
a = '나는_배가_부르다'
a.split('_'), a.split('*')
# >> (['나는', '배가', '부르다'], ['나는_배가_부르다'])
```

split을 많이 쓰게 되는 부분은 `input()`값을 받을 때 띄어쓰기로 구분된 숫자를 정수로 받고 싶다던가, csv로 받은 파일을 리스트로 나눌 때 같은 경우 사용하게 된다.

```py
user_input = input().split()
```
붙어있는 문자열을 한글자씩 리스트에 넣고 싶을 때, `.split('')`는 에러를 낸다

```py
a.split('')
# ValueError: empty separator
list(a)
# >> ['나', '는', '_', '배', '가', '_', '부', '르', '다']
```

### ★★`'separator'.join(iterable)`

`join`은 특이하게 separator 뒤에 붙이는 메서드이고, 괄호 안에 붙일 문자열을 넣는다.

> 문자열을 서로 붙여주는데, 그 사이에 separator를 넣는다.

같은 str 사이에서나 컨테이너 안의 원소들에도 사용 가능하다.

```py
word = '안녕하세요'
words = ['안녕', '하세요']

'!'.join(word)
# >> '안!녕!하!세!요'

'!'.join(words)
# >> '안녕!하세요'
```
붙이는 기준은 for에서 임시변수 할당하던 때를 생각해 보자.

str는 하나하나 끊어서 붙이고, list는 요소들 사이를 기준으로 붙여준다.


### 대문자/소문자 관련

- `.capitalize()`

    앞글자를 대문자로 만들어 반환합니다.

- `.title()`

    어포스트로피(*'*)나 공백 이후를 대문자로 만들어 반환합니다.

- `.upper()`

    모두 대문자로 만들어 반환합니다.

- `lower()`

    모두 소문자로 만들어 반환합니다.

- `swapcase()`

    대소문자를 서로 변경하여 반환합니다.

