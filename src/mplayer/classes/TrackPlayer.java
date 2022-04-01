package mplayer.classes;

import javafx.scene.layout.AnchorPane;

public class TrackPlayer {
    AnchorPane anchorPane;
    public Track currentTrack;
    
    public TrackPlayer(AnchorPane anchorPane)
    {
        this.anchorPane = anchorPane;
    }
    
    public void play(Track track)
    {
        if (currentTrack == null)
        {
            currentTrack = track;
            anchorPane.getChildren().add(track.player);
            
            track.player.bar.play();
        }
        else
        {
            if (currentTrack == track)
            {
                track.player.bar.pause();
            }
            else
            {
                currentTrack.player.bar.stop();

                currentTrack = track;
                anchorPane.getChildren().clear();
                anchorPane.getChildren().add(track.player);
                
                track.player.bar.play();
            }
        }
    }
}