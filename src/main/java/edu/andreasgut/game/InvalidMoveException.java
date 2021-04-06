package edu.andreasgut.game;

public class InvalidMoveException extends RuntimeException{

    public InvalidMoveException(String message) {
        super(message);
    }
}
