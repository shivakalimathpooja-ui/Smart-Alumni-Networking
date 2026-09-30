package com.example.Smartalumni.entity;

import java.time.LocalDateTime;

import com.example.Smartalumni.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String department;

    private String graduationYear;

    private String jobTitle;

    private String company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // ✅ ADD THIS
    @Column(nullable = false)
    private boolean verified;

    private String skills;
    private String location;
    private String higherEducation;

    @Column(name = "reset_token")
    private String resetToken;
    private LocalDateTime resetTokenExpiry;

}