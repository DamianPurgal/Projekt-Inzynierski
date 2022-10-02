package pl.damian.demor.exception.security.authentication;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class AuthenticationFailureException extends BusinessException {

    public AuthenticationFailureException() { super(HttpStatus.UNAUTHORIZED, "Authentication failure"); }

}
