package pl.damian.demor.exception.comment;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class CommentNotFoundException extends BusinessException {

    public CommentNotFoundException() { super(HttpStatus.NOT_FOUND, "Comment not found"); }

}