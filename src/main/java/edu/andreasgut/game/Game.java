package edu.andreasgut.game;

import edu.andreasgut.view.CoordinatesInRepresentation;
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
    private Board oldField;
    boolean putPhase = true;
    boolean movePhase = false;
    private Scanner scanner = new Scanner(System.in);
    private int modus;
    private ViewManager viewManager;

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
        modus = 1;
    }

    public Game(ViewManager viewManager, Player player0) {
        this.viewManager = viewManager;
        this.player0 = player0;
        this.player1 = new Computer( "COMPUTER");
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        round = 0;
        currentPlayer=playerArrayList.get(0);
        board = new Board(this);
        modus = 2;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return playerArrayList.get((getCurrentPlayerIndex()+1)%2);
    }

    public int getCurrentPlayerIndex(){
        return currentPlayer.equals(playerArrayList.get(0)) ? 0 : 1;
    }

    public int getOtherPlayerIndex(){ return currentPlayer.equals(playerArrayList.get(0)) ? 1 : 0; }

    public void updateCurrentPlayer(){ currentPlayer = playerArrayList.get(round%2);}

    public Board getBoard() {
        return board;
    }

    public Board getOldField() {
        return oldField;
    }


    public boolean isPutPhase() {
        return putPhase;
    }

    public boolean isMovePhase() {
        return movePhase;
    }

    public Player getWinner() {
        return winner;
    }

    public void increaseRound(){
        round++;
        viewManager.getScoreView().increaseRound();
    }

    public Player getPlayer0() {
        return player0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public void setOldField(Board oldField) {
        this.oldField = oldField;
    }

    public void play() throws InvalidFieldException {

        while (true){

            if (round == NUMBEROFSTONES*2){
                viewManager.getScoreView().updatePhase("Steine verschieben");
            }

            //viewManager.getScoreView().setPlayerLabelEffects(); // funktioniert noch nicht wie gewünscht

            oldField = (Board) getBoard().clone();
            board.printField();

            if (movePhase ==true && (board.numberOfStonesCurrentPlayer() <= 2 || !board.checkIfAbleToMove(currentPlayer))){
                winGame();
                break;
            }

            System.out.println(getCurrentPlayer().getName() + " ist an der Reihe!");
            viewManager.getLogView().setStatusLabel(getCurrentPlayer().getName() + " ist an der Reihe!");

            setGamesPhaseBooleans();
            setCurrentPlayersJumpBoolean();

            putOrMove();

            if (board.checkTriple(oldField) && (board.isThereStoneToKill(getOtherPlayerIndex())
                    || playerArrayList.get((getCurrentPlayerIndex()+1)%2).isAllowedToJump())){
                System.out.println(currentPlayer.getName() + " darf einen gegnerischen Stein entfernen");
                viewManager.getLogView().setStatusLabel(currentPlayer.getName() +
                        " darf einen gegnerischen Stein entfernen. Wähle den Stein, der entfernt werden soll");
                kill();
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

    private void setCurrentPlayersJumpBoolean(){
        if (round >= NUMBEROFSTONES*2 && getBoard().numberOfStonesCurrentPlayer()<=3){
            getCurrentPlayer().setAllowedToJump(true);
        }
    }

    private void winGame(){
        winner = playerArrayList.get((getCurrentPlayerIndex()+1)%2);
        viewManager.getLogView().setStatusLabel(winner.getName() + " hat das Spiel gewonnen");
        viewManager.getFieldView().setDisable(true);
        System.out.println(winner.getName() + " hat das Spiel gewonnen!");
    }

    private void putOrMove() throws InvalidFieldException {

        //SETZEN UND FAHREN: Menschlicher Spieler
        if(!(getCurrentPlayer() instanceof Computer)){

            if (putPhase){
                Position position = viewManager.getFieldView().humanGraphicPut();
                board.putStone(position, getCurrentPlayerIndex());
                viewManager.getScoreView().increaseStonesPut();
            }

            if(movePhase){
                try {
                    Position[] positions = viewManager.getFieldView().humanGraphicMove();
                    board.move(positions[0], positions[1], getCurrentPlayerIndex());
                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                    putOrMove();
                }
            }
        }

        //SETZEN UND FAHREN: Computerspieler
        if (getCurrentPlayer() instanceof Computer){

            if (putPhase){
                Position position = ((Computer) player1).compPutStone(board);
                board.putStone(position, getCurrentPlayerIndex());
                viewManager.getFieldView().computerGraphicPut(position);
                viewManager.getScoreView().increaseStonesPut();
            }
        }


    }

    private void kill(){
        //STEIN ENTFERNEN: Menschlicher Spieler
        if(!(getCurrentPlayer() instanceof Computer)){
            Position position = viewManager.getFieldView().humanGraphicKill();
            try {
                board.killStone(position, getOtherPlayerIndex());
            } catch (InvalidKillException e) {
                e.printStackTrace();
            }
        }

        //STEIN ENTFERNEN: Computerspieler
        if(getCurrentPlayer() instanceof Computer){
            Position position = ((Computer) player1).compKillStone(board);
            try {
                board.killStone(position, getOtherPlayerIndex());
            } catch (InvalidKillException e) {
                System.out.println(e.getMessage());
                kill();
            }
            viewManager.getFieldView().computerGraphicKill(position);
        }

        viewManager.getScoreView().increaseStonesLost();
        viewManager.getScoreView().increaseStonesKilled();
    }
}

