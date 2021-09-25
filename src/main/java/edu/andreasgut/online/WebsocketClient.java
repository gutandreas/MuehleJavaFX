package edu.andreasgut.online;

import edu.andreasgut.game.Board;
import edu.andreasgut.game.Game;
import edu.andreasgut.game.Move;
import edu.andreasgut.game.Position;
import edu.andreasgut.view.ViewManager;
import javafx.application.Platform;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;


import java.net.URI;

public class WebsocketClient extends WebSocketClient {

    private ViewManager viewManager;
    private Board board;


    public WebsocketClient(URI serverUri, ViewManager viewManager) {
        super(serverUri);
        this.viewManager = viewManager;
        this.board = viewManager.getGame().getBoard();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Verbunden mit Server");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("gameCode", viewManager.getGame().getGameCode());
        jsonObject.put("command", "start");
        send(jsonObject.toString());

    }

    @Override
    public void onMessage(String message) {
        MessageInterface.receiveMessage(viewManager, message);

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }

    public void watchGame(String gameCode){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "watch");
        jsonObject.put("gameCode", gameCode);
        send(jsonObject.toString());
    }

}
