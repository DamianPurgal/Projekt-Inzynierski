package pl.damian.demor.exception.user;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class UserEmailIsNotAvailableException extends BusinessException {

    public UserEmailIsNotAvailableException() { super(HttpStatus.BAD_REQUEST, "Email arleady exists!"); }

    public UserEmailIsNotAvailableException(String message) { super(HttpStatus.BAD_REQUEST, message); }
}
