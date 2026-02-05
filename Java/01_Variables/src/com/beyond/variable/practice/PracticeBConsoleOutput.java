package com.beyond.variable.practice;

public class PracticeBConsoleOutput {
    public void valuePrint() {

        // Sytstem.out.
        System.out.println("hello world");  // System 클래스에서 out은 참조변수, println은 메소드임

        // .print(): 그냥 출력(줄바꿈 없음)
        System.out.println("\n.print()");
        System.out.print("안녕하세요");
        System.out.print(12345);
        System.out.print(true);
        System.out.print('\n');


        // .println(): 출력 후 자동 줄바꿈
        System.out.println("\n.println()");
        System.out.println("안녕하세요");
        System.out.println(12345);
        System.out.println(true);
        System.out.println();

        // .printf(): 서식 지원
        /*
        - 서식 지정자
            %d : 정수형, %o : 8진수, %x : 16진수
            %c : 문자, %s : 문자열
            %f : 실수(소수점 아래 6자리), %e : 지수형태표현
            %A : 16진수 실수
            %b : 논리형

        - 정렬방법
            %5d : 5칸을 확보하고 오른쪽 정렬
            %-5d : 5칸을 확보하고 왼쪽 정렬
            %.2f : 소수점 아래 2자리까지만 표시

        - escape 문자
            원래의 의미를 벗어나는 문자들을 이스케이프 문자라고 한다.
            \t : 수평 탭
            \n : 줄 바꿈
            \r : 리턴
            \" : 큰 따옴표 출력
            \' : 작은 따옴표 출력
            \\ : 역슬래쉬 출력
            \\u + 16진수 : 16진수에 해당하는 유니코드
        */
        System.out.println("\n.printf()");
        System.out.printf("%d\n", 10);
        System.out.printf("%d + %d = %d\n", 100, 200, (100 + 200));  // 특정 위치에 원하는 값을 주고 싶을 때 이용함
        System.out.println(100 + " + " + 200 + " = " + (100 + 200)); // println으로 쓰려면 가독성이 떨어짐.
        System.out.printf("%.3f\t%f\n", 3.1415926535, 2.71828);  // 자리수 제한도 줄 수 있음
        System.out.printf("%b %c %s \t \" \' \\ \u1000 \n", (10 > 11), 'A', "Hello");

        // System.err: 콘솔에 에러 출력 시 system.err를 사용함
        System.err.print("에러 발생!\n");
        System.err.println("에러 발생!");
        System.err.printf("%s", "에러 발생!\n");

    }
}
