package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="country_id")
	private String countryId;

	private String country;

	//bi-directional many-to-one association to Fight
	@OneToMany(mappedBy="countryBean")
	private List<Fight> fights;

	//bi-directional many-to-one association to Fighter
	@OneToMany(mappedBy="countryBean")
	private List<Fighter> fighters;

	//bi-directional many-to-one association to Promoter
	@OneToMany(mappedBy="countryBean")
	private List<Promoter> promoters;

	public Country() {
	}

	public String getCountryId() {
		return this.countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Fight> getFights() {
		return this.fights;
	}

	public void setFights(List<Fight> fights) {
		this.fights = fights;
	}

	public Fight addFight(Fight fight) {
		getFights().add(fight);
		fight.setCountryBean(this);

		return fight;
	}

	public Fight removeFight(Fight fight) {
		getFights().remove(fight);
		fight.setCountryBean(null);

		return fight;
	}

	public List<Fighter> getFighters() {
		return this.fighters;
	}

	public void setFighters(List<Fighter> fighters) {
		this.fighters = fighters;
	}

	public Fighter addFighter(Fighter fighter) {
		getFighters().add(fighter);
		fighter.setCountryBean(this);

		return fighter;
	}

	public Fighter removeFighter(Fighter fighter) {
		getFighters().remove(fighter);
		fighter.setCountryBean(null);

		return fighter;
	}

	public List<Promoter> getPromoters() {
		return this.promoters;
	}

	public void setPromoters(List<Promoter> promoters) {
		this.promoters = promoters;
	}

	public Promoter addPromoter(Promoter promoter) {
		getPromoters().add(promoter);
		promoter.setCountryBean(this);

		return promoter;
	}

	public Promoter removePromoter(Promoter promoter) {
		getPromoters().remove(promoter);
		promoter.setCountryBean(null);

		return promoter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result
				+ ((fighters == null) ? 0 : fighters.hashCode());
		result = prime * result + ((fights == null) ? 0 : fights.hashCode());
		result = prime * result
				+ ((promoters == null) ? 0 : promoters.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (fighters == null) {
			if (other.fighters != null)
				return false;
		} else if (!fighters.equals(other.fighters))
			return false;
		if (fights == null) {
			if (other.fights != null)
				return false;
		} else if (!fights.equals(other.fights))
			return false;
		if (promoters == null) {
			if (other.promoters != null)
				return false;
		} else if (!promoters.equals(other.promoters))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", country=" + country;
	}
	
	
}