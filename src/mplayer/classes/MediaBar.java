package mplayer.classes;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

        public class MediaBar extends HBox { // MediaBar extends Horizontal Box

                // introducing Sliders
                Slider time = new Slider(); // Slider for time
                Slider vol = new Slider(); // Slider for volume
                Button PlayButton = new Button(">"); // For pausing the mediaPlayer
                Label volume = new Label("Volume: ");
                MediaPlayer mediaPlayer;
                
                public Status getStatus()
                {
                    Status status = mediaPlayer.getStatus();
                    return status;
                }
                
                public void play()
                {
                    mediaPlayer.play();
                    PlayButton.setText("||");
                }
                public void pause()
                {
                    mediaPlayer.pause();
                    PlayButton.setText(">");
                }
                
                public void stop()
                {
                    mediaPlayer.stop();
                    PlayButton.setText(">");
                }

                public MediaBar(MediaPlayer play)
                { // Default constructor taking
                        // the MediaPlayer object
                        mediaPlayer = play;

                        setAlignment(Pos.CENTER); // setting the HBox to center
                        setPadding(new Insets(5, 10, 5, 10));
                        // Settih the preference for volume bar
                        vol.setPrefWidth(70);
                        vol.setMinWidth(30);
                        vol.setValue(100);
                        HBox.setHgrow(time, Priority.ALWAYS);
                        PlayButton.setPrefWidth(30);

                        // Adding the components to the bottom

                        getChildren().add(PlayButton); // Playbutton
                        getChildren().add(time); // time slider
                        getChildren().add(volume); // volume slider
                        getChildren().add(vol);

                        // Adding Functionality
                        // to play the media mediaPlayer
                        PlayButton.setOnAction(new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent e)
                                {
                                    Status status = mediaPlayer.getStatus(); // To get the status of Player
                                    if (status == status.PLAYING) {
                                        pause();
                                    } // If the video is stopped, halted or paused
                                    if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
                                        play();
                                    }
                                }
                        });

                        // Providing functionality to time slider
                        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                                public void invalidated(Observable ov)
                                {
                                        updatesValues();
                                }
                        });

                        // Inorder to jump to the certain part of video
                        time.valueProperty().addListener(new InvalidationListener() {
                                public void invalidated(Observable ov)
                                {
                                        if (time.isPressed()) { // It would set the time
                                                // as specified by user by pressing
                                                mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(time.getValue() / 100));
                                        }
                                }
                        });

                        // providing functionality to volume slider
                        vol.valueProperty().addListener(new InvalidationListener() {
                                public void invalidated(Observable ov)
                                {
                                        if (vol.isPressed()) {
                                                mediaPlayer.setVolume(vol.getValue() / 100); // It would set the volume
                                                // as specified by user by pressing
                                        }
                                }
                        });
                }

                // Outside the constructor
                protected void updatesValues()
                {
                        Platform.runLater(new Runnable() {
                                public void run()
                                {
                                        // Updating to the new time value
                                        // This will move the slider while running your video
                                        time.setValue(mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100);
                                }
                        });
                }
        }