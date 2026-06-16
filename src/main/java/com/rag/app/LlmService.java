package com.rag.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LlmService {

    private static final ObjectMapper mapper =
            new ObjectMapper();

    public static String ask(String prompt)
            throws Exception {

        String json = """
        {
          "model":"llama3",
          "prompt":"%s",
          "stream":false
        }
        """.formatted(
                prompt
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
        );

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "http://localhost:11434/api/generate"))
                        .header(
                                "Content-Type",
                                "application/json")
                        .POST(
                                HttpRequest.BodyPublishers
                                        .ofString(json))
                        .build();

        HttpClient client =
                HttpClient.newHttpClient();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers
                                .ofString());

        JsonNode root =
                mapper.readTree(
                        response.body());

        return root.get("response")
                .asText();
    }
}
