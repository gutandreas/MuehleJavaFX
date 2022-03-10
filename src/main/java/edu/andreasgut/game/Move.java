package edu.andreasgut.game;

public class Move {
    private final Position from, to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Zug von Feld " + getFrom() + " nach " + getTo();
    }
}
