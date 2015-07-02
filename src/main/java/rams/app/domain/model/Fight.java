package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the fight database table.
 * 
 */
@Entity
@NamedQuery(name="Fight.findAll", query="SELECT f FROM Fight f")
public class Fight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fight_id")
	private Long fightId;

	@Lob
	@Column(name="fight_comments")
	private String fightComments;

	@Temporal(TemporalType.DATE)
	@Column(name="fight_date")
	private Date fightDate;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="country")
	private Country countryBean;

	//bi-directional one-to-one association to FightResult
	@OneToOne
	@JoinColumn(name="fight_id", referencedColumnName="fight_id")
	private FightResult fightResult;

	//bi-directional many-to-one association to FightType
	@ManyToOne
	@JoinColumn(name="fight_type")
	private FightType fightTypeBean;

	//bi-directional many-to-one association to Fighter
	@ManyToOne
	@JoinColumn(name="fighter1")
	private Fighter fighter1Bean;

	//bi-directional many-to-one association to Fighter
	@ManyToOne
	@JoinColumn(name="fighter2")
	private Fighter fighter2Bean;

	//bi-directional many-to-one association to Promotion
	@ManyToOne
	@JoinColumn(name="promotion")
	private Promotion promotionBean;

	//bi-directional many-to-one association to Sanctioner
	@ManyToOne
	@JoinColumn(name="sanctioner")
	private Sanctioner sanctionerBean;

	//bi-directional many-to-one association to WeightCategory
	@ManyToOne
	@JoinColumn(name="weight_category")
	private WeightCategory weightCategoryBean;

	//bi-directional many-to-many association to MediaTable
	@ManyToMany(mappedBy="fights")
	private List<MediaTable> mediaTables;

	
	
	public Fight() {
	}

	public Long getFightId() {
		return this.fightId;
	}

	public void setFightId(Long fightId) {
		this.fightId = fightId;
	}

	public String getFightComments() {
		return this.fightComments;
	}

	public void setFightComments(String fightComments) {
		this.fightComments = fightComments;
	}

	public Date getFightDate() {
		return this.fightDate;
	}

	public void setFightDate(Date fightDate) {
		this.fightDate = fightDate;
	}

	public Country getCountryBean() {
		return this.countryBean;
	}

	public void setCountryBean(Country countryBean) {
		this.countryBean = countryBean;
	}

	public FightResult getFightResult() {
		return this.fightResult;
	}

	public void setFightResult(FightResult fightResult1) {
		this.fightResult = fightResult1;
	}

	public FightType getFightTypeBean() {
		return this.fightTypeBean;
	}

	public void setFightTypeBean(FightType fightTypeBean) {
		this.fightTypeBean = fightTypeBean;
	}

	public Fighter getFighter1Bean() {
		return this.fighter1Bean;
	}

	public void setFighter1Bean(Fighter fighter1Bean) {
		this.fighter1Bean = fighter1Bean;
	}

	public Fighter getFighter2Bean() {
		return this.fighter2Bean;
	}

	public void setFighter2Bean(Fighter fighter2Bean) {
		this.fighter2Bean = fighter2Bean;
	}

	public Promotion getPromotionBean() {
		return this.promotionBean;
	}

	public void setPromotionBean(Promotion promotionBean) {
		this.promotionBean = promotionBean;
	}

	public Sanctioner getSanctionerBean() {
		return this.sanctionerBean;
	}

	public void setSanctionerBean(Sanctioner sanctionerBean) {
		this.sanctionerBean = sanctionerBean;
	}

	public WeightCategory getWeightCategoryBean() {
		return this.weightCategoryBean;
	}

	public void setWeightCategoryBean(WeightCategory weightCategoryBean) {
		this.weightCategoryBean = weightCategoryBean;
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
				+ ((fightComments == null) ? 0 : fightComments.hashCode());
		result = prime * result
				+ ((fightDate == null) ? 0 : fightDate.hashCode());
		result = prime * result + ((fightId == null) ? 0 : fightId.hashCode());
		result = prime * result
				+ ((fightResult == null) ? 0 : fightResult.hashCode());
		result = prime * result
				+ ((fightTypeBean == null) ? 0 : fightTypeBean.hashCode());
		result = prime * result
				+ ((fighter1Bean == null) ? 0 : fighter1Bean.hashCode());
		result = prime * result
				+ ((fighter2Bean == null) ? 0 : fighter2Bean.hashCode());
		result = prime * result
				+ ((mediaTables == null) ? 0 : mediaTables.hashCode());
		result = prime * result
				+ ((promotionBean == null) ? 0 : promotionBean.hashCode());
		result = prime * result
				+ ((sanctionerBean == null) ? 0 : sanctionerBean.hashCode());
		result = prime
				* result
				+ ((weightCategoryBean == null) ? 0 : weightCategoryBean
						.hashCode());
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
		Fight other = (Fight) obj;
		if (countryBean == null) {
			if (other.countryBean != null)
				return false;
		} else if (!countryBean.equals(other.countryBean))
			return false;
		if (fightComments == null) {
			if (other.fightComments != null)
				return false;
		} else if (!fightComments.equals(other.fightComments))
			return false;
		if (fightDate == null) {
			if (other.fightDate != null)
				return false;
		} else if (!fightDate.equals(other.fightDate))
			return false;
		if (fightId == null) {
			if (other.fightId != null)
				return false;
		} else if (!fightId.equals(other.fightId))
			return false;
		if (fightResult == null) {
			if (other.fightResult != null)
				return false;
		} else if (!fightResult.equals(other.fightResult))
			return false;
		if (fightTypeBean == null) {
			if (other.fightTypeBean != null)
				return false;
		} else if (!fightTypeBean.equals(other.fightTypeBean))
			return false;
		if (fighter1Bean == null) {
			if (other.fighter1Bean != null)
				return false;
		} else if (!fighter1Bean.equals(other.fighter1Bean))
			return false;
		if (fighter2Bean == null) {
			if (other.fighter2Bean != null)
				return false;
		} else if (!fighter2Bean.equals(other.fighter2Bean))
			return false;
		if (mediaTables == null) {
			if (other.mediaTables != null)
				return false;
		} else if (!mediaTables.equals(other.mediaTables))
			return false;
		if (promotionBean == null) {
			if (other.promotionBean != null)
				return false;
		} else if (!promotionBean.equals(other.promotionBean))
			return false;
		if (sanctionerBean == null) {
			if (other.sanctionerBean != null)
				return false;
		} else if (!sanctionerBean.equals(other.sanctionerBean))
			return false;
		if (weightCategoryBean == null) {
			if (other.weightCategoryBean != null)
				return false;
		} else if (!weightCategoryBean.equals(other.weightCategoryBean))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fight [fightId=" + fightId + ", fightComments=" + fightComments
				+ ", fightDate=" + fightDate ;
	}

	

	

}