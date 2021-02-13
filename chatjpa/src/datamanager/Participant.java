package datamanager;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the participants database table.
 * 
 */
@Entity
@Table(name="participants")
@NamedQueries({
	@NamedQuery(name="Participant.findAll", query="SELECT p FROM Participant p"),
	@NamedQuery(name="Participant.findConvParticipants", query="SELECT p FROM Participant p WHERE p.conversation=:conv"),
	@NamedQuery(name="Participant.findUserParticipation", query="SELECT p FROM Participant p WHERE p.user=:user")
	
})
public class Participant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idpart;

	@Temporal(TemporalType.DATE)
	private Date createdat;

	//bi-directional many-to-one association to Conversation
	@ManyToOne
	@JoinColumn(name="conversationid")
	private Conversation conversation;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	public Participant() {
	}

	public String getIdpart() {
		return this.idpart;
	}

	public void setIdpart(String idpart) {
		this.idpart = idpart;
	}

	public Date getCreatedat() {
		return this.createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public Conversation getConversation() {
		return this.conversation;
	}
	
	public Date getConversationDate() {
		return this.getConversation().getCreatedat();
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