package edu.andreasgut.game;

public class Line {

    private final Position firstPosition, secondPosition, thirdPosition;

    public Line(Position firstPosition, Position secondPosition, Position thirdPosition) {
        if (firstPosition.compareTo(secondPosition) > 0 || firstPosition.compareTo(thirdPosition) > 0
                || secondPosition.compareTo(thirdPosition) > 0) {
            throw new IllegalArgumentException();
        }

        this.firstPosition = firstPosition;
        this.secondPosition = secondPosition;
        this.thirdPosition = thirdPosition;
    }

    public Position getFirstPosition() {
        return firstPosition;
    }

    public Position getSecondPosition() {
        return secondPosition;
    }

    public Position getThirdPosition() {
        return thirdPosition;
    }

    @Override
    public String toString() {
        return "Linie mit den Positionen " + firstPosition +
                ", " + secondPosition +
                ", " + thirdPosition;
    }
}
