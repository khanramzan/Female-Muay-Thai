package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the promoter database table.
 * 
 */
@Entity
@NamedQuery(name="Promoter.findAll", query="SELECT p FROM Promoter p")
public class Promoter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="promoter_id")
	private Long promoterId;

	@Lob
	@Column(name="promoter_comments")
	private String promoterComments;

	@Column(name="promoter_name")
	private String promoterName;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="country")
	private Country countryBean;

	//bi-directional many-to-many association to MediaTable
	@ManyToMany
	@JoinTable(
		name="promoter_has_media_table"
		, joinColumns={
			@JoinColumn(name="promoter")
			}
		, inverseJoinColumns={
			@JoinColumn(name="media_table")
			}
		)
	private List<MediaTable> mediaTables;

	//bi-directional many-to-one association to Promotion
	@OneToMany(mappedBy="promoterBean")
	private List<Promotion> promotions;

	public Promoter() {
	}

	public Long getPromoterId() {
		return this.promoterId;
	}

	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}

	public String getPromoterComments() {
		return this.promoterComments;
	}

	public void setPromoterComments(String promoterComments) {
		this.promoterComments = promoterComments;
	}

	public String getPromoterName() {
		return this.promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
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

	public List<Promotion> getPromotions() {
		return this.promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Promotion addPromotion(Promotion promotion) {
		getPromotions().add(promotion);
		promotion.setPromoterBean(this);

		return promotion;
	}

	public Promotion removePromotion(Promotion promotion) {
		getPromotions().remove(promotion);
		promotion.setPromoterBean(null);

		return promotion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((countryBean == null) ? 0 : countryBean.hashCode());
		result = prime * result
				+ ((mediaTables == null) ? 0 : mediaTables.hashCode());
		result = prime
				* result
				+ ((promoterComments == null) ? 0 : promoterComments.hashCode());
		result = prime * result
				+ ((promoterId == null) ? 0 : promoterId.hashCode());
		result = prime * result
				+ ((promoterName == null) ? 0 : promoterName.hashCode());
		result = prime * result
				+ ((promotions == null) ? 0 : promotions.hashCode());
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
		Promoter other = (Promoter) obj;
		if (countryBean == null) {
			if (other.countryBean != null)
				return false;
		} else if (!countryBean.equals(other.countryBean))
			return false;
		if (mediaTables == null) {
			if (other.mediaTables != null)
				return false;
		} else if (!mediaTables.equals(other.mediaTables))
			return false;
		if (promoterComments == null) {
			if (other.promoterComments != null)
				return false;
		} else if (!promoterComments.equals(other.promoterComments))
			return false;
		if (promoterId == null) {
			if (other.promoterId != null)
				return false;
		} else if (!promoterId.equals(other.promoterId))
			return false;
		if (promoterName == null) {
			if (other.promoterName != null)
				return false;
		} else if (!promoterName.equals(other.promoterName))
			return false;
		if (promotions == null) {
			if (other.promotions != null)
				return false;
		} else if (!promotions.equals(other.promotions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Promoter [promoterId=" + promoterId + ", promoterComments="
				+ promoterComments + ", promoterName=" + promoterName;
	}
	
	

}