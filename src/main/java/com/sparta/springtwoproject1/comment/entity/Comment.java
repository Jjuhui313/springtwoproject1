package com.sparta.springtwoproject1.comment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.springtwoproject1.Timestamped;
import com.sparta.springtwoproject1.board.entity.Board;
import com.sparta.springtwoproject1.comment.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Board board;

    public Comment(CommentRequestDto commentRequestDto, Board board, String userName) {
        this.userName = userName;
        this.content = commentRequestDto.getContent();
        this.board = board;
    }


    public Comment update(String content) {
        this.content = content;
        return this;
    }
}
