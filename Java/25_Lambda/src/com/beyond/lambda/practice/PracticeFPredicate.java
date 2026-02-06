package com.beyond.lambda.practice;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class PracticeFPredicate {
    public void method1() {
        Predicate<String> isName = str -> Objects.equals(str, "홍길동");
        System.out.println(isName.test("홍길동"));
        System.out.println(isName.test("이몽룡"));

        BiPredicate<String, Integer> isLength = (str, i) -> str.length() == i;
        System.out.println(isLength.test("홍길동", 3));

        IntPredicate isPositive = i -> i > 0;
        System.out.println(isPositive.test(-10));
        System.out.println(isPositive.test(10));

    }
}
