package edu.andreasgut.sound;

public enum AUDIO {

    MENU_SOUND("MENU_SOUND.mp3"),
    PLAY_SOUND("PLAY_SOUND.mp3"),
    GAMEOVER_SOUND("PLAY_SOUND.mp3");

    private String path;

    AUDIO(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
