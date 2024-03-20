package org.odyssey.cms.advice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException constraintViolationException){
        StringBuilder errorMessage = new StringBuilder();

        for (ConstraintViolation<?> violation : constraintViolationException.getConstraintViolations()) {
            String invalidField = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            if (invalidField.equals("vendor")) {
                errorMessage.append("Credit Card Field: ").append(invalidField).append(" - ").append(message).append("\n");
            } else if (invalidField.equals("bankType")) {
                errorMessage.append("Account Field: ").append(invalidField).append(" - ").append(message).append("\n");
            }
        }

        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);//return new ResponseEntity<String>("Accepted values are sbi, icici, hdfc, boa, citi.", HttpStatus.BAD_REQUEST);
    }
}
