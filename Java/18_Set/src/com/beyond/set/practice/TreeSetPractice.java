package com.beyond.set.practice;

import java.util.TreeSet;

public class TreeSetPractice {
    public void method1() {
        TreeSet<String> set = new TreeSet<>();

        set.add("바");
        set.add("가");
        set.add("나");
        set.add("사");
        set.add("다");
        set.add("가");  // 중복
        set.add("마");
        set.add("사");  // 중복
        set.add("라");
        set.add("마");  // 중복

        System.out.println(set);
        System.out.println(set.size());
        System.out.println(set.first());
        System.out.println(set.last());
    }

    public void method2() {
        TreeSet<Music> musicSet = new TreeSet<>(
                (o1, o2) -> o1.getTitle().compareTo(o2.getTitle())
        );

        // TreeSet을 이용해 중복제거 + 정렬을 동시에 할 수 있다.
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
