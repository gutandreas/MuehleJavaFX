package edu.andreasgut.sound;

public enum SOUNDEFFECT {

    PUT_STONE("PUT_STONE.mp3");

    private String path;

    SOUNDEFFECT(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
