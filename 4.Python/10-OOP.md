# OOP, Object Oriented Programming

컴퓨터 프로그램을 좀더 사람이 생각하는 것과 유사하게 만들기 위해 만든 철학

어셈블리, C언어 등은 절차 중심의 프로그래밍이였지만, 파이썬과 자바 등 최신 언어들은 보통 객체 중심 프로그래밍 언어임


- 절차 중심 VS 객체중심

    절차는 코드의 흐름으로 프로그램을 생각

    객체는 객체와 객체 간의 상호작용으로 프로그램을 생각

    실제 현실세계는 매우 객체지향적이라(사람이 문을 열고 나오는 것도 사람과 문 간의 상호작용) 훨씬 현실반영적임


## 클래스와 객체

### 클래스(Class)

클래스 = 상위 개념, 분류, 그 클래스의 안에서 실제로 가지고 노는 객체가 존재

인간이라는 클래스 안에 나, 너 우리 같은 객체가 존재한다.

필요하다면 내가 클래스를 만들어낼 수 있고, 이미 있던 정수, 문자열, 리스트들 말고도 커스텀 객체를 만들어서 활용 가능

### 객체(Object)

파이썬에서는 모든 게 다 객체(연산자, 키워드, 이름 빼고)

핵심은 객체의 속성과 메서드

- 속성은 사람으로 치면 나이, 주소 등등 -> 그 객체가 가진 특징들

- 메서드는 사람이 할 수 있는 행동, 동작들(밥먹기, 잠자기) -> 그 객체가 할 수 있는 것들

### 인스턴스(Instance)

인스턴스의 사전적 의미는 **예시**

파이썬에서 모든 것은 객체이고, **모든 객체는 특정 클래스의 인스턴스**이다 -> 객체와 인스턴스 사이에 큰 구분은 없다

Ex) 정수(int)는 클래스, `1`은 객체이고, `1`은 정수(int) 클래스의 인스턴스(예시)이다

인스턴스는 결국 특정 클래스와의 관계를 포함하는 단어라 항상 클래스와 함께 나온다.

### 이해하기 쉬운 설명

포켓몬으로 한번에 이해 가능함

1. Class: 피카츄라는 포켓몬 종류

1. Object: 각각의 피카츄

1. Attribute: 피카츄의 특징들 - 도감번호, 타입(전기), 성별, 성격, 이로치, 스탯, ...

1. Method: 쓸수 있는 기술들 - 몸통박치기, 전기자석파, 10만볼트, ...


## 기본 문법

우리가 보통 쓰던 변수/함수명의 이름은 snake_case(다 소문자, 띄어쓰기는 _)로 지음

클래스명은 PascalCase(첫문자/띄어쓰기 뒤는 대문자, 띄어쓰기 없음)로 짓는다

```py
# 클래스, 인스턴스 생성
class Person:
    pass

p1 = Person()
p2 = Person()

# 확인
type(p1)
# >> __main__.Person
isinstance(p2, Person)
# >> True
```

### 인스턴스 속성(attribute)

속성 부여

```py
class Person:
    pass

p1.name = 'kim'
p1.age = 27

print(p1.name, p1.age)
# >> 'kim', 27
```

딕셔너리의 키-밸류 관계와 비슷함

### 인스턴스 메서드(method)

클래스 정의 시 함수를 정의해 주면 그 클래스의 메서드를 설정할 수 있다.

```py
class Person:
    def eat(self, food):
        print(food)
```

일반 함수와 같이 가변인자나 기본인자 등도 다 설정 가능하다.

#### `self`

주의할 점으로, 인스턴스 생성 후 메서드를 호출 시 자동으로 첫 인자로 인스턴스(my_instance)가 들어가진다.

```py
class Person:
    def talk():
        print('안녕')

p1 = Person()
p1.talk()
# TypeError: Person.talk() takes 0 positional arguments but 1 was given
```

그래서 `p1.talk()` 안에 빈것처럼 보이지만, 실제로 먼저 p1이 첫번쨰 인자로 들어가는 것임

그래서 항상 함수 첫번째 인자로 `self`를 설정해 줘야 한다. (권장사항)

클래스의 메서드 중, 자기 자신 혹은 자기의 속성을 인자로 받아야 하는 메서드가 필요할 수 있기 때문임

```py
class Person:
    def intro(self):
        print(f'{self.name}, {self.age}')

p1 = Person()
p1.name = 'kim'
p1.age = '27'

p1.intro()
# >> kim, 27
```

이 self는 인스턴스 각각을 의미한다고 생각하

#### `__init__`

인스턴스 객체가 생성될 때(namespace에 정의될 때) 자동으로 호출되는 함수

생성될 때 바로 속성을 정의해버릴 수도 있다.

```py
class Person:
    def __init__(self, name, age):
        print('응애')
        self.name = name
        self.age = age

p1.Person('kim',27)
# >> 응애

p1.name, p2.name
# >> ('kim',27)
```


#### `__del__`

인스턴스 객체가 소멸될 때 자동으로 호출되는 함수

객체가 소멸된다는 것은 즉 아무도 이 객체의 이름을 기억하지 못할 때

그래서 객체를 소멸시킬 땐 보통 `del` 키워드(변수 할당을 삭제하는 키워드)를 사용한다.

```py
class Person:
    def __init__(self):
        print('응애')

    def __del__(self):
        print('끄앙')

p1 = Person()
# >> 응애

del p1
# >> 끄앙
```

객체를 죽이는 다른 방법도 있다.

변수에 다른 객체를 할당하면, 더이상 원래 객체를 가리키는 화살표가 없어 지워진 것과 마찬가지이기 때문

```py
p1 = 1
# >> 끄앙
```

그래서 p1, p2에 같은 객체를 할당하고 p1을 지워버려도, 객체는 삭제되지 않는다.

```py
p1 = p2 = Person()
# >> 응애

del p1

del p2
# >> 끄앙
```

같은코드를 두번 실행해 버리면 원래 객체를 삭제하고 새로 할당하기 때문에, 생성/소멸이 같이 일어난다.

```py
p1 = person
# >> 응애

p1 = person
# >> 응애
#    으악
```

사실 속성 정의를 위해 잘 쓰이는 `__init__`에 비해, `__del__`은 잘 쓸일이 없음


### 매직 메서드 (Magic Methods)

특별한 일을 하기 위해 만들어진 메서드. `__xxx__` 형태이다

직접 만든 클래스에서 여러 가지 동작이 가능

메서드 | 설명
---|---
`__str__(self)` | `print()` 출력 시 내용 지정
`__eq__(self, other)` | `==` 비교
`__gt__(self, other)` | `>` 비교
`__add__(self, other)` | `+` 연산 정의

### 클래스 변수

모든 인스턴스가 공유하게 되는 클래스의 속성(attribute)

클래스 선언 내부에서 정의되어 인스턴스 어디서나 가져올 수 있다.

```py
class Circle:
    pi = 3.14

c1 = Circle()
c2 = Circle()

c1.pi, c2.pi
# >> 3.14, 3.14
Circle.pi
# >> 3.14
```

## OOP의 핵심 개념

### 추상화(Abstraction)

여러 클래스에서 공통적으로 사용할 속성 및 메서드를 묶어 기본 클래스로 작성

현실 세계를 프로그램 설계에 반영하는 데 사용

예: Student, Teacher 같은 여러 클래스를 따로 만들지 않고, Person이라는 부모 클래스의 하위에 집어넣어 속성과 메서드를 상속

### 상속(Inheritance)

기존 클래스(부모)의 속성과 메서드를 자식 클래스에서 재사용
```py
class Person:
    def __init__(self, name, age):
        self.age = age
        self.name = name
        
    def talk(self):
        print(f'안녕하세요, {self.name}입니다.')

class Student(Person):
    def __init__(self, name, age, score):
        super().__init__(name, age)
        self.score = score
```
하위 클래스인 Student에서도 Person의 메서드인 `.talk`를 사용할 수 있다.

#### `super()`

부모 클래스와 자식 클래스 간의 코드 반복을 피할 때 사용함

아예 부모 클래스의 메서드 전체를 그대로 사용할 수 있다.

```py
class Person:
    def __init__(self, name, age, number, email):
        self.name = name
        self.age = age
        self.number = number
        self.email = email 
        
    def greeting(self):
        print(f'안녕, {self.name}')
      
class Student(Person):
    def __init__(self, name, age, number, email, student_id):
        # 부모클래스의 init 실행.
        super().__init__(name, age, number, email)
        self.student_id = student_id
```
Student의 `__init__`에서 부모 클래스와 중복되는 부분을 뺄 수 있다.

#### 다중 상속

두 개 이상의 클래스를 상속받을 수도 있다.

중복된 속성이나 메서드가 있을 경우, 먼저 상속받은 클래스에 따름


### 다형성(Polymorphism)

같은 이름의 메서드가 상황에 따라 다르게 동작함 - 메서드 오버라이딩(Method Overriding)

자식 클래스에서 상속받은 메서드를 덮어쓸 수 있다.

애초에 `__init__`을 정의하는 것부터가 덮어쓰는 것

### 캡슐화(Encapsulation)

객체의 일부 구현 내용에 대해 외부로부터의 액세스를 차단

일반적인 메서드/속성을 호출 여부에 따라 3개로 나눌 수 있다.

- Public Member

    언더바 없이 시작하는 대부분의 메서드/속성들

    하위 클래스에서 오버라이딩도 가능하고, 어디서나 호출 가능함

- Protected Member

    언더바 1개로 시작하는 메서드/속성들

    부모 클래스 내부와 자식 클래스에서만 호출이 가능하다(국룰).

    밖에서 호출이 되긴 하는데, 그렇게 사용하지 않음

- Private Member

    언더바 2개로 시작하는 메서드/속성들

    사용된 클래스 내에서만 사용이 가능하다.

    하위 클래스에서 상속 및 호출이 불가능하고, 당연히 외부에서 호출도 안됨


