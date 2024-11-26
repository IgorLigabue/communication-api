package br.com.ligabue.communication_api.exceptionhandling;

import br.com.ligabue.communication_api.exceptionhandling.exceptions.MissingParametersException;
import br.com.ligabue.communication_api.exceptionhandling.exceptions.ServiceException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ResponseExceptionMessage> runtimeErrorHandler(RuntimeException exception) {

        ResponseExceptionMessage responseExceptionMessage =
                new ResponseExceptionMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseExceptionMessage);
    }

    @ExceptionHandler(ServiceException.class)
    private ResponseEntity<ResponseExceptionMessage> runtimeErrorHandler(ServiceException exception) {

        ResponseExceptionMessage responseExceptionMessage = new ResponseExceptionMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseExceptionMessage);
    }

    @ExceptionHandler(MissingParametersException.class)
    private ResponseEntity<ResponseExceptionMessage> runtimeErrorHandler(MissingParametersException exception) {

        ResponseExceptionMessage responseExceptionMessage = new ResponseExceptionMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseExceptionMessage);
    }

    @ExceptionHandler(DateTimeParseException.class)
    private ResponseEntity<ResponseExceptionMessage> runtimeErrorHandler(DateTimeParseException exception) {

        ResponseExceptionMessage responseExceptionMessage = new ResponseExceptionMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseExceptionMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ResponseExceptionMessage> runtimeErrorHandler(EntityNotFoundException exception) {

        ResponseExceptionMessage responseExceptionMessage = new ResponseExceptionMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseExceptionMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ResponseExceptionMessage> runtimeErrorHandler(IllegalArgumentException exception) {

        ResponseExceptionMessage responseExceptionMessage = new ResponseExceptionMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseExceptionMessage);
    }
}
