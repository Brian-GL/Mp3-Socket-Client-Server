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
import java.util.Hashtable;

/**
 *
 * @author Gaytan Lomeli Brian Humberto
 */
public class ServerFrame extends javax.swing.JFrame {

    /**
     * Creates new form ServerFrame
     */
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3121896932432542510L;
	/**The server socket for this frame.*/
    ServerSocket serverSocket;
    /**The hash Table to get the request operation for the client.*/
    Hashtable<String,String> hashTable;
    /**The send thread to send data.*/
    Send sendThread;
    
    public ServerFrame() {
        initComponents();
        this.getContentPane().setBackground(new Color(32,38,34));
        hashTable = new Hashtable<String,String>();
        //initialize hashtable:
        hashTable.put("I Wanna Listen To Some Pop Music", "src/MP3 Resources/Midnight Sky - Miley Cyrus.mp3");
        hashTable.put("I Wanna Listen To Some R&B/Soul Music", "src/MP3 Resources/Streets - Doja Cat.mp3");
        hashTable.put("I Wanna Listen To Some Alternative Music", "src/MP3 Resources/fuckhim - Girl Ultra, Ximena Sarinana.mp3");
        hashTable.put("I Wanna Listen To Some Reggaeton Music", "src/MP3 Resources/Caramelo (Remix) - Ozuna.mp3");
        hashTable.put("I Wanna Listen To Some Reggae Music", "src/MP3 Resources/Aire - Golden Ganga.mp3");
        hashTable.put("I Wanna Listen To Some Rock Music", "src/MP3 Resources/Bring Me To Life - Evanescence.mp3");
        hashTable.put("I Wanna Listen To Some Metal Music", "src/MP3 Resources/The World Is Yours - Arch Enemy.mp3");
        hashTable.put("I Wanna Listen To Some K-Pop Music", "src/MP3 Resources/Kill This Love - BLACKPINK.mp3");
        hashTable.put("I Wanna Listen To Some Country Music", "src/MP3 Resources/Need You Now - Lady A.mp3");
        hashTable.put("I Wanna Listen To Some Banda Music", "src/MP3 Resources/Cual Adios - Banda Clave Nueva De Max Peraza.mp3");
        hashTable.put("I Wanna Listen To Some Cumbia Music", "src/MP3 Resources/Cumbia Con La Luna - Control.mp3");
        hashTable.put("I Wanna Listen To Some Hip-Hop Music", "src/MP3 Resources/Gangsta's Paradise - Coolio.mp3");
        hashTable.put("I Wanna Listen To Some Electronic Music", "src/MP3 Resources/Don't You Know - Jagsy.mp3");
        hashTable.put("I Wanna Listen To Some 80's Music", "src/MP3 Resources/Self Control - Laura Branigan.mp3");
        
        /**Creates and start the start connection thread.*/
        StartConnection startSocket = new StartConnection();
        startSocket.start();
    }
    
    /**The send thread.*/
    class Send extends Thread{
        
        ObjectOutputStream output;
        Socket connection;
            
        public Send(Socket socket){
            connection = socket;
        }
        
        void sendData(String message){
            try {
                jTextAreaInformation.append("Client Needs To Play: "+message+"\n");
                jTextAreaInformation.append("Sending Now The Answer!\n");
                output.writeObject(message);
                output.flush();
            } catch (IOException ex) {
                jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }
           
        }
        
        public void run(){
            
            try {
                output = new ObjectOutputStream(connection.getOutputStream());
                output.flush();
            } catch (IOException ex) {
               jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }
            
        }
    }

    /**The start connection thread.*/
    class StartConnection extends Thread{
        
        public void run(){
            
            try {
                serverSocket = new ServerSocket(1998);
                jTextAreaInformation.append("Waiting For A Client Request...\n");
                
                while(true){
                    try{
                    Socket connection = serverSocket.accept();
                    jTextAreaInformation.append("Connected To: "+connection.getInetAddress().getHostName()+"\n");
                    
                    sendThread = new Send(connection);
                    Receive receiveThread = new Receive(connection);
                    
                    sendThread.start();
                    receiveThread.start();
                    
                    } catch (IOException ex) {
                        jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
                    }
                }
                
            } catch (IOException ex) {
                jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }
            
            
        }
    }
    
    
    class Receive extends Thread{
        
        String message;
        ObjectInputStream input;
        Socket connection;
        
        public Receive(Socket socket){connection = socket; message = "";}
        
        public void run(){
            
            try {
                input = new ObjectInputStream(connection.getInputStream());
            } catch (IOException ex) {
                jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }

            while(!message.equals("END")){
                try {
                    
                    message = (String) input.readObject();
                    jTextAreaInformation.append("Client Says: \""+message+"\"\n");
                    //Send the operation from the hash table to the client.
                    sendThread.sendData(hashTable.get(message));
                    
                } catch (SocketException ex){
                    jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
                }
                catch(EOFException ex){
                    jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
                    break;
                }
                catch(IOException | ClassNotFoundException ex){
                    jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
                }
            }

            try{
                input.close();
                connection.close();
            }
            catch(IOException ex){
                 jTextAreaInformation.append("ERROR: "+ex.getMessage()+"\n");
            }
            
            jTextAreaInformation.append("End Connection.");
            System.exit(0);
            
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelInformation = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaInformation = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Server");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabelInformation.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabelInformation.setForeground(new java.awt.Color(255, 255, 255));
        jLabelInformation.setText("Information");

        jTextAreaInformation.setEditable(false);
        jTextAreaInformation.setBackground(new java.awt.Color(51, 51, 51));
        jTextAreaInformation.setColumns(20);
        jTextAreaInformation.setFont(new java.awt.Font("AnjaliOldLipi", 0, 14)); // NOI18N
        jTextAreaInformation.setForeground(new java.awt.Color(255, 255, 255));
        jTextAreaInformation.setRows(5);
        jTextAreaInformation.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setViewportView(jTextAreaInformation);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelInformation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowClosing

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelInformation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaInformation;
    // End of variables declaration//GEN-END:variables
}
