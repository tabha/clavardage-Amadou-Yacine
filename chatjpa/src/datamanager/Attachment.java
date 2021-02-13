package datamanager;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the attachments database table.
 * 
 */
@Entity
@Table(name="attachments")
@NamedQuery(name="Attachment.findAll", query="SELECT a FROM Attachment a")
public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idatt;

	@Temporal(TemporalType.DATE)
	private Date createdat;

	private String fileurl;

	private String thumburl;

	//bi-directional many-to-one association to Message
	@ManyToOne
	@JoinColumn(name="messageid")
	private Message message;

	public Attachment() {
	}

	public String getIdatt() {
		return this.idatt;
	}

	public void setIdatt(String idatt) {
		this.idatt = idatt;
	}

	public Date getCreatedat() {
		return this.createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public String getFileurl() {
		return this.fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public String getThumburl() {
		return this.thumburl;
	}

	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}