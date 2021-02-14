package server;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import com.google.gson.JsonSyntaxException;

import clientClass.ConversationClient;
import datamanager.Conversation;
import datamanager.DBQueries;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import datamanager.User;
import datamanager.GenerateId;
import datamanager.Message;
import datamanager.Participant;
public class ChatServer extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
   
    public static final int AUTO_DECONNECTION_DELAY = 10000;

     
    
    public static DBQueries databaseQueries =null ;
    
    // List des utilisateur sur le serveur
    private ArrayList<ConnectedUser> connectedUsers;
    public class ServerResponse{
        private int error;
        private String dataFormat;
        private String data;
        public ServerResponse(int code,String dataFormat){
            this.error = code;
            this.dataFormat = dataFormat;
            this.data = null;
        }    
        public int getError(){
            return this.error;
        }
        public String getDataFormat(){
            return this.dataFormat;
        }
        public String getData(){
            return this.data;
        }
        public void setData(String data){
            this.data = data;
        }
        public void setError(int err){
            this.error = err;
        }
        public void setDataFormat(String format){
            this.dataFormat = format;
        }
    }
    
    public void init() throws ServletException{
    	connectedUsers = new ArrayList<>();
        databaseQueries = new DBQueries();
	}
    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        // donnée au format json
        response.setContentType("application/json");
        // la reponse au serveur au format json
        ServerResponse serverResponse = new ServerResponse(Constantes.NO_ERROR,"json");
        // flux de sortie
        PrintWriter out = response.getWriter();
        // paramètre de la requet
        HashMap<String,String> params = getParamaterMap(request);
        // constructeur json de google
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy-hh:mm:ss").setPrettyPrinting().create();
        String jsonDataString;
        // request de test
        
        if(params.containsKey("test")){
            serverResponse.setData("Test de connexion reussi");
            jsonDataString = gson.toJson(serverResponse);
            out.write(jsonDataString);
        }
        // action
        else if((params.containsKey("action"))){
            try{
               int action = Integer.parseInt(params.get("action"));
               switch(action){
               	   case Constantes.USER_AUTH:
               		   if(params.containsKey("userData")) {
							String userDataJson = params.get("userData");
							JsonObject jsonObject = new JsonParser().parse(userDataJson.toString()).getAsJsonObject();
							String email = jsonObject.get("email").getAsString();
							String pass = jsonObject.get("password").getAsString();
							User authUserToSend = databaseQueries.AuthenticatedUser(email,pass);
							
							JsonObject js = new JsonObject();
							if(authUserToSend!=null) {
								
								String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	                        	if (ipAddress == null) {  
	                        	   ipAddress = request.getRemoteAddr();  
	                        	}
	                        	InetAddress ipuser = null;
	                        	
	                            int port = -1; //ssssss
								ConnectedUser u = new ConnectedUser(authUserToSend,ipuser,port);
								u.setPseudo("unknown");
								String newToken =null;
								boolean isOk = false;
								boolean isAlreadyConnected = false;
								while( ! isOk && !isAlreadyConnected) {
									newToken =  GenerateId.DoGenerate();
									isOk = true;
									for(ConnectedUser user:connectedUsers) {
										if(user.getToken().equals(newToken)) {
											isOk = false;
											break;
										}
										if(user.getUser().getEmail().equals(email)) {
											isAlreadyConnected = true;
											newToken = user.getToken();
											break;
										}
									}
									
								}
								u.setToken(newToken);
								if(!isAlreadyConnected) {
									connectedUsers.add(u);
								}
								js.addProperty("user",u.formatInJson());
								
								
							}else {
								js.addProperty("error", "Authentication Failed");
							}
							serverResponse.setError(Constantes.NO_ERROR);
							serverResponse.setData(js.toString());
                 	    
               		   }else {
               			   serverResponse.setError(Constantes.NO_USER_DATA);
               			   serverResponse.setData("No user Data entered");
               		   }
               		   
               		break;
               	   case Constantes.USER_CREATE: // inscription user
               		   if(params.containsKey("userData")) {
               			   // recuperation des données utilisateur sous format json
               			   String userDataJson = params.get("userData");
               			   JsonObject jsonObject = new JsonParser().parse(userDataJson.toString()).getAsJsonObject();
               			   String email = jsonObject.get("email").getAsString();
               			   String username = jsonObject.get("username").getAsString();
               			   String password = jsonObject.get("password").getAsString();
               			   try {
								String resultJson=createUser(email,username,password,gson);
								serverResponse.setError(Constantes.NO_ERROR);
								serverResponse.setData(resultJson);
							
               			   } catch (Exception e) {
               				   serverResponse.setError(Constantes.INVALID_ENTRY);
            				   serverResponse.setData("something went wrong");
               			   }
    
               		   }else{
                           // erreur pas de donnée user dans la req
                           serverResponse.setError(Constantes.NO_USER_DATA);
                           serverResponse.setData("No user data");
                       }
               		   break;
                   case Constantes.USER_CONNECTION:
                       // when a user is connected we send him all active users
                       if(params.containsKey("userData")){
                    	   String userDataJson = params.get("userData");
                    	   String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	                       	if (ipAddress == null) {  
	                       	   ipAddress = request.getRemoteAddr();  
	                       	}
	                       	
	                       	

               			   // construction du modèle user 
                    	   JsonObject jsonObject = new JsonParser().parse(userDataJson.toString()).getAsJsonObject();
                    	   String username = jsonObject.get("username").getAsString();
                    	   String pseudo = jsonObject.get("pseudo").getAsString();
                    	   String remoteIp = jsonObject.get("ipAddr").getAsString();
                    	   int remotePort = jsonObject.get("port").getAsInt();
                    	   ConnectedUser u = isKnownUser(username, pseudo);
                    	   if(u!=null) {
                    		   // update last visite user
                    		   u.setLastVisitDate(new Date());
                    		   u.setIp(InetAddress.getByName(remoteIp));
                    		   u.setPort(remotePort);
                    		   String resultJson = formatConnectedUserToResponse(u,gson);
                    		   serverResponse.setError(Constantes.NO_ERROR);
                               serverResponse.setData(resultJson.toString());
                    		   
                    	   }else {
                    		   serverResponse.setError(Constantes.NO_SUCH_USER);
                               serverResponse.setData("Please authenticate First");
                    	   }
                    	  
                       }else{
                           // erreur pas de donnée user dans la req
                           serverResponse.setError(Constantes.NO_USER_DATA);
                           serverResponse.setData("No user data");
                       }
                     break;
                   case Constantes.USER_DISCONNECTION:
                       // do something here for disconnection user
                       break;
                   case Constantes.SAVE_DATA:
                	   if(params.containsKey("userData") && params.containsKey("data")) {
                		   String userDataJson = params.get("userData");
                		   JsonObject jsonObject = new JsonParser().parse(userDataJson.toString()).getAsJsonObject();
                		   String email = jsonObject.get("email").getAsString();
                    	   
                    	   List<User> userList = databaseQueries.getUserByEmail(email);
                    	   if(userList==null || userList.size()!=1) {
                    		   //unknown user
                    		   System.out.println("unknown user");
                    		   serverResponse.setError(Constantes.NO_SUCH_USER);
                               serverResponse.setData("Please authenticate First");
                    	   }else {
                    		   // get the type of data to save
                    		   String dataJson = params.get("data");
                    		   JsonObject dataJsonObject = new JsonParser().parse(dataJson.toString()).getAsJsonObject();
                    		   int type = dataJsonObject.get("type").getAsInt();
                    		   
                    		   switch(type) {
                    		   		case Constantes.MESSAGE :
                    		   			// get the conversation id in json data
                    		   			String convId = dataJsonObject.get("conversationId").getAsString();
                    		   			String sender = dataJsonObject.get("senderId").getAsString();
                    		   			String typeContent = dataJsonObject.get("typeContent").getAsString();
                    		   			String content = dataJsonObject.get("content").getAsString();
                    		   			String responseJson;
                    		   			JsonObject js = new JsonObject();
                    		   			if(convId!=null && ! convId.isEmpty()) {
                    		   				// add the new message in that conversation
                    		   				
                    		   				databaseQueries.addMessageInConversation(convId,sender,content,typeContent);
                    		   				js.addProperty("response", "message added");
                    		   				
                    		   			}else {
                    		   				// shoudl create new conversation and add new message
                    		   				JsonArray participants = dataJsonObject.get("participants").getAsJsonArray();
                    		   				String title = dataJsonObject.get("title").getAsString();
                    		   				String createdConversationID =databaseQueries.createConversationAndAddMessage(sender,content,typeContent,participants,title);
                    		   				
                    		   				js.addProperty("conversationId", createdConversationID);
                    		   				//js.addProperty(property, value);
                    		   			}
                    		   			responseJson = js.toString();
                    		   			serverResponse.setError(Constantes.NO_ERROR);
                    		   			serverResponse.setData(responseJson);
                    		   			break;
                    		   	
                    		   		default:
                    		   			break;
                    		   }
                    	   }
                    		
                	   }
                	   
                	   break;
                   case Constantes.FETCH_USER_HISTORY:
                	   if(params.containsKey("userData")) {
                		   String userDataJson = params.get("userData");
               			   // construction du modèle user 
                    	   JsonObject jsonObject = new JsonParser().parse(userDataJson.toString()).getAsJsonObject();
                    	   String userId = jsonObject.get("userId").getAsString();
                    	   List<Conversation>  history = databaseQueries.getUserHistory(userId);
                    	   String historytoJson = this.formatUserHisotryToJson(history,gson);
                    	   serverResponse.setError(Constantes.NO_ERROR);
                    	   serverResponse.setData(historytoJson);
                	   }else {
                		   
                	   }
                	   break;
                   
                   case Constantes.CHECK_PSEUDO:
                	   if(params.containsKey("userData")) {
                		   String userDataJson = params.get("userData");
 		   				   JsonObject jsonObject = new JsonParser().parse(userDataJson.toString()).getAsJsonObject();
 		   				   
 		   				   String username = jsonObject.get("username").getAsString();
 		   				   String token = jsonObject.get("token").getAsString();
 		   				   String pseudo = jsonObject.get("pseudo").getAsString();
 		   				   boolean found = false;
 		   				   for(ConnectedUser u:connectedUsers) {
 		   					   if(u.getUser().getUsername().equals(username) && u.getToken().equals(token)) {
 		   						   found=true;
 		   					   }
 		   				   }
 		   				   if(!found) {
 		   					   //this user is not authenticated
 		   					   serverResponse.setError(Constantes.USER_NOT_AUTHENTICATED);
 		   					   serverResponse.setData("Please Authenticate First");
 		   				   }else {
 		   					   boolean available = this.checkPseudoAvailability(pseudo,username);
 		   					   if(!available) {
 		   						   serverResponse.setError(Constantes.PSEUDO_TAKEN);
 		   						   serverResponse.setData("Pseudo is taken");
 		   					   }else {
 		   						   serverResponse.setError(Constantes.NO_ERROR);
 		   						   serverResponse.setData("");
 		   					   }
 		   				   }
                	   }
                	   break;
                   case Constantes.START_CONVERSATION:
                	    if(params.containsKey("userData")) {
		        	    	 String userDataJson = params.get("userData");
		   				     JsonObject jsonObject = new JsonParser().parse(userDataJson.toString()).getAsJsonObject();
		   				     String creatorId = jsonObject.get("creatorId").getAsString();
		   				     String type = jsonObject.get("type").getAsString();
		   				     String title = jsonObject.get("title").getAsString();
		   				     DistantUserClass[]  members = gson.fromJson(jsonObject.get("members").getAsString(), DistantUserClass[].class);
		   				     ArrayList<String> membersIds = new ArrayList<String>();
		   				     membersIds.add(creatorId);
		   				     for(DistantUserClass m:members) {
		   				    	 
		   				    	 membersIds.add(m.userId);
		   				     }
		   				     
		   				     Conversation c = databaseQueries.createConversation(creatorId,type,title,membersIds);
		   				     JsonObject json = new JsonObject();
		   				     json.addProperty("conversationId", c.getIdconv());
		   				     json.addProperty("datecreation", c.getCreatedat().toString());
		   				     json.addProperty("title", c.getTitle());
		   				     
		   				     serverResponse.setError(Constantes.NO_ERROR);
		   				     serverResponse.setData(json.toString());
		   				     
                	    }else {
                	    	serverResponse.setError(Constantes.INVALID_ENTRY);
		   				    serverResponse.setData("");
                	    }
                	    break;
                   case Constantes.SAVE_MESSAGE:
                	   	if(params.containsKey("userData")) {
                	   		String userDataJson = params.get("userData");
                	   		JsonObject jsonObject = new JsonParser().parse(userDataJson.toString()).getAsJsonObject();
        		   			String sender = jsonObject.get("senderId").getAsString();
        		   			if(isStillAuthenticated(sender)) {
        		   				String convId = jsonObject.get("conversationId").getAsString();
        		   				String typeContent = jsonObject.get("typeContent").getAsString();
            		   			String content = jsonObject.get("content").getAsString();
            		   			Message m=databaseQueries.addMessageInConversation(convId, sender, content, typeContent);
            		   			serverResponse.setError(Constantes.NO_ERROR);
            		   			JsonObject json = new JsonObject();
            		   			json.addProperty("datecreation",m.getCreatedat().toString());
            		   			json.addProperty("chunkTime",m.getCreatedat().getTime());
            		   			if(typeContent.equals("img")||typeContent.endsWith("file")) {
            		   				json.addProperty("savedFileName",m.getMessagecontent());
            		   			}
            		   			System.out.println("date "+m.getCreatedat().getTime());
            		   			serverResponse.setData(json.toString());
        		   			}else {
        		   				serverResponse.setError(Constantes.INVALID_ENTRY);
        		   				serverResponse.setDataFormat("");
        		   			}
        		   			
                	   	}else {
                	   		serverResponse.setError(Constantes.INVALID_ENTRY);
		   				    serverResponse.setData("");
                	   	}
                	   break;
                   default:
                       break;
               }
            }catch(JsonSyntaxException e){
                
            }
            finally{
                jsonDataString = gson.toJson(serverResponse);
                out.write(jsonDataString);
            }
        }else{
            serverResponse.setError(Constantes.NO_ACTION);
            serverResponse.setData("Error No action specified in param");
            jsonDataString = gson.toJson(serverResponse);
            out.write(jsonDataString);
        }
        deconnectOldUser();
    }
    private boolean isStillAuthenticated(String userId) {
    	for(ConnectedUser u: this.connectedUsers) {
    		if(u.getUser().getIduser().equals(userId) && u.getPort()!=-1) return true;
    	}
    	return false;
    }
    private HashMap<String,String> getParamaterMap(HttpServletRequest req){
        HashMap<String,String> map = new HashMap<>();
        Enumeration<String> params = req.getParameterNames();
        
        String name,value="";
        while(params.hasMoreElements()){
            name=params.nextElement();
            value="";
            for(String val:req.getParameterValues(name))
                value += val;
            map.put(name,value);    
            
        }
        return map;
    }
    
    private boolean checkPseudoAvailability(String pseudo,String username) {
    	ConnectedUser user = null;
    	for(ConnectedUser u:this.connectedUsers) {
    		if(u.getPseudo().equals(pseudo)  && !u.getUser().getUsername().equals(username)) {
    			return false;
    		}
    		if(u.getUser().getUsername().equals(username)) {
    			user = u;
    		}
    	}
    	user.setPseudo(pseudo);
    	
    	return true;
    }
    
    
    private void deconnectOldUser(){
    	if(this.connectedUsers.isEmpty())
    		return;
        Date currentTime = new Date();
        ArrayList<ConnectedUser> temp = new ArrayList<ConnectedUser>(this.connectedUsers);
        for(ConnectedUser u: this.connectedUsers) {
        	if((currentTime.getTime() - u.getLastVisiteDate().getTime()) >=AUTO_DECONNECTION_DELAY ) {
        		temp.remove(u);
        	}
        }
        this.connectedUsers=temp;
       
    }
    /**
     * return a json array in format string containing all conversations
     * @param history
     * @return
     */
    private String formatUserHisotryToJson(List<Conversation> history,Gson gson) {
    	List<ConversationClient> tmp = new ArrayList<ConversationClient>();
    	for(Conversation c:history) {
    		ConversationClient cc =new ConversationClient(c);
    		cc.build(c);
    		tmp.add(cc);
    	}
    	return gson.toJson(tmp);
    	
    }
    /**
     * Create a user an return a json format of the created user
     * @param email
     * @param username
     * @param password
     * @param gson
     * @return
     * @throws Exception
     */
    private String createUser(String email,String username, String password,Gson gson) throws Exception {
    	User createdUser = databaseQueries.InsertUser(username, email, password);
		User userTosend = new User();
		userTosend.setIduser(createdUser.getIduser());
		userTosend.setEmail(createdUser.getEmail());
		userTosend.setPassworduser(null);
		userTosend.setCreatedat(createdUser.getCreatedat());
		userTosend.setUsername(createdUser.getUsername());
		String resultJson= gson.toJson(userTosend,User.class);
		return resultJson;
    }
    private ConnectedUser isKnownUser(String username,String pseudo) {
    	for(ConnectedUser u: this.connectedUsers) {
    		if(u.getUser().getUsername().equals(username) && u.getPseudo().equals(pseudo)) {
    			return u;
    		}
    	}
    	return null;
    }
    
    private String formatConnectedUserToResponse(ConnectedUser remoteUser,Gson gson) {
    	ArrayList<DistantUserClass> temp = new ArrayList<DistantUserClass>();
    	for(ConnectedUser u: this.connectedUsers) {
    		if(u.getPort() ==-1 || remoteUser.getUser().getIduser().equals(u.getUser().getIduser()) ) continue;
    		temp.add(u.formatUserInDistantClass());
    	}
    	return gson.toJson(temp);
    	
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        doGet(request,response);
    }
}