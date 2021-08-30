package edu.andreasgut.game;

import com.sun.javafx.tk.Toolkit;
import edu.andreasgut.online.WebsocketClient;
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
        WebsocketClient.setLoopObject(loopObject);

        // Put von Websocket abholen
        // Evtl. mit https://de.switch-case.com/61360391

        Position position = (Position) Platform.enterNestedEventLoop(loopObject);
        return position;
    }

    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {
        return null;
    }
}
