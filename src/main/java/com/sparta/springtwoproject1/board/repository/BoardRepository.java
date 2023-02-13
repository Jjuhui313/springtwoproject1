package com.sparta.springtwoproject1.board.repository;

import com.sparta.springtwoproject1.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreateAtDesc();
}
