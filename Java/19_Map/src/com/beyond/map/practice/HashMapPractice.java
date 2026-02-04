package com.beyond.map.practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapPractice {
    public void method1() {
        // 키는 문자열, 값은 Snack 객체
        Map<String, Snack> snackMap = new HashMap<>();
        snackMap.put("프링글스", new Snack("프링글스", "양파맛", 500));  // 키값 중복
        snackMap.put("프링글스", new Snack("프링글스", "오리지널", 400));  // 갱신되면 새걸로 들어간다
        snackMap.put("참쌀설병", new Snack("참쌀설병", "기본", 600));
        snackMap.put("버터링", new Snack("버터링", "버터맛", 700));
        snackMap.put("새우깡", new Snack("새우깡", "매운맛", 800));

        System.out.println(snackMap);
        System.out.println(snackMap.size());
        System.out.println();

        System.out.println(snackMap.get("프링글스"));


        // remove() 메소드는 key에 해당하는 Entry 객체를 삭제하는 메소드이다.
        Snack snack = snackMap.remove("새우깡");

        System.out.println(snackMap);
        System.out.println(snackMap.size());
        System.out.println(snackMap.isEmpty());
        System.out.println(snack);
        System.out.println();

        // clear() 메소드는 모든 Entry 객체를 삭제하는 메소드이다.
        snackMap.clear();

        System.out.println(snackMap);
        System.out.println(snackMap.size());
        System.out.println(snackMap.isEmpty());
        System.out.println();

    }

    public void method2() {
        Map<String, Snack> snackMap = new HashMap<>();
        snackMap.put("프링글스", new Snack("프링글스", "오리지널", 400));
        snackMap.put("참쌀설병", new Snack("참쌀설병", "기본", 600));
        snackMap.put("버터링", new Snack("버터링", "버터맛", 700));
        snackMap.put("새우깡", new Snack("새우깡", "매운맛", 800));

        // 인덱스가 없어서 for문 사용 불가
        /*
        for (int i = 0; i < snackMap.size(); i++) {
            System.out.println(snackMap.???);
        }
        */

        // 반복문 쓰기
        // 1. keySet() 메소드 이용 - Entry에서 키를 받아서 Set으로 만들어준다.
        Set<String > set = snackMap.keySet();

        for (String key : set) {
            System.out.printf("key: %s, value: %s\n", key, snackMap.get(key));
        }
        // set.forEach(key -> System.out.printf("key: %s, value: %s\n", key, snackMap.get(key)));

        // 2. .entrySet() 메소드를 이용
        Set<Map.Entry<String, Snack>> entries = snackMap.entrySet();
        // Set에 Entry객체가 들어있고, Entry 는 키로 String, 값으로 Snack이 들어있다는 뜻

        for (Map.Entry<String, Snack> entry : entries) {
            System.out.printf("key: %s, value: %s\n", entry.getKey(), entry.getValue());
        }


    }
}
