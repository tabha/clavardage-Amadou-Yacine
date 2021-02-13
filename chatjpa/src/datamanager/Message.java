package datamanager;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the messages database table.
 * 
 */
@Entity 
@Table(name="messages")
@NamedQueries({
	@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m"),
	@NamedQuery(name="Message.findConvMessage", query="SELECT m FROM Message m WHERE m.conversation=:conv")

	
})
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idmes;

	@Temporal(TemporalType.DATE)
	private Date createdat;

	private String messagecontent;

	private String messagetype;

	//bi-directional many-to-one association to Attachment
	@OneToMany(mappedBy="message")
	private List<Attachment> attachments;

	//bi-directional many-to-one association to Conversation
	@ManyToOne
	@JoinColumn(name="conversationid")
	private Conversation conversation;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="senderid")
	private User user;

	public Message() {
	}

	public String getIdmes() {
		return this.idmes;
	}

	public void setIdmes(String idmes) {
		this.idmes = idmes;
	}

	public Date getCreatedat() {
		return this.createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public String getMessagecontent() {
		return this.messagecontent;
	}

	public void setMessagecontent(String messagecontent) {
		this.messagecontent = messagecontent;
	}

	public String getMessagetype() {
		return this.messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	public List<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Attachment addAttachment(Attachment attachment) {
		getAttachments().add(attachment);
		attachment.setMessage(this);

		return attachment;
	}

	public Attachment removeAttachment(Attachment attachment) {
		getAttachments().remove(attachment);
		attachment.setMessage(null);

		return attachment;
	}

	public Conversation getConversation() {
		return this.conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}