package com.beyond.assiststream;

import com.beyond.assiststream.practice.PracticeDObjectStream;

public class Application {
    public static void main(String[] args) {
        System.out.println("========== 프로그램 실행 ==========\n");

        // 문자 변환 보조 스트림
        // new PracticeAByteToCharStream().method1();
        // new PracticeAByteToCharStream().method2();

        // 성능 향상 보조 스트림
        // new PracticeBBufferedStream().fileSave();
        // new PracticeBBufferedStream().fileRead();

        // 기본 타입 입출력 보조 스트림
        // new PracticeCDataStream().fileSaveAndRead();

        // 객체 입출력 보조 스트림
        new PracticeDObjectStream().fileSave();
        new PracticeDObjectStream().fileRead();

        System.out.println("\n========== 프로그램 종료 ==========");
    }
}
