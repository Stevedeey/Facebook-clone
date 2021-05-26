package com.example.springtfacebook.repositories;

import com.example.springtfacebook.model.Likes;
import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByPostPostId(Long postId);
    List<Likes> findAllByPostPostIdAndPersonId(Long postId, Long personId);
    @Transactional
    void deleteLikesByPostAndPerson(Post post, Person person);
}



