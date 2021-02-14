package server;

public class Constantes {
	public static final int TEST_SERVER =0;
	public static final int USER_CONNECTION = 1;
	public static final int USER_DISCONNECTION = 2;
	public static final int USER_CREATE = 3;
	public static final int USER_AUTH = 4;
	public static final int CHECK_PSEUDO = 5;
	public static final int LOGOUT = 6;
	public static final int NO_ERROR = 20;
	public static final int NO_ACTION = 21;
	
	public static final int NO_USER_DATA = 22;
	public static final int NO_SUCH_USER = 26;
	public static final int INVALID_JSON =23;
	public static final int INVALID_ENTRY =24;
	public static final int PSEUDO_TAKEN = 25;
	public static final int SAVE_DATA = 30;
	public static final int SAVE_MESSAGE = 31;
	public static final int FETCH_USER_HISTORY = 40;
	public static final int FETCH_PARTICIPANT = 41;
	public static final int DECONNECTION = 6;
	// Data type to save in the data base
	public static final int MESSAGE = 40;
	
	public static final int AUTO_DECONNECTION_DELAY = 5000;
	
	public static final int USER_NOT_AUTHENTICATED = 26;
	public static final int START_CONVERSATION = 27;
}
