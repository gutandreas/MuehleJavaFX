package edu.andreasgut.game;

import edu.andreasgut.online.MessageHandler;
import edu.andreasgut.online.MessageInterface;
import edu.andreasgut.view.ViewManager;
import javafx.application.Platform;
import org.json.JSONObject;

public class HumanPlayer extends Player implements MessageHandler {

    private Position clickedPutPosition, clickedKillPosition, clickedMovePositionTakeStep, clickedMovePositionReleaseStep;

    public HumanPlayer(ViewManager viewManager, String name, boolean local) {
        super(viewManager, name, local);
    }

    @Override
    Position put(Board board, int playerIndex) {
        board.putStone(clickedPutPosition, playerIndex);

        return clickedPutPosition;
    }

    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {
        Move move = new Move(clickedMovePositionTakeStep, clickedMovePositionReleaseStep);

        return move;
    }

    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {
        board.clearStone(clickedKillPosition);

        return clickedKillPosition;
    }




    public void setClickedPutPosition(Position clickedPutPosition) {
        this.clickedPutPosition = clickedPutPosition;
    }

    public void setClickedKillPosition(Position clickedKillPosition) {
        this.clickedKillPosition = clickedKillPosition;
    }

    public void setClickedMovePositionTakeStep(Position clickedMovePositionTakeStep) {
        this.clickedMovePositionTakeStep = clickedMovePositionTakeStep;
    }

    public void setClickedMovePositionReleaseStep(Position clickedMovePositionReleaseStep) {
        this.clickedMovePositionReleaseStep = clickedMovePositionReleaseStep;
    }


    @Override
    public void prepareKill(ViewManager viewManager) {

        //lokaler Spieler
        if (viewManager.getGame().getCurrentPlayer().isLocal()){
            viewManager.getGame().setClickOkay(true);
            viewManager.getGame().setKillPhase(true);
            viewManager.getFieldView().setKillCursor();
        }
        //nicht lokaler Spieler
        else {
            viewManager.getGame().setClickOkay(false);
        }
    }

    @Override
    public void prepareNextPutOrMove(ViewManager viewManager) {
        if (viewManager.getGame().getCurrentPlayer().isLocal()) {
            viewManager.getGame().setClickOkay(true);
        }
    }
}
