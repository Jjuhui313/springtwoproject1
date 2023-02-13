package com.sparta.springtwoproject1.user.service;

import com.sparta.springtwoproject1.jwt.JwtUtil;
import com.sparta.springtwoproject1.user.dto.LoginRequestDto;
import com.sparta.springtwoproject1.user.dto.SignUpRequestDto;
import com.sparta.springtwoproject1.user.entity.Users;
import com.sparta.springtwoproject1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    private final String SUCCESS = "회원가입 성공";


    public String signup(SignUpRequestDto requestDto) {
        if(userRepository.findByUserName(requestDto.getUserName()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저이름입니다.");
        }

        Users signUpUser = new Users(requestDto);
        userRepository.save(signUpUser);

        return SUCCESS;
    }

    public String login(LoginRequestDto loginDto) {
        Optional<Users> findUser = userRepository.findByUserName(loginDto.getUserName());

        if(findUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        Users preUser = findUser.orElseThrow();

        if(!preUser.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("아이디/비밀번호가 일치하지 않습니다.");
        }
        return jwtUtil.createToken(preUser.getUserName());
    }
}
