package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the fighter database table.
 * 
 */
@Entity
@NamedQuery(name="Fighter.findAll", query="SELECT f FROM Fighter f")
public class Fighter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fighter_id")
	private Long fighterId;

	@Column(name="fighter_club")
	private String fighterClub;

	@Lob
	@Column(name="fighter_comments")
	private String fighterComments;

	@Column(name="fighter_name")
	private String fighterName;

	//bi-directional many-to-one association to Fight
	@OneToMany(mappedBy="fighter1Bean")
	private List<Fight> fights1;

	//bi-directional many-to-one association to Fight
	@OneToMany(mappedBy="fighter2Bean")
	private List<Fight> fights2;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="country")
	private Country countryBean;

	//bi-directional many-to-many association to MediaTable
	@ManyToMany(mappedBy="fighters")
	private List<MediaTable> mediaTables;

	public Fighter() {
	}

	public Long getFighterId() {
		return this.fighterId;
	}

	public void setFighterId(Long fighterId) {
		this.fighterId = fighterId;
	}

	public String getFighterClub() {
		return this.fighterClub;
	}

	public void setFighterClub(String fighterClub) {
		this.fighterClub = fighterClub;
	}

	public String getFighterComments() {
		return this.fighterComments;
	}

	public void setFighterComments(String fighterComments) {
		this.fighterComments = fighterComments;
	}

	public String getFighterName() {
		return this.fighterName;
	}

	public void setFighterName(String fighterName) {
		this.fighterName = fighterName;
	}

	public List<Fight> getFights1() {
		return this.fights1;
	}

	public void setFights1(List<Fight> fights1) {
		this.fights1 = fights1;
	}

	public Fight addFights1(Fight fights1) {
		getFights1().add(fights1);
		fights1.setFighter1Bean(this);

		return fights1;
	}

	public Fight removeFights1(Fight fights1) {
		getFights1().remove(fights1);
		fights1.setFighter1Bean(null);

		return fights1;
	}

	public List<Fight> getFights2() {
		return this.fights2;
	}

	public void setFights2(List<Fight> fights2) {
		this.fights2 = fights2;
	}

	public Fight addFights2(Fight fights2) {
		getFights2().add(fights2);
		fights2.setFighter2Bean(this);

		return fights2;
	}

	public Fight removeFights2(Fight fights2) {
		getFights2().remove(fights2);
		fights2.setFighter2Bean(null);

		return fights2;
	}

	public Country getCountryBean() {
		return this.countryBean;
	}

	public void setCountryBean(Country countryBean) {
		this.countryBean = countryBean;
	}

	public List<MediaTable> getMediaTables() {
		return this.mediaTables;
	}

	public void setMediaTables(List<MediaTable> mediaTables) {
		this.mediaTables = mediaTables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((countryBean == null) ? 0 : countryBean.hashCode());
		result = prime * result
				+ ((fighterClub == null) ? 0 : fighterClub.hashCode());
		result = prime * result
				+ ((fighterComments == null) ? 0 : fighterComments.hashCode());
		result = prime * result
				+ ((fighterId == null) ? 0 : fighterId.hashCode());
		result = prime * result
				+ ((fighterName == null) ? 0 : fighterName.hashCode());
		result = prime * result + ((fights1 == null) ? 0 : fights1.hashCode());
		result = prime * result + ((fights2 == null) ? 0 : fights2.hashCode());
		result = prime * result
				+ ((mediaTables == null) ? 0 : mediaTables.hashCode());
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
		Fighter other = (Fighter) obj;
		if (countryBean == null) {
			if (other.countryBean != null)
				return false;
		} else if (!countryBean.equals(other.countryBean))
			return false;
		if (fighterClub == null) {
			if (other.fighterClub != null)
				return false;
		} else if (!fighterClub.equals(other.fighterClub))
			return false;
		if (fighterComments == null) {
			if (other.fighterComments != null)
				return false;
		} else if (!fighterComments.equals(other.fighterComments))
			return false;
		if (fighterId == null) {
			if (other.fighterId != null)
				return false;
		} else if (!fighterId.equals(other.fighterId))
			return false;
		if (fighterName == null) {
			if (other.fighterName != null)
				return false;
		} else if (!fighterName.equals(other.fighterName))
			return false;
		if (fights1 == null) {
			if (other.fights1 != null)
				return false;
		} else if (!fights1.equals(other.fights1))
			return false;
		if (fights2 == null) {
			if (other.fights2 != null)
				return false;
		} else if (!fights2.equals(other.fights2))
			return false;
		if (mediaTables == null) {
			if (other.mediaTables != null)
				return false;
		} else if (!mediaTables.equals(other.mediaTables))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fighter [fighterId=" + fighterId + ", fighterClub="
				+ fighterClub + ", fighterComments=" + fighterComments
				+ ", fighterName=" + fighterName ;
	}

	
}