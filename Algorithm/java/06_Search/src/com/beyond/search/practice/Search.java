package com.beyond.search.practice;

public class Search {
    // 배열에서 특정 숫자의 인덱스를 찾는 메소드
    public static int linearSearch(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {

                return i;
            }
        }

        return -1;
    }

    // 문자열에서 특정 문자의 인덱스를 찾는 메소드
    public static int linearSearch(String str, char target){
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == target) {

                return i;
            }
        }
        
        return -1;
    }

    // 배열의 요소에서 가장 작은 값을 찾는 메소드
    public static int minValue(int[] numbers) {
        int min = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < min) {
                min = numbers[i];
            }
        }

        return min;

        // return Arrays.stream(numbers).min().orElse(0);
    }

    // 배열에서 특정 숫자의 인덱스를 찾는 메소드 (이진 탐색, 반복문 사용)
    public static int binarySearch(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (numbers[mid] == target) {
                return mid;
            } else if (numbers[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    // 배열에서 특정 숫자의 인덱스를 찾는 메소드 (이진 탐색, 재귀 호출 사용)
    public static int binarySearch(int[] numbers, int left, int right, int target) {
        if (left <= right) {
            int mid = (left + right) / 2;

            if (numbers[mid] == target) {
                return mid;
            } else if (numbers[mid] < target) {
                return binarySearch(numbers, mid + 1, right, target);
            } else {
                return binarySearch(numbers, left, mid - 1, target);
            }
        }

        return -1;
    }
}
