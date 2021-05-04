package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

public class HumanPlayer extends Player{

    public HumanPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }

    @Override
    Position put(Board board, int playerIndex) {
        Position position = viewManager.getFieldView().humanGraphicPut();

        return position;
    }

    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {
        Move move = viewManager.getFieldView().humanGraphicMove();

        return move;
    }

    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {
        Position position = viewManager.getFieldView().humanGraphicKill();

        return position;
    }
}
