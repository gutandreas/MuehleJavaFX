package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;
import javafx.geometry.Pos;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class OfflineGame extends Game {


    protected Player winner;
    protected int round;
    protected final int NUMBEROFSTONES = 9;
    protected Player currentPlayer;
    protected boolean putPhase = true;
    protected boolean movePhase = false;
    protected boolean movePhaseTake = true;
    protected boolean movePhaseRelase = false;
    protected boolean killPhase = false;
    protected boolean clickOkay = true;


    ArrayList<Player> playerArrayList = new ArrayList<>();

    public OfflineGame(ViewManager viewManager, Player player0, Player player1) {
        super(viewManager, player0, player1);
        currentPlayer = player0;
    }



    public OfflineGame(ViewManager viewManager, Player player0, boolean player2starts) {

        super(viewManager, player0, new ComputerPlayer(viewManager, "COMPUTER"));
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





    public void nextStep(Position clickedPosition) {


        if (clickOkay){

            clickOkay = false;

            if (round == NUMBEROFSTONES*2){
                viewManager.getScoreView().updatePhase("Steine verschieben");
            }

            if (killPhase){
                if (board.checkKill(clickedPosition, getOtherPlayerIndex())){
                    ((HumanPlayer) currentPlayer).setClickedKillPosition(clickedPosition);
                    currentPlayer.kill(board, getCurrentPlayerIndex(), getOtherPlayerIndex());
                    viewManager.getFieldView().graphicKill(clickedPosition);
                    updateGameState(false, true);
                    if (currentPlayer instanceof ComputerPlayer){
                        callComputer();
                    }
                    clickOkay = true;
                    killPhase = false;
                    return;}
                else {
                    System.out.println("Ungültiger Kill");
                    viewManager.getLogView().setStatusLabel("Auf diesem Feld kann kein Stein entfernt werden.");
                    clickOkay = true;
                    return;
                }
            }

            if (putPhase){
                if (board.checkPut(clickedPosition)){

                    ((HumanPlayer) currentPlayer).setClickedPutPosition(clickedPosition);
                    currentPlayer.put(board, getCurrentPlayerIndex());
                    viewManager.getFieldView().graphicPut(clickedPosition, getCurrentPlayerIndex(), 0);

                    if (board.checkMorris(clickedPosition) && board.isThereStoneToKill(getOtherPlayerIndex())){
                        killPhase = true;
                        clickOkay = true;
                        viewManager.getFieldView().setKillCursor();
                        viewManager.getLogView().setStatusLabel(currentPlayer.getName() + " darf einen gegnerischen Stein entfernen.");
                        return;
                    }
                    else {
                        updateGameState(true, false);
                        if (currentPlayer instanceof ComputerPlayer){
                            callComputer();
                        }
                        else {
                            clickOkay = true;
                        }
                        return;
                    }

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
                    ((HumanPlayer) currentPlayer).setClickedMovePositionTakeStep(clickedPosition);
                    movePhaseTake = false;
                    movePhaseRelase = true;
                    clickOkay = true;
                    viewManager.getFieldView().setPutCursor();
                    viewManager.getFieldView().graphicKill(clickedPosition);
                    return;
                }
                if (movePhaseRelase){
                    ((HumanPlayer) currentPlayer).setClickedMovePositionReleaseStep(clickedPosition);
                    boolean allowedToJump = board.countPlayersStones(getCurrentPlayerIndex()) == 3;
                    Move move = currentPlayer.move(board, getCurrentPlayerIndex(), allowedToJump);
                    if (board.checkMove(move,allowedToJump)){
                        board.move(move, getCurrentPlayerIndex());
                        viewManager.getFieldView().graphicMove(move, getCurrentPlayerIndex());
                        updateGameState(false,false);
                        movePhaseTake = true;
                        movePhaseRelase = false;
                        if (board.checkMorris(move.getTo()) && board.isThereStoneToKill(getOtherPlayerIndex())){
                            killPhase = true;
                            clickOkay = true;
                            return;
                        }
                        if (currentPlayer instanceof ComputerPlayer){
                            callComputer();
                            return;
                        }
                    }
                    else {
                        System.out.println("Kein gültiger Move");
                        viewManager.getLogView().setStatusLabel("Das ist kein gültiger Zug");
                        clickOkay = true;
                    }
                }


            }

            System.out.println(board);



            //viewManager.getScoreView().setPlayerLabelEffects(); // funktioniert noch nicht wie gewünscht

            /*Position positionToCheckMorris = null;

            if (putPhase){
                positionToCheckMorris = put(position);
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

            if (movePhase &&
                    (board.countPlayersStones(getCurrentPlayerIndex()) <= 2
                            || !board.checkIfAbleToMove(getCurrentPlayerIndex()))){
                winGame();
                return;
            }

            increaseRound();
            updateCurrentPlayer();*/}
        else {
            System.out.println("Kein Klick möglich");
            viewManager.getLogView().setStatusLabel("Warten Sie bis Sie an der Reihe sind.");
        }

    }

    public void callComputer(){
        if (round < NUMBEROFSTONES*2){
            boolean kill = false;
            Position computerPutPosition = currentPlayer.put(board,getCurrentPlayerIndex());
            board.putStone(computerPutPosition, getCurrentPlayerIndex());
            viewManager.getFieldView().graphicPut(computerPutPosition, getCurrentPlayerIndex(), 0);
            if (board.checkMorris(computerPutPosition) && board.isThereStoneToKill(getOtherPlayerIndex())){
                Position computerKillPosition = currentPlayer.kill(board,getCurrentPlayerIndex(), getOtherPlayerIndex());
                board.clearStone(computerKillPosition);
                viewManager.getFieldView().graphicKill(computerKillPosition);
                kill = true;
            }
            clickOkay = true;
            updateGameState(true, kill);
            return;
        }
        else {
            boolean allowedToJump = board.countPlayersStones(getCurrentPlayerIndex()) == 3;
            boolean kill = false;
            Move computerMove = currentPlayer.move(board, getCurrentPlayerIndex(), allowedToJump);
            if (board.checkMove(computerMove, allowedToJump)){
                board.move(computerMove, getCurrentPlayerIndex());
                viewManager.getFieldView().graphicMove(computerMove, getCurrentPlayerIndex());
                if (board.checkMorris(computerMove.getTo()) && board.isThereStoneToKill(getOtherPlayerIndex())){
                    Position computerKillPosition = currentPlayer.kill(board,getCurrentPlayerIndex(), getOtherPlayerIndex());
                    board.clearStone(computerKillPosition);
                    viewManager.getFieldView().graphicKill(computerKillPosition);
                    kill = true;
                }
                clickOkay = true;
                updateGameState(false, kill);
                return;
            }
        }

    }







    private Position put(Position position){
        Position putPosition = currentPlayer.put(board, getCurrentPlayerIndex());
        Position positionToCheckMorris;

        if (board.checkPut(putPosition)){
            board.putStone(putPosition, getCurrentPlayerIndex());

            if (currentPlayer instanceof ComputerPlayer){
                viewManager.getFieldView().graphicPut(putPosition, getCurrentPlayerIndex(), 500);
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
        Move move = currentPlayer.move(board, getCurrentPlayerIndex(),
                board.countPlayersStones(getCurrentPlayerIndex())==3);
        Position positionToCheckMorris;

        if (board.checkMove(move, board.countPlayersStones(getCurrentPlayerIndex())==3)){
            board.move(move, getCurrentPlayerIndex());

            if (currentPlayer instanceof ComputerPlayer){
                viewManager.getFieldView().graphicMove(move, getCurrentPlayerIndex());
            }
        }
        else {
            throw new InvalidMoveException("Das ist ein ungültiger Zug.");
        }

        positionToCheckMorris = move.getTo();

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
                viewManager.getFieldView().graphicKill(killPosition);
            }
        }
        else {
            throw new InvalidKillException("Auf diesem Feld befindet sich kein gegnerischer Stein");
        }
    }



}