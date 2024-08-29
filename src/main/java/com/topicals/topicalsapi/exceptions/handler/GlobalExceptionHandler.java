package com.topicals.topicalsapi.exceptions.handler;

import com.topicals.topicalsapi.exceptions.AlreadyExistsException;
import com.topicals.topicalsapi.exceptions.BadRequestException;
import com.topicals.topicalsapi.exceptions.NotFoundException;
import com.topicals.topicalsapi.exceptions.helper.ExceptionDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private String LocalDateTimeNow()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    private List<String> extractValidationErrors(BindException bindException) {
        return bindException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());

        problemDetails.setProperty("timestamp", LocalDateTimeNow());
        return problemDetails;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ProblemDetail handleAlreadyExistsException(AlreadyExistsException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(HttpStatus.CONFLICT, ex.getLocalizedMessage());

        problemDetails.setProperty("timestamp", LocalDateTimeNow());
        return problemDetails;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException ex) {
        ProblemDetail problemDetails = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

        problemDetails.setProperty("timestamp", LocalDateTimeNow());
        return problemDetails;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        List<String> errorList = extractValidationErrors(ex);
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                errorList,
                LocalDateTimeNow()
        );

        return handleExceptionInternal(ex, exceptionDetails, headers, exceptionDetails.getTitle(), request);
    }

}
