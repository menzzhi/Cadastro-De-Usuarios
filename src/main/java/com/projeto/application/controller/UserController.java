package com.projeto.application.controller;

import com.projeto.application.dto.post.UserPostDto;
import com.projeto.application.dto.response.UserResponseDto;
import com.projeto.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Método utilizado para criar um usuário na API. É retornado uma DTO do usuário.
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserPostDto userPostDto){
        UserResponseDto responseUserDto = userService.createUser(userPostDto);
        return ResponseEntity.ok(responseUserDto);
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllRegisteredUsers(){
        List<UserResponseDto> userList = userService.getAllRegisteredUsers();
        return ResponseEntity.ok(userList);
    }
    
}
