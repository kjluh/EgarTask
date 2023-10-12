package egar.egartask.controllers;


import egar.egartask.exception.ExceptionMessage;
import egar.egartask.exception.MyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MyException.class)
    public ResponseEntity<ExceptionMessage> exceptionMessage(){
        ExceptionMessage message = new ExceptionMessage();
        return ResponseEntity.badRequest().body(message);
    }
}
