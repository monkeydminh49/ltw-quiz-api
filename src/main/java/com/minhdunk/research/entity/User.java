package com.minhdunk.research.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minhdunk.research.utils.UserGender;
import com.minhdunk.research.utils.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(
        name = "USERS"
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private Boolean enabled;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private UserGender gender;

}
