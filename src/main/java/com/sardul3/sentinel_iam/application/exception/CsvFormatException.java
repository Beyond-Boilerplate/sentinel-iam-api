package com.sardul3.sentinel_iam.application.exception;

public class CsvFormatException extends RuntimeException {
    public CsvFormatException(String message) {
        super(message);
    }
}