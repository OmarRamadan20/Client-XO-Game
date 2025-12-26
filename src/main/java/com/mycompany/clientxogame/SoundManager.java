package com.mycompany.clientxogame;

import javafx.scene.media.AudioClip;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private static final Map<String, AudioClip> sounds = new HashMap<>();
    private static MediaPlayer backgroundMusic;

    static {
        loadSound("enter", "/sounds/enter_click.mp3");
        loadSound("back", "/sounds/back_click.mp3");
        loadBackgroundMusic("/sounds/back_ground2.mp3");
    }

    private static void loadSound(String name, String path) {
        try {
            AudioClip clip = new AudioClip(SoundManager.class.getResource(path).toExternalForm());
            sounds.put(name, clip);
        } catch (Exception e) {
            System.err.println("Audio Not found");
        }
    }

    public static void play(String name) {
        AudioClip clip = sounds.get(name);
        if (clip != null) {
            clip.play();
        }
    }

    private static void loadBackgroundMusic(String path) {
        try {
            Media media = new Media(SoundManager.class.getResource(path).toExternalForm());
            backgroundMusic = new MediaPlayer(media);

            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);

            backgroundMusic.setVolume(0.2);
        } catch (Exception e) {
            System.err.println("Error loading background music");
        }
    }

    public static void startBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.play();
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }
    }
}
