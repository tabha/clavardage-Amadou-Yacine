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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import networkManager.Controller.ConnectionError;



import networkManager.Controller.SendConnectionError;
import static networkManager.Controller.USER_CONNECTION;



public class ServerRequestHandler extends TimerTask {
	private Controller controller;
	
	public ServerRequestHandler(Controller controller) {
            this.controller = controller;
	}
	
	@Override
	public void run() {
            Gson gsonCommand = new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy-hh:mm:ss").create();
            // create data user to send to the server

            try {
                String username= this.controller.getUser().getUserName();
                String pseudo = this.controller.getUser().getPseudo();
                String ip = this.controller.getUser().getIpAddr().getHostAddress();
                if(!Controller.testConnectionServer())
                    throw new ConnectionError();
                this.controller.sendConnectionServer(username, pseudo, ip, this.controller.getUser().getPortNum());
            }catch(IOException e) {
                    // show error GUI
                    //TODO
                    System.exit(Controller.EXIT_GET_CONNECTED_USERS);
            }catch(ConnectionError | NumberFormatException e) {
                    // shwo erro GUI

                    // TODO
                    // exit
                    System.exit(Controller.EXIT_ERROR_SERVER_UNAVAILABLE);
            }
            
    }

}
