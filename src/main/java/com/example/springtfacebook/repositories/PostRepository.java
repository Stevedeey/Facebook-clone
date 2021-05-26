package com.example.springtfacebook.repositories;

import com.example.springtfacebook.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByPostId(Long postId);
    @Transactional
    void deletePostByPostIdAndPersonId(Long postId, Long personId);

}

