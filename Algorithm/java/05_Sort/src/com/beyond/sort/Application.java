package com.beyond.sort;

import com.beyond.sort.practice.ArraySort;

import java.util.Arrays;
/*
    시간 복잡도
      - 입력 크기에 따른 연산 횟수를 의미한다.
      - 시간 복잡도가 낮으면 낮을수록 좋다.

    빅오(Big-O) 표기법
      - 입력 데이터의 크기(n)가 증가할 때 알고리즘의 실행 시간 또는 연산 횟수가 어떻게 증가하는지를 나타내는 표기법이다.
      - 최악의 시간 복잡도를 표현하는 방법이다.

    시간 복잡도 크기 비교
      - O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(n³) < O(2ⁿ) < O(n!)
 */
public class Application {
    public static void main(String[] args) {
        // 삽입 정렬
        int[] numbers = {3, 1, 5, 4, 9, 2};

        System.out.println(Arrays.toString(numbers));

        ArraySort.insertionSort(numbers);

        System.out.println(Arrays.toString(numbers));
        System.out.println();

        // 버블 정렬
        numbers = new int[] {3, 1, 5, 4, 9, 2};

        System.out.println(Arrays.toString(numbers));

        ArraySort.bubbleSort(numbers);

        System.out.println(Arrays.toString(numbers));
        System.out.println();
        
        // 병합 정렬
        numbers = new int[] {3, 1, 5, 4, 9, 2};

        System.out.println(Arrays.toString(numbers));

        ArraySort.mergeSort(numbers, 0, numbers.length - 1);

        System.out.println(Arrays.toString(numbers));
        System.out.println();

        // 퀵 정렬
        numbers = new int[] {3, 1, 5, 2, 9, 4};

        System.out.println(Arrays.toString(numbers));

        ArraySort.quickSort(numbers, 0, numbers.length - 1);

        System.out.println(Arrays.toString(numbers));
        System.out.println();
    }
}
