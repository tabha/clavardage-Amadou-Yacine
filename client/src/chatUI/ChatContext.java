 /* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatUI;

import networkManager.User;

/**
 *
 * @author amadou
 */
public class ChatContext {
    private User authUser = null;
     
    public void setAuthenticated(User user){
        this.authUser =  user;
    }
    public boolean isAuthenticated(){
        return authUser!=null;
    }
    
    public String getToken(){
        return this.authUser.getToken();
    }
    public String getUsername(){
        return this.authUser.getUserName();
    }
    public void clearContext(){
        this.authUser = null;
    }
    public void setPseudo(String p){
        authUser.setPseudo(p);
    }
    public User getAuthUser(){
        return authUser;
    }
}
