package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;


import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Player player0;
    private Player player1;
    private Player winner;
    private int round;
    private final int NUMBEROFSTONES = 9;
    private Player currentPlayer;
    private Board board;
    boolean putPhase = true;
    boolean movePhase = false;
    private Scanner scanner = new Scanner(System.in);
    private ViewManager viewManager;
    private boolean player2starts;

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

    public Game(ViewManager viewManager, Player player0, boolean player2starts) {
        this.viewManager = viewManager;
        this.player0 = player0;
        this.player1 = new ComputerPlayer(viewManager, "COMPUTER");
        this.player2starts = player2starts;
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        round = 0;
        if (player2starts){
            currentPlayer=playerArrayList.get(1);}
        else {
            currentPlayer=playerArrayList.get(0);}
        board = new Board(this);
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() { return playerArrayList.get(getOtherPlayerIndex()); }

    public Player getPlayer0() {
        return player0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public int getCurrentPlayerIndex(){
        return currentPlayer.equals(playerArrayList.get(0)) ? 0 : 1;
    }

    public int getOtherPlayerIndex(){ return currentPlayer.equals(playerArrayList.get(0)) ? 1 : 0; }

    public void updateCurrentPlayer(){
        if(player2starts){
            currentPlayer = playerArrayList.get((round+1)%2);}
        else {
            currentPlayer = playerArrayList.get(round%2);
        }}

    public Board getBoard() {
        return board;
    }

    public void increaseRound(){
        round++;
        viewManager.getScoreView().increaseRound();
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public void play() {

        while (true){

            if (round == NUMBEROFSTONES*2){
                viewManager.getScoreView().updatePhase("Steine verschieben");
            }

            //viewManager.getScoreView().setPlayerLabelEffects(); // funktioniert noch nicht wie gewünscht

            System.out.println(board);

            if (movePhase && (board.countPlayersStones(getCurrentPlayerIndex()) <= 2
                    || !board.checkIfAbleToMove(getCurrentPlayerIndex()))){
                winGame();
                break;
            }

            System.out.println(getCurrentPlayer().getName() + " ist an der Reihe!");
            viewManager.getLogView().setStatusLabel(getCurrentPlayer().getName() + " ist an der Reihe!");

            setGamesPhaseBooleans();

            Position position = null;

            if (putPhase){
                try { position = currentPlayer.put(board, getCurrentPlayerIndex());}
                catch (InvalidPutException e){
                    e.printStackTrace();
                }
            }

            if (movePhase){
                try { position = currentPlayer.move(board, getCurrentPlayerIndex(),
                        board.countPlayersStones(getCurrentPlayerIndex())==3);}
                catch (InvalidMoveException | InvalidPutException e){
                    e.printStackTrace();
                }

            }

            if (board.checkMorris(position) && (board.isThereStoneToKill(getOtherPlayerIndex())
                    || (board.countPlayersStones(getOtherPlayerIndex())==3) && movePhase)){
                System.out.println(currentPlayer.getName() + " darf einen gegnerischen Stein entfernen");
                viewManager.getLogView().setStatusLabel(currentPlayer.getName() +
                        " darf einen gegnerischen Stein entfernen. Wähle den Stein, der entfernt werden soll");

                try { currentPlayer.kill(board, getOtherPlayerIndex()); }
                catch (InvalidKillException e) {
                    e.printStackTrace(); }
            }

            increaseRound();
            updateCurrentPlayer();

            }

    }


    private void setGamesPhaseBooleans(){
        if (round == NUMBEROFSTONES*2){
            putPhase = false;
            movePhase = true;}
    }


    private void winGame(){
        winner = playerArrayList.get(getOtherPlayerIndex());
        viewManager.getLogView().setStatusLabel(winner.getName() + " hat das Spiel gewonnen");
        viewManager.getFieldView().setDisable(true);
        System.out.println(winner.getName() + " hat das Spiel gewonnen!");
    }
}

