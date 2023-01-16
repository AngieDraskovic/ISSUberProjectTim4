package iss.tim4.validation;

import iss.tim4.errors.ErrorDTO;
import iss.tim4.errors.UberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorDTO> handleConstraintViolationException(MethodArgumentNotValidException e) throws UberException {
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder("Request finished with validation errors: \n");

        for (ObjectError error : errorList ) {
            FieldError fe = (FieldError) error;
            sb.append(error.getDefaultMessage()+ "\n\n");
        }
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, sb.toString());

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
