package br.com.fiap.locatech.locatech.controllers.handlers;

import br.com.fiap.locatech.locatech.dtos.ResourceConflictDTO;
import br.com.fiap.locatech.locatech.dtos.ResourceNotFoundDto;
import br.com.fiap.locatech.locatech.dtos.ValidationErrorDTO;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceConflictException;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDto> handlerResourceNotFoundException(ResourceNotFoundException e) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status.value()).body(new ResourceNotFoundDto(e.getMessage(), status.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<String>();
        for (var error: e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return ResponseEntity.status(status.value()).body(new ValidationErrorDTO(errors, status.value()));
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ResourceConflictDTO> handlerResourceConflictException(ResourceConflictException e) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status.value()).body(new ResourceConflictDTO(e.getMessage(), status.value()));
    }
}
