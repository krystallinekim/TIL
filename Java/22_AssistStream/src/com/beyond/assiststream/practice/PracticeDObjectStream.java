package com.beyond.assiststream.practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PracticeDObjectStream {
    public void fileSave() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("c_object.dat"))) {
            oos.writeObject(new Member("Hong123", "1234", "홍길동", 34, 'M', 179.9));

        } catch (IOException e) {
                e.printStackTrace();
        }

    }

    public void fileRead() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("c_object.dat"))) {
            Member member = (Member) ois.readObject();  // 반환타입이 Object
            System.out.println(member.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

