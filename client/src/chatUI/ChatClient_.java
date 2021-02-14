/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatUI;

/**
 *
 * @author amadou
 */
public class ChatClient_ {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ChatContext context1,context2,context3;
        context1= new ChatContext();
        context2= new ChatContext();
        //context3= new ChatContext();
        new LandingPage(context1).setVisible(true);
        new LandingPage(context2).setVisible(true);
        //new LandingPage(context3).setVisible(true);
        
    }
    
}
