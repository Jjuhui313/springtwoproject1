package com.sparta.springtwoproject1.user.controller;

import com.sparta.springtwoproject1.user.dto.AuthMessage;
import com.sparta.springtwoproject1.user.dto.SignUpRequestDto;
import com.sparta.springtwoproject1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthMessage> signup(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        String message;
        try {
            message = userService.signup(signUpRequestDto);
        } catch(IllegalStateException e) {
            AuthMessage authMessage = new AuthMessage("중복된 유저이름이 이미 존재합니다.", BAD_REQUEST.value());
            return new ResponseEntity<>(authMessage, BAD_REQUEST);
        }
        AuthMessage authMessage = new AuthMessage(message, OK.value());

        return new ResponseEntity<>(authMessage, OK);
    }


}
