package iss.tim4.errors;

import org.springframework.http.HttpStatus;

public class UberException extends Exception {
    private final HttpStatus status;

    public UberException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
