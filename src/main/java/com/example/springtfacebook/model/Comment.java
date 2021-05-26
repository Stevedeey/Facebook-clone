package com.example.springtfacebook.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
public class Comment {
    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy  = SEQUENCE,
            generator = "comment_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;



    @Column(
            name = "comment",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String comment;


    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
}
