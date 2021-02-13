package datamanager;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the conversation database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Conversation.findAll", query="SELECT c FROM Conversation c"),
	@NamedQuery(name="Conversation.findById", query="SELECT c FROM Conversation c WHERE c.idconv=:id"),
	@NamedQuery(name="Conversation.findUserConv", query="SELECT c FROM Conversation c WHERE c.user= :user"),
	@NamedQuery(name="Conversation.findConvByType", query="SELECT c FROM Conversation c WHERE c.typeconv=:type"),

	
})
public class Conversation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idconv;

	@Temporal(TemporalType.DATE)
	private Date createdat;

	private String title;

	private String typeconv;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="creatorid")
	private User user;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="conversation")
	private List<Message> messages;

	//bi-directional many-to-one association to Participant
	@OneToMany(mappedBy="conversation")
	private List<Participant> participants;

	public Conversation() {
		this.participants = new ArrayList<Participant>();
		this.messages = new ArrayList<Message>(); 
	}

	public String getIdconv() {
		return this.idconv;
	}

	public void setIdconv(String idconv) {
		this.idconv = idconv;
	}

	public Date getCreatedat() {
		return this.createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTypeconv() {
		return this.typeconv;
	}

	public void setTypeconv(String typeconv) {
		this.typeconv = typeconv;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setConversation(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setConversation(null);

		return message;
	}

	public List<Participant> getParticipants() {
		return this.participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public Participant addParticipant(Participant participant) {
		getParticipants().add(participant);
		participant.setConversation(this);

		return participant;
	}

	public Participant removeParticipant(Participant participant) {
		getParticipants().remove(participant);
		participant.setConversation(null);

		return participant;
	}
	
	
}

