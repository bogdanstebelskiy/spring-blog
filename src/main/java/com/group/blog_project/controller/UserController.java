package com.group.blog_project.controller;

import com.group.blog_project.constants.ValidationConstants;
import com.group.blog_project.dto.User.LoginRequestDTO;
import com.group.blog_project.dto.User.UserRequestDTO;
import com.group.blog_project.dto.User.UserResponseDTO;
import com.group.blog_project.service.UserService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable UUID userId
    ) {
        UserResponseDTO user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO userResponseDTO = userService.createUser(dto);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/api/users/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable UUID userId,
            @RequestBody @Valid UserRequestDTO dto
    ) {
        UserResponseDTO updatedUser = userService.updateUser(dto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID userId
    ) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO userResponseDTO = userService.createUser(dto);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginRequestDTO dto) {
        String jwtToken = userService.verify(dto);
        UserResponseDTO userResponseDTO = userService.getUserByUsername(dto.getUsername());
        Map<String, Object> response = new HashMap<>();
        response.put("jwtToken", jwtToken);
        response.put("user", userResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

