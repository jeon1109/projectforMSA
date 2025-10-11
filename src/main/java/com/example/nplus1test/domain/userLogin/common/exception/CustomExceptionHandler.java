package com.example.nplus1test.domain.userLogin.common.exception;

import com.example.nplus1test.domain.userLogin.common.response.BaseResponse;
import com.example.nplus1test.domain.userLogin.common.status.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage != null ? errorMessage : "Not Exception Message");
        });

        BaseResponse<Map<String, String>> response =
                new BaseResponse<>(ResultCode.ERROR.name(), errors, ResultCode.ERROR.getDesc());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    protected ResponseEntity<BaseResponse<Map<String, String>>> invalidInputException(InvalidInputException ex) {
        Map<String, String> errors = Map.of(
                ex.getFieldName(),
                ex.getMessage() != null ? ex.getMessage() : "Not Exception Message"
        );

        BaseResponse<Map<String, String>> response =
                new BaseResponse<>(ResultCode.ERROR.name(), errors, ResultCode.ERROR.getDesc());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse<Map<String, String>>> defaultException(Exception ex) {
        Map<String, String> errors = Map.of(
                "미처리 에러",
                ex.getMessage() != null ? ex.getMessage() : "Not Exception Message"
        );

        BaseResponse<Map<String, String>> response =
                new BaseResponse<>(ResultCode.ERROR.name(), errors, ResultCode.ERROR.getDesc());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
