package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the fight_type database table.
 * 
 */
@Entity
@Table(name="fight_type")
@NamedQuery(name="FightType.findAll", query="SELECT f FROM FightType f")
public class FightType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fight_type_id")
	private Long fightTypeId;

	@Column(name="fight_type")
	private String fightType;

	@Lob
	@Column(name="fight_type_comments")
	private String fightTypeComments;

	@Lob
	@Column(name="fight_typec_description")
	private String fightTypecDescription;

	//bi-directional many-to-one association to Fight
	@OneToMany(mappedBy="fightTypeBean")
	private List<Fight> fights;

	public FightType() {
	}

	public Long getFightTypeId() {
		return this.fightTypeId;
	}

	public void setFightTypeId(Long fightTypeId) {
		this.fightTypeId = fightTypeId;
	}

	public String getFightType() {
		return this.fightType;
	}

	public void setFightType(String fightType) {
		this.fightType = fightType;
	}

	public String getFightTypeComments() {
		return this.fightTypeComments;
	}

	public void setFightTypeComments(String fightTypeComments) {
		this.fightTypeComments = fightTypeComments;
	}

	public String getFightTypecDescription() {
		return this.fightTypecDescription;
	}

	public void setFightTypecDescription(String fightTypecDescription) {
		this.fightTypecDescription = fightTypecDescription;
	}

	public List<Fight> getFights() {
		return this.fights;
	}

	public void setFights(List<Fight> fights) {
		this.fights = fights;
	}

	public Fight addFight(Fight fight) {
		getFights().add(fight);
		fight.setFightTypeBean(this);

		return fight;
	}

	public Fight removeFight(Fight fight) {
		getFights().remove(fight);
		fight.setFightTypeBean(null);

		return fight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fightType == null) ? 0 : fightType.hashCode());
		result = prime
				* result
				+ ((fightTypeComments == null) ? 0 : fightTypeComments
						.hashCode());
		result = prime * result
				+ ((fightTypeId == null) ? 0 : fightTypeId.hashCode());
		result = prime
				* result
				+ ((fightTypecDescription == null) ? 0 : fightTypecDescription
						.hashCode());
		result = prime * result + ((fights == null) ? 0 : fights.hashCode());
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
		FightType other = (FightType) obj;
		if (fightType == null) {
			if (other.fightType != null)
				return false;
		} else if (!fightType.equals(other.fightType))
			return false;
		if (fightTypeComments == null) {
			if (other.fightTypeComments != null)
				return false;
		} else if (!fightTypeComments.equals(other.fightTypeComments))
			return false;
		if (fightTypeId == null) {
			if (other.fightTypeId != null)
				return false;
		} else if (!fightTypeId.equals(other.fightTypeId))
			return false;
		if (fightTypecDescription == null) {
			if (other.fightTypecDescription != null)
				return false;
		} else if (!fightTypecDescription.equals(other.fightTypecDescription))
			return false;
		if (fights == null) {
			if (other.fights != null)
				return false;
		} else if (!fights.equals(other.fights))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FightType [fightTypeId=" + fightTypeId + ", fightType="
				+ fightType + ", fightTypeComments=" + fightTypeComments
				+ ", fightTypecDescription=" + fightTypecDescription;
		
	}
	
	
	

}