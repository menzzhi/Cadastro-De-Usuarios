package com.projeto.application.service;

import com.projeto.application.dto.post.UserPostDto;
import com.projeto.application.dto.put.UserPutDto;
import com.projeto.application.dto.response.UserResponseDto;
import com.projeto.application.entity.User;
import com.projeto.application.repository.UserRepository;
import jakarta.transaction.TransactionalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserPostDto userPostDto) {

        if(userPostDto.username().isEmpty() || userPostDto.username().isBlank()){
            throw new RuntimeException("Não foi informado um nome de usuário válido");
        } else if (userPostDto.email().isEmpty() || userPostDto.email().isBlank()){
            throw new RuntimeException("Não foi informado um e-mail válido.");
        } else if(userPostDto.password().isEmpty() || userPostDto.password().isBlank()){
            throw new RuntimeException("Não foi informado uma senha válida");
        }

        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(userPostDto.email()))){
            throw new RuntimeException("Esse e-mail já está cadastrado.");
        }

        if (userRepository.findAll().stream().anyMatch(u -> u.getUsername().equals(userPostDto.username()))){
            throw new RuntimeException("Esse nome de usuário já está cadastrado.");
        }

        try{
            User createdUser = new User(userPostDto.username(), userPostDto.email(), userPostDto.password());// É criado uma variável da classe User
            User savedUser = userRepository.save(createdUser);                                                // no qual é utilizado para ser salva dentro do repositório
            return new UserResponseDto(
                    savedUser.getUserId(), savedUser.getUsername(), savedUser.getEmail());
        } catch (TransactionalException transactionalException){
            throw new TransactionalException("E-mail está inválido", transactionalException);
        }

    }

    public List<UserResponseDto> getAllRegisteredUsers() {
        return userRepository.findAll().stream().map(
                u -> new UserResponseDto(u.getUserId(), u.getUsername(), u.getEmail())).toList();
    }


    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.
                findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new UserResponseDto(user.getUserId(), user.getUsername(), user.getEmail());
    }

    public void updateUsernameAndPassword(UserPutDto userPutDto, Long userId) {
        User user = userRepository.
                findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setUsername(userPutDto.username());
        user.setPassword(userPutDto.password());

        userRepository.save(user);
    }

    public UserResponseDto getYourUserdata(String email, String password) {

        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(email)) &&
                userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(email))){
            User user = userRepository.findByEmail(email);
            return new UserResponseDto(user.getUserId(), user.getUsername(), user.getEmail());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
