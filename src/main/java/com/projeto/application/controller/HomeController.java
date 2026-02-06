package com.projeto.application.controller;

import com.projeto.application.dto.post.UserPostDto;
import com.projeto.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping
public class HomeController {

    private final UserService userService;
    private final UserController userController;

    public HomeController(UserService userService, UserController userController) {
        this.userService = userService;
        this.userController = userController;
    }

    @GetMapping("/home")
    public String homePage(){
        return "index";
    }

    @GetMapping("/home/register")
    public String registerUser(){
        return "register.html";
    }

    @PostMapping("/home/register")
    public ResponseEntity<String> registerUser(@ModelAttribute UserPostDto userPostDto){
        return userController.createUser(userPostDto);
    }
}
