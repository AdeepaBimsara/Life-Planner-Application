package lk.ijse.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lk.ijse.backend.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> handleGenericException(Exception e){
        e.printStackTrace();
            return new ResponseEntity<>(new APIResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Eroor",
                    e.getMessage()
            ),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<APIResponse<String>> handleNullPointerException(NullPointerException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Null Values are not allowed",
                e.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Object>>
    handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors
        ),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse handleUserNameNotFoundException(UsernameNotFoundException ex) {
        ex.printStackTrace();

        return new APIResponse(
                HttpStatus.NOT_FOUND.value(),
                "Username not found",
                ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse handleBadCredentialsException(BadCredentialsException ex) {
        ex.printStackTrace();
        return new APIResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "username or password is incorrect",
                ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse handleExpiredJwtException(ExpiredJwtException ex) {
        ex.printStackTrace();
        return new APIResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "expired token",
                ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIResponse handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return new APIResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error occurred",
                ex.getMessage());
    }
}
