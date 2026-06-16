package com.rag.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmbeddingService {

    private static final ObjectMapper mapper =
            new ObjectMapper();

    public static float[] getEmbedding(String text)
            throws Exception {

        String json = """
        {
          "model":"nomic-embed-text",
          "prompt":"%s"
        }
        """.formatted(
                text.replace("\"", "\\\"")
        );

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                            URI.create(
                              "http://localhost:11434/api/embeddings"
                            )
                        )
                        .header(
                            "Content-Type",
                            "application/json"
                        )
                        .POST(
                            HttpRequest.BodyPublishers
                                    .ofString(json)
                        )
                        .build();

        HttpClient client =
                HttpClient.newHttpClient();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        JsonNode root =
                mapper.readTree(response.body());

        JsonNode embeddingNode =
                root.get("embedding");

        float[] embedding =
                new float[embeddingNode.size()];

        for (int i = 0; i < embedding.length; i++) {
            embedding[i] =
                    (float) embeddingNode.get(i)
                            .asDouble();
        }

        return embedding;
    }
}
