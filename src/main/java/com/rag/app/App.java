package com.rag.app;

/**
 * Hello world!
 */
public class App {
        public static void main(String[] args)
            throws Exception {

        float[] ceo =
                EmbeddingService.getEmbedding(
                        "CEO");

        float[] chief =
                EmbeddingService.getEmbedding(
                        "Chief Executive Officer");

        double similarity =
                CosineSimilarity.calculate(
                        ceo,
                        chief);

        System.out.println(
                "Similarity = "
                + similarity);

    }
}
