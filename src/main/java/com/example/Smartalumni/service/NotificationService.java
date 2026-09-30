package com.example.Smartalumni.service;

import com.example.Smartalumni.entity.Notification;
import com.example.Smartalumni.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repo;

    public void create(Long userId, String message, String type) {
        Notification notification = Notification.builder()
                .userId(userId)
                .message(message)
                .type(type)
                .build();

        repo.save(notification);
    }
}