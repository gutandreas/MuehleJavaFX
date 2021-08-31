package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

public abstract class Player {

    private final String name;
    protected String uuid;
    protected final ViewManager viewManager;


    public Player(ViewManager viewManager, String name) {
        this.viewManager = viewManager;
        this.name = name;
    }

    public Player(ViewManager viewManager, String name, String uuid) {
        this.viewManager = viewManager;
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    abstract Move move(Board board, int playerIndex, boolean allowedToJump);
    abstract Position put(Board board, int playerIndex);
    abstract Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex);
}
