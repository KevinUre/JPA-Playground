package com.example.jpademo.repositories;

import com.example.jpademo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespoitory extends JpaRepository<User, Long> {

}