package mplayer.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
//Для того, чтобы работали треки:   

import java.util.List;

import javafx.fxml.Initializable;

import javafx.scene.layout.AnchorPane;

import javafx.scene.text.Text;
import mplayer.classes.Database;

import mplayer.classes.Track;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import javafx.scene.paint.Color;
import mplayer.classes.TrackPlayer;

public class TrackController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button Info1, Info2, Info3, Info4;
    
    @FXML
    private AnchorPane MainAnchor;
    
    @FXML
    private AnchorPane PlayerAnchor;
    
    private List<Button> control_buttons;
    
    int count2=1;
    public void PlayTrack2(ActionEvent event)  {
            if (count2 ==1){ //Конструкция для того, чтобы не сливались поток одного и того же трека
            count2++;
//            Track track = new Track("C:/Users/alash/Documents/NetBeansProjects/MPlayer/src/mplayer/tracks/Rasco_Situations.wav");
//            track.play();
            
            }
    }
//    public void Pause2 (ActionEvent event){
////            track.pause();
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database db = new Database();

        List<Track> tracks = db.getTracks();
        
        TrackPlayer trackPlayer = new TrackPlayer(PlayerAnchor);
        
        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);

            Button play_button = new Button("Play");
            play_button.setStyle("-fx-background-color: #717171;");

            play_button.setLayoutX(14);
            play_button.setLayoutY(105 + (60 * i));

            play_button.setMinWidth(64);
            play_button.setMinHeight(30);
            play_button.setVisible(true);

            play_button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent arg0) {
                    try {
                        trackPlayer.play(track);
//                        if (trackPlayer.currentTrack.player.bar.getStatus() == Status.PLAYING)
//                        {
//                            play_button.setText("Pause");
//                        }
//                        else
//                        {
//                            play_button.setText("Play");
//                        }
                    } catch (Exception e) {System.out.println("Ошибка кнопок запуска и паузы"); }
                }
            });
            
            Text title_text = new Text();
            title_text.setText(track.title);
            title_text.setLayoutX(116);
            title_text.setLayoutY(124 + (60 * i));
            title_text.setFill(Color.WHITE);
            
            Text author_text = new Text();
            author_text.setText(track.author);
            author_text.setLayoutX(374);
            author_text.setLayoutY(124 + (60 * i));
            author_text.setFill(Color.WHITE);
            
            MainAnchor.getChildren().add(play_button);
            MainAnchor.getChildren().add(title_text);
            MainAnchor.getChildren().add(author_text);
        }
    }
}