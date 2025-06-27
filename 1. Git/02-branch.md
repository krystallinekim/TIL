# 브랜치의 개념

각 commit에 붙일 수 있는 스티커

다른 사람들과 협업할 때 충돌을 줄일 수 있고, main 코드를 유지하며 새로운 걸 추가할 수 있고, 여러 기능을 동시에 개발하는 등등, 매우 다양한 상황에 쓰인다

---

# Branch 명령어

1. ## **`git branch`**

    브랜치 스티커를 현재 commit에 붙이는 명령어

    독립적인 작업 공간인 branch를 생성한다

    기존 코드에 영향을 주지 않고 새로운 기능을 실험/개발 시 사용

    ```bash
    git branch # 현재 브랜치 목록을 확인
    git branch <이름> # <이름>이라는 브랜치를 현재 commit에 생성
    ```

    이 때, 현재 어떤 브랜치에 있는지는 `*`표시로 확인(이걸 `HEAD`라고 함)



1. ## `git switch`

    원하는 스티커가 붙어 있는 브랜치로 이동할 수 있다.

    다른 브랜치로 이동하거나 새 브랜치를 생성하고 이동할 수 있음

    **무조건 현재 작업중인 파일을 저장/스테이징/커밋 후에만 이동 가능함**

    ```bash
    git switch <브랜치 이름>
    git switch -c <새 브랜치 이름> # 새 브랜치 생성 후 이동
    ```
    예전에는 `git checkout`을 이용했음


1. ## `git merge`

    작업한 브랜치를 다른 브랜치(main 포함)에 병합

    이 때 알아서 어느 부분이 다른지 알려주고, 다른 부분을 선택할 수 있다.

    **현재 Head가 있는 브랜치에서 merge할 branch를 선택하는 형식**

    ```bash
    git merge <브랜치2>
    ```

    병합 후에는 브랜치를 삭제해도 됨 (`git branch -d <브랜치명>`)


# 보통 작업 FLOW

작업 시작: git clone 또는 git switch -c 새브랜치

파일 수정 후: git add, git commit

작업 공유: git push

다른 사람의 작업 반영: git pull

기능 개발 끝나면: git switch main, git merge 작업브랜치

정리: git branch -d 작업브랜치

---

# [Git Learning](https://learngitbranching.js.org/?locale=ko)

상대 참조, 작업 되돌리기, rebase, Cherry-pick 등등 볼 거 많음