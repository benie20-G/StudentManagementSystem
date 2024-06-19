package com.example.springdem;

import com.example.springdem.user.User;
import com.example.springdem.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("inezanicole@gmail.com");
        user.setPassword("1234");
        user.setFirstname("Ineza");
        user.setLastname("Nicole");

        User savedUser = repo.save(user);


        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);


    }

    @Test
    public void testListAllUsers() {
        Iterable<User> users = repo.findAll();
        assertThat(users).hasSizeGreaterThan(0);

        for (User user : users) {
            System.out.println(user);

        }

    }

    @Test
    public void testUdate() {
        Integer userid = 1;
        Optional<User> optionalUser = repo.findById(userid);

        User user = optionalUser.get();
        user.setPassword("irera");
        repo.save(user);

        User updateUser = repo.findById(userid).get();
        assertThat(updateUser.getPassword()).isEqualTo("irera");

    }

    @Test
    public  void  testGet(){
        Integer userid = 1;
        Optional<User> optionalUser = repo.findById(userid);
        assertThat(optionalUser).isPresent();
        User user = optionalUser.get();
        System.out.println(user);

    }

    @Test
    public  void testDeleteUser(){
        Integer userid = 2;
        repo.deleteById(userid);

        Optional<User> optionalUser = repo.findById(userid);
        assertThat(optionalUser).isNotPresent();

    }
}
