package pl.damian.demor.repository;

import org.springframework.data.repository.CrudRepository;
import pl.damian.demor.model.Blackboard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlackboardRepository extends CrudRepository<Blackboard, Long> {

    List<Blackboard> findAll();

    Optional<Blackboard> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
