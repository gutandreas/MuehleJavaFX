package edu.andreasgut.sound;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class AudioPlayer {

    private final AudioClip audioclipMENU;
    private final AudioClip audioclipPLAY;
    private final AudioClip audioclipGAMEOVER;
    private AudioClip currentAudioclip;
    private final AudioClip audioclipPUTSTONE;
    private final AudioClip audioclipKILLSTONE;
    private final URL menuSoundPath;
    private final URL playSoundPath;
    private final URL gameoverSoundPath;
    private final URL putStonePath;
    private final URL killStonePath;
    private boolean musicOn = true;


    public AudioPlayer() {
        menuSoundPath = getClass().getResource(MUSIC.MENU_SOUND.getPath());
        playSoundPath = getClass().getResource(MUSIC.PLAY_SOUND.getPath());
        gameoverSoundPath = getClass().getResource(MUSIC.GAMEOVER_SOUND.getPath());

        putStonePath = getClass().getResource(SOUNDEFFECT.PUT_STONE.getPath());
        killStonePath = getClass().getResource(SOUNDEFFECT.KILL_STONE.getPath());


        audioclipMENU = new AudioClip(menuSoundPath.toString());
        audioclipPLAY = new AudioClip(playSoundPath.toString());
        audioclipGAMEOVER = new AudioClip(gameoverSoundPath.toString());

        audioclipPUTSTONE = new AudioClip(putStonePath.toString());
        audioclipKILLSTONE = new AudioClip(killStonePath.toString());

        audioclipMENU.setRate(1);
        audioclipMENU.setCycleCount(AudioClip.INDEFINITE);
        audioclipMENU.play();
        currentAudioclip = audioclipMENU;

        audioclipPLAY.setCycleCount(AudioClip.INDEFINITE);
        audioclipGAMEOVER.setCycleCount(AudioClip.INDEFINITE);
    }

    public void chooseSound(MUSIC musicEnum) {
        stopMusic();
        switch (musicEnum) {
            case MENU_SOUND:
                currentAudioclip = audioclipMENU;
                audioclipMENU.play();
                break;
            case PLAY_SOUND:
                currentAudioclip = audioclipPLAY;
                audioclipPLAY.play();
                break;
            case GAMEOVER_SOUND:
                currentAudioclip = audioclipGAMEOVER;
                audioclipGAMEOVER.play();
                break;
        }

        musicOn = true;
    }

    public void continueMusic() {
        currentAudioclip.play();
    }

    public void stopMusic() {
        audioclipMENU.stop();
        audioclipPLAY.stop();
        audioclipGAMEOVER.stop();
        musicOn = false;
    }

    public void playSoundEffect(SOUNDEFFECT effectEnum) {
        switch (effectEnum) {
            case PUT_STONE:
                audioclipPUTSTONE.play();
                break;
            case KILL_STONE:
                audioclipKILLSTONE.play();
                break;
        }
    }

    public AudioClip getCurrentAudioclip() {
        return currentAudioclip;
    }

    public boolean isMusicOn() {
        return musicOn;
    }
}
