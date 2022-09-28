package pl.damian.demor.exception.blackboardColumn;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class BlackboardColumnNotFoundException extends BusinessException {

    public BlackboardColumnNotFoundException() { super(HttpStatus.NOT_FOUND, "Column not found"); }

}
