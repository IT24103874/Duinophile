package com.duinophile.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatBotService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String PYTHON_API_URL = "http://localhost:8000/api/chat";

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatRequest {
        private String instruction;
        private String code;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatResponse {
        private String fixed_code;
        private String explanation;
    }

    public ChatResponse getFixForCode(String instruction, String code) {
        try {
            ChatRequest req = new ChatRequest(instruction, code);
            // Send POST request to Python FastAPI server
            return restTemplate.postForObject(PYTHON_API_URL, req, ChatResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ChatResponse("", "Error connecting to the NLP backend! Ensure your Python ML FastAPI server is currently running on localhost:8000.");
        }
    }
}
