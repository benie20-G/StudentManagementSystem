package com.example.springdem.user;

import com.example.springdem.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {
    @Autowired private UserService service;
    @GetMapping("/users")
    public  String showUserList(Model model, HttpSession session){
            // User is logged in, retrieve user object from session
         // Retrieve existing session without creating a new one
        if (session.getAttribute("user") == null) {
            return "redirect:/login"; // Redirect to login page if user is not logged in
        }
        List<User>  listUsers = service.listAll();
        UserModel user = (UserModel) session.getAttribute("user");

        model.addAttribute("firstname",user.getFirstname());
        model.addAttribute("lastname",user.getLastname());
        model.addAttribute("listUsers",listUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public  String showNewForm(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("pageTitle","Add new Student");
        return "userForm";
    }

    @PostMapping("/users/save")
    public  String saveUser(User user, RedirectAttributes ra){
        service.save(user);
        ra.addFlashAttribute("message","Student has been saved successfully");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id,Model model,RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit Student (ID:" + id +")");
          return "userForm";

        } catch (UserNotFoundException e) {
          ra.addFlashAttribute("message",e.getMessage());
            return  "redirect:/users";
        }


    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message","Student is now delete");

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());

        }
        return  "redirect:/users";

    }


}
