package com.sparta.springtwoproject1.user.dto;

import lombok.Getter;

@Getter
public class AuthMessage {
    private String msg;
    private Integer httpStatus;

    public AuthMessage(String msg, Integer httpStatus) {
        this.msg = msg;
        this.httpStatus = httpStatus;
    }
}
