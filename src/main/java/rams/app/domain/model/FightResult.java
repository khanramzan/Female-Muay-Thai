package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the fight_result database table.
 * 
 */
@Entity
@Table(name="fight_result")
@NamedQuery(name="FightResult.findAll", query="SELECT f FROM FightResult f")
public class FightResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fight_result_id")
	private Long fightResultId;

	@Lob
	@Column(name="fight_result")
	private String fightResult;

	@Lob
	@Column(name="fight_result_comments")
	private String fightResultComments;

	//bi-directional one-to-one association to Fight
	@OneToOne(mappedBy="fightResult")
	private Fight fight;

	//bi-directional many-to-many association to MediaTable
	@ManyToMany(mappedBy="fightResults")
	private List<MediaTable> mediaTables;

	

	public FightResult() {
	}

	public Long getFightResultId() {
		return this.fightResultId;
	}

	public void setFightResultId(Long fightResultId) {
		this.fightResultId = fightResultId;
	}

	public String getFightResult() {
		return this.fightResult;
	}

	public void setFightResult(String fightResult) {
		this.fightResult = fightResult;
	}

	public String getFightResultComments() {
		return this.fightResultComments;
	}

	public void setFightResultComments(String fightResultComments) {
		this.fightResultComments = fightResultComments;
	}

	public Fight getFight1() {
		return this.fight;
	}

	public void setFight1(Fight fight1) {
		this.fight = fight1;
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
		result = prime * result + ((fight == null) ? 0 : fight.hashCode());
		result = prime * result
				+ ((fightResult == null) ? 0 : fightResult.hashCode());
		result = prime
				* result
				+ ((fightResultComments == null) ? 0 : fightResultComments
						.hashCode());
		result = prime * result
				+ ((fightResultId == null) ? 0 : fightResultId.hashCode());
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
		FightResult other = (FightResult) obj;
		if (fight == null) {
			if (other.fight != null)
				return false;
		} else if (!fight.equals(other.fight))
			return false;
		if (fightResult == null) {
			if (other.fightResult != null)
				return false;
		} else if (!fightResult.equals(other.fightResult))
			return false;
		if (fightResultComments == null) {
			if (other.fightResultComments != null)
				return false;
		} else if (!fightResultComments.equals(other.fightResultComments))
			return false;
		if (fightResultId == null) {
			if (other.fightResultId != null)
				return false;
		} else if (!fightResultId.equals(other.fightResultId))
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
		return "FightResult [fightResultId=" + fightResultId + ", fightResult="
				+ fightResult + ", fightResultComments=" + fightResultComments;
	}

	

	
	
	

}