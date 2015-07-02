package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the promotion database table.
 * 
 */
@Entity
@NamedQuery(name="Promotion.findAll", query="SELECT p FROM Promotion p")
public class Promotion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="promotion_id")
	private Long promotionId;

	@Lob
	@Column(name="promotion_comments")
	private String promotionComments;

	@Column(name="promotion_name")
	private String promotionName;

	//bi-directional many-to-one association to Fight
	@OneToMany(mappedBy="promotionBean")
	private List<Fight> fights;

	//bi-directional many-to-many association to MediaTable
	@ManyToMany
	@JoinTable(
		name="promotion_has_media_table"
		, joinColumns={
			@JoinColumn(name="promotion")
			}
		, inverseJoinColumns={
			@JoinColumn(name="media_table")
			}
		)
	private List<MediaTable> mediaTables;

	//bi-directional many-to-one association to Promoter
	@ManyToOne
	@JoinColumn(name="promoter")
	private Promoter promoterBean;

	public Promotion() {
	}

	public Long getPromotionId() {
		return this.promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionComments() {
		return this.promotionComments;
	}

	public void setPromotionComments(String promotionComments) {
		this.promotionComments = promotionComments;
	}

	public String getPromotionName() {
		return this.promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public List<Fight> getFights() {
		return this.fights;
	}

	public void setFights(List<Fight> fights) {
		this.fights = fights;
	}

	public Fight addFight(Fight fight) {
		getFights().add(fight);
		fight.setPromotionBean(this);

		return fight;
	}

	public Fight removeFight(Fight fight) {
		getFights().remove(fight);
		fight.setPromotionBean(null);

		return fight;
	}

	public List<MediaTable> getMediaTables() {
		return this.mediaTables;
	}

	public void setMediaTables(List<MediaTable> mediaTables) {
		this.mediaTables = mediaTables;
	}

	public Promoter getPromoterBean() {
		return this.promoterBean;
	}

	public void setPromoterBean(Promoter promoterBean) {
		this.promoterBean = promoterBean;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fights == null) ? 0 : fights.hashCode());
		result = prime * result
				+ ((mediaTables == null) ? 0 : mediaTables.hashCode());
		result = prime * result
				+ ((promoterBean == null) ? 0 : promoterBean.hashCode());
		result = prime
				* result
				+ ((promotionComments == null) ? 0 : promotionComments
						.hashCode());
		result = prime * result
				+ ((promotionId == null) ? 0 : promotionId.hashCode());
		result = prime * result
				+ ((promotionName == null) ? 0 : promotionName.hashCode());
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
		Promotion other = (Promotion) obj;
		if (fights == null) {
			if (other.fights != null)
				return false;
		} else if (!fights.equals(other.fights))
			return false;
		if (mediaTables == null) {
			if (other.mediaTables != null)
				return false;
		} else if (!mediaTables.equals(other.mediaTables))
			return false;
		if (promoterBean == null) {
			if (other.promoterBean != null)
				return false;
		} else if (!promoterBean.equals(other.promoterBean))
			return false;
		if (promotionComments == null) {
			if (other.promotionComments != null)
				return false;
		} else if (!promotionComments.equals(other.promotionComments))
			return false;
		if (promotionId == null) {
			if (other.promotionId != null)
				return false;
		} else if (!promotionId.equals(other.promotionId))
			return false;
		if (promotionName == null) {
			if (other.promotionName != null)
				return false;
		} else if (!promotionName.equals(other.promotionName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Promotion [promotionId=" + promotionId + ", promotionComments="
				+ promotionComments + ", promotionName=" + promotionName;
	}
	
	
	
	

}