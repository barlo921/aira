package com.barlo.project_service.exception;

public abstract class AbstractProjectException extends RuntimeException {
    public AbstractProjectException(String message) {
        super(message);
    }
}
