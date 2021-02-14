/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkManager;

/**
 *
 * @author amadou
 */

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private User sender;
	private Conversation conversation;
	private Date dateEnvoi;
	public void setDateEnvoi(Date dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	private String typeMsg;
	
	
	public String getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(String typeMsg) {
		this.typeMsg = typeMsg;
	}
	public Message() {
		
	}
	public Message(String msg, User sender, Conversation convo, Date dateEnvoi) {
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
	public User getSender() {
		return this.sender;
	}
	public void setSender(User us) {
		this.sender = us;
	}
	public Conversation getConversation() {
		return this.conversation;
	}
	public void setConversation(Conversation c) {
		this.conversation = c;
	}
	
	public Date getDateEnvoi() {
		return this.dateEnvoi;
	}
	
	
	
}
