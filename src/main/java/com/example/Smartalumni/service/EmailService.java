package com.example.Smartalumni.service;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String token);
}