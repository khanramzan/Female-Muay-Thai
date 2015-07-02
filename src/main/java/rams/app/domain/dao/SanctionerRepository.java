package rams.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.Sanctioner;

public interface SanctionerRepository extends JpaRepository<Sanctioner, Long> {

}
