package com.beyond.lambda.practice;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/*
표준 함수적 인터페이스
Consumer
매개값은 있고, 리턴값이 없는 `.accept()`라고 하는 추상 메소드를 가진다.

매개값을 소비하는 역할
람다식을 매개변수를 소비하는 형태로 만들면 된다.
*/
public class PracticeBConsumer {

    public void method1() {
        // Consumer<T>: T 타입의 객체를 받아서 소비
        Consumer<String> consumer = str -> {
            System.out.println(str);
        };
        consumer.accept("Consumer");

        // BiConsumer<T, U>: T, U 타입의 객체를 받아서 소비
        BiConsumer<String, String> biConsumer = (str1, str2) -> System.out.println(str1 + str2);
        biConsumer.accept("bi", "Consumer");

    }

}
