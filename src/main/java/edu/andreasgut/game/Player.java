package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.lang.reflect.Field;

public abstract class Player {

    private String name;
    protected ViewManager viewManager;

    public Player(ViewManager viewManager, String name) {
        this.viewManager = viewManager;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    abstract Position[] move(Board board, int playerIndex, boolean allowedToJump);
    abstract Position put(Board board, int playerIndex);
    abstract Position kill(Board board, int otherPlayerIndex);
}
