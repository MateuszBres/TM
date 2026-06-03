package com.example.task_manager_backend.user.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;


@Table(name = "accounts")
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name ="created_at")
    private LocalDate createdAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

}
