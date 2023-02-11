package com.sparta.springtwoproject1.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {
    private String userName;
    private String password;
}
