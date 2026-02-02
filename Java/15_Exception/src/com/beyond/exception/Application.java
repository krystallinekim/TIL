package com.beyond.exception;

import com.beyond.exception.practice.Parent;

public class Application {
    public static void main(String[] args) {
        System.out.println("================= 프로그램 시작 =================");

        // new TryCatchFinally().method(); // ArithmeticException
        // new Throws().method();
        // new RuntimeExceptionError().method1();
        new Parent().method();

        System.out.println("================= 프로그램 종료 =================");
    }
}
