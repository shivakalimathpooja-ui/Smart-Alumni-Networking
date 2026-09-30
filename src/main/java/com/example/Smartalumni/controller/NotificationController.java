package com.example.Smartalumni.controller;

import com.example.Smartalumni.entity.Notification;
import com.example.Smartalumni.repository.NotificationRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repo;

    @GetMapping("/my")
    public Map<String, Object> getMyNotifications(@RequestParam Long userId) {
        List<Notification> list = repo.findByUserIdOrderByCreatedAtDesc(userId);
        long unread = repo.countByUserIdAndReadFalse(userId);

        Map<String, Object> res = new HashMap<>();
        res.put("notifications", list);
        res.put("unreadCount", unread);

        return res;
    }

    @PatchMapping("/mark-read/{id}")
    public String markRead(@PathVariable Long id) {
        Notification n = repo.findById(id).orElseThrow();
        n.setRead(true);
        repo.save(n);
        return "Marked as read";
    }

    @PatchMapping("/mark-all-read")
    public String markAll(@RequestParam Long userId) {
        List<Notification> list = repo.findByUserIdOrderByCreatedAtDesc(userId);
        list.forEach(n -> n.setRead(true));
        repo.saveAll(list);
        return "All marked as read";
    }

    @PostConstruct
    public void testNotification() {
        Notification n = new Notification();
        n.setUserId(1L);
        n.setMessage("Test Notification");
        n.setType("TEST");
        repo.save(n);
    }
}