package com.example.e1941319_mini_project.model;

public class SequenceGenerator {
    private String documentId;
    private Long id;

    public SequenceGenerator(String documentId, Long id) {
        this.documentId = documentId;
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
