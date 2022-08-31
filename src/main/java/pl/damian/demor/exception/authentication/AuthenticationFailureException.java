package pl.damian.demor.exception.authentication;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class AuthenticationFailureException extends BusinessException {

    public AuthenticationFailureException() { super(HttpStatus.UNAUTHORIZED, "Authentication failure"); }

    public AuthenticationFailureException(String message) { super(HttpStatus.UNAUTHORIZED, message); }
}
