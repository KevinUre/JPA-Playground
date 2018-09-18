package com.example.jpademo.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Post {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    public User author;

    public String contents;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "likedPosts")
    public List<User> likes;

}
