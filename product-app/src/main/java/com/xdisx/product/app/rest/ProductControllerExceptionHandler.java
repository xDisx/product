package com.xdisx.product.app.rest;

import com.xdisx.product.api.exception.ProductCreateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = ProductController.class)
public class ProductControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProductCreateException.class, ProductCreateException.class})
    public ResponseEntity<Object> handleProductCreateException(WebRequest r, ProductCreateException e) {
        return setResponse(r, e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> setResponse(WebRequest r, ProductCreateException e, HttpStatus status) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(status, e.getMessage());

        return new ResponseEntity<>(body, status);
    }
}
