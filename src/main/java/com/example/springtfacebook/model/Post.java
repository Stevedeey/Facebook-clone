package com.example.springtfacebook.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

//@Data
@Entity
public class Post {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy  = SEQUENCE,
            generator = "post_sequence"
    )
    @Column(
            name = "postId",
            updatable = false
    )
    private Long postId;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "VARCHAR(250)"
    )
    private String title;

    @Column(
            name = "body",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String body;

    @Column(
            name = "imageName",
            nullable = false,
            columnDefinition = "VARCHAR(250)"
    )
    private String imageName;



    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Person person;

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToOne(mappedBy = "post")
    private Likes mylike;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Likes getMylike() {
        return mylike;
    }

    public void setMylike(Likes mylike) {
        this.mylike = mylike;
    }
}
