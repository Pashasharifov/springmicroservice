package az.company.ms_product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(InsufficientQuantityException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(InsufficientQuantityException exception){
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(MethodArgumentNotValidException exception){
        return ErrorResponse.builder()
                .message(exception.getBindingResult().getFieldError().getDefaultMessage())
                .build();
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception exception){
        return ErrorResponse.builder()
                .message("Unexpected error occured. Please try again later.")
                .build();
    }
}
