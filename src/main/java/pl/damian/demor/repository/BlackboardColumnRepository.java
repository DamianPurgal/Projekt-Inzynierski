package pl.damian.demor.repository;

import org.springframework.data.repository.CrudRepository;
import pl.damian.demor.model.BlackboardColumn;

public interface BlackboardColumnRepository extends CrudRepository<BlackboardColumn, Long> {
}
