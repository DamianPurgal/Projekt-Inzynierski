package pl.damian.demor.repository;

import org.springframework.data.repository.CrudRepository;
import pl.damian.demor.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
