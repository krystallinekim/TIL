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

- 모든 클래스는 Object 클래스의 후손이다.
    - `public class Animal extends Object { ... }`

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

- 자식 클래스의 생성자 안에서 `super()`를 통해서 부모 클래스의 생성자를 호출한다.(생략 가능)
    - `super()`는 자식 클래스의 생성자 첫 줄에 위치해야 한다. - 부모 객체가 먼저 생성되어야 하니까
    - `this()`가 자기 자신을 호출하는 것처럼, `super()`가 부모 클래스를 호출하는 것 

    ```java
    // 부모 클래스
    public class Animal {
        private String name;
        private String kinds;
        
        public Animal() {
        }
        
        public Animal(String name, String kinds) {
            this.name = name;
            this.kinds = kinds;
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
            super(name, kinds);  // Animal(name, kinds) 생성자를 호출 -> 자식 객체의 name, kinds에 그 값을 초기값으로 설정
        
            this.weight = weight;
        }
    }
    ```
    

## 5. 메소드 오버라이딩(Overriding)

- 부모 클래스의 메소드를 자식 클래스에서 다시 재정의해서 사용하는 것을 메소드 오버라이딩이라 한다.
- @Override 어노테이션을 붙여 준다. (생략 가능)
- 자식 클래스에서 메소드 오버라이딩은 부모의 메소드와 동일한 선언부를 가져야 한다.
- 부모 클래스의 메소드가 private 접근 제한을 가지면 자식 클래스는 메소드를 오버라이딩 할 수 없다.
    
    ```java
    // 부모 클래스
    public class Animal {
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
    // 자식 클래스
    public class Dog extends Animal {
        private int weight;
        
        public Dog() {
        }
        
        public Dog(String name, String kinds, int weight) {
            super(name, kinds);
        
            this.weight = weight;
        }
        
        @Override
        public String bark() {
            return "멍멍~ 짖는다.";
        }
    }
    ```
    
- 자식 객체에서 오버라이딩된 메소드를 호출하면 부모 객체의 메소드가 아닌 오버라이딩된 자식 메소드가 호출된다.
- 즉, 부모 객체의 메소드는 삭제되는 것이 아닌 오버라이딩된 메소드에 의해 가려지게 된다.
    
    ```java
    Dog dog = new Dog();
    
    System.out.println(dog.bark()); // "멍멍~ 짖는다." 출력
    ```
    
- 자식 클래스 내부에서 오버라이딩된 부모 클래스의 메소드를 호출해야 하는 상황이 발생한다면 super를 통해서 부모 메소드를 호출할 수 있다.
    
    ```java
    // 부모 클래스
    public class Animal {
        private String name;
        private String kinds;
        
        // 생성자 선언
        ...
        
        public String bark() {
            return "짖는다.";
        }
    }
    ```
    
    ```java
    // 자식 클래스
    public class Dog extends Animal {
        private int weight;
        
        // 생성자 선언
        ...
        @Override
        public String bark() {
            return "멍멍~ " + super.bark();
        }
    }
    ```
    

## 6. final 클래스와 final 메소드

- final 키워드는 필드뿐만 아니라 클래스와 메소드 선언 시에 사용할 수 있다.
- final 키워드를 클래스 선언에 붙이게 되면 이 클래스는 상속할 수 없는 클래스가 된다.
    
    ```java
    // java.lang에서 제공하는 String 클래스
    public final class String {
        ...
    }
    ```
    
- final 키워드를 메소드 선언에 붙이게 되면 이 메소드는 재정의할 수 없는 메소드가 된다.
    
    ```java
    // java.lang에서 제공하는 Object 클래스
    public class Object {
        public final void wait() {
            ...
        }
    }
    ```
# 다형성(Polymorphism)

## 1. 다형성(Polymorphism)

- 다형성은 같은 타입이지만 실행 결과가 다양한 객체를 이용할 수 있는 성질을 말한다.
- 부모 클래스 타입의 참조 변수에 자식 객체들을 대입하여 다룰 수 있는 것이 다형성의 기본 개념이다.

### 1.1. 업 캐스팅(Up Casting)

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
    

### 1.2. 다운 캐스팅(Down Casting)

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