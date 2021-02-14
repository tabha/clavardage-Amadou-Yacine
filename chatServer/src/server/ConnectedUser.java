package server;
import datamanager.User;
import java.io.*;
import java.util.*;

import com.google.gson.JsonObject;

import java.net.*;

public class ConnectedUser  {
    private final User user;
    private Date lastVisitDate;
    private int portNum;
    private String token;
    private InetAddress ipAddr;
    private String pseudo;
    
    public ConnectedUser(User user,InetAddress ip,int port){
        this.user = user;
        this.ipAddr = ip;
        this.portNum = port;
        this.lastVisitDate = new Date();
        
    }
    
    public void setPort(int port) {
    	this.portNum = port;
    }
    public void setIp(InetAddress ip) {
    	this.ipAddr =  ip;
    }
    
    public String getPseudo() {
    	return this.pseudo;
    }
    public void setPseudo(String p) {
    	this.pseudo = p;
    }
    public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser(){
        return this.user;
    }
    public Date getLastVisiteDate(){
        return this.lastVisitDate;
    }
    
    public int getPort(){
        return this.portNum;
    }
    public InetAddress getAddress(){
        return this.ipAddr;
    }
    public void setLastVisitDate(Date lastVisite){
        this.lastVisitDate = lastVisite;
    }
    
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof ConnectedUser || obj==null)) return false;
        ConnectedUser u = (ConnectedUser) obj;
        return Objects.equals(u.getUser().getIduser(), this.getUser().getIduser());
    }
    @Override
    public String toString(){
        return "["+user.toString()+ ", IpAddress =" + this.ipAddr.toString() + " port="+this.portNum+"]";
    }
    public String formatInJson() {
    	User u = this.getUser();
    	JsonObject js = new JsonObject();
		js.addProperty("iduser", u.getIduser());
		js.addProperty("email", u.getEmail());
		js.addProperty("username", u.getUsername());
		
		if(this.getAddress() != null) js.addProperty("ipAddr", this.getAddress().getHostAddress());
		if(this.portNum==-1) js.addProperty("port", this.getPort());
		js.addProperty("token", this.token);
		if(this.getLastVisiteDate()!=null)
			js.addProperty("lastvisite", this.getLastVisiteDate().toString());
    	return js.toString();
    }
    public DistantUserClass formatUserInDistantClass() {
    	return new DistantUserClass(
    			this.user.getIduser(),
    			this.user.getUsername(),
    			this.lastVisitDate,
    			this.portNum,
    			this.ipAddr,
    			this.user.getEmail(),
    			this.pseudo,
    			this.user.getUsername()
    			);
    }
}

class DistantUserClass {
	public String userId;
	public String userName;
	public Date lastVisiteDate;
	private int portNum;
	private InetAddress ipAddr;
	private String email;
    private String pseudo;
	private String firstname;
	public DistantUserClass(
			String userId,
			String userName,
			Date lastvisite,
			int portNum,
			InetAddress ip,
			String email,
			String pseudo,
			String firstName
			) {
	 this.userId = userId;
	 this.userName = userName;
	 this.lastVisiteDate = lastvisite;
	 this.portNum = portNum;
	 this.ipAddr = ip;
	 this.pseudo = pseudo;
	 this.email = email;
	 this.firstname = firstName;
	}
}
