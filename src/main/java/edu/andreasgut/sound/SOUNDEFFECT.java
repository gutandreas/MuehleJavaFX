package edu.andreasgut.sound;

public enum SOUNDEFFECT {

    PUT_STONE("PUT_STONE.mp3"),
    KILL_STONE("KILL_STONE.mp3");

    private final String path;

    SOUNDEFFECT(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
