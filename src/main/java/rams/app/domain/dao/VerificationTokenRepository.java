package rams.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.User;
import rams.app.domain.model.VerificationToken;


public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    public VerificationToken findByToken(String token);

    public VerificationToken findByUser(User user);
}
