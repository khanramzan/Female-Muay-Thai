package rams.app.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rams.app.domain.model.Country;

public interface CountryRepository extends JpaRepository<Country, String> {

	public List<Country> findCountriesByCountryContaining(String term);
}
