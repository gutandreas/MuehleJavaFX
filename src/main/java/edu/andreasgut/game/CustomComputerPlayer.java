package edu.andreasgut.game;

import edu.andreasgut.communication.Messenger;
import edu.andreasgut.view.ViewManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

public class CustomComputerPlayer extends ComputerPlayer{


    public CustomComputerPlayer(ViewManager viewManager, String name, String uuid, ScorePoints putPoints, ScorePoints movePoints, int levelLimit) {
        super(viewManager, name, uuid, putPoints, movePoints, levelLimit);
    }

    //TODO: Implementieren Sie die Methode put
    @Override
    Position put(Board board, int playerIndex){

        Position position = null;

        return position;
    }


    //TODO: Implementieren Sie die Methode move
    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {

        Move move = null;

        return move;
    }


    //TODO: Implementieren Sie die Methode kill
    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {

        Position position = null;

        return position;
    }



    //!!!NICHT VERÄNDERN!!!
    @Override
    public void prepareKill(ViewManager viewManager) {
        viewManager.getLogView().getNextComputerStepButton().setKill(viewManager);
    }

    @Override
    public void preparePutOrMove(ViewManager viewManager) {
        if (viewManager.getGame().isPutPhase()){
            viewManager.getLogView().getNextComputerStepButton().setPut(viewManager);
        }
        else {
            viewManager.getLogView().getNextComputerStepButton().setMove(viewManager);
        }
    }

    @Override
    public void triggerKill(ViewManager viewManager) {
        Game game = viewManager.getGame();
        Position position = kill(game.getBoard(), game.getCurrentPlayerIndex(), game.getOtherPlayerIndex());
        boolean killOkay = position != null && game.getBoard().isKillPossibleAt(position, game.getOtherPlayerIndex());

        if (killOkay){
            Messenger.sendKillMessage(viewManager, position);}
        else {
            System.out.println("Ungültiger Kill wird durch zufälligen Kill ersetzt");
            showAlert();
            LinkedList<Position> allPossibleKills = Advisor.getAllPossibleKills(game.getBoard(), game.getCurrentPlayerIndex());
            Random random = new Random();
            Position randomPosition = allPossibleKills.get(random.nextInt(allPossibleKills.size()));
            Messenger.sendKillMessage(viewManager, randomPosition);
        }
    }


    @Override
    public void triggerPut(ViewManager viewManager){

        Game game = viewManager.getGame();
        Position position = put(game.getBoard(), game.getCurrentPlayerIndex());
        boolean putOkay = position != null && game.getBoard().isPutPossibleAt(position);

        if (putOkay){
            Messenger.sendPutMessage(viewManager, position);
        }
        else {
            System.out.println("Ungültiger Put wird durch zufälligen Put ersetzt");
            showAlert();
            LinkedList<Position> allPossiblePuts = Advisor.getFreePositions(game.getBoard());
            Random random = new Random();
            Position randomPosition = allPossiblePuts.get(random.nextInt(allPossiblePuts.size()));
            Messenger.sendPutMessage(viewManager, randomPosition);
        }
    }

    @Override
    public void triggerMove(ViewManager viewManager){

        Game game = viewManager.getGame();
        boolean allowedToJump = game.getBoard().numberOfStonesOf(game.getCurrentPlayerIndex()) == 3;
        Move move = move(game.getBoard(), game.getCurrentPlayerIndex(), allowedToJump);
        boolean moveOkay = move != null && game.getBoard().isMovePossibleAt(move.getFrom(), move.getTo(), allowedToJump);

        if (moveOkay){
            Messenger.sendMoveMessage(viewManager, move);
        }
        else {
            System.out.println("Ungültiger Move wurde durch zufälligen Move ersetzt");
            showAlert();
            LinkedList<Move> allPossibleMoves = Advisor.getAllPossibleMoves(game.getBoard(), game.getCurrentPlayerIndex());
            Random random = new Random();
            Move randomMove = allPossibleMoves.get(random.nextInt(allPossibleMoves.size()));
            Messenger.sendMoveMessage(viewManager, randomMove);
        }
    }

    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Der Computer hat einen ungültigen Zug berechnet. " +
                "Um das Spiel trotzdem weiterspielen zu können, wurde der Zug durch einen zufälligen Zug ersetzt.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            alert.close();
        }
    }
}
