# 다형성(Polymorphism)

- 다형성 = 형태가 여러 개인 성질

- 다형성은 같은 타입이지만 실행 결과가 다양한 객체를 이용할 수 있는 성질을 말한다.
    - 참조변수 하나에 다양한 객체를 대입할 수 있다
    - 실행 결과도 다양함

- 부모 클래스 타입의 참조 변수에 자식 객체들을 대입하여 다룰 수 있는 것이 다형성의 기본 개념이다.
    - 다형성은 상속 관계에 있을 때만 가능하다

## 객체의 형변환

### 업 캐스팅(Up Casting)

- 업 캐스팅은 자식 타입의 객체가 부모 타입의 객체로 형 변환되는 것을 말한다.
    - 기본 타입에서, 값의 표현범위에 따라 자동/명시적으로 형변환이 되던 것처럼, 클래스도 데이터 타입이라 형변환이 가능
    - 부모 타입의 참조변수에 자식 타입의 객체가 대입되어 있는 상태

- 업 캐스팅은 **자동**으로 형 변환이 일어나기 때문에 부모 클래스 타입의 참조 변수가 모든 자식 객체들을 별도의 형 변환 없이 대입 받을 수 있다.
    - 부모 타입으로 업 캐스팅된 이후에는 부모 클래스에 선언된 필드와 메소드만 접근이 가능하다.
    - 참조변수가 부모 타입이기 때문임

- 단, 예외가 있는데 부모 타입의 메소드가 오버라이딩되었다면 **오버라이딩된 메소드가 대신 호출**된다.
    - 실제 객체는 자식 클래스 타입인데, 여기서는 메소드가 덮어씌워져있기 때문
    
    ```java
    // Dog 클래스 타입의 객체를 Animal 클래스 타입의 참조 변수에 대입
    Animal animal = new Dog();  // 원래 new Animal();이어야 정상이지만, Dog()을 넣어도 자동으로 형변환됨
    
    // 자식 클래스에서 오버라이딩 된 코드가 실행된다.
    System.out.println(animal.bark()); // "멍멍~ 짖는다."
    ```
    
    ```java
    // Cat 클래스 타입의 객체를 Animal 클래스 타입의 참조 변수에 대입
    Animal animal = new Cat();
    
    // 자식 클래스에서 오버라이딩 된 코드가 실행된다.
    System.out.println(animal.bark()); // "야옹~ 운다."
    ```

- 하나의 참조변수 animal에 Dog타입, Cat타입이 대입될 수 있다 -> 하나의 참조변수에 다양한 객체를 대입할 수 있다.
    - 객체에 따라 메소드도 다르게 실행된다 -> 같은 코드로 다양한 결과를 낸다

### 다운 캐스팅(Down Casting)

- 다운 캐스팅은 부모 타입의 객체가 자식 타입의 객체로 형 변환되는 것을 말한다.
    - 다운 캐스팅은 자동으로 형 변환이 일어나지 않기 때문에 **형 변환 연산자**를 사용해서 형 변환을 해야 한다.
    - 업캐스팅은 부모가 하나라서 어떤 데이터타입으로 바꾸고 싶은지 명시해주지 않아도 됨
    - 자식 클래스의 멤버에 접근할 수 있어진다.

- 역시 상속 관계에서만 일어날 수 있다.

    ```java
    Animal animal = new Dog();
    Dog dog = (Dog) animal;  // 이 animal을 Dog타입(자식 클래스)로 되돌리는 것
    ```

- 부모 클래스 타입 참조 변수가 실제로 참조하는 객체를 확인하지 않고 강제 형 변환을 시도하면 `ClassCastException` 예외가 발생할 수 있다.

- 객체가 어떤 클래스의 인스턴스인지 `instanceof` 연산자를 사용해서 확인할 수 있다.
    - 배열에 다양한 클래스를 업캐스팅해서 넣어놨는데, 상황에 따라서 각 요소를 다운캐스팅해서 쓰고 싶을 때 사용
    
    ```java
    for (Product product : products) {
        System.out.println(product);

        // isinstanceof를 이용해 각 케이스별로 처리해준다
        if (product instanceof Desktop) {
            System.out.println(((Desktop) product).isAllInOne());
        } else if (product instanceof SmartPhone) {
            System.out.println(((SmartPhone) product).getMobileAgency());
        } else if (product instanceof Television) {
            System.out.println(((Television) product).getSize());
        }
    }
    ```

## 다형성 활용

- 다양한 클래스의 매개변수에 대해 하나의 동작을 하고 싶을 때, 오버로딩을 쓰면 문제를 해결은 할 수 있지만, 매개변수 종류마다 메소드를 하나씩 만들어 줘야 한다.
    
    ```java
    // 메인
    productInfo(new Desktop());     // Desktop 객체
    productInfo(new SmartPhone());  // SmartPhone 객체
    productInfo(new Television());  // Television 객체
    ```
    ```java
    public static void productInfo(Desktop desktop){
        System.out.println(desktop);
    }

    public static void productInfo(SmartPhone smartPhone){
        System.out.println(smartPhone);
    }

    public static void productInfo(Television television){
        System.out.println(television);
    }
    ```

- 다형성을 이용해 다양한 클래스의 부모 클래스에 대해 메소드를 만들면 하나로 정리할 수 있다.
    - 단, 다른 동작이 보고 싶다면 그냥 오버로딩하는게 맞다 
    ```java
    public static void productInfo(Product product){
        System.out.println(product);
    }
    ```
- `println()` 메소드의 경우, 기본타입에 대해서는 하나하나 메소드를 만들었지만, 클래스를 받을 때는 `Object` 하나로 퉁쳐놨음
    - 모든 클래스는 `Object`의 자식 타입이므로 업캐스팅이 가능

    

## 추상 클래스(Abstract Class)

- 클래스들의 공통적인 특성을 추출해서 선언한 클래스를 추상 클래스라고 한다.

- 추상 클래스를 부모 타입으로, 객체로 생성될 실체 클래스가 자식 타입으로 구현되어 추상 클래스의 모든 특성을 물려받을 수 있다.

- 추상 클래스는 공통되는 필드와 메소드를 추출해서 만들었기 때문에 **객체를 직접 생성해서 사용할 수 없고** 부모 클래스로만 사용된다.
    - 객체를 만들면 에러난다

### 추상 클래스 선언

- 추상 클래스의 선언 구문은 `[접근 제한자] abstract class 클래스명 { ... }`이다.

- 추상 클래스 내에 필드, 메소드, 생성자를 포함할 수 있다.

- 추상 클래스는 객체로 생성이 안되지만 참조 변수의 타입으로는 사용이 가능하다.
    - 다형성을 적용하라는 얘기

    ```java
    public abstract class Animal {
        private String name;
        private String kinds;  // 공통 필드
        
        // 생성자도 가질 수 있다(자식 클래스 객체를 만들면 부모 클래스 객체가 만들어지고, 이 때 사용될 생성자)
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
    

### 추상 메소드(Abstract Method)

- 추상 클래스에 선언된 메소드가 자식 클래스마다 실행 내용이 달라야 하는 경우 추상 메소드를 선언할 수 있다.
    - 추상 메소드는 추상 클래스에서 선언할 수 있다. 추상클래스 밖에서는 에러 
    - 메소드의 선언부만 있는 메소드의 **실행 내용인 중괄호({})가 없는 메소드**이다.

- 추상 클래스를 상속하는 자식 클래스는 **반드시 추상 메소드를 오버라이딩** 해야 한다.
    - 오버라이딩하지 않으면 컴파일 에러가 발생하는데 자식 클래스에서 내용을 채우도록 강제화한다.

- 추상 메소드의 선언 구문은 `[접근 제한자] abstract 반환형 메소드명([매개변수]);`이다.
    
    ```java
    public abstract class Animal {
        ...

        // 추상 메소드 선언
        public abstract String bark();  // 메소드에 실행 내용이 없다
    }
    ```
    
    ```java
    // 자식 클래스
    public class Dog extends Animal {
        ...
        
        // 추상 메소드는 자식 클래스에서 반드시 오버라이딩 해야 한다.
        @Override
        public String bark() {
            return "멍멍~ 짖는다.";
        }
    }
    ```
    
## 인터페이스(Interface)

- 인터페이스는 이를 구현하는 클래스들이 반드시 구현해야 하는 공통 기능을 정의한다.

- 인터페이스는 참조 변수의 타입으로 사용할 수 있으며, 이를 통해 다양한 구현 객체를 동일한 방식으로 다룰 수 있다.

- 인터페이스를 통해 객체를 사용하면, 개발 코드의 변경 없이 사용하는 객체를 다른 구현체로 교체할 수 있다.
        
### 인터페이스 선언

- 인터페이스는 클래스처럼 생성하지만, 객체를 생성하지 않는다.
    - 생성자, 필드, 메소드를 가질 수 없다.

- 인터페이스의 선언 구문은 `[접근 제한자] interface 인터페이스명 { ... }`이다.
    - 인터페이스는 선언된 필드는 모두 `public static final`의 특성을 갖는다.(상수 필드)
        - 일반 필드로 생성해도 `public static final`이 생략된 상태로 생성됨
        - `final`이라서 반드시 초기화되어야 하고, `static` 블록을 사용할 수 없어 선언과 동시에 초기화되어야 한다.

    - 인터페이스에 선언된 메소드는 모두 `public abstract`의 특성을 갖는다.(추상 메소드)
        - `public abstract`도 생략 가능
        - java 고버전에서는 `public static`, `public default` 등도 생성 가능해졌다.
    
    ```java
    public interface Runable {
      void run(); // public abstract 생략 가능
    }
    ```
    

### 인터페이스 구현

- 인터페이스를 구현하는 클래스는 클래스 선언부에 `implements` 키워드를 추가하고 인터페이스명을 명시해야 한다.
    - 인터페이스를 구현해야 하는 클래스들을 모아놓은 부모 클래스에서 구현하는 편

- 인터페이스를 구현하는 클래스는 인터페이스에 정의된 **추상 메소드를 반드시 오버라이딩** 해야 한다.
    - 추상 클래스에서 인터페이스를 구현할 경우 추상 메소드가 있는건 괜찮아서 에러가 나지 않지만, 하위 자식 클래스에서 에러가 생기게 됨

    ```java
    // 인터페이스 구현 방법
    public class Cat implements Runable {
        ...
        
        @Override
        public void run() {
            System.out.println("Cat runs");
        }
    }
    ```
    
- 상속과 다르게 인터페이스는 다중 구현이 가능하다.
    - 다중 상속이 되면, 부모 클래스의 멤버 중 이름이 같은 멤버가 있다면 어떤 멤버를 호출해야 할지 명확하지 않다.
    - 인터페이스는 이 기능이 구현되어야 한다는 제약사항에 가까워 조건만 만족하면 된다. 어차피 내용도 없고, 구현 클래스에서 새로 오버라이딩 해야 하기 때문
    
    ```java
    // 인터페이스의 다중 구현 시 콤마(,)로 구분한다.
    public class Cat implements Runable, Swimable {
            ...
        @Override
        public void run() {
            System.out.println("Cat runs");
        }
        
        @Override
        public void swim() {
            System.out.println("Cat swims");
        }
    }
    ```
    
- 인터페이스를 구현하는 클래스로 객체를 생성 후 구현된 메소드를 호출할 수 있다.
    
    ```java
    Cat cat = new Cat();
    
    cat.run();
    cat.swim();
    ```
    

### 인터페이스 상속

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