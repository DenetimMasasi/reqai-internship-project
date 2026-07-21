package com.company.reqai.exception;

public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(Long documentId) {
        super("Document with ID " + documentId + " was not found.");
    }
}