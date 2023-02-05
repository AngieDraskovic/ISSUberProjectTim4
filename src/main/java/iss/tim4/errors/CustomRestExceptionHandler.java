package iss.tim4.errors;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler({UberException.class})
    public ResponseEntity<ErrorDTO> handleSpecial(UberException ex, WebRequest request) {
        ErrorDTO apiError = new ErrorDTO(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorDTO> handleArgs(IllegalArgumentException ex, WebRequest request) {
        ErrorDTO apiError = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorDTO> handleAccess(AccessDeniedException ex, WebRequest request) {
        ErrorDTO apiError = new ErrorDTO(HttpStatus.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleNull(EntityNotFoundException ex, WebRequest request) {
        ErrorDTO apiError = new ErrorDTO(HttpStatus.NOT_FOUND, "User does not exist!");
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorDTO> handleNulll(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorDTO apiError = new ErrorDTO(HttpStatus.BAD_REQUEST, "Bad JSON!");
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> handleAll(Exception ex, WebRequest request) {
        if (Objects.equals(ex.getMessage(), "Bad credentials") ||
                Objects.equals(ex.getMessage(),
                        "Cannot invoke \"iss.tim4.domain.model.User.getEmail()\" because \"user\" is null"))
        {
            return new ResponseEntity<>(new ErrorDTO(
                    HttpStatus.BAD_REQUEST, "Wrong username or password!"),
                    new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        if(Objects.equals(ex.getMessage(), "Access is denied")){
            return new ResponseEntity<>(new ErrorDTO(
                    HttpStatus.FORBIDDEN, "Access is denied!"),
                    new HttpHeaders(), HttpStatus.FORBIDDEN);

        }
        ErrorDTO apiError = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({DataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorDTO> handleConstraintViolationException(DataException e) {
        ErrorDTO apiError = new ErrorDTO(HttpStatus.BAD_REQUEST, "Bad data!");
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorDTO apiError = new ErrorDTO(HttpStatus.BAD_REQUEST, "Bad data!");
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorDTO> handleConstraintViolationException(MethodArgumentNotValidException e) {
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder();

        for (ObjectError error : errorList ) {
            FieldError fe = (FieldError) error;
            sb.append(error.getDefaultMessage());
            sb.append(System.getProperty("line.separator"));
//            sb.append("\n");
//            sb.append(System.lineSeparator());
        }
        System.out.println("ISPIS: " + sb);
        System.out.println("ISPIS: " + sb.toString());
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, sb.toString());

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }




}
