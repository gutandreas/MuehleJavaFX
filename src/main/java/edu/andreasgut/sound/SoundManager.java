package edu.andreasgut.sound;

import edu.andreasgut.view.ViewManager;
import javafx.scene.media.AudioClip;

import java.net.URL;

public class SoundManager {

    private AudioClip audioclipMENU, audioclipPLAY, audioclipGAMEOVER, currentAudioclip;
    private URL menuSoundPath, playSoundPath, gameoverSoundPath;
    private ViewManager viewManager;


    public SoundManager(ViewManager viewManager) {
        this.viewManager = viewManager;
        menuSoundPath = getClass().getResource(AUDIO.MENU_SOUND.getPath());
        playSoundPath = getClass().getResource(AUDIO.PLAY_SOUND.getPath());
        gameoverSoundPath = getClass().getResource(AUDIO.GAMEOVER_SOUND.getPath());

        audioclipMENU = new AudioClip(menuSoundPath.toString());
        audioclipPLAY = new AudioClip(playSoundPath.toString());
        audioclipGAMEOVER = new AudioClip(gameoverSoundPath.toString());

        audioclipMENU.setRate(1);
        audioclipMENU.setCycleCount(200);
        audioclipMENU.play();
        currentAudioclip = audioclipMENU;
    }

    public void chooseSound(AUDIO audioEnum){
        stopMusic();
        switch (audioEnum){
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
    }

    public void stopMusic(){
        audioclipMENU.stop();
        audioclipPLAY.stop();
        audioclipGAMEOVER.stop();
    }

    public AudioClip getCurrentAudioclip() {
        return currentAudioclip;
    }
}
