package edu.andreasgut.view.fxElements;

public enum STONECOLOR {

    BLACK("edu/andreasgut/Images/StoneBlack.png"),
    WHITE( "edu/andreasgut/Images/StoneWhite.png");

    private String path;

    STONECOLOR(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}