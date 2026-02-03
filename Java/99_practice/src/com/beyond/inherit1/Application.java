package com.beyond.inherit1;

import com.beyond.inherit1.practice.Employee;
import com.beyond.inherit1.practice.Student;

import java.util.Objects;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Student[] students = {
                new Student("홍길동", 20, 178.2, 70.0, 1, "정보시스템공학과"),
                new Student("김말똥", 21, 187.3, 80.0, 2, "경영학과"),
                new Student("강개순", 23, 167.0, 45.0, 4, "정보통신학과")
        };

        Employee[] employees = new Employee[10];

        Scanner scanner = new Scanner(System.in);
        int i = 0;

        OUTER:
        while (true) {
            employees[i] = new Employee();

            System.out.print("이름을 입력해 주세요 > ");
            String name = scanner.next();
            employees[i].setName(name);

            System.out.print("나이를 입력해 주세요 > ");
            int age = scanner.nextInt();
            employees[i].setAge(age);

            System.out.print("신장을 입력해 주세요 > ");
            double height = scanner.nextDouble();
            employees[i].setHeight(height);

            System.out.print("몸무게를 입력해 주세요 > ");
            double weight = scanner.nextDouble();
            employees[i].setWeight(weight);

            System.out.print("급여를 입력해 주세요 > ");
            int salary = scanner.nextInt();
            employees[i].setSalary(salary);

            System.out.print("부서를 입력해 주세요 > ");
            String dept = scanner.next();
            employees[i].setDept(dept);

            while (true) {
                System.out.print("계속 입력할까요? > ");
                String key = scanner.next();

                if (Objects.equals(key, "y") || Objects.equals(key, "Y")) {
                    i++;
                    break;
                }

                if (Objects.equals(key, "n")) {
                    break OUTER;
                }

            }
        }
        for (int j = 0; j <= i; j++) {
            System.out.println(employees[j].information());
        }
    }
}
