package com.onboardassist.service;

import com.onboardassist.entity.KnowledgeChunk;
import com.onboardassist.entity.KnowledgeDocument;
import com.onboardassist.repository.KnowledgeChunkRepository;
import com.onboardassist.repository.KnowledgeDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeService implements CommandLineRunner {
    private final KnowledgeDocumentRepository documentRepository;
    private final KnowledgeChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;
    
    private static final int CHUNK_SIZE = 500; // characters per chunk
    private static final int CHUNK_OVERLAP = 50;

    @Override
    public void run(String... args) {
        seedDefaultKnowledgeIfEmpty();
    }

    public void seedDefaultKnowledgeIfEmpty() {

        if (documentRepository.count() > 0 && chunkRepository.count() > 0) {
            log.info(
                    "Knowledge base already contains {} documents. Skipping seeding.",
                    documentRepository.count()
            );
            return;
        }

        log.info("Knowledge base is empty. Loading PDF documents...");

        try {

            loadPdfDocument(
                    "Induction Program",
                    "InductionGuide.pdf",
                    "HR Onboarding Policy"
            );

            loadPdfDocument(
                    "Assessment Policy",
                    "AssessmentPolicy.pdf",
                    "Assessment Policy Document"
            );

            loadPdfDocument(
                    "Leave Policy",
                    "LeavePolicy.pdf",
                    "HR Leave Policy"
            );

            loadPdfDocument(
                    "Training Guide",
                    "TrainingGuide.pdf",
                    "Learning Portal Guide"
            );
            loadPdfDocument(
                    "IT Support Guide",
                    "ITSupportGuide.pdf",
                    "IT Support Guide"
            );

            log.info("Knowledge base loaded successfully from PDF files.");

        } catch (Exception e) {

            log.error(
                    "Failed to load PDF documents into knowledge base",
                    e
            );
        }
    }
    private void loadPdfDocument(
            String title,
            String pdfFileName,
            String source) {

        String content = extractTextFromPdf(pdfFileName);

        addDocument(
                title,
                content,
                source
        );
    }
    private String extractTextFromPdf(String pdfFileName) {

        try {

            ClassPathResource resource =
                    new ClassPathResource(
                            "knowledge-base/" + pdfFileName
                    );

            try (InputStream inputStream =
                         resource.getInputStream();
                 PDDocument document =
                         PDDocument.load(inputStream)) {

                PDFTextStripper stripper =
                        new PDFTextStripper();

                return stripper.getText(document);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to read PDF: " + pdfFileName,
                    e
            );
        }
    }


    public KnowledgeDocument addDocument(String title, String content, String source) {
        // 1. Save document
        KnowledgeDocument doc = new KnowledgeDocument();
        doc.setTitle(title);
        doc.setContent(content);
        doc.setSource(source);
        doc = documentRepository.save(doc);
        
        // 2. Split into chunks
        List<String> chunks = splitIntoChunks(content);
        
        // 3. Generate embeddings and save each chunk
        for (String chunkContent : chunks) {
            String embedding = embeddingService.generateEmbeddingString(chunkContent);
            KnowledgeChunk chunk = new KnowledgeChunk();
            chunk.setDocument(doc);
            chunk.setContent(chunkContent);
            chunk.setEmbedding(embedding);
            chunkRepository.save(chunk);
        }
        
        return doc;
    }
    
    private List<String> splitIntoChunks(String text) {
        // Split text into overlapping chunks of CHUNK_SIZE characters
        List<String> chunks = new ArrayList<>();
        int length = text.length();
        int start = 0;
        while (start < length) {
            int end = Math.min(start + CHUNK_SIZE, length);
            // Try to split at a sentence boundary
            if (end < length) {
                int lastPeriod = text.lastIndexOf('.', end);
                if (lastPeriod > start + CHUNK_SIZE / 2) end = lastPeriod + 1;
            }
            chunks.add(text.substring(start, end).trim());
            start = Math.max(start + CHUNK_SIZE - CHUNK_OVERLAP, end);
        }
        return chunks;
    }
}
