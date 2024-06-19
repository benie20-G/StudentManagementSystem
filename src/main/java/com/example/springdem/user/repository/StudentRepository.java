package com.example.springdem.user.repository;

import com.example.springdem.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<UserModel, Integer> {
    public long countById(Integer id);

    UserModel findByEmail(String email);
}