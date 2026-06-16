package com.rag.app;
import java.util.ArrayList;
import java.util.List;

public class Chunker {

    public static List<DocumentChunk> chunk(
            String text) {

        List<DocumentChunk> chunks =
                new ArrayList<>();

        String[] parts =
                text.split("\\R\\s*\\R");

        for (String part : parts) {
            String trimmed =
                    part.trim();

            if (trimmed.isEmpty()) {
                continue;
            }

            chunks.add(
                    new DocumentChunk(
                            trimmed));
        }

        return chunks;
    }
}
