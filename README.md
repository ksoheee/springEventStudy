# 실행 및 구조 설명

## 1. 실행 방법

### 실행 환경

- JDK 25
- Spring Boot 4.1.1
- Gradle
- H2 Database

### DB 설정

개발 환경에서는 H2 Database를 사용하며 MySQL 호환 모드로 실행합니다.

```yaml
spring:
  datasource:
    url: jdbc:h2:./db_dev;MODE=MySQL
    username: sa
    password:
    driver-class-name: org.h2.Driver
```

### 실행 명령

프로젝트 루트 디렉터리에서 다음 명령어를 실행합니다.

```bash
./gradlew bootRun
```

---

## 2. 구조 설명

### 모듈 구성

프로젝트는 `Member`와 `Post` 도메인을 분리하여 구성했습니다.

- **Member 모듈**
  - 회원 정보 관리
  - 회원 정책 관리
  - 회원 활동점수 관리
  - 비밀번호 정책을 활용한 보안 팁 제공

- **Post 모듈**
  - 게시글 관리
  - 댓글 관리
  - 게시글에서 사용하는 회원 정보 복제본 관리

두 모듈이 서로의 도메인 객체에 직접 의존하지 않도록 구성하고, 필요한 데이터 전달은 Spring Event 또는 HTTP API를 통해 처리했습니다.

### Spring Event와 HTTP API를 구분한 이유

모듈 간 통신은 반환값의 필요 여부에 따라 Spring Event와 HTTP API로 구분했습니다.

**Spring Event**는 호출한 모듈에서 처리 결과를 즉시 받을 필요가 없는 경우 사용했습니다.

- 회원 정보 변경 시 Post 모듈의 회원 복제본 갱신
- 게시글 작성 시 회원 활동점수 증가
- 댓글 작성 시 회원 활동점수 증가

게시글 또는 댓글 작성 사실을 Member 모듈에 전달한 후 Post 모듈이 처리 결과를 받을 필요가 없기 때문에 이벤트 방식으로 처리했습니다.

반면 **HTTP API**는 호출한 모듈에서 즉시 반환값이 필요한 경우 사용했습니다.

게시글 작성 시 Member 모듈의 비밀번호 정책을 기반으로 생성한 보안 팁을 응답에 포함해야 합니다. 따라서 Post 모듈에서 `MemberApiClient`를 통해 Member 모듈의 API를 호출하고, 반환받은 보안 팁을 게시글 작성 결과에 포함하도록 구현했습니다.

### 회원 복제 흐름

회원의 원본 데이터는 Member 모듈에서 관리하고, Post 모듈에서는 게시글과 댓글 처리에 필요한 회원 정보를 복제하여 관리합니다.

회원 정보가 생성되거나 변경되면 Member 모듈에서 Spring Event를 발행하고, Post 모듈에서 해당 이벤트를 수신하여 회원 복제본에 반영합니다.

이를 통해 Post 모듈이 Member 모듈의 엔티티에 직접 의존하지 않으면서 필요한 회원 정보를 사용할 수 있도록 구성했습니다.

---

## 3. 확인 결과

### 초기 데이터

애플리케이션 실행 시 초기 데이터가 정상적으로 생성되는 것을 확인했습니다.

SELECT COUNT(*) FROM MEMBER_MEMBER ; 
COUNT(*)
6

SELECT COUNT(*) FROM POST_POST ; 
COUNT(*)
6

SELECT COUNT(*) FROM POST_COMMENT; 
COUNT(*)
3


### 회원 활동점수

게시글 작성 시 `3점`, 댓글 작성 시 `1점`이 증가하도록 구현했습니다.

초기 데이터 생성 후 각 회원의 게시글 및 댓글 작성 수에 따라 활동점수가 정상적으로 반영되는 것을 확인했습니다.

SELECT * FROM MEMBER_MEMBER; 
| ID | Activity Score | Nickname | Password | Username | Created Date | Modified Date |
|---:|---:|---|---|---|---|---|
| 1 | 0 | system | pwd | system | 2026-10-04 12:39:07.540657 | 2026-10-04 12:39:07.540657 |
| 2 | 0 | holding | pwd | holding | 2026-10-04 12:39:07.554648 | 2026-10-04 12:39:07.554648 |
| 3 | 0 | admin | pwd | admin | 2026-10-04 12:39:07.556076 | 2026-10-04 12:39:07.556076 |
| 4 | 10 | user1 | pwd | user1 | 2026-10-04 12:39:07.557392 | 2026-10-04 12:39:07.709569 |
| 5 | 7 | user2 | pwd | user2 | 2026-10-04 12:39:07.558686 | 2026-10-04 12:39:07.707809 |
| 6 | 4 | user3 | pwd | user3 | 2026-10-04 12:39:07.559778 | 2026-10-04 12:39:07.705830 |

### 회원 원본과 복제본 일치 확인

Member 모듈에서 관리하는 회원 원본 데이터와 Post 모듈에서 관리하는 회원 복제본 데이터를 비교하여 회원 정보가 정상적으로 동기화되는 것을 확인했습니다.

SELECT * FROM MEMBER_MEMBER;

| ID | Activity Score | Nickname | Password | Username | Created Date | Modified Date |
|---:|---:|---|---|---|---|---|
| 1 | 0 | system | pwd | system | 2026-10-04 12:39:07.540657 | 2026-10-04 12:39:07.540657 |
| 2 | 0 | holding | pwd | holding | 2026-10-04 12:39:07.554648 | 2026-10-04 12:39:07.554648 |
| 3 | 0 | admin | pwd | admin | 2026-10-04 12:39:07.556076 | 2026-10-04 12:39:07.556076 |
| 4 | 10 | user1 | pwd | user1 | 2026-10-04 12:39:07.557392 | 2026-10-04 12:39:07.709569 |
| 5 | 7 | user2 | pwd | user2 | 2026-10-04 12:39:07.558686 | 2026-10-04 12:39:07.707809 |
| 6 | 4 | user3 | pwd | user3 | 2026-10-04 12:39:07.559778 | 2026-10-04 12:39:07.705830 |

SELECT * FROM POST_MEMBER;

| ID | Activity Score | Nickname | Password | Username | Created Date | Modified Date |
|---:|---:|---|---|---|---|---|
| 1 | 0 | system |  | system | 2026-10-04 12:39:07.540657 | 2026-10-04 12:39:07.540657 |
| 2 | 0 | holding |  | holding | 2026-10-04 12:39:07.554648 | 2026-10-04 12:39:07.554648 |
| 3 | 0 | admin |  | admin | 2026-10-04 12:39:07.556076 | 2026-10-04 12:39:07.556076 |
| 4 | 10 | user1 |  | user1 | 2026-10-04 12:39:07.557392 | 2026-10-04 12:39:07.681733 |
| 5 | 7 | user2 |  | user2 | 2026-10-04 12:39:07.558686 | 2026-10-04 12:39:07.686740 |
| 6 | 4 | user3 |  | user3 | 2026-10-04 12:39:07.559778 | 2026-10-04 12:39:07.689921 |

### 재실행 시 중복 확인

애플리케이션을 재실행한 후에도 기존 초기 데이터가 중복으로 생성되지 않는 것을 확인했습니다.

### 보안 팁 호출 확인

게시글 작성 시 Post 모듈에서 `MemberApiClient`를 통해 Member 모듈의 보안 팁 API를 호출하고, 반환된 보안 팁이 게시글 작성 응답에 포함되는 것을 확인했습니다.

실행 로그 :

```text
2026-10-04T12:39:07.656+09:00 DEBUG 63361 --- [back] [  restartedMain] c.b.boundcontext.post.in.PostDataInit    : 1번 글이 생성되었습니다. 보안 팁: 비밀번호의 유효기간은 90입니다.
2026-10-04T12:39:07.659+09:00 DEBUG 63361 --- [back] [  restartedMain] c.b.boundcontext.post.in.PostDataInit    : 2번 글이 생성되었습니다. 보안 팁: 비밀번호의 유효기간은 90입니다.
2026-10-04T12:39:07.661+09:00 DEBUG 63361 --- [back] [  restartedMain] c.b.boundcontext.post.in.PostDataInit    : 3번 글이 생성되었습니다. 보안 팁: 비밀번호의 유효기간은 90입니다.
2026-10-04T12:39:07.663+09:00 DEBUG 63361 --- [back] [  restartedMain] c.b.boundcontext.post.in.PostDataInit    : 4번 글이 생성되었습니다. 보안 팁: 비밀번호의 유효기간은 90입니다.
2026-10-04T12:39:07.665+09:00 DEBUG 63361 --- [back] [  restartedMain] c.b.boundcontext.post.in.PostDataInit    : 5번 글이 생성되었습니다. 보안 팁: 비밀번호의 유효기간은 90입니다.
2026-10-04T12:39:07.667+09:00 DEBUG 63361 --- [back] [  restartedMain] c.b.boundcontext.post.in.PostDataInit    : 6번 글이 생성되었습니다. 보안 팁: 비밀번호의 유효기간은 90입니다.
```