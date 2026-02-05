package com.beyond.assiststream.practice;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PracticeAByteToCharStream {

    // InputStreamReader()
    public void method1() {

        try {
            char[] buffer = new char[20];
            InputStreamReader isr = new InputStreamReader(System.in);
            // BufferedReader br = new BufferedReader(isr);
            System.out.print("입력 > ");
            isr.read(buffer);
            for (char c : buffer) {
                System.out.print(c);
            }
            System.out.println();
            // close시 System.in이 close되어 이후 키보드 입력이 막힌다.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // OutputStreamWriter
    public void method2() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
            bw.write("안녕하세요. 곧 점심이네요. 오늘 점심은 뭘 먹을까요?");
            bw.newLine();
            bw.write("오늘 점심은 우동을 먹었어요");
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
