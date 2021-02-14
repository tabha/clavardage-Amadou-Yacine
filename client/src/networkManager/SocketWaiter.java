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
import java.net.ServerSocket;
import java.net.Socket;

public class SocketWaiter extends Thread {
	private ServerSocket tcpServerSocket;
	private Controller controller;
	/**
	 * Crée un ServerSocketWaiter 
	 * @param tcpServerSocket ServerSocket à utiliser
	 * @param controller Controlleur de l'application
	 */
	public SocketWaiter(ServerSocket tcpServerSocket, Controller controller) {
            super("SocketWaiter");
            this.tcpServerSocket = tcpServerSocket;
            this.controller = controller;
	}
	
	/**
	 * Thread qui gère la connexion avec des nouveaux utilisateurs
	 */
	@Override
	public void run() {
            Socket tcpSocket;
            try {
                // Cette boucle permet d'être toujours en écoute même après une première connexion
                while(true) {
                    // On attent que quelqu'un se connecte
                    System.out.println(this.controller.getUser().getUserName()+" Controller waiting connection...");
                    tcpSocket = tcpServerSocket.accept();
                    
                    SocketReader socketReaderInput = new SocketReader("ServerSocketRead"+this.controller.getUser().getUserName(), tcpSocket, controller);
                    socketReaderInput.start();
                    
                }
            } catch (IOException e) {
                    //TODO show error GUI
                    //	GUI.showError("Impossible de recevoir les connexions.");
            }
	}
}
