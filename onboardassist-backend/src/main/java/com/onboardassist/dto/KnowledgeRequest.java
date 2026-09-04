package com.onboardassist.dto;

import jakarta.validation.constraints.NotBlank;

public record KnowledgeRequest(@NotBlank String title, @NotBlank String content, String source) {}
