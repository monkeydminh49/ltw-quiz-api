package com.minhdunk.research.service;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.NotFoundException;

import com.minhdunk.research.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("User with username " + username + " not found."));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User with id " + id + " not found."));
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
