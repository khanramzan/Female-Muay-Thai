package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the weight_category database table.
 * 
 */
@Entity
@Table(name="weight_category")
@NamedQuery(name="WeightCategory.findAll", query="SELECT w FROM WeightCategory w")
public class WeightCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="weight_category_id")
	private Long weightCategoryId;

	@Lob
	@Column(name="weight_category_description")
	private String weightCategoryDescription;

	@Column(name="weight_category_name")
	private String weightCategoryName;

	@Column(name="weight_limits")
	private String weightLimits;

	//bi-directional many-to-one association to Fight
	@OneToMany(mappedBy="weightCategoryBean")
	private List<Fight> fights;

	public WeightCategory() {
	}

	public Long getWeightCategoryId() {
		return this.weightCategoryId;
	}

	public void setWeightCategoryId(Long weightCategoryId) {
		this.weightCategoryId = weightCategoryId;
	}

	public String getWeightCategoryDescription() {
		return this.weightCategoryDescription;
	}

	public void setWeightCategoryDescription(String weightCategoryDescription) {
		this.weightCategoryDescription = weightCategoryDescription;
	}

	public String getWeightCategoryName() {
		return this.weightCategoryName;
	}

	public void setWeightCategoryName(String weightCategoryName) {
		this.weightCategoryName = weightCategoryName;
	}

	public String getWeightLimits() {
		return this.weightLimits;
	}

	public void setWeightLimits(String weightLimits) {
		this.weightLimits = weightLimits;
	}

	public List<Fight> getFights() {
		return this.fights;
	}

	public void setFights(List<Fight> fights) {
		this.fights = fights;
	}

	public Fight addFight(Fight fight) {
		getFights().add(fight);
		fight.setWeightCategoryBean(this);

		return fight;
	}

	public Fight removeFight(Fight fight) {
		getFights().remove(fight);
		fight.setWeightCategoryBean(null);

		return fight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fights == null) ? 0 : fights.hashCode());
		result = prime
				* result
				+ ((weightCategoryDescription == null) ? 0
						: weightCategoryDescription.hashCode());
		result = prime
				* result
				+ ((weightCategoryId == null) ? 0 : weightCategoryId.hashCode());
		result = prime
				* result
				+ ((weightCategoryName == null) ? 0 : weightCategoryName
						.hashCode());
		result = prime * result
				+ ((weightLimits == null) ? 0 : weightLimits.hashCode());
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
		WeightCategory other = (WeightCategory) obj;
		if (fights == null) {
			if (other.fights != null)
				return false;
		} else if (!fights.equals(other.fights))
			return false;
		if (weightCategoryDescription == null) {
			if (other.weightCategoryDescription != null)
				return false;
		} else if (!weightCategoryDescription
				.equals(other.weightCategoryDescription))
			return false;
		if (weightCategoryId == null) {
			if (other.weightCategoryId != null)
				return false;
		} else if (!weightCategoryId.equals(other.weightCategoryId))
			return false;
		if (weightCategoryName == null) {
			if (other.weightCategoryName != null)
				return false;
		} else if (!weightCategoryName.equals(other.weightCategoryName))
			return false;
		if (weightLimits == null) {
			if (other.weightLimits != null)
				return false;
		} else if (!weightLimits.equals(other.weightLimits))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WeightCategory [weightCategoryId=" + weightCategoryId
				+ ", weightCategoryDescription=" + weightCategoryDescription
				+ ", weightCategoryName=" + weightCategoryName
				+ ", weightLimits=" + weightLimits ;
	}
	
	
	
	
	

}