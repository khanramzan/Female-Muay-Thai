package rams.app.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import rams.app.domain.model.FightType;

public interface FightTypeRepository extends JpaRepository<FightType, Long> {
	
	public List<FightType> findFightTypeByFightTypeContaining(String term);

}
