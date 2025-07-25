# Methods

데이터 값들, 관계 등에 적용할 수 있는 명령어들

길어져서 string, list에 관련된 methods는 분리함

## Tuple

> 튜플은 순서가 있지만(ordered), 불변(immutable) 자료형이다.

값이 바뀌지 않기 때문에, 값을 변경하는 메서드는 아예 의미가 없고, 순서를 조회하는 메서드만 지원한다.

### `.count(x)`

> 튜플에서 x가 나오는 횟수를 반환한다.

리스트의 `.count(x)`와 같다.

### `.index(x)`

> 튜플에 있는, 첫 번째 x의 인덱스를 반환함

역시 리스트에서 보던 `index()`와 같다.

## Set

> 셋은 순서가 없고(unordered), 변경(mutable), 순회(iterable)는 가능하다.

내부에 중복을 허용하지 않는다는 점도 기억하자.

### `.add(elem)`

> 집합에 원소(elem)를 추가한다.

```py
a = {'사과', '바나나', '포도'}
a.add('수박')
print(a)
# >> {'포도', '수박', '사과', '바나나'}
```
### `.update(*others)`

> 여러 값들(iterable한 데이터 구조)을 집합에 한번에 추가한다.

```py
a = {'사과', '바나나', '포도'}
a.update({'수박', '토마토','포도'})
print(a)
# >> {'포도', '수박', '사과', '바나나', '토마토'}
```
set의 특성상, 중복은 무시하고 받아온다.

### `.remove(elem)`

> 집합에서 원소를 삭제한다. 없으면 에러를 낸다.

```py
a = {'사과', '바나나', '포도'}

a.remove('사과')
print(a)
# >> {'포도', '바나나'}

a.remove('토마토')
# KeyError: '토마토'
```

### `.discard(elem)`

> 집합에서 원소를 삭제한다. 대신, 원소가 없어도 에러를 내지 않는다.

집합 안에 원소가 있는지 없는지 확실하지 않을 때 쓸 수 있다.

## Dictionary

> 역시 변경, 순회가 가능하고, 순서가 없다

다만, {키:값}이 자료구조에 쌍으로 들어간 점을 고려해 보자.

### `.get(key[, default])`

> 키를 통해 값을 가져오고, 없으면 없다고 한다(에러를 내지 않는다)

키를 통해 값을 가져오는 건 `dict[key]`도 충분히 할 수 있지만, 이 방법으로는 없는 키를 가져오고 싶을 때 에러를 낸다.

키가 있는지 없는지 확실하지 않을 때 `.get()`을 쓰게 된다.

```py
d = {'a': 1, 'b': 2, 'c': 3}

print(d['d'])
# KeyError: 'd'

print(d.get('d'))
# None
```

기본적으로 `.get()`으로 없는걸 가져오면 None, 즉 아무것도 반환하지 않지만, 특정 값을 반환하게 할 수도 있다.

```py
print(d.get('d'), '없음')
# 없음
```

### `.setdefault(key[, default])`

`.get()`과 비슷한 동작을 한다.

> 키가 있다면 가져오되, 키가 없으면 없는 키를 삽입한 후 가져온다.

심지어, 값도 설정을 통해 넣을 수 있다.

```py
d = {'a': 1, 'b': 2, 'c': 3}

d.setdefault('d', 4)
print(d)
# >> {'a': 1, 'b': 2, 'c': 3, 'd': 4}
```

### `.pop(key[, default])`

> key가 딕셔너리에 있으면 제거하고, 그 값을 반환함. 딕셔너리에 없으면 기본값을 반환한다.

기본값 설정 안하면 키가 없을 시 에러가 난다.


### `.update([other])`

> others의 key: value 쌍으로 현재 딕셔너리를 덮어쓴다. 

others는 key: value 쌍으로 되어 있으면 다 가능함 

딕셔너리에 새로 추가하는 키값이 있으면 바꿔주고, 없다면 새로 키 값을 추가하고 그 값을 할당하는 식이다.

`.setdefault()`와 비슷해 보이지만, 가져오는 기능이 없고 업데이트만 가능함