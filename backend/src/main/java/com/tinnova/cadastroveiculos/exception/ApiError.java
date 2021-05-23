package com.tinnova.cadastroveiculos.exception;

import com.tinnova.cadastroveiculos.services.LocaleMessageSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiError {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private List<ApiSubError> errors;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public void addBindingResult(BindingResult bindingResult, LocaleMessageSource localeMessageSource) {
        bindingResult.getGlobalErrors().forEach(this::addValidationError);
        bindingResult.getFieldErrors().forEach(fieldError -> addValidationError(fieldError, localeMessageSource));
    }

    private void addValidationError(FieldError fieldError, LocaleMessageSource localeMessageSource) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                localeMessageSource.getMessage(fieldError));
    }

    private void addValidationError(String object, String error) {
        addSubError(new ApiSubError(object, error));
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    private void addValidationError(String object, String field, Object rejectedValue, String error) {
        addSubError(new ApiSubError(object, field, rejectedValue, error));
    }

    public void addSubError(ApiSubError subError) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(subError);
    }

    @Getter
    @AllArgsConstructor
    private static class ApiSubError {
        private String object;
        private String input;
        private Object rejectedValue;
        private String error;

        ApiSubError(String object, String error) {
            this.object = object;
            this.error = error;
        }
    }
}
