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
import chatUtils.ChatColors;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import networkManager.User;

public class RendererUser extends DefaultListCellRenderer implements ListCellRenderer<Object>{
    private Color colorBackGround;
    private Color colorForeground;
    public RendererUser(Color bgColor,Color fgColor){
        this.colorBackGround = bgColor;
        this.colorForeground = fgColor;
    }
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,int index,boolean isSelected,boolean cellHasFocus){
        //return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        User it;
        it = (User)value;
        this.setText(it.getUserName());
        this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-user-color-s.png")));
        if(isSelected){
            this.setBackground(this.colorBackGround);
            this.setForeground(this.colorForeground);
        }else{
            this.setBackground(list.getBackground());
            this.setForeground(ChatColors.SELECTED_ITEM_TEXT);
        }
        this.setEnabled(true);
        this.setFont(list.getFont());
        this.setIconTextGap(30);
        this.setAlignmentX(10.3f);
        this.setSize(220, 70);
        return this;
    }
}
