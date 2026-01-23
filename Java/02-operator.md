# 연산자(Operator)

## 1. 연산자

- 프로그램에서 데이터를 처리하여 결과를 산출하는 과정을 연산이라고 한다.
- 연산에 사용되는 표시나 기호를 연산자(*Operator*)라고 한다.
- 연산의 대상이 되는 데이터를 피연산자(*Operand*)라고 한다.
- 자바에서는 다양한 연산자들을 제공하고 있으며 숫자뿐만 아니라 문자열 등 다양한 데이터들도 연산이 가능하다.
- 연산자는 피연산자의 수에 따라 단항, 이항, 삼항 연산자로 구분된다.

## 2. 단항 연산자

- 단항 연산자는 한 개의 피연산자를 필요로 하는 연산자이다.

### 2.1. 부호 연산자

- 부호 연산자는 양수 및 음수를 표시하는 연산자이다. (+ 부호 유지, - 부호 변경)
- boolean, char 타입을 제외한 기본 타입에 사용할 수 있다.
    
    ```java
    int num = -10;
    ```
    

### 2.2. 논리 부정 연산자

- 논리 부정 연산자는 true를 false로 false를 true로 변경하는 연산자이다.
- boolean 타입에만 사용할 수 있다.
    
    ```java
    boolean result = true;
    
    System.out.println(result); // true
    System.out.println(!result); // false
    ```
    

### 2.3. 증감 연산자

- 증감 연산자는 피연산자의 값에 1을 증가시키거나 감소시키는 연산자이다.
- boolean 타입을 제외한 기본 타입에 사용할 수 있다.
- 증감 연산자가 피연산자 앞에 있으면 먼저 증감 연산을 수행하고 다음 연산을 수행한다.
    
    ```java
    int num = 10;
    int result = ++num; // 전위 연산, 먼저 num을 11로 바꾼다.
    
    System.out.println(num + ", " + result); // 11, 11
    ```
    
- 증감 연산자가 피연산자 뒤에 있으면 다른 연산을 먼저 수행하고 증감 연산을 수행한다.
    
    ```java
    int num = 10;
    int result = num++; // 후위 연산, 먼저 result에 10을 대입하고 num을 11로 바꾼다.
    
    System.out.println(num + ", " + result); // 11, 10
    ```

- 괄호와는 상관없이 무조건 가장 먼저/가장 늦게 연산됨

## 3. 이항 연산자

- 이항 연산자는 두 개의 피연산자를 필요로 하는 연산자이다.

### 3.1. 산술 연산자

- 수학의 사칙연산(+, -, *, /)과 나머지 연산(%)을 하는 연산자이다.
- boolean 타입을 제외한 기본 타입에 사용할 수 있다.
    
    ```java
    System.out.println(10 + 5); // 15
    System.out.println(10 - 5); // 5
    System.out.println(10 * 5); // 50
    System.out.println(10 / 5); // 2
    System.out.println(10 % 5); // 0
    System.out.println(10 % 3); // 1
    ```
    
- 문자형(char) 연산도 가능하지만, 문자형을 숫자형으로 바꿔서 계산하는 방식
    ```java
    char ch = 'A';

    System.out.println((ch + 1));  // char -> int, 66
    System.out.println((char) (ch + 1));  // 'B'
    ```

- 나눗셈에서, 0으로 나누기는 에러(`ArithmeticException: / by zero`)
    - 단, 실수형으로 나누면 에러 안남
        ```java
        System.out.println(5 / 0.0);  // Infinity
        System.out.println(5 % 0.0);  // NaN, Not a Number
        ```

### 3.2. 문자열 연결 연산자

- 문자열에서 + 연산자는 문자열을 서로 연결하는 연산자이다.
- 피연산자 중에 한 쪽이 문자열이면 다른 피연산자를 문자열로 변환하고 서로 연결한다.
    
    ```java
    System.out.println("Hello" + "World"); // HelloWorld
    System.out.println("1" + 2); // 12
    ```
    

### 3.3. 비교 연산자

- 비교 연산자는 피연산자의 대소(>, >=, <, <=) 또는 동등(==, !=)을 비교하는 연산자이다.
- 대소 비교는 boolean 타입을 제외한 모든 기본 타입에 사용할 수 있고(Java에서는 TF가 0, 1이 아니다), 동등 비교는 모든 타입에서 사용할 수 있다.
- 비교 연산의 결과 값은 boolean 타입이다. (true 또는 false)
    
    ```java
        // ≥, ≤, ≠는 키보드로 누를 수 없기 때문에 >=, <=, !=로 사용한다.
        System.out.println(10 == 5); // false
        System.out.println(10 != 5); // true
        System.out.println(10 > 5); // true
        System.out.println(10 <= 5); // false
        System.out.println(true == false); // false
        System.out.println(true > false); // Syntax Error
    ```

- 부동소수점 때문에 소수점 아래 계산에는 케이스 별로 오차가 생김 (3 == 3.0, 0.1 != 0.1f)
    - 실수 타입으로는 비교연산자 사용하면 안됨

### 3.4. 논리 연산자

- 논리 연산자는 논리 값을 비교하는 연산자이다.
- 논리 연산의 결과 값은 boolean 타입이다. (true 또는 false)
    
    ```java
    // 논리곱(&&)은 피연산자가 모두 true일 경우 연산 결과가 true이다. - AND
    System.out.println(true && true); // true
    System.out.println(true && false); // false
    
    // 논리합(||)은 피연산자 중 하나만 true 이면 연산 결과가 true이다. - OR
    System.out.println(true || false); // true
    System.out.println(false || false); // false
    ```
    

### 3.5. 대입 연산자

- 대입 연산자는 오른쪽 피연산자의 값을 왼쪽 변수에 저장한다.
    
    ```java
    int num = 10;
    // 오른쪽 피연산자는 리터럴, 변수, 연산식(결과가 값이니까)이 올 수 있다.
    int result = num * 10;
    ```
    

### 3.6. 복합 대입 연산자

- 다른 연산자와 대입 연산자가 결합한 것으로 자기 자신과 연산 후 연산 결과를 자기 자신에게 대입한다.
- 코드가 간결하고 메모리에서 직접 연산을 수행하여 연산 속도가 빠르다.
- 증감 연산자와 비슷해 보이지만 증감 연산자는 1씩 증감하지만 복합 대입 연산자는 원하는 값만큼 증감시키고 변수에 저장한다.
    
    ```java
    a += 10; // a = a + 10;
    a -= 10; // a = a - 10;
    a *= 10; // a = a * 10;
    a /= 10; // a = a / 10;
    a %= 10; // a = a % 10;
    ```
    

## 3. 삼항 연산자

- 삼항 연산자는 세 개의 피연산자를 필요로 하는 연산자이다.
- 삼항 연산자는 뒤에서 배우는 if 문으로 변경해서 작성할 수 있지만 한 줄에 간단하게 사용하고자 할 때 사용한다.
- 삼항 연산자는 중첩해서 여러 번 사용이 가능하다.
    
    ```java
    // ? 앞의 조건식을 연산하여 true가 나오면 결과는 a, false 가 나오면 결과는 b가 된다.
    int max = (a > b) ? a : b;
    int min = (a < b) ? a : b;
    ```
    

## 4. 연산자의 우선순위

- 수학에서처럼 프로그램 연산자에도 우선순위가 존재한다.
- 단항, 이항, 삼항 연산자 순으로 우선순위를 가진다.
- 산술, 비교, 논리, 대입 연산자 순으로 우선순위를 가진다.
- 동일한 우선순위의 연산의 경우 대부분 왼쪽에서 오른쪽으로 연산을 시작한다. (단항, 부호, 대입 연산자 제외)