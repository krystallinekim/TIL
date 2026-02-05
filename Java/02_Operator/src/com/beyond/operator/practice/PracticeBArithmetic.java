package com.beyond.operator.practice;

public class PracticeBArithmetic {
    public void method1() {
        System.out.println("Arithmetic.method1");

        // 정수
        System.out.println("\n정수의 산술연산");
        int num1 = 10;
        int num2 = 3;

        System.out.println(num1 + num2);  // 13
        System.out.println(num1 - num2);  // 7
        System.out.println(num1 * num2);  // 30
        System.out.println(num1 / num2);  // 3
        System.out.println(num1 % num2);  // 1

        // 실수
        System.out.println("\n실수의 산술연산");

        double num3 = 35.0;
        double num4 = 10.0;

        System.out.println(num3 + num4);  // 13
        System.out.println(num3 - num4);  // 7
        System.out.println(num3 * num4);  // 30
        System.out.println(num3 / num4);  // 3
        System.out.println(num3 % num4);  // 1

        // 문자
        // 산술연산이 되지만, 문자가 아니라 코드값으로 변환해서 계산해서 가능함
        System.out.println("\n문자의 산술연산");
        char ch = 'A';

        System.out.println((ch + 1));  // char -> int, 66
        System.out.println((char) (ch + 1));

        // 참고

        // 0으로 나누기는 에러 - ArithmeticException: / by zero
        // System.out.println(5 / 0);
        // System.out.println(5 % 0);

        //
        System.out.println(5 / 0.0);  // Infinity
        System.out.println(5 % 0.0);  // NaN, Not a Number

    }
}
