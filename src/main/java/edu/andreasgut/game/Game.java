package edu.andreasgut.game;

import edu.andreasgut.online.Messenger;
import edu.andreasgut.online.WebsocketClient;
import edu.andreasgut.view.FieldViewPlay;
import edu.andreasgut.view.ViewManager;

import java.util.ArrayList;

public class Game {

    private final Player player0;
    private final Player player1;
    private Player winner;
    private int round;
    private final int NUMBEROFSTONES = 9;
    private Player currentPlayer;
    private Board board;
    boolean putPhase = true;
    boolean movePhase = false;
    boolean movePhaseTake = true;
    boolean movePhaseRelease = false;
    boolean gameOver = false;
    private boolean killPhase = false;
    private final ViewManager viewManager;
    private boolean player2starts;
    private boolean clickOkay = true;
    private String gameCode;
    private Position lastClickedPosition;
    private WebsocketClient websocketClient;
    private boolean joinExistingGame;


    ArrayList<Player> playerArrayList = new ArrayList<>();

    public Game(ViewManager viewManager, Player player0, Player player1, Board board, int round) {
        this.viewManager = viewManager;
        this.player0 = player0;
        this.player1 = player1;
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        currentPlayer=playerArrayList.get(0);
        this.board = new Board(this);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getNumberOnPosition(i, j) != 9) {
                    Position tempPosition = new Position(i, j);
                    this.board.putStone(tempPosition, board.getNumberOnPosition(i, j));
                }
            }
        }
        this.round = round;
        if (round <=18){
            putPhase = true;
            movePhase = false;
        }
        else {
            putPhase = false;
            movePhase = true;
            movePhaseTake = true;
            movePhaseRelease = false;
        }

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
        this.joinExistingGame = joinExistingGame;
    }


    public Game(ViewManager viewManager, Player player0, boolean player2starts, ScorePoints putPoints, ScorePoints movePoints, int levelLimit, Board board, int round) {
        this.viewManager = viewManager;
        this.player0 = player0;
        this.player1 = new StandardComputerPlayer(viewManager, "COMPUTER", putPoints, movePoints, levelLimit);
        this.player2starts = player2starts;
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        this.board = new Board(this);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getNumberOnPosition(i, j) != 9) {
                    Position tempPosition = new Position(i, j);
                    this.board.putStone(tempPosition, board.getNumberOnPosition(i, j));
                }
            }
        }
        if (player2starts){
            currentPlayer=playerArrayList.get(1);}
        else {
            currentPlayer=playerArrayList.get(0);}

        this.round = round;
        if (round <=18){
            putPhase = true;
            movePhase = false;
        }
        else {
            putPhase = false;
            movePhase = true;
            movePhaseTake = true;
            movePhaseRelease = false;
        }


    }

    public boolean isJoinExistingGame() {
        return joinExistingGame;
    }

    public int getRound() {
        return round;
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

    public void setClickOkay(boolean clickOkay) {
        this.clickOkay = clickOkay;
    }


    public void setKillPhase(boolean killPhase) {
        this.killPhase = killPhase;
    }

    public String getGameCode() {
        return gameCode;
    }

    public boolean isGameOver() {
        return gameOver;
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
                    Messenger.sendKillMessage(viewManager, clickedPosition);
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
                    Messenger.sendPutMessage(viewManager, clickedPosition);
                    return;

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
                        ((FieldViewPlay) viewManager.getFieldView()).setPutCursor();
                        viewManager.getFieldView().graphicTake(clickedPosition);
                        lastClickedPosition = clickedPosition;
                        clickOkay = true;
                        movePhaseRelease = true;
                        movePhaseTake = false;
                        return;}
                    else {
                        clickOkay = true;
                        return;
                    }

                }


                if (movePhaseRelease){
                    boolean allowedToJump = board.countPlayersStones(getCurrentPlayerIndex()) == 3;
                    Move move = new Move(lastClickedPosition, clickedPosition);
                    if (board.checkMove(move,allowedToJump)){
                        Messenger.sendMoveMessage(viewManager, move);
                    }
                    else {
                        System.out.println("Kein gültiger Move");
                        viewManager.getLogView().setStatusLabel("Das ist kein gültiger Zug");
                        viewManager.getFieldView().graphicPut(lastClickedPosition, getCurrentPlayerIndex(), 0, true);
                        ((FieldViewPlay) viewManager.getFieldView()).setMoveCursor();
                        clickOkay = true;
                        movePhaseRelease = false;
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



    public void updateGameState(boolean put, boolean killHappend, boolean increaseRound){
        if (put){
            viewManager.getScoreView().increaseStonesPut();
        }
        if (killHappend){
            viewManager.getScoreView().increaseStonesKilled();
            viewManager.getScoreView().increaseStonesLost();
        }


        if (increaseRound) {

            increaseRound();
            updateCurrentPlayer();
            setGamesPhaseBooleans();
            viewManager.getLogView().setStatusLabel(currentPlayer.getName() + " ist an der Reihe");
            System.out.println(round);

            if (round < NUMBEROFSTONES * 2) {
                putPhase = true;
                if (viewManager.getFieldView().isActivateBoardFunctions()) {
                    ((FieldViewPlay) viewManager.getFieldView()).setPutCursor();
                }
            } else {
                movePhase = true;
                movePhaseTake = true;
                movePhaseRelease = false;
                if (viewManager.getFieldView().isActivateBoardFunctions()) {
                    ((FieldViewPlay) viewManager.getFieldView()).setMoveCursor();
                }
            }
        }

        checkWinner();

    }





    private void setGamesPhaseBooleans(){
        if (round >= NUMBEROFSTONES*2){
            putPhase = false;
            movePhase = true;
            viewManager.getScoreView().updatePhase("Steine verschieben");
        }
    }


    private void checkWinner(){

        boolean thereIsAWinner = (movePhase && board.countPlayersStones(getCurrentPlayerIndex()) < 3)
                || (movePhase && !board.checkIfAbleToMove(getCurrentPlayerIndex()));

        if (thereIsAWinner){
            gameOver = true;
            winner = getOtherPlayer();
            viewManager.getLogView().setStatusLabel(winner.getName() + " hat das Spiel gewonnen");
            viewManager.getFieldView().setDisable(true);
            System.out.println(winner.getName() + " hat das Spiel gewonnen!");
        }
    }
}