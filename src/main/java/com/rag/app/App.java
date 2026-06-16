package com.rag.app;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args)
            throws Exception {

        String document = DocumentLoader.load(
                "docs/company.txt");

        List<DocumentChunk> chunks = Chunker.chunk(document);

        System.out.println(
                "Generating embeddings...\n");

        for (DocumentChunk chunk : chunks) {

            float[] embedding = EmbeddingService
                    .getEmbedding(
                            chunk.getText());

            chunk.setEmbedding(
                    embedding);
        }

        String question = "Who is the CEO? And when has joined";

        List<SearchResult> topKResults = Retriever.retrieveTopK(
                question,
                chunks, 3);

        String context = ContextBuilder.build(
                topKResults);

        String prompt = """
                Answer only using the provided context.

                Context:

                %s

                Question:

                %s
                """
                .formatted(
                        context,
                        question);

        String answer = LlmService.ask(
                prompt);

        System.out.println(
                "\nQuestion:");
        System.out.println(
                question);

        System.out.println(
                "\nAnswer:");

        System.out.println(
                answer);
    }
}
