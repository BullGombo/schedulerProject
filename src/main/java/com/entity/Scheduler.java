package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "scheduler")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scheduler extends BaseEntity {

    // ######################################## 테이블 속성 ########################################
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    // 패스워드는 응답시 json에 포함되어선 안되고, 수정/삭제 요청시 전달되여야함
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    // ######################################## 생성자 ########################################
    public  Scheduler(String title, String content, String writer, String password) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
    }

    // ######################################## 메서드 ########################################
    // 제목, 작성자만 수정
    public void updateSchedule(String title, String writer) {
        this.title = title;
//        this.content = content;
        this.writer = writer;
//        this.password = password;
    }

}
