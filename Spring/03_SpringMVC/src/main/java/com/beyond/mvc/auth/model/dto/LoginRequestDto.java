package com.beyond.mvc.auth.model.dto;

// 필드가 전부 final이기 때문에 레코드 클래스로 저장할 수 있다.
// 매개변수처럼 보이는 것들이 전부 private final 필드들
// 필드에 대한 getter가 자동, equals/hashCode/toString도 자동, setter는 만들 수 없음
public record LoginRequestDto(String userName, String password, String[] hobby) {
}  // 필드명은 파라미터와 이름이 같아야 함 - hobbies로 쓰면 안됨

// 보통 DTO를 만들 때 레코드 클래스로 만들면 편하지만, 가끔 setter가 필요한 경우가 생겨서 그럴때는 일반 클래스로 만들어주면 된다.
