package com.sparta.springtwoproject1.comment.dto;

import com.sparta.springtwoproject1.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(Comment comment) {

        this.content = comment.getContent();
    }
}
