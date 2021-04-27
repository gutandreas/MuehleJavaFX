package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;
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
    private final ViewManager viewManager;
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


    public Player getOtherPlayer() {
        return playerArrayList.get(getOtherPlayerIndex());
    }


    public Player getPlayer0() {
        return player0;
    }


    public Player getPlayer1() {
        return player1;
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


    public void increaseRound(){
        round++;
        viewManager.getScoreView().increaseRound();
    }


    public ViewManager getViewManager() {
        return viewManager;
    }


    public void play() {

        while (true){

            System.out.println(board);

            if (round == NUMBEROFSTONES*2){
                viewManager.getScoreView().updatePhase("Steine verschieben");
            }

            //viewManager.getScoreView().setPlayerLabelEffects(); // funktioniert noch nicht wie gewünscht

            if (movePhase &&
                    (board.countPlayersStones(getCurrentPlayerIndex()) <= 2
                    || !board.checkIfAbleToMove(getCurrentPlayerIndex()))){
                winGame();
                break;
            }

            System.out.println(getCurrentPlayer().getName() + " ist an der Reihe!");
            viewManager.getLogView().setStatusLabel(getCurrentPlayer().getName() + " ist an der Reihe!");

            setGamesPhaseBooleans();

            Position positionToCheckMorris = null;

            if (putPhase){
                positionToCheckMorris = put();
            }

            if (movePhase && board.checkIfAbleToMove(getCurrentPlayerIndex())){
                positionToCheckMorris = move();
            }

            if (positionToCheckMorris != null
                    && board.checkMorris(positionToCheckMorris)
                    && (board.isThereStoneToKill(getOtherPlayerIndex())
                    || (board.countPlayersStones(getOtherPlayerIndex())==3) && movePhase)){
                kill();
            }

            increaseRound();
            updateCurrentPlayer();

            }
    }


    private Position put(){
        Position putPosition = currentPlayer.put(board, getCurrentPlayerIndex());
        Position positionToCheckMorris;

        if (board.checkPut(putPosition)){
            board.putStone(putPosition, getCurrentPlayerIndex());

            if (currentPlayer instanceof ComputerPlayer){
                viewManager.getFieldView().computerGraphicPut(putPosition);
            }

            viewManager.getScoreView().increaseStonesPut();
        }
        else {
            throw new InvalidPutException("Dieses Feld ist nicht frei.");
        }

        positionToCheckMorris = putPosition;

        return positionToCheckMorris;
    }


    private Position move(){
        Position[] movePositions = currentPlayer.move(board, getCurrentPlayerIndex(),
                board.countPlayersStones(getCurrentPlayerIndex())==3);
        Position positionToCheckMorris;

        if (board.checkMove(movePositions[0], movePositions[1],
                board.countPlayersStones(getCurrentPlayerIndex())==3)){
            board.move(movePositions[0], movePositions[1], getCurrentPlayerIndex());

            if (currentPlayer instanceof ComputerPlayer){
                viewManager.getFieldView().computerGraphicMove(movePositions);
            }
        }
        else {
            throw new InvalidMoveException("Das ist ein ungültiger Zug.");
        }

        positionToCheckMorris = movePositions[1];

        return positionToCheckMorris;
    }


    private void kill(){
        System.out.println(currentPlayer.getName() + " darf einen gegnerischen Stein entfernen");
        viewManager.getLogView().setStatusLabel(currentPlayer.getName() +
                " darf einen gegnerischen Stein entfernen. Wähle den Stein, der entfernt werden soll");

        Position killPosition = currentPlayer.kill(board, getCurrentPlayerIndex(), getOtherPlayerIndex());

        if(board.checkKill(killPosition, getOtherPlayerIndex())){
            board.clearStone(killPosition);
            viewManager.getScoreView().increaseStonesKilled();
            viewManager.getScoreView().increaseStonesLost();

            if (currentPlayer instanceof ComputerPlayer){
                viewManager.getFieldView().computerGraphicKill(killPosition);
            }
        }
        else {
            throw new InvalidKillException("Auf diesem Feld befindet sich kein gegnerischer Stein");
        }
    }


    private void setGamesPhaseBooleans(){
        if (round == NUMBEROFSTONES*2){
            putPhase = false;
            movePhase = true;}
    }


    private void winGame(){
        winner = getOtherPlayer();
        viewManager.getLogView().setStatusLabel(winner.getName() + " hat das Spiel gewonnen");
        viewManager.getFieldView().setDisable(true);
        System.out.println(winner.getName() + " hat das Spiel gewonnen!");
    }
}