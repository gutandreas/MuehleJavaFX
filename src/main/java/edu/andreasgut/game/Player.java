package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.lang.reflect.Field;

public abstract class Player {

    private String name;
    protected ViewManager viewManager;
    //private boolean allowedToJump = false;

    public Player(ViewManager viewManager, String name) {
        this.viewManager = viewManager;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*public void setAllowedToJump(boolean allowedToJump) {
        this.allowedToJump = allowedToJump;
    }

    public boolean isAllowedToJump() {
        return allowedToJump;
    }*/

    abstract Position move(Board board, int playerIndex, boolean allowedToJump) throws InvalidMoveException, InvalidPutException;
    abstract Position put(Board board, int playerIndex) throws InvalidPutException;
    abstract void kill(Board board, int playerIndex) throws InvalidKillException;
}
