package com.beyond.array.practice;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class PracticeAArray {
    public void method1() {
        System.out.println("배열의 선언, 생성 및 초기화");

        // 1, 배열의 선언 및 생성
        int[] iArray = new int[5];

        int[] iArray2;
        iArray2 = new int[5];

        // 2. 배열 변수와 인덱스를 이용한 값 저장
        iArray[0] = 1;
        iArray[1] = 2;
        iArray[2] = 3;
        iArray[3] = 4;
        iArray[4] = 5;
        // iArray[5] = 6;  // ArrayIndexOutOfBoundsException

        // 3. 배열 변수와 인덱스를 이용한 값 출력
        System.out.println(iArray[0]);
        System.out.println(iArray[1]);
        System.out.println(iArray[2]);
        System.out.println(iArray[3]);
        System.out.println(iArray[4]);

        // 4. 반복문을 이용한 입/출력
        for (int i = 0; i < iArray.length; i++) {  // 배열변수.length = 배열 길이
            iArray[i] = (i + 1) * 10;
        }

        // 향상된 for문 (배열을 좀 더 쉽게 처리할 목적)
        for (int value : iArray) {  // 배열의 모든 요소에 대해 value에 하나씩 넣어서 반복
            System.out.println(value);
        }

        // 5. Arrays
        System.out.println(Arrays.toString(iArray));
        System.out.println(Arrays.toString(iArray2));  // int의 초기값은 0
    }

    public void method2() {
        System.out.println("값 목록으로 배열 생성 및 초기화");

        int[] iArray = new int[] {1, 2, 3, 4, 5};
        // int[] iArray = {1, 2, 3, 4, 5};  // new 자료형[]는 생략 가능함

        System.out.println(iArray.length);
        System.out.println(Arrays.toString(iArray));
        System.out.println();

        // double[] dArray = {1.1, 2.2, 3.3, 4.4};
        double[] dArray;
        dArray = new double[] {5.5, 6.6, 7.7, 8.8};

        System.out.println(dArray.length);
        System.out.println(Arrays.toString(dArray));
        System.out.println();

    }

    public void method3() {
        System.out.println("문자열 배열");
        // 사용자로부터 과일 이름을 입력받아서 배열에 저장, 출력
        Scanner scanner = new Scanner(System.in);
        String[] fruits = new String[3];

        for (int i = 0; i < fruits.length; i++) {
            System.out.printf("과일 이름%d > ", i+1);
            fruits[i] = scanner.nextLine();
        }

        System.out.println();
        System.out.println(Arrays.toString(fruits));
    }

    public void method4() {
        System.out.println("배열의 정렬");
        int[] iArray = {2, 7, 4, 3, 4, 6};
        String[] sArray = {"귤", "orange", "Apple", "apple", "레몬", "banana", "peach"};

        System.out.println(Arrays.toString(iArray));
        System.out.println(Arrays.toString(sArray));
        System.out.println();

        // 오름차순 정렬
        Arrays.sort(iArray);
        Arrays.sort(sArray);

        System.out.println(Arrays.toString(iArray));
        System.out.println(Arrays.toString(sArray));
        System.out.println();

        // 내림차순 정렬(공식적으로 지원하진 않음)
        // 기본 타입은 오름차순으로 정렬 후 반대로 저장
        int[] iArray_copy = new int[iArray.length];
        Arrays.sort(iArray);
        for (int i = 0; i < iArray.length ; i++) {
            iArray_copy[iArray.length - 1 - i] = iArray[i];
        }
        System.out.println(Arrays.toString(iArray_copy));
        System.out.println();

        // 문자열은 Arrays.sort()에 Collections.reverseOrder() 하면 됨
        Arrays.sort(sArray, Collections.reverseOrder());

        System.out.println(Arrays.toString(sArray));
        System.out.println();

        // 기본타입을 내림차순 정렬하기 위한 편법
        // 기본타입을 객체로 바꿔서 Collections.reverseOrder를 쓸 수 있게 바꿔줌

        Integer[] integers = {2, 4 ,3, 1 ,5};
        Arrays.sort(integers, Collections.reverseOrder());

        System.out.println(Arrays.toString(integers));

    }

    /*
     * 실습 문제 1
     * 사용자가 입력하는 정수값으로 배열의 길이를 지정하여 배열을 생성하고
     * 생성된 배열의 크기만큼 반복문을 실행하여 랜덤값을(1 ~ 100) 배열의 저장하고 출력한다.
     *
     * 예시)
     * 정수값을 입력해 주세요. > 3
     *
     * numbers[0] : 77
     * numbers[1] : 88
     * numbers[2] : 6
     */
    public void practice1() {
        System.out.println("\n실습 문제 1");
        Scanner scanner = new Scanner(System.in);

        System.out.print("정수값을 입력해 주세요 > ");
        int n = scanner.nextInt();

        int[] iArray = new int[n];
        for (int i = 0; i < iArray.length; i++) {
            iArray[i] = (int) (Math.random() * 100 + 1);
        }

        System.out.println(Arrays.toString(iArray));

    }

    /*
     * 실습 문제 2
     *
     * 사용자에게 3명의 키를 입력받아 배열에 저장하고
     * 반복문을 통해 3명의 키의 총합, 평균값을 구하시오.
     *
     * 예시)
     * 키 입력 > 180.0
     * 키 입력 > 177.3
     * 키 입력 > 168.2
     *
     * 총합 : 525.5
     * 평균 : 175.2
     */
    public void practice2() {
        System.out.println("\n실습 문제 2");
        Scanner scanner = new Scanner(System.in);

        double[] heights = new double[3];

        for (int i = 0; i < heights.length; i++) {
            System.out.print("키 입력 > ");
            heights[i] = scanner.nextDouble();
        }

        double sum = 0;
        for (double height : heights) {
            sum += height;
        }

        System.out.printf("총합 : %.1f\n", sum);
        System.out.printf("평균 : %.1f\n", sum / heights.length);


    }
}
