package com.example.springtfacebook.services;

import com.example.springtfacebook.POJO.PostMapper;
import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.model.Post;

import java.util.List;

public interface PostService {
    boolean createPost(Long userId, Post post);
    List<PostMapper> getPost(Person currentUser);
}
