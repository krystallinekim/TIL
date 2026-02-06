package com.beyond.lambda.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;

public class PracticeGMethodPreference {
    public void method1() {

        System.out.println();
        // 정적 메소드 참조
        // 클래스 이름 :: 정적 메소드 이름
        IntBinaryOperator ibo = Math::max;
        System.out.println(ibo.applyAsInt(1, 2));

        // IntUnaryOperator iuo = Math::max;  // 매개변수 숫자가 맞지 않아 에러

        System.out.println();
        // 객체 메소드 참조
        Consumer<String> consumer = System.out::println;
        consumer.accept("hi");

//        String str = "Hello World!";
//        Supplier<Integer> supplier = str::length;
//        System.out.println(supplier.get());

        // 매개변수 메소드 참조
        Function<String, Integer> function = String::length;
        System.out.println(function.apply("Hello World!"));

        // 생성자 참조
        // Function<Integer, List<String>> listFunction = size -> new ArrayList<>(size);
        Function<Integer, List<String>> listFunction = ArrayList::new;
        System.out.println(listFunction.apply(10));


    }
}
