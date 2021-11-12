package edu.andreasgut.online;

import edu.andreasgut.game.*;
import edu.andreasgut.view.ViewManager;
import javafx.application.Platform;
import org.json.JSONObject;

public class MessageInterface {

    private static void sendMessage(ViewManager viewManager, String message){
        if (viewManager.getGame().getWebsocketClient() == null){
            receiveMessage(viewManager, message);
        }
        else {
            viewManager.getGame().getWebsocketClient().send(message);
        }
    }

    public static void sendPutMessage(ViewManager viewManager, Position position){
        JSONObject jsonObject = new JSONObject();
        System.out.println(viewManager.getGame().getCurrentPlayer().getUuid());


        jsonObject.put("gameCode", viewManager.getGame().getGameCode());
        jsonObject.put("command", "update");
        jsonObject.put("action", "put");
        jsonObject.put("playerUuid", viewManager.getGame().getCurrentPlayer().getUuid());
        jsonObject.put("ring", position.getRing());
        jsonObject.put("field", position.getField());
        jsonObject.put("callComputer", false);
        jsonObject.put("playerIndex", viewManager.getGame().getCurrentPlayerIndex());
        sendMessage(viewManager, jsonObject.toString());
    }

    public static void sendMoveMessage(ViewManager viewManager, Move move) {

        JSONObject jsonObject = new JSONObject();


        jsonObject.put("gameCode", viewManager.getGame().getGameCode());
        jsonObject.put("command", "update");
        jsonObject.put("action", "move");
        jsonObject.put("playerUuid", viewManager.getGame().getCurrentPlayer().getUuid());
        jsonObject.put("moveFromRing", move.getFrom().getRing());
        jsonObject.put("moveFromField", move.getFrom().getField());
        jsonObject.put("moveToRing", move.getTo().getRing());
        jsonObject.put("moveToField", move.getTo().getField());
        jsonObject.put("callComputer", false);
        jsonObject.put("playerIndex", viewManager.getGame().getCurrentPlayerIndex());
        sendMessage(viewManager, jsonObject.toString());
    }

    public static void sendKillMessage(ViewManager viewManager, Position position) {

        JSONObject jsonObject = new JSONObject();
        System.out.println(viewManager.getGame().getCurrentPlayer().getUuid());

        jsonObject.put("gameCode", viewManager.getGame().getGameCode());
        jsonObject.put("command", "update");
        jsonObject.put("action", "kill");
        jsonObject.put("playerUuid", viewManager.getGame().getCurrentPlayer().getUuid());
        jsonObject.put("ring", position.getRing());
        jsonObject.put("field", position.getField());
        jsonObject.put("callComputer", false);
        jsonObject.put("playerIndex", viewManager.getGame().getCurrentPlayerIndex());
        sendMessage(viewManager, jsonObject.toString());
    }


    public static void receiveMessage(ViewManager viewManager, String message){

        Board board = viewManager.getGame().getBoard();
        System.out.println(message);
        JSONObject jsonObject = new JSONObject(message);
        String command = jsonObject.getString("command");
        Game game = viewManager.getGame();

        switch (command){
            case "join":
                if (!game.isJoinExistingGame()){
                        System.out.println("Spiel beigetreten");
                        viewManager.getLogView().activateNextComputerStepButton();
                        game.getPlayer1().setName(jsonObject.getString("player2Name"));
                        Platform.runLater(()-> viewManager.getScoreView().getPlayer2Label().setText("Player 2: " + jsonObject.getString("player2Name")));
                }
                break;

            case "update":

                if (jsonObject.getString("action").equals("put")){

                    int ring = jsonObject.getInt("ring");
                    int field = jsonObject.getInt("field");
                    int playerIndex = jsonObject.getInt("playerIndex");

                    Position position = new Position(ring, field);
                    System.out.println(position);

                    if (board.checkPut(position)){
                        board.putStone(position, playerIndex);
                        viewManager.getFieldView().graphicPut(position, viewManager.getGame().getCurrentPlayerIndex(), 200);
                        System.out.println(board);
                        //führt zu Mühle
                        if (board.checkMorris(position) && board.isThereStoneToKill(1-playerIndex)){
                           viewManager.getGame().getCurrentPlayer().prepareKill(viewManager);
                        }
                        //führt nicht zu Mühle
                        else {
                            game.updateGameState(true, false);
                            game.getCurrentPlayer().prepareNextPutOrMove(viewManager);
                        }
                    }
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
                    boolean jump = board.countPlayersStones(game.getCurrentPlayerIndex()) == 3;


                    if (board.checkMove(move, jump)){
                        board.move(move, playerIndex);
                        viewManager.getFieldView().graphicMove(move, playerIndex);
                        System.out.println(board);
                        //führt zu Mühle
                        if (board.checkMorris(move.getTo()) && board.isThereStoneToKill(1-playerIndex)){
                            viewManager.getGame().getCurrentPlayer().prepareKill(viewManager);
                        }
                        //führt nicht zu Mühle
                        else {
                            game.updateGameState(true, false);
                            game.getCurrentPlayer().prepareNextPutOrMove(viewManager);
                            }
                    }
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
                        viewManager.getFieldView().graphicKill(position);
                        System.out.println(board);

                        game.updateGameState(false, true);
                        game.setKillPhase(false);
                        game.getCurrentPlayer().prepareNextPutOrMove(viewManager);

                    }
                    else {
                        System.out.println("Es wurde ein ungültiger Kill ausgeführt");
                    }
                }
                break;
        }
    }
}
