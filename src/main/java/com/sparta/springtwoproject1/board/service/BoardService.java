package com.sparta.springtwoproject1.board.service;

import com.sparta.springtwoproject1.board.dto.BoardRequestDto;
import com.sparta.springtwoproject1.board.dto.BoardResponseDto;
import com.sparta.springtwoproject1.board.dto.MessageDto;
import com.sparta.springtwoproject1.board.entity.Board;
import com.sparta.springtwoproject1.board.repository.BoardRepository;
import com.sparta.springtwoproject1.comment.service.CommentService;
import com.sparta.springtwoproject1.exception.dto.ExcepMsg;
import com.sparta.springtwoproject1.jwt.JwtUtil;
import com.sparta.springtwoproject1.user.entity.Users;
import com.sparta.springtwoproject1.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.springtwoproject1.user.entity.UserRoleEnum.ADMIN;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CommentService commentService;

    public List<BoardResponseDto> getPosts() {
        List<BoardResponseDto> board = boardRepository.findAllByOrderByCreateAtDesc().stream().map(BoardResponseDto::new).collect(Collectors.toList());
        return board.stream().map(boardResponseDto -> new BoardResponseDto(boardResponseDto, commentService.getComment(boardResponseDto.getId()))).collect(Collectors.toList());
    }

    public BoardResponseDto findById(Long id) {
        Board entity = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다.")
        );
        return new BoardResponseDto(entity);

    }

    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if(token == null) {
            return null;
        }
        if(!jwtUtil.validateToken(token)) {
            return null;
        }

        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Users user = userRepository.findByUserName(claims.getSubject()).orElseThrow();

        Board board = new Board(requestDto, user);
        Board saveBoard = boardRepository.save(board);
//        return new BoardResponseDto(savaBoard);
//        boardRepository.save(board);

        return new BoardResponseDto(saveBoard);

    }

    public MessageDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request) throws IllegalAccessException {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Users users = userRepository.findByUserName(claims.getSubject()).orElseThrow();

        if(token == null) {
            return null;
        }
        if(!jwtUtil.validateToken(token) || users.getRole().equals(ADMIN)) {
            return null;
        }

        if(!board.getUser().getId().equals(users.getId())) {
            throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
        }
        board.update(requestDto);
        return new MessageDto(true, new BoardResponseDto(board));

    }

    public ExcepMsg deleteBoard(Long id, HttpServletRequest request) throws IllegalAccessException {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Users users = userRepository.findByUserName(claims.getSubject()).orElseThrow();

        if(token == null) {
            return null;
        }
        if(!jwtUtil.validateToken(token) || users.getRole().equals(ADMIN)) {
            return null;
        }

        if(!board.getUser().getId().equals(users.getId())) {
            throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
        }
        boardRepository.deleteById(id);
        return new ExcepMsg("게시글 삭제 성공", OK.value());
    }
}
