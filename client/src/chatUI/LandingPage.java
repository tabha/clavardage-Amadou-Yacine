/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatUI;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JPanel;
import networkManager.Controller;
import networkManager.User;

/**
 *
 * @author amadou
 */
public class LandingPage extends javax.swing.JFrame {

    /**
     * Creates new form LandingPage
     */
    public LandingPage(ChatContext c) {
        initComponents();
        this.activeButton = this.signInButton;
        this.context = c;
        loadView(this.signInPane);
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        signInMainPanel = new javax.swing.JPanel();
        separatorPanel = new javax.swing.JPanel();
        signInButton = new javax.swing.JButton();
        signUpButton = new javax.swing.JButton();
        signInSignUpPanel = new javax.swing.JPanel();
        signUpPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        usernameSignupField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        passwordSignupField = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        emailSignUpField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        confirmPassSignUpField = new javax.swing.JPasswordField();
        signUpButtonField = new javax.swing.JButton();
        signInPane = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        usernameSignInField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        passwordSignInField = new javax.swing.JPasswordField();
        jLabel14 = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        signInMainPanel.setBackground(new java.awt.Color(248, 249, 249));
        signInMainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        separatorPanel.setBackground(new java.awt.Color(52, 73, 94));

        javax.swing.GroupLayout separatorPanelLayout = new javax.swing.GroupLayout(separatorPanel);
        separatorPanel.setLayout(separatorPanelLayout);
        separatorPanelLayout.setHorizontalGroup(
            separatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        separatorPanelLayout.setVerticalGroup(
            separatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        signInMainPanel.add(separatorPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 150, 480));

        signInButton.setBackground(new java.awt.Color(2, 105, 164));
        signInButton.setForeground(new java.awt.Color(255, 255, 255));
        signInButton.setText("Sign In");
        signInButton.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(255, 153, 153)));
        signInButton.setOpaque(true);
        signInButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInButtonMouseClicked(evt);
            }
        });
        signInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInButtonActionPerformed(evt);
            }
        });
        signInMainPanel.add(signInButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 160, 40));

        signUpButton.setBackground(new java.awt.Color(52, 73, 94));
        signUpButton.setForeground(new java.awt.Color(255, 255, 255));
        signUpButton.setText("Sign Up");
        signUpButton.setBorder(null);
        signUpButton.setOpaque(true);
        signUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpButtonActionPerformed(evt);
            }
        });
        signInMainPanel.add(signUpButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 160, 40));

        signInSignUpPanel.setBackground(new java.awt.Color(248, 249, 249));

        signUpPanel.setBackground(new java.awt.Color(248, 249, 249));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-user.png"))); // NOI18N

        usernameSignupField.setBackground(new java.awt.Color(248, 249, 249));
        usernameSignupField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameSignupField.setToolTipText("");
        usernameSignupField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(2, 105, 164)));
        usernameSignupField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameSignupFieldActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-lock_2.png"))); // NOI18N

        passwordSignupField.setBackground(new java.awt.Color(248, 249, 249));
        passwordSignupField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordSignupField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(2, 105, 164)));

        jLabel3.setFont(new java.awt.Font(".SF NS Text", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(2, 105, 164));
        jLabel3.setText("Sign Up");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-email.png"))); // NOI18N

        emailSignUpField.setBackground(new java.awt.Color(248, 249, 249));
        emailSignUpField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        emailSignUpField.setToolTipText("");
        emailSignUpField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(2, 105, 164)));
        emailSignUpField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailSignUpFieldActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-lock_2.png"))); // NOI18N

        confirmPassSignUpField.setBackground(new java.awt.Color(248, 249, 249));
        confirmPassSignUpField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confirmPassSignUpField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(2, 105, 164)));

        signUpButtonField.setBackground(new java.awt.Color(52, 152, 219));
        signUpButtonField.setForeground(new java.awt.Color(255, 255, 255));
        signUpButtonField.setText("Sign Up");
        signUpButtonField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(56, 102, 134)));
        signUpButtonField.setOpaque(true);
        signUpButtonField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpButtonFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout signUpPanelLayout = new javax.swing.GroupLayout(signUpPanel);
        signUpPanel.setLayout(signUpPanelLayout);
        signUpPanelLayout.setHorizontalGroup(
            signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signUpPanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(signUpButtonField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(confirmPassSignUpField)
                    .addComponent(passwordSignupField)
                    .addComponent(emailSignUpField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameSignupField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, signUpPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        signUpPanelLayout.setVerticalGroup(
            signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signUpPanelLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(usernameSignupField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signUpPanelLayout.createSequentialGroup()
                        .addGroup(signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(emailSignUpField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(signUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordSignupField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(confirmPassSignUpField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(28, 28, 28)
                .addComponent(signUpButtonField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        signInPane.setBackground(new java.awt.Color(248, 249, 249));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-user.png"))); // NOI18N

        usernameSignInField.setBackground(new java.awt.Color(248, 249, 249));
        usernameSignInField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameSignInField.setToolTipText("");
        usernameSignInField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(2, 105, 164)));
        usernameSignInField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameSignInFieldActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-lock_2.png"))); // NOI18N

        passwordSignInField.setBackground(new java.awt.Color(248, 249, 249));
        passwordSignInField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordSignInField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(2, 105, 164)));

        jLabel14.setFont(new java.awt.Font(".SF NS Text", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(2, 105, 164));
        jLabel14.setText("Sign In");

        loginButton.setBackground(new java.awt.Color(52, 152, 219));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Login");
        loginButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(56, 102, 134)));
        loginButton.setOpaque(true);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout signInPaneLayout = new javax.swing.GroupLayout(signInPane);
        signInPane.setLayout(signInPaneLayout);
        signInPaneLayout.setHorizontalGroup(
            signInPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signInPaneLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(signInPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(signInPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(loginButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordSignInField)
                    .addComponent(usernameSignInField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, signInPaneLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        signInPaneLayout.setVerticalGroup(
            signInPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signInPaneLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addGroup(signInPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(usernameSignInField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(signInPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(passwordSignInField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(84, 84, 84)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout signInSignUpPanelLayout = new javax.swing.GroupLayout(signInSignUpPanel);
        signInSignUpPanel.setLayout(signInSignUpPanelLayout);
        signInSignUpPanelLayout.setHorizontalGroup(
            signInSignUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
            .addGroup(signInSignUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(signInSignUpPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(signUpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(signInSignUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signInSignUpPanelLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(signInPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        signInSignUpPanelLayout.setVerticalGroup(
            signInSignUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
            .addGroup(signInSignUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(signInSignUpPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(signUpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(signInSignUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signInSignUpPanelLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(signInPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        signInMainPanel.add(signInSignUpPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 370, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(signInMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(signInMainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 474, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signUpButtonActionPerformed
        navigateTo(this.signUpButton);
        loadView(this.signUpPanel);
    }//GEN-LAST:event_signUpButtonActionPerformed

    private void signInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signInButtonActionPerformed
        // TODO add your handling code here:
        navigateTo(this.signInButton);
        loadView(this.signInPane);
    }//GEN-LAST:event_signInButtonActionPerformed

    private void signInButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInButtonMouseClicked
        
    }//GEN-LAST:event_signInButtonMouseClicked

    private void usernameSignupFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameSignupFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameSignupFieldActionPerformed

    private void emailSignUpFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailSignUpFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailSignUpFieldActionPerformed

    private void usernameSignInFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameSignInFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameSignInFieldActionPerformed

    private boolean isValidFormatCred(){
        return  ( !this.usernameSignInField.getText().trim().isEmpty() && this.passwordSignInField.getPassword().length!=0 ) ;
    }
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        // login
        if(!isValidFormatCred()){
            showError("Invalid format input");
            
            return;
        }
        String email = this.usernameSignInField.getText().trim();
        String password = new String(this.passwordSignInField.getPassword()).trim();
               
        try{
            User authUser = Controller.Authenticated(email, password);
            if(authUser==null){
                showError("Authentication failed");
                return;
            }
            Controller c = new  Controller();    
            this.setVisible(false);
            context.setAuthenticated(authUser);
            PseudoNameForm pseudoForm = new PseudoNameForm(context,c);
            pseudoForm.setLocation(this.getX(), this.getY());
            pseudoForm.setVisible(true);
        }catch(Exception e){
            showError(e.getMessage());
        }
       
        
    }//GEN-LAST:event_loginButtonActionPerformed
    public static boolean isValidEmail(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    }
    private boolean isValidFormatSignUpFields(){
        String password = new String(this.passwordSignupField.getPassword());
        String confirmPass = new String(this.confirmPassSignUpField.getPassword());
        return (
                !this.usernameSignupField.getText().trim().isEmpty() &&
                isValidEmail(this.emailSignUpField.getText().trim()) &&
                !password.isEmpty() &&
                password.equals(confirmPass)
                );
    }
    private void signUpButtonFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signUpButtonFieldActionPerformed
        // TODO add your handling code here:
        if(!isValidFormatSignUpFields()){
            showError("Invalid format input");
            
            return;
        }
        String email,username;
        email = this.emailSignUpField.getText().trim();
        username = this.usernameSignupField.getText().trim();
        String password = new String(this.passwordSignupField.getPassword());
        try {
            StringBuilder error = new StringBuilder();
            boolean isSignedUp= Controller.signUpUser(username, email, password,error);
            if(isSignedUp){
                signInButtonActionPerformed(evt);
            }else{
                 showError("Invalid format input "+error.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_signUpButtonFieldActionPerformed
    private void loadView(JPanel view){
       this.signInSignUpPanel.removeAll();
       this.signInSignUpPanel.repaint();
       this.signInSignUpPanel.revalidate();
       
       this.signInSignUpPanel.add(view);
       this.signInSignUpPanel.repaint();
       this.signInSignUpPanel.revalidate();

    }
    private void showError(String messageError){
        System.out.println("Error :"+messageError);
    }
   
    private void navigateTo(JButton to){
        if(this.activeButton==to){
            return;
        }
        this.activeButton.setBackground(new java.awt.Color(52, 73, 94));
        //this.activeButton.setForeground(new java.awt.Color(255, 255, 255));
        this.activeButton.setBorder(null);
        to.setBackground(new java.awt.Color(2, 105, 164));
       // to.setForeground(new java.awt.Color(255, 255, 255));
        to.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 4, 0, new java.awt.Color(255, 153, 153)));
        this.activeButton = to;
        
    }
    
    private JButton activeButton;
    private ChatContext context = new ChatContext();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField confirmPassSignUpField;
    private javax.swing.JTextField emailSignUpField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordSignInField;
    private javax.swing.JPasswordField passwordSignupField;
    private javax.swing.JPanel separatorPanel;
    private javax.swing.JButton signInButton;
    private javax.swing.JPanel signInMainPanel;
    private javax.swing.JPanel signInPane;
    private javax.swing.JPanel signInSignUpPanel;
    private javax.swing.JButton signUpButton;
    private javax.swing.JButton signUpButtonField;
    private javax.swing.JPanel signUpPanel;
    private javax.swing.JTextField usernameSignInField;
    private javax.swing.JTextField usernameSignupField;
    // End of variables declaration//GEN-END:variables
}
