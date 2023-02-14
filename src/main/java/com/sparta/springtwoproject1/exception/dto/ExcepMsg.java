package com.sparta.springtwoproject1.exception.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExcepMsg {
    private String msg;
    private Integer status;

    public ExcepMsg(String msg, Integer status) {
        this.msg = msg;
        this.status = status;
    }
}
