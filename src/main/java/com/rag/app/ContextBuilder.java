package com.rag.app;

import java.util.List;

public class ContextBuilder {

    public static String build(
            List<SearchResult> results) {

        StringBuilder sb =
                new StringBuilder();

        for (SearchResult result : results) {

            sb.append(
                    result.getChunk()
                            .getText());

            sb.append("\n\n");
        }

        return sb.toString();
    }
}