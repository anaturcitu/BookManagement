package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.dto.RegisterRequest;
import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("IN LOGIN PAGE");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerRequest") RegisterRequest request,
                               BindingResult bindingResult, Model model) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", null, "Passwords do not match");
        }

        if (userService.emailExists(request.getEmail())) {
            bindingResult.rejectValue("email", null, "Email already in use");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.registerUser(request);
        return "redirect:/login";
    }

}
