package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.util.ArrayList;

public abstract class Game {

    Player player0, player1;
    ViewManager viewManager;
    Board board;
    int round;
    final int NUMBEROFSTONES = 9;
    boolean putPhase = true;
    boolean movePhase = false;
    boolean player2starts = false;
    Player currentPlayer;
    Player winner;
    ArrayList<Player> playerArrayList = new ArrayList<>();

    public Game(ViewManager viewManager, Player player0, Player player1) {
        this.viewManager = viewManager;
        board = new Board(this);
        this.player0 = player0;
        this.player1 = player1;
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        currentPlayer = player0;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentPlayerIndex(){
        return currentPlayer.equals(playerArrayList.get(0)) ? 0 : 1;
    }


    public int getOtherPlayerIndex(){
        return currentPlayer.equals(playerArrayList.get(0)) ? 1 : 0;
    }

    public Player getOtherPlayer() {
        return playerArrayList.get(getOtherPlayerIndex());
    }

    public ViewManager getViewManager() {
        return viewManager;
    }


    public Player getPlayer0() {
        return player0;
    }


    public Player getPlayer1() {
        return player1;
    }

    public int getRound() {
        return round;
    }

    private void setGamesPhaseBooleans(){
        if (round >= NUMBEROFSTONES*2){
            putPhase = false;
            movePhase = true;}
    }

    public void increaseRound(){
        round++;
        viewManager.getScoreView().increaseRound();
    }


    private void checkWinner(){
        if (movePhase && board.countPlayersStones(getOtherPlayerIndex()) < 3){
            winner = getCurrentPlayer();
            viewManager.getLogView().setStatusLabel(winner.getName() + " hat das Spiel gewonnen");
            viewManager.getFieldView().setDisable(true);
            System.out.println(winner.getName() + " hat das Spiel gewonnen!");}
    }

    protected void updateGameState(boolean put, boolean kill){
        if (put){
            viewManager.getScoreView().increaseStonesPut();
        }
        if (kill){
            viewManager.getScoreView().increaseStonesKilled();
            viewManager.getScoreView().increaseStonesLost();
            checkWinner();
        }

        increaseRound();
        updateCurrentPlayer();
        setGamesPhaseBooleans();
        viewManager.getLogView().setStatusLabel(currentPlayer.getName() + " ist an der Reihe");

        if (round < NUMBEROFSTONES*2){
            viewManager.getFieldView().setPutCursor();
        }
        else {
            viewManager.getFieldView().setMoveCursor();
        }

        System.out.println(board);
    }


    public void updateCurrentPlayer(){
        if(player2starts){
            currentPlayer = playerArrayList.get((round+1)%2);}
        else {
            currentPlayer = playerArrayList.get(round%2);
        }
    }
}
