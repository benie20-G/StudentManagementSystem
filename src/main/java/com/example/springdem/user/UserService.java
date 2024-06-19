package com.example.springdem.user;


import com.example.springdem.UserModel;
import com.example.springdem.user.repository.StudentRepository;
import com.example.springdem.user.repository.UserRepository;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository repo;
    @Autowired private StudentRepository srep;

    @PersistenceContext
    private EntityManager entityManager;

    public  List<User> listAll(){
        return (List<User>) repo.findAll();

    }

    public  void save(User user){
        repo.save(user);
    }

    public  User get(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
                if(result.isPresent()){
                    return result.get();
                }
                throw  new UserNotFoundException("Could not find user with id "+id);
    }


    public  void  delete(Integer id) throws UserNotFoundException {
        Long count= repo.countById(id);
         if(count==null || count==0){
             throw new UserNotFoundException("Could not find user with that  id ");
         }
        repo.deleteById(id);
    }


    public UserModel login(String email, String password) {

        Query query = entityManager.createQuery("SELECT u FROM UserModel u WHERE u.email = :email");
        query.setParameter("email", email);

        try {
            UserModel user = (UserModel) query.getSingleResult();
            // Check if the provided password matches the stored password
            if (user.getPassword().equals(password)) {
                return user; // Return the user if login is successful
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle exceptions (e.g., no user found for the provided email)
        }

        return null;// Return null if login fails
    }

    public void registerUser(UserModel userModel) {
        srep.save(userModel);
    }
}
