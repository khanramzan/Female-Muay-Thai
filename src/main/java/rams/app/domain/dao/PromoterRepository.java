package rams.app.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.Promoter;

public interface PromoterRepository extends JpaRepository<Promoter, Long>{

	public List<Promoter> findByPromoterNameContaining(String term);
}
