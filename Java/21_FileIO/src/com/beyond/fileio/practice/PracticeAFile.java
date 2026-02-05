package com.beyond.fileio.practice;

import java.io.File;
import java.io.IOException;

public class PracticeAFile {
    public void method1() {
        boolean result = false;
        // File 클래스
        // 파일의 크기, 속성, 이름 등의 정보를 얻어내는 메소드 + 생성/삭제 메소드를 제공
        // 내부 데이터를 읽고 쓰기는 불가능
        // 읽고 쓰기를 위해서는 입출력 스트림을 이용하면 된다.
        // 입출력 관련된거라 예외처리가 필요함
        try {

            // 폴더 생성
            File demo = new File("C:/Users/IP_Time/TIL/Java/21_FileIO/temp/demo");
            if (!demo.exists()) {
            //    result = demo.mkdir();  // 폴더를 생성(하나만)
                result = demo.mkdirs();  // 그 위치까지의 폴더를 전부 생성
            }

            System.out.printf("폴더 생성 여부: %b\n", result);


            // 파일 생성
            // File file = new File("test.txt");  // new로 객체를 만들면, heap 메모리 상에만 존재하는 객체이다.
            File file = new File("C:/Users/IP_Time/TIL/Java/21_FileIO/temp/demo/test.txt");


            System.out.println(file.getName());             // 파일 이름 조회
            System.out.println(file.getAbsolutePath());     // 파일 경로
            System.out.println(file.length());              // 파일 길이 = 파일 용량
            System.out.println(file.exists());              // 파일 존재 여부
            System.out.println(file.isFile());              // 파일 / 디렉터리 구분
            System.out.println(file.isDirectory());         // 디렉터리 / 파일 구분




            if (file.exists()) {
                // file.delete();          // 파일/디렉터리를 삭제
            } else {
                // 존재하지 않는 경로를 제시하면 IOException 발생
                file.createNewFile();   // 실행 시 물리적인 파일이 생성된다.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
