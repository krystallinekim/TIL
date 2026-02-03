package com.beyond.list.practice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArrayListPractice {
    // List
    public void method1() {
        List list = new ArrayList();
        list.add("안녕하세요");
        list.add(LocalDateTime.now());
        list.add(true); // 기본 타입은 Object 타입으로 autoboxing 되서 저장됨
        list.add(3.14);
        list.add('A');
        list.add(null);

        System.out.println(list);
        System.out.println(list.size());

        String str = (String) list.get(0);
        LocalDateTime now = (LocalDateTime) list.get(1);
        Boolean bool = (Boolean) list.get(2);

        System.out.println(str);
        System.out.println(now);
        System.out.println(bool);

        for (Object o : list) {
            System.out.println(o);
        }

        System.out.println(list);
        list.set(2, 0);
        System.out.println(list);

        System.out.println(list);
        list.remove(null);
        System.out.println(list);
        list.add(3, 10000);
        // list.remove(10000);  // int 10000이라는 데이터가 아니라 인덱스 10000번으로 인식
        list.remove(Integer.valueOf(10000));  // 10000이라는 값을 가진 object 데이터가 들어간다.

        list.clear();

        System.out.println(list.isEmpty());
    }

    // type parameter
    public void method2() {
        List<String> fruits = new ArrayList<>();
        // 리스트에 들어갈 객체의 타입 고정
        // 리스트에 저장도 타입이 고정
        // 꺼낼 때도 고정된 타입으로 나오기 때문에 다운캐스팅 할 필요가 없다.
        // JDK 1.7부터는 생성자에 타입변수를 안줘도 타입 추론을 해서 알아서 맞춰준다.
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("orange");
        fruits.add("kiwi");
        fruits.add("grape");
        // fruits.add(10);  // 다른 타입의 데이터는 저장되지 않는다.

        System.out.println(fruits);

        // List<int> numbers = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        numbers.add(Integer.valueOf(10));
        numbers.add(3);

        System.out.println(numbers);


    }
}
