package rams.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

}
