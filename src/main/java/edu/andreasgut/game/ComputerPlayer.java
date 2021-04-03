package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

public class ComputerPlayer extends Player {


    public ComputerPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }

    @Override
    Position move(Board board, int playerIndex, boolean allowedToJump) throws InvalidMoveException, InvalidPutException {

        return null;
    }

    @Override
    Position put(Board board, int playerIndex) throws InvalidPutException {
        Position position = new Position();
        int i;
        int j;

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (board.getArray()[i][j] == 9) {
                    position.setRing(i);
                    position.setField(j);


                    board.putStone(position, playerIndex);

                    viewManager.getFieldView().computerGraphicPut(position);
                    viewManager.getScoreView().increaseStonesPut();

                    return position;
                }
            }
        }



        return position;
    }

    @Override
    void kill(Board board, int otherPlayerIndex) throws InvalidKillException {
        Position position = new Position();
        int i;
        int j;

        loop:{
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (board.checkKill(new Position(i,j), otherPlayerIndex)) {
                    position.setRing(i);
                    position.setField(j);

                    board.killStone(position, otherPlayerIndex);

                    viewManager.getFieldView().computerGraphicKill(position);

                    viewManager.getScoreView().increaseStonesKilled();
                    viewManager.getScoreView().increaseStonesLost();
                    break loop;

                }
            }
        }}
    }




}
