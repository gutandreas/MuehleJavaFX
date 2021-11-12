package edu.andreasgut.game;

import edu.andreasgut.online.MessageHandler;
import edu.andreasgut.view.ViewManager;

public abstract class Player implements MessageHandler {

    protected String name;
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

    public void setName(String name) {
        this.name = name;
    }

}
