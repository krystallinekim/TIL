package com.beyond.set.practice;

import java.util.HashSet;
import java.util.Iterator;
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

        // 2. Iterator 반복자 사용
        Iterator<String> iterator = set.iterator();
        // iterator.hasNext();  // iterator를 돌리면서 다음 요소가 있는지 확인(boolean)
        // iterator.next();  // 다음 요소로 넘어가는 메소드

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println();


    }

    public void method2() {
        Set<Music> musicSet = new HashSet<>();

        musicSet.add(new Music("MUSIC", "가수2", 5));
        musicSet.add(new Music("으으음악", "음악가", 6));
        musicSet.add(new Music("music_no.2", "가수", 2));
        musicSet.add(new Music("으으음악", "음악가", 6));  // 중복
        musicSet.add(new Music("!!music", "artist1", 4));
        musicSet.add(new Music("music1", "artist1", 1));
        musicSet.add(new Music("!!music", "artist1", 4));  // 중복
        musicSet.add(new Music("음악3", "not_artist", 3));

        for (Music music: musicSet) {
            System.out.println(music);
        }

        System.out.println(musicSet.size());

    }
}

