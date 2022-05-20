package edu.andreasgut.sound;

public enum MUSIC {

    MENU_SOUND("/edu/andreasgut/Sound/MENU_SOUND.mp3"),
    PLAY_SOUND("/edu/andreasgut/Sound/PLAY_SOUND.mp3"),
    GAMEOVER_SOUND("/edu/andreasgut/Sound/MENU_SOUND.mp3");

    private final String path;

    MUSIC(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
