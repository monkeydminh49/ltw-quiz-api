package com.minhdunk.research.dto;

import com.minhdunk.research.utils.UserGender;
import com.minhdunk.research.utils.UserRole;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
@Data
public class UserInputDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private LocalDate dateOfBirth;

    public UserInputDTO(String firstName, String lastName, String username, String email, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
}
