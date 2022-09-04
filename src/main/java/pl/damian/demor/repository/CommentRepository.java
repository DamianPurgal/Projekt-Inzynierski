package pl.damian.demor.repository;

import org.springframework.data.repository.CrudRepository;
import pl.damian.demor.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
