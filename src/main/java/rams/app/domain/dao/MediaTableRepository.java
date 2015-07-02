package rams.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.MediaTable;

public interface MediaTableRepository extends JpaRepository<MediaTable, Long> {

}
