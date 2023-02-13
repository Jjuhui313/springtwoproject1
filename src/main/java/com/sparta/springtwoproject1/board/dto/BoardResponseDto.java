package com.sparta.springtwoproject1.board.dto;

import com.sparta.springtwoproject1.board.entity.Board;
import com.sparta.springtwoproject1.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class BoardResponseDto {

    private Long id;

    private String title;

    private String userName;

    private String content;

    private List<Comment> comment;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.userName = entity.getUserName();
        this.content = entity.getContent();
        this.comment = entity.getComment();
        this.createdAt = entity.getCreateAt();
        this.modifiedAt = entity.getModifiedAt();
    }

    public BoardResponseDto(BoardResponseDto board, List<Comment> comments) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.userName = board.getUserName();
        this.comment = comments;
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();

    }

    public BoardResponseDto(Long id, String title, String userName, String content, List<Comment> comments, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.content = content;
        this.comment = comments;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static BoardResponseDto of(Board board) {
        return BoardResponseDto.builder()
                .createdAt(board.getCreateAt())
                .modifiedAt(board.getModifiedAt())
                .title(board.getTitle())
                .userName(board.getUserName())
                .content(board.getContent())
                .build();
    }
}
