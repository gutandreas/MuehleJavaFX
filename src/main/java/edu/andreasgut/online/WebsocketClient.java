package edu.andreasgut.online;

import edu.andreasgut.game.*;
import edu.andreasgut.view.ViewManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;


import java.net.URI;

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
        jsonObject.put("gameCode", ((OnlineGame) viewManager.getGame()).getGameCode());
        jsonObject.put("command", "start");
        send(jsonObject.toString());

    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        JSONObject jsonObject = new JSONObject(message);
        String command = jsonObject.getString("command");
        Board board = viewManager.getGame().getBoard();

        switch (command){
            case "join":
                System.out.println("Spiel beigetreten");
                viewManager.getLogView().activateNextComputerStepButton();
                break;

            case "update":

                if (jsonObject.getString("action").equals("put")){

                    int ring = jsonObject.getInt("ring");
                    int field = jsonObject.getInt("field");
                    int playerIndex = jsonObject.getInt("playerIndex");
                    OnlineGame game = ((OnlineGame) viewManager.getGame());

                    Position position = new Position(ring, field);
                    System.out.println(position);

                    if (board.checkPut(position)){
                        board.putStone(position, playerIndex);
                        viewManager.getFieldView().graphicPut(position, viewManager.getGame().getCurrentPlayerIndex(), 0);
                        if (board.checkMorris(position) && board.isThereStoneToKill(1-playerIndex) ||
                                (game.getRound() > 18 && board.countPlayersStones(1-playerIndex) != 3)){
                            // Gegner darf Stein entfernen
                        }
                        else {
                            viewManager.getLogView().activateNextComputerStepButton();
                        }
                        System.out.println(board);}
                    else {
                        System.out.println("Es wurde ein ungültiger Put ausgeführt");
                    }
                }

                if (jsonObject.getString("action").equals("move")){

                    int moveFromRing = jsonObject.getInt("moveFromRing");
                    int moveFromField = jsonObject.getInt("moveFromField");
                    int moveToRing = jsonObject.getInt("moveToRing");
                    int moveToField = jsonObject.getInt("moveToField");
                    int playerIndex = jsonObject.getInt("playerIndex");

                    Move move = new Move(new Position(moveFromRing, moveFromField), new Position(moveToRing, moveToField));
                    boolean jump = board.countPlayersStones(0) == 3;


                    if (board.checkMove(move, jump)){
                        board.move(move, playerIndex);
                        System.out.println(board);}
                    else {
                        System.out.println("Es wurde ein ungültiger Move ausgeführt");
                    }

                }

                if (jsonObject.getString("action").equals("kill")){

                    int ring = jsonObject.getInt("ring");
                    int field = jsonObject.getInt("field");

                    int playerIndex = board.getNumberOnPosition(ring, field);
                    Position position = new Position(ring, field);

                    if (board.checkKill(position, playerIndex)){
                        board.clearStone(position);
                        System.out.println(board);
                    }
                    else {
                        System.out.println("Es wurde ein ungültiger Kill ausgeführt");
                    }


                }
                break;

        }

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
