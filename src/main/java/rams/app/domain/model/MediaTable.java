package rams.app.domain.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the media_table database table.
 * 
 */
@Entity
@Table(name="media_table")
@NamedQuery(name="MediaTable.findAll", query="SELECT m FROM MediaTable m")
public class MediaTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="media_table_id")
	private Long mediaTableId;

	private boolean classic;

	@Lob
	@Column(name="media_comments")
	private String mediaComments;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="media_date")
	private Date mediaDate;

	@Lob
	@Column(name="media_on_server")
	private byte[] mediaOnServer;

	@Column(name="media_url")
	private String mediaUrl;

	//bi-directional many-to-many association to Fight
	@ManyToMany
	@JoinTable(
		name="fight_has_media_table"
		, joinColumns={
			@JoinColumn(name="media_table")
			}
		, inverseJoinColumns={
			@JoinColumn(name="fight")
			}
		)
	private List<Fight> fights;

	//bi-directional many-to-many association to FightResult
	@ManyToMany
	@JoinTable(
		name="fight_result_has_media_table"
		, joinColumns={
			@JoinColumn(name="media_table")
			}
		, inverseJoinColumns={
			@JoinColumn(name="fight_result")
			}
		)
	private List<FightResult> fightResults;

	//bi-directional many-to-many association to Fighter
	@ManyToMany
	@JoinTable(
		name="fighter_has_media_table"
		, joinColumns={
			@JoinColumn(name="media_table")
			}
		, inverseJoinColumns={
			@JoinColumn(name="fighter")
			}
		)
	private List<Fighter> fighters;

	//bi-directional many-to-many association to Promoter
	@ManyToMany//(mappedBy="mediaTables")
	@JoinTable(
			name="promoter_has_media_table"
			, joinColumns={
				@JoinColumn(name="media_table")
				}
			, inverseJoinColumns={
				@JoinColumn(name="promoter")
				}
			)
	
	private List<Promoter> promoters;

	//bi-directional many-to-many association to Promotion
	@ManyToMany//(mappedBy="mediaTables")
	@JoinTable(
			name="promotion_has_media_table"
			, joinColumns={
				@JoinColumn(name="media_table")
				}
			, inverseJoinColumns={
				@JoinColumn(name="promotion")
				}
			)
	
	
	private List<Promotion> promotions;

	public MediaTable() {
	}

	public Long getMediaTableId() {
		return this.mediaTableId;
	}

	public void setMediaTableId(Long mediaTableId) {
		this.mediaTableId = mediaTableId;
	}

	public boolean getClassic() {
		return this.classic;
	}

	public void setClassic(boolean classic) {
		this.classic = classic;
	}

	public String getMediaComments() {
		return this.mediaComments;
	}

	public void setMediaComments(String mediaComments) {
		this.mediaComments = mediaComments;
	}

	public Date getMediaDate() {
		return this.mediaDate;
	}

	public void setMediaDate(Date mediaDate) {
		this.mediaDate = mediaDate;
	}

	public byte[] getMediaOnServer() {
		return this.mediaOnServer;
	}

	public void setMediaOnServer(byte[] mediaOnServer) {
		this.mediaOnServer = mediaOnServer;
	}

	public String getMediaUrl() {
		return this.mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public List<Fight> getFights() {
		return this.fights;
	}

	public void setFights(List<Fight> fights) {
		this.fights = fights;
	}

	public List<FightResult> getFightResults() {
		return this.fightResults;
	}

	public void setFightResults(List<FightResult> fightResults) {
		this.fightResults = fightResults;
	}

	public List<Fighter> getFighters() {
		return this.fighters;
	}

	public void setFighters(List<Fighter> fighters) {
		this.fighters = fighters;
	}

	public List<Promoter> getPromoters() {
		return this.promoters;
	}

	public void setPromoters(List<Promoter> promoters) {
		this.promoters = promoters;
	}

	public List<Promotion> getPromotions() {
		return this.promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (classic ? 1231 : 1237);
		result = prime * result
				+ ((fightResults == null) ? 0 : fightResults.hashCode());
		result = prime * result
				+ ((fighters == null) ? 0 : fighters.hashCode());
		result = prime * result + ((fights == null) ? 0 : fights.hashCode());
		result = prime * result
				+ ((mediaComments == null) ? 0 : mediaComments.hashCode());
		result = prime * result
				+ ((mediaDate == null) ? 0 : mediaDate.hashCode());
		result = prime * result + Arrays.hashCode(mediaOnServer);
		result = prime * result
				+ ((mediaTableId == null) ? 0 : mediaTableId.hashCode());
		result = prime * result
				+ ((mediaUrl == null) ? 0 : mediaUrl.hashCode());
		result = prime * result
				+ ((promoters == null) ? 0 : promoters.hashCode());
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
		MediaTable other = (MediaTable) obj;
		if (classic != other.classic)
			return false;
		if (fightResults == null) {
			if (other.fightResults != null)
				return false;
		} else if (!fightResults.equals(other.fightResults))
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
		if (mediaComments == null) {
			if (other.mediaComments != null)
				return false;
		} else if (!mediaComments.equals(other.mediaComments))
			return false;
		if (mediaDate == null) {
			if (other.mediaDate != null)
				return false;
		} else if (!mediaDate.equals(other.mediaDate))
			return false;
		if (!Arrays.equals(mediaOnServer, other.mediaOnServer))
			return false;
		if (mediaTableId == null) {
			if (other.mediaTableId != null)
				return false;
		} else if (!mediaTableId.equals(other.mediaTableId))
			return false;
		if (mediaUrl == null) {
			if (other.mediaUrl != null)
				return false;
		} else if (!mediaUrl.equals(other.mediaUrl))
			return false;
		if (promoters == null) {
			if (other.promoters != null)
				return false;
		} else if (!promoters.equals(other.promoters))
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
		return "MediaTable [mediaTableId=" + mediaTableId + ", classic="
				+ classic + ", mediaComments=" + mediaComments + ", mediaDate="
				+ mediaDate + " mediaUrl=" + mediaUrl;
	}
	
	
	

}