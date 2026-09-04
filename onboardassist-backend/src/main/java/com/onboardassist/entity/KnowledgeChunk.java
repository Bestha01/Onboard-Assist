package com.onboardassist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="knowledge_chunks")
@Getter
@Setter
@NoArgsConstructor
public class KnowledgeChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private KnowledgeDocument document;

    @Column(columnDefinition="TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String embedding;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
