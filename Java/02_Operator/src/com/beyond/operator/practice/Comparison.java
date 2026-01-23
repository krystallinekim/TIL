package com.beyond.operator.practice;

import java.util.Scanner;

public class Comparison {
    public void method1() {
        System.out.println("비교 연산자");
        int num1 = 10;
        int num2 = 25;
        boolean res = false;


//        res = (num1 == num2);  // false
//        res = (num1 != num2);  // true
//        res = (num1 >= num2);  // false
//        res = (num1 <= num2);  // true
//        res = ('A' < 'B');  // true
//        res = (3 == 3.0);  // true
//        res = (0.1 == 0.1f);  // false
            // 부동소수점 때문에 비교연산에 오차가 생긴다
        System.out.println("result : " + res);


    }
    public void method2() {
        System.out.println("값을 입력받아 짝수인지 홀수인지 판단");
        int num;

        Scanner scanner = new Scanner(System.in);

        System.out.print("숫자 입력:");

        num = scanner.nextInt();

        if (num % 2 == 0) {
            System.out.println("짝수");
        }else {
            System.out.println("홀수");
        }


    }
}
