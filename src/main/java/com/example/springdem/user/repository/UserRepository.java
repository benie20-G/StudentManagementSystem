package com.example.springdem.user.repository;

import com.example.springdem.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
     public long countById(Integer id);
}
