package com.example.springtfacebook.services;

import com.example.springtfacebook.POJO.CommentMapper;
import com.example.springtfacebook.model.Comment;
import com.example.springtfacebook.model.Person;

import java.util.List;

public interface CommentService {
    boolean createComment(Long userId, Long postId, Comment comment);
    public List<CommentMapper> getComments(Long postId);
    boolean editComment(Long commentId, Person person, Long postId, String comment);
    boolean deleteComment(Long commentId);
}
