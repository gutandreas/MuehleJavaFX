package edu.andreasgut.game;

import java.util.LinkedList;

public class Advisor {

    static boolean checkEnemyOpenMorris(Board board, int ownPlayerIndex){
        return !getMyEnemysOpenMorrisList(board, ownPlayerIndex).isEmpty();
    }


    static public LinkedList<Position> getMyEnemysOpenMorrisList(Board board, int ownPlayerIndex){

        LinkedList<Position> positionList = new LinkedList<>();
        for (int quarter = 0; quarter < 4; quarter++) {

            //Powerpoint rot
            for (int startRing = 0; startRing < 2; startRing++) {
                if (board.isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position(startRing + 1, (quarter * 2 + 1) % 8), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 2) % 8), ownPlayerIndex)
                        && board.isFieldFree(new Position(startRing, (quarter * 2 + 1) % 8))) {

                    positionList.add(new Position(startRing, (quarter * 2 + 1) % 8));
                    System.out.println("Rote offene Mühle erkannt");
                }}

            //Powerpoint blau
            for (int startRing = 1; startRing < 3; startRing++) {
                if (board.isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position(startRing - 1, (quarter * 2 + 1) % 8), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 2) % 8), ownPlayerIndex)
                        && board.isFieldFree(new Position(startRing, (quarter * 2 + 1) % 8))) {

                    positionList.add(new Position(startRing, (quarter * 2 + 1) % 8));
                    System.out.println("Blaue offene Mühle erkannt");
                }}

            //Powerpoint gelb
            for (int startRing = 1; startRing < 3; startRing++) {
                if (board.isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 7)% 8 ), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 1)  % 8), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 2) % 8), ownPlayerIndex)
                        && board.isFieldFree(new Position(startRing, (quarter * 2) % 8))) {

                    positionList.add(new Position(startRing, (quarter * 2) % 8));
                    System.out.println("Gelbe offene Mühle erkannt");
                }}

            //Powerpoint pink
            for (int startRing = 1; startRing < 3; startRing++) {
                if (board.isThisMyEnemysStone(new Position(startRing, (quarter * 2)% 8 ), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 1)  % 8), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 3) % 8), ownPlayerIndex)
                        && board.isFieldFree(new Position(startRing, (quarter * 2 + 2) % 8))) {

                    positionList.add(new Position(startRing, (quarter * 2 + 2) % 8));
                    System.out.println("Pinke offene Mühle erkannt");
                }}

            //Powerpoint grün
            for (int startRing = 0; startRing < 3; startRing++) {
                if ((board.isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 2) % 8), ownPlayerIndex)
                        || board.isThisMyEnemysStone(new Position(startRing, (quarter * 2) % 8), ownPlayerIndex))
                        && board.isThisMyEnemysStone(new Position((startRing + 1) % 3, quarter * 2+1), ownPlayerIndex)
                        && board.isThisMyEnemysStone(new Position((startRing + 2) % 3 , quarter * 2+1), ownPlayerIndex)
                        && board.isFieldFree(new Position(startRing, quarter * 2 + 1))) {

                    positionList.add(new Position(startRing, quarter * 2 + 1));
                    System.out.println("Grüne offene Mühle erkannt");
                }}
        }

        for (Position position:positionList) {
            System.out.println("Offene Mühle mit Lücke in Ring " + position.getRing() +" auf Feld " + position.getField());

        }

        return positionList;
    }

}
