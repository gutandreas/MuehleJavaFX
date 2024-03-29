package edu.andreasgut.view;

public enum STONECOLOR {

    BLACK("edu/andreasgut/Images/StoneBlack.png",
            "edu/andreasgut/Images/HandCursorBlack.png",
            "edu/andreasgut/Images/KillCursorBlack.png"),
    WHITE("edu/andreasgut/Images/StoneWhite.png",
            "edu/andreasgut/Images/HandCursorWhite.png",
            "edu/andreasgut/Images/KillCursorWhite.png");

    private final String pathStone;
    private final String pathMoveCursor;
    private final String pathKillCursor;

    STONECOLOR(String pathStone, String pathMoveCursor, String pathKillCursor) {
        this.pathStone = pathStone;
        this.pathMoveCursor = pathMoveCursor;
        this.pathKillCursor = pathKillCursor;
    }

    public String getPathStone() {
        return pathStone;
    }

    public String getPathMoveCursor() {
        return pathMoveCursor;
    }

    public String getPathKillCursor() {
        return pathKillCursor;
    }
}