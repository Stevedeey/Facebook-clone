package com.example.springtfacebook.repositories;

import com.example.springtfacebook.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostPostId(Long postId);
    Comment findCommentById(Long commentId);
    @Transactional
    void deleteCommentById(Long id);
}
