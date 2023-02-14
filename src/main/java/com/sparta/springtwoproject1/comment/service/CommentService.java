package com.sparta.springtwoproject1.comment.service;

import com.sparta.springtwoproject1.board.entity.Board;
import com.sparta.springtwoproject1.board.repository.BoardRepository;
import com.sparta.springtwoproject1.comment.dto.CommentRequestDto;
import com.sparta.springtwoproject1.comment.dto.CommentResponseDto;
import com.sparta.springtwoproject1.comment.entity.Comment;
import com.sparta.springtwoproject1.comment.repository.CommentRepository;
import com.sparta.springtwoproject1.exception.dto.ExcepMsg;
import com.sparta.springtwoproject1.jwt.JwtUtil;
import com.sparta.springtwoproject1.user.entity.Users;
import com.sparta.springtwoproject1.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.springtwoproject1.user.entity.UserRoleEnum.USER;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private  final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    public CommentResponseDto create(Long bId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if(!jwtUtil.validateToken(token)) {
            return null;
        }
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Board board = boardRepository.findById(bId).orElseThrow();
        Comment comment = new Comment(commentRequestDto, board, claims.getSubject());

        commentRepository.save(comment);
        return CommentResponseDto.of(comment);
    }

    public List<Comment> getComment(Long id) {
        List<Comment> comments = commentRepository.findByBoard_IdOrderByCreateAtDesc(id);
        comments.forEach(comment -> log.info("comment : body = {}", comment.getContent(), comment.getIsDeleted()));
        return comments.stream().filter(comment -> comment.getIsDeleted() != true).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto update(Long cId, CommentRequestDto commentRequestDto, HttpServletRequest request) throws  IllegalAccessException {
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Users users = userRepository.findByUserName(claims.getSubject()).orElseThrow();
        if(!jwtUtil.validateToken(token)) {
            return null;
        }
        Comment comment = commentRepository.findById(cId).orElseThrow();
        if(!comment.getUserName().equals(users.getUserName()) && users.getRole() == USER) {
            throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
        }
        comment.update(commentRequestDto.getContent());

        return new CommentResponseDto(comment);

    }

    @Transactional
    public ExcepMsg deleteComment(Long cId, HttpServletRequest request) throws IllegalAccessException {
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Users users = userRepository.findByUserName(claims.getSubject()).orElseThrow();
        if(!jwtUtil.validateToken(token)) {
            return null;
        }
        Comment comment = commentRepository.findById(cId).orElseThrow();
        if(!comment.getUserName().equals(users.getUserName()) && users.getRole() == USER) {
            throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
        }
//        commentRepository.deleteById(cId);
        comment.setIsDeleted();
        return new ExcepMsg("삭제 성공", OK.value());
    }
}
