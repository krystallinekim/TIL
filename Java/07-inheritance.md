# 상속(Inheritance)

- 객체 지향 프로그래밍에서 상속은 부모 클래스의 멤버(필드, 메서드)를 자식 클래스에게 물려주는 것을 말한다.
    - 부모에 해당하는 클래스를 부모 클래스 또는 상위, 슈퍼 클래스라 한다.
    - 자식에 해당하는 클래스를 자식 클래스 또는 하위, 서브 클래스라 한다.

- 상속을 통해서 다른 클래스가 가지고 있는 멤버를 직접 만들지 않고 상속을 받음으로써 자신의 멤버처럼 사용할 수 있다.

## 상속의 장점

- 코드의 중복을 줄여준다.
    - 잘 만들어놓은 클래스를 재사용 가능
    - 적은 양의 코드로 새로운 클래스 작성이 가능하다.

- 상속을 통해서 연관된 클래스들에 대한 공통적인 규약을 정의할 수 있다.

- 상속을 통해서 코드를 공통적으로 관리하기 때문에 코드의 추가, 수정 및 삭제가 용이하다.

## 클래스의 상속

- 클래스의 상속 구문은 `[접근 제한자] class 자식클래스명 extends 부모클래스명 { ... }`이다.
    - 자식 클래스가 원하는 부모 클래스를 선택해 상속받는다

- 자바는 다중 상속을 지원하지 않는다.
    - 여러 클래스를 상속받을 수 없다

- 부모 클래스에서 private 접근 제한을 갖는 필드와 메소드는 상속에서 제외된다.
    - private 접근제한자는 선언된 클래스에서만 쓸 수 있기 때문
    - default는 자식 클래스가 같은 패키지에 있을 때만 상속됨
    
    ```java
    // 부모 클래스
    public class Animal {
        ...
    }
    ```
    
    ```java
    // 자식 클래스
    public class Dog extends Animal {
        ...
    }
    ```
    

## 자식 객체 생성

- 생성자는 객체 생성을 위한 특별한 메소드로 상속되지 않는다.

- 자식 클래스의 객체를 생성하면 **부모 클래스의 생성자가 먼저 호출**되면서 부모 객체가 생성되고 자식 객체가 생성된다.

- 자식 클래스의 생성자 안에서 `super()`를 통해서 부모 클래스의 생성자를 호출한다.(생략 시 기본 부모의 생성자 사용)
    - `super()`는 자식 클래스의 생성자 첫 줄에 위치해야 한다. - 부모 객체가 먼저 생성되어야 하니까
    - `this()`가 자기 자신을 호출하는 것처럼, `super()`로 부모 클래스를 호출하는 것 
    
    1. 매개 변수가 있는 생성자를 이용해 초기값을 전달할 수 있다. (가장 일반적인 방법)

    2. 부모 객체를 기본생성자(`super()`)로 생성한 뒤, 부모의 setter 메소드를 이용해 초기값을 설정할 수도 있다.
        - 부모 객체를 `super`로 부를 수도 있지만, `this`, 아니면 아예 생략해서 메소드만 사용할 수도 있음

    3. 부모 필드가 `protected`일 경우, 부모 필드에 직접 접근해서 초기화할 수도 있다.
    
    ```java
    // 부모 클래스
    public class Animal {
        // 필드
        protected String name;
        protected String kinds;
        protected int age;
        
        // 생성자
        public Animal() {
        }
        
        public Animal(String name, String kinds, int age) {
            this.name = name;
            this.kinds = kinds;
            this.age = age;
        
        // setter
        public void setName(String name) {this.name = name;}
        public void setKinds(String kinds) {this.kinds = kinds;}
        public void setAge(String age) {this.age = age;}

        }
    }
    ```
    
    ```java
    // 자식 클래스
    public class Dog extends Animal {
        private int weight;
        
        public Dog() {
            // super();  // 생략 시 자동으로 생성한다.
        }
        
        public Dog(String name, String kinds, int weight) {
            // 1. 매개변수로 초기화
            super(name, kinds, age);  // Animal(name, kinds, age) 생성자를 호출 -> 자식 객체의 name, kinds, age에 그 값을 초기값으로 설정
        
            // 2. 메소드를 이용해 초기화
            super.setName(name);
            this.setKinds(kinds);
            setAge(age);

            // 3. 부모의 필드에 직접 접근 (부모 필드가 protected일 경우)
            this.name = name;
            super.kinds = kinds;
            super.age = age;

            // 필드 추가 초기화
            this.weight = weight;
        }
    }
    ```
    

## 메소드 오버라이딩(Overriding)

- 부모 클래스의 메소드를 자식 클래스에서 다시 **재정의**해서 사용하는 것을 메소드 오버라이딩이라 한다.
    - `@Override` 어노테이션을 붙여 준다. (생략 가능, 컴파일러에게 재정의된 메소드라고 알려주는 메타데이터)
    - 자식 클래스에서 메소드 오버라이딩은 부모의 메소드와 동일한 선언부(접근제한자, 반환타입, 매개변수)를 가져야 한다.
        - 매개변수가 다른건 오버라이딩(재정의)이 아니라 오버로딩(덮어쓰기)

- 부모 클래스의 메소드가 private 접근 제한을 가지면 자식 클래스는 메소드를 오버라이딩 할 수 없다.
    - 오버라이딩 된 메소드는 부모 클래스의 메소드보다 좁은 범위의 접근제한자만 가질 수 있다.

    ```java
    // 부모 클래스
    public class Animal {
        ...

        public String bark() {
            return "짖는다.";
        }
    }
    ```
    
    ```java
    // 자식 클래스
    public class Dog extends Animal {
        ...

        @Override
        public String bark() {
            return "멍멍~ 짖는다.";
        }
    }
    ```
    
- 자식 객체에서 오버라이딩된 메소드를 호출하면 부모 객체의 메소드가 아닌 오버라이딩된 자식 메소드가 호출된다.
    - 부모 객체의 메소드는 삭제되는 것이 아닌 오버라이딩된 메소드에 의해 가려지게 된다.(없어지는건 아님)
    
    ```java
    Dog dog = new Dog();
    
    System.out.println(dog.bark()); // "멍멍~ 짖는다." 출력
    ```
    
- 자식 클래스 내부에서 오버라이딩된 부모 클래스의 메소드를 호출해야 하는 상황이 발생한다면 `super`를 통해서 부모 메소드를 호출할 수 있다.
    
    ```java
    // 부모 클래스
    public class Animal {
        ...
        
        public String bark() {
            return "짖는다.";
        }
    }
    ```
    
    ```java
    // 자식 클래스
    public class Dog extends Animal {
        ...
    
        @Override
        public String bark() {
            return "멍멍~ " + super.bark();  // 멍멍~ 짖는다.
        }
    }
    ```


## Object 클래스

- 모든 클래스는 Object 클래스의 자손이다. `public class Animal [extends Object] { ... }`
    - `Object` 클래스의 메소드를 전부 사용할 수 있다.

### `.toString()`

객체의 문자열 정보를 리턴하는 메소드

- 기본적으로 Object 클래스에서는 `클래스명@해시코드`를 리턴한다.(해시코드는 자바에서 가상메모리의 주소)
    - `com.beyond.inheritance.practice.Book@79fc0f2f`

- 자식 클래스에서 원하는 유용한 문자열 정보를 리턴하도록 재정의할 수 있다. 
    - `Alt+Ins`에서 `tostring()`을 선택하고, 필드를 고르면 `클래스명{필드='값'...}`처럼 재정의해준다.
        ```java
        @Override
        public String toString() {
            return "Book{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", price=" + price +
                    '}';
        }
        ```
        ```java
        Book book1 = new Book("자바의 정석", "남궁성", 36000);
        
        System.out.println(book1);  // Book{title='자바의 정석', author='남궁성', price=36000}
        ```

    - 객체의 데이터를 문자열로 만들어서 보기 편하게 해줌

- 기본적으로 객체의 참조변수를 출력하면 내부적으로 `toString()` 내용을 출력한다.
    - 클래스에서 `toString()`을 재정의하면, 재정의한 메소드가 실행된다.

### `.equals()`

매개값으로 전달받은 객체와 자신이 동일한 객체인지 확인하는 메소드

- 동일한 객체라면 true, 그렇지 않다면 false를 리턴한다.

- 기본적으로 `Object` 클래스에서는 주소값을 비교한다. 
    - 참조변수에는 객체의 주소가 들어있는데, 객체를 만들 때 전부 `new`를 통해 만들었으므로 전부 주소가 다르다.
        ```java
        Book book1 = new Book("자바의 정석", "남궁성", 36000);
        Book book2 = new Book("자바의 정석", "남궁성", 36000);
        Book book3 = new Book("혼자 공부하는 자바", "신용권", 28000);

        System.out.println(book1 == book2);         // false, 주소값 비교
        System.out.println(book1.equals(book2));    // false, 주소값 비교
        System.out.println(book1.equals(book3));    // false, 주소값 비교
        ```

- 자식 클래스에서 객체의 필드값들을 비교하도록 재정의할 수 있다.
    - book1, book2는 서로 물리적으로 주소값이 달라 다른 객체지만, 필드값이 같아 의미적으로는 같은 객체라고 표현할 수 있다.
        ```java
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;  // null이면 바로 false, 클래스가 달라도 false
            Book book = (Book) o;  // 매개값으로 받은 객체를 형변환
            return price == book.price && Objects.equals(title, book.title) && Objects.equals(author, book.author);  // 필드값이 같은지 확인, 다 같아야 true
        }
        ```
        ```java
        System.out.println(book1 == book2);         // false, 주소값 비교
        System.out.println(book1.equals(book2));    // true, 필드값 비교
        System.out.println(book1.equals(book3));    // true, 필드값 비교
        ```

### `.hashCode()`

객체를 식별할 수 있는 하나의 정수값

- 기본적으로 `Object` 클래스에서는 객체의 메모리 주소를 이용해서 해시값을 생성한다.
  
- 자식 클래스에서 객체가 가지는 필드의 값들이 같다면, 같은 해시코드를 리턴하도록 재정의
    ```java
    @Override
    public int hashCode() {
        return Objects.hash(title, author, price);
    }
    ```
    ```java
    System.out.println(book1.hashCode());  // -1961489797
    System.out.println(book2.hashCode());  // -1961489797
    System.out.println(book3.hashCode());  // -1579165619
    ```

- `equals()`, `hashCode()`는 나중에 Set 구현 시 중복값 처리할 때 사용함


## final 클래스와 final 메소드

- final 키워드는 필드뿐만 아니라 클래스와 메소드 선언 시에 사용할 수 있다.
    - final 키워드를 클래스 선언에 붙이게 되면 이 클래스는 **상속할 수 없는 클래스**가 된다.
        
        ```java
        // java.lang에서 제공하는 String 클래스
        public final class String {
            ...
        }
        ```
        
    - final 키워드를 메소드 선언에 붙이게 되면 이 메소드는 **재정의할 수 없는 메소드**가 된다.
        
        ```java
        // java.lang에서 제공하는 Object 클래스
        public class Object {
            public final void wait() {
                ...
            }
        }
        ```

## 다형성(Polymorphism)

- 다형성은 같은 타입이지만 실행 결과가 다양한 객체를 이용할 수 있는 성질을 말한다.
- 부모 클래스 타입의 참조 변수에 자식 객체들을 대입하여 다룰 수 있는 것이 다형성의 기본 개념이다.

### 업 캐스팅(Up Casting)

- 업 캐스팅은 자식 타입의 객체가 부모 타입의 객체로 형 변환되는 것을 말한다.
- 업 캐스팅은 자동으로 형 변환이 일어나기 때문에 부모 클래스 타입의 참조 변수가 모든 자식 객체들을 별도의 형 변환 없이 대입 받을 수 있다.
- 부모 타입으로 업 캐스팅된 이후에는 부모 클래스에 선언된 필드와 메소드만 접근이 가능하다.
- 단, 예외가 있는데 부모 타입의 메소드가 오버라이딩되었다면 오버라이딩된 메소드가 대신 호출된다.
    
    ```java
    // Dog 클래스 타입의 객체를 Animal 클래스 타입의 참조 변수에 대입
    Animal animal = new Dog();
    
    // 자식 클래스에서 오버라이딩 된 코드가 실행된다.
    System.out.println(animal.bark()); // "멍멍~ 짖는다." 출력
    ```
    
    ```java
    // Cat 클래스 타입의 객체를 Animal 클래스 타입의 참조 변수에 대입
    Animal animal = new Cat();
    
    // 자식 클래스에서 오버라이딩 된 코드가 실행된다.
    System.out.println(animal.bark()); // "야옹~ 운다." 출력
    ```
    

### 다운 캐스팅(Down Casting)

- 다운 캐스팅은 부모 타입의 객체가 자식 타입의 객체로 형 변환되는 것을 말한다.
- 다운 캐스팅은 자동으로 형 변환이 일어나지 않기 때문에 형 변환 연산자를 사용해서 형 변환을 해야 한다.
    
    ```java
    Animal animal = new Dog();
    // 클래스 간의 형 변환은 반드시 상속 관계에 있는 클래스들끼리만 가능하다.
    Dog dog = (Dog) animal;
    ```
    
- 부모 클래스 타입 참조 변수가 실제로 참조하는 객체를 확인하지 않고 강제 형 변환을 시도하면 ClassCastException 예외가 발생할 수 있다.
- 객체가 어떤 클래스의 인스턴스인지 instanceof 연산자를 사용해서 확인할 수 있다.
    
    ```java
    // animal이 참조하는 객체가 Dog 클래스로 생성된 객체이면 true 아니면 false
    if(animal instanceof Dog) {
        ...
    // animal이 참조하는 객체가 Cat 클래스로 생성된 객체이면 true 아니면 false
    } else if (animal instanceof Cat){
        ...
    }
    ```
    

## 2. 추상 클래스(Abstract Class)

- 클래스들의 공통적인 특성을 추출해서 선언한 클래스를 추상 클래스라고 한다.
- 추상 클래스를 부모 타입으로, 객체로 생성될 실체 클래스가 자식 타입으로 구현되어 추상 클래스의 모든 특성을 물려받을 수 있다.
- 추상 클래스는 공통되는 필드와 메소드를 추출해서 만들었기 때문에 객체를 직접 생성해서 사용할 수 없고 부모 클래스로만 사용된다.

### 2.1. 추상 클래스 선언

- 추상 클래스의 선언 구문은 `[접근 제한자] abstract class 클래스명 { ... }`이다.
- 추상 클래스 내에 필드, 메소드, 생성자를 포함할 수 있다.
- 추상 클래스는 객체로 생성이 안되지만 참조 변수의 타입으로는 사용이 가능하다.
    
    ```java
    public abstract class Animal {
        private String name;
        private String kinds;
        
        public Animal() {
        }
        
        public Animal(String name, String kinds) {
            this.name = name;
            this.kinds = kinds;
        }
        
        public String bark() {
            return "짖는다.";
        }
    }
    ```
    
    ```java
    Animal animal = new Animal(); // 에러 발생
    Animal animal = new Dog(); // 다형성 적용 가능
    ```
    

### 2.2. 추상 메소드(Abstract Method)

- 추상 클래스에 선언된 메소드가 자식 클래스마다 실행 내용이 달라야 하는 경우 추상 메소드를 선언할 수 있다.
- 추상 메소드는 추상 클래스에서 선언할 수 있고 메소드의 선언부만 있는 메소드의 실행 내용인 중괄호({})가 없는 메소드이다.
- 추상 클래스를 상속하는 자식 클래스는 반드시 추상 메소드를 오버라이딩 해야 한다.
- 오버라이딩하지 않으면 컴파일 에러가 발생하는데 자식 클래스에서 내용을 채우도록 강제화한다.
- 추상 메소드의 선언 구문은 `[접근 제한자] abstract 반환형 메소드명([매개변수]);`이다.
    
    ```java
    public abstract class Animal {
        private String name;
        private String kinds;
        
        public Animal() {
        }
        
        public Animal(String name, String kinds) {
            this.name = name;
            this.kinds = kinds;
        }
        
        // 추상 메소드 선언
        public abstract String bark();
    }
    ```
    
    ```java
    // 자식 클래스
    public class Dog extends Animal {
        private int weight;
        
        // 생성자 선언
        ...
        
        // 추상 메소드는 반드시 오버라이딩 해야 한다.
        @Override
        public String bark() {
            return "멍멍~ 짖는다.";
        }
    }
    ```
    

## 3. 인터페이스(Interface)

- 인터페이스는 이를 구현하는 클래스들이 반드시 구현해야 하는 공통 기능을 정의한다.
- 인터페이스를 통해 객체를 사용하면, 실행 코드의 변경 없이 사용하는 객체를 다른 구현체로 교체할 수 있다.
        

### 3.1. 인터페이스 선언

- 인터페이스의 선언 구문은 `[접근 제한자] interface 인터페이스명 { ... }`이다.
- 인터페이스 선언은 class 키워드 대신에 interface 키워드를 사용한다.
- 인터페이스는 선언된 필드는 모두 public static final의 특성을 갖는다.
- 인터페이스에 선언된 메소드는 모두 public abstract의 특성을 갖는다.
- 자바 8부터 디폴트 메소드와 정적 메소드도 선언이 가능하다.
    
    ```java
    public interface Runable {
        void run(); // public abstract 생략 가능
    }
    ```
    

### 3.2. 인터페이스 구현

- 인터페이스를 구현하는 클래스는 클래스 선언부에 implements 키워드를 추가하고 인터페이스명을 명시해야 한다.
- 인터페이스를 구현하는 클래스는 인터페이스에 정의된 추상 메소드를 반드시 오버라이딩 해야 한다.
    
    ```java
    // 인터페이스 구현 방법
    public class Cat implements Runable {
        ...
        
        @Override
        public void run() {
            ...
        }
    }
    ```
    
- 상속과 다르게 인터페이스는 다중 구현이 가능하다.
    
    ```java
    // 인터페이스의 다중 구현 시 콤마(,)로 구분한다.
    public class Cat implements Runable, Swimable {
            ...
        @Override
        public void run() {
            ...
        }
        
        @Override
        public void swim() {
            ...
        }
    }
    ```
    
- 인터페이스를 구현하는 클래스로 객체를 생성 후 구현된 메소드를 호출할 수 있다.
    
    ```java
    Cat cat = new Cat();
    
    cat.run();
    cat.swim();
    ```
    

### 3.3. 인터페이스 상속

- 인터페이스도 다른 인터페이스를 상속할 수 있다.
- 인터페이스는 클래스와 달리 다중 상속을 허용한다.
- 인터페이스를 상속하는 구문은 `[접근 제한자] interface 하위인터페이스 extends 상위인터페이스1, 상위인터페이스2 { ... }`이다.
    
    ```java
    public interface Basic extends Runable, Swimable {
        void eat();
    }
    ```
    
- 하위 인터페이스를 구현하는 클래스는 하위 인터페이스의 추상 메소드 뿐만 아니라 상위 인터페이스들의 모든 추상 메소드들을 오버라이딩 해야 한다.
    
    ```java
    public class Cat implements Basic {
            ...
        
        @Override
        public void eat() {
            ...
        }
        
        @Override
        public void run() {
            ...
        }
        
        @Override
        public void swim() {
            ...
        }
    }
    ```