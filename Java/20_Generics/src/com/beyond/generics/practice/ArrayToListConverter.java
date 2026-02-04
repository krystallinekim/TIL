package com.beyond.generics.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// 제네릭스 타입으로 선언 후, 객체 생성 시 타입 결정
public class ArrayToListConverter<T> {
// Number를 상속하는 클래스만 올 수 있게 설정, 인터페이스를 구현하는 클래스만 오게 제한할 수도 있다.
// public class ArrayToListConverter<T extends Number> {
    // 원래 데이터 타입이 들어가야 하는 곳에 전부 제네릭스 타입으로 변경
    private final T[] values;

    public ArrayToListConverter(T[] values) {
        this.values = values;
    }

    public void print() {
        for (T value : values) {
            System.out.println(value);
        }
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();

        // for (T value : values) {
        //     list.add(value);
        // }
        Collections.addAll(list, values);

        return list;
    }
}
