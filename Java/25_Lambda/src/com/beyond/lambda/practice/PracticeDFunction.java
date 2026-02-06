package com.beyond.lambda.practice;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class PracticeDFunction {
    public void method1() {
        Function<Integer, Double> function = a ->  (double) a;
        System.out.println(function.apply(10));

        BiFunction<Integer, Double, String> biFunction = (a, b) ->  String.format("%d, %.2f", a, b);
        System.out.println(biFunction.apply(10, 3.14));

        ToIntFunction<String> toIntFunction = value -> Integer.parseInt(value);
        System.out.println(toIntFunction.applyAsInt("123"));
    }
}
