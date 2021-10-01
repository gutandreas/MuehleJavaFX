package edu.andreasgut.game;

import edu.andreasgut.online.MessageInterface;
import edu.andreasgut.online.WebsocketClient;
import edu.andreasgut.view.ViewManager;
import org.json.JSONObject;

import java.util.ArrayList;

public class Game {

    private final Player player0;
    private final Player player1;
    private Player winner;
    private int round;
    private final int NUMBEROFSTONES = 9;
    private Player currentPlayer;
    private final Board board;
    boolean putPhase = true;
    boolean movePhase = false;
    boolean movePhaseTake = true;
    boolean movePhaseRelase = false;
    private boolean killPhase = false;
    private final ViewManager viewManager;
    private boolean player2starts;
    private boolean clickOkay = true;
    private String gameCode;
    private Position lastClickedPosition;
    private WebsocketClient websocketClient;
    private boolean joinExistingGame;


    ArrayList<Player> playerArrayList = new ArrayList<>();

    public Game(ViewManager viewManager, Player player0, Player player1) {
        this.viewManager = viewManager;
        this.player0 = player0;
        this.player1 = player1;
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        round = 0;
        currentPlayer=playerArrayList.get(0);
        board = new Board(this);
    }

    public Game(ViewManager viewManager, Player player0, Player player1, String gameCode, boolean joinExistingGame) {
        this.viewManager = viewManager;
        this.player0 = player0;
        this.player1 = player1;
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        round = 0;
        currentPlayer=playerArrayList.get(0);
        this.gameCode = gameCode;
        board = new Board(this);
        this.joinExistingGame = joinExistingGame;
    }


    public Game(ViewManager viewManager, Player player0, boolean player2starts) {
        this.viewManager = viewManager;
        this.player0 = player0;
        this.player1 = new ComputerPlayer(viewManager, "COMPUTER", true);
        this.player2starts = player2starts;
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        round = 0;
        board = new Board(this);
        if (player2starts){
            currentPlayer=playerArrayList.get(1);}
        else {
            currentPlayer=playerArrayList.get(0);}


    }

    public boolean isJoinExistingGame() {
        return joinExistingGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public Player getOtherPlayer() {
        return playerArrayList.get(getOtherPlayerIndex());
    }


    public Player getPlayer0() {
        return player0;
    }


    public Player getPlayer1() {
        return player1;
    }

    public boolean isPutPhase() {
        return putPhase;
    }

    public boolean isMovePhase() {
        return movePhase;
    }

    public boolean isKillPhase() {
        return killPhase;
    }

    public WebsocketClient getWebsocketClient() {
        return websocketClient;
    }

    public void setWebsocketClient(WebsocketClient websocketClient) {
        this.websocketClient = websocketClient;
    }

    public int getCurrentPlayerIndex(){
        return currentPlayer.equals(playerArrayList.get(0)) ? 0 : 1;
    }


    public int getOtherPlayerIndex(){
        return currentPlayer.equals(playerArrayList.get(0)) ? 1 : 0;
    }


    public void updateCurrentPlayer(){
        if(player2starts){
            currentPlayer = playerArrayList.get((round+1)%2);}
        else {
            currentPlayer = playerArrayList.get(round%2);
        }
    }


    public Board getBoard() {
        return board;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public void setClickOkay(boolean clickOkay) {
        this.clickOkay = clickOkay;
    }

    public void setPutPhase(boolean putPhase) {
        this.putPhase = putPhase;
    }

    public void setMovePhase(boolean movePhase) {
        this.movePhase = movePhase;
    }

    public void setMovePhaseTake(boolean movePhaseTake) {
        this.movePhaseTake = movePhaseTake;
    }

    public void setMovePhaseRelase(boolean movePhaseRelase) {
        this.movePhaseRelase = movePhaseRelase;
    }

    public void setKillPhase(boolean killPhase) {
        this.killPhase = killPhase;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void increaseRound(){
        round++;
        viewManager.getScoreView().increaseRound();
    }


    public ViewManager getViewManager() {
        return viewManager;
    }


    public void nextStep(Position clickedPosition) {


        if (clickOkay){

            clickOkay = false;


            if (killPhase){
                if (board.checkKill(clickedPosition, getOtherPlayerIndex())){
                    sendKillToMessageInterface(clickedPosition);
                    return;
                   }
                else {
                    System.out.println("Ungültiger Kill");
                    viewManager.getLogView().setStatusLabel("Auf diesem Feld kann kein Stein entfernt werden.");
                    clickOkay = true;
                    return;
                }
            }

            if (putPhase){
                if (board.checkPut(clickedPosition)){

                    sendPutToMessageInterface(clickedPosition);

                }
                else {
                    System.out.println("Ungültiger Put, Feld ist nicht frei");
                    viewManager.getLogView().setStatusLabel("Dieses Feld ist nicht frei.");
                    clickOkay = true;
                    return;
                }
            }

            if (movePhase){
                if (movePhaseTake){
                    if (board.isThisMyStone(clickedPosition, getCurrentPlayerIndex())){
                        viewManager.getFieldView().setPutCursor();
                        viewManager.getFieldView().graphicTake(clickedPosition);
                        lastClickedPosition = clickedPosition;
                        clickOkay = true;
                        movePhaseRelase = true;
                        movePhaseTake = false;
                        return;}
                    else {
                        clickOkay = true;
                        return;
                    }

                }


                if (movePhaseRelase){
                    boolean allowedToJump = board.countPlayersStones(getCurrentPlayerIndex()) == 3;
                    Move move = new Move(lastClickedPosition, clickedPosition);
                    if (board.checkMove(move,allowedToJump)){
                        sendMoveToMessageInterface(move);

                    }
                    else {
                        System.out.println("Kein gültiger Move");
                        viewManager.getLogView().setStatusLabel("Das ist kein gültiger Zug");
                        viewManager.getFieldView().graphicPut(lastClickedPosition, getCurrentPlayerIndex(), 0);
                        viewManager.getFieldView().setMoveCursor();
                        clickOkay = true;
                        movePhaseRelase = false;
                        movePhaseTake = true;
                    }
                }


            }



        }
        else {
            System.out.println("Kein Klick möglich");
            viewManager.getLogView().setStatusLabel("Warten Sie, bis Sie an der Reihe sind.");
        }

    }

    public void callComputer(boolean put, boolean move, boolean kill){

        if (put){
            Position computerPutPosition = currentPlayer.put(board,getCurrentPlayerIndex());
            sendPutToMessageInterface(computerPutPosition);
            return;
        }

        if (move){
            boolean allowedToJump = board.countPlayersStones(getCurrentPlayerIndex()) == 3;
            Move computerMove = currentPlayer.move(board, getCurrentPlayerIndex(), allowedToJump);
            sendMoveToMessageInterface(computerMove);
            return;
        }

        if (kill){
            Position computerKillPosition = currentPlayer.kill(board,getCurrentPlayerIndex(), getOtherPlayerIndex());
            sendKillToMessageInterface(computerKillPosition);
        }

        /*if (round < NUMBEROFSTONES*2){
            Position computerPutPosition = currentPlayer.put(board,getCurrentPlayerIndex());
            sendPutToMessageInterface(computerPutPosition);
            if (board.checkMorris(computerPutPosition) && board.isThereStoneToKill(getOtherPlayerIndex())){
                Position computerKillPosition = currentPlayer.kill(board,getCurrentPlayerIndex(), getOtherPlayerIndex());
                sendKillToMessageInterface(computerKillPosition);
            }
            return;
        }
        else {
            boolean allowedToJump = board.countPlayersStones(getCurrentPlayerIndex()) == 3;
            Move computerMove = currentPlayer.move(board, getCurrentPlayerIndex(), allowedToJump);
            if (board.checkMove(computerMove, allowedToJump)){
                sendMoveToMessageInterface(computerMove);
                if (board.checkMorris(computerMove.getTo()) && board.isThereStoneToKill(getOtherPlayerIndex())){
                    Position computerKillPosition = currentPlayer.kill(board,getCurrentPlayerIndex(), getOtherPlayerIndex());
                    sendKillToMessageInterface(computerKillPosition);
                }

                return;
            }
        }*/

    }



    private void sendPutToMessageInterface(Position position) {

        JSONObject jsonObject = new JSONObject();
        System.out.println(viewManager.getGame().getCurrentPlayer().getUuid());


        jsonObject.put("gameCode", gameCode);
        jsonObject.put("command", "update");
        jsonObject.put("action", "put");
        jsonObject.put("playerUuid", viewManager.getGame().getCurrentPlayer().getUuid());
        jsonObject.put("ring", position.getRing());
        jsonObject.put("field", position.getField());
        jsonObject.put("callComputer", false);
        jsonObject.put("playerIndex", getCurrentPlayerIndex());
        MessageInterface.sendMessage(viewManager, jsonObject.toString());
    }

    private void sendMoveToMessageInterface(Move move) {

        JSONObject jsonObject = new JSONObject();
        System.out.println(viewManager.getGame().getCurrentPlayer().getUuid());

        jsonObject.put("gameCode", gameCode);
        jsonObject.put("command", "update");
        jsonObject.put("action", "move");
        jsonObject.put("playerUuid", viewManager.getGame().getCurrentPlayer().getUuid());
        jsonObject.put("moveFromRing", move.getFrom().getRing());
        jsonObject.put("moveFromField", move.getFrom().getField());
        jsonObject.put("moveToRing", move.getTo().getRing());
        jsonObject.put("moveToField", move.getTo().getField());
        jsonObject.put("callComputer", false);
        jsonObject.put("playerIndex", getCurrentPlayerIndex());
        MessageInterface.sendMessage(viewManager, jsonObject.toString());
    }

    private void sendKillToMessageInterface(Position position) {

        JSONObject jsonObject = new JSONObject();
        System.out.println(viewManager.getGame().getCurrentPlayer().getUuid());

        jsonObject.put("gameCode", gameCode);
        jsonObject.put("command", "update");
        jsonObject.put("action", "kill");
        jsonObject.put("playerUuid", viewManager.getGame().getCurrentPlayer().getUuid());
        jsonObject.put("ring", position.getRing());
        jsonObject.put("field", position.getField());
        jsonObject.put("callComputer", false);
        jsonObject.put("playerIndex", getCurrentPlayerIndex());
        MessageInterface.sendMessage(viewManager, jsonObject.toString());
    }

    public void updateGameState(boolean put, boolean killHappend){
        if (put){
            viewManager.getScoreView().increaseStonesPut();
        }
        if (killHappend){
            viewManager.getScoreView().increaseStonesKilled();
            viewManager.getScoreView().increaseStonesLost();
            if (checkWinner()){
                return;
            }
        }

        increaseRound();
        updateCurrentPlayer();
        setGamesPhaseBooleans();
        viewManager.getLogView().setStatusLabel(currentPlayer.getName() + " ist an der Reihe");

        if (round < NUMBEROFSTONES*2){
            putPhase = true;
            if (viewManager.getFieldView().isActivateBoardFunctions()){
                viewManager.getFieldView().setPutCursor();}
        }
        else {
            movePhase = true;
            movePhaseTake = true;
            movePhaseRelase = false;
            if (viewManager.getFieldView().isActivateBoardFunctions()){
                viewManager.getFieldView().setMoveCursor();}
        }

    }





    private void setGamesPhaseBooleans(){
        if (round >= NUMBEROFSTONES*2){
            putPhase = false;
            movePhase = true;
            viewManager.getScoreView().updatePhase("Steine verschieben");
        }
    }


    private boolean checkWinner(){
        if (movePhase && board.countPlayersStones(getOtherPlayerIndex()) < 3){
            winner = getCurrentPlayer();
            viewManager.getLogView().setStatusLabel(winner.getName() + " hat das Spiel gewonnen");
            viewManager.getFieldView().setDisable(true);
            System.out.println(winner.getName() + " hat das Spiel gewonnen!");
            return true;
        }
        else {
            return false;
        }
    }
}