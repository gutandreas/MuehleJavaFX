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
    private Field3 field;
    private Field3 oldField;
    private boolean gameOver;
    boolean phase1 = true;
    boolean phase2 = false;
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
        field = new Field3(this);
        modus = 1;
    }

    public Game(ViewManager viewManager, Player player0) {
        this.viewManager = viewManager;
        this.player0 = player0;
        this.player1 = new Computer( "Computer");
        playerArrayList.add(0, player0);
        playerArrayList.add(1, player1);
        round = 0;
        currentPlayer=playerArrayList.get(0);
        field = new Field3(this);
        modus = 2;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void updateCurrentPlayer(){
        currentPlayer = playerArrayList.get(round%2);
    }

    public int getCurrentPlayerIndex(){
        return currentPlayer.equals(playerArrayList.get(0)) ? 0 : 1;
    }

    public Field3 getField() {
        return field;
    }

    public Field3 getOldField() {
        return oldField;
    }


    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void increaseRound(){
        round++;
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

    public void setOldField(Field3 oldField) {
        this.oldField = oldField;
    }

    public void play() throws InvalidFieldException {

        while (true){

            updateCurrentPlayer();

            oldField = (Field3) getField().clone();


            if (phase2==true && getField().numberOfStonesCurrentPlayer() <= 2){
                gameOver = true;
                winner = playerArrayList.get((getCurrentPlayerIndex()+1)%2);
                System.out.println(winner.getName() + " hat das Spiel gewonnen!");
            }

            setGamesPhaseBooleans();
            setCurrentPlayersJumpBoolean();

            System.out.println(getCurrentPlayer().getName() + " ist an der Reihe!");



            putOrMove();
            field.printField();
            if (field.checkTriple(oldField) && field.isThereStoneToKill()){
                //TODO:kill-methode anpassen
                System.out.println("Ein Stein wird gekillt");
            };



            increaseRound();}


    }


    private void setGamesPhaseBooleans(){
        if (round == NUMBEROFSTONES*2){
            phase1 = false;
            phase2 = true;}
    }

    private void setCurrentPlayersJumpBoolean(){
        if (round >= NUMBEROFSTONES*2 && getField().numberOfStonesCurrentPlayer()<=3){
            getCurrentPlayer().setAllowedToJump(true);
        }
    }

    private void putOrMove() throws InvalidFieldException {

        //SETZEN UND FAHREN: Menschlicher Spieler
        if(!(getCurrentPlayer() instanceof Computer)){

            if (phase1){
                CoordinatesInRepresentation tempCoords = viewManager.getFieldView().humanGraphicPut();
                field.putStone(tempCoords.getRing(), tempCoords.getField());


            }

            if(phase2){
                System.out.println("Ring Start:");
                int ringNow = scanner.nextInt();
                System.out.println("Feld Start:");
                int fieldNow = scanner.nextInt();
                System.out.println("Ring Ziel:");
                int ringDest= scanner.nextInt();
                System.out.println("Feld Ziel:");
                int fieldDest = scanner.nextInt();
                try {
                    field.move(ringNow,fieldNow,ringDest,fieldDest,getCurrentPlayer().isAllowedToJump());
                }
                catch (InvalidMoveException | InvalidFieldException | IndexOutOfBoundsException e){
                    System.out.println(e.getMessage());
                    putOrMove();
                }
            }
        }

        //SETZEN UND FAHREN: Computerspieler
        if (getCurrentPlayer() instanceof Computer){

            if (phase1){
                int[] temp = ((Computer) player1).compPutStone(field);
                field.putStone(temp[0], temp[1]);
                viewManager.getFieldView().computerGraphicPut(temp[0], temp[1]);
            }
        }
    }

    private void kill(){
        //STEIN ENTFERNEN: Menschlicher Spieler
        if(!(getCurrentPlayer() instanceof Computer)){
            viewManager.getFieldView().humanGraphicKill();}

        //STEIN ENTFERNEN: Computerspieler
        if(getCurrentPlayer() instanceof Computer){
            int[] temp = ((Computer) player1).compKillStone(field);
            try {
                field.killStone(temp[0], temp[1]);
            } catch (InvalidKillException e) {
                System.out.println(e.getMessage());
                kill();
            }
        }
    }
}

