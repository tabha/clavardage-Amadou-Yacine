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

import chatUtils.Constantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conversation implements Serializable {
        private static final long serialVersionUID = 1L;
	private Date createdat;
	private String idConv;
	private List<User> participants;
	
	public List<User> getParticipants() {
		return participants;
	}


	public void setParticipants(List<User> participants) {
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

	private User user;
	
	private List<Message> messages;

	public Conversation() {
            this.participants = new ArrayList<User>();
            this.messages = new ArrayList<Message>();
	}
	
	
	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}
        public String getPseudoTitle(){
            return title;
        }
	public String getTitle() {
            if(!title.equals(Constantes.UNTITLED)) return title;
            
            // format Title to print
            String formatTitle = "";
            if(title.equals(Constantes.UNTITLED)){
                if(getTypeconv().equals(Constantes.GROUP)){
                    int count = 1;
                    int index = 0;
                    formatTitle="";
                    while(count <=2 && index < getParticipants().size()){
                        formatTitle += count == 2 ? ",":"";
                        User u = getParticipants().get(index);
                        if(u.equals(user)){
                            index++;
                            continue;
                        }
                        formatTitle += u.getUserName();
                        count++;
                    }
                }else{
                    for(User u: getParticipants()){
                        if(u.equals(user)) continue;
                        formatTitle = u.getUserName();
                        break;
                    }
                }
            }
            return formatTitle;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public boolean equals(Object obj) {
            if( obj == null || !(obj instanceof Conversation)) return false;
            Conversation c = (Conversation) obj;
            return c.getIdConv().equals(this.idConv);
	}

}
