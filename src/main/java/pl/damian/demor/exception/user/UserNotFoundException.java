package pl.damian.demor.exception.user;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() { super(HttpStatus.NOT_FOUND, "User not found!"); }

    public UserNotFoundException(String message) { super(HttpStatus.NOT_FOUND, message); }


}
