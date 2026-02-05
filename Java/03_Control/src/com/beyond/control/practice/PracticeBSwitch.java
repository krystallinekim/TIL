package com.beyond.control.practice;

import java.util.Scanner;

public class PracticeBSwitch {

    public void method1() {
        System.out.println("과일 이름을 입력받고 가격을 출력");

        int price;
        String fruits;

        Scanner scanner = new Scanner(System.in);

        System.out.print("과일 > ");
        fruits = scanner.nextLine();

        switch (fruits) {
            case "포도":
                price = 5000;
                break;
            case "사과":
                price = 2000;
                break;
            case "딸기":
                price = 9000;
                break;
            case "귤":
                price = 10000;
                break;
            default:
                System.out.println("가격표에 없는 과일입니다.");
                return;  // 더이상 코드를 실행하지 않고, 실행 흐름을 메소드를 호출한 곳으로 돌려둔다.
        }

        System.out.printf("%s의 가격은 %d원입니다.", fruits, price);

    }

    public void method2() {
        System.out.println("월을 입력받아서 해당 달의 마지막 날짜를 출력");

        Scanner scanner = new Scanner(System.in);

        System.out.print("월 > ");
        int month = scanner.nextInt();

        if (!(1 <= month && month <= 12)) {
            System.out.println("1 ~ 12 사이의 숫자를 입력해 주세요");
            return;
        }

        switch(month) {
            case 2 :
                System.out.printf("%d월은 28일, 혹은 29일까지 있음", month);
                break;
//            case 4 :
//            case 6 :
//            case 9 :
//            case 11 :
            case 4,6,9,11 :
                System.out.printf("%d월은 30일까지 있음", month);
                break;
//            case 1 :
//            case 3 :
//            case 5 :
//            case 7 :
//            case 8 :
//            case 10 :
//            case 12 :
            case 1,3,5,7,8,10,12 :
                System.out.printf("%d월은 31일까지 있음", month);
                break;
        }
    }

    public void practice1() {
        System.out.println("두 정수를 입력받고, 연산자 기호를 입력받아서 계산 출력");

        Scanner scanner = new Scanner(System.in);
        System.out.print("정수1 > ");
        int num1 = scanner.nextInt();

        System.out.print("정수2 > ");
        int num2 = scanner.nextInt();

        System.out.print("연산자 > ");
        char ch = scanner.next().charAt(0);

        switch (ch) {
            case '+' :
                System.out.printf("%d %c %d = %d", num1, ch, num2, num1 + num2);

                break;
            case '-' :
                System.out.printf("%d %c %d = %d", num1, ch, num2, num1 - num2);

                break;
            case '*' :
                System.out.printf("%d %c %d = %d", num1, ch, num2, num1 * num2);

                break;
            case '/' :
                System.out.printf("%d %c %d = %.3f", num1, ch, num2, (double) num1 / (double) num2);

                break;
            default :
                System.out.println("연산자가 잘못되었습니다.");
        }

    }
}
