package com.rag.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmbeddingService {

    private static final ObjectMapper mapper =
            new ObjectMapper();

    public static float[] getEmbedding(String text)
            throws Exception {

        ObjectNode payload =
                mapper.createObjectNode();

        payload.put(
                "model",
                "nomic-embed-text");

        payload.put(
                "prompt",
                text);

        String json =
                mapper.writeValueAsString(
                        payload);

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

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {
            throw new IllegalStateException(
                    "Ollama embedding request failed with HTTP "
                            + response.statusCode()
                            + ": "
                            + response.body());
        }

        JsonNode root =
                mapper.readTree(response.body());

        JsonNode embeddingNode =
                root.get("embedding");

        if (embeddingNode == null
                && root.has("embeddings")
                && root.get("embeddings").isArray()
                && !root.get("embeddings").isEmpty()) {
            embeddingNode =
                    root.get("embeddings").get(0);
        }

        if (embeddingNode == null
                || !embeddingNode.isArray()) {
            String error =
                    root.has("error")
                            ? root.get("error").asText()
                            : response.body();

            throw new IllegalStateException(
                    "Ollama response did not contain an embedding. "
                            + "Check that Ollama is running and the "
                            + "'nomic-embed-text' model is installed. "
                            + "Response: "
                            + error);
        }

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
