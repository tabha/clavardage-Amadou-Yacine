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
import chatUtils.LocalData;
import java.io.*;
import java.net.*;
import java.util.*;


public class SocketWriter extends Thread {
	private Socket tcpSocket;
	private Controller controller;
	private Conversation conversation;
	private LocalData ld;
	public SocketWriter(String name, Socket tcpSocket,Controller c,Conversation convo) {
            super(name);
            this.tcpSocket = tcpSocket;
            this.controller = c;
            this.conversation = convo;
            
	}
	
	private static String encodeMessageToString(Message msg) throws  IOException{
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutput objOutput;
            objOutput = new ObjectOutputStream(byteOutputStream);
            objOutput.writeObject(msg);
            objOutput.close();
            return Base64.getEncoder().encodeToString(byteOutputStream.toByteArray());
	}
	private static String encodeFileToString(File file) throws IOException{
            FileInputStream receivedFileStream = new FileInputStream(file);
            byte[] inputFile = new byte[(int)file.length()];
            receivedFileStream.read(inputFile);
            receivedFileStream.close();
            return Base64.getEncoder().encodeToString(inputFile);
	}
	@Override
	public void run() {
            try {
                System.out.println(this.getName()+" Writer waiting on outputStream ");
                
                // ou recupère un flux d'ecriture sur la socket tcp
                PrintWriter outputDataWriter = new PrintWriter(tcpSocket.getOutputStream(),true);
                Message msg;
                System.out.println("Writer outputStream Received");
                // tanque la socket est ouverte 
                while( ! tcpSocket.isClosed()) {
                    msg = controller.getMessageToSend();
                    if(msg != null &&  ! msg.getMessage().isEmpty() ) {
                       
                        if(msg.getTypeMsg().equals(Constantes.STOP)) break;
                       
                        if(this.conversation.equals(msg.getConversation())){
                            
                            if(msg.getTypeMsg().equals(Constantes.TEXT)){
                                outputDataWriter.println(encodeMessageToString(msg));
                                this.controller.saveMessageInDistantServer(conversation, msg);
                            }else{// FILE OR IMG
                                // send message text with Conversation group the message content is the path to the file to send
                                // send only file Name 
                                String[] tokens = msg.getMessage().split("/");
                                File file = new File(msg.getMessage());
                                String fileName = tokens[tokens.length-1];
                                msg.setMessage(fileName);
                                // send server
                                this.controller.saveMessageInDistantServer(conversation, msg);
                                outputDataWriter.println(encodeMessageToString(msg));
                                // send the file or Image
                                outputDataWriter.println(encodeFileToString(file));
                                // save fale in local directory for next history
                                fileName = msg.getMessage();
                                LocalData.saveSentFile(file, fileName);
                                
                            }
                            
                        }
                       
                        this.controller.messageSent(msg);
                        
                    }
                }

            }catch(Exception e) {
                // show error to the GUI
                e.printStackTrace();
                // TODO
            }finally {
                if(tcpSocket != null){
                    try {
                        tcpSocket.close();
                    }catch(Exception e) {
                        // Show error to in the GUI
                        // TODO
                    }
                }
            }
	}
}

