package com.beyond.fileio.practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PracticeBFileByteStream {

    public void fileSave() {
        FileOutputStream fos = null;
        byte[] values = {99, 100, 101, 102, 103};
        
        byte[] test = new byte[10];
        for (byte i = 0; i < test.length; i++) {
            test[i] = i;
        }

        try {
            fos = new FileOutputStream("a_byte.dat");
            fos.write(97);  // ASCII 기준 'a'
            fos.write('b');  // char는 형변환되어서 넘어감 -> 98
            fos.write(10);  // 줄바꿈 문자
            fos.write('c');
            fos.write(10);
            // fos.write('가');  // 한글을 2바이트라서 바이트 단위 스트림으로는 제대로 출력할 수 없다. -> 파일 깨짐
            fos.write(values);  // 배열도 받는다
            fos.write(10);
            fos.write(values, 1, 2);
            fos.flush();  // 버퍼에 잔류한 내용을 출력
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // try/catch에 작성하면 실행되지 않을 수 있으므로 finally에 작성
            try {
                fos.close();  // 연결을 끊고, 사용하던 리소스를 종료 - 스트림 사용시 마지막에 close()해줌
            } catch (IOException e) {
                e.printStackTrace();  // 근데 .close()에서도 에러가 날 수 있으므로 try-catch로 둘러싼다
            }
        }
    }

    public void fileRead() {
        FileInputStream fis = null;

        byte[] buffer = new byte[50];

        try {
            fis = new FileInputStream("a_byte.dat");
//            System.out.println((char) fis.read());  // byte 파일로 읽고, 받아올 때는 int로 받아온다.
//            System.out.println((char) fis.read());
//            System.out.println((char) fis.read());
//
//            // 위에 4개 파일을 읽었기 때문에 그 다음 바이트부터 읽어온다. -> 스트림
//            System.out.println(fis.read(buffer, 10, 10));
//            System.out.println(Arrays.toString(buffer));
//            // 읽어올 때마다 한 바이트씩 읽어온다.
//
//            // 읽어올 데이터를 다 읽어온 경우 -1을 리턴함
//            System.out.println(fis.read());

            // 반복문을 통해 -1이 나올때까지 계속 출력하면 된다
            int value;
            while ((value = fis.read()) != -1) {
                System.out.println((char) value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
