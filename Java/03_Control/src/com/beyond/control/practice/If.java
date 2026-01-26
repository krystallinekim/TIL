package com.beyond.control.practice;

import java.util.Scanner;

public class If {
    public void method1() {
        System.out.println("사용자에게 정수를 입력받고, 홀수인지 짝수인지 구분");

        Scanner scanner = new Scanner(System.in);
        System.out.print("정수 입력 > ");
        int num = scanner.nextInt();

        System.out.printf("%d\n", num);

        if (num % 2 == 0) {
            System.out.println("짝수");
        } else {
            System.out.println("홀수");
        }
    }

    public void method2() {
        System.out.println("사용자에게 이름을 입력받아 '홍길동'이 맞는지 확인하기");

        Scanner scanner = new Scanner(System.in);
        System.out.print("이름 입력 > ");
        String username = scanner.nextLine();

//        문자열에서의 동등비교
//        System.out.println("홍길동" == "홍길동");
//        System.out.println("홍길동".equals(username));
//        System.out.println("홍길동".equals(new String("홍길동")));

        if (username.equals("홍길동")) {
            System.out.println("홍길동 본인입니다");
        } else {
            System.out.println("본인이 아닙니다.");
        }
    }

    public void method3() {
        System.out.println("알파벳 하나를 입력받아서 대/소문자 확인하기");

        Scanner scanner = new Scanner(System.in);
        System.out.print("알파벳 입력 > ");
        char ch = scanner.next().charAt(0);

        if ('a' <= ch && ch <= 'z') {
            System.out.println("소문자");
        } else if ('A' <= ch && ch <= 'Z') {
            System.out.println("대문자");
        } else {
            System.out.println("알파벳이 아님");
        }

    }

    public void practice1() {
        System.out.println("실습 1");
        /*
         * 사용자에게 점수(0 ~ 100)를 입력받아서 점수별로 등급을 출력하라
         *   - 90점 이상은 A 등급
         *   - 90점 미만 80점 이상은 B 등급
         *   - 80점 미만 70점 이상은 C 등급
         *   - 70점 미만 60점 이상은 D 등급
         *   - 60점 미만 F 등급
         *
         *  예시)
         *    점수 > 90
         *    당신의 점수는 90점이고 등급은 A입니다.
         */

        int num;
        char grade;

        Scanner scanner = new Scanner(System.in);
        System.out.print("점수 입력 > ");
        num = scanner.nextInt();

        if (0 <= num && num <= 100) {
            if (num >= 90) {
                grade = 'A';
            } else if (num >= 80) {
                grade = 'B';
            } else if (num >= 70) {
                grade = 'C';
            } else if (num >= 60) {
                grade = 'D';
            } else {
                grade = 'F';
            }
            System.out.printf("당신의 점수는 %d점이고, 등급은 %c입니다.\n", num, grade);
        } else {
            System.out.println("점수가 범위를 벗어났습니다.");
        }

    }
}
