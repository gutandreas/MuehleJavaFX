package edu.andreasgut.online;

import edu.andreasgut.view.ViewManager;

public interface MessageHandler {

     void prepareKill(ViewManager viewManager);
     void prepareNextPutOrMove(ViewManager viewManager);
}
