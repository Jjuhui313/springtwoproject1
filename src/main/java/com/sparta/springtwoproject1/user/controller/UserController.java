package com.sparta.springtwoproject1.user.controller;

import com.sparta.springtwoproject1.user.dto.AuthMessage;
import com.sparta.springtwoproject1.user.dto.LoginRequestDto;
import com.sparta.springtwoproject1.user.dto.SignUpRequestDto;
import com.sparta.springtwoproject1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
        } catch(IllegalArgumentException e) {
            AuthMessage authMessage = new AuthMessage("중복된 username 입니다.", BAD_REQUEST.value());
            return new ResponseEntity<>(authMessage, BAD_REQUEST);
        }
        AuthMessage authMessage = new AuthMessage(message, OK.value());

        return new ResponseEntity<>(authMessage, OK);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthMessage> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) {
        String userToken;

        try {
            userToken = userService.login(loginDto);
        }
        catch(IllegalArgumentException e) {
            AuthMessage authMessage = new AuthMessage("회원을 찾을 수 없습니다.", BAD_REQUEST.value());
            return new ResponseEntity<>(authMessage, BAD_REQUEST);
        }
        response.addHeader("Authorization", userToken);

        AuthMessage authMessage = new AuthMessage("로그인 성공", OK.value());
        return new ResponseEntity<>(authMessage, OK);
    }


}
