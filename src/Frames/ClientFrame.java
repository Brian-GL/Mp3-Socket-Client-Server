/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import java.awt.Color;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Gaytan Lomeli Brian Humberto
 */

/**The client frame of this homework*/
public class ClientFrame extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2514871321452020815L;
	/**The server socket.*/
    ServerSocket serverSocket;
    /**The client socket*/
    Socket clientSocket;
    /**The send thread to send the data to the server.*/
    Send sendThread;
    /**The server frame*/
    ServerFrame serverFrame;
    /**The path mp3 files to play.*/
    String mp3Path;

    /**
     * Creates new form ClientFrame
     */
    public ClientFrame() {
        initComponents();
        
        serverFrame = new ServerFrame();
        serverFrame.setVisible(true);
        
        this.getContentPane().setBackground(new Color(32,38,34));
        /**Starts the start connection thread.*/
        StartConnection startConnection = new StartConnection();
        startConnection.start();
        mp3Path = "";
        
    }

    /**Send Thread.*/
    class Send extends Thread{
        /**The output objectoutputstream.*/
        ObjectOutputStream output;
        /**The socket connection of this thread.*/
        Socket connection;
        
        /**Creates a new Send Thread from a socket.*/
        public Send(Socket socket) { connection = socket; }
        
        /**Sends the data using a message and, of course, the output ObjectOutputStream */
        public void sendData(String message){
            try {
                jTextAreaInformation.append("Send Request To Server: \""+message+"\"\n");
                output.writeObject(message);
                output.flush();
            } catch (IOException ex) {
               jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }
        }
        
        @Override
        public void run(){
            try {
                output = new ObjectOutputStream(connection.getOutputStream());
                output.flush();
            } catch (IOException ex) {
               jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }
        }
    }
    
    /**The start connection thread to start all the connections between client and server.*/
    class StartConnection extends Thread{
 
        @Override
        public void run(){
            
            try {
                jTextAreaInformation.append("Finding The Server...\n");
                clientSocket = new Socket("127.0.0.1",1998);
                jTextAreaInformation.append("Connected To: "+clientSocket.getInetAddress().getHostName()+"\n");
                
                Receive receiveThread = new Receive(clientSocket);
                receiveThread.start();
                sendThread = new Send(clientSocket);
                sendThread.start();
                
            } catch (IOException ex) {
                jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }
                
        }
        
    }
    
    /**The receive thread to receive info.*/
    class Receive extends Thread{
        
        String message;
        ObjectInputStream input;
        Socket connection;
        
        public Receive(Socket socket){connection = socket; message = "";}
        
        @Override
        public void run(){
            try {
                input = new ObjectInputStream(connection.getInputStream());
            } catch (IOException ex) {
                jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }
            
            while(!message.equals("END")) {

	            try {
	                message = (String) input.readObject();
                        if(mp3Path.isEmpty()){
                            jButtonPlayMusic.setEnabled(true);
                            jButtonPlayMusic.setForeground(Color.WHITE);
                            //jTextAreaInformation.append("Enabled\n");
                        }
                        mp3Path = message;
	                jTextAreaInformation.append("Server Says: \"You Should Play "+message+"\"\n");
                        String realName = message.substring(18);
	                jTextAreaInformation.append("So, Let's Define "+realName+" To The MP3 To Play!\n");
                        jButtonPlayMusic.setText("Play "+realName);
                       
	                
	            } catch (SocketException | EOFException ex){
	                jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
	                break;
	            }
	            catch(IOException | ClassNotFoundException ex){
	                jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
	                break;
	            }
            }
           
            try{
                input.close();
                connection.close();
                System.exit(0);
            }
            catch(IOException ex){
                 jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }

        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxOptions = new javax.swing.JComboBox<>();
        jLabelWhatToListenTo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaInformation = new javax.swing.JTextArea();
        jLabelInformation = new javax.swing.JLabel();
        jButtonSendRequest = new javax.swing.JButton();
        jButtonPlayMusic = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Client");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jComboBoxOptions.setBackground(new java.awt.Color(51, 51, 51));
        jComboBoxOptions.setFont(new java.awt.Font("FreeSans", 0, 24)); // NOI18N
        jComboBoxOptions.setForeground(new java.awt.Color(204, 204, 204));
        jComboBoxOptions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I Wanna Listen To Some Pop Music", "I Wanna Listen To Some R&B/Soul Music", "I Wanna Listen To Some Electronic Music", "I Wanna Listen To Some 80's Music", "I Wanna Listen To Some Reggaeton Music", "I Wanna Listen To Some Alternative Music", "I Wanna Listen To Some Cumbia Music", "I Wanna Listen To Some Banda Music", "I Wanna Listen To Some Hip-Hop Music", "I Wanna Listen To Some Rock Music", "I Wanna Listen To Some Metal Music", "I Wanna Listen To Some Country Music", "I Wanna Listen To Some Reggae Music", "I Wanna Listen To Some K-Pop Music" }));
        jComboBoxOptions.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabelWhatToListenTo.setFont(new java.awt.Font("Liberation Sans Narrow", 0, 18)); // NOI18N
        jLabelWhatToListenTo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelWhatToListenTo.setText("What You Wanna Listen To?");

        jTextAreaInformation.setEditable(false);
        jTextAreaInformation.setBackground(new java.awt.Color(51, 51, 51));
        jTextAreaInformation.setColumns(20);
        jTextAreaInformation.setFont(new java.awt.Font("AnjaliOldLipi", 0, 14)); // NOI18N
        jTextAreaInformation.setForeground(new java.awt.Color(204, 255, 255));
        jTextAreaInformation.setRows(5);
        jTextAreaInformation.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(jTextAreaInformation);

        jLabelInformation.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabelInformation.setForeground(new java.awt.Color(255, 255, 255));
        jLabelInformation.setText("Information");

        jButtonSendRequest.setBackground(new java.awt.Color(51, 51, 51));
        jButtonSendRequest.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jButtonSendRequest.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSendRequest.setText("Send Request");
        jButtonSendRequest.setToolTipText("Send Request Button - Tap To Send Request");
        jButtonSendRequest.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSendRequest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSendRequestMouseClicked(evt);
            }
        });

        jButtonPlayMusic.setBackground(new java.awt.Color(51, 51, 51));
        jButtonPlayMusic.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jButtonPlayMusic.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPlayMusic.setText("Play The Music");
        jButtonPlayMusic.setToolTipText("Opens the Music Player to play the selected mp3");
        jButtonPlayMusic.setActionCommand("Play The Music");
        jButtonPlayMusic.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButtonPlayMusic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPlayMusic.setEnabled(false);
        jButtonPlayMusic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonPlayMusicMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxOptions, 0, 648, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonPlayMusic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSendRequest))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelWhatToListenTo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelWhatToListenTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelInformation)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonSendRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPlayMusic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSendRequestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSendRequestMouseClicked
        // TODO add your handling code here:
        //Send the request to the server.
        sendThread.sendData((String) jComboBoxOptions.getSelectedItem());
         
    }//GEN-LAST:event_jButtonSendRequestMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void jButtonPlayMusicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonPlayMusicMouseClicked
        // TODO add your handling code here:
        
        MusicPlayer mp3Player = new MusicPlayer(mp3Path);
        mp3Player.setVisible(true);
        
        //Close server  and client Frame:
        
        serverFrame.setVisible(false);
        this.setVisible(false);
        
        serverFrame.dispose();
        this.dispose();
    
        
    }//GEN-LAST:event_jButtonPlayMusicMouseClicked

    /**
     * @param args the command line arguments
     */
    
   
    public static void main(String args[]){

        var clientFrame = new ClientFrame();
        clientFrame.setVisible(true);
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPlayMusic;
    private javax.swing.JButton jButtonSendRequest;
    private javax.swing.JComboBox<String> jComboBoxOptions;
    private javax.swing.JLabel jLabelInformation;
    private javax.swing.JLabel jLabelWhatToListenTo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaInformation;
    // End of variables declaration//GEN-END:variables
}
