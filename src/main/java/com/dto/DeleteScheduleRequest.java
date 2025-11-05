package com.dto;

import lombok.Getter;

@Getter
public class DeleteScheduleRequest {

    private String title;
    private String content;
    private String writer;
    private String password;

}
