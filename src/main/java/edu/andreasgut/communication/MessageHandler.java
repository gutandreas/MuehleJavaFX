package edu.andreasgut.communication;

import edu.andreasgut.view.ViewManager;

public interface MessageHandler {

    void prepareKill(ViewManager viewManager);

    void preparePutOrMove(ViewManager viewManager);
}
