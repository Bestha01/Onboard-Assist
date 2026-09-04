package com.onboardassist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="chat_history")
@Getter
@Setter
@NoArgsConstructor
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String sessionId;

    @Column(columnDefinition="TEXT")
    private String question;

    @Column(columnDefinition="TEXT")
    private String answer;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
