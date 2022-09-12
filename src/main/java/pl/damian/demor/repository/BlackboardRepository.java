package pl.damian.demor.repository;

import org.springframework.data.repository.CrudRepository;
import pl.damian.demor.model.Blackboard;

import java.util.List;

public interface BlackboardRepository extends CrudRepository<Blackboard, Long> {

    List<Blackboard> findAll();
}
