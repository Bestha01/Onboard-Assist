-- ============================================================
-- OnboardAssist Database Schema
-- Run this script in PostgreSQL after creating the database
-- ============================================================

-- Step 1: Enable pgvector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- Step 2: Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Step 3: Create knowledge_documents table
CREATE TABLE IF NOT EXISTS knowledge_documents (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    content TEXT NOT NULL,
    source VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Step 4: Create knowledge_chunks table (with pgvector embedding column)
-- gemini-embedding-001 produces 768-dimensional vectors
CREATE TABLE IF NOT EXISTS knowledge_chunks (
    id BIGSERIAL PRIMARY KEY,
    document_id BIGINT NOT NULL REFERENCES knowledge_documents(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    embedding vector(768),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Step 5: Create chat_history table
CREATE TABLE IF NOT EXISTS chat_history (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    session_id VARCHAR(255),
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Step 6: Create vector similarity index (cosine distance)
-- HNSW index for fast approximate nearest neighbor search
CREATE INDEX IF NOT EXISTS idx_knowledge_chunks_embedding
    ON knowledge_chunks USING hnsw (embedding vector_cosine_ops)
    WITH (m = 16, ef_construction = 64);

-- Step 7: Create regular indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_chat_history_user_id ON chat_history(user_id);
CREATE INDEX IF NOT EXISTS idx_chat_history_session_id ON chat_history(session_id);
CREATE INDEX IF NOT EXISTS idx_knowledge_chunks_document_id ON knowledge_chunks(document_id);
