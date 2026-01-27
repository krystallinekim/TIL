package com.beyond.control.practice;

import java.util.Scanner;

public class LoopPractice {

    /*
     * 실습 문제 1
     * 1부터 사용자가 입력한 수까지의 합계를 구하시오.
     *
     * 예시)
     * 정수 > 10
     *
     * 총 합계 : 55
     */
    public void practice1() {
        System.out.println("실습 문제 1");

        int num;
        int sum = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.print("정수 > ");
        num = scanner.nextInt();

        for (int i = 0; i <= num; i++) {
            sum += i;
        }
        System.out.println("총 합계 : " + sum);

    }

    /*
     * 실습 문제 2
     * 사용자가 입력한 단을 출력하시오.
     *
     * 예시 1)
     *    단 수(2 ~ 9)를 입력해 주세요. > 3
     *    3 X 1 = 3
     *    ...
     *    3 X 9 = 27
     *
     * 예시 2)
     *    단 수(2 ~ 9)를 입력해 주세요. > 12
     *    2 ~ 9 사이의 양수를 입력하여야 합니다.
     */
    public void practice2() {
        System.out.println("실습 문제 2");

        int num;

        Scanner scanner = new Scanner(System.in);

        System.out.print("단 수(2 ~ 9)를 입력해 주세요. > ");
        num = scanner.nextInt();

        for (int i = 1; i < 10; i++) {
            System.out.printf("%d X %d = %d\n", num, i, num * i);
        }
    }

    /*
     * 실습 문제 3
     * 사용자가 "안녕하세요."라고 입력을 하면 아래와 같이 출력 되도록 코드를 작성하라.
     * '안'
     * '녕'
     * '하'
     * '세'
     * '요'
     * '.'
     */
    public void practice3() {
        System.out.println("실습 문제 3");
        Scanner scanner = new Scanner(System.in);

        System.out.print("문자열 입력 > ");
        String input = scanner.nextLine();

        for (int i = 0; i < input.length(); i++) {
            System.out.println(input.charAt(i));
        }

    }

    /*
     * 실습 문제 4
     * 아래와 같이 출력 되도록 중첩 for 문을 작성하시오.
     *
     * 예시)
     *   1***
     *   *2**
     *   **3*
     *   ***4
     */
    public void practice4() {
        System.out.println("실습 문제 4");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print('*');
            }
            System.out.print(i+1);
            for (int j = 0; j < (3-i); j++) {
                System.out.print('*');
            }
            System.out.println();
        }
    }

    /*
     * 실습 문제 5
     * 아래와 같이 출력 되도록 중첩 for 문을 작성하시오.
     *
     * 예시)
     *   *
     *   **
     *   ***
     *   ****
     *   *****
     */
    public void practice5() {
        System.out.println("실습 문제 5");
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= i ; j++) {
                System.out.print('*');
            }
            System.out.println();
        }
    }

    /*
     * 실습 문제 6
     *
     * 반복적으로 두 정수를 입력받고 연산자 기호(+, -, *, /)를 입력 받아서 계산을 출력하라
     * 단, 연산자 기호를 잘못 입력하는 경우 "연산자를 잘못 입력하셨습니다." 출력하고 프로그램을 종료한다.
     *
     * 예시)
     *   첫 번째 수 > 3
     *   두 번째 수 > 4
     *   연산자 입력(+, -, *, /) > +
     *
     *   3 + 4 = 7
     *
     *   첫 번째 수 > 3
     *   두 번째 수 > 4
     *   연산자 입력(+, -, *, /) > a
     *
     *   연산자를 잘못 입력하셨습니다.
     */
    public void practice6() {
        System.out.println("실습 문제 6");

        int num1;
        int num2;
        int res;
        char ch;

        Scanner scanner = new Scanner(System.in);

        System.out.print("정수1 > ");
        num1 = scanner.nextInt();

        while (true) {

            System.out.print("연산자 > ");
            ch = scanner.next().charAt(0);

            System.out.print("정수2 > ");
            num2 = scanner.nextInt();

            switch (ch) {
                case '+':
                    res = num1 + num2;
                    System.out.printf("%d %c %d = %d\n", num1, ch, num2, res);
                    num1 = res;

                    break;
                case '-':
                    res = num1 - num2;
                    System.out.printf("%d %c %d = %d\n", num1, ch, num2, res);
                    num1 = res;

                    break;
                case '*':
                    res = num1 * num2;
                    System.out.printf("%d %c %d = %d\n", num1, ch, num2, res);
                    num1 = res;

                    break;
                case '/':
                    res = num1 / num2;
                    System.out.printf("%d %c %d = %.3f\n", num1, ch, num2, res);
                    num1 = res;

                    break;
                default:
                    System.out.println("연산자가 잘못되었습니다.");
                    return;

            }
        }

    }
}
