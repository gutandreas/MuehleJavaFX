package edu.andreasgut.game;

import java.util.LinkedList;

public class BoardMoveKillScoreSet {

    private Board board;
    private Move move;
    private Position kill;
    private int score;
    private LinkedList<BoardMoveKillScoreSet> nextSets = new LinkedList<>();

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Position getKill() {
        return kill;
    }

    public void setKill(Position kill) {
        this.kill = kill;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LinkedList<BoardMoveKillScoreSet> getNextSets() {
        return nextSets;
    }

    public void addNextSet(BoardMoveKillScoreSet boardMoveKillScoreSet){
        nextSets.add(boardMoveKillScoreSet);
    }
}
