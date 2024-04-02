package com.minhdunk.research.controller;

import com.minhdunk.research.dto.BaseResponse;
import com.minhdunk.research.dto.UserInputDTO;
import com.minhdunk.research.dto.UserOutputDTO;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.mapper.UserMapper;
import com.minhdunk.research.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/user")
    private UserOutputDTO getUserByUsernameInPrincipal(Principal principal){
        return userMapper.getUserOutputDTOFromUser(userService.getUserByUsername(principal.getName()));
    }

    @GetMapping("/users/{id}")
    private UserOutputDTO getUserById(@PathVariable Long id){
        return userMapper.getUserOutputDTOFromUser(userService.getUserById(id));
    }

    @GetMapping("/users/all")
    private List<UserOutputDTO> getAllUsers(){
        return userMapper.getUserOutputDTOsFromUsers(userService.getAllUsers());
    }

//    edit user infor
    @PutMapping("/users/{id}")
    private BaseResponse editUser(@PathVariable Long id, @RequestBody UserInputDTO userInputDTO){
        userService.editUser(id, userInputDTO);
        return new BaseResponse("ok", "Edit user successfully", null);
    }

    @PostMapping("/users/create")
    private BaseResponse createUser(@RequestBody UserInputDTO userInputDTO){
        var user = userService.createUser(userInputDTO);
        return new BaseResponse("ok", "Create user successfully", userMapper.getUserOutputDTOFromUser(user));
    }

    @PostMapping("/users/admin/create")
    private BaseResponse createAdmin(@RequestBody UserInputDTO userInputDTO){
        var user = userService.createAdmin(userInputDTO);
        return new BaseResponse("ok", "Create admin successfully", userMapper.getUserOutputDTOFromUser((User) user));
    }

//    delete user
    @DeleteMapping("/users/{id}")
    private BaseResponse deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new BaseResponse("ok", "Delete user successfully", null);
    }
}
