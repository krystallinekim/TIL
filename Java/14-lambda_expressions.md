# 람다식(Lambda Expressions)

## 람다식

- 자바에서 함수적 프로그래밍을 위해서 자바 8부터 람다식을 지원한다.
    - 자바에서 함수 역할을 하는 메소드를 이용하려면, 객체를 생성해서 메소드를 호출해야 한다.
    - 단독으로 함수를 사용할 수는 없다

- 람다식은 매개변수를 가지는 함수와 같은 코드 블록이지만 런타임 시에는 **인터페이스의 구현 객체**를 생성한다.

- 람다식을 사용하면 코드가 간결해지고, 컬렉션의 요소들을 필터링하거나 매핑해서 원하는 결과를 쉽게 가져올 수 있다.
    
    ```java
    ([자료형 매개변수, ...]) -> {  // 매개변수는 선택
        // 실행 코드
    
        [return 반환값;]  // 반환값도 선택
    };
    ```

- 예시

    ```java
    () -> { ... }                           // 매개변수도, 반환값도 없는 코드

    (int a) -> { System.out.println(a) }    // 정수 a를 받아서 출력하는 코드
    (a) -> { System.out.println(a) }        // 매개변수의 데이터 타입도 추론 가능해서 생략 가능
    a -> System.out.println(a)              // 매개변수가 하나면 소괄호를, 실행코드가 한줄이면 중괄호도 생략 가능

    (int a, int b) -> { return a + b; }     // 정수 a와 b를 받아서 더해주는 코드
    (a, b) -> return a + b;                 // 매개변수가 2개라 소괄호는 생략 불가
    (a, b) -> a + b;                        // return 하나만 있으면 생략 가능함
    ```

## 함수적 인터페이스(Functional Interface)

- 람다식은 인터페이스 타입의 변수에 대입되고 람다식은 인터페이스의 구현 객체를 생성한다.

- **하나의 추상 메소드가 선언된 인터페이스만** 람다식으로 구현 객체를 생성할 수 있는데 이러한 인터페이스를 함수적 인터페이스라고 한다.

- 함수적 인터페이스를 선언할 때 `@FunctionalInterface` 어노테이션을 붙이면 두 개 이상의 추상메소드가 선언되지 않도록 컴파일러가 체크해 준다.
    
    ```java
    @FunctionalInterface
    public interface Runnable {
    
        public abstract void run();
    }
    ```
    - `Runnable`, `Comparator` 같은게 대표적인 함수적 인터페이스 - 람다식으로 생성 가능하다
    
- 함수적 인터페이스의 모양(매개변수, 반환값)을 보고, 맞춰서 람다식을 작성해야 한다.
    ```java
    @FunctionalInterface
    public interface FunctionalC {
        int execute(int x, int y);
    }
    ```
    ```java
    public void method1() {
        FunctionalC fc = (a, b) -> {
            System.out.printf("매개변수가 (%d, %d)로 2개 있고, 반환값도 있는 람다식\n", a, b);
            return a + b;
        };
        System.out.println(fc.execute(2, 3));  // 함수적 인터페이스 객체를 생성하고, 따로 호출해서 실행해야 한다.
    }
    ```
- 람다식에서 `this`를 사용할 수 있는데, 이 때 `this`는 람다식이 실행하는 객체(`pfi`)를 의미한다.
- 익명 구현 객체에서 `this`는 구현한 객체 `fi`의 참조
    ```java
    // 메인
    PracticeAFI pfi = new PracticeAFI();
    pfi.method(300);
    ```
    ```java
    public class PracticeAFI{
        private int number = 100;  // 필드

        public void method(int arg) {
            int number = 200;
            FunctionalA fi;

            // 람다식
            fi = () -> {
                System.out.println(arg);            // 300, 매개변수
                System.out.println(number);         // 200, 지역변수
                System.out.println(this.number);    // 100, this는 람다식이 실행하는 객체(pfi)의 참조 -> 클래스 전체의 필드
            };

            fi.execute();
            System.out.println();
            
            // 익명구현객체
            fi = new FunctionalA() {
                int number = 400;

                @Override
                public void execute() {

                    System.out.println(arg);            // 300, 매개변수
                    System.out.println(number);         // 400, 지역변수
                    System.out.println(this.number);    // 400, 익명구현객체에서 this는 객체 fi의 참조 -> 
                                                        // 메소드의 필드가 아니라, 지금 구현한 객체 안의 지역변수가 필요하다.
                }
            }
        }
    }
    ```

- 람다식에서 지역 변수, 매개 변수를 사용할 때에는 `final` 혹은 `유사 final`(초기화 이후에 값을 한번도 변경하지 않은 변수) 이어야 한다.
    - 지역변수와 매개변수는 읽는 것은 허용되지만, 한번 선언되고 나서는 람다식 내부/외부에서 변경할 수 없다.
    - 나중에 멀티스레드 환경에서 문제가 생긴다.


### 표준 함수적 인터페이스

- 자바 8부터 빈번히 사용되는 함수적 인터페이스는 `java.util.function` 표준 API 패키지로 제공된다.

- 표준 함수적 인터페이스는 **용도에 따라** 크게 `Consumer`, `Supplier`, `Function`, `Operator`, `Predicate`로 구분된다.

- 직접 만들어서 사용해도 되지만, 이 용도의 함수는 이 인터페이스를 받아오면 된다는 뜻

#### **Consumer**

```java
@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);
}
```

- 매개값은 있고, 리턴값이 없는 `.accept()` 추상 메소드를 갖는다.

- 매개값을 소비하는 역할로, 람다식도 매개값을 소비하도록 만들어 주면 된다.

    ```java
    // Consumer<T>: T 타입의 객체를 받아서 소비
    Consumer<String> consumer = str -> {
        System.out.println(str);
    };
    consumer.accept("Consumer");  // Consumer
    ```

    ```java
    // BiConsumer<T, U>: T, U 타입의 객체를 받아서 소비
    BiConsumer<String, String> biConsumer = (str1, str2) -> System.out.println(str1 + str2);
    biConsumer.accept("bi", "Consumer");  // biConsumer
    ```
    - `Consumer<T>`(타입 하나), `BiConsumer<T, U>`(타입 2개), `IntConsumer`(정수만), `ObjDoubleConsumer<T>`(객체 + 실수값) 등의 다양한 Consumer 인터페이스가 존재한다.

#### **Supplier**

```java
@FunctionalInterface
public interface Supplier<T> {

    T get();
}
```

- 매개값이 없고, 리턴값만 있는 `.get()` 추상메소드를 갖는다.

- 메소드를 호출한 곳으로 데이터를 공급(리턴)하는 역할을 한다.

    ```java
    // Supplier<T>: T 타입의 객체를 리턴
    Supplier<String > supplier = () -> {
        return "Supplier";
    };
    System.out.println(supplier.get());  // Supplier
    ```


#### **Function**

```java
@FunctionalInterface
public interface Function<T, R> {

    R apply(T t);  // R타입으로 리턴
}
```

- 매개값과 리턴값이 있는 `.apply()` 추상메소드를 가진다.

- 주로 매개값(`T` 타입)을 받아서 리턴값(`R`타입)으로 변환(매핑)할 때 주로 사용한다.

    ```java
    Function<Integer, Double> function = a ->  (double) a;
    System.out.println(function.apply(10));  // 10.0
    ```
    ```java
    BiFunction<Integer, Double, String> biFunction = (a, b) ->  String.format("%d, %.2f", a, b);
    System.out.println(biFunction.apply(10, 3.14));  // 10, 3.14
    ```
    - `Function<T,R>`(T→R), `BiFunction<T,U,R>`(T,U→R) 등의 다양한 인터페이스가 있다.
        - 좀 종류가 많다. 주로 특정 데이터 타입에서 특정 데이터 타입으로 전환해주는 함수들(`DoubleToIntFunction`, `LongFunction`, `ToDoubleFunction`)
    ```java
    ToIntFunction<String> toIntFunction = value -> Integer.parseInt(value);
    System.out.println(toIntFunction.applyAsInt("123"));
    ```


#### **Operator**

```java
@FunctionalInterface
public interface IntBinaryOperator {

    int applyAsInt(int left, int right);
}
```
- 그냥 `Operator` 인터페이스는 없다. `UnaryOperator`가 가장 기본

- 매개값, 리턴값이 있는 `.applyXXX()` 추상메소드를 가진다.

- 똑같이 `.apply()`를 쓰지만, `Function`이 매핑 위주였다면, `Operator`는 매개값을 이용해 연산을 하고 같은 타입으로 결과를 리턴한다.

    ```java
    IntBinaryOperator intBinaryOperator = (a, b) -> a * b;
    System.out.println(intBinaryOperator.applyAsInt(3, 4));  // 12

    IntUnaryOperator intUnaryOperator = a -> a * a * a;
    System.out.println(intUnaryOperator.applyAsInt(10));  // 1000
    ```

    - `IntBinaryOperator`: 두 개의 int값을 연산해서 int값으로 리턴한다.
    - `IntUnaryOperator`: int값 하나를 연산해서 int값을 리턴한다.

#### **Predicate**

```java
@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);
}
```

- 매개변수와 **논리값**을 리턴하는 `test()` 추상 메소드를 가지고 있다.

    ```java
    Predicate<String> isName = str -> Objects.equals(str, "홍길동");
    System.out.println(isName.test("홍길동"));  // true
    System.out.println(isName.test("이몽룡"));  // false

    BiPredicate<String, Integer> biPredicate = (str, i) -> str.length() == i;
    System.out.println(biPredicate.test("홍길동", 3));  // true

    IntPredicate isPositive = i -> i > 0;
    System.out.println(isPositive.test(-10));  // false
    ```

    - 매개값을 조사해서 `true`/`false`로 리턴한다.
        - `Predicate<T>`: T 타입의 객체를 조사해서 true, false를 리턴한다.
        - `BiPredicate<T, U>`: T, U 타입의 객체를 조사해서 true, false를 리턴한다.
        - `IntPredicate`: int 객체를 조사해서 true, false를 리턴한다.

## 1. 메소드 참조 **(Method Reference)**

- 자바 8부터 도입된 문법으로, 기존 메소드 또는 생성자를 참조하여 함수적 인터페이스의 구현을 간결하게 표현한다.
- 람다식에서 매개변수 전달과 메소드 호출이 단순히 이어지는 경우, 메소드 참조를 사용해 불필요한 매개변수 표현을 생략할 수 있다.

## 2. 정적 메소드 참조

- 클래스 이름 뒤에 :: 붙이고 정적 메소드 이름을 기술하면 된다.
    
    ```java
    IntBinaryOperator intBinaryOperator = Math::max;
    ```
    

## 3. 객체의 메소드 참조

- 참조 변수 뒤에 :: 붙이고 메소드 이름을 기술하면 된다.
    
    ```java
    Consumer<String> consumer = System.out::println;
    ```
    

## 4. 매개변수의 메소드 참조

- 매개변수의 클래스 이름 뒤에 :: 붙이고 메소드 이름을 기술하면 된다.
    
    ```java
    Function<Student, String> function = Student::getName;
    ```
    

## 5. 생성자 참조

- 클래스 이름 뒤에 :: 붙이고 new 연산자를 기술하면 된다.
    
    ```java
    Supplier<Student> studentSupplier = Student::new;
    ```

## 1. 스트림(Stream) API

- 스트림은 자바 8부터 추가된 기능으로 컬렉션(배열)의 저장 요소들을 하나씩 참조해서 람다식으로 처리할 수 있도록 해주는 반복자이다.
- 스트림은 내부 반복자를 사용해서 병렬 처리와 중간 처리, 최종 처리 작업을 수행할 수 있다.

## 2. 스트림의 생성

- java.util.stream 패키지에 존재하고 BaseStream 인터페이스를 부모로 해서 자식 인터페이스들이 상속 관계를 이루고 있다.
    
    ```java
    public interface BaseStream<T, S extends BaseStream<T, S>> extends AutoCloseable {
        ...
    }
    
    public interface Stream<T> extends BaseStream<T, Stream<T>> {
        ...
    }
    
    public interface IntStream extends BaseStream<Integer, IntStream> {
        ...
    }
    
    public interface LongStream extends BaseStream<Long, LongStream> {
        ...
    }
    
    public interface DoubleStream extends BaseStream<Double, DoubleStream> {
        ...
    }
    ```
    
- IntStream, LongStream의 range(), rangeClosed() 메소드를 이용해서 숫자 범위로 스트림을 생성할 수 있다.
    
    ```java
    IntStream stream = IntStream.range(1, 10);
    ```
    
- Arrays.stream(배열) 메소드를 이용해서 배열로부터 스트림을 생성할 수 있다.
    
    ```java
    String[] names = {"임꺽정", "홍길동", "이몽룡"};
    
    Stream<String> stream = Arrays.stream(names);
    ```
    
- 컬렉션의 stream() 메소드를 이용해서 컬렉션으로부터 스트림을 생성할 수 있다.
    
    ```java
    List<String> names = Arrays.asList("임꺽정", "홍길동", "이몽룡");
    
    Stream<String> stream = names.stream();
    ```
    

## 3. 중간 처리 메소드

- 스트림은 데이터의 필터링, 정렬, 매핑 등의 처리를 할 수 있는 중간 처리 메소드를 제공한다.
- 리턴 타입이 스트림이라면 중간 처리 메소드이다.
    
    ```java
    int[] array = {1, 2, 3, 4, 5, 6};
    
    Arrays.stream(array)
          .filter(value -> value % 2 == 0) // 중간 처리 메소드
          .forEach(value -> System.out.println(value)); // 최종 처리 메소드
    ```
    

## 4. 최종 처리 메소드

- 스트림은 데이터의 집계, 수집, 반복 처리 등의 처리를 할 수 있는 최종 처리 메소드를 제공한다.
- 리턴 타입이 기본 타입이거나 Optional 타입이라면 최종 처리 메소드이다.
    
    ```java
    int sum = 0;
    int[] array = {1, 2, 3, 4, 5, 6};
    
    sum = Arrays.stream(array).sum(); // 최종 처리 메소드
    ```