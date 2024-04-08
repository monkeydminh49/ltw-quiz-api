package com.minhdunk.research.service;


import com.minhdunk.research.dto.UserInputDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;

import com.minhdunk.research.repository.UserRepository;
import com.minhdunk.research.utils.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("User with username " + username + " not found."));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User with id " + id + " not found."));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User editUser(Long id, UserInputDTO userInputDTO) {
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User with id " + id + " not found."));
//        kiem tra cac truong co thay doi hay khong neu co thi set lai
        if (userInputDTO.getFirstName() != null) {
            user.setFirstName(userInputDTO.getFirstName());
        }
        if (userInputDTO.getLastName() != null) {
            user.setLastName(userInputDTO.getLastName());
        }
        if (userInputDTO.getEmail() != null) {
            user.setEmail(userInputDTO.getEmail());
        }
        if (userInputDTO.getUsername() != null) {
            user.setUsername(userInputDTO.getUsername());
        }
        if (userInputDTO.getDateOfBirth() != null) {
            user.setDateOfBirth(userInputDTO.getDateOfBirth());
        }
        if (userInputDTO.getGender() != null) {
            user.setGender(userInputDTO.getGender());
        }
        if (userInputDTO.getUsername() != null) {
            user.setUsername(userInputDTO.getUsername());
        }
        return userRepository.save(user);
    }
    @Transactional
    public User createUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        user.setUsername(userInputDTO.getUsername());
        user.setDateOfBirth(userInputDTO.getDateOfBirth());
        user.setRole(UserRole.ROLE_STUDENT);
        user.setPassword(userInputDTO.getPassword());
        return userRepository.save(user);
    }
    @Transactional
    public Object createAdmin(UserInputDTO userInputDTO) {
        User user = new User();
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        user.setUsername(userInputDTO.getUsername());
        user.setDateOfBirth(userInputDTO.getDateOfBirth());
        user.setRole(UserRole.ROLE_ADMIN);
        user.setPassword(userInputDTO.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
