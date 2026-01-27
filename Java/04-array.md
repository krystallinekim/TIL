# 배열

- 자바는 같은 타입의 많은 데이터를 다루는 효율적인 방법인 배열을 제공한다.
- 배열은 같은 타입의 데이터를 연속된 공간에 나열시키고, 각 데이터에 인덱스(index)를 부여해 데이터를 읽거나, 데이터를 저장하는 방법을 제공한다.
        

## 배열 변수의 선언

- 변수와 마찬가지로 배열을 사용하기 위해서는 배열 변수를 선언해야 한다.
- 배열 변수를 선언하는 구문은 `자료형[] 변수명 또는 자료형 변수명[]`이다.
    - `자료형 변수명[]`은 java에서 권장되는 스타일은 아님
    
    ```java
    // 대괄호([])는 배열 변수를 선언할 때 사용하는 기호이다.
    int[] iArray;
    double dArray[];
    ```
    

## 배열 생성 및 초기화

### new 연산자로 배열 생성 및 초기화

- 배열에 저장될 값을 배열 생성 후 저장하려면 new 연산자로 배열을 생성하면 된다.

- 배열의 크기만 알고 있고, 안에 무슨 값이 들어갈지는 아직 정해지지 않았을 때 사용한다.

- new 연산자로 배열을 생성하는 구문은 `new 자료형[배열 크기]`이다.
    - 크기 지정을 해주지 않으면 에러남
    
    ```java
    // 배열 변수 선언 후 배열 생성
    int[] iArray;
    
    iArray = new int[5]; 
    
    // 배열 변수 선언과 동시에 배열 생성
    double dArray[] = new double[5]; 
    ```
    
- 배열이 생성되고 나서 배열에 저장되어 있는 값을 읽거나 저장, 변경하기 위해서는 배열 변수명과 인덱스를 사용한다.
    
    ```java
    //  배열의 인덱스는 0번부터 시작한다. (Zero Base)
    iArray[0] = 10;
    iArray[1] = 20;
    iArray[2] = 30;
    iArray[3] = 40;
    iArray[4] = 50;
     // iArray[5] = 60; // ArrayIndexOutOfBoundsException
    
    System.out.println(iArray[0]); // 10 출력
    System.out.println(iArray[1]); // 20 출력
    System.out.println(iArray[2]); // 30 출력
    System.out.println(iArray[3]); // 40 출력
    System.out.println(iArray[4]); // 50 출력
    // System.out.println(iArray[5]); // ArrayIndexOutOfBoundsException
    ```
    
- 배열 사용 시 반복문을 활용하여 편리하게 사용이 가능하다.
    - `배열변수.length` 필드로 배열의 길이를 얻을 수 있다.
    
    ```java
    for(int i = 0; i < dArray.length; i++) {
        iArray[i] = (i + 1) * 10;
    
        System.out.println(iArray[i]);
    }
    ```
- 배열의 출력은 일반적으로는 for문, 특히 향상된 for문을 이용한다.

    ```java
    for (int value : iArray) {
        System.out.println(value);
    }
    ```

- 배열 출력 시에는 `Arrays` 클래스의 `toString()` 메소드를 이용해 깔끔하게 볼 수 있다.
    ```java
    System.out.println(Arrays.toString(iArray));
    // >> [1, 2, 3, 4, 5]
    ```


### 값 목록으로 배열 생성 및 초기화

- 배열에 저장될 값을 배열 생성과 동시에 초기화하려면 값 목록으로 배열을 생성하면 된다.

- 생성 시에 배열에 뭐가 들어갈지 알고 있을 때 사용

- 크기는 값 목록의 크기로 자동으로 설정(배열 크기를 따로 지정할 수 없다)
 
- 값 목록으로 배열을 생성하는 구문은 `new 자료형[] {값, 값, 값, ... } 또는 {값, 값, 값, ... }`이다. (`new 자료형[]`은 여기서는 생략 가능)
    
    ```java
    // 배열 생성과 동시에 초기화
    int[] iArray = new int[] {1, 2, 3, 4, 5}; 
    double[] dArray = {1.1, 2.2, 3.3, 4.4, 5.5};
    ```
    
- 배열 변수를 이미 선언한 후에 중괄호를 사용한 배열 생성은 불가능
    - 배열 변수를 미리 선언한 후에 중괄호를 사용한 배열 생성은 `new` 연산자를 사용해서 값 목록을 지정해야 한다.
    
    ```java
    int[] iArray;
    
    // iArray = {1, 2, 3, 4, 5}; // 에러 - 배열 이니셜라이저는 new에서만 가능
    iArray = new int[] {1, 2, 3, 4, 5};
    ```


## 배열의 저장 구조

- 배열 변수는 참조 변수로 Stack 영역에 생성된다.

- `new 자료형[] {값, 값, 값, ... } 또는 {값, 값, 값, ... }` 구문을 실행하면 주어진 값들을 요소로 가지는 배열을 Heap 영역에 생성하고, 배열의 시작 주소를 반환한다.    

- `new 자료형[배열 크기]` 구문을 실행하면 Heap 영역에 배열을 생성하고, 배열의 시작 주소를 반환한다. 이 때, 배열은 자동적으로 기본값으로 초기화된다.
    - 배열을 만들 때 일단 Heap에 빈 배열을 만드는데, 이 때 쓰레기 데이터가 있을 수 있어 배열의 크기와 자료형 별 초기값을 이용함 
 
    
    | 자료형 | 초기값 | 자료형 | 초기값 |
    | --- | --- | --- | --- |
    | 정수형 | 0 | 실수형 | 0.0 |
    | 문자형 | \u0000 | 논리형 | false |
    | 참조형 | null |  |  |

- null(널)은 참조형 변수가 Heap 영역의 객체를 참조하지 않는다고 뜻하는 값이다.
    - 배열을 `String[]`으로 만들면, 초기값은 전부 null이다 = 배열의 요소가 Heap 영역 어딘가를 참조 중이지 않다는 뜻

- null 값을 가지고 있는 참조 변수를 사용하면 `NullPointerException`이 발생한다.

## 배열의 정렬

- `Array.sort(배열)`을 사용한다. 오름차순으로만 정렬해 준다.
    
    ```java
    int[] iArray = {4,5,7,3,5};  // [4, 5, 7, 3, 5]
    Arrays.sort(iArray);         // [3, 4, 5, 5, 7]
    ```

- 문자형도 정렬 가능하다. (A~Z, ㄱ~ㅎ)

- 자료형에 따라 알아서 정렬 알고리즘을 선택해서 사용해준다.

- 내림차순의 경우, 직접 지원하지 않는다.
    
    - 기본 타입의 경우, 오름차순으로 정렬 후 새로 저장함
    
        ```java
        int[] iArray = {2, 7, 4, 3, 4, 6};
        Arrays.sort(iArray);

        int[] iArray_copy = new int[iArray.length];
        
        for (int i = 0; i < iArray.length ; i++) {
            iArray_copy[iArray.length - 1 - i] = iArray[i];
        }

        System.out.println(Arrays.toString(iArray_copy));  // [7, 6, 4, 4, 3, 2]
        ```

    - 문자열은 `Arrays.sort()`에 `Collections.reverseOrder()`을 사용한다.
        
        ```java
        Arrays.sort(sArray, Collections.reverseOrder());  // 기본타입에서는 사용 X
        ```


## 2차원 배열

- 배열의 요소로 **다른 배열**을 가지는 배열을 2차원 배열이라고 한다.
- 2차원 배열은 논리적으로 행과 열로 이루어진 표 형태로 존재한다고 생각하면 된다.
- 2차원 배열은 할당된 공간마다 인덱스 번호 두 개를 갖는다. (앞 번호는 행, 뒤 번호는 열)

### 2차원 배열 변수 선언

- 2차원 배열 변수를 선언하는 구문은 `자료형[][] 변수명 또는 자료형 변수명[][]`이다.
    
    ```java
    int[][] iArray;
    double dArray[][];  // 각각 붙이는 것도 가능하긴 한데
    byte[] bArray[];    // 가독성 떨어져서 잘 안씀
    ```
    

### 2차원 배열의 생성 및 초기화

#### new 연산자로 2차원 배열 생성 및 초기화

- new 연산자로 2차원 배열을 생성하는 구문은 `new 자료형[행 크기][열 크기]`이다.
    
    ```java
    int[][] iArray;
    double[][] dArray = new double[4][4];
    
    iArray = new int[4][4];
    ```
    
- 2차원 배열이 생성되고 나서 배열을 사용하기 위해서는 변수명과 두 개의 인덱스(행과 열)를 사용한다.
    
    ```java
    iArray[0][0] = 10;
    iArray[0][1] = 20;
    iArray[0][2] = 30;
    iArray[0][3] = 40;
    ...
    
    System.out.println(iArray[0][0]); // 10 출력
    System.out.println(iArray[0][1]); // 20 출력
    System.out.println(iArray[0][2]); // 30 출력
    System.out.println(iArray[0][3]); // 40 출력
    ...
    ```
    
- 2차원 배열 사용 시 중첩 반복문을 활용하여 편리하게 사용이 가능하다.
    
    ```java
    for(int i = 0; i < iArray.length; i++) {
        for(int j = 0; j < iArray[i].length; j++) {  // iArray[i]를 해야 열 길이가 다를 때 적용 가능

            iArray[i][j] = (j + 1) * 10;
    
            System.out.println(iArray[i][j]);
        }
    }
    ```
    

#### 값 목록으로 2차원 배열 생성 및 초기화

- 값 목록으로 2차원 배열을 생성하는 구문은 `new 자료형[][] {{값, 값, ... }, {값, 값, ... }}`이다.
    - 여기서도 생성하자마자 값 목록을 주면 `new 자료형[][]`는 생략 가능
    
    ```java
    int[][] iArray = new int[][] {{1, 2, 3, 4}, {5, 6, 7, 8}};
    double dArray[][] = {{1.1, 2.2, 3.3, 4.4}, {5.5, 6.6, 7.7, 8.8}};
    ```

- 가변배열: 이차원 배열에서, 안쪽 배열의 길이가 다른 배열
    - 행의 길이만 정해져 있다면, 열의 길이는 뭐가 들어와도 상관없다
    ```java
    String[][] sArray = new String[3][];
    sArray[0] = new String[] {"Linux"};
    sArray[1] = new String[] {"MariaDB", "Java"};
    sArray[2] = new String[] {"Spring", "HTML5", "CSS3"};
    ```
