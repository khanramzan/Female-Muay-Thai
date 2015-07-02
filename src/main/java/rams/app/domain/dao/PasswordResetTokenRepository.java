package rams.app.domain.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.PasswordResetToken;
import rams.app.domain.model.User;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    public PasswordResetToken findByToken(String token);

    public PasswordResetToken findByUser(User user);
}

