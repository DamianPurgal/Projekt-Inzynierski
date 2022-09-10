package pl.damian.demor.exception.blackboardContributor;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class BlackboardContributorArleadyExistsException extends BusinessException {

    public BlackboardContributorArleadyExistsException() { super(HttpStatus.BAD_REQUEST, "Contributor arleady added"); }

    public BlackboardContributorArleadyExistsException(String message) { super(HttpStatus.BAD_REQUEST, message); }
}
