# 📘 Scheduler Project API 명세서

프로젝트명 : Scheduler Project

개발 환경 : Spring Boot 3.5.7, Java 17, MySQL 8, IntelliJ

주요 기술 스택 : Spring Web / Spring Data JPA / Lombok / MySQL

테스트 툴 : Postman


---
---


## 🗂️ 디렉터리 구조


```
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
```


---

## 🧱 ERD (Entity Relationship Diagram)


<img width="826" height="1344" alt="entityManagerFactory(EntityManagerFactoryBuilder, PersistenceManagedTypes)" src="https://github.com/user-attachments/assets/535339a5-6264-42bc-b7c2-43396987ac80" />



```
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
```


---

## 🧱 클래스 다이어그램

<img width="2812" height="2804" alt="SchedulerDiagram" src="https://github.com/user-attachments/assets/85c8d3b2-fadd-4119-b904-150dc9837374" />

<img width="4034" height="3788" alt="SchedulerDiagram2" src="https://github.com/user-attachments/assets/b8290f14-797d-4f69-b4e1-0c408eed946c" />


---

## 📌 데이터 테이블 컬럼 속성 설명


<img width="550" height="387" alt="image" src="https://github.com/user-attachments/assets/543fec35-1c51-4bab-802d-3b5e9b10f99a" />


<img width="406" height="301" alt="image" src="https://github.com/user-attachments/assets/df38a8d3-b1a9-4983-82db-b15170a811b3" />


- id: 일정의 고유 식별자 (자동 증가)

- title: 일정 제목

- content: 일정 내용

- writer: 작성자명

- password: 수정/삭제 시 필요한 비밀번호 (응답에는 노출되지 않음)

- created_at: 작성일

- updated_at: 수정일

---

## ⚙️ API 명세서

| 기능             | Method   | URL                       | 요청 예시                                                                                     | 응답 예시                                                                                                                                                       | 상태코드             |
| -------------- | -------- | ------------------------- | ----------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- |
| **일정 생성**      | `POST`   | `/scheduler`              | `json { "title": "회의 일정", "content": "오전 10시 회의", "writer": "김동욱", "password": "1234" } ` | `json { "id": 1, "title": "회의 일정", "content": "오전 10시 회의", "writer": "김동욱", "createdAt": "2025-11-06T15:00:00", "updatedAt": "2025-11-06T15:00:00" } `      | `201 Created`    |
| **단건 조회**      | `GET`    | `/scheduler/{scheduleId}` | `/scheduler/1`                                                                            | `json { "id": 1, "title": "회의 일정", "content": "오전 10시 회의", "writer": "김동욱", "createdAt": "2025-11-06T15:00:00", "updatedAt": "2025-11-06T15:00:00" } `      | `200 OK`         |
| **작성자별 다건 조회** | `GET`    | `/scheduler?writer=홍길동`   | `/scheduler?writer=홍길동`                                                                   | `json [ { "id": 3, "title": "출장 준비", "content": "자료 정리", "writer": "홍길동", "createdAt": "2025-11-06T14:00:00", "updatedAt": "2025-11-06T14:10:00" } ] `      | `200 OK`         |
| **전체 일정 조회**   | `GET`    | `/scheduler`              | `/scheduler`                                                                              | `json [ { "id": 1, "title": "회의 일정", "writer": "김동욱", "content": "오전 10시 회의", "createdAt": "2025-11-06T15:00:00", "updatedAt": "2025-11-06T15:00:00" } ] `  | `200 OK`         |
| **일정 수정**      | `PUT`    | `/scheduler/{scheduleId}` | `json { "title": "회의 일정 (수정)", "writer": "김동욱", "password": "1234" } `                    | `json { "id": 1, "title": "회의 일정 (수정)", "writer": "김동욱", "content": "오전 10시 회의", "createdAt": "2025-11-06T15:00:00", "updatedAt": "2025-11-06T16:00:00" } ` | `200 OK`         |
| **일정 삭제**      | `DELETE` | `/scheduler/{scheduleId}` | `/scheduler/1`                                                                            | (본문 없음)                                                                                                                                                     | `204 No Content` |

---


## 🔒 비밀번호 처리 규칙

| 구분              | 규칙                                 |
| --------------- | ---------------------------------- |
| **생성 (POST)**   | 요청 시 `password` 필드 필수              |
| **조회 (GET)**    | 응답에서 `password` 필드 제외              |
| **수정 (PUT)**    | 요청 시 `password` 검증 후 수정 가능         |
| **삭제 (DELETE)** | 삭제 시에도 `password` 검증 필요 (현재 구현 예정) |


---


## 🧩 데이터베이스 테이블 스키마


```
CREATE TABLE scheduler (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    writer VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME(6),
    updated_at DATETIME(6)
);
```


---


### 🧠 추가 정보

| 항목           | 내용                                                        |
| ------------ | --------------------------------------------------------- |
| **JPA 설정**   | `spring.jpa.hibernate.ddl-auto=create` (앱 실행 시 테이블 자동 생성) |
| **로깅 설정**    | SQL 쿼리 출력 (`spring.jpa.show-sql=true`)                    |
| **MySQL 연결** | DB명: `scheduler`, user: `root`, pw: `1234`                |
| **Auditing** | `BaseEntity`를 통해 `createdAt`, `updatedAt` 자동 관리           |
| **패스워드 보호**  | `@JsonProperty(access = WRITE_ONLY)` 으로 응답에서 숨김           |


---
---

### 🧭 전체 흐름 요약


```
Client (Postman, Browser)
    ↓
[Controller]
    ↓
[Service]
    ↓
[Repository → DB(Entity)]
```

- 요청 시: JSON → DTO → Entity → DB

- 응답 시: DB(Entity) → DTO → JSON

---

### ⚙️ 1️⃣ POST 요청 (일정 생성)

#### 🧩 요청 예시

```
POST /scheduler
{
  "title": "일정제목5",
  "content": "5",
  "writer": "홍길동",
  "password": "55555"
}

```

#### 🔄 흐름

| 단계                              | 클래스 / 메서드                                                                        | 역할                                          |
| ------------------------------- | -------------------------------------------------------------------------------- | ------------------------------------------- |
| **① Client → Controller**       | `SchedulerController.createSchedule(@RequestBody CreateScheduleRequest request)` | JSON이 `CreateScheduleRequest` DTO 객체로 역직렬화됨 |
| **② Controller → Service**      | `schedulerService.createSchedule(request)`                                       | Controller가 DTO를 그대로 Service에 전달            |
| **③ Service → Entity**          | `new Scheduler(title, content, writer, password)`                                | DTO 데이터를 `Scheduler` 엔티티로 변환                |
| **④ Service → Repository**      | `schedulerRepository.save(scheduler)`                                            | 엔티티를 DB에 저장 (JPA가 SQL INSERT 실행)            |
| **⑤ Repository → DB**           | DB의 `scheduler` 테이블에 행 생성                                                        | `id`, `created_at`, `updated_at` 자동 생성      |
| **⑥ DB → Service → Controller** | `CreateScheduleResponse` DTO 생성                                                  | Entity를 응답용 DTO로 변환                         |
| **⑦ Controller → Client**       | JSON 응답 반환                                                                       | DTO → JSON 직렬화 후 반환                         |


#### 🧠 정리
```
JSON → DTO → Entity → DB
DB → Entity → DTO → JSON
```

---

### ⚙️ 2️⃣ GET 요청 (단건 조회)

#### 요청 예시
```
GET /scheduler/1
```

#### 흐름

| 단계                    | 클래스 / 메서드                                        | 역할                                  |
| --------------------- | ------------------------------------------------ | ----------------------------------- |
| ① Controller          | `getSchedule(@PathVariable Long scheduleId)`     | URL `{scheduleId}` 값을 메서드 매개변수에 바인딩 |
| ② Service             | `schedulerRepository.findById(scheduleId)`       | 해당 ID의 일정 엔티티 조회                    |
| ③ Repository          | JPA가 SQL `SELECT * FROM scheduler WHERE id=?` 실행 | 결과를 `Scheduler` 객체로 변환              |
| ④ Service             | `new GetScheduleResponse(entity)`                | 엔티티를 DTO로 변환                        |
| ⑤ Controller → Client | DTO → JSON 변환 후 응답                               |                                     |


---

### ⚙️ 3️⃣ GET 요청 (작성자 기준 다건 조회)

#### 요청 예시

```
GET /scheduler?writer=홍길동
```

| 단계                    | 클래스 / 메서드                                                             | 역할                                 |
| --------------------- | --------------------------------------------------------------------- | ---------------------------------- |
| ① Controller          | `getAllWritersSchedules(@RequestParam(required=false) String writer)` | 쿼리 파라미터(`writer`)를 받아서 Service로 전달 |
| ② Service             | `schedulerRepository.findByWriter(writer)` (or 전체조회)                  | 조건에 맞는 일정 목록 조회                    |
| ③ Repository          | SQL `SELECT * FROM scheduler WHERE writer='홍길동'`                      | 여러 개의 `Scheduler` 엔티티 반환           |
| ④ Service             | 각 Entity → `GetScheduleResponse` DTO로 변환 (Stream or Loop)             |                                    |
| ⑤ Controller → Client | DTO 리스트를 JSON 배열로 직렬화해 응답                                             |                                    |


---

### ⚙️ 4️⃣ PUT 요청 (일정 수정)

#### 요청 예시

```
PUT /scheduler/3
{
  "title": "수정된 일정",
  "writer": "홍길동"
}
```

| 단계                    | 클래스 / 메서드                                                                                   | 역할                               |
| --------------------- | ------------------------------------------------------------------------------------------- | -------------------------------- |
| ① Controller          | `updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request)` | PathVariable과 Body JSON을 DTO로 받음 |
| ② Service             | `findById(scheduleId)` → 엔티티 조회                                                             | 존재 확인                            |
| ③ Service             | `scheduler.updateSchedule(title, writer)`                                                   | 엔티티의 필드값 수정                      |
| ④ JPA                 | 변경감지(Dirty Checking) → 자동 UPDATE SQL 실행                                                     |                                  |
| ⑤ Service             | 수정된 Entity → `UpdateScheduleResponse` DTO로 변환                                               |                                  |
| ⑥ Controller → Client | DTO → JSON 응답 반환                                                                            |                                  |


#### 🧠 포인트

- 트랜잭션 안에서 엔티티의 필드가 바뀌면
- JPA가 자동으로 UPDATE 쿼리를 생성해 DB에 반영함.

---

### ⚙️ 5️⃣ DELETE 요청 (일정 삭제)

#### 요청 예시
```
DELETE /scheduler/3
```

| 단계                    | 클래스 / 메서드                                       | 역할                     |
| --------------------- | ----------------------------------------------- | ---------------------- |
| ① Controller          | `deleteSchedule(@PathVariable Long scheduleId)` | PathVariable로 일정 ID 수신 |
| ② Service             | `schedulerRepository.deleteById(scheduleId)`    | 존재 여부 확인 후 삭제 실행       |
| ③ Repository          | SQL `DELETE FROM scheduler WHERE id=?` 실행       |                        |
| ④ Controller → Client | HTTP 상태코드 204(No Content) 응답                    |                        |


---

### 🧾 정리 요약표

| 요청            | 데이터 흐름                               | 주요 역할           |
| ------------- | ------------------------------------ | --------------- |
| **POST**      | JSON → DTO → Entity → DB             | 새 일정 생성         |
| **GET (단건)**  | DB → Entity → DTO → JSON             | 특정 ID 일정 조회     |
| **GET (작성자)** | DB → List<Entity> → List<DTO> → JSON | 특정 작성자 일정 목록 조회 |
| **PUT**       | JSON → DTO → Entity 수정 → DB          | 일정 수정           |
| **DELETE**    | ID 값 → DB 삭제                         | 일정 삭제           |



| 계층         | 역할            | 장점             |
| ---------- | ------------- | -------------- |
| Controller | HTTP 요청/응답 처리 | 클라이언트와의 통신만 담당 |
| Service    | 비즈니스 로직 수행    | 재사용성, 유지보수성 ↑  |
| Repository | DB 접근         | DB 교체나 변경에도 유연 |


---
---

### 🧾 예시 시나리오

1️⃣ 일정 생성

```
POST /scheduler
{
  "title": "스터디 미팅",
  "content": "Spring 프로젝트 리뷰",
  "writer": "김동욱",
  "password": "1111"
}
```


2️⃣ 일정 수정

```
PUT /scheduler/1
{
  "title": "스터디 미팅(변경)",
  "writer": "김동욱",
  "password": "1111"
}
```


3️⃣ 작성자 일정 조회

```
GET /scheduler?writer=김동욱
```

---


### 📄 작성자 정보

Author: 김동욱

Language: Java 17

Framework: Spring Boot 3.5.7

Database: MySQL 8

IDE: IntelliJ IDEA

Test Tool: Postman


