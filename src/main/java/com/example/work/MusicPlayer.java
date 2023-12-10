package com.example.work;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private Label currentSongLabel;
    private ProgressBar songProgressBar;
    private ComboBox<String> songSpeedComboBox;
    private Slider songVolumeSlider;
    private MediaPlayer mediaPlayer;
    private List<File> songs;
    private int currentSongIndex;
    private double currentVolume = 1.0;
    private double currentRate = 1.0;

    public MusicPlayer() {
        songs = loadSongs();
        currentSongIndex = 0;
        if (!songs.isEmpty()) {
            setupMediaPlayer();
        }
    }

    private List<File> loadSongs() {
        File directory = new File("music");
        File[] files = directory.listFiles();
        List<File> songList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    songList.add(file);
                }
            }
        }
        return songList;
    }

    public void initializePlayerComponents(Label currentSongLabel, ProgressBar songProgressBar, ComboBox<String> songSpeedComboBox, Slider songVolumeSlider) {
        this.currentSongLabel = currentSongLabel;
        this.songProgressBar = songProgressBar;
        this.songSpeedComboBox = songSpeedComboBox;
        this.songVolumeSlider = songVolumeSlider;
    }

    private void setupMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Media media = new Media(songs.get(currentSongIndex).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(currentVolume);
        mediaPlayer.setRate(currentRate);

        mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
            if (mediaPlayer.getTotalDuration().toSeconds() > 0) {
                songProgressBar.setProgress(newTime.toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
            }
        });

        mediaPlayer.setOnEndOfMedia(this::nextSong);
        updateSongLabel();
    }

    private void updateSongLabel() {
        if (currentSongLabel != null) {
            currentSongLabel.setText(songs.get(currentSongIndex).getName());
        }
    }

    public void playSong() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void stopSong() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void resetSong() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(0));
        }
    }

    public void nextSong() {
        if (songs.size() > 0) {
            currentSongIndex = (currentSongIndex + 1) % songs.size();
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            setupMediaPlayer();
            playSong();
        }
    }

    public void previousSong() {
        if (songs.size() > 0) {
            currentSongIndex = (currentSongIndex - 1 + songs.size()) % songs.size();
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            setupMediaPlayer();
            playSong();
        }
    }

    public void changeSpeed(String speed) {
        if (speed != null && !speed.isEmpty()) {
            currentRate = Integer.parseInt(speed.replace("%", "")) / 100.0;
            if (mediaPlayer != null) {
                mediaPlayer.setRate(currentRate);
            }
        }
    }

    public void setVolume(double volume) {
        currentVolume = volume / 100.0;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(currentVolume);
        }
    }

    public List<String> getSongNames() {
        List<String> songNames = new ArrayList<>();
        for (File song : songs) {
            songNames.add(song.getName());
        }
        return songNames;
    }

    public void playSongByName(String songName) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose(); // Dispose of the current MediaPlayer
        }

        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getName().equals(songName)) {
                currentSongIndex = i;
                setupMediaPlayer(); // This will create a new MediaPlayer for the new song
                playSong();
                break;
            }
        }
    }
}
