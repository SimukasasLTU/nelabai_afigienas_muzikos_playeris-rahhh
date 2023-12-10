package com.example.work;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MusicPlayerController {

    @FXML
    private Label currentSong;
    @FXML
    private Button playSong, stopSong, resetSong, previousSong, nextSong;
    @FXML
    private ComboBox<String> songSpeed;
    @FXML
    private Slider songVolumeSlider;
    @FXML
    private ProgressBar songProgress;

    private MusicPlayer musicPlayer;
    @FXML
    private ListView<String> songListView;

    @FXML
    public void initialize() {
        musicPlayer = new MusicPlayer();
        musicPlayer.initializePlayerComponents(currentSong, songProgress, songSpeed, songVolumeSlider);
        populateSpeedOptions();
        populateSongList();
        setupVolumeSlider();
    }

    @FXML
    public void playSong() {
        musicPlayer.playSong();
    }

    @FXML
    public void stopSong() {
        musicPlayer.stopSong();
    }

    @FXML
    public void resetSong() {
        musicPlayer.resetSong();
    }

    @FXML
    public void previousSong() {
        musicPlayer.previousSong();
    }

    @FXML
    public void nextSong() {
        musicPlayer.nextSong();
    }

    @FXML
    public void changeSpeed() {
        String speed = songSpeed.getValue();
        musicPlayer.changeSpeed(speed);
    }
    private void populateSpeedOptions() {
        songSpeed.getItems().addAll("25%", "50%", "75%", "100%", "125%", "150%", "175%", "200%");
        songSpeed.getSelectionModel().select("100%"); // Default speed
    }
    private void populateSongList() {
        List<String> songNames = musicPlayer.getSongNames();
        songListView.getItems().addAll(songNames);
        songListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            musicPlayer.playSongByName(newValue);
        });
    }
    private void setupVolumeSlider() {
        songVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicPlayer.setVolume(newValue.doubleValue());
        });
    }
}
