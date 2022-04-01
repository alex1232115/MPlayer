package mplayer.classes;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Track {
    public int id;
    public String title;
    public String author;
    public String file;

    public Boolean isPlaying;
    public Clip clip;
    
    public Player player;

    public Track(int id, String title, String author, String file) { //Конструктор
        this.id = id;
        this.title = title;
        this.author = author;
        this.file = file;
        
        this.player = new Player("file:///" + file);
        
        this.isPlaying = false;

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(this.file).getAbsoluteFile());
            this.clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setFramePosition(0);
        } 
        catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void play(){
        this.isPlaying = true;
        this.clip.start();
    }

    public void pause(){
        this.isPlaying = false;
        this.clip.stop();
    }    
}