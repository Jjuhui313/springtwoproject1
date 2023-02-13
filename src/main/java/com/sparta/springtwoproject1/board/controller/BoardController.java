package com.sparta.springtwoproject1.board.controller;

import com.sparta.springtwoproject1.board.dto.BoardRequestDto;
import com.sparta.springtwoproject1.board.dto.BoardResponseDto;
import com.sparta.springtwoproject1.board.dto.MessageDto;
import com.sparta.springtwoproject1.board.entity.Board;
import com.sparta.springtwoproject1.board.service.BoardService;
import com.sparta.springtwoproject1.comment.entity.Comment;
import com.sparta.springtwoproject1.comment.service.CommentService;
import com.sparta.springtwoproject1.exception.dto.ExcepMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.createBoard(requestDto, request);
    }

    @GetMapping("/board")
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
    }

    @GetMapping("/board/{id}")
    public BoardResponseDto getPost(@PathVariable Long id) {
        List<Comment> comments = commentService.getComment(id);
        BoardResponseDto board = boardService.findById(id);
        return new BoardResponseDto(board, comments);
    }

    @Transactional
    @PatchMapping("/board/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        MessageDto msg = null;

        try {
            msg = boardService.update(id, requestDto, request);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        if(!msg.getSuccess()) {
            return new ResponseEntity<>(msg, UNAUTHORIZED);
        }
        return new ResponseEntity<>(msg, OK);
    }

    @Transactional
    @DeleteMapping("/board/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        ExcepMsg msg = null;
        try {
            msg = boardService.deleteBoard(id, request);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new ExcepMsg("작성자만 삭제/수정할 수 있습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExcepMsg("토큰이 유효하지 않습니다.", BAD_REQUEST.value()), BAD_REQUEST);
        }
        return new ResponseEntity<>(msg, OK);
    }
}
