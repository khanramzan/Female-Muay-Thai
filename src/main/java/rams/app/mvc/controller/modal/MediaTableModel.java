package rams.app.mvc.controller.modal;

import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class MediaTableModel {

	private Long mediaTableId;

	private boolean classic;

	@Lob
	private String mediaComments;

	@Temporal(TemporalType.DATE)
	private Date mediaDate;

	@Lob
	private byte[] mediaOnServer;
	
	private String strImg;

	public Long getMediaTableId() {
		return mediaTableId;
	}

	public void setMediaTableId(Long mediaTableId) {
		this.mediaTableId = mediaTableId;
	}

	public boolean isClassic() {
		return classic;
	}

	public void setClassic(boolean classic) {
		this.classic = classic;
	}

	public String getMediaComments() {
		return mediaComments;
	}

	public void setMediaComments(String mediaComments) {
		this.mediaComments = mediaComments;
	}

	public Date getMediaDate() {
		return mediaDate;
	}

	public void setMediaDate(Date mediaDate) {
		this.mediaDate = mediaDate;
	}

	public byte[] getMediaOnServer() {
		return mediaOnServer;
	}

	public void setMediaOnServer(byte[] mediaOnServer) {
		this.mediaOnServer = mediaOnServer;
	}

	public String getStrImg() {
		return strImg;
	}

	public void setStrImg(String strImg) {
		this.strImg = strImg;
	}

	
	
	
}
