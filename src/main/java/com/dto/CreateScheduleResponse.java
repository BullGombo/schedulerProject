package com.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponse {

    // ######################################## 속성 필드 ########################################
    private final long id;
    private final String title;
    private final String content;
    private final String writer;
    //private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // ######################################## 생성자 ########################################
    public CreateScheduleResponse(long id, String title, String content, String writer, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        //this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
