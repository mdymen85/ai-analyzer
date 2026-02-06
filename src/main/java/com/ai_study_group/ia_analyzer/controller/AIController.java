package com.ai_study_group.ia_analyzer.controller;

import com.ai_study_group.ia_analyzer.service.WeatherService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {

    private final ChatClient chatClient;

    public AIController(ChatClient.Builder builder, WeatherService weatherService) {
        this.chatClient = builder
            .defaultTools(weatherService) // Register all @Tool methods in this bean
            .build();
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String message) {
        return chatClient.prompt(message)
                         .call()
                         .content();
    }
}
