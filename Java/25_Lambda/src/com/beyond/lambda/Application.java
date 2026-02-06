package com.beyond.lambda;

import com.beyond.lambda.practice.PracticeAFunctionalInterface;

public class Application {
    public static void main(String[] args) {

        // 함수적 인터페이스와 람다식
        new PracticeAFunctionalInterface().method1();
        new PracticeAFunctionalInterface().method2();
        new PracticeAFunctionalInterface().method3();

        PracticeAFunctionalInterface pfi = new PracticeAFunctionalInterface();
        pfi.method4(300);


        // 표준 함수적 인터페이스





    }
}
