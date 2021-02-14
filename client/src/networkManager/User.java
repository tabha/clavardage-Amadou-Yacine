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
import java.net.InetAddress;
import java.util.Date;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String token;
	private Date lastVisiteDate;
	private int portNum;
	private InetAddress ipAddr;
	private String email;
        private String pseudo;
	private String firstname;

	
	public User(String userId, String username,InetAddress ipAddr) {
		this.userId = userId;
		this.userName = username;
		this.ipAddr = ipAddr;
	}

        public User() {
        }

        
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
        public String getPseudo(){
            return this.pseudo;
        }
        public void setPseudo(String p){
            this.pseudo = p;
        }
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}
	public void setToken(String tok) {
		this.token = tok;
	}
	public String getToken() {
		return this.token;
	}
	public String getUserId() {
		return this.userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public Date getLastVisiteDate() {
		return this.lastVisiteDate;
	}
	
	public void setLastVisiteDate(Date lastVisiteDate) {
		this.lastVisiteDate = lastVisiteDate;
	}
	public int getPortNum() {
		return this.portNum;
	}
	
	public InetAddress getIpAddr() {
		return this.ipAddr;
	}
	public void setIpAddr(InetAddress ip){
            this.ipAddr = ip;
        }
	
	@Override
	public boolean equals(Object obj) {
            if( obj == null || !(obj instanceof User)) return false;
            User u = (User) obj;
            return u.userId.equals(this.userId);
	}
	@Override
	public String toString() {
            return "[ (id="+ this.userId+"), username="+ this.userName +", token="+this.getToken()+"]";
	}
	
}

