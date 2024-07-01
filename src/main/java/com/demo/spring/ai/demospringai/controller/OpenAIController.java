package com.demo.spring.ai.demospringai.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIController {
    private final OpenAiChatModel chatModel;

    @Autowired
    public OpenAIController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/openai/chat")
    public String chat(String message){
        String response = chatModel.call(message);
        return response;
    }
}
