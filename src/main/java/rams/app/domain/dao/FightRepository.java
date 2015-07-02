package rams.app.domain.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.Fight;

public interface FightRepository extends JpaRepository<Fight, Long>{

	public List<Fight> findByFightDateIsAfter(Date date);
	
	public List<Fight> findByFightDateIsBeforeOrderByFightDateDesc(Date date);
}
