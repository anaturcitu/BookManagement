package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.dto.RegisterRequest;
import com.unibuc.bookmanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        log.info("S-a accesat pagina de login");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        log.info("S-a accesat pagina de înregistrare");
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerRequest") RegisterRequest request,
                               BindingResult bindingResult, Model model) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            log.warn("Parolele nu se potrivesc pentru emailul: {}", request.getEmail());
            bindingResult.rejectValue("confirmPassword", "invalid.confirmPassword", "Passwords do not match");
        }

        if (userService.emailExists(request.getEmail())) {
            log.warn("Încercare de înregistrare cu email deja existent: {}", request.getEmail());
            bindingResult.rejectValue("email", "invalid.email", "Email already in use");
        }

        if (bindingResult.hasErrors()) {
            log.info("Înregistrare eșuată pentru emailul: {}", request.getEmail());
            return "register";
        }

        userService.registerUser(request);
        log.info("Utilizator înregistrat cu succes: {}", request.getEmail());
        return "redirect:/login";
    }
}
