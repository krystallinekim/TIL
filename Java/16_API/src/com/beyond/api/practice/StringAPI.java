package com.beyond.api.practice;

import java.util.Arrays;
import java.util.StringTokenizer;

public class StringAPI {
    public void method1() {
        // String 클래스가 제공하는 메소드들
        String str = "Hello World";

        // 1. concat(str) 메소드 전달받은 문자열과 원본 문자열을 하나로 합친 새로운 문자열을 리턴한다.
        // String concat = str.concat("!!!");
        String concat = str + "!!!!";

        System.out.println(concat);
        System.out.println(System.identityHashCode(concat));
        System.out.println(str);
        System.out.println(System.identityHashCode(str));
        System.out.println();

        // 2. substring(beginIndex) 메소드는 매개값으로 전달된 위치에서부터 끝까지의 문자열을 새로 생성해서 리턴한다.
        //    substring(beginIndex, endIndex) 메소드는 매개값의 beginIndex 위치에서부터 endIndex - 1까지의 문자열을 새로 생성해서 리턴한다.
        String substring1 = str.substring(6);
        String substring2 = str.substring(2, 6);

        System.out.println(substring1);
        System.out.println(substring2);
        System.out.println(str);
        System.out.println();

        // 3. indexOf(str) 메소드는 전달받은 문자열의 시작 인덱스를 리턴한다.
        //    indexOf(ch)
        // int index = str.indexOf("World");
        // 주어진 문자열이 포함되어 있지 않으면 -1 리턴
        // int index = str.indexOf("World", 7);
        // int index = str.indexOf('o');
        // int index = str.indexOf('o', 6);
        // 주어진 문자가 포함되어 있지 않으면 -1 리턴
        int index = str.indexOf('o', 8);

        System.out.println(index);
        System.out.println();

        // 4. replace(oldChar, newChar) 메소드는 문자열에서 oldChar가 newChar로 변경된 문자열을 새로 생성해서 리턴한다.
        //    replace(target, replacement) 메소드는 문자열에서 target이 replacement로 변경된 문자열을 새로 생성해서 리턴한다.
        // String replace = str.replace('l', 'c');
        String replace = str.replace("World", "Java");

        System.out.println(replace);
        System.out.println(str);
        System.out.println();

        // 5. trim() 메소드는 문자열의 앞뒤 공백을 제거한 문자열을 새로 생성해서 리턴한다.
        str = " Java ";

        System.out.println(str.trim());
        System.out.println(str);
        System.out.println();

        // 6. split(regex) 메소드는 입력받은 구분자로 문자열을 분리하고 배열에 담아서 리턴한다.
        str = "Linux,MariaDB,Java,Spring,HTML5,CSS3,Vue.js,Docker,Kubernetes,Jenkins";

        String[] strings = str.split(",");

        System.out.println(strings.length);
        System.out.println(Arrays.toString(strings));
        System.out.println();

        // 7. toCharArray() 메소드는 문자열의 문자들을 배열에 담아서 리턴한다.
        char[] chars = str.toCharArray();

        System.out.println(chars.length);
        System.out.println(Arrays.toString(chars));
        System.out.println();

        // 8. String.valueOf(...) 정적 메소드는 매개값으로 전달받은 값들을 문자열로 변경해서 리턴한다.
        // String value = String.valueOf(1234);
        // String value = String.valueOf(3.141592f);
        // String value = String.valueOf(false);
        String value = String.valueOf(chars);

        System.out.println(value);
    }

    }
    public void method2() {
        String str = "Linux,MariaDB,Java,Spring,HTML5,CSS3,Vue.js,Docker,Kubernates,Jenkins";

        StringTokenizer st = new StringTokenizer(str, ",");

//        System.out.println(st.nextToken());
//
//        System.out.println(st.countTokens());
//        System.out.println(st.hasMoreTokens());

        // for 문을 이용해 토큰 출력
//        int l = st.countTokens();  // countTokens()가 남은 토큰의 숫자라서 인라인으로 넣으면 안됨
//        for (int i = 0; i < l ; i++) {
//            System.out.println(st.nextToken());
//        }

        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }

    }
}
