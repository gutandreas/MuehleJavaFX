package edu.andreasgut.game;

import edu.andreasgut.online.WebsocketClient;
import edu.andreasgut.view.ViewManager;
import org.json.JSONObject;

public class OnlineGame extends Game{

    WebsocketClient websocketClient;
    String gameCode;
    ViewManager viewManager;
    Player player0;
    Player player1;
    boolean startGame;
    int round = 0;
    final int NUMBEROFSTONES = 9;
    Board board;


    public OnlineGame(ViewManager viewManager, Player player0, Player player1, String gameCode, WebsocketClient websocketClient) {
        super(viewManager, player0, player1);
        this.websocketClient = websocketClient;
        this.gameCode = gameCode;

    }




    private void sendPutToWebsocket(Position position) {

        JSONObject jsonObject = new JSONObject();
        System.out.println(viewManager.getGame().getCurrentPlayer().getUuid());

        jsonObject.put("gameCode", gameCode);
        jsonObject.put("command", "update");
        jsonObject.put("action", "put");
        jsonObject.put("playerUuid", player0.getUuid());
        jsonObject.put("ring", position.getRing());
        jsonObject.put("field", position.getField());
        jsonObject.put("callComputer", false);
        websocketClient.send(jsonObject.toString());
    }

    private void sendKillToWebsocket(Position position) {

        JSONObject jsonObject = new JSONObject();
        System.out.println(viewManager.getGame().getCurrentPlayer().getUuid());

        jsonObject.put("gameCode", gameCode);
        jsonObject.put("command", "update");
        jsonObject.put("action", "kill");
        jsonObject.put("playerUuid", player0.getUuid());
        jsonObject.put("ring", position.getRing());
        jsonObject.put("field", position.getField());
        jsonObject.put("callComputer", false);
        websocketClient.send(jsonObject.toString());
    }

    public String getGameCode() {
        return gameCode;
    }

    public void getNextComputerStep() {
        if (round < NUMBEROFSTONES*2){
            Position computerPutPosition = currentPlayer.put(board,getCurrentPlayerIndex());
            sendPutToWebsocket(computerPutPosition);
            board.putStone(computerPutPosition, getCurrentPlayerIndex());
            viewManager.getFieldView().graphicPut(computerPutPosition, getCurrentPlayerIndex(), 0);
            if (board.checkMorris(computerPutPosition) && board.isThereStoneToKill(getOtherPlayerIndex())){
                Position computerKillPosition = currentPlayer.kill(board,getCurrentPlayerIndex(), getOtherPlayerIndex());
                sendKillToWebsocket(computerKillPosition);
                board.clearStone(computerKillPosition);
                viewManager.getFieldView().graphicKill(computerKillPosition);
            }

            return;
        }
        else {
            boolean allowedToJump = board.countPlayersStones(getCurrentPlayerIndex()) == 3;
            Move computerMove = currentPlayer.move(board, getCurrentPlayerIndex(), allowedToJump);
            if (board.checkMove(computerMove, allowedToJump)){
                board.move(computerMove, getCurrentPlayerIndex());
                viewManager.getFieldView().graphicMove(computerMove, getCurrentPlayerIndex());
                if (board.checkMorris(computerMove.getTo()) && board.isThereStoneToKill(getOtherPlayerIndex())){
                    Position computerKillPosition = currentPlayer.kill(board,getCurrentPlayerIndex(), getOtherPlayerIndex());
                    sendKillToWebsocket(computerKillPosition);
                    board.clearStone(computerKillPosition);
                    viewManager.getFieldView().graphicKill(computerKillPosition);
                }
                return;
            }
        }


    }
}