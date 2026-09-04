package com.onboardassist.controller;

import com.onboardassist.dto.KnowledgeRequest;
import com.onboardassist.entity.KnowledgeDocument;
import com.onboardassist.repository.KnowledgeDocumentRepository;
import com.onboardassist.service.KnowledgeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
@CrossOrigin
@RequiredArgsConstructor
public class KnowledgeController {

    private final KnowledgeService knowledgeService;
    private final KnowledgeDocumentRepository documentRepository;

    @PostMapping("/documents")
    public ResponseEntity<KnowledgeDocument> addDocument(@Valid @RequestBody KnowledgeRequest request) {
        return ResponseEntity.ok(knowledgeService.addDocument(request.title(), request.content(), request.source()));
    }

    @GetMapping("/documents")
    public ResponseEntity<List<KnowledgeDocument>> getAllDocuments() {
        return ResponseEntity.ok(documentRepository.findAll());
    }
}
