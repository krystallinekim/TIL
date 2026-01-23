package com.beyond.variable.practice;

import java.util.Scanner;

public class ConsoleInput {
    public void keyboardInput(){
        int age;
        String name;
        double height;
        char gender;

        // Scanner 클래스 - 사용자로부터 입력되는 정수, 실수, 문자열을 읽어오는 클래스
        //  Scanner만으로는 키보드에서 입력받은 데이터를 이용하지 못하고, System.in에서 전달받아야 함.
        Scanner scanner = new Scanner(System.in);

        System.out.print("이름을 입력해주세요:");
        name = scanner.nextLine();
        System.out.printf("당신의 이름은 %s입니다.", name);

        System.out.print("나이를 입력해주세요:");
        age = scanner.nextInt();
        System.out.printf("당신의 이름은 %s이고, 나이는 %d세 입니다.", name, age);

        System.out.print("키를 입력해주세요:");
        height = scanner.nextDouble();
        System.out.printf("당신의 이름은 %s이고, 나이는 %d세이고, 키는 %.1f입니다.", name, age, height);

        System.out.print("성별을 입력해주세요(남, 여):");
        gender = scanner.next().charAt(0);  // scanner에서는 문자형은 지원 안함 -> charAt()(String의 메소드, i번째 char를 가져온다)
        System.out.printf("당신의 이름은 %s이고, 나이는 %d세이고, 키는 %.1f이고, 성별은 %c성입니다.",
                name, age, height, gender);

    }
}


