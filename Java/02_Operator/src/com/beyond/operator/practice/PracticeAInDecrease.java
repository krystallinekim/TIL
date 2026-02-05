package com.beyond.operator.practice;

public class PracticeAInDecrease {
    public void method1() {
        System.out.println("증감연산\n");
        int num;

    // 전위 증감연산
        num = 10;

        System.out.println("전위연산 전 - " + num);
        System.out.println("1회 실행 - " + ++num);
        System.out.println("2회 실행 - " + ++num);
        System.out.println("3회 실행 - " + ++num);
        System.out.println("전위연산 후 - " + num);
        System.out.println();


    // 후위 증감연산
        System.out.println("후위연산 전 - " + num);
        System.out.println("1회 실행 - " + num--);
        System.out.println("2회 실행 - " + num--);
        System.out.println("3회 실행 - " + num--);
        System.out.println("후위연산 후 - " + num);
        System.out.println();

        int num1 = 10;
        int num2 = 20;
        int num3 = 30;

        // 전/후위연산에서 괄호는 의미 없음
        System.out.println(num1++);  // 10, num1 = 11
        System.out.println((++num1) + (num2++));  // 12 + 20 = 32, num1 = 12, num2 = 21
        System.out.println((num1++) + (--num2) + (--num3));  // 12 + 20 + 29 = 61, num1 = 13, num2 = 20, num3 = 29

        System.out.println(num1);  // 13
        System.out.println(num2);  // 20
        System.out.println(num3);  // 29

    }
}
