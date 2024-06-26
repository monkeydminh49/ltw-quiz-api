package com.minhdunk.research.dto;

import com.minhdunk.research.utils.UserGender;
import com.minhdunk.research.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOutputDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private UserRole role;
    private String token;
    private LocalDate dateOfBirth;
    private String avatarId;
    private UserGender gender;

}
