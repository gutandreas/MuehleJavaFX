package edu.andreasgut.game;

public class OpenMorris {

    private Position firstPosition, secondPosition, thirdPosition, gapPosition;

    public OpenMorris(Position firstPosition, Position secondPosition, Position thirdPosition, Position gapPosition) {
        if (firstPosition.compareTo(secondPosition) > 0 || firstPosition.compareTo(thirdPosition) > 0
                || secondPosition.compareTo(thirdPosition) > 0){
            throw new IllegalArgumentException();
        }

        this.firstPosition = firstPosition;
        this.secondPosition = secondPosition;
        this.thirdPosition = thirdPosition;
        this.gapPosition = gapPosition;
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

    public Position getGapPosition() {
        return gapPosition;
    }
}
