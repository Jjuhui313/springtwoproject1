package com.sparta.springtwoproject1.board.entity;

import com.sparta.springtwoproject1.Timestamped;
import com.sparta.springtwoproject1.board.dto.BoardRequestDto;
import com.sparta.springtwoproject1.comment.entity.Comment;
import com.sparta.springtwoproject1.user.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Users user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Comment> comment = new ArrayList<>();

    public Board(BoardRequestDto requestDto, Users user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
        this.isDeleted = false;
    }


    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public String getUserName() {

        return this.user.getUserName();
    }

    public void delete(Boolean deleted) {
        this.isDeleted = deleted;
    }
}
