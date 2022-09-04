package pl.damian.demor.repository;

import org.springframework.data.repository.CrudRepository;
import pl.damian.demor.model.Blackboard;

public interface BlackboardRepository extends CrudRepository<Blackboard, Long> {
}
