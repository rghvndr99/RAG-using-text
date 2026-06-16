package com.rag.app;

import java.util.List;

public class Retriever {

    public static DocumentChunk retrieve(
            String question,
            List<DocumentChunk> chunks)
            throws Exception {

        float[] queryEmbedding =
                EmbeddingService
                        .getEmbedding(question);

        DocumentChunk bestChunk = null;

        double bestScore = -1;

        for (DocumentChunk chunk : chunks) {

            double score =
                    CosineSimilarity
                            .calculate(
                                    queryEmbedding,
                                    chunk.getEmbedding());

            System.out.println(
                    score
                    + " -> "
                    + chunk.getText());

            if (score > bestScore) {

                bestScore = score;
                bestChunk = chunk;
            }
        }

        System.out.println(
                "\nBest Score = "
                + bestScore);

        return bestChunk;
    }
}