package com.beyond.search;

import com.beyond.search.practice.Search;

public class Application {
    public static void main(String[] args) {
        // 선형 탐색
        int[] numbers = {13, 8, 5, 4, 9, 12};

        System.out.println(Search.linearSearch(numbers, 9));
        System.out.println(Search.linearSearch("Hello World", 'e'));
        System.out.println(Search.minValue(numbers));
        System.out.println();

        // 이진 탐색
        numbers = new int[]{2, 3, 5, 9, 12, 15, 30};

        System.out.println(Search.binarySearch(numbers, 30));
        System.out.println(Search.binarySearch(numbers, 0, numbers.length - 1, 12));
        System.out.println();
    }
}
