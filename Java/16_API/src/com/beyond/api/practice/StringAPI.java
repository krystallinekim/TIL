package com.beyond.api.practice;

import java.util.Arrays;

public class StringAPI {
    public void method() {
        // String 클래스가 제공하는 메소드들

        // 1. .concat(str)
        String str = "Hello World";
        String concat = str.concat("!!!");
        // String concat = str + " World";  // .concat()은 매개값을 하나만 받지만, +는 여러개를 받을 수 있다.
        System.out.println(str);
        System.out.println(concat);
        System.out.println(str == concat);

        // 2. .substring(beginIndex[, endIndex])
        String substring1 = str.substring(6);
        String substring2 = str.substring(2, 8);
        System.out.println(substring1);
        System.out.println(substring2);
        System.out.println(str);

        // 3. ,indexOf(str)
        int idx = str.indexOf("World");
        System.out.println(idx);

        String str2 = "Linux,MariaDB,Java,Spring,HTML5,CSS3,Vue.js,Docker";

        String[] strings = str2.split(",");
        System.out.println(strings.length);
        System.out.println(Arrays.toString(strings));
        System.out.println();

        char[] chars = str2.toCharArray();

        System.out.println(chars.length);
        System.out.println(Arrays.toString(chars));
        System.out.println();

    }
}
