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

| Return type   | Methods                           | Description                                         |
| ------------- | --------------------------------- | --------------------------------------------------- |
| boolean       | add(E e)                          | 주어진 객체를 맨 끝에 추가한다.                       |
| void          | add(int index, E element)         | 주어진 인덱스에 객체를 추가한다.                      |
| boolean       | addAll(Collection<? extends E> c) | 주어진 Collection 타입 객체를 리스트에 추가한다.      |
| E             | set(int index, E element)         | 주어진 인덱스에 저장된 객체를 주어진 객체로 바꾼다.    |
| boolean       | contains(Object o)                | 주어진 객체가 저장되어 있는지를 확인한다.             |
| E             | get(int index)                    | 주어진 인덱스에 저장된 객체를 리턴한다.               |
| Iterator<E>   | iterator()                        | 저장된 객체를 한 번씩 가져오는 반복자 리턴한다.        |
| boolean       | isEmpty()                         | 컬렉션이 비어 있는지 조사한다.                        |
| int           | size()                            | 저장되어 있는 전체 객체수를 리턴한다.                  |
| void          | clear()                           | 저장된 모든 객체를 삭제한다.                          |
| E             | remove(int index)                 | 주어진 인덱스에 저장된 객체를 삭제한다.                |
| boolean       | remove(Object o)                  | 주어진 객체를 삭제한다.                               |

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


### 정렬

일반적인 java에서 제공하는 클래스에 대해 정렬은 간단하게 구현 가능하다.

#### 오름차순

오름차순 정렬은 `Collections.sort()` 메소드를 사용한다.
- `List.sort(null)`을 이용해도 같은 동작을 한다.

    ```java
    // numbers = [5, 3, 2, 4, 1]
    Collections.sort(numbers);  // [1, 2, 3, 4, 5]

    numbers.sort(null);         // [1, 2, 3, 4, 5]
    ```

#### 내림차순

내림차순 정렬은 오름차순 정렬 후 `Collections.reverse()`를 이용해 역순으로 뒤집을 수 있다.
- `Collections.sort()`에서 리스트 외에 역순 Comparator(`Collections.reverseOrder()`)를 두 번째 매개값으로 줘도 됨
- `List.sort()` 뒤에 같은 Comparator를 줘도 내림차순으로 정렬된다.

    ```java
    // numbers = [5, 3, 2, 4, 1]
    Collections.sort(numbers);      // [1, 2, 3, 4, 5]
    Collections.reverse(numbers);   // [5, 4, 3, 2, 1]

    Collections.sort(numbers, Collections.reverseOrder());  // [5, 4, 3, 2, 1]

    numbers.sort(Collections.reverseOrder());  // [5, 4, 3, 2, 1]
    ```

### `Comparable`, `Comparator` 인터페이스

- List 정렬 시, 특정 기준으로 정렬하고 싶을 경우 일반적으로는 `Collections.sort()` 메소드를 구현한다.

- `String`, `int` 등의 java에서 제공하는 클래스로 정렬할 때와 달리, 직접 만든 클래스에는 정렬 기준이 없다.

    | Interface  | Return | Method              | 설명                                                                                             |
    | ---------- | ------ | ------------------- | ------------------------------------------------------------------------------------------------ |
    | Comparable | int    | compareTo(T o)      | 자신과 인자로 주어진 객체를 비교하여 같으면 0, 자신이 크면 양수, 자신이 작으면 음수를 반환한다.         |
    | Comparator | int    | compare(T o1, T o2) | 두 개의 인자를 받아서 비교하여 같으면 0, 첫 번째 인자가 크면 양수, 두 번째 인자가 크면 음수를 반환한다. |

    - `.compare()`, `.compareTo()` 메소드는 정수를 반환한다. 이 정수를 기준으로 정렬함

#### `Comparable`

- 클래스 자체에서 `Comparable` 인터페이스를 구현하도록 설정할 수 있다.

    ```java
    public class Music implements Comparable<Music> {
        ...

        @Override
        public int compareTo(Music music) {
            //오름차순 정렬
            return this.ranking - music.ranking;  // 자기 랭킹보다 크면 양수, 작으면 음수 -> 양수가 나오면 뒤로 간다
        }
    }
    ```

- 이 인터페이스 구현 시 `.compareTo()` 메소드를 오버라이드 해야 한다.
    - 메소드에서 반환되는 값을 가지고 정렬 기준을 잡음
    
    - 자신과 인자로 주어진 객체를 비교하여 같으면 0, 자신이 크면 양수, 자신이 작으면 음수를 반환한다.
        - 값은 중요하지 않고, 양수/음수/0 구분만 중요함
        - return값이 양수일 경우 정렬 시 뒤로 가고, 음수일 경우 정렬 시 앞으로 온다.

    - `Comparable` 뒤에 타입 파라미터를 설정 시 오버라이드된 `compareTo` 메소드에서도 그 타입 파라미터를 따라가게 된다.

- `Collections.sort()`를 호출할 때 `Comparable` 인터페이스를 구현하고 있는 객체를 가지고 있는 리스트를 매개값으로 전달하면 정렬이 된다.
    ```java
    List<Music> musicList = new ArrayList<>();
    ...
    Collections.sort(musicList);  // musiclist의 랭킹 기준으로 오름차순 정렬된다.
    ```

#### `Comparator`

- 리스트의 객체가 `Comparable` 인터페이스를 구현하고 있지 않거나, 다른 `Comparable`과 다른 기준으로 정렬하고 싶은 경우 사용한다.
    
- 정렬 기준을 지정하기 위해 `Comparator` 인터페이스를 구현해야 한다.
    ```java
    public class ArtistAscending implements Comparator<Music>{

        @Override
        public int compare(Music music1, Music music2) {
            // 기존 String의 compareTo() 메소드를 사용
            return music1.getArtist().compareTo(music2.getArtist());  
        }
    }
    ```
    - 새로운 비교용 클래스를 생성해서 사용한다.

- `Comparator` 인터페이스를 구현하려면 `.compare()` 메소드를 만들면 된다.
    - `Comparator` 인터페이스의 `.compare()` 메소드는 인자를 2개 받아 정수를 리턴한다.
    - 두 개의 인자를 받아서 비교하여 같으면 0, 첫 번째 인자가 크면 양수, 작으면 음수를 반환한다.
    - 양수인 경우 첫 번째 인자를 뒤로, 음수인 경우 앞으로 정렬한다.    

- `Collections.sort()` 메소드의 두 번째 인자로 `Comparator` 인터페이스를 구현한 객체를 전달해야 한다.
    ```java
    List<Music> musicList = new ArrayList<>();
    ...
    // musiclist의 아티스트 기준으로 오름차순 정렬된다.
    Collections.sort(musicList, new ArtistAscending());  
    ```
    - 새로운 정렬기준 객체를 만들어서 전달

- 정렬기준을 만들 때마다 새로운 클래스를 만들기는 힘들기 때문에, 다른 방법을 이용할 수 있다.
    1. 익명 구현 객체
        ```java
        Collections.sort(musicList, new Comparator<Music>() {

            @Override
            public int compare(Music music1, Music music2) {
                return music1.getTitle().compareTo(music2.getTitle());
            }
        });
        ```
        - 인터페이스는 추상 메소드 때문에 객체로 생성할 수 없지만, 그자리에서 바로 오버라이드를 통해 일회용으로 추상 메소드를 재정의함
            - 실제 컴파일 될 때는 익명의 클래스가 하나 생긴다

        - 람다식이 생기고 나서부터는 지금 이 방식은 잘 사용하지 않는다.

    2. 람다식(JDK 1.8부터) - 익명 구현 객체를 만드는 더 쉬운 방법
        ```java
        Collections.sort(musicList, (music1, music2) ->
            music1.getTitle().compareTo(music2.getTitle())
        );
        ```
        - 익명 구현 개체 구현하던 방식을 더 간단하게 표현한 것
        - 익명 클래스를 만들지도 않는다.


## Set

- Set은 저장 **순서를 유지하지 않는** 구조를 가진 인터페이스이다.
    - 인덱스도 없다
    - `HashSet`, `LinkedHashSet`, `TreeSet`등의 클래스가 `Set`을 구현한다.
        - `LinkedHashSet`은 저장한 순서는 유지해 준다.  

- 중복되는 객체를 저장할 수 없다 
    - null도 중복을 허용하지 않기 때문에 1개만 저장할 수 있다.    
    
- 인덱스로 관리하지 않기 때문에 인덱스를 매개 값으로 갖는 메소드가 없다.
    - `for`문에서 인덱스로 순회하는 것이 아니라, 향상된 `for`문을 이용해 순회시킨다.

- 전체 객체를 대상으로 한 번씩 반복해서 가져오는 반복자(Iterator)를 제공한다.

### Set 메소드

| Return type  | Method                            | 설명                                           |
| ------------ | --------------------------------- | ---------------------------------------------- |
| boolean      | add(E e)                          | 주어진 객체를 추가한다.                          |
| boolean      | addAll(Collection<? extends E> c) | 주어진 Collection 타입 객체를 Set에 추가한다.     |
| boolean      | contains(Object o)                | 주어진 객체가 저장되어 있는지를 확인한다.          |
| Iterator\<E> | iterator()                        | 저장된 객체를 한 번씩 가져오는 반복자 리턴한다.     |
| boolean      | isEmpty()                         | 컬렉션이 비어 있는지 조사한다.                     |
| int          | size()                            | 저장되어 있는 전체 객체수를 리턴한다.               |
| void         | clear()                           | 저장된 모든 객체를 삭제한다.                       |
| boolean      | remove(Object o)                  | 주어진 객체를 삭제한다.                            |

- `Collection` 인터페이스를 구현하기 때문에, `List` 메소드와 유사하다.

#### `.add()`

```java
Set<String> set = new HashSet<>();

set.add(null);
set.add("반갑습니다");
set.add(new String("반갑습니다"));
set.add("여러분");
set.add("안녕하세요");
set.add("여러분");
set.add(null);

System.out.println(set);        // [null, 반갑습니다, 안녕하세요, 여러분]
System.out.println(set.size()); // 4
```

- 중복된 값을 add해도 저장되지 않는다.

- 저장된 순서도 저장되지 않는다.(일반 `HashSet`의 경우)
    - 엄밀히 말하면 저장된 해시값의 순서대로 출력된다.

### `Set`에 저장된 객체에 접근하는 방법

1. 향상된 `for`문 활용

    - 일반 for문은 인덱스가 없어서 사용할 수 없다.
        
        ```java
        for (String str: set) {
            System.out.println(str);
        }
        ```

    - 이 방식을 개선해서 람다식, 메서드 참조 등으로 접근할 수도 있다.
        ```java
        // 람다식
        set.forEach(s -> System.out.println(s));

        // 메서드 참조 활용
        set.forEach(System.out::println);
        ```

2. `Iterator` 반복자를 사용하는 방법

    - `Iterator`는 해당 Collection의 모든 객체에 한번씩 순회하면서 접근하는 반복자이다.
        ```java
        Iterator<String> iterator = set.iterator();

        while (iterator.hasNext()) {  // iterator.hasNext(): iterator를 돌리면서 다음 요소가 있는지 확인(boolean)
            System.out.println(iterator.next());  // iterator.next(): 다음 요소로 넘어가는 메소드
        }
        ```
    - 요즘은 잘 쓰지 않고, 향상된 for문을 사용한다.

### `Set`의 중복 확인

객체의 `.equals()`와 `.hashCode()`를 이용해서 두 객체가 같은지, 다르지를 파악한다.

- `String`, `Integer` 등의 java 지원 클래스에서는 이미 값 기준으로 비교하도록 설정되어 두 객체를 구분한다.
- 직접 클래스를 만들었을 경우, 기본값은 각 객체의 주소를 기준으로 객체를 비교하기 때문에, 같은 값이지만 다른 객체로 인식한다.
- 클래스에서 `.hashCode()`, `.equals()`를 재정의해서 값을 기준으로 비교하도록 설정해야 한다.

### `TreeSet`

- 이진트리(자식 노드가 최대 2개)를 이용하는 Set의 일종

    ```java
    TreeSet<String> set = new TreeSet<>();

    set.add("바");
    set.add("가");
    set.add("나");
    set.add("사");
    set.add("다");
    set.add("가");  // 중복
    set.add("마");
    set.add("사");  // 중복
    set.add("라");
    set.add("마");  // 중복

    System.out.println(set);            // [가, 나, 다, 라, 마, 바, 사]
    System.out.println(set.size());     // 7
    
    // Treeset 클래스에서 제공하는 메소드
    System.out.println(set.first());    // 가
    System.out.println(set.last());     // 사
    ```
- 자료가 저장될 때부터 정렬되면서 들어간다. 검색하는데 시간복잡도도 낮아서 빨리 된다.
    - `null`을 `Treeset`에 저장하려 하면 에러가 난다. 정렬을 할 수 없기 때문

- 직접 만든 클래스의 경우, `Comparable` 인터페이스를 구현하지 않으면(정렬 기준이 없으면) TreeSet을 이용할 수 없다.
    - 직접 `Comparator` 기준을 새로 만들어서 구현할 수도 있음. 어쨌든 정렬 기준이 필요하다
        ```java
        TreeSet<Music> musicSet = new TreeSet<>(
            (o1, o2) -> o1.getTitle().compareTo(o2.getTitle())
        );
        ```

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


## 제네릭스

- JDK 1.5부터 제공되는 기능이다.
- 클래스와 인터페이스, 메소드 내부에서 다룰 데이터의 타입을 지정할 수 있다.
- 타입 파라미터는 코드 작성 시 구체적인 타입으로 대체되어 다양한 코드를 생성하도록 도와준다.

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


### 제네릭스의 장점

- 실행 시 잘못된 타입 사용으로 발생할 수 있는 에러를 컴파일 시에 체크할 수 있다.
- 컬렉션에 저장되는 요소의 타입을 제한하기 때문에 불필요한 형 변환을 제거할 수 있다.
    
    ```java
    List list = new ArrayList();
    
    list.add("Hello");
    
    String str = (String) list.get(0);
    ```
    
    ```java
    List<String> list = new ArrayList<String>();
    
    list.add("Hello");
    
    String str = list.get(0);
    ```
    

### 제네릭스 타입

- 타입 파라미터를 가지는 클래스와 인터페이스를 제네릭스 타입이라고 한다.
- 제네릭스 타입은 클래스 또는 인터페이스 이름 뒤에 "<>" 기호를 추가하고 사이에 타입 파라미터가 위치한다.
- 타입 파라미터는 대문자 알파벳 한 글자로 작성한다.
    
    ```java
      // java.util 패키지의 ArrayList 클래스
      public class ArrayList<E> extends ... {
        ...
      }
    
      // java.util 패키지의 List 인터페이스
      public interface List<E> extends Collection<E> {
        ...
      }
    ```
    
- 제네릭스 타입의 클래스를 객체로 생성할 때 구체적인 타입으로 변경된다.
- jdk 1.7부터 중복 기술을 줄이기 위해 다이아몬드 연산자"<>"를 제공한다.
- 컴파일러는 타입 파라미터 부분에 "<>" 연산자를 사용하면 타입 파라미터를 자동으로 설정한다.
    
    ```java
    List<String> list = new ArrayList<>(); // 타입 추론
    
    ...
    ```
    

#### 멀티 타입 파라미터

- 제네릭스 타입은 두 개 이상의 멀티 타입 파라미터를 사용할 수 있는데, 이 경우 각 타입 파라미터를 콤마로 구분한다.
    
    ```java
    // java.util 패키지의 Map 인터페이스
    public interface Map<K, V> {
      ...
    }
    ```
    
    ```java
    Map<String, Integer> map = new HashMap<>();
    
    ...
    ```
    

### 제네릭스 타입과 상속

- 제네릭스 타입도 부모 클래스가 될 수 있고 타입 파라미터는 자식 클래스에도 기술해야 한다.
- 자식 클래스는 추가적으로 타입 파라미터를 가질 수 있다.
    
    ```java
    // java.util.function 패키지의 Function 인터페이스
    public interface Function<T, R> {
      ...
    }
    ```
    
    ```java
    // java.util.function 패키지의 UnaryOperator 인터페이스
    public interface UnaryOperator<T> extends Function<T, T> {
      ...
    }
    ```
    

### 제네릭스 타입 제한

- 타입 파라미터를 지정할 때 상속 및 구현 관계로 제한된 파라미터를 선언하려면 타입 파라미터 뒤에 extends 키워드를 붙이고 상위 타입을 명시하면 된다.
- 인터페이스로 제한된 파라미터를 선언할 때 implements를 사용하지 않고 동일하게 extends를 사용한다.
    
    ```java
      public <T extends 상위타입> 클래스명 {
        ...
      }
    ```
    

### 제네릭스 메소드

- 제네릭스 메소드는 리턴 타입과 매개 타입으로 타입 파라미터를 갖는 메소드를 말한다.
- 리턴 타입 앞에 "<>" 기호를 추가하고 타입 파라미터를 기술한다.
- 제네릭스 메소드도 멀티 파라미터를 가질 수 있다.
- 타입 파라미터를 기술한 이후에는 리턴 타입과 매개 타입에서 타입 파라미터를 사용하면 된다.
    
    ```java
    // java.util 패키지의 Collections 클래스
    public class Collections {
      public static <T extends Comparable<? super T>> void sort(List<T> list) {
        ...
      }
      public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
        ...
      }
      ...
    }
    ```
    
    ```java
    List<String> list = Arrays.asList("이몽룡", "홍길동", "문인수");
    
    Collections.<String>sort(list);
    ```
    
- 제네릭스 메소드를 호출할 때 타입 파라미터를 생략하면 매개값으로 구체적인 타입을 추론한다.
    
    ```java
    List<String> list = Arrays.asList("이몽룡", "홍길동", "문인수");
    
    Collections.sort(list); // 타입 추론
    ```
    

### 와일드카드(Wildcard)

- 와일드카드 문자는 하나 이상의 다른 문자들을 대표적으로 상징하는 특수 문자(?)를 의미한다.
- 타입 파라미터로 <?>는 모든 클래스나 인터페이스 타입이 올 수 있다.
- 타입 파라미터로 <? extends T>는 T 타입이거나 T 타입을 상속하는 타입만 올 수 있다.
- 타입 파라미터로 <? super T>는 T 타입이거나 T 타입의 부모 타입만 올 수 있다.

