package com.sparta.springtwoproject1.comment.dto;

import com.sparta.springtwoproject1.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {

    private String content;
    private String userName;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment entity) {
        this.content = entity.getContent();
        this.userName = entity.getUserName();
        this.createAt = entity.getCreateAt();
        this.modifiedAt = entity.getModifiedAt();
    }

    public CommentResponseDto(String content, String userName, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.content = content;
        this.userName = userName;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .userName(comment.getUserName())
                .createAt(comment.getCreateAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
