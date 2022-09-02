package pl.damian.demor.exception.authorization;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class AuthorizationDeniedException extends BusinessException {

    public AuthorizationDeniedException() { super(HttpStatus.FORBIDDEN, "Authorization denied"); }

    public AuthorizationDeniedException(String message) { super(HttpStatus.FORBIDDEN, message); }
}
