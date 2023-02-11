package com.sparta.springtwoproject1.user.service;

import com.sparta.springtwoproject1.jwt.JwtUtil;
import com.sparta.springtwoproject1.user.dto.SignUpRequestDto;
import com.sparta.springtwoproject1.user.entity.Users;
import com.sparta.springtwoproject1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    private final String SUCCESS = "회원가입 성공";


    public String signup(SignUpRequestDto requestDto) {
        if(userRepository.findByUserName(requestDto.getUserName()).isPresent()) {
            throw new IllegalArgumentException("이미 동일한 이름의 유저가 존재합니다.");
        }

        Users signUpUser = new Users(requestDto);
        userRepository.save(signUpUser);

        return SUCCESS;
    }
}
