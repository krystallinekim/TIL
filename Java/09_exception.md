# 예외(Exception) 처리

## 에러(Error)

- 프로그램 수행 시 치명적 상황이 발생하여 비정상 종료 상황이 발생한 것을 프로그램 에러라고 한다.

- 에러가 발생하면 프로그램은 더이상 실행되지 않고, 그 지점에서 즉시 중지된다.


### 에러의 종류

1. **컴파일 에러**
    - 소스코드 상의 문법 에러
    - 소스코드를 수정하여 해결할 수 있다.

2. **런타임 에러**
    - 프로그램 실행 중에 발생하는 에러
    - 사용자로부터 잘못된 값을 입력받거나 계산식의 오류 등으로 발생할 수 있다.

3. **시스템 에러**
    - 컴퓨터 하드웨어 오작동 또는 고장으로 인해 발생하는 에러
    - 소스코드를 수정하여 해결이 불가능하다.

## 예외(Exception)

- 사용자의 잘못된 조작 또는 개발자의 잘못된 코딩으로 인해 발생하는 프로그램 오류를 말한다.
    - java에서는 런타임 에러를 예외로 처리한다.

- 자바에서는 예외를 클래스로 관리하며, 모든 예외는 `Exception` 클래스를 상속한다.
    - 다형성을 통해 모든 에러는 `Exception` 클래스로 처리 가능함

- 예외는 반드시 처리해야 하는 `Checked Exception`과 처리하지 않아도 되는(필수 아님) `Unchecked Exception`으로 구분된다.

### CheckedException

- `Exception` 클래스를 상속하고 있는 예외들
    - `ClassNotFoundException`(없는 클래스 사용 시), `IOException`(인풋, 아웃풋 에러), `SQLException`(SQL 쿼리 이상), ...

- 컴파일 시 예외 처리 코드가 있는지 검사하는 예외를 말한다.

- 예외 처리가 되어있지 않으면 컴파일 에러를 발생시킨다.(try ~ catch, throws: 예외 처리 구문)
    - 반드시 예외를 처리해야 한다.

- 조건문이나 소스코드 수정으로는 해결이 되지 않는다. 주로 외부에 매개체와 입출력이 일어날 때 발생한다.

### UnCheckedException

- `RuntimeException` 클래스를 상속하고 있는 예외들
    - `NullPointerException`(참조변수가 null인데 사용할 경우), `IndexOutOfBoundsException`(배열의 인덱스 번호 오류), ...
     
- 컴파일 시 예외 처리 코드가 있는지 검사하지 않는 예외를 말한다.

- `RuntimeException` 같은 경우엔 프로그램 실행할 때 문제가 발생되는 것이기 때문에 충분히 예측이 가능하기 때문에 **조건문, 코드수정 등을 통해서 충분히 처리가 가능**하다.
    - 예외처리코드를 사용할 필요가 없다

## 예외 처리

- 프로그램에서 예외가 발생했을 경우 프로그램의 갑작스러운 종료를 막고 정상 실행을 유지할 수 있도록 처리하는 코드를 예외 처리 코드라고 한다.
    - 예외가 발생하던, 발생하지 않던 프로그램이 끝까지 돌아가게 만드는 역할

### try-catch-finally 문

- `try` 블록에는 예외가 발생할 가능이 있는 코드가 위치한다.
    - `try` 블록의 코드에서 예외가 발생하면 즉시 실행을 멈추고 `catch` 블록으로 이동하여 예외 처리 코드를 실행한다.
    - 예외가 발생하지 않으면 `try` 블록을 끝까지 실행한다. `catch` 블록은 실행되지 않는다.

- `finally` 블록은 생략이 가능하고 예외 발생 여부와 상관없이 항상 실행할 내용이 있을 경우에 `finally` 블록을 작성해 준다.

    ```java
    try {
        // 예외 발생 가능성이 있는 코드
    } catch (Exception e) {
        // 예외 처리 코드
    } finally {
        // 예외 발생 여부와 상관없이 실행해야 하는 코드
    }
    ```

- `catch`에서는 `try`에서 던져진 예외 코드를 받아서 `e`에 집어넣는다. 

    1. 다중 캐치
        - `e`의 참조타입을 바꿔서 다양한 에러 종류별로 실행할 코드를 다르게 할 수도 있다.
            ```java
            try{
                ...
            } catch (ArithmeticException e) {
                // ArithmeticException에 대한 처리
            } catch (NullPointerException e) {
                // NullPointerException에 대한 처리
            } catch (Exception e) {
                // 내가 모르는 에러에 대한 처리코드
            } finally {
                ...
            }
            ```
        - 받을 수 있으면 실행하고 `finally`로, 받을 수 없다면 넘기고 다음 `catch`로 넘어간다.
        - `catch`는 순서대로 작동하기 때문에, `Exception`에 대한 `catch`는 맨 밑에 둬야 한다.   
    
    2. 멀티캐치
        - 다양한 에러에 대해 처리할 코드가 같은 경우, 처리를 합칠 수 있다.
            ```java
            try{
                ...
            } catch (ArithmeticException | NullPointerException e) {
                // Arithmetic 및 NullPointer에 대한 처리
            } catch (Exception e) {
                ...
            } finally {
                ...
            }

            ```
    

### throws

- 메소드 내에서 예외가 발생할 수 있는 경우, 처리 방법은 2가지가 있다.
    1. 메소드 내에서 `try-catch` 블록으로 예외를 처리 - 가장 기본적인 처리 방법임
    2. throws 키워드를 통해서 메소드를 호출한 곳으로 예외를 떠넘길 수도 있다.

- throws 키워드는 메소드 선언부 끝에 작성되어 메소드에서 처리하지 않는 예외를 호출한 곳으로 떠넘기는 역할을 한다.
    - 호출한 곳이 메소드라면, `throws` 키워드로 다시 상위 메소드로 예외를 떠넘길 수 있다.
    - 정상 종료를 위해선 상위 어딘가에서 **반드시** `try-catch`를 이용해 처리해줘야 한다.
    - 메인 메소드까지 `throws`로 넘길 수도 있다. 메인은 JVM에서 그냥 `.printStackTrace()` 찍어서 보여주고 끝난다. 단, 프로그램 정상 종료는 안됨

        ```java
        // BufferedReader 클래스의 readLine() 메소드
        public String readLine() throws IOException { // IOException이 발생할 수 있다는 의미.
            ...
        }
        ```

    - `throws`에 예외 클래스는 여러 개 설정할 수도 있다.
        - 물론 `Exception`으로 한번에 처리할 수 있지만, 어떤 예외를 던질지 명확하지 않아 구체적으로 써주는게 좋다.
    

### 예외와 오버라이딩

- 부모 클래스의 메소드를 자식 클래스에서 오버라이딩 시 메소드가 throws 하는 Exception과 같거나 하위 클래스이어야 한다.
    - 그냥 같은걸 throw하는 것도 답임
    - 하위 예외 클래스는 보통 더 구체적으로 예외를 알려주는 클래스이기 때문
    - 상위 예외 클래스를 오버라이딩할 경우, 아예 다른 종류의 예외를 throw할 수도 있어 금지됨
    
    ```java
    public class Parent {
        public void method() throws IOException {
            ...
        }
    }
    ```
    
    ```java
    public class Child extends Parent {
    
        // EOFException은 IOException의 하위 클래스이므로 오버라이딩이 가능하다.
        @Override
        public void method() throws FileNotFoundException, EOFException, SocketException {
            ...
        }
    }
    ```
    
    ```java
    public class Child extends Parent {
    
        // Exception은 IOException의 상위 클래스이므로 오버라이딩이 불가능하다.
        @Override
        public void method() throws Exception {
            ...
        }
    }
    ```