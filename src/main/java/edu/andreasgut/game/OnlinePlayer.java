package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;
import javafx.application.Platform;

public class OnlinePlayer extends Player{


    public OnlinePlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }

    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {
        return null;
    }

    @Override
    Position put(Board board, int playerIndex) {
        Object loopObject = new Object();
        Platform.enterNestedEventLoop(loopObject);
        return null;
    }

    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {
        return null;
    }
}
