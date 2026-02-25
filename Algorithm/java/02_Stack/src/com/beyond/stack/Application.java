package com.beyond.stack;

import com.beyond.stack.practice.ArrayStack;
import com.beyond.stack.practice.SinglyLinkedStack;
import com.beyond.stack.practice.Stack;

public class Application {
    public static void main(String[] args) {
        // 자바에서 제공하는 Stack 클래스 테스트
        // Stack<String> stack = new Stack<>();

        // System.out.println(stack);
        // System.out.println(stack.size());
        // System.out.println(stack.isEmpty());
        // System.out.println();

        // push() 메소드 테스트
        // stack.push("딸기");
        // stack.push("포도");

        // System.out.println(stack);
        // System.out.println(stack.size());
        // System.out.println(stack.isEmpty());
        // System.out.println();

        // pop() 메소드 테스트
        // System.out.println(stack.pop());
        // System.out.println(stack);
        // System.out.println(stack.size());
        // System.out.println(stack.isEmpty());
        // System.out.println();

        // contains() 메소드 테스트
        // System.out.println(stack.contains("딸기"));
        // System.out.println(stack.contains("포도"));
        // System.out.println();

        // peek() 메소드 테스트
        // System.out.println(stack.peek());
        // System.out.println(stack);
        // System.out.println();

        // ArrayStack 구현 및 테스트
        // Stack<String> stack = new ArrayStack<>(5);
        Stack<String> stack = new SinglyLinkedStack<>();

        // push() 메소드 구현
        stack.push("딸기");
        stack.push("포도");
        // stack.push("복숭아");
        // stack.push("키위");
        // stack.push("바나나");
        // stack.push("메론");

        System.out.println(stack);
        System.out.println(stack.size());
        System.out.println(stack.isEmpty());
        System.out.println();

        // pop() 메소드 구현
        System.out.println(stack.pop());
        // System.out.println(stack.pop());
        // System.out.println(stack.pop());
        System.out.println(stack);
        System.out.println(stack.size());
        System.out.println(stack.isEmpty());
        System.out.println();

        // contains() 메소드 구현
        System.out.println(stack.contains("딸기"));
        System.out.println(stack.contains("포도"));
        System.out.println();

        // peek() 메소드 테스트
        System.out.println(stack.peek());
        System.out.println(stack);
        System.out.println();
    }
}
