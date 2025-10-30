# 시간복잡도

알고리즘이 실행되는데 필요한 시간

주로 빅오(Big-O) 표기법을 이용해 표현한다

## 빅오 표기법(O)

입력 크기 n에 대한 알고리즘 실행 시 최악의 경우를 표현한다.

알고리즘 성능 평가에 있어 안정성을 위해 최악의 경우를 가정하기 때문에 일반적으로 빅오 표기법을 활용한다.

계산을 편하게 하기 위해 입력 N에 따른 수행 시간의 증가율만을 사용한다. 즉 시간복잡도가 다항식으로 이루어질 때에는 최고차항만을 고려하며 계수는 생략한다.

빅오메가는 최선, 빅세타는 평균을 표현한다.

## 시간 복잡도 종류

- 상수 시간 복잡도 : $O(1)$

    입력 크기에 상관없이 실행 시간이 일정하다.
    
    예시 : 배열에서 특정 인덱스의 요소에 접근
 
- 로그 시간 복잡도 : $O(logN)$

    실행 시간이 입력 크기의 로그에 비례하여 증가한다.

    예시 : 이진 탐색 알고리즘
 
- 선형 시간 복잡도 : $O(N)$

    실행 시간이 입력 크기에 비례하여 증가한다.

    예시 : 리스트 또는 배열에 대한 반복문 알고리즘
 
- 선형 로그 시간 복잡도 : $O(NlogN)$

    실행 시간이 N과 logN의 곱에 비례하여 증가한다.

    일반적으로 O(logN)의 시간이 걸리는 로직을 N번 반복할 때 걸리는 시간복잡도이다.

    예시 : 효율적인 정렬 알고리즘(퀵소트, 머지소트)
 
- 제곱 시간 복잡도 : $O(N^2)$
  
    실행 시간이 입력 크기의 제곱에 비례하여 증가한다.
  
    예시 : 이중 반복문을 사용하는 알고리즘
 
- 지수 시간 복잡도:  $O(2^n)$

    설명 : 실행 시간이 입력 크기에 대한 지수 함수로 증가하는 알고리즘이다.

    예시 : 재귀함수를 활용한 피보나치 수열 구현

## 메서드의 시간복잡도 정리

```text
			Complexity of Python Operations

Lists:
                               Complexity
Operation     | Example      | Class         | Notes
--------------+--------------+---------------+-------------------------------
Index         | l[i]         | O(1)	     |
Store         | l[i] = 0     | O(1)	     |
Length        | len(l)       | O(1)	     |
Append        | l.append(5)  | O(1)	     | mostly: ICS-46 covers details
Pop	          | l.pop()      | O(1)	     | same as l.pop(-1), popping at end
Clear         | l.clear()    | O(1)	     | similar to l = []

Slice         | l[a:b]       | O(b-a)	     | l[1:5]:O(l)/l[:]:O(len(l)-0)=O(N)
Extend        | l.extend(...)| O(len(...))   | depends only on len of extension
Construction  | list(...)    | O(len(...))   | depends on length of ... iterable

check ==, !=  | l1 == l2     | O(N)      |
Insert        | l[a:b] = ... | O(N)	     |
Delete        | del l[i]     | O(N)	     | depends on i; O(N) in worst case
Containment   | x in/not in l| O(N)	     | linearly searches list
Copy          | l.copy()     | O(N)	     | Same as l[:] which is O(N)
Remove        | l.remove(...)| O(N)	     |
Pop	          | l.pop(i)     | O(N)	     | O(N-i): l.pop(0):O(N) (see above)
Extreme value | min(l)/max(l)| O(N)	     | linearly searches list for value
Reverse	      | l.reverse()  | O(N)	     |
Iteration     | for v in l:  | O(N)      | Worst: no return/break in loop

Sort          | l.sort()     | O(N Log N)    | key/reverse mostly doesn't change
Multiply      | k*l          | O(k N)        | 5*l is O(N): len(l)*l is O(N**2)

Tuples support all operations that do not mutate the data structure (and they
have the same complexity classes).


Sets:
                               Complexity
Operation     | Example      | Class         | Notes
--------------+--------------+---------------+-------------------------------
Length        | len(s)       | O(1)	     |
Add           | s.add(5)     | O(1)	     |
Containment   | x in/not in s| O(1)	     | compare to list/tuple - O(N)
Remove        | s.remove(..) | O(1)	     | compare to list/tuple - O(N)
Discard       | s.discard(..)| O(1)	     |
Pop           | s.pop()      | O(1)	     | popped value "randomly" selected
Clear         | s.clear()    | O(1)	     | similar to s = set()

Construction  | set(...)     | O(len(...))   | depends on length of ... iterable
check ==, !=  | s != t       | O(len(s))     | same as len(t); False in O(1) if
      	      	     	       		       the lengths are different
<=/<          | s <= t       | O(len(s))     | issubset
>=/>          | s >= t       | O(len(t))     | issuperset s <= t == t >= s
Union         | s | t        | O(len(s)+len(t))
Intersection  | s & t        | O(len(s)+len(t))
Difference    | s - t        | O(len(s)+len(t))
Symmetric Diff| s ^ t        | O(len(s)+len(t))

Iteration     | for v in s:  | O(N)          | Worst: no return/break in loop
Copy          | s.copy()     | O(N)	     |

Sets have many more operations that are O(1) compared with lists and tuples.
Not needing to keep values in a specific order in a set (while lists/tuples
require an order) allows for faster implementations of some set operations.

Frozen sets support all operations that do not mutate the data structure (and
they have the same  complexity classes).


Dictionaries: dict and defaultdict

                               Complexity
Operation     | Example      | Class         | Notes
--------------+--------------+---------------+-------------------------------
Index         | d[k]         | O(1)	     |
Store         | d[k] = v     | O(1)	     |
Length        | len(d)       | O(1)	     |
Delete        | del d[k]     | O(1)	     |
get/setdefault| d.get(k)     | O(1)	     |
Pop           | d.pop(k)     | O(1)	     |
Pop item      | d.popitem()  | O(1)	     | popped item "randomly" selected
Clear         | d.clear()    | O(1)	     | similar to s = {} or = dict()
View          | d.keys()     | O(1)	     | same for d.values()

Construction  | dict(...)    | O(len(...))   | depends # (key,value) 2-tuples

Iteration     | for k in d:  | O(N)          | all forms: keys, values, items
	      	      	       		     | Worst: no return/break in loop
So, most dict operations are O(1).
```