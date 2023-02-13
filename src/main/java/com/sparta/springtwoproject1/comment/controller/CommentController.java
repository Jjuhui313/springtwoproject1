package com.sparta.springtwoproject1.comment.controller;

import com.sparta.springtwoproject1.board.dto.MessageDto;
import com.sparta.springtwoproject1.comment.dto.CommentRequestDto;
import com.sparta.springtwoproject1.comment.dto.CommentResponseDto;
import com.sparta.springtwoproject1.comment.service.CommentService;
import com.sparta.springtwoproject1.exception.dto.ExcepMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/board/{b-id}/comment")
    public ResponseEntity<Object> createComment(@PathVariable(name = "b-id") Long bId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {

        CommentResponseDto commentResponseDto = null;

        try {
            commentResponseDto = commentService.create(bId, commentRequestDto, request);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(commentResponseDto, OK);
    }

    @PatchMapping("/board/{b-id}/comment/{c-id}")
    public ResponseEntity<Object> update(@PathVariable(name = "c-id") Long cId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        CommentResponseDto commentResponseDto = null;

        try {
            commentResponseDto = commentService.update(cId, commentRequestDto, request);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
        return new ResponseEntity<>(commentResponseDto, OK);
    }

    @DeleteMapping("/board/{b-id}/comment/{c-id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "c-id") Long cId, HttpServletRequest request) {
        ExcepMsg msg = null;
        try {
            msg = commentService.deleteComment(cId, request);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, OK);
    }


}
