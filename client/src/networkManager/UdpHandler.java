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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class UdpHandler extends Thread {
	private static final int UDP_IDENTITY = 2005;
	private static final int NONE_STATUS = 1;
	private static final int DECONNECTION_STATUS = 2;
	private static final int CONNECTION_STATUS = 3;
	private static final int CONNECTION_RESPONSE_SATUS = 4;
	private static final int USERNAME_CHANGED_STATUS = 5;
	private static final int USERNAME_MODIFIED_OCCUPIED = 6;
	private Controller controller;
	private DatagramSocket udpSocket;
	private int portNum;
	
	public UdpHandler() {
		super("UdpHandler");
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public DatagramSocket getUdpSocket() {
		return udpSocket;
	}

	public void setUdpSocket(DatagramSocket udpSocket) {
		this.udpSocket = udpSocket;
	}

	public int getPortNum() {
		return portNum;
	}

	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}
	
	
	public byte[] createMessage(int status,User user) throws IOException{
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream);
		oo.writeInt(UDP_IDENTITY);
		oo.writeInt(status);
		oo.writeObject(user);
		oo.close();
		return bStream.toByteArray();
	}
	
	public void sendUdpMessage(byte[] message,InetAddress ipAddress) throws IOException{
		DatagramPacket out = new DatagramPacket(message,message.length,ipAddress,portNum);
		udpSocket.send(out);
		
	}
	@Override
	public void run() {
		byte[] buffer = new byte[1024];
		DatagramPacket in = new DatagramPacket(buffer,buffer.length);
		int status = NONE_STATUS;
		User receivedUser = null;
		int udpIdentity = -1;
		while(true) {
			try {
				udpSocket.receive(in);
				// Recive data
				byte[] receivedMessage = in.getData();
				ObjectInputStream iStream;
				iStream = new ObjectInputStream(new ByteArrayInputStream(receivedMessage));
				udpIdentity = (int) iStream.readInt();
				//check if we must process the packet by comparing the identity
				if(udpIdentity!=UDP_IDENTITY)
                                    continue;
				//get the Message
				status = (int) iStream.readInt();
				receivedUser = (User) iStream.readObject();
				iStream.close();
				switch(status) {
					case DECONNECTION_STATUS:
						// the controller should received the disconnected user
						//TODO
//						controller.receiveDisconnectedUser(user);
						break;
					case CONNECTION_STATUS:
						//TODO
						break;
					case CONNECTION_RESPONSE_SATUS:
						//TODO
						break;
					case USERNAME_CHANGED_STATUS:
						//TODO
						break;
					case USERNAME_MODIFIED_OCCUPIED:
						//TODO
						break;
					
				}
			}catch(StreamCorruptedException | EOFException e) {
				// nothing to do
			}catch(IOException | ClassNotFoundException e) {
				// TODO
				// show erro in the GUI
			}
		}
	}
	
}
