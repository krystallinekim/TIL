package com.beyond.aop.annotation;

/*
* 어노테이션
* JDK 1.5부터 추가된 기능으로, 코드에 대한 추가적인 정보를 제공하는 메타데이터
* 비즈니스 로직에 영향을 주지는 않지만, 컴파일 과정에서 유효성 체크, 코드 컴파일 방식 등을 알려주는 정보를 제공
* 클래스, 메소드, 필드, 매개변수 등에 추가할 수 있다.
* 프레임워크에서도 어노테이션을 이용해 자기 작업을 실행하는데 활용한다.
* */

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// 어디에 이 어노테이션을 붙일 지 선언하는 역할 - 메소드(METHOD)와 필드(FIELD)에만 작성할 수 있게 제한함
@Target({METHOD, FIELD})
// 어노테이션의 유효범위를 지정
// SOURCE: 소스코드에서만 유효한 범위 - @Data, @Override
// CLASS: 클래스를 참조할 때까지 유효
// RUNTIME: 코드가 실행 중일 때에도 유지, JVM에 의해 참조 가능 - @AutoWired, @Component
@Retention(RUNTIME)
//@Inherited - 부모 클래스에서 어노테이션 선언 시 자식 클래스에도 상속됨
public @interface Nologging {
}
