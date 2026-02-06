package com.beyond.lambda;

import com.beyond.lambda.practice.PracticeFPredicate;

public class Application {
    public static void main(String[] args) {

        // 함수적 인터페이스와 람다식
        // new PracticeAFunctionalInterface().method1();
        // new PracticeAFunctionalInterface().method2();
        // new PracticeAFunctionalInterface().method3();

        // PracticeAFunctionalInterface pfi = new PracticeAFunctionalInterface();
        // pfi.method4(300);


        // 표준 함수적 인터페이스
        // 자바에서 제공해 준다.
        // 1. Consumer
        // new PracticeBConsumer().method1();

        // 2. Supplier
        // new PracticeCSupplier().method1();

        // 3. Function
        // new PracticeDFunction().method1();

        // 4. Operator
        // new PracticeEOperator().method1();

        // 5. Predicate
        new PracticeFPredicate().method1();
    }
}
