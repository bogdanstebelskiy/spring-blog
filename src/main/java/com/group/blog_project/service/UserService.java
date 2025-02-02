package com.group.blog_project.service;

import ch.qos.logback.core.pattern.color.BoldCyanCompositeConverter;
import com.group.blog_project.dto.Post.PostResponseDTO;
import com.group.blog_project.dto.User.LoginRequestDTO;
import com.group.blog_project.dto.User.UserRequestDTO;
import com.group.blog_project.dto.User.UserResponseDTO;
import com.group.blog_project.exception.VerificationException;
import com.group.blog_project.mapper.UserMapper;
import com.group.blog_project.model.Post;
import com.group.blog_project.model.Role;
import com.group.blog_project.model.User;
import com.group.blog_project.repository.Role.RoleRepository;
import com.group.blog_project.repository.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String verify(LoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPasswordHash()));

        if(!authentication.isAuthenticated()) {
            throw new VerificationException("Verification Failed!");
        }

        return jwtService.generateToken(dto.getUsername());
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        List<Role> roles = dto.getRoleNames().stream()
                .map(roleName -> roleRepository.findFirstByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Role with name=%s was not found!", roleName))))
                .collect(Collectors.toList());

        String encodedPassword = encoder.encode(dto.getPasswordHash());

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .passwordHash(encodedPassword)
                .avatarUrl(dto.getAvatarUrl())
                .roles(roles)
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO dto, UUID id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%s was not found!", id)));

        List<Role> roles = dto.getRoleNames().stream()
                .map(roleName -> roleRepository.findFirstByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Role with name=%s was not found!", roleName))))
                .collect(Collectors.toList());


        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());
        existingUser.setAvatarUrl(dto.getAvatarUrl());
        existingUser.setRoles(roles);
        existingUser.setPasswordHash(dto.getPasswordHash());
        existingUser.setUpdatedAt(new Date());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponseDto(updatedUser);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%s was not found!", id)));
        return userMapper.toResponseDto(user);
    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No user with username=%s was found!", username)));
        return userMapper.toResponseDto(user);
    }
}


