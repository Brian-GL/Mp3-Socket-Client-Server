/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import Structures.DoubleList;
import Structures.List;
import Structures.Pair;
import jaco.mp3.player.MP3Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.images.Artwork;

/**
 *
 * @author Gaytan Lomeli Brian Humberto
 * 
 */

/**Music Player Frame to play only mp3 files.*/
public class MusicPlayer extends JFrame {

    /**
     * Creates new form MusicPlayer
     */
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5921711637820147353L;
    /**Define status for the music player (play or pause).*/
    Status status;
    /**Define MP3Player Class From JACO MP3Player Lib (useful to play mp3 files).*/
    MP3Player player;
    
    ImageIcon playIcon = new ImageIcon(getClass().getResource("/Icons/play.png"));
    ImageIcon pauseIcon = new ImageIcon(getClass().getResource("/Icons/pause.png"));

    /**Creates a new Music Player Frame
     * @param mp3Location -> the mp3 file location.
     */
    public MusicPlayer(String mp3Location){
        initComponents();
        
        // Create our mp3Player
        player = new MP3Player();
        //Create our mp3 file from mp3Location
        File songFile = new File(mp3Location);
        
    
         try{
            
            AudioFile audioFile = AudioFileIO.read(songFile);
            Tag tag = audioFile.getTag();
            String title = tag.getFirst(FieldKey.TITLE);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);

            jLabelTitle.setText(title);
            jLabelArtist.setText(artist);
            jLabelAlbum.setText(album);
            
            //Get the artwork from the mp3 file:
            Artwork artWork = tag.getFirstArtwork();
            //Create an image icon from the bytes of the artwork:
            ImageIcon imageIcon = new ImageIcon(artWork.getBinaryData());
            //Resize the imageIcon to fit in JLabelCoverArt:
            Image resultingImage = fitImage(imageIcon.getImage(),jLabelCoverArt.getWidth(),jLabelCoverArt.getHeight());
            jLabelCoverArt.setText("");
            jLabelCoverArt.setIcon(new ImageIcon(resultingImage));

            //Convert the resulting image to a buffered image just to get the main colors of the artwork.
            var bufferedImage = convertImage(resultingImage);
            //Create a double list to get the most popular colors of the artwork:
            var colors = new DoubleList<Color,Integer>();
            
            Color firstMax = Color.WHITE;
            int max = 0;
            //Visit some pixels of the artwork to get a color and see how many times that color shows in the artwork. The most popular will be the main color.
            for(int i = 0; i < bufferedImage.getWidth();i+=30){
                for(int j = 0; j < bufferedImage.getHeight();j+=30){
                    Color color = new Color(bufferedImage.getRGB(i,j));
                    
                    List<Pair<Color,Integer>>.Node node = colors.GetNodeFromFirst(color);
                    
                    if(node == null){
                        //It does not exist
                        colors.AddToBack(color, 0);
                    }
                    else{
                        //It does exist
                        node.Value().Second(node.Value().Second()+1);
                        int count = node.Value().Second();
                        
                        if(count > max){
                            firstMax = node.Value().First();
                            max = count;
                        }
                        
                    }
                    
                }
                
            }
            
            //Get another random color from the artwork:
            
            Color secondMax = firstMax;
            int differenceRed = Math.abs(firstMax.getRed() - secondMax.getRed());
            int differenceBlue = Math.abs(firstMax.getBlue() - secondMax.getBlue());
            int differenceGreen = Math.abs(firstMax.getGreen()- secondMax.getGreen());
            int rounds = 0;
            while(differenceRed < 25 || differenceGreen < 25 || differenceBlue < 25){
                
                if(rounds < colors.Size()){
                    secondMax = colors.GetAt(new Random(System.currentTimeMillis()).nextInt(colors.Size()-2)+2).First();
                    differenceRed = Math.abs(firstMax.getRed() - secondMax.getRed());
                    differenceBlue = Math.abs(firstMax.getBlue() - secondMax.getBlue());
                    differenceGreen = Math.abs(firstMax.getGreen()- secondMax.getGreen());
                }
                
                if(rounds >= colors.Size()){
                    Random r = new Random(System.currentTimeMillis());
                    secondMax = new Color(r.nextInt(255-1)+1,r.nextInt(255-1)+1,r.nextInt(255-1)+1);
                    differenceRed = Math.abs(firstMax.getRed() - secondMax.getRed());
                    differenceBlue = Math.abs(firstMax.getBlue() - secondMax.getBlue());
                    differenceGreen = Math.abs(firstMax.getGreen()- secondMax.getGreen());
                }
           
                rounds++;
            }
            //Apply that colors to our components.
            jLabelTitle.setForeground(secondMax);
            jLabelAlbum.setForeground(secondMax);
            jLabelArtist.setForeground(secondMax);
            jButtonPlayPause.setBackground(secondMax);
           
            getContentPane().setBackground(firstMax);
            
            //set the button icon to pause:
            jButtonPlayPause.setIcon(pauseIcon); // NOI18N
            //When we created this frame, the player will start, so, the initial status will be play:
            status = Status.PLAY;
            //add songFile to player's playlist:
            player.addToPlayList(songFile);
            //Start to play the mp3 file:
            player.play();
            System.out.println("Ready to play: "+mp3Location);
            System.out.println("Wait For It. Be Patient.");
        
        }
        catch(IOException | CannotReadException | InvalidAudioFrameException | ReadOnlyFileException | TagException e){
            System.out.println("Error: "+e.getMessage());
        }

    }

    /**Music player enum status*/
    enum Status{
        PLAY,
        PAUSE
    }
    
    @Override
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Icons/player.png"));
        return retValue;
    }

    /**
    * Converts a given image into a BufferedImage
    *
    * @param img The Image to be converted
    * @return The converted BufferedImage
    */
    BufferedImage convertImage(Image img){
      
       // Create a buffered image with transparency
       BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

       // Draw the image on to the buffered image
       Graphics2D bGr = bImage.createGraphics();
       bGr.drawImage(img, 0, 0, null);
       bGr.dispose();

       // Return the buffered image
       return bImage;
   }
    /**Resizes an image.*/
    Image fitImage(Image img , int w , int h)
    {
        BufferedImage resizedimage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedimage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0,w,h,null);
        g2.dispose();
        return resizedimage;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonPlayPause = new javax.swing.JButton();
        jLabelCoverArt = new javax.swing.JLabel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelAlbum = new javax.swing.JLabel();
        jLabelArtist = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Music Player");
        setIconImage(getIconImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jButtonPlayPause.setBackground(new Color(255,255,255,200));
        jButtonPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/play.png"))); // NOI18N
        jButtonPlayPause.setToolTipText("Play Button");
        jButtonPlayPause.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonPlayPause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonPlayPauseMouseClicked(evt);
            }
        });

        jLabelCoverArt.setText("label");
        jLabelCoverArt.setToolTipText("Cover Art");
        jLabelCoverArt.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabelCoverArt.setMaximumSize(new java.awt.Dimension(364, 370));

        jLabelTitle.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 18)); // NOI18N
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Title");
        jLabelTitle.setToolTipText("Song Title");

        jLabelAlbum.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 18)); // NOI18N
        jLabelAlbum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAlbum.setText("Album");
        jLabelAlbum.setToolTipText("Album Name");

        jLabelArtist.setFont(new java.awt.Font("DejaVu Sans Condensed", 0, 18)); // NOI18N
        jLabelArtist.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelArtist.setText("Artist");
        jLabelArtist.setToolTipText("Artist(s)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelCoverArt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelArtist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelAlbum, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
                .addGap(0, 40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonPlayPause)
                .addGap(200, 200, 200))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCoverArt, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelArtist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAlbum)
                .addGap(18, 18, 18)
                .addComponent(jButtonPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPlayPauseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonPlayPauseMouseClicked
        // TODO add your handling code here:
        switch (status) {
            case PLAY -> {
                status = Status.PAUSE;
                jButtonPlayPause.setIcon(playIcon); // NOI18N
                player.pause(); 
               
            }
            case PAUSE -> {
                status = Status.PLAY;
                jButtonPlayPause.setIcon(pauseIcon); // NOI18N
                player.play();
               
            }
        }
    }//GEN-LAST:event_jButtonPlayPauseMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        player.stop();
        player.setEnabled(false);
        player = null;
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonPlayPause;
    private javax.swing.JLabel jLabelAlbum;
    private javax.swing.JLabel jLabelArtist;
    private javax.swing.JLabel jLabelCoverArt;
    private javax.swing.JLabel jLabelTitle;
    // End of variables declaration//GEN-END:variables
}
