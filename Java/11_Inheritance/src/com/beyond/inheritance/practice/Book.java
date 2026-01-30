package com.beyond.inheritance.practice;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private int price;

    public Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String information() {
        return String.format("=== Information - Book ===\n- 제목 \t%s\n- 저자 \t%s\n- 가격 \t%d\n",
                this.title, this.author, this.price);

    }

    // 1, .toString() 메소드 - 객체의 문자열 정보를 리턴
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }

    // 2. .equals() 메소드 - 특정 객체가 매개값으로 들어온 객체와 같은지 비교하는 메소드
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;  // null이면 바로 false, 클래스가 달라도 false

        Book book = (Book) o;

        return price == book.price && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    // 3. .hashCode() 메소드 - 객체를 식별할 수 있는 하나의 정수값

    @Override
    public int hashCode() {
        return Objects.hash(title, author, price);
    }

}
