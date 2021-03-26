package edu.andreasgut.game;

public class Position {
    int ring;
    int field;

    public Position() {
    }

    public Position(int ring, int field) {
        this.ring = ring;
        this.field = field;
    }

    public int getRing() {
        return ring;
    }

    public int getField() {
        return field;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public void setField(int field) {
        this.field = field;
    }
}
