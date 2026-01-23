//패키지
//- 폴더를 만들어서 파일을 저장하고 관리하듯이 패키지를 만들어 클래스를 저장하고 관리한다.
//- 패키지는 클래스를 유일하게 만들어주는 식별자 역할을 한다.
//- 모든 클래스는 하나의 패키지에 속하며, 패키지가 선언되지 않는 클래스는 자동적으로 이름 없는 패키지(default)에 속하게 된다.

/*
주석
- 코드에 대한 설명이나 그 외 다른 정보를 넣을 때 사용하는 것으로 컴파일 시 컴파일러가 주석 부분은 건너뛴다.
*/

package com.beyond.variable;

import com.beyond.variable.practice.ConsoleInput;

// 클래스 이름은 java 파일의 이름과 동일해야 함
// Alt+Enter를 하면 할 수 있는 동작들을 보여줌
public class Application {
    // 메인 메소드 / 실행 메소드
    public static void main(String[] args) {

//        // 0. 메인 메소드에서 바로 코드 실행
//        System.out.println("Hello World");
//
//        // 1. 메소드가 속한 클래스를 생성해야 함
//        // Variable 클래스의 variableTest() 메소드
//
//        // 클래스의 변수명 = new 클래스명();
//        // 2-1. 클래스를 일일히 호출하는 방법
//        // com.beyond.variable.practice.Variable variable = new com.beyond.variable.practice.Variable();
//        // 2-2. import를 통해 생성한 클래스로 메소드를 호출
//        // 이 때 variable도 주소를 참조하는 참조변수
//        Variable variable = new Variable();
//
//        System.out.println();
//        System.out.println("=== 기본 타입 자료형 ===");
//        variable.primitiveDataType();
//
//        System.out.println();
//        System.out.println("=== 참조 타입 자료형 ===");
//        variable.referenceDataType();
//
//        System.out.println();
//        System.out.println("=== 형변환 ===");
//        variable.typeCasting();
//
//
//        ConsoleOutput consoleOutput = new ConsoleOutput();
//        System.out.println("=== 출력 메소드 ===");
//        consoleOutput.valuePrint();

        System.out.println("=== 입력 메소드 ===");
        // ConsoleInput consoleInput = new ConsoleInput();  // 주소를 변수에 저장하고, 변수를 재사용도 가능함
        new ConsoleInput().keyboardInput();  // 변수를 한번밖에 안쓴다면, 굳이 변수에 담지 말고 바로 써버릴 수도 있다.


    }


}


