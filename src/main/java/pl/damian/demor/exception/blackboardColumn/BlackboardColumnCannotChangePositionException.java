package pl.damian.demor.exception.blackboardColumn;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class BlackboardColumnCannotChangePositionException extends BusinessException {

    public BlackboardColumnCannotChangePositionException() { super(HttpStatus.BAD_REQUEST, "Cannot change column position"); }

}