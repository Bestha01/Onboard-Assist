package com.onboardassist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="knowledge_documents")
@Getter
@Setter
@NoArgsConstructor
public class KnowledgeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition="TEXT")
    private String content;

    private String source;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
