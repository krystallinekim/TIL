package com.beyond.streamapi.practice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PracticeAStream {
    // 숫자 범위로 스트림 생성
    public void method1() {
        IntStream stream;
        int sum;

        stream = IntStream.range(1, 10);

        sum = stream.sum();  // 내부 반복자 -> 병렬로 처리하게 만들기 쉽다
        System.out.println(sum);  // 반복문을 사용할 필요가 없어짐

        System.out.println();
    }

    // 배열로 스트림 생성
    public void method2() {
        String[] names = {"임꺽정", "홍길동", "이몽룡", "성춘향", "김철수", "이몽룡"};
        // 그냥 출력은 for문 써도 됨
        // 중복제거+출력+기타등등을 한번에

        // 배열로 스트림 생성
        Stream<String> stream = Arrays.stream(names);
        stream.parallel()  // 스레드 만들어서 병렬처리
                .distinct()
                .forEach(str -> System.out.print(str + ", "));

        System.out.println();

        Stream<String> stream2 = Stream.of(names);
        stream2.forEach(System.out::println);
    }

    // 컬렉션을 통해 스트림 생성
    public void method3() {
        List<String> names = Arrays.asList("임꺽정", "홍길동", "이몽룡", "성춘향", "김철수", "이몽룡");  // asList는 수정, 삭제 불가한 리스트임
        Stream<String> stream = names.stream();

        stream.distinct().forEach(System.out::println);





    }
}
