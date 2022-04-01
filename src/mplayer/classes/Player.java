package mplayer.classes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane // Player class extend BorderPane
// in order to divide the media
// player into regions
{
    Media media;
    public MediaPlayer mediaPlayer;
    Pane mpane;
    public MediaBar bar;
    public Player(String file)
    { // Default constructor
            media = new Media(file);
            mediaPlayer = new MediaPlayer(media);
            mpane = new Pane();

            // inorder to add the view
            setCenter(mpane);
            bar = new MediaBar(mediaPlayer); // Passing the player to MediaBar
            setBottom(bar); // Setting the MediaBar at bottom
            setStyle("-fx-background-color:#7B68EE"); // Adding color to the mediabar
    }
}