package pl.damian.demor.exception.blackboardContributor;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class ContributorNotFoundException extends BusinessException {

    public ContributorNotFoundException() { super(HttpStatus.NOT_FOUND, "Contributor not found"); }

}
