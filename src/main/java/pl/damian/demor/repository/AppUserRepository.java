package pl.damian.demor.repository;

import org.springframework.data.repository.CrudRepository;
import pl.damian.demor.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
}
