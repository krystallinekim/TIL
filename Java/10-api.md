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

- 기본 타입(int, bool, char, ...)의 자료형을 객체로 포장해 주는 클래스
    - int -> Integer, bool -> Boolean, char -> Character, ...

- 프로그램에 따라 기본타입을 객체로 취급해서 처리할 경우가 있는데, 이럴 때 사용한다.
    - `Collection`은 객체를 저장하는데, 기본타입은 객체가 아니라 저장할 수가 없다.
    - 어떤 메소드에서 `Object` 타입을 받아야 할 때 기본타입을 넣어주고 싶을 때 사용


### Boxing

- 기본 자료형을 Wrapper 클래스로 포장하는 것을 Boxing이라고 한다.
    - 기본타입 데이터 -> Wrapper 타입 데이터

- 객체로 나오기 때문에 클래스에서 제공하는 다양한 메소드들을 사용 가능하다.
    - `.equals()`(값 비교), `.compareTo()`(비교 후 0/1 반환) 등

1. Wrapper 클래스 객체를 직접 생성 (1.9버전부터 `@Deprecated`됨)
    ```java
    Integer i = new Integer(10);
    Double d = new Double(3.14);
    Character c = new Character('A');
    ```

    - Wrapper 클래스 객체인데, 값으로 주어진 값을 final field로 들고 있는 것

2. Wrapper 클래스의 static 메소드를 통해 생성

    ```java
    Integer i2 = Integer.valueOf(10);
    Double d2 = Double.valueOf("3.14");
    Character c2 = Character.valueOf('A');
    ```
    - 매개값이 다른 타입이어도 알아서 형변환되서 들어간다.

3. Auto Boxing (1.5버전 이후부터)

    ```java
    Integer i3 = 10;
    Double d3 = 3.14;
    Character c3 = 'A';
    ```
    - 대입연산자 왼쪽에는 Object 타입인 객체가 오고, 오른쪽에는 기본타입인 10, 3.14, 'A' 등이 오지만 대입이 정상적으로 일어난다.
        - 단, 자동 형변환은 일어나지 않는다.
    - 굳이 정적 메소드나 직접 생성 방식을 사용할 필요가 없다

### Unboxing

- 반대로, wrapper 클래스를 다시 기본 자료형으로 풀어주는 것을 Unboxing이라고 한다.

1. Wrapper 객체의 메소드 이용

    ```java
    int num1 = i1.intValue();
    double dnum1 = d1.doubleValue();
    char ch1 = c1.charValue();
    ```

    - 값만 변수에 저장하기 때문에 동등비교(`==`)를 해도 같다고 나온다.

2. Auto Unboxing
    ```java
    int num2 = i2;
    double dnum2 = d2;
    char ch2 = c2;
    ```

    - 역시 대입연산자 좌우가 타입이 다르지만, 자동으로 언박싱이 일어난다.

### 문자열과 Wrapper 클래스
- 기본 자료형과 문자열은 기본적으로 상호 변환이 불가
    ```java
    int num = "10";     // 에러
    String str = 10;    // 에러
    ```


1. 문자열 -> 기본 자료형

    ```java
    int inum = Integer.parseInt("10");          // 10
    double dnum = Double.parseDouble("3.14");   // 3.14
    double dnum2 = Double.parseDouble("10");    // 10.0
    ```

    - `.parseXXX()`를 이용한다.
    - static 메소드임
    - 문자열은 문자열만 제거하면 원하는 데이터 타입이 나오게 만들어 줘야 한다.
        - 다른 타입을 넣으면 에러(`NumberFormatException`)가 발생한다.
        - 단, 자동 형변환이 되는 경우는 가능함

2. 기본 자료형 -> 문자열

    ```java
        String str1 = String.valueOf(10);    // "10"
        String str2 = String.valueOf(3.14);  // "3.14"
    ```

    - `String.valueOf()`을 이용한다.
        - 역시 static 메소드
        - 다양한 타입의 매개변수로 오버로딩 되어있음
    
    ```java
    String str3 = Integer.valueOf(10).toString();
    String str4 = Double.valueOf(3.14).toString();
    ```
    - Wrapper 클래스의 `.valueOf()`를 사용할 수도 있다.
    - 일반적으로는 `String.valueOf()`를 이용하지만, Wrapper 클래스를 이용하고 있을 때는 `.toString()`을 이용해 언박싱할 수도 있는 것

## 날짜, 시간과 관련된 클래스

### `java.util.Date` 클래스

- 날짜, 시간을 표현하는 클래스
    - 객체 간에 날짜와 시간 정보를 주고받을 때 사용

- 좀 많이 옛날에 사용하던 방식. 이제는 잘 사용하지 않는다

#### 생성자

1. 기본 생성자는 현재 날짜, 시간을 반환한다.
    ```java
    Date today = new Date();
    System.out.println(today);  // Tue Feb 03 12:03:58 KST 2026
    ```

2. 생성자에 숫자(long) 하나를 넣으면, 1970년 1월 1일 00시(UTC) 기준으로 객체를 생성해 준다.
    ```java
    Date when = new Date(1000L);
    System.out.println(when);  // Thu Jan 01 09:00:01 KST 1970
    ```
    - 숫자는 밀리초(ms) 단위

3. int 3개를 넣으면 year, month, date 기준으로 나온다. (`@Depricated`됨)
    ```java
    Date when = new Date(2026, 2, 3);
    System.out.println(when);  // Wed Mar 03 00:00:00 KST 3926
    ```
    - 근데 연도가 제대로 나오지는 않음. 
    - 연도는 현대 연도에서 1900을 빼야하고, 월은 0~11 사이 값으로 적어야 한다.

#### 메소드

```java
System.out.println(today.getTime());        // 1770088983812    // 시각을 밀리초 단위로 표현
System.out.println(today.getYear());        // 126              // 연도에서 1900을 뺀 값(1900부터 셈)
System.out.println(today.getYear() + 1900); // 2026             // 우리가 보는 연도
System.out.println(today.getMonth());       // 1                // 달에서 1을 뺀 값(0부터 셈)
System.out.println(today.getMonth() + 1);   // 2                // 우리가 보는 달
System.out.println(today.getDate());        // 3                // 현재 날짜
System.out.println(today.getHours());       // 12               // 시간정보
System.out.println(today.getMinutes());     // 23               // 분정보
System.out.println(today.getSeconds());     // 3                // 초정보
```

- 사용하기 좋진 않다.

#### `SimpleDateFormat` 클래스

```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd(E) HH:mm:ss");
System.out.println(sdf.format(today));  // 2026-02-03(화) 12:30:14
```

- 주어진 날짜 데이터를 정해진 포맷으로 나타내 준다.
    - 이때 포맷 문자를 이용함


### `java.time` 패키지

- Java 1.8부터 날짜, 시간을 나타내는 `time` 패키지를 제공하기 시작함

- `java.util.Date`의 불편한 점들을 많이 개선해서 나옴

- 생성자를 직접 호출할 수 없고, 제공되는 static 메소드를 이용해서 객체를 만들 수 있다.
    - 생성자의 접근제한이 `private`으로 되어 있음

#### `java.time.LocalDateTime`

- 날짜, 시간 정보를 모두 저장 가능한 객체를 생성하는 클래스

    ```java
    // LocalDateTime now = new LocalDateTime();  // 생성자로 직접 생성 불가
    LocalDateTime now = LocalDateTime.now();  // 제공되는 정적 메소드를 이용해서 만들어야 함
    System.out.println(now);  // 2026-02-03T12:43:55.970445300
    ```

- `Date`와는 표기법이 다르다. 국제표준 날짜 시간 데이터 표기법(ISO 8601)을 따른다.

- 나노초(ns)까지의 시간을 표기한다.

- 자동으로 현재 위치의 시간대를 기준으로(Asia/Seoul) 맞춰주는데, `ZonedDateTime`과 `Zoneid.of`를 쓰면 다른 시간대에서의 현재시각을 알 수 있다.

- 특정 시각 정보를 저장한 객체를 생성할 때는 `.of()`를 이용한다.

    ```java
    LocalDateTime when = LocalDateTime.of(2026, 2, 3, 15, 8, 30);
    System.out.println(when);  // 2026-02-03T15:08:30
    ```

    - `Date` 때와는 다르게 연도에서 1900을 빼거나, 월에서 1을 뺄 필요가 없다. 좀 더 사용하기 편리함

- `.parse()`를 이용해서 문자열을 시계열 데이터로 바꿀 수도 있다.

    ````java
    LocalDateTime when2 = LocalDateTime.parse("2025-12-31T12:30:11");
    System.out.println(when2);  // 2025-12-31T12:30:11
    ```

- 날짜, 시간 정보를 출력할 때는 `.getXXX()`를 이용

    ```java
    System.out.println(now.getYear());          // 2026
    System.out.println(now.getMonth());         // FEBRUARY (enum)
    System.out.println(now.getMonthValue());    // 2
    System.out.println(now.getDayOfYear());     // 34       // 현재 연도에서 몇번째 날짜인지
    System.out.println(now.getDayOfMonth());    // 3        // 현재 월에서 몇번째 날짜인지
    System.out.println(now.getDayOfWeek());     // TUESDAY (enum)
    ```

- 날짜, 시간을 조작할 때는 `.plusXXX()`, `.minusXXX()` 이용

    ```java
    System.out.println(now.minusYears(3));  // 2023-02-03T14:30:16.725853400
    System.out.println(now.plusDays(10));   // 2023-02-03T14:30:16.725853400
    System.out.println(now.plusYears(2).minusDays(3).plusMonths(4).minusWeeks(5));  // 2028-04-26T14:31:26.461321200
    ```

- 날짜, 시간을 비교할 때는 `.isAfter()`, `isBefore()` 이용함

    ```java
    System.out.println(now.isAfter(now.plusDays(3)));   // false
    System.out.println(now.isEqual(now.plusDays(3)));   // false
    System.out.println(now.isBefore(now.plusDays(3)));  // true
    ```
    - `Date`를 이용할 때보다 압도적으로 편리하다

- 원하는 형태로 출력하고 싶을 때 `DateTimeFormatter`를 이용해 포매팅도 가능
    ```java
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(E) HH:mm:ss");
    System.out.println(formatter.format(now));  // 2026-02-03(화) 15:09:07
    System.out.println(now.format(formatter));  // 2026-02-03(화) 15:09:07
    System.out.println(now.format(DateTimeFormatter.BASIC_ISO_DATE));  // 20260203
    ```
    - 표준 포맷으로 표기하는 것도 가능하다. 

#### `java.time.LocalDate`, `java.time.LocalTime`

- 각각 날짜/시간 정보만 저장 가능한 객체를 생성하는 클래스
    ```java
    // LocalDate
    LocalDate today = LocalDate.now();
    LocalDate today = LocalDate.of(2026, 2, 3);
    LocalDate today = LocalDate.parse("2026-02-03");
    System.out.println(today);  // 2026-02-03

    // LocalTime
    LocalTime today = LocalTime.now();
    System.out.println(today);  // 14:43:31.456602600
    ```

- `LocalDateTime` 객체는 `LocalDate`, `LocalTime`로 변환이 가능하다.
    - `.toLocalDate`, `.toLocalTime` 을 이용함

    ```java
    LocalDateTime now = LocalDateTime.now()
    System.out.println(now);              // 2026-02-03T12:43:55.970445300
    System.out.println(now.toLocalDate);  // 2026-02-03
    System.out.println(now.toLocalTime);  // 12:43:55.970445300
    ```
