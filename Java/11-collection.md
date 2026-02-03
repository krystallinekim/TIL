# 컬렉션(Collection)

- 컬렉션(컬렉션 프레임워크)은 자바에서 제공하는 자료구조를 담당하는 프레임워크로 `java.util` 패키지에 컬렉션과 관련된 인터페이스와 클래스들이 포함되어 있다.
- 자료구조는 데이터를 효율적으로 이용하는 방법으로 데이터에 편리하게 접근하고, 효율적으로 사용하기 위해서 데이터를 저장하거나 조직하는 방법을 말한다.
- 컬렉션을 사용하면 데이터의 추가, 삭제, 정렬 등의 처리가 간단하게 해결되어 자료구조적 알고리즘을 구현할 필요가 없다.

- 자료구조는 `Map`, `Iterable` 계열로 나뉜다.
    - `Collection`은 `Iterable`의 자식 인터페이스로, `List`, `Queue`, `Set`으로 나뉜다. 
    - `Iterable`들은 전부 향상된 for문을 사용할 수 있음
    
- **객체 데이터만 저장**한다.
    - 기본 타입의 데이터는 wrapper 클래스로 포장되어 들어감

## List

- List는 자료들을 순차적으로 늘어놓은 구조를 가지고 있는 **인터페이스**이다.
    - 저장되는 객체를 인덱스로 관리하기 때문에 인덱스로 객체를 검색, 삭제할 수 있는 기능을 제공한다.
    - 중복되는 객체를 저장 가능하고 null 값도 저장될 수 있다.
    - 특정 인덱스의 객체를 제거하면 바로 뒤 인덱스부터 마지막 인덱스까지 모두 앞으로 1씩 당겨진다.

- `ArrayList`, `LinkedList`, `Vector` 등의 클래스가 있다.
    - `Stack`도 List 인터페이스를 구현하지만, 나머지와는 느낌이 살짝 다름

### List 메소드

| 리턴 타입 | 메소드 | 설명 |
| --- | --- | --- |
| boolean | add(E e) | 주어진 객체를 맨 끝에 추가한다. |
| void | add(int index, E element) | 주어진 인덱스에 객체를 추가한다. |
| boolean | addAll(Collection<? extends E> c) | 주어진 Collection 타입 객체를 리스트에 추가한다. |
| E | set(int index, E element) | 주어진 인덱스에 저장된 객체를 주어진 객체로 바꾼다. |
| boolean | contains(Object o) | 주어진 객체가 저장되어 있는지를 확인한다. |
| E | get(int index) | 주어진 인덱스에 저장된 객체를 리턴한다. |
| Iterator<E> | iterator() | 저장된 객체를 한 번씩 가져오는 반복자 리턴한다. |
| boolean | isEmpty() | 컬렉션이 비어 있는지 조사한다. |
| int | size() | 저장되어 있는 전체 객체수를 리턴한다. |
| void | clear() | 저장된 모든 객체를 삭제한다. |
| E | remove(int index) | 주어진 인덱스에 저장된 객체를 삭제한다. |
| boolean | remove(Object o) | 주어진 객체를 삭제한다. |

#### `.add()`

List에 객체를 저장하기 위해서는 `.add(E)`를 이용한다. E는 elements

```java
List list = new ArrayList();
list.add("안녕하세요");
// 중복되는 객체 저장이 가능
list.add("안녕하세요");
list.add(LocalDateTime.now());
// 기본 타입은 Object 타입으로 autoboxing 되서 저장됨
list.add(true);  
list.add(3.14);
// 객체를 직접 저장하는 게 아니기 때문에 null도 저장 가능함
list.add(null);  
System.out.println(list);  // [안녕하세요, 안녕하세요, 2026-02-03T16:12:44.209179100, true, 3.14, null]
```
- `List`의 `toString()`은 대괄호 안에 리스트 안의 객체들을 `,`로 구분해서 출력한다.

- `.add(idx, E)`를 이용하면 원하는 인덱스 위치에 데이터를 삽입할 수도 있다.
    ```java
    list.add(5, 10000);
    System.out.println(list);  // [안녕하세요, 안녕하세요, 2026-02-03T16:12:44.209179100, true, 10000, 3.14, null]
    ```

- `addFirst`, `addLast`는 최근(JDK 21)에 추가된 메소드로, 맨 앞/맨 뒤에 요소를 추가하는 메소드

#### `.get()`

List에서 객체를 꺼내올 때는 `.get(idx)`를 이용함

```java
String str = (String) list.get(0);
LocalDateTime now = (LocalDateTime) list.get(1);
Boolean bool = (Boolean) list.get(2);
```
    
- 저장할 때 `Object` 타입으로 업캐스팅 되어 저장되는데, 원하는 타입으로 가져오기 위해서는 다운캐스팅을 해야 한다. 

- 직접 인덱스 번호에 접근할 수도 있지만, `for`문을 이용해서 순회하는 것도 가능하다.
    - `Iterator`이므로 향상된 `for`문도 이용이 가능함
    ```java
    for (int i = 0; i < list.size(); i++) {
        System.out.println(list.get(i));
    }
    // 향상된 for문
    for (Object o : list) {
        System.out.println(o);
    }
    ```
#### `.set()`

`.set(idx, E)` 메소드를 이용하면 원하는 인덱스 위치의 객체를 변경할 수 있다.

#### `.remove()`

`.remove(idx)`를 이용하면 원하는 인덱스 위치의 객체를 리스트에서 지우고, 그 뒤 인덱스를 하나씩 당긴다.

- 인덱스 대신 리스트에 있는 객체를 줘도(`.remove(object)`) 그 객체를 지워준다(가장 먼저 나오는 객체로)
    - 이 때 `null`도 객체로 지울 수 있음

- `removeFirst`, `removeLast`도 지원한다.(JDK 21부터)

- 정수 타입의 값을 remove로 제거하고 싶을 때, int 그대로 넣으면 인덱스로 인식해서 제거하려고 한다.
    - Wrapper 객체로 Boxing해서 집어넣으면 됨 - `Integer.valueOf(10000)`
    - 매개값과 타입이 겹칠 때 필요하다.

#### `.isEmpty()`

리스트가 비어있는지 확인하는 메소드

```java
List list = new ArrayList();
System.out.println(list);               // []
System.out.println(list.isEmpty());     // true

list.add(null);
System.out.println(list);               // [null]
System.out.println(list.isEmpty());     // false

list.clear();  // 리스트 내의 객체를 전부 지우는 메소드
System.out.println(list);               // []
System.out.println(list.isEmpty());     // true
```

- 리스트가 비어있다는 건, `List` 객체는 만들어 놨는데 저장한 요소(객체)가 없다는 것

- 리스트 안에 null만 요소로 들어가 있어도 리스트는 차있는 것으로 나온다.

- 조건문, 반복문에서 많이 이용




## 3. Comparable, Comparator 인터페이스

- List를 정렬할 때 Collections.sort() 메소드를 사용하는데 이때 정렬 기준을 지정하기 위해 구현해야 하는 인터페이스이다.
- Collections.sort()를 호출할 때 Comparable 인터페이스를 구현하고 있는 객체를 가지고 있는 리스트를 매개값으로 전달해야 한다.
- 리스트의 객체가 Comparable 인터페이스를 구현하고 있지 않는다면 Collections.sort() 메소드의 두 번째 인자로 Comparator 인터페이스를 구현한 객체를 전달해야 한다.
    
    
    | 인터페이스 | 리턴 타입 | 메소드 | 설명 |
    | --- | --- | --- | --- |
    | Comparable | int | compareTo(T o) | 자신과 인자로 주어진 객체를 비교하여 같으면 0, 자신이 크면 양수, 자신이 작으면 음수를 반환한다. |
    | Comparator | int | compare(T o1, T o2) | 두 개의 인자를 받아서 비교하여 같으면 0, 첫 번째 인자가 크면 양수, 두 번째 인자가 크면 음수를 반환한다. |

## 4. Set

- Set은 저장 순서를 유지하지 않는 구조를 가지고 있다.
- 중복되는 객체를 저장할 수 없고 null도 중복을 허용하지 않기 때문에 1개만 저장할 수 있다.
    
    
- 인덱스로 관리하지 않기 때문에 인덱스를 매개 값으로 갖는 메소드가 없다.
- 전체 객체를 대상으로 한 번씩 반복해서 가져오는 반복자(Iterator)를 제공한다.
- Set 인터페이스의 주요 메소드는 아래와 같다.
    
    
    | 리턴 타입 | 메소드 | 설명 |
    | --- | --- | --- |
    | boolean | add(E e) | 주어진 객체를 추가한다. |
    | boolean | addAll(Collection<? extends E> c) | 주어진 Collection 타입 객체를 Set에 추가한다. |
    | boolean | contains(Object o) | 주어진 객체가 저장되어 있는지를 확인한다. |
    | Iterator\<E> | iterator() | 저장된 객체를 한 번씩 가져오는 반복자 리턴한다. |
    | boolean | isEmpty() | 컬렉션이 비어 있는지 조사한다. |
    | int | size() | 저장되어 있는 전체 객체수를 리턴한다. |
    | void | clear() | 저장된 모든 객체를 삭제한다. |
    | boolean | remove(Object o) | 주어진 객체를 삭제한다. |

## 5. Map

- 키(key)와 값(value)으로 구성된 Entry 객체를 저장하는 구조를 가지고 있다.
- 키(key)와 값(value) 모두 객체이다.
    
    
- 키(key)는 중복 저장을 허용하지 않고 값(value)은 중복 저장이 가능하다.
- Map 인터페이스의 주요 메소드는 아래와 같다.

    | 리턴 타입 | 메소드 | 설명 |
    | --- | --- | --- |
    | V | put(K key, V value) | 주어진 키와 값을 추가, 저장이 되면 값을 리턴한다. |
    | boolean | containsKey(Object key) | 주어진 키가 있는지 확인하여 결과 리턴한다. |
    | boolean | containsValue(Object value) | 주어진 값이 있는지 확인하여 결과 리턴한다. |
    | Set<Map.Entry<K,V>> | entrySet() | 키와 값의 쌍으로 구성된 모든 Map.Entry 객체를 set에 담아서 리턴한다. |
    | V | get(Object key) | 주어진 키의 값을 리턴한다. |
    | boolean | isEmpty() | 컬렉션이 비어있는지 조사한다. |
    | Set\<K> | keySet() | 모든 키를 Set 객체에 담아서 리턴한다. |
    | int | size() | 저장된 키의 수를 리턴한다. |
    | Collection\<V> | values() | 저장된 모든 값을 Collection에 담아서 리턴한다. |
    | void | clear() | 모든 Map.Entry를 삭제한다. |
    | V | remove(Object key) | 주어진 키와 일치하는 Map.Entry 삭제, 삭제가 되면 값을 리턴한다. |


## 제네릭스 타입


### 타입 파라미터

- Type Parameter를 이용해 리스트에 들어갈 타입을 고정할 수 있다.
    - 리스트의 요소 타입이 고정되면서 꺼낼 때도 항상 그 타입으로 나오기 때문에, 매번 다운캐스팅 할 필요가 없다.
    - 생략 시 Object 타입으로 들어간다.

    ```java
    List<String> list = new ArrayList<String>();

    list.add("apple");
    list.add("banana");
    // list.add(10);  // 에러
    ```

- 생성자쪽 타입 파라미터는 생략해도 타입 추론을 통해 알아서 맞춰준다.

- 문법적으로, 클래스 타입만 설정할 수 있어 기본타입 데이터는 설정할 수 없다

    ```java
    // List<int> numbers = new ArrayList<>();  // 에러
    List<Integer> numbers = new ArrayList<>();
    numbers.add(Integer.valueOf(10));
    numbers.add(9);  // AutoBoxing 적용
    ```
    - Wrapper 타입으로 설정하면 됨