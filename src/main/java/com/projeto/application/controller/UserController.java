package com.projeto.application.controller;

import com.projeto.application.dto.post.UserPostDto;
import com.projeto.application.dto.put.UserPutDto;
import com.projeto.application.dto.response.UserResponseDto;
import com.projeto.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<String> createUser(@RequestBody UserPostDto userPostDto){
        UserResponseDto user = userService.createUser(userPostDto);
        ServletUriComponentsBuilder servletUriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
        servletUriComponentsBuilder.path("/{userId}");
        URI uri = servletUriComponentsBuilder.build(user.userId());
        return ResponseEntity.created(uri).body("Seu cadastro foi criado com sucesso!");
    }

    // Método que retorna todos os usuários cadastrados na API. Retorna uma lista de DTO.
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllRegisteredUsers(){
        List<UserResponseDto> userList = userService.getAllRegisteredUsers();
        return ResponseEntity.ok(userList);
    }

    // Método criado para retornar a DTO de um usuário específico, através do ID dele.
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId){
        UserResponseDto userById = userService.getUserById(userId);
        return ResponseEntity.ok(userById);
    }

    // Método criado para que o usuário retorne algumas informações do próprio cadastro, utilizando o e-mail e senha dele
    @GetMapping("/data")
    public ResponseEntity<UserResponseDto> getYourData(@RequestParam String email, @RequestParam String password){
        UserResponseDto yourUserdata = userService.getYourUserdata(email, password);
        return ResponseEntity.ok(yourUserdata);
    }

    // Método que atualiza o nome e senha do usuário.
    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateUsernameAndPassword(
            @RequestBody UserPutDto userPutDto, @PathVariable Long userId){
        userService.updateUsernameAndPassword(userPutDto, userId);
        return ResponseEntity.noContent().build();
    }
}
