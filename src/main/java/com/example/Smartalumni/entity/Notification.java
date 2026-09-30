package com.example.Smartalumni.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String message;

    private String type; // MENTORSHIP / EVENT / JOB / ANNOUNCEMENT

    @Column(name = "id_read")
    private boolean read;

    private LocalDateTime createdAt = LocalDateTime.now();
}