package com.beyond.set.practice;

import java.util.HashSet;
import java.util.Set;

public class HashSetPractice {
    public void method1() {
        Set<String> set = new HashSet<>();

        set.add(null);
        set.add("반갑습니다");
        set.add(new String("반갑습니다"));
        set.add("여러분");
        set.add("안녕하세요");
        set.add("여러분");
        set.add(null);

        System.out.println(set);
        System.out.println(set.size());
        System.out.println(set.isEmpty());
        System.out.println();

        // Set에 저장된 객체에 접근하는 방법
        // 1. 향for문
        for (String str: set) {
            System.out.println(str);
        }

        // 1-1. 람다식
        // set.forEach(s -> System.out.println(s));

        // 1-2. 메서드 참조 활용
        // set.forEach(System.out::println);

    }
}
