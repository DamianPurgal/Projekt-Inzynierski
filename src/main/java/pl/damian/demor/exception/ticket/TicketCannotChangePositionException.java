package pl.damian.demor.exception.ticket;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class TicketCannotChangePositionException extends BusinessException {

    public TicketCannotChangePositionException() { super(HttpStatus.BAD_REQUEST, "Cannot change ticket position"); }

}