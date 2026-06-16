package com.rag.app;
import java.util.List;
/**
 * Hello world!
 */
public class App {
    public static void main(String[] args)
            throws Exception {

        String document =
                DocumentLoader.load(
                        "docs/company.txt");

        List<DocumentChunk> chunks =
                Chunker.chunk(document);

        System.out.println(
                "Generating embeddings...\n");

        for (DocumentChunk chunk : chunks) {

            float[] embedding =
                    EmbeddingService
                            .getEmbedding(
                                    chunk.getText());

            chunk.setEmbedding(
                    embedding);
        }

        String question =
                "Who is the CEO?";

        DocumentChunk answer =
                Retriever.retrieve(
                        question,
                        chunks);

        System.out.println(
                "\nQuestion:");
        System.out.println(
                question);

        System.out.println(
                "\nRetrieved Chunk:");
        System.out.println(
                answer.getText());
    }
}