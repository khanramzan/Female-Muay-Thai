package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the sanctioner database table.
 * 
 */
@Entity
@NamedQuery(name="Sanctioner.findAll", query="SELECT s FROM Sanctioner s")
public class Sanctioner implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sanctioner_id")
	private Long sanctionerId;

	@Lob
	@Column(name="sanctioner_comments")
	private String sanctionerComments;

	@Column(name="sanctioner_name")
	private String sanctionerName;

	//bi-directional many-to-one association to Fight
	@OneToMany(mappedBy="sanctionerBean")
	private List<Fight> fights;

	public Sanctioner() {
	}

	public Long getSanctionerId() {
		return this.sanctionerId;
	}

	public void setSanctionerId(Long sanctionerId) {
		this.sanctionerId = sanctionerId;
	}

	public String getSanctionerComments() {
		return this.sanctionerComments;
	}

	public void setSanctionerComments(String sanctionerComments) {
		this.sanctionerComments = sanctionerComments;
	}

	public String getSanctionerName() {
		return this.sanctionerName;
	}

	public void setSanctionerName(String sanctionerName) {
		this.sanctionerName = sanctionerName;
	}

	public List<Fight> getFights() {
		return this.fights;
	}

	public void setFights(List<Fight> fights) {
		this.fights = fights;
	}

	public Fight addFight(Fight fight) {
		getFights().add(fight);
		fight.setSanctionerBean(this);

		return fight;
	}

	public Fight removeFight(Fight fight) {
		getFights().remove(fight);
		fight.setSanctionerBean(null);

		return fight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fights == null) ? 0 : fights.hashCode());
		result = prime
				* result
				+ ((sanctionerComments == null) ? 0 : sanctionerComments
						.hashCode());
		result = prime * result
				+ ((sanctionerId == null) ? 0 : sanctionerId.hashCode());
		result = prime * result
				+ ((sanctionerName == null) ? 0 : sanctionerName.hashCode());
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
		Sanctioner other = (Sanctioner) obj;
		if (fights == null) {
			if (other.fights != null)
				return false;
		} else if (!fights.equals(other.fights))
			return false;
		if (sanctionerComments == null) {
			if (other.sanctionerComments != null)
				return false;
		} else if (!sanctionerComments.equals(other.sanctionerComments))
			return false;
		if (sanctionerId == null) {
			if (other.sanctionerId != null)
				return false;
		} else if (!sanctionerId.equals(other.sanctionerId))
			return false;
		if (sanctionerName == null) {
			if (other.sanctionerName != null)
				return false;
		} else if (!sanctionerName.equals(other.sanctionerName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sanctioner [sanctionerId=" + sanctionerId
				+ ", sanctionerComments=" + sanctionerComments
				+ ", sanctionerName=" + sanctionerName;
	}
	
	
	

}