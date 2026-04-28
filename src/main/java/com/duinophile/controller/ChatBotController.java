package com.duinophile.controller;

import com.duinophile.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ChatBotController {

    @Autowired
    private ChatBotService chatBotService;

    // Renders the HTML template
    @GetMapping("/chatbot")
    public String renderChatbotPage() {
        return "chatbot"; // will resolve to src/main/resources/templates/chatbot.html
    }

    // Handles the AJAX requests from the UI
    @PostMapping("/api/chatbot/ask")
    @ResponseBody
    public ResponseEntity<Map<String, String>> askChatBot(@RequestBody Map<String, String> payload) {
        String message = payload.getOrDefault("message", "");
        String instruction = payload.getOrDefault("instruction", "Fix this code:");
        String code = payload.getOrDefault("code", message); // Use message if code is empty
        
        ChatBotService.ChatResponse response = chatBotService.getFixForCode(instruction, code);
        
        String explanation = response.getExplanation();
        if (explanation != null) {
            // Strip out trailing "Ex" or "Ex Fixed code..." hallucinations
            explanation = explanation.replaceAll("(?i)\\bEx\\b\\s*(Fixed code:?)?.*$", "").trim();
        }
        
        String fixedCode = response.getFixed_code();
        if (fixedCode != null) {
            // Strip out leading "Fixed code:" if the model generated it
            fixedCode = fixedCode.replaceFirst("(?i)^Fixed code:?\\s*", "").trim();
        }
        
        String formattedReply = "💡 " + explanation + "\n\n💻 Corrected Code:\n" + fixedCode;
        
        // Build a Map so it supports both new standalone page AND the old layout.html widget!
        Map<String, String> finalResponse = new java.util.HashMap<>();
        finalResponse.put("fixed_code", fixedCode);
        finalResponse.put("explanation", explanation);
        finalResponse.put("original_code", code);
        finalResponse.put("reply", formattedReply);
        
        return ResponseEntity.ok(finalResponse);
    }
}
