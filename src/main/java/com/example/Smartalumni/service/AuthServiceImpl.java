package com.example.Smartalumni.service;

import com.example.Smartalumni.dto.*;
import com.example.Smartalumni.entity.PasswordResetToken;
import com.example.Smartalumni.entity.User;
import com.example.Smartalumni.repository.PasswordResetTokenRepository;
import com.example.Smartalumni.repository.UserRepository;
import com.example.Smartalumni.security.JwtUtil;
import com.example.Smartalumni.service.AuthService;
import com.example.Smartalumni.service.EmailService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    // REGISTER
    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .department(request.getDepartment())
                .graduationYear(request.getGraduationYear())
                .jobTitle(request.getJobTitle())
                .company(request.getCompany())
                .verified(false)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getId()
        );

        return AuthResponse.builder()
                .token(token)
                .message("Registration successful")
                .email(user.getEmail())
                .role(user.getRole().name())
                .userId(user.getId())
                .fullName(user.getFullName())
                .build();
    }

    // LOGIN
    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getId()
        );

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .email(user.getEmail())
                .role(user.getRole().name())
                .userId(user.getId())
                .fullName(user.getFullName())
                .build();
    }

    // FORGOT PASSWORD
    @Override
    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        passwordResetTokenRepository.deleteByUserId(user.getId());

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .build();

        passwordResetTokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    // RESET PASSWORD
    @Override
    public void resetPassword(ResetPasswordRequest request) {

        PasswordResetToken tokenEntity = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = tokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        passwordResetTokenRepository.delete(tokenEntity);
    }
}