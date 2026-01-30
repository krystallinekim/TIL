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
