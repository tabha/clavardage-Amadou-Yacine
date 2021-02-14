package clientClass;

import java.util.ArrayList;

/**
 *
 * @author amadou
 */

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import datamanager.Conversation;
import datamanager.Message;
import datamanager.Participant;

public class ConversationClient {
	private Date createdat;
	private String idConv;
	private List<UserClient> participants;
	
	
	public List<UserClient> getParticipants() {
		return participants;
	}
	public ConversationClient(Conversation c) {
		this.idConv=c.getIdconv();
		this.createdat=c.getCreatedat();
		this.title = c.getTitle();
		this.typeconv = c.getTypeconv();
	}
	
	public void build(Conversation c) {
		getMessages(c);
		getParticipants(c);
	}
	public  void getMessages(Conversation c){
		 List<MessageClient> messages= new  ArrayList<MessageClient>();
		 for(Message m:c.getMessages()){
			 messages.add(new MessageClient(m,this));
		 }
		 this.messages = messages;
	}
	
	public void getParticipants(Conversation c){
		List<UserClient> part = new ArrayList<UserClient>();
		for(Participant u: c.getParticipants()) {
			part.add(new UserClient(u.getUser()));
		}
		this.participants = part;
	}

	public void setParticipants(List<UserClient> participants) {
		this.participants = participants;
	}


	public String getIdConv() {
		return idConv;
	}


	public void setIdConv(String idConv) {
		this.idConv = idConv;
	}

	private String title;
	private String typeconv;
	
	private Date updatedat;

	private UserClient user;
	
	private List<MessageClient> messages;

	public ConversationClient() {
		
	}
	
	
	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTypeconv() {
		return typeconv;
	}

	public void setTypeconv(String typeconv) {
		this.typeconv = typeconv;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	public UserClient getUser() {
		return user;
	}

	public void setUser(UserClient user) {
		this.user = user;
	}

	public List<MessageClient> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageClient> messages) {
		this.messages = messages;
	}
	public String toJson() {
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
		JsonObject j = new JsonObject();
		j.addProperty("id", this.idConv);
		j.addProperty("title",this.title);
		j.addProperty("type", this.typeconv);
		j.addProperty("datecreation", this.createdat.toString());
		j.addProperty("creator",gson.toJson(this.user));
		j.addProperty("participants",gson.toJson(this.participants));
		j.addProperty("messages", gson.toJson(this.messages));
		return j.toString();
	}
	

}
