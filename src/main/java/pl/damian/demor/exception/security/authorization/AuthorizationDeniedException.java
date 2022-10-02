package pl.damian.demor.exception.security.authorization;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class AuthorizationDeniedException extends BusinessException {

    public AuthorizationDeniedException() { super(HttpStatus.FORBIDDEN, "Authorization denied"); }

}
