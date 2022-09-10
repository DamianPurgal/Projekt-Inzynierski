package pl.damian.demor.exception.blackboard;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class BlackboardNotFoundException extends BusinessException {

    public BlackboardNotFoundException() { super(HttpStatus.NOT_FOUND, "Blackboard not found"); }

    public BlackboardNotFoundException(String message) { super(HttpStatus.NOT_FOUND, message); }
}
