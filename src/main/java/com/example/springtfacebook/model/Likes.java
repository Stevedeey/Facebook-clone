package com.example.springtfacebook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class Likes {
    @Id
    @SequenceGenerator(
            name = "like_sequence",
            sequenceName = "like_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy  = SEQUENCE,
            generator = "like_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;


    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
}
