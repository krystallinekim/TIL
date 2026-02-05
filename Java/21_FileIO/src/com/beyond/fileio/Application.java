package com.beyond.fileio;

import com.beyond.fileio.practice.PracticeCCharStream;

public class Application {
    public static void main(String[] args) {
        System.out.println("================ 프로그램 시작 ================");

        // new PracticeAFile().method1();
        // new PracticeBFileByteStream().fileSave();
        // new PracticeBFileByteStream().fileRead();
        // new PracticeCCharStream().fileSave();
        new PracticeCCharStream().fileRead();


        System.out.println("================ 프로그램 종료 ================");
    }
}
