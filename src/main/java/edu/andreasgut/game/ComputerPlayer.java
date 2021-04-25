package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

public class ComputerPlayer extends Player {


    public ComputerPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }



    @Override
    Position put(Board board, int playerIndex) {
        Position position = new Position();
        int i;
        int j;

        // bildet Mühle wenn 2 Steine über Ringe hinweg
        for (int field = 1; field < 8; field+=2) {
            for (int row = 0; row < 3; row++) {
                if (board.getArray()[row][field] == viewManager.getGame().getCurrentPlayerIndex()
                        && board.getArray()[(row+1)%3][field] == viewManager.getGame().getCurrentPlayerIndex()
                        && board.isFieldFree(new Position((row+2)%3, field))) {
                    return new Position((row+2)%3, field); }
            }
        }

        // blockt wenn 2 Steine innerhalb von Ring nebeneinander
        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {
                if (board.getArray()[row][field] == viewManager.getGame().getCurrentPlayerIndex()
                        && board.getArray()[row][(field + 1) % 8] == viewManager.getGame().getCurrentPlayerIndex()) {
                    if ((field % 2) == 0 && board.isFieldFree(new Position(row ,(field + 2) % 8))) {
                        return new Position(row, (field + 2) % 8);
                    }
                    if ((field % 2) == 1 && board.isFieldFree(new Position(row, (field + 7) % 8 ))) {
                        return new Position(row, (field + 7) % 8);
                    }
                }
            }
        }


        // blockt wenn 2 Steine über Ringe hinweg
        for (int field = 1; field < 8; field+=2) {
            for (int row = 0; row < 3; row++) {
                if (board.getArray()[row][field] == viewManager.getGame().getOtherPlayerIndex()
                    && board.getArray()[(row+1)%3][field] == viewManager.getGame().getOtherPlayerIndex()
                    && board.isFieldFree(new Position((row+2)%3, field))) {
                        return new Position((row+2)%3, field); }
            }
        }

        // blockt wenn 2 Steine innerhalb von Ring nebeneinander
        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {
                if (board.getArray()[row][field] == viewManager.getGame().getOtherPlayerIndex()
                        && board.getArray()[row][(field + 1) % 8] == viewManager.getGame().getOtherPlayerIndex()) {
                    if ((field % 2) == 0 && board.isFieldFree(new Position(row ,(field + 2) % 8))) {
                        return new Position(row, (field + 2) % 8);
                    }
                    if ((field % 2) == 1 && board.isFieldFree(new Position(row, (field + 7) % 8 ))) {
                        return new Position(row, (field + 7) % 8);
                    }
                }
            }
        }

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (board.getArray()[i][j] == 9) {
                    position.setRing(i);
                    position.setField(j);

                    return position;
                }
            }
        }

        return position;
    }


    @Override
    Position[] move(Board board, int playerIndex, boolean allowedToJump) {
        Position[] positions = new Position[2];

        loop:{
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 7; j++){
                if (board.isThisMyStone(new Position(i,j),playerIndex)){
                    if(board.isFieldFree(new Position(i,(j+1)%8))){
                        positions[0] = new Position(i, j);
                        positions[1] = new Position(i, (j+1)%8);
                        System.out.println(i+" "+j);
                        break loop;
                    }
                    if(board.isFieldFree(new Position(i,(j+7)%8))){
                        positions[0] = new Position(i, j);
                        positions[1] = new Position(i, (j+7)%8);
                        System.out.println(i+" "+j);
                        break loop;
                    }


                }
        }}}
        return positions;
    }


    @Override
    Position kill(Board board, int otherPlayerIndex) {
        Position position = new Position();
        int i;
        int j;

        loop:{
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (board.checkKill(new Position(i,j), otherPlayerIndex)) {
                    position.setRing(i);
                    position.setField(j);

                    break loop;
                }
            }
        }}
        return position;
    }

}
