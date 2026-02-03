# API

- 일반적으로, API는 운영체제, 서버, 프로그래밍 언어가 제공하는 기능을 제어할 수 있게 만든 인터페이스를 의미한다.
- java에서는 API는 프로그램 개발에 자주 사용되는 클래스, 인터페이스의 모음을 뜻함

## 문자열 클래스

- 클래스 `String`은 불변 클래스이다.
    - 한번 문자열을 생성하면 변경할 수 없다는 뜻
    - 바꾸고 싶다면 새로운 객체를 만들어서 덮어씌워야 한다.

    ```java
    new String("Hello");
    ```
- 변경이 적고, 읽기가 많을 경우에 사용하는 것이 좋다.

### `.length()`

- 원본 문자열의 길이를 `int`로 반환한다.

    ```java
    String str = "Hello";

    System.out.println(str.length());  // 5
    ```

### `.charAt(i)`

- 원본 문자열에서 `i`번째 인덱스의 `char`를 반환한다.

    ```java
    String str = "Hello";

    System.out.println(str.charAt(2));  // l
    ```

### `.concat(str)`

- 전달받은 문자열(str)과 원본 문자열을 하나로 합친 새로운 문자열을 리턴한다.

    ```java
    String str = "Hello World";

    String concat = str.concat("!!!");

    System.out.println(str);            // Hello World
    System.out.println(concat);         // Hello World!!!
    System.out.println(str == concat);  // false
    ```

- concat을 돌려도 기존 문자열에 변화가 가지 않고, 서로 주소값이 다르게 찍히는 것도 확인 가능하다.
    - `String` 클래스가 불변 클래스이기 때문
- `+`연산자를 통해 연결하는 것과 같은 동작을 하는데, `.concat()`은 매개값을 하나만 받을 수 있다.

### `.substring(beginIndex[, endIndex])`

- 전달된 위치(`beginIndex`)부터 끝까지(혹은 `endIndex`까지)의 문자열을 새로 생성해서 리턴한다.
    - 매개값의 개수에 따라 오버로딩된 메소드
    ```java
    String str = "Hello World";

    System.out.println(str.substring(6));  // World
    System.out.println(str.substring(2, 8));  // llo Wo
    ```

### `.indexOf(str(ch)[, fromIndex])`

- 원본 문자열에서, 전달받은 문자열(`str`/`ch`)이 첫 번째로 나타나는 시작 인덱스를 리턴한다.
    - `.lastIndexOf()`를 이용하면 마지막 인덱스부터 검색함

- 만약, 주어진 문자열이 포함되지 않을 경우 -1을 리턴한다.

- 몇 번째 인덱스(`fromIndex`가 있을 경우)부터 검색할지를 정할 수도 있다. 

    ```java
    String str = "Hello World";

    System.out.println(str.indexOf("World"));  // 6
    System.out.println(str.indexOf("Java"));  // -1
    System.out.println(str.indexOf('o', 6));  // 7
    ```

### `.replace(oldChar, newChar)`, `.replace(target, replacement)`

- 원본 문자열에서 `oldChar`/`target`가 `newChar`/`replacement`로 변경된 문자열을 새로 생성해서 리턴
    - `char`, `String` 둘 다 매개값으로 받을 수 있다. 
    
    ```java
    String str = "Hello World";

    System.out.println(str.replace('l', 'c'));  // Hecco Worcd    
    System.out.println(str.replace("World", "Java"));  // Hello Java
    ```

- 정규표현식도 받는다.

### `.trim()`

- 문자열 앞뒤 공백을 제거한 새로운 문자열을 생성해서 리턴
    ```java
    String str = "    Hello World    ";

    System.out.println(str.trim());  // Hello World
    ```

### `.split(regex)`

- 입력받은 구분자(`regex`)로 문자열을 분리하고, **배열**에 담아서 리턴
    ```java
    String str = "Linux,MariaDB,Java,Spring,HTML5,CSS3,Vue.js,Docker";

    String[] strings = str.split(",");

    System.out.println(strings.length);  // 8 
    System.out.println(Arrays.toString(strings));  // [Linux, MariaDB, Java, Spring, HTML5, CSS3, Vue.js, Docker]
    ```
- `String.toCharArray()`를 쓰면 각 문자별로 나눠서 배열로 만들 수도 있다.   

### `String.valueOf(arg)`

- 정적 메소드로, 매개값으로 다양한 데이터(정수, 실수, 논리값, **배열**, ...)를 받는다
- 받은 매개값(`arg`)을 문자열로 변경해서 리턴한다.

    ```java
    System.out.println(String.valueof(1.234));  // "1.234" 
    System.out.println(String.valueof(false));  // "false" 
    ```

### `StringTokenizer(str, delimeter)` 클래스

- 생성자로 전달받은 문자열(`str`)을 구분자(`delimeter`)를 이용해서 분리
- 분리된 최소 단위를 **토큰**이라고 한다.

    ```java
    String str = "Linux,MariaDB,Java,Spring,HTML5,CSS3,Vue.js,Docker,Kubernates,Jenkins";

    StringTokenizer st = new StringTokenizer(str, ",");
    
    // nextToken(): 토큰을 하나 꺼내서 리턴한다.
    System.out.println(st.nextToken());  // Linux

    // countTokens(): 남아있는 토큰 수를 리턴
    System.out.println(st.countTokens());  // 9

    // hasMoreTokens(): 토큰이 남아있다면 true, 없으면 false
    System.out.println(st.hasMoreTokens());  // true

    ```

- `for`문, `while`문을 통해 출력할 수 있다.
    ```java
    // countTokens()가 남은 토큰의 숫자라서 인라인으로 넣으면 안됨
    int l = st.countTokens();  
    for (int i = 0; i < l ; i++) {
        System.out.println(st.nextToken());
    }
    // 향상된 for문은 적용되지 않음

    while (st.hasMoreTokens()) {
        System.out.println(st.nextToken());
    }

    ```

- 문자열을 나눌 때, `.split()`이랑 이거 중에 골라서 사용하면 된다. 결과가 배열로 나오느냐/토큰으로 나오느냐의 차이

## 가변 문자열 클래스

- `StringBuffer`, `StrignBuilder` 2가지를 사용할 수 있다.

- 둘의 차이는 다른건 없고, 동기화 차이
    - `StringBuffer`는 멀티 스레드 환경에서 사용 권장
    - `StrignBuilder`는 단일 스레드 환경에서 실행 권장

- **가변 클래스**이다 (문자열을 생성하고 나서 변경이 가능함)
    - 기본적으로 16개(수정 가능)의 문자를 저장 가능한 버퍼가 미리 생성되고, 문자가 저장됨에 따라서 **자동으로 증가**한다.
    - 내부 버퍼에 문자열을 저장해 놓고, 그 안에서 추가/수정/삭제 작업이 가능하도록 설계됨

- 생성자에 문자열, 버퍼 크기 등을 전달할 수 있다.
    - 버퍼 크기를 전달하면 기본적으로 16개지만, 버퍼 공간을 더 늘리는 것도 가능하다.(딱히 의미는 없음)
    - 버퍼 공간은 문자열 길이 +16개의 여유공간을 남겨놓고 계속 늘어난다.
    - 문자열을 전달하면 객체에 기본 문자열이 저장됨

- 문자열에 추가/수정/삭제 작업이 많은 경우에 `String` 대신 사용한다.

    ```java
    StringBuilder sb = new StringBuilder("안녕하세요");  // int capacity(버퍼 크기) / String str(문자열 내용)

    System.out.println(sb);             // 안녕하세요, 문자열 내용
    System.out.println(sb.length());    // 5, 문자열 길이
    System.out.println(sb.capacity());  // 21, 버퍼 공간 크기
    ```

### `.append("...")`

- 기본 문자열 뒤에 매개값으로 전달받은 문자열을 추가한다.

    ```java
    sb.append(" 저는 홍길동입니다.");
    
    System.out.println(sb);             // 안녕하세요 저는 홍길동입니다.
    System.out.println(sb.length());    // 16
    System.out.println(sb.capacity());  // 21
    ```

- `str += "더할 문자열"`과 같은 작업을 하지만, 작동 방식이 다르다.
    - `String`을 이용하면 새로운 객체를 만들어서 덮어씌우는 것. 연산 이전/이후의 객체 주소가 다르다.
        - 연결연산이 계속될 경우, 새로운 문자열 객체를 계속 생성해야 한다.
        - 객체를 만드는 데도 계속 리소스가 소비된다.

    - `StringBuffer`의 경우 기존 문자열 객체를 수정해 반환하기 때문에, 이전/이후의 객체 주소가 같다
        - 연결연산을 많이 해도 버퍼 공간만 늘어나고, 객체는 새로 생성할 필요가 없다.

### `.insert(offset, str)`

- 기존 문자열에서 `offset` 위치부터 전달된 문자열(`str`)을 추가한다.

    ```java
    sb.insert(6, "!중간에추가된무언가긴문자열!");

    System.out.println(sb);             // 안녕하세요 !중간에추가된무언가긴문자열!저는 홍길동입니다.
    System.out.println(sb.length());    // 31
    System.out.println(sb.capacity());  // 44
    ```


### `.delete(start, end)`

- 기존 문자열의 `start` 인덱스부터 `end`-1 인덱스까지 문자열을 삭제한다.

    ```java
    sb.delete(6, 21);

    System.out.println(sb);             // 안녕하세요 저는 홍길동입니다.
    System.out.println(sb.length());    // 16
    System.out.println(sb.capacity());  // 44
    ```

- `.deleteCharAt(idx)`를 이용하면 특정 인덱스의 문자만 삭제하는 것도 가능하다.

### 메소드 체이닝

- 가변 문자열에서 사용하는 메소드 `append`, `insert`, `delete` 등은 리턴값이 `this`이다.
- 해당 객체의 참조를 그대로 반환하기 때문에, 바로 이어서 다음 작업을 실행할 수 있다.
    
    ```java
    sb = new StringBuilder("Java Programming");
    sb.append(" API").delete(5,17).reverse();

    System.out.println(sb);  // IPA avaJ
    ```


## Wrapper 클래스

- 기본 타입의 자료형을 객체로 바꿔서 넘겨주는 클래스
- 

## 날짜, 시간과 관련된 클래스
