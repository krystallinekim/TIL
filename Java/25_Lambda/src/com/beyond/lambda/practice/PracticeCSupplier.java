package com.beyond.lambda.practice;

import java.util.function.Supplier;

public class PracticeCSupplier {
    public void method1() {
        // Supplier<T>: T 타입의 객체를 리턴
        // Supplier<String> supplier = () -> "Supplier";
        Supplier<String > supplier = () -> {
            return "Supplier";
        };
        System.out.println(supplier.get());
    }
}
