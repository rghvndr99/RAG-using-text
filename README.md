# RAG Using Text

A simple Java Maven project that demonstrates the core building blocks of a text-based RAG workflow:

- Generate embeddings for text using a local Ollama embedding model.
- Store text and embedding data in a small `DocumentChunk` object.
- Compare embeddings with cosine similarity.

The current demo compares the similarity between `CEO` and `Chief Executive Officer`.

## Tech Stack

- Java 17
- Maven
- JUnit 5
- Jackson Databind
- Ollama embeddings API

## Prerequisites

Install the following before running the project:

- Java 17 or newer
- Maven
- Ollama

Pull the embedding model used by the app:

```bash
ollama pull nomic-embed-text
```

Make sure Ollama is running locally:

```bash
ollama serve
```

The app sends embedding requests to:

```text
http://localhost:11434/api/embeddings
```

## Run The App

From the project root:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.rag.app.App"
```

Expected output will look similar to:

```text
Similarity = 0.8...
```

The exact value may vary depending on the embedding model output.

## Run Tests

```bash
mvn test
```

## Project Structure

```text
.
├── docs/
│   └── company.txt
├── src/
│   ├── main/java/com/rag/app/
│   │   ├── App.java
│   │   ├── CosineSimilarity.java
│   │   ├── DocumentChunk.java
│   │   └── EmbeddingService.java
│   └── test/java/com/rag/app/
│       └── AppTest.java
├── pom.xml
└── README.md
```

## Main Classes

`EmbeddingService` calls the local Ollama API and converts the returned JSON embedding into a `float[]`.

`CosineSimilarity` calculates similarity between two embedding vectors.

`DocumentChunk` represents a text chunk and its embedding.

`App` runs the demo by embedding two related phrases and printing their similarity score.

## Notes

- `target/` is ignored because it contains generated Maven build output.
- `docs/company.txt` contains sample company text that can be used later for chunking and retrieval experiments.

