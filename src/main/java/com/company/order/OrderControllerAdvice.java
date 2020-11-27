package com.company.order;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderControllerAdvice {

    @ResponseBody
    @ExceptionHandler(OrdereNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderNotFoundHandler(OrdereNotFoundException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String orderUniqueConstraintViolationHandler(ConstraintViolationException ex) {
        return ex.getSQLException().getMessage();
    }
}
