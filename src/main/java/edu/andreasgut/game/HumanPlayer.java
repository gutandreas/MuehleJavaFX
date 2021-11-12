package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

public class HumanPlayer extends Player {

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
