package datamanager;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")

@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email=:email")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iduser;

	@Temporal(TemporalType.DATE)
	private Date createdat;

	private String email;

	private String passworduser;

	private String username;

	//bi-directional many-to-one association to Conversation
	@OneToMany(mappedBy="user")
	private List<Conversation> conversations;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="user")
	private List<Message> messages;

	//bi-directional many-to-one association to Participant
	@OneToMany(mappedBy="user")
	private List<Participant> participants;

	public User() {
	}

	public String getIduser() {
		return this.iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public Date getCreatedat() {
		return this.createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassworduser() {
		return this.passworduser;
	}

	public void setPassworduser(String passworduser) {
		this.passworduser = passworduser;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Conversation> getConversations() {
		return this.conversations;
	}

	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}

	public Conversation addConversation(Conversation conversation) {
		getConversations().add(conversation);
		conversation.setUser(this);

		return conversation;
	}

	public Conversation removeConversation(Conversation conversation) {
		getConversations().remove(conversation);
		conversation.setUser(null);

		return conversation;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setUser(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setUser(null);

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
		participant.setUser(this);

		return participant;
	}

	public Participant removeParticipant(Participant participant) {
		getParticipants().remove(participant);
		participant.setUser(null);

		return participant;
	}
	
	public User clone() {
		User u = new User();
		u.setIduser(this.iduser);
		u.setEmail(this.email);
		u.setUsername(this.username);
		u.setCreatedat(this.createdat);
		u.setPassworduser(null);
		return u;
	}

}