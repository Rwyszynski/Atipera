package com.zadanie2.validation;

import com.zadanie2.exception.NotExistingUserError;
import com.zadanie2.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationErrorHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus
    public NotExistingUserError handleValidationException(UserNotFoundException userError) {
        String message = userError.getMessage();
        return new NotExistingUserError(HttpStatus.BAD_REQUEST.value(),message);
    }
}

