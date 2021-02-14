package clientClass;

import java.io.Serializable;
import java.util.Date;

import datamanager.Message;

public class MessageClient implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private UserClient sender;
	private ConversationClient conversation;
	private Date dateEnvoi;
	public void setDateEnvoi(Date dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}
	
	
	public MessageClient(Message m,ConversationClient c) {
		this.msg = m.getMessagecontent();
		this.typeMsg = m.getMessagetype();
		this.dateEnvoi = m.getCreatedat();
		this.sender = new UserClient(m.getUser());
		
	}
	private String typeMsg;
	
	
	public String getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(String typeMsg) {
		this.typeMsg = typeMsg;
	}
	public MessageClient() {
		
	}
	public MessageClient(String msg, UserClient sender, ConversationClient convo, Date dateEnvoi) {
		this.msg = msg;
		this.sender = sender;
		this.conversation = convo;
		this.dateEnvoi = dateEnvoi;
	}
	
	public String getMessage() {
		return this.msg;
	}
	public void setMessage(String msg) {
		this.msg = msg;
	}
	public UserClient getSender() {
		return this.sender;
	}
	public void setSender(UserClient us) {
		this.sender = us;
	}
	public ConversationClient getConversation() {
		return this.conversation;
	}
	public void setConversation(ConversationClient c) {
		this.conversation = c;
	}
	
	public Date getDateEnvoi() {
		return this.dateEnvoi;
	}
	
	
	
}
