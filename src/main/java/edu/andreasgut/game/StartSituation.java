package edu.andreasgut.game;

import java.util.LinkedList;

public class StartSituation {

    private int round;
    private Board board;
    private String description;

    public StartSituation(int round, Board board, String description) {
        this.round = round;
        this.board = board;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public static StartSituation[] produceStartSituations(){
        StartSituation[] startSituations = new StartSituation[5];

        Board board0 = new BoardImpl();

        StartSituation startSituation0 = new StartSituation(0, board0, "Leeres Spielfeld:");

        startSituations[0] = startSituation0;

        Board board1 = new BoardImpl();
        board1.putStone(new Position(0, 1), 0);
        board1.putStone(new Position(1, 1), 0);
        board1.putStone(new Position(2, 0), 0);
        board1.putStone(new Position(1, 7), 0);
        board1.putStone(new Position(1, 6), 0);
        board1.putStone(new Position(1, 5), 0);
        board1.putStone(new Position(1, 4), 0);
        board1.putStone(new Position(0, 6), 0);
        board1.putStone(new Position(0, 4), 0);

        board1.putStone(new Position(0, 3), 1);
        board1.putStone(new Position(2, 1), 1);
        board1.putStone(new Position(0, 7), 1);


        StartSituation startSituation1 = new StartSituation(48, board1, "Spielfeld, das zu einem Computerfehler führt...");

        startSituations[1] = startSituation1;


        Board board2 = new BoardImpl();
        board2.putStone(new Position(1, 0), 0);
        board2.putStone(new Position(1, 1), 0);
        board2.putStone(new Position(2, 1), 0);
        board2.putStone(new Position(2, 2), 0);
        board2.putStone(new Position(1, 3), 0);
        board2.putStone(new Position(1, 7), 0);


        board2.putStone(new Position(0, 1), 1);
        board2.putStone(new Position(1, 2), 1);
        board2.putStone(new Position(2, 0), 1);
        board2.putStone(new Position(1,4), 1);
        board2.putStone(new Position(1,6), 1);


        StartSituation startSituation2 = new StartSituation(15, board2, "Spielfeld, das zu einem Computerfehler führt...");

        startSituations[2] = startSituation2;

        return startSituations;
    }

}
