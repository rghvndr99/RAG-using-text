package com.rag.app;

import java.util.*;

public class Retriever {

    public static List<SearchResult> retrieveTopK(
            String question,
            List<DocumentChunk> chunks, int k)
            throws Exception {

        float[] queryEmbedding =
                EmbeddingService
                        .getEmbedding(question);

        List<SearchResult> topKResults = new ArrayList<>();

        for (DocumentChunk chunk : chunks) {

            double score =
                    CosineSimilarity.calculate(
                            queryEmbedding,
                            chunk.getEmbedding());

            topKResults.add(
                    new SearchResult(
                            chunk,
                            score));
        }
        topKResults.sort(
                Comparator.comparingDouble(
                        SearchResult::getScore)
                        .reversed());
        return topKResults.stream()
                .limit(k)
                .toList();
    }
}