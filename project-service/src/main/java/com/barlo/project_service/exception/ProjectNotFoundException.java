package com.barlo.project_service.exception;

public class ProjectNotFoundException extends AbstractProjectException {
    public ProjectNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
