package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

public class HumanPlayer extends Player{

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
}
