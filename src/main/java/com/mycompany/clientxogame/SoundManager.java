package com.mycompany.clientxogame;

import javafx.scene.media.AudioClip;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final Map<String, AudioClip> sounds = new HashMap<>();

    static {
        loadSound("enter", "/sounds/enter_click.mp3");
        loadSound("back", "/sounds/back_click.mp3");
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
}