package com.beyond.list.practice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
        fruits.add("banana");
        fruits.add("kiwi");
        fruits.add("grape");
        fruits.add("apple");
        fruits.add("orange");
        // fruits.add(10);  // 다른 타입의 데이터는 저장되지 않는다.

        System.out.println(fruits);
        // List<int> numbers = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        numbers.add(Integer.valueOf(10));
        numbers.add(3);
        numbers.add(1);
        numbers.add(9);
        numbers.add(2);
        numbers.add(8);



        // 정렬 - 오름차순
        // Collections.sort(fruits);
        fruits.sort(null);  // 받아온 리스트의 sort 메소드를 호출하는거라 이거도 가능
        System.out.println(fruits);
        System.out.println();

        System.out.println(numbers);
        Collections.sort(numbers);
        System.out.println(numbers);
        System.out.println();

        // 내림차순
        // 1. 오름차순 정렬 후 역순으로
        // Collections.sort(fruits);
        // Collections.reverse(fruits);

        // 2. sort에 2번째 매개값으로 comparator를 준다
        // Collections.sort(fruits, Collections.reverseOrder());

        // 3. fruits에서 sort 메소드를 호출 시 바로 comparator를 줌
        fruits.sort(Collections.reverseOrder());

        System.out.println(fruits);
        System.out.println();

    }

    //
    public void method3() {
        List<Music> musicList = new ArrayList<>();

        musicList.add(new Music("MUSIC", "가수2", 5));
        musicList.add(new Music("music_no.2", "가수", 2));
        musicList.add(new Music("으으음악", "음악가", 6));
        musicList.add(new Music("!!music", "artist1", 4));
        musicList.add(new Music("music1", "artist1", 1));
        musicList.add(new Music("음악3", "not_artist", 3));

        // 랭킹 순 정렬
        // Collections.sort(musicList);

        // 아티스트 순 정렬
        // Collections.sort(musicList, new ArtistAscending());

        // 타이틀 순 정렬
        // 1. 익명 구현 개체
        // Collections.sort(musicList, new Comparator<Music>());  // 인터페이스는 객체로 만들 수 없다

        // 추상 메소드를 일회용으로 그자리에서 구현함
        /*
        Collections.sort(musicList, new Comparator<Music>() {
            @Override
            public int compare(Music music1, Music music2) {
                return music1.getTitle().compareTo(music2.getTitle());
            }
        });
        */

        // 2. 람다식
        Collections.sort(musicList, (music1, music2) ->
            music1.getTitle().compareTo(music2.getTitle())
        );


        for (Music music : musicList) {
            System.out.println(music);
        }
    }
}
