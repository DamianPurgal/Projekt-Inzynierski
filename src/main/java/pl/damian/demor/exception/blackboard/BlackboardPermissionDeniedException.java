package pl.damian.demor.exception.blackboard;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class BlackboardPermissionDeniedException extends BusinessException {

    public BlackboardPermissionDeniedException() { super(HttpStatus.BAD_REQUEST, "Permission to blackboard denied"); }

    public BlackboardPermissionDeniedException(String message) { super(HttpStatus.BAD_REQUEST, message); }
}
