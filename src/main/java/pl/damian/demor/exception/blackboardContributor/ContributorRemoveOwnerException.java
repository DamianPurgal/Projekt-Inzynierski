package pl.damian.demor.exception.blackboardContributor;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class ContributorRemoveOwnerException extends BusinessException {

    public ContributorRemoveOwnerException() { super(HttpStatus.BAD_REQUEST, "Owner cannot be removed"); }

}