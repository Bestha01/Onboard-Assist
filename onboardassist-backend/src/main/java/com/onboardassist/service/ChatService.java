package com.onboardassist.service;

import com.onboardassist.dto.ChatRequest;
import com.onboardassist.dto.ChatResponse;
import com.onboardassist.entity.ChatHistory;
import com.onboardassist.entity.User;
import com.onboardassist.repository.ChatHistoryRepository;
import com.onboardassist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RagService ragService;
    private final ChatHistoryRepository chatHistoryRepository;
    private final UserRepository userRepository;

    public ChatResponse processChat(ChatRequest request, String userEmail) {
        // 1. Process through RAG
        String answer = ragService.processQuery(request.question());
        
        // 2. Save to chat history
        User user = userRepository.findByEmail(userEmail).orElse(null);
        String sessionId = request.sessionId() != null ? request.sessionId() : UUID.randomUUID().toString();
        
        if (user != null) {
            ChatHistory history = new ChatHistory();
            history.setUser(user);
            history.setSessionId(sessionId);
            history.setQuestion(request.question());
            history.setAnswer(answer);
            chatHistoryRepository.save(history);
        }
        
        return new ChatResponse(answer, sessionId);
    }
}
