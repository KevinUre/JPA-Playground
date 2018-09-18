package com.example.jpademo.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Post> posts;

    public String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable
    public List<Post> likedPosts;
}
