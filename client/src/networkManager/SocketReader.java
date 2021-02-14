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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Date;




public class SocketReader extends Thread {
	
	private Socket tcpSocket;
	private Controller controller;
	private Conversation conversation;
	
	public Conversation getConversation() {
            return conversation;
	}
	public void setConversation(Conversation conversation) {
            this.conversation = conversation;
	}
	public SocketReader() {
		
	}
	public SocketReader(String name, Socket tcpSocket, Controller controller) {
            // TODO Auto-generated constructor stub
            super(name);
            this.controller = controller;
            this.tcpSocket = tcpSocket;
            this.conversation = null;
	}
	
	private Message decodeMessageFromString(String stringData) throws ClassNotFoundException,IOException{
            byte[] data = Base64.getDecoder().decode(stringData);
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return (Message) inputStream.readObject();
	}
	
	private void decodeAndSaveFileFromString(String fileData,String savePath) throws IOException{
            byte[] data = Base64.getDecoder().decode(fileData);
            FileOutputStream outputFile = new FileOutputStream(savePath);
            outputFile.write(data);
            outputFile.close();
	}
	@Override
	public void run() {
            try {
                Message msg = null;
                BufferedReader inputDataReader = new BufferedReader(new InputStreamReader(this.tcpSocket.getInputStream()));
                String stringDataInput = inputDataReader.readLine();
                while( ! stringDataInput.isEmpty()) {
                    // Decode the message
                    msg = decodeMessageFromString(stringDataInput);
                    if(this.conversation==null) {
                        System.out.println("changing conversation");
                        this.conversation = msg.getConversation();
                        System.out.println("creating socketWriter");
                        SocketWriter socketWriterOutput = new SocketWriter("ServerSocketWriter"+this.controller.getUser().getUserName(), tcpSocket, controller, this.getConversation());
                        socketWriterOutput.start();
                        //this.controller.addOpenedConvesation(this.conversation);
                    }
                    // check if we received a file or an image
                    
                    if(msg.getTypeMsg().equals(Constantes.TEXT)){
                       System.out.println("Message reçu "+msg.getMessage()); 
                       
                       this.conversation.getMessages().add(msg);
                    }else if(msg.getTypeMsg().equals(Constantes.FILE) || msg.getTypeMsg().equals(Constantes.IMG)){
                       
                        String fileName= msg.getMessage(); // file Name
                       
                        String basePath = new File("").getAbsolutePath();
                        
                        String fileInputData = inputDataReader.readLine(); // encoded File
                        
                        String pathToSave =basePath+"/src/chatData/received/"+fileName;
                        //System.out.println(fileInputData);
                        decodeAndSaveFileFromString(fileInputData,pathToSave);
                    }
                    controller.receiveMessage(msg);
                    // Read Next Message
                    stringDataInput = inputDataReader.readLine(); // bloquant
                }
            }catch(SocketException e) {
                e.printStackTrace();
            }catch(Exception e) {
                    // TODO
                    // show error
                e.printStackTrace();
            }finally{
                    try {
                            tcpSocket.close();
                    }catch(Exception e) {
                            // TODO
                            // show error in the GUI
                    }
            }
	}
}
