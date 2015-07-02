package rams.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.FightResult;

public interface FightResultRepository extends JpaRepository<FightResult, Long> {

}
