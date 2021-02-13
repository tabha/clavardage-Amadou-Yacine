package datamanager;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Date;


public class DBQueries {

    private EntityManager entityManager = null;
    private EntityManagerFactory entityManagerFactory = null;
    private EntityTransaction transaction = null;
    public DBQueries(){
       
        // create the entity factory
        entityManagerFactory = Persistence.createEntityManagerFactory("dataManagerPU");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
      
    }
    /**
     * retrieve a user by his id from the database
     * @param userId
     * @return
     */
    public List<User> getUserById(String userId){
        return entityManager.createNamedQuery("User.findByIduser",User.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public List<User> getUserByEmail(String email) {
    	return entityManager.createNamedQuery("User.findByEmail",User.class)
    			.setParameter("email", email)
    			.getResultList();
    }
    /**
     * Insert a user in the database and return the new id of the newUser in the database
     * @param newUser
     * @return
     * @throws Exception
     */
    public String InsertUser(User newUser) throws Exception{
    	String newId = GenerateId.DoGenerate();
    	User isTaken = entityManager.find(User.class,newId);
    	if(isTaken!=null)
    		return InsertUser(newUser);
    	newUser.setIduser(newId);
    	try{
            transaction.begin();
            entityManager.persist(newUser);
            transaction.commit();
            System.out.println("new user added");
        }catch(Exception e){
            transaction.rollback();
            throw new Exception(e.getMessage());
        }
    	return newId;
    }
    public User InsertUser(String username, String email, String password ) throws Exception{
    	String newId = GenerateId.DoGenerate();
    	User isTaken = entityManager.find(User.class,newId);
    	if(isTaken!=null)
    		return InsertUser( username,  email,  password);
    	User newUser = new User();
    	newUser.setIduser(newId);
    	newUser.setEmail(email);
    	newUser.setUsername(username);
    	newUser.setPassworduser(BCrypt.hashpw(password, BCrypt.gensalt(12)));
    	Date now= new Date();
    	newUser.setCreatedat(now);
    	try{
            transaction.begin();
            entityManager.persist(newUser);
            transaction.commit();
            System.out.println("new user added");
        }catch(Exception e){
            transaction.rollback();
            throw new Exception(e.getMessage());
        }
    	return newUser;
    }
    /**
     * Remove a user
     * @param userId
     */
    public void deleteUser(String userId){
        User toBeRemoved = entityManager.find(User.class, userId);
        if(toBeRemoved==null){
            return;
        }
        try{
            transaction.begin();
            entityManager.merge(toBeRemoved);
            entityManager.remove(toBeRemoved);
            transaction.commit();
            System.out.println("new user removed");
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
           System.out.println("something went wrong");
        }
    }
    /**
     * change the password
     * @param userId
     * @param newPass
     */
    public void changePassword(String userId,String newPass) {
    	User toBeUpdated = entityManager.find(User.class,userId);
    	if(toBeUpdated==null) {
    		return;
    	}
    	try{
            transaction.begin();
            entityManager.merge(toBeUpdated);
            toBeUpdated.setPassworduser(newPass);
            transaction.commit();
            System.out.println("Password changed");
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
           System.out.println("something went wrong");
        }
    	
    }
    /**
     * Check if a given user is Authenticated
     * @param u
     * @return
     */
    public boolean isAuthenticated(User u){
        User authUser = entityManager.find(User.class, u.getIduser());
        if(authUser==null ||  !authUser.getEmail().equals(u.getEmail()) || !authUser.getPassworduser().equals(u.getPassworduser()))
            return false;
        return true;
    }
    /**
     * get all users in the database
     * @return
     */
    public List<User> getUsers(){
      return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
    } 
    /**
     * retrive the user history
     * @param userId
     * @return
     */
    public List<Conversation> getUserHistory(String userId) {
    	User user = entityManager.find(User.class, userId);
    	if(user==null) {
    		return null;
    	}
    	List<Conversation> convo = new ArrayList<>();
    	
    	entityManager.createNamedQuery("Participant.findUserParticipation",Participant.class)
    		.setParameter("user", user)
    		.getResultList()
    		.stream()
    		.sorted(Comparator.comparing(Participant::getConversationDate))
    		.forEach((p)->{
    			Conversation conv = p.getConversation();
    			List<Message> messageConv = getMessage(conv);
    			List<Participant> participants = getParticipants(conv.getIdconv(),userId);
    			conv.setMessages(messageConv);
    			conv.setParticipants(participants);
    			convo.add(conv);
    		});
    	;
    	
    	return convo;
    }
    // Retrieves all message from a specific conversation
    public List<Message> getMessage(Conversation conv){
    	List<Message> mess = new ArrayList<>();
    	entityManager.createNamedQuery("Message.findConvMessage", Message.class)
    		.setParameter("conv", conv)
    		.getResultList()
    		.stream().sorted(Comparator.comparing(Message::getCreatedat))
    		.forEach((m)-> mess.add(m));
    		
    	return mess;
    }
    /**
     * Create a conversation with a specific user;
     * @param conv
     * @return
     */
    public String createConvo(Conversation conv,User otherUser) {
    	String newId = GenerateId.DoGenerate();
    	Conversation isTaken = entityManager.find(Conversation.class, newId);
    	if(isTaken!=null)
    		 return createConvo(conv,otherUser);
    	// insert the conv in the database
    	conv.setIdconv(newId);
    	boolean created = false;
    	try {
    		transaction.begin();
    		entityManager.persist(conv);
    		transaction.commit();
    		created = true;
    	}catch(Exception e) {
    		transaction.rollback();
    	}
    	// ad the other user to this conversation
    	conv = entityManager.find(Conversation.class, newId);
    	if(created) {
    		System.out.println("joining");
    		this.joinConvo(conv, otherUser);
    		this.joinConvo(conv, conv.getUser());
    	}
    	return newId;
    	
    }
    public String insertNewMessage(Message mess) {
    	String newId = GenerateId.DoGenerate();
    	Message isTaken = entityManager.find(Message.class, newId);
    	if(isTaken!=null)
    		return insertNewMessage(mess);
    	//
    	mess.setIdmes(newId);
    	try {
    		transaction.begin();
    		entityManager.persist(mess);
    		transaction.commit();
    	}catch(Exception e) {
    		transaction.rollback();
    		e.printStackTrace();
    	}
    	return newId;
    }
    
    private boolean hasSameMembers(ArrayList<String> membersIds,Conversation c) {
    	boolean found = false; 
    	
    	for(Participant p: getParticipants(c.getIdconv(),null) ) {
    		System.out.print("Particpant : "+p.getUser().getIduser());
    		for(String id:membersIds) {
    			if(p.getUser().getIduser().equals(id)) {
    				System.out.println(" is found");
    				found=true;
    				break;
    			}
        	}
    		if(!found) {
    			System.out.println(" is not found");
    			return false;
    		}
    	}
    	return true;
    	
    }
 
    /**
     * Retrives all Participant from 
     * @return 
     * */
    public List<Participant> getParticipants(String convId,String except){
    	Conversation c = entityManager.find(Conversation.class, convId);
    	List<Participant> part = new ArrayList<>();
    	entityManager.createNamedQuery("Participant.findConvParticipants",Participant.class)
    	.setParameter("conv", c)
    	.getResultList()
    	.forEach((p)-> {
    		if(!p.getUser().getIduser().equals(except)) {
    			part.add(p);	
    		}
    	});
    	return part;
    }
    public void addParticipantInConvo(Conversation conv,User us) {
    	String newId = GenerateId.DoGenerate();
    	Participant taken = entityManager.find(Participant.class,newId);
    	if(taken!=null) {
    		addParticipantInConvo(conv,us);
    		return;
    	}
    	taken = new Participant();
    	taken.setIdpart(newId);
    }
    
    public Conversation getConversationWithMembers(String creator,String type,String title,ArrayList<String> membersIds) {
    	List<Conversation> conversations = entityManager.createNamedQuery("Conversation.findConvByType",Conversation.class).setParameter("type", type).getResultList();
    	List<Participant> participants;
    	
    	for(Conversation c: conversations) {
    		participants = getParticipants(c.getIdconv(),creator);
    		if(hasSameMembers(membersIds,c)) {
    			return c;
    		}
    	}
    	return null;
    
    }
    
    public Conversation createConversation(String creatorId,String type,String title,ArrayList<String> membersIds ) {
    	Conversation createdConversation = getConversationWithMembers(creatorId,type,title,membersIds);
    	if(createdConversation!=null) {
    		return createdConversation;
    	}
    	String newId = GenerateId.DoGenerate();
    	createdConversation = entityManager.find(Conversation.class, newId);
    	if(createdConversation!=null) {
    		return createConversation(creatorId,type,title,membersIds);
    		
    	}
    	ArrayList<User> members = new ArrayList<User>(); 
    	User creator = entityManager.find(User.class, creatorId);
    	
    	createdConversation = new Conversation();
    	createdConversation.setIdconv(newId);
    	createdConversation.setUser(creator);
    	createdConversation.setTypeconv(type);
    	createdConversation.setTitle(title);
    	createdConversation.setCreatedat(new Date() );
    	for(String memberId:membersIds) {
    		members.add(entityManager.find(User.class, memberId));
    	}
    	createdConversation.setCreatedat(new Date());
    	try {
    		transaction.begin();
    		entityManager.merge(createdConversation);
    		entityManager.persist(createdConversation);
    		transaction.commit();
    	}catch(Exception e) {
    		e.printStackTrace();
    		transaction.rollback();
    		
    	}
    	for(User m:members) {
    		joinConvo(createdConversation,m);
    	}
    	return createdConversation;
    }
    public void joinConvo(Conversation conv,User user) {
    	String newId = GenerateId.DoGenerate();
    	Participant taken = entityManager.find(Participant.class,newId);
    	if(taken!=null) {
    		joinConvo(conv,user);
    		return;
    	}
    	taken = new Participant();
    	taken.setIdpart(newId);
    	taken.setConversation(conv);
    	Date now = new Date();
    	
    	taken.setUser(user);
    	taken.setCreatedat(now);
    	
    	try {
    		transaction.begin();
    		entityManager.merge(taken);
    		entityManager.persist(taken);
    		transaction.commit();
    	}catch(Exception e) {
    		transaction.rollback();
    		System.out.println("erreur");
    	}
    }
    public void close()
   {
      
   } 
   // conversation must exist
   public Message addMessageInConversation(String convId,String senderId,String content,String type) {
	   
	   String messId = GenerateId.DoGenerate();
	   Message m = entityManager.find(Message.class,messId);
	   if(m!= null) {
		   return addMessageInConversation(convId,senderId,content,type);
	   }
	   Conversation conv = entityManager.find(Conversation.class, convId);
	   m = new Message();
	   m.setIdmes(messId);
	   Date now = new Date();
	   m.setCreatedat(now);
	   m.setMessagetype(type);
	   User sender = entityManager.find(User.class, senderId);
	   m.setConversation(conv);
	   m.setUser(sender);
	   if(type.equals("img")||type.equals("file")) {
		   String[] toks = content.split("\\.");
		   String name=toks[0];
		   content = name+"-"+now.getTime()+"."+toks[1];
	   }
	   m.setMessagecontent(content);
	   try {
		   transaction.begin();
		   entityManager.persist(m);
		   transaction.commit();
	   }catch(Exception e) {
		   e.printStackTrace();
		   transaction.rollback();
	   }
	   return m;
   }
   /**
    * 
    * @param email
    * @param password
    * @return return null when the credentials are incorrect
    */
   public User AuthenticatedUser(String email,String password) {
	   try {
		   User authUser = entityManager.createNamedQuery("User.findByEmail",User.class)
				   .setParameter("email", email).getSingleResult();   
		   if(authUser.getPassworduser()==null) {
			   System.out.println("Error");
			   return null;
		   }
		   User userTosend=null;
		   if(BCrypt.checkpw(password,  authUser.getPassworduser())) {
			    userTosend= authUser.clone();
			   
		   }
		   return userTosend;
	   }catch(javax.persistence.NoResultException e) {
		   return null;
	   }
	   
	   
   }
   /**
    * Create a new conversation and add a new message in that conversation
    * @param senderId
    * @param content
    * @param typeContent
    * @param participants
    * @param title
    * @return
    */
   public String createConversationAndAddMessage(String senderId,String content,String typeContent,JsonArray participants,String title) {
	   String newConId = GenerateId.DoGenerate();
	   Conversation c = entityManager.find(Conversation.class, newConId);
	   if(c!=null) {
		   return createConversationAndAddMessage(senderId,content,typeContent,participants,title);
	   }
	   c = new Conversation();
	   c.setIdconv(newConId);
	   User sender= entityManager.find(User.class, senderId);
	   c.setUser(sender);
	   Date now = new Date();
	   c.setCreatedat(now);
	   
	   c.setTitle(title);
	   
	   String typeConv = "single";
	   if(participants.size() != 2) {
		   typeConv = "group";
	   }
	   c.setTypeconv(typeConv);
	   // add conersation in database
	   try {
		   transaction.begin();
		   entityManager.persist(c);
		   transaction.commit();
		   
		   
	   }catch(Exception e) {
		   transaction.rollback();
	   }
	   // now that the conv is created, let's add the message in it.
	   // all participants should join that conversation
	   c = entityManager.find(Conversation.class, newConId); // the newly created conversation
	   for(JsonElement userId:participants) {
		   if( ! userId.isJsonNull()) {
			   String id = userId.getAsString();
			   User u = entityManager.find(User.class, id);
			   joinConvo(c, u);
		   }
	   }
	   // add message in that conversation.
	   addMessageInConversation(newConId, senderId, content, typeContent);
	   return newConId;
   }
   
  
  
}
