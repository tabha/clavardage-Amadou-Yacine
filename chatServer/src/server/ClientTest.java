package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import clientClass.ConversationClient;
import clientClass.UserClient;
import datamanager.User;
import datamanager.Conversation;
import datamanager.DBQueries;
import datamanager.Message;
import datamanager.Participant;
public class ClientTest {
	static String maximJson ="{\n"
			+ "  \"iduser\": 200,\n"
			+ "  \"createdat\": \"12/12/2020-12:00:00\",\n"
			+ "  \"email\": \"amadouBk@gmail.com\",\n"
			+ "  \"firstname\": \"maxim\",\n"
			+ "  \"isactive\": true,\n"
			+ "  \"lastname\": \"BK\",\n"
			+ "  \"passworduser\": \"markpassword\",\n"
			+ "  \"updatedat\": \"12/12/2020-12:00:00\",\n"
			+ "  \"conversations\": [],\n"
			+ "  \"messages\": [],\n"
			+ "  \"participants\": [],\n"
			+ "  \"tempusernames\": []\n"
			+ "}";
	
	public static void sendGet() throws MalformedURLException, ProtocolException, IOException{
		URL url = new URL("http://localhost:8080/chatServer/Welcome");
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection.setRequestMethod("GET");
	    Map<String, String> params = new HashMap<>();
	    params.put("action", "1");
	    params.put("allActiveUsers", "true");
	    params.put("userData", maximJson);
	    StringBuilder postData = new StringBuilder();
	    for (Map.Entry<String, String> param : params.entrySet()) {
	        if (postData.length() != 0) {
	            postData.append('&');
	        }
	        postData.append(param.getKey());
	        postData.append('=');
	        postData.append(String.valueOf(param.getValue()));
	    }
	    System.out.println(postData.toString());
	    
	    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
       connection.setDoOutput(true);
       try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
           writer.write(postDataBytes);
           writer.flush();
           writer.close();

           StringBuilder content;

           try (BufferedReader in = new BufferedReader(
                   new InputStreamReader(connection.getInputStream()))) {
           String line;
           content = new StringBuilder();
              while ((line = in.readLine()) != null) {
                   content.append(line);
                   content.append(System.lineSeparator());
               }
           }
           JsonObject jsonObject = new JsonParser().parse(content.toString()).getAsJsonObject();
           System.out.println(content.toString());
           String dataJson = jsonObject.get("data").getAsString();
           System.out.println(dataJson);
           new JsonParser().parse(dataJson).getAsJsonArray()
           				   .forEach((j)->System.out.println(j));
       } finally {
           connection.disconnect();
           System.out.println("Disconnected");
       }
	}
	public static void sendPost() throws MalformedURLException, ProtocolException, IOException{
		URL url = new URL("http://localhost:8080/chatServer/Welcome");
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection.setRequestMethod("GET");
	    Map<String, String> params = new HashMap<>();
	    
	    params.put("action", "3");
	    User amadou =new User();
	    amadou.setIduser("ddd");
	   
	    amadou.setEmail("maoutou@gmail.com");
	    amadou.setPassworduser("alilpass");
	    Date now = new Date();
	   
	    amadou.setCreatedat(now);
	    
	    
	    Gson gson;
       gson = new GsonBuilder().setDateFormat("dd/MM/yyyy-hh:mm:ss").setPrettyPrinting().create();
       String json = gson.toJson(amadou);
       System.out.println(json);
       params.put("userData", json);
	    StringBuilder postData = new StringBuilder();
	    for (Map.Entry<String, String> param : params.entrySet()) {
	        if (postData.length() != 0) {
	            postData.append('&');
	        }
	        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	        postData.append('=');
	        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	    }
	    System.out.println(postData.toString());
	    
	    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
       connection.setDoOutput(true);
       try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
           writer.write(postDataBytes);
           writer.flush();
           writer.close();

           StringBuilder content;

           try (BufferedReader in = new BufferedReader(
                   new InputStreamReader(connection.getInputStream()))) {
           String line;
           content = new StringBuilder();
              while ((line = in.readLine()) != null) {
                   content.append(line);
                   content.append(System.lineSeparator());
               }
           }
          try {
       	   JsonObject jsonObject = new JsonParser().parse(content.toString()).getAsJsonObject();
       	   if(jsonObject.isJsonObject()) {
       		   String jsonResponse = jsonObject.get("data").getAsString();
       		   System.out.println(jsonResponse);
       		   User createdUser = gson.fromJson(jsonResponse,User.class);
       		   if(createdUser.getIduser()==amadou.getIduser() && createdUser.getEmail().equals(amadou.getEmail())) {
       			   System.out.println("Creation success");
       		   }else {
       			   System.out.println("Creation fail");
       		   }
       		   
       	   }
          }catch (JsonSyntaxException err){
               err.printStackTrace();
          }
           System.out.println(content.toString());
       } finally {
           connection.disconnect();
           System.out.println("Disconnected");
       }
	}
	public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException{
		
		test();
	}
	
	public static void test() throws UnknownHostException {
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy-hh:mm:ss").setPrettyPrinting().create();
		ArrayList<ConnectedUser> connectedUsers = new ArrayList<ConnectedUser>();
		User amadou =new User();
	    amadou.setIduser("ddd");
	   
	    amadou.setEmail("maoutou@gmail.com");
	    amadou.setPassworduser("alilpass");
	    Date now = new Date();
	   
	    amadou.setCreatedat(now);
		connectedUsers.add(new ConnectedUser(amadou,InetAddress.getLocalHost(),5005));
		//System.out.println(gson.toJson(connectedUsers));
		
		
		Conversation c= new Conversation();
		c.setIdconv("id");
		c.setTitle("title");
		c.setTypeconv("tyep");
		c.setCreatedat(new Date());
		c.setUser(amadou);
		Message m=new Message();
		m.setIdmes("idms");
		m.setMessagetype("text");
		m.setMessagecontent("heello test");
		m.setCreatedat(new Date());
		
		//m.setConversation(c);
		m.setUser(amadou);
		//System.out.println(gson.toJson(m));
		Participant p = new Participant();
		p.setUser(amadou);
		p.setCreatedat(new Date());
		p.setIdpart("idpart");
		c.addParticipant(p);
		c.addMessage(m);
		//System.out.println(gson.toJson(c));
		UserClient uc = new UserClient(amadou);
		ConversationClient cc = new ConversationClient(c);
		cc.build(c);
		System.out.println(gson.toJson(cc));
		JsonObject js = new JsonObject();
		System.out.println(cc.toJson());
		JsonObject jsonObject = new JsonParser().parse(cc.toJson()).getAsJsonObject();
		JsonArray jarray = new JsonParser().parse(jsonObject.get("participants").getAsString()).getAsJsonArray();
		System.out.println(jarray.toString());
		JsonParser parser = new JsonParser();
		System.out.println(parser.parse(jsonObject.get("messages").getAsString()).getAsJsonArray().toString());
		//js.addProperty("Messages",gson.toJson(uc));
		System.out.println(js.toString());
	}
	
}
