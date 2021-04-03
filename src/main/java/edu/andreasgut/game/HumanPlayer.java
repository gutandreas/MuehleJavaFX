package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

public class HumanPlayer extends Player{

    public HumanPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }

    @Override
    Position move(Board board, int playerIndex, boolean allowedToJump) throws InvalidMoveException, InvalidPutException {
        Position[] positions = viewManager.getFieldView().humanGraphicMove();
        board.move(positions[0], positions[1], viewManager.getGame().getCurrentPlayerIndex(), allowedToJump);
        return positions[1];
    }

    @Override
    Position put(Board board, int playerIndex) throws InvalidPutException {
        Position position = viewManager.getFieldView().humanGraphicPut();

        board.putStone(position, playerIndex);
        viewManager.getScoreView().increaseStonesPut();

        return position;
    }

    @Override
    void kill(Board board, int otherPlayerIndex) throws InvalidKillException {
        Position position = viewManager.getFieldView().humanGraphicKill();
        board.killStone(position, otherPlayerIndex);

        viewManager.getScoreView().increaseStonesLost();
        viewManager.getScoreView().increaseStonesKilled();
    }
}
