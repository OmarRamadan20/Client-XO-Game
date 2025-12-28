package com.mycompany.clientxogame.sound;

import javafx.scene.media.AudioClip;
import java.util.HashMap;

public class SoundManager {

    private static SoundManager instance; // singleton
    private AudioClip backgroundMusic;
    private HashMap<String, AudioClip> buttonSounds = new HashMap<>();

    private SoundManager() {
        try {
            backgroundMusic = new AudioClip(getClass().getResource("/sounds/back_ground2.mp3").toExternalForm());
            backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
            backgroundMusic.setVolume(0.05);

            loadButtonSound("enter", "/sounds/enter_click.mp3");
            loadButtonSound("back", "/sounds/back_click.mp3");
            loadButtonSound("playClick", "/sounds/play_click.mp3");

        } catch (Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
        }
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private void loadButtonSound(String name, String path) {
        try {
            AudioClip clip = new AudioClip(getClass().getResource(path).toExternalForm());
            buttonSounds.put(name, clip);
        } catch (Exception e) {
            System.err.println("Button sound not found: " + path);
        }
    }

    public void playBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            //backgroundMusic.play();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public void playButton(String name) {
        AudioClip clip = buttonSounds.get(name);
        if (clip != null) {
            clip.play();
        }
    }
    
    

    
}
