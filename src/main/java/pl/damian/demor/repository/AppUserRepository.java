package pl.damian.demor.repository;

import org.springframework.data.repository.CrudRepository;
import pl.damian.demor.model.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
}
