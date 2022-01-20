package edu.andreasgut.online;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import edu.andreasgut.view.ViewManager;

public class WebsocketClient extends WebSocketClient {

    private ViewManager viewManager;

    public WebsocketClient(URI serverUri, ViewManager viewManager) {
        super(serverUri);
        this.viewManager = viewManager;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Verbunden mit Server");
        JSONObject jsonObject = new JSONObject();

        if (viewManager.getGame().isJoinExistingGame()){
            System.out.println("join");
            jsonObject.put("gameCode", viewManager.getGame().getGameCode());
            jsonObject.put("command", "join");
            jsonObject.put("player2Name", viewManager.getGame().getPlayer1().getName());
            jsonObject.put("playerUuid", viewManager.getGame().getPlayer1().getUuid());
        }
        else {
            System.out.println("start");
            jsonObject.put("gameCode", viewManager.getGame().getGameCode());
            jsonObject.put("command", "start");
        }

        send(jsonObject.toString());

    }

    @Override
    public void onMessage(String message) {
        Messenger.receiveMessage(viewManager, message);

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
