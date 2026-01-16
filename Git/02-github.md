# 깃허브


## 1. Github란?

- 코드를 온라인에 안전하게 저장하고, 여러 사람이 함께 작업할 수 있게 도와주는 서비스이다.
- Github은 `코드 저장`, `버전 관리`, `협업`, `자동화` 등을 한 번에 할 수 있는 개발 플랫폼으로 많은 개발자와 조직에서 사용하고 있다.

## **2. Github 프로젝트(Project)**

### 2.1. 프로젝트(Project)

- `프로젝트(Project)`는 이슈와 풀 리퀘스트를 프로젝트 단위로 관리하기 위한 기능이다.
- 이슈와 풀 리퀘스트의 진행 상태를 시각화하여 쉽게 관리할 수 있다.

### 2.2. 프로젝트(Project) 생성

- 프로젝트를 생성할 저장소에서 `Projects` 메뉴를 클릭한 뒤, `New project` 버튼을 클릭해 새 프로젝트를 생성한다.
    
    ![image.png](attachment:5ece199b-1b8f-4767-9bd5-2f7eeedc0cb7:image.png)
    
- 사용할 프로젝트 템플릿을 선택하거나 빈 프로젝트를 선택해 생성할 수 있다.
    
    ![image.png](attachment:76bf8d91-d6bc-401a-ab13-c370250f9ba8:image.png)
    
- 프로젝트의 이름과 어떤 저장소의 이슈나 풀 리퀘스트를 자동으로 가져올지 선택 후 `Create project` 버튼을 클릭한다.
    
    ![image.png](attachment:91217ec5-485d-4801-a588-4cd39061ad00:image.png)
    
    ![image.png](attachment:bd15150f-0b83-44cf-9656-ba73817dbf33:image.png)
    

### 2.3. 워크플로우(**Workflows)**

- `워크플로우(Workflows)`는 프로젝트의 자동화 규칙(Automation Rules)을 설정하는 곳이다.
- 이슈나 풀 리퀘스트의 상태 변화에 따라 자동으로 프로젝트 항목을 추가/이동/수정하는 기능이다.
    
    ![image.png](attachment:bcca7b34-ba13-47b8-8c8f-3466eb5ed57a:image.png)
    

### 2.4. 레이아웃(**Layout)**

- `레이아웃(Layout)`은 프로젝트 데이터를 시각적으로 어떻게 표현할지 결정하는 화면 구성 방식이다.
- `테이블 레이아웃(Table Layout)`은 가장 기본이 되는 레이아웃으로 백로그 정리나 스프린트 준비에 많이 사용된다.
    
    ![image.png](attachment:fd576430-7027-4115-8601-d1dd0e9c63a5:image.png)
    
- `보드 레이아웃(Board Layout)`은 칸반 스타일의 레이아웃으로 상태 변화 중심으로 작업 흐름을 한눈에 볼 수 있다.
    
    ![image.png](attachment:f68310f6-0a86-41c4-b920-ed0b8b9bfc9a:image.png)
    
- `로드맵 레이아웃(Roadmap Layout)`은 이슈나 풀 리퀘스트의 일정(시작일~마감일)을 타임라인 형태로 시각화한다.
    
    ![image.png](attachment:1598ec55-39ae-424e-8779-195312e59695:image.png)
    

## 3. **Github 이슈(Issue)**

### **3.1. 이슈(Issue)**

- `이슈(Issue)`는 프로젝트 내에서 버그(Bug), 기능 요청(Feature Request), 작업(Task) 등을 기록하고 관리할 수 있는 기능이다.
- 또한 하위 이슈를 추가해 하나의 이슈를 더 작은 단위로 세분화하여 관리할 수 있다.

### **3.2. 이슈(Issue) 생성**

- 이슈를 생성할 저장소에서 `Issues` 메뉴를 클릭한 뒤, `New issue`버튼을 클릭해 새 프로젝트를 생성한다.
    
    ![image.png](attachment:a1dc37d6-7619-4f07-8cbc-5132ef5e9706:image.png)
    
- 이슈 생성에 필요한 정보를 입력 후 `Create` 버튼을 클릭한다.
    
    ![image.png](attachment:b32bdf31-7e98-48d9-b210-70c684a4385d:image.png)
    
    ![image.png](attachment:92c51681-3ec8-411c-b3eb-b647995b48a4:image.png)
    

### **3.3. 하위 이슈(Sub Issue) 생성**

- 이슈 상세 보기에서 `Create sub-issue`를 클릭해서 새로운 하위 이슈를 생성한다.
    
    ![image.png](attachment:486508df-4a03-4e1e-89c9-fd578cd021b3:image.png)
    
    ![image.png](attachment:2a96e143-928d-44ab-8e28-8d5c5d490d54:image.png)
    
- `Add existing issue`를 클릭해서 기존 이슈를 해당 이슈의 하위에 포함시킬 수 있다.
    
    ![image.png](attachment:d83dc08e-65b8-4662-ab40-cde8511b6b05:image.png)
    

### **3.2. 이슈(Issue)와 브랜치 연결**

- 이슈 상세 보기 오른쪽 사이드바의 Development의 `Create a branch` 링크를 클릭해서 새로운 브랜치를 생성하고 이슈와 연결한다.
    
    ![image.png](attachment:25118464-1c9f-414e-beec-69e6633fc848:image.png)
    
- 해당 저장소에서 이슈와 연결된 브랜치를 확인할 수 있다.
    
    ![image.png](attachment:1ef5039a-625d-47e0-9fe3-6ae1203dbf86:image.png)
    

## 4. **Github 풀 리퀘스트(Pull Requests)**

### **4.1. 풀 리퀘스트(Pull Requests)**

- `풀 리퀘스트(Pull Requests)`는  한 브랜치에서 작업한 변경 사항을 다른 브랜치(주로 기본 브랜치)에 병합해 달라고 요청하는 절차이다.
- 새로운 추가 기능, 버그 수정, 코드 개선 등을 변경 사항을 반영하기 위해 사용되며, 팀원들은 변경 내용을 리뷰하고, 토론한 뒤 병합 여부를 결정할 수 있다.

### **4.2. 풀 리퀘스트(Pull Requests) 생성 및 병합**

- 먼저 작업할 이슈를 기반으로 새로운 브랜치를 만들고, 해당 브랜치에서 필요한 개발 작업을 진행한다.
- 작업이 완료되면 변경 사항을 커밋한 뒤, 이 커밋들을 포함한 풀 리퀘스트를 생성한다.
    
    ![image.png](attachment:52552aa2-cfe1-4e21-b1f5-e00d51910fea:image.png)
    
    - 여러 개의 커밋이 있어도 하나의 풀 리퀘스트로 묶어 요청할 수 있다.
- 풀 리퀘스트 생성 시 제목, 설명, 리뷰어 지정 등을 함께 설정해야 한다.
    
    ![image.png](attachment:9eecdef7-0277-4e85-9819-62960970759e:image.png)
    
- 팀원들이 변경 내용을 리뷰하고 병합할 수 있도록 한다.
    
    ![image.png](attachment:b7267ebc-1634-44d2-9c70-a366fedc5982:image.png)
    

### **4**.3. 브랜치 보호 규칙(Branch Protection Rules)

- 기본적으로 리뷰어(Reviewer)를 지정하더라도 브랜치에 보호 규칙(Branch Protection Rules)이 활성화되어 있지 않으면 승인(Approve) 없이도 병합(Merge)이 가능하다.
- 병합(Merge)이 가능한 이유는 main 브랜치에 보호 규칙이 없거나, 보호 규칙 내에서 리뷰 승인 요구 옵션이 설정되어 있지 않기 때문이다.
- 저장소에서 `Settings` 메뉴로 이동한 뒤 `Rulesets`를 선택하고 `New ruleset` 버튼을 클릭하여 새 브랜치 룰셋을 생성한다.
    
    ![image.png](attachment:49a3d915-7a6e-4dbd-828e-f73d16315810:image.png)
    
- 룰셋의 이름과 상태, 룰셋을 적용할 브랜치를 정의한다.
    
    ![image.png](attachment:d891b6f1-6098-4746-bc1f-e8dde70128f8:image.png)
    
- 아래의 브랜치 관련 규칙을 정의한 후 `Create` 버튼을 클릭해서 룰셋을 생성한다.
    
    ![image.png](attachment:341c02f3-7d08-458a-bd1a-02534cd6a271:image.png)
    
    ![image.png](attachment:9d340f2d-5955-4b2c-a5fd-3d2791446dd9:image.png)
    
    ![image.png](attachment:de225da0-b7a5-4357-adc8-3b3d67ca3740:image.png)
    
- 브랜치 룰셋을 생성 후 풀 리퀘스트를 요청하면 아래와 같이 리뷰어의 승인(Approve)이 있어야 병합이 가능하다.
    
    ![image.png](attachment:18654ea1-6253-4787-bcad-13d59d776411:image.png)
    
- 지정된 리뷰어로 해당 풀 리퀘스트를 승인한다.
    
    ![image.png](attachment:48dfcd02-50b1-4129-9b7a-9097d71df7ab:image.png)
    
- 리뷰어의 승인 후에는 아래와 같이 병합이 가능해 진다.
    
    ![image.png](attachment:1ad5d353-0c01-407d-9fe9-980869fafe62:image.png)


## 이슈 템플릿

`/.github/ISSUE_TEMPLATE/`