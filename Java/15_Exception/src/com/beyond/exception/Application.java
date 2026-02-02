package com.beyond.exception;

import com.beyond.exception.practice.Throws;

public class Application {
    public static void main(String[] args) {
        System.out.println("================= 프로그램 시작 =================");

        // new TryCatchFinally().method1(); // ArithmeticException
        new Throws().method1();

        System.out.println("================= 프로그램 종료 =================");
    }
}
