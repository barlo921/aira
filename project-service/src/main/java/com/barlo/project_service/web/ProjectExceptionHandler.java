package com.barlo.project_service.web;

import com.barlo.project_service.exception.AbstractProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ProjectExceptionHandler {
    @ExceptionHandler(AbstractProjectException.class)
    public ResponseEntity<Object> handleProjectException(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), status);
    }
}
