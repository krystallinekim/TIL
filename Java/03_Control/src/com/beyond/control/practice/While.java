package com.beyond.control.practice;

import java.util.Scanner;

public class While {
    public void method1() {
        System.out.println("1부터 랜덤 값(1~10 사이)까지의 합계를 출력");

        int sum = 0;
        int random = (int) (Math.random() * 10 + 1);
        int i = 1;

        while (i <= random) {
            sum += i;
            i += 1;
        }

        System.out.printf("1 ~ %d까지의 합 : %d", random, sum);
    }

    public void method2() {
        System.out.println("문자열을 입력받고, 그대로 출력함. 단, 'exit' 입력시 프로그램을 종료");

        String input = "";

        Scanner scanner = new Scanner(System.in);

        // 1. 무한루프에서 조건에 맞으면 break
        while (true) {
            System.out.print("문자열을 입력해 주세요 > ");
            input = scanner.nextLine();
            System.out.println(input);

            if ("exit".equals(input)) {
                System.out.println("작동을 종료합니다");
                break;
            }
        }
    }

    public void method3() {
        System.out.println("문자열을 입력받고, 그대로 출력함. 단, 'exit' 입력시 프로그램을 종료");

        String input = "";

        Scanner scanner = new Scanner(System.in);

        // 2. 그냥 조건식을 조건문 부분에 넣을수도 있다.
        while (!"exit".equals(input)) {
            System.out.print("문자열을 입력해 주세요 > ");
            input = scanner.nextLine();
            System.out.println(input);
        }

        System.out.println("작동을 종료합니다");
    }

    public void method4() {
        System.out.println("문자열을 입력받고, 그대로 출력함. 단, 'exit' 입력시 프로그램을 종료");

        String input;

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("문자열을 입력해 주세요 > ");
            input = scanner.nextLine();
            System.out.println(input);
        } while (!"exit".equals(input));
        System.out.println("작동을 종료합니다");

    }
}
