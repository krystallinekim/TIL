package com.beyond.variable.practice;

public class Variable {
    // 메소드
    public void primitiveDataType(){

    // 논리형 변수
        System.out.println();
        System.out.println("boolean");

        // 변수의 선언(지역변수)
        // 자로형 변수명;
        boolean isTrue;  // 논리형 변수 - 1바이트 논리값

        // 변수 초기화(지역변수 선언하고 값 할당 안하면 에러임)
        // 변수명 = 값;
        isTrue = false;  // 0, 1은 안됨

        System.out.println(isTrue);  // 실제로 이걸 실행하려면 main메소드에서 메소드를 호출해야 함

    // 정수형 변수

        System.out.println();
        System.out.println("숫자형");
        byte bNum;   // 1byte 정수 (-128~127)
        short sNum;  // 2byte 정수 (-32768~32767)
        int iNum;    // 4byte 정수 (-21억 ~ 21억)
        long lNum;   // 8byte 정수 (매우 길다)
        // 보통 int 씀

        bNum = 127;  // 128은 에러
        sNum = 32767;
        iNum = 2147483647;
        lNum = 9223372036854775807L;  // long 타입 숫자 뒤에는 L을 붙여줘야 함
        System.out.println(bNum);
        System.out.println(sNum);
        System.out.println(iNum);
        System.out.println(lNum);

    // 실수형 변수
        System.out.println();
        System.out.println("실수형");

        float fNum;  // 4byte 실수
        double dNum; // 8byte 실수
        // 보통 double 씀

        fNum = 3.141592F;  // float라고 선언
        dNum = 3.141592;   // 기본적으로 Double 타입임
        System.out.println(fNum);
        System.out.println(dNum);


    // 문자형
        System.out.println();
        System.out.println("문자형");
        // char ch;     // 2byte 유니코드 문자
        // 선언과 동시에 초기화도 가능함
        char ch = 'A';  // 문자는 '', 문자열은 ""
        // char ch = 97;   // 'a'
        // char ch = '\u1000'; // 빈문자
        // char ch = 0;  // 빈문자
        System.out.println(ch);


        System.out.println();
        System.out.println("final 변수");

    // final 변수
        // 상속이 안되는 클래스 / 재정의 안되는 변수
        int age = 10;
        final int AGE = 10;  // 대소문자 구분함

        System.out.println(age);
        System.out.println(AGE);

        age = 20;
        // AGE = 20;  // 에러남

        System.out.println(age);
        System.out.println(AGE);


    }

    public void referenceDataType(){
    // 문자열
        System.out.println("문자열");

        String name = "";  // String은 자료형이자 클래스
        // 클래스가 자료형으로 오는 변수는 다 참조타입
        // 문자열을 가리키는 참조변수를 선언한 것


        System.out.println(name);
        name = "자바 재밌다";
        System.out.println(name);

        String name2 = new String("문자열");

        // int num = null;  // 이건 안됨
        String name3 = null;  // 아무것도 참조하지 않는다는 의미로 null이 가능함

    }


    public void typeCasting() {
    // 자동 형변환
        System.out.println();
        System.out.println("자동 형변환");

        byte bNum = 10;
        int iNum = bNum;  // =도 연산자 - 양 옆의 데이터 타입이 달라도 연산해줌 (short -> int 자동 형변환)
        // short sNum = iNum;  // 이건 안됨. 값에 손실이 생겨서
        short sNum = 10;

        System.out.println(bNum);  // 10
        System.out.println(iNum);  // 10
        System.out.println();

        char ch = 'a';
        iNum = ch;
        System.out.println(ch);    // a
        System.out.println(iNum);  // 97(유니코드)

        // ch = bNum;  // 에러남. byte에는 음수값이 들어가서


        double result1 = 12 + 3.3;  // int(-> double) + double = double
        System.out.println(result1);

        // short result2 = bNum + sNum;  // 에러, byte(-> int) + short(-> int) = short(x) int(o)
        // 그래서 실제로 result2는 int가 필요함
        // int result2 = bNum + sNum;

        // 아예 리터럴로 하면 돌아감
        byte result2 = 10 + 12;
        System.out.println(result2);

        long result3 = 30 + 30;  // int + int = int(< long)
        System.out.println(result3);

        long result4 = 30L + 30;  // long + int(-> long) = long
        System.out.println(result4);

        // 리터럴로 넣으면 컴파일 단계에서 계산된 상태로 들어간다.

    // 강제 형변환
        System.out.println();
        System.out.println("강제 형변환");

        System.out.println((char) iNum);  // iNum = 10

        double dNum = 4.4652356489751321546;
        System.out.println(dNum);

        float fNum = (float) dNum;  // 그냥 쓰면 에러
        System.out.println(fNum);   // 다만 소수점 아래가 손실이 생김

        int iNum2 = (int) dNum;
        System.out.println(iNum2);

        System.out.println((byte) 290);  // 34
        // 290 = 100100010(2), 34 = 0100010(2) -> 이진수에서 표현할 수 있는 부분만 표현됨.

        System.out.println((byte) 990);  // -34


        System.out.println((int) (iNum + dNum));  // 두 수의 정수부분을 더한 경우를 계산할 때라던가 활용

    }
}
