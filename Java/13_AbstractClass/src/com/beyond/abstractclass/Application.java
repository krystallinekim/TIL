package com.beyond.abstractclass;

import com.beyond.abstractclass.practice.BasketBall;
import com.beyond.abstractclass.practice.FootBall;
import com.beyond.abstractclass.practice.Sports;

public class Application {
    public static void main(String[] args) {
        // 추상 클래스는 객체로 만들 수 없다
        // 참조변수로는 사용이 가능함
        // Sports sports = new Sports();

        // 추상 클래스를 참조변수로 사용해 자식 객체를 참조할 수 있다.(다형성)
        Sports basketBall = new BasketBall(5);
        Sports footBall = new FootBall(11);

        Sports[] sports = {
                basketBall,
                footBall
        };

        for (Sports s : sports) {
            s.printRule();
        }
    }
}
