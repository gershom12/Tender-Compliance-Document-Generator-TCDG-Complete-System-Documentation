package com.tcdg.tcdgdocumentservice.service;

import com.tcdg.tcdgdocumentservice.client.CompanyClient;
import com.tcdg.tcdgdocumentservice.client.TemplateClient;
import com.tcdg.tcdgdocumentservice.dto.DocumentRequestEvent;
import com.tcdg.tcdgdocumentservice.kafka.DocumentEventProducer;
import com.tcdg.tcdgdocumentservice.model.Document;
import com.tcdg.tcdgdocumentservice.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final CompanyClient companyClient;
    private final TemplateClient templateClient;
    private final PlaceholderMergeService mergeService;
    private final DocumentRepository documentRepository;
    private final DocumentEventProducer eventProducer;

    @Transactional
    public void generateDocument(DocumentRequestEvent event) {

        Document document = Document.builder()
                .tenantId(event.getTenantId())
                .companyId(event.getCompanyId())
                .tenderId(event.getTenderId())
                .status(Document.Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        document = documentRepository.save(document);

        try {

            // 1️⃣ Fetch data
            Map<String, Object> companyData =
                    companyClient.getCompanyData(event.getCompanyId());

            String templateContent =
                    templateClient.getTemplate(event.getTemplateId());

            // 2️⃣ Merge placeholders
            String finalContent =
                    mergeService.merge(templateContent, companyData);

            // 3️⃣ Generate file
            // String fileUrl = generateDocxFile(finalContent);
            byte[] fileBytes = generateDocxFile(finalContent);

            // Upload to MinIO
            String fileUrl = storageService.uploadFile(fileBytes, "document-" + document.getId() + ".docx");

            document.setFileUrl(fileUrl);
            document.setStatus(Document.Status.GENERATED);

        } catch (Exception e) {
            document.setStatus(Document.Status.FAILED);
        }

        documentRepository.save(document);

        // 4️⃣ Publish event
        eventProducer.publishGeneratedEvent(document);
    }

    private byte[] generateDocxFile(String content) throws Exception {
        // Create a new DOCX document
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Add a paragraph and set the text
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(content);

            // Write the document to a byte array
            document.write(out);
            return out.toByteArray();
        }
    }
}