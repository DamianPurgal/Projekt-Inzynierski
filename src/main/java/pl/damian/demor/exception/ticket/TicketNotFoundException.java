package pl.damian.demor.exception.ticket;

import org.springframework.http.HttpStatus;
import pl.damian.demor.exception.BusinessException;

public class TicketNotFoundException extends BusinessException {

    public TicketNotFoundException() { super(HttpStatus.NOT_FOUND, "Ticket not found"); }
}
