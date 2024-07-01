package com.demo.spring.ai.demospringai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MistralAIController {
    private final MistralAiChatModel mistralAiChatModel;

    @Value("${spring.ai.mistralai.api-key}")
    private String API_KEY;

    @Autowired
    public MistralAIController(MistralAiChatModel mistralAiChatModel) {
        this.mistralAiChatModel = mistralAiChatModel;
    }

    @GetMapping("/mistralai/chat")
    public String chat(String message){
        String response = mistralAiChatModel.call(message);
        return response;
    }

    @GetMapping("/mistralai/movies")
    public ChatResponse movies(@RequestParam(name ="category") String category, @RequestParam(name="year") String year){

        MistralAiApi mistralAiApi = new MistralAiApi(API_KEY);
        MistralAiChatOptions options = MistralAiChatOptions.builder()
                .withModel(MistralAiApi.ChatModel.LARGE.getValue())
                .withMaxTokens(2000).build();

        MistralAiChatModel chatModel = new MistralAiChatModel(mistralAiApi, options);

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("""
                Peux-tu me donner les meilleures films de l'année suivante : {year}
                et de la catégorie suivante : {category}.
                Je souhaite uniquement la réponse en format Json dans l'ordre suivant :
                - year pour l'année du film
                - category pour la catégorie du film
                - director pour la réalisateur du film
                - summary pour un petit résumé du film
                """);
        Prompt prompt = systemPromptTemplate.create(Map.of("year", year, "category", category));
        ChatResponse chatResponse = chatModel.call(prompt);
        return chatResponse;
    }

    @GetMapping("/mistralai/json/movies")
    public Map moviesJson(@RequestParam(name ="category") String category, @RequestParam(name="year") String year) throws JsonProcessingException {

        MistralAiApi mistralAiApi = new MistralAiApi(API_KEY);
        MistralAiChatOptions options = MistralAiChatOptions.builder()
                .withModel(MistralAiApi.ChatModel.LARGE.getValue())
                .withMaxTokens(2000).build();

        MistralAiChatModel chatModel = new MistralAiChatModel(mistralAiApi, options);

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("""
                Peux-tu me donner les meilleures films de l'année suivante : {year}
                et de la catégorie suivante : {category}.
                Je souhaite uniquement la réponse en format Json dans l'ordre suivant :
                - titre pour le titre du film
                - year pour l'année du film
                - category pour la catégorie du film
                - director pour la réalisateur du film
                - summary pour un petit résumé du film
                - rate pour la note du film
                """);
        Prompt prompt = systemPromptTemplate.create(Map.of("year", year, "category", category));
        ChatResponse chatResponse = chatModel.call(prompt);

        String content = chatResponse.getResult().getOutput().getContent();
        return new ObjectMapper().readValue(content,Map.class);
    }

    @GetMapping("/mistralai/json/movie")
    public Map movieJson(@RequestParam(name ="category") String category, @RequestParam(name="year") String year) throws JsonProcessingException {

        MistralAiApi mistralAiApi = new MistralAiApi(API_KEY);
        MistralAiChatOptions options = MistralAiChatOptions.builder()
                .withModel(MistralAiApi.ChatModel.LARGE.getValue())
                .withMaxTokens(2000).build();

        MistralAiChatModel chatModel = new MistralAiChatModel(mistralAiApi, options);

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("""
                Peux-tu me donner le meilleur films de l'année suivante : {year}
                et de la catégorie suivante : {category}.
                Je souhaite uniquement la réponse en format Json dans l'ordre suivant :
                - titre pour le titre du film
                - year pour l'année du film
                - category pour la catégorie du film
                - director pour la réalisateur du film
                - summary pour un petit résumé du film
                - rate pour la note du film
                """);
        Prompt prompt = systemPromptTemplate.create(Map.of("year", year, "category", category));
        ChatResponse chatResponse = chatModel.call(prompt);

        String content = chatResponse.getResult().getOutput().getContent();
        return new ObjectMapper().readValue(content,Map.class);
    }

}
