package rams.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.WeightCategory;

public interface WeightCategoryRepository extends JpaRepository<WeightCategory, Long> {

}
