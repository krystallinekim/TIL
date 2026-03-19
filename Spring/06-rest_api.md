# REST API(Representational State Transfer API)

- REST(Representational State Transfer)는 자원을 이름으로 구분하여 해당 자원의 상태를 주고받는 것을 의미한다.
- REST API는 REST 아키텍처 스타일을 따르는 API로, HTTP 프로토콜을 통해 요청을 보내고 응답을 받는 API이다.

## REST API의 특징

- 자원(Resource)
    - 모든 것은 자원으로 간주되며, `URI(Uniform Resource Identifier)`로 식별된다.

- 표현(Representation)
    - 자원의 상태는 `JSON`, `XML` 등의 형태로 표현된다.

- 상태없음(Stateless)
    - 서버는 클라이언트의 상태를 유지하지 않는다.

- 캐시 가능(Cacheable)
    - HTTP 응답은 캐시 가능하도록 설정될 수 있다.

- 인터페이스 일관성(Uniform Interface)
    - 자원에 대한 접근 방식이 일관되게 설계되어야 한다.

## REST API 설계

- REST API는 자원을 중심으로 설계되며 자원은 URI로 식별된다.

- 자원을 나타낼 때는 명사로 표현하고 일반적으로 복수형을 사용한다.(무조건 소문자를 사용한다)
    - /users
    - /users/1
    - /products
    - /products/30
    - /users/1/orders
    - /credit-cards
    - /customer-addresses

- 동사는 HTTP 메서드(GET, POST, PUT, DELETE 등)를 통해 표현한다.
    - `GET` - 자원 조회
    - `POST` - 자원 생성
    - `PUT` - 자원 전체 수정
    - `PATCH` - 자원 부분 수정
    - `DELETE` - 자원 삭제

- HTTP 상태 코드로 결과를 전달한다.
    - `200` - OK (요청 성공)
    - `201` - Created (자원 생성 성공)
    - `204` - No Content (요청 성공, 응답 본문 없음)
    - `400` - Bad Request (잘못된 요청)
    - `401` - Unauthorized (인증 필요)
    - `403` - Forbidden (권한 없음)
    - `404` - Not Found (자원 없음)
    - `500` - Internal Server Error (서버 오류)
    - 커스텀 상태 코드를 만들 수도 있다.

- 요청과 응답에 `JSON(JavaScript Object Notation)`을 사용한다.
    
    ```json
    // JSON은 자바스크립트 객체를 만들 때 사용하면 표현법으로 구조화된 데이터를 표현하는 표기법이다.
    "{name: '홍길동', age: 20, hobby: ['축구', '농구', '야구']}"
    ```
    - `JSON`은 비동기 통신에서 객체 타입의 데이터 전송 시 XML 대비 용량이 작고 속도가 빠르다.
