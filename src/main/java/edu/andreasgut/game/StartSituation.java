package edu.andreasgut.game;

import java.util.LinkedList;

public class StartSituation {

    private int round;
    private Board board;

    public StartSituation(int round, Board board) {
        this.round = round;
        this.board = board;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Board getBoard() {
        return board;
    }



    public static StartSituation[] produceStartSituations(){
        StartSituation[] startSituations = new StartSituation[5];

        Board board = new Board();
        board.putStone(new Position(0, 1), 0);
        board.putStone(new Position(1, 1), 0);
        board.putStone(new Position(2, 0), 0);
        board.putStone(new Position(1, 7), 0);
        board.putStone(new Position(1, 6), 0);
        board.putStone(new Position(1, 5), 0);
        board.putStone(new Position(1, 4), 0);
        board.putStone(new Position(0, 6), 0);
        board.putStone(new Position(0, 4), 0);

        board.putStone(new Position(0, 3), 1);
        board.putStone(new Position(2, 1), 1);
        board.putStone(new Position(0, 7), 1);


        StartSituation startSituation = new StartSituation(48, board);

        startSituations[1] = startSituation;

        return startSituations;
    }

}
