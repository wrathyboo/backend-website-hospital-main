package com.lsd.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import com.lsd.lib.exception.*;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Order(value = PriorityOrdered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<?> handleGlobalException(Throwable ex, HttpServletRequest request) {
        // Customize exception
        if (ex instanceof LsdException) {
            ErrorMessage exception = ((LsdException) ex).getErrorMessage();
            LOGGER.error("[EXCEPTION] {} exception {}", ex.getClass().getName(), ex);
            return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Unauthorized
        if (ex instanceof AccessDeniedException) {
            LOGGER.error("[EXCEPTION] {} exception {}", ex.getClass().getName(), ex);
            return new ResponseEntity<>(new WebException(ErrorCode.FORBIDDEN).getErrorMessage(), HttpStatus.FORBIDDEN);
        }
        // Internal server
        LOGGER.error("[EXCEPTION] {} exception {}", ex.getClass().getName(), ex);
        return new ResponseEntity<>(new WebException(ErrorCode.INTERNAL_SERVER).getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}