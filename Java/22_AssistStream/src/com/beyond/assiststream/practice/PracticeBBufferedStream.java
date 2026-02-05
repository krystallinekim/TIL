package com.beyond.assiststream.practice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PracticeBBufferedStream {
    public void fileSave() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("a_buffer.txt"))) {

            bw.write("안녕하세요");
            bw.newLine();  // 개행 메소드
            bw.write("개행 메소드를 사용했어요\n");
            bw.write("show me the money");
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileRead() {
        try (BufferedReader br = new BufferedReader(new FileReader("a_buffer.txt"))) {
            // BufferedReader 제공 메소드
            // 한 줄을 읽어올 수 있다.
            // System.out.println(br.readLine());
            // System.out.println(br.readLine());  // 더이상 읽어올 데이터가 없다면 null을 리턴

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
