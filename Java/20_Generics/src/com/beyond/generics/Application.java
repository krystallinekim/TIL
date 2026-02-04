package com.beyond.generics;

import com.beyond.generics.practice.ArrayToListConverter;

public class Application {
    public static void main(String[] args) {

        // 배열을 가지고 있을 때,
        String[] names = {"홍길동", "성춘향", "이몽룡", "김철수", "박영희"};

        // 문자열 말고, 정수형 배열도 적용하고 싶음
        Integer[] numbers = {1, 2, 3, 4, 5};

        // 실수형은?
        Double[] doubles = {1.2, 3.4, 5.6, 7.8, 9.0};

        // 자료형만 다르고, 코드는 다 똑같은 클래스를 만들어야 한다
        // -> 객체 생성 시점에 데이터 타입이 정해지도록 만들면 됨(제네릭스)

        // 리스트로 변환하는 클래스
        // <> 안에 지정 안하면 Object -> 객체 쓸 때 전부 다운캐스팅 해야됨
//        ArrayToListConverter<String> converter = new ArrayToListConverter<>(names);
//        ArrayToListConverter<Integer> converter = new ArrayToListConverter<>(numbers);
        ArrayToListConverter<Double> converter = new ArrayToListConverter<>(doubles);

        // 변환 후 출력
        converter.print();

        // 리스트 객체를 반환
        System.out.println(converter.toList());
    }
}
