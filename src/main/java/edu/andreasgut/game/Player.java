package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

public abstract class Player {

    private final String name;
    protected String uuid;
    protected final ViewManager viewManager;
    protected boolean local;


    public Player(ViewManager viewManager, String name, boolean local) {
        this.viewManager = viewManager;
        this.name = name;
        this.local = local;
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

    public boolean isLocal() {
        return local;
    }

    abstract Move move(Board board, int playerIndex, boolean allowedToJump);
    abstract Position put(Board board, int playerIndex);
    abstract Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex);
}
