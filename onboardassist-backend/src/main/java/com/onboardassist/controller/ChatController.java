package com.onboardassist.controller;

import com.onboardassist.dto.ChatRequest;
import com.onboardassist.dto.ChatResponse;
import com.onboardassist.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> ask(@Valid @RequestBody ChatRequest request) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(chatService.processChat(request, currentUserEmail));
    }
}
