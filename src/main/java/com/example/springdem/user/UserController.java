package com.example.springdem.user;
import com.example.springdem.UserModel;
import com.example.springdem.user.Auth.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

       private AuthenticationManager authenticationManager;
//       @Autowired
//       private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userModel", new UserModel());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userModel") UserModel userModel, RedirectAttributes ra) {
        userService.registerUser(userModel);
        ra.addFlashAttribute("message", "User registered successfully!");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userModel", new UserModel());
        return "loginForm";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("userModel") UserModel userModel, HttpServletRequest request, RedirectAttributes ra) {

        String email = userModel.getEmail();
        String password = userModel.getPassword();

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(email, password));
//
//
//        if(authentication.isAuthenticated()){
//            String token = "";
//        }


        UserModel user = userService.login(userModel.getEmail(), userModel.getPassword());
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/users";
        } else {
            ra.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute("userModel") UserModel userModel, HttpServletRequest request, RedirectAttributes ra) {
//
//        String email = userModel.getEmail();
//        String password = userModel.getPassword();
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(email, password));
//
//
//        if(authentication.isAuthenticated()){
//            String token = jwtTokenProvider.generateToken(authentication);
//            HttpSession session = request.getSession();
//            session.setAttribute("token", token);
//
//            return  "redirect:?users";
//        }
//        else{
//            ra.addFlashAttribute("error", "Invalid email or password");
//            return "redirect:/login";
//        }
//    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
