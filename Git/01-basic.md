# **Git 기초 개념**

Git은 코드 변경 이력을 관리하는 도구.

Git ≠ Github. Git은 내 컴퓨터 내부에서 버전 관리(Version Control System)만을 담당

Github는 그걸 인터넷에 올리는 클라우드

# Git 명령어

## **`git init`**

Git 저장소 초기화.

저장소가 아닌 일반 폴더에서 최초 1회만 실행하는 명령어.

실행하면 현재 위치한 폴더가 Git 저장소로 초기화.

폴더 안에 .git 폴더를 만들어줌 → 지워버리면 저장소가 아니게 됨

```
git init
```
## **`git config`**

```
git config --global.user.name # 유저네임 지정
git config --global.user.emali # 이메일 지정
```

초기 지정용 -> 처음에 유저네임/이메일 적용 후에는 건드릴 이유 없음

github의 유저네임과 이메일과 똑같이 적용하는게 맞다


## **`git add`**

수정한 파일을 커밋할 준비. 최초 등록 포함해서 특정 파일 또는 모든 파일을 추가.

```
git add <파일명>  # 특정 파일만 지정
git add .  # 현재 폴더 전체 -> 매우 유용
```


## **`git commit`**

변경 사항을 Git에 저장. **커밋 메시지로 설명 추가.**

```
git commit -m "<메시지>"
```

이미 add된 변경사항만 commit할 수 있다.

## **`git status`**

현재 Git 상태 확인. 어떤 파일이 수정되었고, 추가할 파일은 무엇인지 확인.

```
git status
```

## **`git remote add origin`**
```
git remote add origin <URL> #연결해줄 github의 remote repository의 주소를 지정해줌
```
내 컴퓨터의 repo는 local, github는 remote repo라고 부른다.

github에 새로운 repo를 올릴 때마다 최초 한번씩 적용하는 용도

## **`git push origin main`**

```
git push origin main
```

아마 가장 많이 쓰게 될 명령어 중 하나

local에 저장된 **commit**을 remote에 저장함

## **`git clone`**

```
git clone <URL>
```

github의 remote repo에서 local repo로 복사함

bash에서 작성할 경우에는 ctrl+v가 안먹히므로 우클릭으로 하면 됨