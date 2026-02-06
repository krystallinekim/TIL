package com.beyond.lambda.practice;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

public class PracticeEOperator {
    public void method1() {
        IntBinaryOperator intBinaryOperator = (a, b) -> a * b;
        System.out.println(intBinaryOperator.applyAsInt(3, 4));

        IntUnaryOperator intUnaryOperator = a -> a * a * a;
        System.out.println(intUnaryOperator.applyAsInt(10));
    }
}
