package com.example.Smartalumni.service;

import com.example.Smartalumni.dto.*;
import com.example.Smartalumni.dto.AuthResponse;
import com.example.Smartalumni.dto.ForgotPasswordRequest;
import com.example.Smartalumni.dto.LoginRequest;
import com.example.Smartalumni.dto.RegisterRequest;
import com.example.Smartalumni.dto.ResetPasswordRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    String forgotPassword(String email);

    String resetPassword(String token, String newPassword);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
