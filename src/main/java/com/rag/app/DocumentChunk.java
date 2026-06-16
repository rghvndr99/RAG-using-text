package com.rag.app;

public class DocumentChunk {
    private final String text;
    private float[] embedding;

    public DocumentChunk(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }
}
