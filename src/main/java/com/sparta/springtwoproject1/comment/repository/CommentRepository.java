package com.sparta.springtwoproject1.comment.repository;

import com.sparta.springtwoproject1.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard_IdOrderByCreateAtDesc(Long id);

}
