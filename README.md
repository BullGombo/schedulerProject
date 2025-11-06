API 명세서, ERD 첨부하기

📘 Scheduler Project API 명세서

프로젝트명 : Scheduler Project
개발 환경 : Spring Boot 3.5.7, Java 17, MySQL 8, IntelliJ
주요 기술 스택 : Spring Web / Spring Data JPA / Lombok / MySQL
테스트 툴 : Postman


🗂️ 디렉터리 구조

schedulerProject/
├── src/
│   └── main/
│       ├── java/com/
│       │   ├── controller/
│       │   │   └── SchedulerController.java
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── repository/
│       │   ├── service/
│       │   └── SchedulerProjectApplication.java
│       └── resources/
│           └── application.properties
├── build.gradle
└── README.md



🧱 ERD (Entity Relationship Diagram)

erDiagram
    SCHEDULER {
        BIGINT id PK
        VARCHAR title
        VARCHAR content
        VARCHAR writer
        VARCHAR password
        DATETIME created_at
        DATETIME updated_at
    }


📌 설명

id: 일정의 고유 식별자 (자동 증가)

title: 일정 제목

content: 일정 내용

writer: 작성자명

password: 수정/삭제 시 필요한 비밀번호 (응답에는 노출되지 않음)

created_at: 작성일

updated_at: 수정일


⚙️ API 명세서

| 기능             | Method   | URL                       | 요청 예시                                                                                     | 응답 예시                                                                                                                                                       | 상태코드             |
| -------------- | -------- | ------------------------- | ----------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- |
| **일정 생성**      | `POST`   | `/scheduler`              | `json { "title": "회의 일정", "content": "오전 10시 회의", "writer": "김동욱", "password": "1234" } ` | `json { "id": 1, "title": "회의 일정", "content": "오전 10시 회의", "writer": "김동욱", "createdAt": "2025-11-06T15:00:00", "updatedAt": "2025-11-06T15:00:00" } `      | `201 Created`    |
| **단건 조회**      | `GET`    | `/scheduler/{scheduleId}` | `/scheduler/1`                                                                            | `json { "id": 1, "title": "회의 일정", "content": "오전 10시 회의", "writer": "김동욱", "createdAt": "2025-11-06T15:00:00", "updatedAt": "2025-11-06T15:00:00" } `      | `200 OK`         |
| **작성자별 다건 조회** | `GET`    | `/scheduler?writer=홍길동`   | `/scheduler?writer=홍길동`                                                                   | `json [ { "id": 3, "title": "출장 준비", "content": "자료 정리", "writer": "홍길동", "createdAt": "2025-11-06T14:00:00", "updatedAt": "2025-11-06T14:10:00" } ] `      | `200 OK`         |
| **전체 일정 조회**   | `GET`    | `/scheduler`              | `/scheduler`                                                                              | `json [ { "id": 1, "title": "회의 일정", "writer": "김동욱", "content": "오전 10시 회의", "createdAt": "2025-11-06T15:00:00", "updatedAt": "2025-11-06T15:00:00" } ] `  | `200 OK`         |
| **일정 수정**      | `PUT`    | `/scheduler/{scheduleId}` | `json { "title": "회의 일정 (수정)", "writer": "김동욱", "password": "1234" } `                    | `json { "id": 1, "title": "회의 일정 (수정)", "writer": "김동욱", "content": "오전 10시 회의", "createdAt": "2025-11-06T15:00:00", "updatedAt": "2025-11-06T16:00:00" } ` | `200 OK`         |
| **일정 삭제**      | `DELETE` | `/scheduler/{scheduleId}` | `/scheduler/1`                                                                            | (본문 없음)                                                                                                                                                     | `204 No Content` |


🔒 비밀번호 처리 규칙

| 구분              | 규칙                                 |
| --------------- | ---------------------------------- |
| **생성 (POST)**   | 요청 시 `password` 필드 필수              |
| **조회 (GET)**    | 응답에서 `password` 필드 제외              |
| **수정 (PUT)**    | 요청 시 `password` 검증 후 수정 가능         |
| **삭제 (DELETE)** | 삭제 시에도 `password` 검증 필요 (현재 구현 예정) |


🧩 데이터베이스 테이블 스키마

CREATE TABLE scheduler (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    writer VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME(6),
    updated_at DATETIME(6)
);


🧠 추가 정보

| 항목           | 내용                                                        |
| ------------ | --------------------------------------------------------- |
| **JPA 설정**   | `spring.jpa.hibernate.ddl-auto=create` (앱 실행 시 테이블 자동 생성) |
| **로깅 설정**    | SQL 쿼리 출력 (`spring.jpa.show-sql=true`)                    |
| **MySQL 연결** | DB명: `scheduler`, user: `root`, pw: `1234`                |
| **Auditing** | `BaseEntity`를 통해 `createdAt`, `updatedAt` 자동 관리           |
| **패스워드 보호**  | `@JsonProperty(access = WRITE_ONLY)` 으로 응답에서 숨김           |


🧾 예시 시나리오

1️⃣ 일정 생성

POST /scheduler
{
  "title": "스터디 미팅",
  "content": "Spring 프로젝트 리뷰",
  "writer": "김동욱",
  "password": "1111"
}


2️⃣ 일정 수정

PUT /scheduler/1
{
  "title": "스터디 미팅(변경)",
  "writer": "김동욱",
  "password": "1111"
}


3️⃣ 작성자 일정 조회

GET /scheduler?writer=김동욱

📄 작성자 정보

Author: 김동욱

Language: Java 17

Framework: Spring Boot 3.5.7

Database: MySQL 8

IDE: IntelliJ IDEA

Test Tool: Postman


