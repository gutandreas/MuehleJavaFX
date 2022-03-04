package edu.andreasgut.communication;

import edu.andreasgut.game.*;
import edu.andreasgut.view.ViewManager;
import javafx.application.Platform;
import org.json.JSONObject;

public class Messenger {

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
        jsonObject.put("triggerAxidraw", true);
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
        jsonObject.put("triggerAxidraw", true);
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
        jsonObject.put("triggerAxidraw", true);
        jsonObject.put("playerIndex", viewManager.getGame().getCurrentPlayerIndex());
        sendMessage(viewManager, jsonObject.toString());
    }

    public static void sendChatMessage(ViewManager viewManager, String message){

        JSONObject jsonObject = new JSONObject();
        Game game = viewManager.getGame();

        jsonObject.put("gameCode", game.getGameCode());
        jsonObject.put("command", "chat");
        jsonObject.put("name", game.getCurrentPlayer().getName());
        jsonObject.put("playerUuid", game.getCurrentPlayer().getUuid());
        jsonObject.put("message", message);
        sendMessage(viewManager, jsonObject.toString());
    }

    public static void sendGiveUpMessage(ViewManager viewManager){

        JSONObject jsonObject = new JSONObject();
        Game game = viewManager.getGame();

        jsonObject.put("gameCode", game.getGameCode());
        jsonObject.put("command", "giveup");
        jsonObject.put("name", game.getCurrentPlayer().getName());
        jsonObject.put("playerUuid", game.getCurrentPlayer().getUuid());
        sendMessage(viewManager, jsonObject.toString());
    }

    public static void sendGameOverMessage(ViewManager viewManager, String details){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("gameCode", viewManager.getGame().getGameCode());
        jsonObject.put("playerUuid", viewManager.getGame().getCurrentPlayer().getUuid());
        jsonObject.put("playerIndex", viewManager.getGame().getCurrentPlayerIndex());
        jsonObject.put("command", "gameOver");
        jsonObject.put("details", details);
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
                        viewManager.getLogView().activateChatElements(true);
                        game.getPlayer1().setName(jsonObject.getString("player2Name"));
                        Platform.runLater(()-> viewManager.getScoreView().updatePlayer2Label(jsonObject.getString("player2Name")));
                }
                break;

            case "chat":{
                viewManager.getLogView().postChatMessage(jsonObject.getString("name"), jsonObject.getString("message"));
                break;
            }

            case "gameOver":
                int index = jsonObject.getInt("playerIndex");
                String name = game.getPlayerByIndex(1-index).getName();
                viewManager.getLogView().setStatusLabel(name + " hat das Spiel gewonnen!");
                viewManager.getFieldView().setDisable(true);
                if (game.getWebsocketClient() != null){
                    viewManager.getLogView().activateChatElements(false);}
                break;

            case "roboterConnection":
                if (jsonObject.getBoolean("connected")){
                    viewManager.getScoreView().acitvateRoboterConnectedLabel(true);
                    game.setRoboterWatching(jsonObject.getBoolean("watching"));
                    game.setRoboterPlaying(jsonObject.getBoolean("playing"));
                }
                else {
                    viewManager.getScoreView().acitvateRoboterConnectedLabel(false);
                    game.setRoboterWatching(false);
                    game.setRoboterPlaying(false);
                    viewManager.getScoreView().acitvateRoboterConnectedLabel(false);
                }

                break;

            case "update":

                if (!game.isGameOver()) {

                    if (jsonObject.getString("action").equals("put")) {

                        int ring = jsonObject.getInt("ring");
                        int field = jsonObject.getInt("field");
                        int playerIndex = jsonObject.getInt("playerIndex");

                        Position position = new Position(ring, field);
                        System.out.println(position);

                        if (board.isPutPossibleAt(position)) {
                            board.putStone(position, playerIndex);
                            viewManager.getFieldView().graphicPut(position, viewManager.getGame().getCurrentPlayerIndex(), true);
                            System.out.println(board);
                            //führt zu Mühle
                            if (board.isPositionPartOfMorris(position) && board.canPlayerKill(playerIndex)) {
                                game.updateGameState(true, false, false);
                                viewManager.getGame().getCurrentPlayer().prepareKill(viewManager);
                            }
                            //führt nicht zu Mühle
                            else {
                                game.updateGameState(true, false, true);
                                game.getCurrentPlayer().preparePutOrMove(viewManager);
                            }
                        } else {
                            System.out.println("Es wurde ein ungültiger Put ausgeführt");
                        }
                    }

                    if (jsonObject.getString("action").equals("move")) {

                        int moveFromRing = jsonObject.getInt("moveFromRing");
                        int moveFromField = jsonObject.getInt("moveFromField");
                        int moveToRing = jsonObject.getInt("moveToRing");
                        int moveToField = jsonObject.getInt("moveToField");
                        int playerIndex = jsonObject.getInt("playerIndex");

                        Move move = new Move(new Position(moveFromRing, moveFromField), new Position(moveToRing, moveToField));
                        boolean jump = board.numberOfStonesOf(game.getCurrentPlayerIndex()) == 3;


                        if (board.isMovePossibleAt(move.getFrom(), move.getTo(), jump)) {
                            board.moveStone(move.getFrom(), move.getTo(), playerIndex);
                            viewManager.getFieldView().graphicMove(move, playerIndex);
                            System.out.println(board);
                            //führt zu Mühle
                            if (board.isPositionPartOfMorris(move.getTo()) && board.canPlayerKill(playerIndex)) {
                                game.updateGameState(false, false, false);
                                viewManager.getGame().getCurrentPlayer().prepareKill(viewManager);
                            }
                            //führt nicht zu Mühle
                            else {
                                game.updateGameState(false, false, true);
                                if (!game.isGameOver()) {
                                    game.getCurrentPlayer().preparePutOrMove(viewManager);
                                }
                            }
                        } else {
                            System.out.println("Es wurde ein ungültiger Move ausgeführt");
                        }

                    }

                    if (jsonObject.getString("action").equals("kill")) {

                        int ring = jsonObject.getInt("ring");
                        int field = jsonObject.getInt("field");

                        Position position = new Position(ring, field);
                        int playerIndex = board.getNumberOnPosition(position);

                        if (board.isKillPossibleAt(position, playerIndex)) {
                            board.removeStone(position);
                            viewManager.getFieldView().graphicKill(position, true);
                            System.out.println(board);

                            game.updateGameState(false, true, true);
                            game.setKillPhase(false);

                            if (!game.isGameOver()) {
                                game.getCurrentPlayer().preparePutOrMove(viewManager);
                            }

                        } else {
                            System.out.println("Es wurde ein ungültiger Kill ausgeführt");
                        }
                    }
                    break;
                }
        }
    }
}
