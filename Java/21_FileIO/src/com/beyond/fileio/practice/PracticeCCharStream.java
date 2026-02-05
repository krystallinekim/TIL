package com.beyond.fileio.practice;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class PracticeCCharStream {
    public void fileSave() {

        // try-with-resources
        // 예외 여부와 상관없이 try 안에서 사용된 리소스를 자동으로 종료하는 구문

        try (FileWriter fw = new FileWriter("b_char.txt")) {
            fw.write('A');
            fw.write('\n');
            fw.write('가');  // 바이트 단위라 FileOutputStream에서는 한글이 안써졌음
            fw.write('\n');
            fw.write(new char[] {'a', 'p', 'p', 'l', 'e'});  // 문자 배열도 가능
            fw.write('\n');
            fw.write(new char[] {'안', '녕', '하', '세', '요'});  // 한글 배열도 가능
            fw.write('\n');

            fw.write("apple\n");
            fw.write("안녕하세요. 저는 홍길동입니다. 만나서 반갑습니다.\n", 3, 8);

            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void fileRead() {
        char[] buffer = new char[50];

        try (FileReader fr = new FileReader("b_char.txt")) {
            System.out.println((char) fr.read());
            System.out.println((char) fr.read());
            System.out.println((char) fr.read());
            System.out.println(fr.read(buffer));  // 28글자
            System.out.println(Arrays.toString(buffer));

            System.out.println(fr.read());  // 다 읽어온 이후로는 -1 반환
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
