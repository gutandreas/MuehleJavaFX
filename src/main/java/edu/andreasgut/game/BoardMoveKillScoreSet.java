package edu.andreasgut.game;


public class BoardMoveKillScoreSet {

    private Board board;
    private Move move;
    private Position kill;
    private int score;
    private BoardMoveKillScoreSet parent;

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

    public BoardMoveKillScoreSet getParent() {
        return parent;
    }

    public void setParent(BoardMoveKillScoreSet parent) {
        this.parent = parent;
    }
}
