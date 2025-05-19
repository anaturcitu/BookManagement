package com.unibuc.bookmanagement.controllers;

import com.unibuc.bookmanagement.dto.RegisterRequest;
import com.unibuc.bookmanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        log.info("Se accesează pagina de autentificare.");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        log.info("Se accesează formularul de înregistrare.");
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerRequest") RegisterRequest request,
                               BindingResult bindingResult, Model model) {
        log.info("Se încearcă înregistrarea utilizatorului cu email: {}", request.getEmail());
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "", "Parolele nu se potrivesc");
            log.warn("Eroare la înregistrare: parolele nu se potrivesc pentru email {}", request.getEmail());
        }

        if (userService.emailExists(request.getEmail())) {
            bindingResult.rejectValue("email", "", "Email deja utilizat");
            log.warn("Eroare la înregistrare: emailul {} este deja folosit", request.getEmail());
        }


        if (bindingResult.hasErrors()) {
            log.warn("Înregistrare eșuată din cauza erorilor de validare.");
            return "register";
        }

        userService.registerUser(request);
        log.info("Utilizatorul {} a fost înregistrat cu succes.", request.getUsername());
        return "redirect:/login";
    }
}
