package rams.app.domain.dao;

import org.springframework.data.repository.CrudRepository;

import rams.app.domain.model.User;

/**
 * Allows managing {@link User} instances.
 *
 * @author Rob Winch
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
}