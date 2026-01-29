package com.beyond.enumeration;

import com.beyond.enumeration.practice.Week;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {

        System.out.println(Week.THURSDAY);  // 그냥 상수 프린트한것

        // Week today;  // 자료형 변수명;
        Week today = Week.THURSDAY;  // Week 타입의 참조변수 today, 변수에 저장된 값은 THURSDAY

        // enum 타입에서 제공하는 메소드
        // System.out.println(Week.THURSDAY.name());  // today 대신 Week.THURSDAY를 써도 똑같음
        System.out.println(today.name());                   // THURSDAY
        System.out.println(today.ordinal());                // 3
        System.out.println(today.equals(Week.SUNDAY));      // false    // 문자열에서 동등비교할 때 쓰던거
        System.out.println(today.compareTo(Week.SUNDAY));   // -3       // 해당 매개변수 기준으로 뒤에있으면 +, 앞에있으면 -

        Week friday = Week.valueOf("FRIDAY"); // 문자열을 보고, 문자열과 같은 타입/이름의 열거 상수(객체)를 찾아옴
        System.out.println(friday);

        Week[] weekdays = Week.values();  // 열거 상수들을 배열에 저장해서 전달함
        System.out.println(Arrays.toString(weekdays));
        System.out.println();

        // 속성 추가
        System.out.println(today.getFullName());
        System.out.println(today.getShortName());
        System.out.println(today.getValue());

        today = Week.FRIDAY;

        System.out.println(today.getFullName());
        System.out.println(today.getShortName());
        System.out.println(today.getValue());

    }
}
