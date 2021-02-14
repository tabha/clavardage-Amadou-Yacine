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

import chatUtils.CSS;
import chatUtils.ChatColors;
import chatUtils.ChatIcons;
import chatUtils.Constantes;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import networkManager.Controller;
import networkManager.Conversation;
import networkManager.User;

public class RendererConversation extends DefaultListCellRenderer implements ListCellRenderer<Object>{
    
    
    private Controller controller = null;
    
    public RendererConversation(Controller c){
        this.controller = c;
    }
    
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,int index,boolean isSelected,boolean cellHasFocus){
        //return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        Conversation it;
        it = (Conversation)value;
        String content="";
        String iconpath = it.getTypeconv().equals(Constantes.GROUP) ? ChatIcons.GROUP_ICON_MINI : ChatIcons.SINGLE_CONV_ICON;
        
        String icon = "<img class='right' src=" + getClass().getResource(iconpath)+ "' width='50' height='50' alt='image' />";
        String header = "<span class='header'>" + it.getTitle() + "</span>";
        String lastMessage ="Empty";
        int unreadMessages = controller.getUnreadMessagesNumber(it);
        if(unreadMessages!=0){
            lastMessage =unreadMessages+" new messages";
        } else if(!it.getMessages().isEmpty()){
            lastMessage = it.getMessages().get(it.getMessages().size()-1).getMessage();
            int size = lastMessage.length() > 10 ? 10 : lastMessage.length();
            lastMessage = lastMessage.substring(0, size);
        }
        
        
        String lastText="<span class='lastMessage-sent'>"+ lastMessage+ "</span>";
        content = "<html>"
                    +CSS.STYLE
                    +"<div class='container'>"
                        +"<div class='left'>"+header+"</div>"
                        +"<div class='left'>"
                            +icon
                            +"<span class='header'>" + lastMessage + "</span>"
                        +"</div>"
                    +"</div>"
                    
                 +"</html>";
        
        this.setText(content);
        
        if(isSelected){
            this.setBackground(ChatColors.SELECTED_ITEM_COLOR);
            this.setForeground(ChatColors.SECONDARY_COLOR);
        }else{
            this.setBackground(list.getBackground());
            this.setForeground(list.getForeground());
        }
        this.setEnabled(true);
        this.setFont(list.getFont());
        //this.setIconTextGap(30);
        this.setSize(220, 60);
        return this;
    }
}
