package com.example.springtfacebook.services;

import com.example.springtfacebook.model.Person;

public interface LikeService {
    public boolean likePost(Person person, Long postId, String action);
}
