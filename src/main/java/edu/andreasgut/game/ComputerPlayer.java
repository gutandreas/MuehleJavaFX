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
    void kill(Board board, int playerIndex) throws InvalidKillException {
        Position position = new Position();
        int i;
        int j;

        loop:{
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (board.getArray()[i][j] == 0 && board.checkKill(new Position(i,j))) {
                    position.setRing(i);
                    position.setField(j);

                    board.killStone(position, playerIndex);

                    viewManager.getFieldView().computerGraphicKill(position);

                    viewManager.getScoreView().increaseStonesKilled();
                    viewManager.getScoreView().increaseStonesLost();
                    break loop;

                }
            }
        }}
    }



    //Entfernt den nächsten gegnerischen Stein
    public Position compKillStone(Board board) {
        Position position = new Position();
        int i;
        int j;

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (board.getArray()[i][j] == 0 && board.checkKill(new Position(i,j))) {
                    position.setRing(i);
                    position.setField(j);

                    return position;
                }
            }
        }
        return position;
    }

}
