# 얕은 복사와 깊은 복사

파이썬에서 변수에 값을 저장한다는 건, 그 값을 메모리에 쓰고 그 주소(id)를 변수에 저장하는 것에 가깝다.

예를 들어보자.

```py
lst = [1, 2, 3]
print(id(lst))
# >> 3252331722176
```

`lst`라는 변수에 바로 `[1, 2, 3]`라는 리스트가 저장되는 것이 아니고, 메모리 어딘가에 `[1, 2, 3]`라는 리스트가 저장된 후 그 주소인 `3252331722176`을 lst에 적어놓는 것이다. 

## Assignment

그럼, 만약 `lst_copy`라는 새로운 변수를 만들고, 여기에 `lst`를 복사하면 어떻게 될까?

```py
lst = [1, 2, 3]
lst_copy = lst

print(id(lst), id(lst_copy))
# >> 3252331722176 3252331722176

print(id(lst) == id(lst_copy))
# >> True

lst_copy[0] = 10
print(lst[0])
# >> 10
```
지금 한 작업은 `lst_copy`라는 새로운 변수를 만들어서 여기에 `lst`에 적혀 있는 모든 것, 즉 `[1, 2, 3]`의 주소인 `3252331722176`을 저장한 것이다.

즉, 메모리에 적힌 `[1, 2, 3]`는 그대로 있고, 이걸 가리키는 화살표만 복사해서 가져온 셈이다.

이 상황에서 `lst_copy`의 데이터를 바꿔버리면, 메모리의 `3252331722176`에 있는 데이터를 바꾸게 되고, 결국 원본 `lst`의 데이터를 바꾸는 것과 별 차이가 없어진다.

즉, 그냥 변수명을 새로 만들어서 `새_변수명 = 원래_변수명`을 하는 것은 전혀 복사가 아니고, 그냥 원래 변수명을 2번 쓴 것과 다르지 않다는 것이다.

## shallow_copy

그렇다면, 복사를 할 때 주소를 가져오는 게 아니라 메모리에 있는 리스트를 통째로 복사해 오면 된다.

```py
lst = [1, 2, 3]
lst_shallow = lst[:]

print(id(lst), id(lst_shallow))
# >> 3252331696384 3252331694272

print(id(lst) == id(lst_shallow))
# >> False

lst_shallow[0] = 10
print(lst[0])
# >> 1
```

리스트에서의 slicing은, 리스트의 특정 부분의 값들을 복사해 새로운 리스트에 저장하는 것에 가깝다. 

`[:]`처럼 리스트의 전체를 slicing하면, `[1, 2, 3]`이라는 새로운 리스트를 만들어 `lst_shallow`에 저장한 것이기 때문에, 실제 가리키는 주소가 달라지게 된다.

즉, `lst_shallow`의 데이터를 바꿔도 원본 `lst`에는 영향이 가지 않는다는 말이다.

---

다만, 이는 1차원 리스트에서만의 일이고, 리스트의 차원이 하나씩 높아질수록 문제가 생기게 된다.

다음 예시를 보자.

```py
lst = [1, 2, [1, 2]]
lst_shallow = lst[:]

print(id(lst[2]), id(lst_shallow[2]))
# >> 3252331713728 3252331713728

print(id(lst[2]) == id(lst_shallow)[2])
# >> True

lst_shallow[2][0] = 10
print(lst[2][0])
# >> 10
```
위 경우, 변수 `lst`의 주소가 가리키는 메모리에는 `[1, 2, [1, 2]]`가 존재하는 게 아니라, `[1, 2, [1, 2]의 주소]`가 존재하게 된다. 

즉, slicing을 통해 원본 `lst`의 모든 값을 복사한 새로운 리스트 `lst_shallow`에 `[1, 2]`의 주소가 저장된다는 말이고, 그냥 `=`을 썼을 때처럼 하나의 메모리를 2개의 포인터가 가리키는 형태가 된 것이다.

그래서 `lst[2]`와 `lst_shallow[2]`의 id를 각각 찍어보면 같게 나오는 것이다.

slicing을 **shallow copy**라고 부르는 것도 그래서이다.

## Deep_copy

그래서 나온 것이 Deep copy, 깊은 복사이다.

깊은 복사는 리스트 안의 모든 데이터를 int나 str같은 immutable한 값 단위까지 내려가 복사해오는 작업이다.

다만, python 내장함수만으로는 힘들고 `copy`모듈을 가져와야 가능한 작업이다.

```py
from copy import deepcopy

lst = [1, 2, [1, 2]]
lst_deep = deepcopy(lst)

print(id(lst[2]), id(lst_deep[2]))
# >> 3252331713728 3252331754324

print(id(lst[2]) == id(lst_deep)[2])
# >> False
```
deepcopy 함수를 이용하면, 리스트 안의 모든 데이터를 복사해 오기 때문에, 아무리 `lst_deep`의 값을 바꿔도 원본 `lst`의 값은 변하지 않는다.


---

# [코드 시각화](https://pythontutor.com/visualize.html#mode=edit)

실제로 데이터가 메모리에 어떻게 저장되는지 보기가 좋다

copy에 관해 