package com.sparta.springtwoproject1.board.dto;

import lombok.Getter;

@Getter
public class MessageDto {
    private Boolean success;
    private Object data;

    public MessageDto(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }
}
