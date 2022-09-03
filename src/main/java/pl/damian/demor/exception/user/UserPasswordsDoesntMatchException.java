package pl.damian.demor.exception.user;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class UserPasswordsDoesntMatchException extends BusinessException {

    public UserPasswordsDoesntMatchException() { super(HttpStatus.BAD_REQUEST, "User passwords doesnt match"); }

    public UserPasswordsDoesntMatchException(String message) { super(HttpStatus.BAD_REQUEST, message); }
}
