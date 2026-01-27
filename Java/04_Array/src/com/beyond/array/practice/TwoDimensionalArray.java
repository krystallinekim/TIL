package com.beyond.array.practice;

public class TwoDimensionalArray {
    // 2차원 배열의 선언, 생성 및 초기화 (new 연산자)
    public void method1() {
        System.out.println("new를 이용한 2차원 배열");

        // 1. new 연산자를 이용한 2차원 배열 선언, 생성 및 초기화
        int[][] iArray;
        iArray = new int[3][5];

        // 2. 2차원 배열 변수와 인덱스를 이용한 값 지정
        //  1  2  3  4  5
        //  6  7  8  9 10
        // 11 12 13 14 15

        System.out.println(iArray.length);    // 행 수
        System.out.println(iArray[0].length); // 열 수

        int value = 1;

        for (int i = 0; i < iArray.length; i++) {
            for (int j = 0; j < iArray[0].length; j++) {
                iArray[i][j] = value++;
            }
        }

        // 2차원 배열 변수와 인덱스를 이용한 값 출력
        for (int i = 0; i < iArray.length; i++) {
            for (int j = 0; j < iArray[0].length; j++) {
                System.out.printf("%2d ", iArray[i][j]);
            }
            System.out.println();
        }
    }

    // 2차원 배열의 선언, 생성 및 초기화 (값 목록)
    public void method2() {
        System.out.println("값 목록을 이용한 2차원 배열");

        String[][] sArray = {
                {"Linux", "MariaDB"},
                {"Java", "Spring", "Vue.js"},
                {"HTML5", "CSS3", "Docker"}
        };

        for (int i = 0; i < sArray.length; i++) {
            for (int j = 0; j < sArray[i].length; j++) {  // sArray[i]를 해야 행별 길이가 다를 때 적용 가능
                System.out.printf("%s\t", sArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        String[][] sArray2 = new String[3][];
        sArray2[0] = new String[] {"Linux"};
        sArray2[1] = new String[] {"MariaDB", "Java"};
        sArray2[2] = new String[] {"Spring", "HTML5", "CSS3"};


        for (int i = 0; i < sArray2.length; i++) {
            for (int j = 0; j < sArray2[i].length; j++) {
                System.out.printf("%s\t", sArray2[i][j]);
            }
            System.out.println();
        }
    }
}
