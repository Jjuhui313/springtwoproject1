package com.sparta.springtwoproject1.user.entity;

import com.sparta.springtwoproject1.Timestamped;
import com.sparta.springtwoproject1.user.dto.SignUpRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

import static com.sparta.springtwoproject1.user.entity.UserRoleEnum.USER;

@Entity
@Getter
@NoArgsConstructor
@Validated
public class Users extends Timestamped {
    @Id @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public Users(SignUpRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
        this.role = USER;
    }
}
