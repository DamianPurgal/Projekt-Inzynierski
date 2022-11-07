package pl.damian.demor.exception.blackboardContributor;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class ContributorArleadyExistsException extends BusinessException {

    public ContributorArleadyExistsException() { super(HttpStatus.BAD_REQUEST, "Contributor arleady added"); }

}
