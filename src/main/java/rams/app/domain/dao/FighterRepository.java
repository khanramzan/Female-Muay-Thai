package rams.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.Fighter;

public interface FighterRepository extends JpaRepository<Fighter, Long> {

}
