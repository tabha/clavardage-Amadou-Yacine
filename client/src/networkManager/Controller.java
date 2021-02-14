package networkManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amadou
 */

import chatUI.ChatMain;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;

import chatUtils.Constantes;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import chatUI.Error;
public class Controller {
	private User user;
	private ChatMain gui;
        private static final DateFormat dateFormat = new SimpleDateFormat(
            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	private  ArrayList<User>  connectedUsers = new ArrayList<User>();
        private  ArrayList<Conversation>  history = new ArrayList<Conversation>();
        private ArrayList<Conversation>  disconnectedConversation = new ArrayList<Conversation>();
        private ArrayList<Conversation> openConversations=new ArrayList<Conversation>();
	private ArrayList<Message>  unreadmessages=new ArrayList<Message>();;;
	private boolean useServer = true;
        private static final Gson gsonCommand = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
	private ArrayList<Conversation> conversations = new ArrayList<Conversation>();
	private UdpHandler udpHandler;
	private InetAddress ipBrodcast;
	private volatile Message messageToSend = null;
	public Controller(){
            this.connectedUsers = new ArrayList<User>();
            this.conversations = new ArrayList<Conversation>();
        }
        /*
        public Controller(ChatMain gui,User user,boolean useServer,UdpHandler udp)*/
	public void setGUI(ChatMain gui){
            this.gui = gui;
        }
	// server Infos
	private static String serverIp;
	private static int serverPort;
	private static int timeoutConnection;
	private static int updateInterval=5000;
	private static String pathWebpage = null;
	private Error error;
	private Timer timer;
	
	/**
	 * Constantes
	 */
	// Utilise sur les machines Linux
	private static final String PATH_WEBPAGE_LOWERCASE = "/chatServer/Welcome";
	// Utilise sur les machines Windows
	private static final String PATH_WEBPAGE_UPPERCASE = "/ChatServer/Welcome";
	
	/**
	 * Constantes
	 */
	public static final int EXIT_WITHOUT_ERROR = 20;
	public static final int EXIT_GET_CONNECTED_USERS = 21;
	public static final int EXIT_ERROR_SEND_CONNECTION = 22;
	public static final int EXIT_ERROR_SEND_DECONNECTION = 23;
	public static final int EXIT_WITH_ERROR = 24;
	public static final int EXIT_ERROR_SERVER_UNAVAILABLE = 25;
	public static final int USER_AUTH = 4;
        public static final int USER_CONNECTION = 1;
	public static final int USER_CREATE = 3;
        public static final int CHECK_PSEUDO = 5;
        public static final int LOGOUT = 6;
	public static final int FETCH_USER_HISTORY = 40;
	public static final int NO_ERROR = 20;
        public static final int PSEUDO_TAKEN = 25;
        private static final int USER_NOT_AUTHENTICATED = 26;

        void receiveMessage(Message msg) {
            Conversation c=msg.getConversation();
            
            if(!this.isActiveConversation(c)){
                this.conversations.add(c);
            }
            if(!this.isOpenedConversation(c)){
                this.openConversations.add(c);
            }
            if(!this.history.contains(c)){
                this.history.add(c);
            }
            if(this.gui.isSelectedConversation(c)){
                this.gui.updateConversationView(c);
                this.gui.displayConversation(c);
            }else{
                this.unreadmessages.add(msg);
                this.gui.updateConversationsList();
            }
           
            
        }
        
        public boolean isActiveConversation(Conversation conv){
            for(Conversation c: this.conversations){
                if(c.getIdConv().equals(conv.getIdConv()))
                    return true;
            }
            return false;
        }
        
        
        
        public ArrayList<Conversation> getHistory(){
            return this.history;
        }
        
        public boolean isOpenedConversation(Conversation conv){
            for(Conversation c:this.openConversations){
                if(c.getIdConv().equals(conv.getIdConv())){
                    return true;
                }
            }
            return false;
        }
        public Conversation getConversationWithUser(User value) {
            for(Conversation c:this.conversations){
                if(c.getTypeconv().equals("single")){
                    for(User u : c.getParticipants()){
                        if((u.equals(value))){
                            return c;
                        }
                    }
                }
            }
            return null;
        }

    void addOpenedConvesation(Conversation conversation) {
        this.openConversations.add(conversation);
    }

    

    public int getUnreadMessagesNumber(Conversation it) {
        int count = 0;
        for(Message m:this.unreadmessages){
            if(m.getConversation().equals(it)) count++;
        }
        return count;
    }

    public ArrayList<Message> getUndreadMessages(Conversation c) {
       ArrayList<Message> removed = new ArrayList<Message>();
       for(Message m:this.unreadmessages){
           if(m.getConversation().equals(c)){
               removed.add(m);
           }
       }
       for(Message m:removed){
           this.unreadmessages.remove(m);
       }
       return removed;
    }
	/**
	 * Erreurs 
	 */
	@SuppressWarnings("serial")
	public static class ConnectionError extends Exception {};
	@SuppressWarnings("serial")
	public static class SendConnectionError extends Exception {};
	@SuppressWarnings("serial")
	public static class SendDeconnectionError extends Exception {};
	
	
	
	/**
	 * Connect the current user to the Application
	 * @param user
	 * @throws IOException 
	 */
	
	public void connect(User authenticatedUser) throws IOException {
            
            this.user = authenticatedUser;
            // fetch user History form server
            fetchUserHistoryFromServer();
            // change to port num pick it randomly
            SecureRandom rand = new SecureRandom();
            int port = rand.nextInt(45501) + 20000;
            user.setPortNum(port);
            //
            int serverUserPort = user.getPortNum();
            
            // create à new socket for this current client
            ServerSocket socketuser = new ServerSocket(serverUserPort);
            // create a listineer for this server
            SocketWaiter serverSocketWaiter = new SocketWaiter(socketuser,this);
            // start the server for this client
            serverSocketWaiter.start();
            // send Connection to Server
            InetAddress ip = getPublicAdress();
            this.user.setIpAddr(ip);
            System.out.println("connected user :"+user.getUserName() + ", IP="+ip.getHostName()+", portNum="+port);
            sendConnectionServer(authenticatedUser.getUserName(),authenticatedUser.getPseudo(),ip.getHostAddress(),user.getPortNum());
            
            //signalConnectionToConnectedUsers();
            if(this.useServer){
                timer = new Timer();
                timer.scheduleAtFixedRate(new ServerRequestHandler(this), 0, updateInterval);
            }else{
                //use udp protocol to annonce new connection
            }
		
	}
	private static InetAddress getPublicAdress() throws SocketException{
           
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) continue;
                for (InterfaceAddress a : networkInterface.getInterfaceAddresses()) {
                    if (a.getAddress() instanceof Inet4Address) {
                        return a.getAddress();
                    }
                }
            }
            return null;
        }
        public void sendConnectionServer(String username,String  pseudo,String ip,int port) throws IOException{
            JsonObject js = new JsonObject();
            js.addProperty("username", username);
            js.addProperty("pseudo", pseudo);
            js.addProperty("ipAddr", ip);
            js.addProperty("port",port);
            Map<String, String> params = new HashMap<>();
            params.put("action", Integer.toString(USER_CONNECTION));
            params.put("userData", js.toString());
            String postData = formatParam(params);
            HttpURLConnection con = sendRequestToServer(postData);
            String response = getResponseContent(con);
            Gson gsonCommand = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
            ServerResponse serverResponse = gsonCommand.fromJson(response, ServerResponse.class);
            //System.out.println("response "+serverResponse.getData());
            // Show user history in GUI
            // parse data
            User[] usersAray = gsonCommand.fromJson(serverResponse.getData(), User[].class);
            this.receiveConnectedUsersFromServer(new ArrayList<>(Arrays.asList(usersAray)));
            this.conversations = getActiveConversations();
        }
	/**
	 * Disconnect the user
	 * 
	 */
	public void disconnet() {
		
	}
	public ArrayList<User> getConnectedUsers() {
		return connectedUsers;
	}
	public void setConnectedUsers(ArrayList<User> connectedUsers) {
		this.connectedUsers = connectedUsers;
	}
	public ArrayList<Conversation> getConversations() {
		return conversations;
	}
	public void setConversations(ArrayList<Conversation> conversations) {
		this.conversations = conversations;
	}
	public UdpHandler getUdpHandler() {
		return udpHandler;
	}
	public void setUdpHandler(UdpHandler udpHandler) {
		this.udpHandler = udpHandler;
	}
	public Message getMessageToSend() {
		return messageToSend;
	}
	public void setMessageToSend(Message messageToSend) {
		this.messageToSend = messageToSend;
	}
	public void setErrorHandlPage(Error err){
            this.error = err;
        }
	public User getUser() {
            return user;
	}
	public void setUser(User user) {
            this.user = user;
	}
	
	/*
	public static boolean testConnectionServer() {
		//TODO
		return true;
	}*/
	/**
	 * Teste si le serveur est accessible
	 * @return True si le serveur est accessible, False sinon
	 */
	public static boolean testConnectionServer() {
		
            // On a deja teste la connexion et on connait l'URL correcte
            if(pathWebpage != null) {

                try {

                    // Creation de l'URL
                    //URL url = new URL("http://" + serverIP + ":" + serverPort + pathWebpage);
                    URL url = new URL("http://localhost:8080/chatServer/Welcome");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("HEAD");
                    con.setConnectTimeout(timeoutConnection);

                    // Renvoie True si tout se passe bien
                    return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
                }                // Permet de detecter un timeout
                catch (IOException e) {
                        return false;
                }
            }
            // On teste les URL avec et sans majuscules (configuration differente selon Windows ou Linux)
            else {
                boolean connectionOK = false;

                // Test pour Linux (sans majuscule)
                try {
                    // Creation de l'URL
                    //URL url = new URL("http://" + serverIP + ":" + serverPort + PATH_WEBPAGE_LOWERCASE);
                    URL url = new URL("http://localhost:8080/chatServer/Welcome");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("HEAD");
                    con.setConnectTimeout(timeoutConnection);
                    System.out.println("Connected on linux or Mac");
                    if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            connectionOK = true;
                            pathWebpage = PATH_WEBPAGE_LOWERCASE;
                    }
                }
                // Permet de detecter un timeout
                catch (IOException e) {}

                // Test pour Windows (avec majuscules) si le premier test a echoue
                if(!connectionOK) {
                    try {
                            // Creation de l'URL
                            //URL url = new URL("http://" + serverIP + ":" + serverPort + PATH_WEBPAGE_UPPERCASE);
                            URL url = new URL("http://localhost:8080/chatServer/Welcome");
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("HEAD");
                            con.setConnectTimeout(timeoutConnection);
                            System.out.println(con.toString());
                            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                    connectionOK = true;
                                    pathWebpage = PATH_WEBPAGE_UPPERCASE;
                            }

                    }
                    // Permet de detecter un timeout
                    catch (IOException e) {}
                }

                    return connectionOK;
            }

	}

	/**
	 * Envoie une requete au serveur
	 * @param action L'action demandee au serveur
	 * @param paramValue La valeur du parametre passe
	 * @return La connexion au serveur (contenant le status, la reponse, etc.)
	 * @throws IOException Si le serveur est inaccessible
	 */
	public static HttpURLConnection sendRequestToServer(String paramValue) throws IOException {
            URL url = new URL("http://localhost:8080/chatServer/Welcome");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(timeoutConnection);
            //con.setReadTimeout(500);
            byte[] postDataBytes = paramValue.toString().getBytes("UTF-8");
            con.setDoOutput(true);
            try (DataOutputStream writer = new DataOutputStream(con.getOutputStream())) {
               writer.write(postDataBytes);
               writer.flush();
               writer.close();
            } finally {
                
            }
            return con;
	}
	
	
	public void fetchUserHistoryFromServer() throws IOException{            
            String userJson = gsonCommand.toJson(user,User.class);
            ServerResponse serverResponse = this.sendRequestToServerAndGetResponse(userJson, Constantes.FETCH_USER_HISTORY);
            String data = serverResponse.getData();
            Conversation[] history = gsonCommand.fromJson(data, Conversation[].class);
            this.history = new ArrayList<>(Arrays.asList(history));
            this.conversations = getActiveConversations();
	}
	
        public ArrayList<Conversation> getActiveConversations(){
            ArrayList<Conversation> activeConvo =new ArrayList<Conversation>();
            HashSet<String> added = new HashSet<String>();
            if(this.connectedUsers==null){
               return activeConvo;
            }
            for(Conversation c: this.history){
              
                for(User u:this.connectedUsers){
                    if( isInGroup(c,u) && !added.contains(c.getIdConv())){
                        
                        added.add(c.getIdConv());
                        activeConvo.add(c);
                        break;
                    }
                }
            }
            
            return activeConvo;
        }
        
        
        private boolean isInGroup(Conversation c,User other){
            for(User u:c.getParticipants()){
                if(u.getUserId().equals(other.getUserId())){
                    u.setIpAddr(other.getIpAddr());
                    u.setPortNum(other.getPortNum());
                    u.setPseudo(other.getPseudo());
                    return true;
                }
                
            }
            return false;
        }
        
	private static String formatParam(Map<String, String> params) {
		StringBuilder postData = new StringBuilder();
	    for (Map.Entry<String, String> param : params.entrySet()) {
	        if (postData.length() != 0) {
	            postData.append('&');
	        }
	        postData.append(param.getKey());
	        postData.append('=');
	        postData.append(String.valueOf(param.getValue()));
	    }
	    return postData.toString();
	}
	public static User Authenticated(String email,String password) throws IOException {
		JsonObject js = new JsonObject();
		js.addProperty("email", email);
		js.addProperty("password", password);
		Map<String, String> params = new HashMap<>();
                params.put("action", Integer.toString(Constantes.USER_AUTH));
                params.put("userData", js.toString());
                String postData = formatParam(params);

                HttpURLConnection con = sendRequestToServer(postData);
                String response = getResponseContent(con);
                Gson gsonCommand = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
                ServerResponse serverResponse = gsonCommand.fromJson(response, ServerResponse.class);
                String data = serverResponse.getData();
                //System.out.println("data "+data);
                // get the user from data
                return getUserFromResponseData(data);

        }
	public static User getUserFromResponseData(String data) throws UnknownHostException {
		JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
		//String userS = jsonObject.get("user").isJsonNull();
		if(jsonObject.has("error") || ! jsonObject.has("user")){
                    return null;
                }
		String userS = jsonObject.get("user").getAsString();
		JsonObject obj = new JsonParser().parse(userS).getAsJsonObject();
		if(obj.isJsonNull() || obj==null){
                    return null;
                }
		String id = obj.get("iduser").getAsString();
		String email = obj.get("email").getAsString();
		String username = obj.get("username").getAsString();
		String token = obj.get("token").getAsString();
                InetAddress ipAddr = null;
                if(obj.get("ipAddr")!=null){
                 ipAddr= InetAddress.getByName(obj.get("ipAddr").getAsString());
                }
		
		int port = obj.get("port").getAsInt();
		User u = new User(id,username,ipAddr);
		u.setEmail(email);
		u.setPortNum(port);
		u.setToken(token);
		return u;	
		
	}
	
	/**
	 * Retourne le contenu texte d'une reponse du serveur
	 * @param con La connexion au serveur
	 * @return Le contenu texte de la reponse
	 * @throws IOException Si une erreur dans la connexion survient
	 */
	public static String getResponseContent(HttpURLConnection con) throws IOException {
		
            int responseCode = con.getResponseCode();
            InputStream inputStream;

            if(200 <= responseCode && responseCode <= 299)
                    inputStream = con.getInputStream();
            else
                    inputStream = con.getErrorStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder content = new StringBuilder();
            String currentLine;

            while((currentLine = in.readLine()) != null) {
                    content.append(currentLine);
                    content.append(System.lineSeparator());
            }
            in.close();
            return content.toString();
	}
	
	
	/**
	 * Recupere toutes les adresses IP de la machine et les adresses de broadcast associées
	 * @return Map avec toutes les addresses IP
	 * @throws SocketException Exception de Socket
	 */
	public static Map<InetAddress, InetAddress> getAllIpAndBroadcast() throws SocketException {
		
            Map<InetAddress, InetAddress> listIP = new HashMap<>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) continue;
                for (InterfaceAddress a : networkInterface.getInterfaceAddresses()) {
                    if (a.getAddress() instanceof Inet4Address) {
                        listIP.put(a.getAddress(), a.getBroadcast());
                    }
                }
            }
            return listIP;
	}
        
        public static boolean signUpUser(String username, String email, String pass,StringBuilder error) throws IOException{
            JsonObject js = new JsonObject();
            js.addProperty("email", email);
            js.addProperty("password", pass);
            js.addProperty("username", username);
            Map<String, String> params = new HashMap<>();
            params.put("action", Integer.toString(Constantes.USER_CREATE));
            params.put("userData", js.toString());
            String postData = formatParam(params);
            HttpURLConnection con = sendRequestToServer(postData);
            String response = getResponseContent(con);
            Gson gsonCommand = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
            ServerResponse serverResponse = gsonCommand.fromJson(response, ServerResponse.class);
            if(serverResponse.getError()!=Constantes.NO_ERROR){
                String data = serverResponse.getData();
                error.append(data);
                return false;
            }
            return true;
        }
        public static boolean chechPseudoAvailable(String username,String token,String pseudo,StringBuilder error) throws IOException{
            JsonObject js = new JsonObject();
            js.addProperty("username", username);
            js.addProperty("token", token);
            js.addProperty("pseudo", pseudo);
            Map<String, String> params = new HashMap<>();
            params.put("action", Integer.toString(Constantes.CHECK_PSEUDO));
            params.put("userData", js.toString());
            String postData = formatParam(params);
            HttpURLConnection con = sendRequestToServer(postData);
            String response = getResponseContent(con);
            Gson gsonCommand = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
            ServerResponse serverResponse = gsonCommand.fromJson(response, ServerResponse.class);
            if(serverResponse.getError()!=Constantes.NO_ERROR){
                String data = serverResponse.getData();
                error.append(data);
                return false;
            }else if(serverResponse.getError()==Constantes.PSEUDO_TAKEN){
                String data = serverResponse.getData();
                error.append(data);
                return true;
            }
            else{
                String data = serverResponse.getData();
                error.append(data);
                return true;
            }
            
        }
	
        public static void logout(String username,String token,StringBuilder error) throws IOException{
            JsonObject js = new JsonObject();
            js.addProperty("username", username);
            js.addProperty("token", token);
            Map<String, String> params = new HashMap<>();
            params.put("action", Integer.toString(Constantes.LOGOUT));
            params.put("userData", js.toString());
            String postData = formatParam(params);
            HttpURLConnection con = sendRequestToServer(postData);
            String response = getResponseContent(con);
            Gson gsonCommand = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
            ServerResponse serverResponse = gsonCommand.fromJson(response, ServerResponse.class);
            if(serverResponse.getError()!=Constantes.NO_ERROR){
                String data = serverResponse.getData();
                error.append(data);
            }else{
                
            }
        }
        
        public void receiveConnectedUsersFromServer(ArrayList<User> receivedUsers) {
	
		// Utilise pour supprimer les utilisateurs deconnectes
		ArrayList<User> disconnectedUsers = new ArrayList<User>(connectedUsers);
		
		// On traite chaque utilisateur recu
		for(User u : receivedUsers) {
                    
                    disconnectedUsers.remove(u);
                    
                    boolean userHasChanged = false;
                    if(u.equals(this.user)) continue;
                    // On verifie qu'on ne recoit pas sa propre annonce et qu'on ne connait pas deja l'utilisateur
                    if(!connectedUsers.contains(u) && !u.equals(user)) {
                        
                        connectedUsers.add(u);
                    }
                    
                   
                }
		
		// Gestion des utilisateurs deconnectes
		if(!disconnectedUsers.isEmpty()) {
                    for(User u : disconnectedUsers)
                            connectedUsers.remove(u);
		}
		
		
	}
        
        public Conversation getConversation(String id){
            for(Conversation c: this.conversations){
                if(c.getIdConv().equals(id)){
                    return c;
                }
            }
            return null;
        }
        public void startConversationGroup(String id){
            Conversation c = getConversation(id);
        }
        
        public ServerResponse sendRequestToServerAndGetResponse(String userData,int action) throws IOException{
            Map<String, String> params = new HashMap<>();
            params.put("action", Integer.toString(action));
            params.put("userData", userData);
            String postData = formatParam(params);
            HttpURLConnection con = sendRequestToServer(postData);
            String response = getResponseContent(con);
            return  gsonCommand.fromJson(response, ServerResponse.class);
        }
        public void  restartConversation(Conversation conv) throws IOException{
            this.disconnectedConversation.remove(conv);
            startConversationHandlers(conv);
            for(Conversation c: this.conversations){
                if(c.getIdConv().equals(conv.getIdConv())){
                    return;
                }
            }
            this.conversations.add(conv);
           
        }
        public Conversation startNewConversation(String type,String title,List<User> members) throws IOException{
            if(members.isEmpty()){
                System.out.println("empty members");
                this.error.setErrorMessage("empty memebers");
                return null;
            }
                
            Gson gsonCommand = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
            User creator = this.user;
            JsonObject js = new JsonObject();
            js.addProperty("creatorId",creator.getUserId());
            js.addProperty("type", type);
            js.addProperty("title", title);
            js.addProperty("members", gsonCommand.toJson(members));
            ServerResponse serverResponse = this.sendRequestToServerAndGetResponse(js.toString(), Constantes.START_CONVERSATION);
            JsonObject jsonObject = new JsonParser().parse(serverResponse.getData()).getAsJsonObject();
            String idConv =jsonObject.get("conversationId").getAsString();
            String date = jsonObject.get("datecreation").getAsString();
            String realTitle = jsonObject.get("title").getAsString();
            Conversation c = new Conversation();
            c.setIdConv(idConv);
            c.setParticipants(members);
            c.setTypeconv(type);
            c.setTitle(realTitle);
            c.setUser(user);
            Date createdat = null;
            try {
                //createdat = this.formatter.parse(date);
                createdat= dateFormat.parse(date);
            } catch (ParseException ex) {
                // show error
            }
            c.setCreatedat(createdat);
            this.conversations.add(c);
            this.history.add(c);
            
            return c;
        }
        public User getConnectedUser(User u){
            for(User user:this.connectedUsers){
                if(user.equals(u)){
                    return user;
                }
            }
            return null;
        }
        public void startConversationHandlers(Conversation c) throws IOException{
            if(c.getTypeconv().equals("single")){
                
                c = this.getConversation(c.getIdConv());
                User contact =null;
                for(User u: c.getParticipants()){
                    if(u.equals(this.user)) continue;
                    contact = u;
                }
                System.out.println("IP contact"+contact.getIpAddr().getHostName()+", port="+contact.getPortNum());
                System.out.println("IP user"+user.getIpAddr().getHostName()+", port="+user.getPortNum());
                Socket contactSocket = new Socket(contact.getIpAddr(),contact.getPortNum());
                SocketWriter socketWriter = new SocketWriter("clientSocketWriter",contactSocket, this, c);
		SocketReader socketReader = new SocketReader("clientSocketReader", contactSocket, this);
		socketWriter.start();
                
		socketReader.start();
                
                // TODO update ui
                
            }else{
                // brodcast the message
            }
        }
        public void receiveConversation(Conversation received){
            for(Conversation c: this.conversations){
                if(c.getIdConv().equals(received.getIdConv()))
                    return;
            }
            // new conversation
            this.history.add(received);
            // online conversation
            this.conversations.add(received);
        }
        public void saveMessageInDistantServer(Conversation c, Message m) throws IOException, ParseException{
            
            JsonObject js = new JsonObject();
            js.addProperty("senderId",user.getUserId());
            js.addProperty("conversationId", c.getIdConv());
            js.addProperty("typeContent", m.getTypeMsg());
            js.addProperty("content", m.getMessage());
            ServerResponse response = sendRequestToServerAndGetResponse(js.toString(),Constantes.SAVE_MESSAGE);
            JsonObject jsonObject = new JsonParser().parse(response.getData()).getAsJsonObject();
            String date = jsonObject.get("datecreation").getAsString();
            String time = jsonObject.get("chunkTime").getAsString();
            if(m.getTypeMsg().equals(Constantes.IMG) || m.getTypeMsg().equals(Constantes.FILE)){
                String savedFileName = jsonObject.get("savedFileName").getAsString();
                m.setMessage(savedFileName);
            }
            m.setDateEnvoi(dateFormat.parse(date));
            c.getMessages().add(m);
            
        }
        public void sendMessage(Conversation c, Message m) throws IOException, ParseException{
           
            if(!this.isActiveConversation(c)){
                c = this.startNewConversation(c.getTypeconv(), c.getPseudoTitle(), c.getParticipants());
                this.conversations.add(c);
            }
            
            if(!this.isOpenedConversation(c)){
                this.openConversations.add(c);
                this.startConversationHandlers(c);
            }
            // send message to server for saving i
            m.setConversation(c);
            
            // send the message to all participant via tcp socket   
            this.messageToSend = m;
        }
        public void  messageSent(Message msg){
            this.gui.addSentMessage(msg);
            
            this.gui.displayConversation(msg.getConversation());
            
            this.messageToSend=null;
        }     
        public String sendMessageToGroup(Message m,Conversation c){
            for(User u: c.getParticipants()){
                if(isActiveUser(u)){
                    sendMessageToUser(m,u);
                }
            }
          return "";  
        }
        public void sendMessageToUser(Message m, User  u){
            
        }
        private boolean isActiveUser(User u){
            return this.connectedUsers.contains(u);
        }
        private void showHistory(){
            System.out.println("Hisotry of "+ this.user.getUserName());
            for(Conversation c: this.history){
                System.out.println(c.getIdConv()+ ", title="+ c.getTitle());
                for(User u: c.getParticipants()){
                    System.out.println(" "+u.getUserId()+", name="+u.getUserName());
                }
            }
            System.out.println("Active conversations of "+ this.user.getUserName());
            for(Conversation c: this.conversations){
                System.out.println(c.getIdConv()+ ", title="+ c.getTitle());
                for(User u: c.getParticipants()){
                    System.out.println(" "+u.getUserId()+", name="+u.getUserName());
                }
            }
            showConnectedUsers();
        }
        private void showConnectedUsers(){
            System.out.println("Connected users of "+ this.user.getUserName());
            for(User u: this.connectedUsers){
                System.out.println(" "+u.getUserId()+", name="+u.getUserName());
            }
        }
}                                           

