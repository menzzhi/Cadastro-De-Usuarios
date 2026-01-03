package com.projeto.application.service;

import com.projeto.application.dto.post.UserPostDto;
import com.projeto.application.dto.response.UserResponseDto;
import com.projeto.application.entity.User;
import com.projeto.application.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserPostDto userPostDto) {
        User createdUser = new User(userPostDto.username(), userPostDto.email(), userPostDto.password()); // É criado uma variável da classe User
        User savedUser = userRepository.save(createdUser);                                                // no qual é utilizado para ser salva dentro do repositório
        UserResponseDto responseUserDto = new UserResponseDto(
                savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword());

        return responseUserDto;
    }

    public List<UserResponseDto> getAllRegisteredUsers() {
        List<UserResponseDto> usersList = userRepository.findAll().stream().map(
                u -> new UserResponseDto(u.getUsername(), u.getEmail(), u.getPassword())).toList();

        return usersList;
    }
}
