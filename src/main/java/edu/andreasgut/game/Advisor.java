package edu.andreasgut.game;

import java.util.*;

public class Advisor {

    static boolean checkIfMyEnemyHasOpenMorris(Board board, int ownPlayerIndex) {
        return !getMyEnemysOpenMorrisList(board, ownPlayerIndex).isEmpty();
    }

    static boolean checkIfIHaveOpenMorris(Board board, int ownPlayerIndex) {
        return !getMyOpenMorrisList(board, ownPlayerIndex).isEmpty();
    }

    static public int countMyStones(Board board, int playerIndex){
        return board.countPlayersStones(playerIndex);
    }

    static public int countMyEnemysStones(Board board, int ownPlayerIndex){
        int enemysIndex = 1 - ownPlayerIndex;
        return board.countPlayersStones(enemysIndex);
    }

    static public LinkedList<Line> getLinesWithoutEnemysStones(Board board, int ownPlayerIndex){
        LinkedList<Line> lines = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 6; field+=2) {

                Position position1 = new Position(ring, field);
                Position position2 = new Position(ring, field+1);
                Position position3 = new Position(ring, field+2);

                if (!board.isThisMyEnemysStone(position1, ownPlayerIndex)
                        && !board.isThisMyEnemysStone(position2, ownPlayerIndex)
                        && !board.isThisMyEnemysStone(position3, ownPlayerIndex)){
                    lines.add(new Line(position1, position2, position3));
                }}}

        for (int field = 1; field < 6; field+=2) {

            Position position1 = new Position(0, field);
            Position position2 = new Position(1, field);
            Position position3 = new Position(2, field);

            if (!board.isThisMyEnemysStone(position1, ownPlayerIndex)
                    && !board.isThisMyEnemysStone(position2, ownPlayerIndex)
                    && !board.isThisMyEnemysStone(position3, ownPlayerIndex)){
                lines.add(new Line(position1, position2, position3));
            }}

        return lines;
    }


    static public LinkedList<Line> getFreeLines(Board board){
        LinkedList<Line> lines = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 6; field+=2) {

                Position position1 = new Position(ring, field);
                Position position2 = new Position(ring, field+1);
                Position position3 = new Position(ring, field+2);

                if (board.isFieldFree(position1)
                        && board.isFieldFree(position2)
                        && board.isFieldFree(position3)){
                    lines.add(new Line(position1, position2, position3));
                }}}

        for (int field = 1; field < 6; field+=2) {

            Position position1 = new Position(0, field);
            Position position2 = new Position(1, field);
            Position position3 = new Position(2, field);

            if (board.isFieldFree(position1)
                    && board.isFieldFree(position2)
                    && board.isFieldFree(position3)){
                lines.add(new Line(position1, position2, position3));
            }}

        return lines;
    }

    static public LinkedList<ClosedMorris> getMyEnemysClosedMorrisList(Board board, int ownPlayerIndex) {
        int enemysIndex = 1-ownPlayerIndex;
        return getMyClosedMorrisList(board, enemysIndex);
    }

    static public LinkedList<ClosedMorris> getMyClosedMorrisList(Board board, int ownPlayerIndex) {

        LinkedList<ClosedMorris> closedMorrisLinkedList = new LinkedList<>();

        for (int quarter = 0; quarter < 4; quarter++) {


            for (int startRing = 0; startRing < 2; startRing++) {
                Position position1 = new Position(startRing, quarter * 2);
                Position position2 = new Position(startRing, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);

                checkClosedMorris(board, ownPlayerIndex, closedMorrisLinkedList, position1, position2, position3);
            }

            Position position1 = new Position(0, quarter * 2 + 1);
            Position position2 = new Position(1, quarter * 2 + 1);
            Position position3 = new Position(2, quarter * 2 + 1);

            checkClosedMorris(board,ownPlayerIndex, closedMorrisLinkedList, position1, position2, position3);
        }

        return closedMorrisLinkedList;
    }

    static private void checkClosedMorris(Board board, int playerIndex, LinkedList<ClosedMorris> closedMorrisLinkedList,
                                          Position position1, Position position2, Position position3){

        if (board.isThisMyStone(position1, playerIndex)
                && board.isThisMyStone(position2, playerIndex)
                && board.isThisMyStone(position3, playerIndex)){

            ArrayList<Position> positions = new ArrayList<>();
            positions.add(position1);
            positions.add(position2);
            positions.add(position3);

            Collections.sort(positions);

            closedMorrisLinkedList.add(new ClosedMorris(board, positions.get(0), positions.get(1), positions.get(2)));
        }
    }


    static public LinkedList<OpenMorris> getMyEnemysOpenMorrisList(Board board, int ownPlayerIndex) {
        int enemysIndex = 1-ownPlayerIndex;
        return getMyOpenMorrisList(board, enemysIndex);
    }

    static public LinkedList<OpenMorris> getMyOpenMorrisList(Board board, int ownPlayerIndex) {

        LinkedList<OpenMorris> openMorrisLinkedList = new LinkedList<>();

        for (int quarter = 0; quarter < 4; quarter++) {

            //Powerpoint rot
            for (int startRing = 0; startRing < 2; startRing++) {

                Position position1 = new Position(startRing, quarter * 2);
                Position position2 = new Position(startRing + 1, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 1) % 8);

                checkOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Rot");
            }

            //Powerpoint blau
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, quarter * 2);
                Position position2 = new Position(startRing - 1, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 1) % 8);

                checkOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Blau");
            }

            //Powerpoint gelb
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2 + 7) % 8);
                Position position2 = new Position(startRing, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2) % 8);

                checkOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Gelb");
            }

            //Powerpoint pink
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2) % 8);
                Position position2 = new Position(startRing, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 3) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 2) % 8);

                checkOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Pink");
            }

            //Powerpoint grün
            for (int startRing = 0; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position position2 = new Position((startRing + 1) % 3, quarter * 2 + 1);
                Position position3 = new Position((startRing + 2) % 3, quarter * 2 + 1);
                Position gapPosition = new Position(startRing , quarter * 2 + 1);

                checkOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Grün");
            }

            //Powerpoint orange
            for (int startRing = 0; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2) % 8);
                Position position2 = new Position((startRing + 1) % 3, quarter * 2 + 1);
                Position position3 = new Position((startRing + 2) % 3, quarter * 2 + 1);
                Position gapPosition = new Position(startRing, quarter * 2 + 1);

                checkOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Orang");

            }
        }

        return openMorrisLinkedList;
    }

    static private void checkOpenMorris(Board board, int playerIndex, LinkedList<OpenMorris> openMorrisLinkedList,
                                        Position position1, Position position2, Position position3,
                                        Position gapPosition, String morrisColor){

        if (board.isThisMyStone(position1, playerIndex)
                && board.isThisMyStone(position2, playerIndex)
                && board.isThisMyStone(position3, playerIndex)
                && board.isFieldFree(gapPosition)) {

            ArrayList<Position> positions = new ArrayList<>();
            positions.add(position1);
            positions.add(position2);
            positions.add(position3);
            Collections.sort(positions);

            openMorrisLinkedList.add(new OpenMorris(positions.get(0), positions.get(1), positions.get(2), gapPosition));
            System.out.println(morrisColor + "e offene Mühle erkannt");
        }
    }



    static public LinkedList<Move> getAllPossibleMoves(Board board, int playerIndex) {


        LinkedList<Move> moveList = new LinkedList<>();

        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {

                Position from = new Position(row, field);
                if (board.isThisMyStone(from, playerIndex)) {
                    if (board.isFieldFree(new Position(row, (field + 1) % 8))) {
                        Move move = new Move();
                        move.setFrom(from);
                        move.setTo(new Position(row, (field + 1) % 8));
                        moveList.add(move);
                    }

                    if (board.isFieldFree(new Position(row, (field + 7) % 8))) {
                        Move move = new Move();
                        move.setFrom(from);
                        move.setTo(new Position(row, (field + 7) % 8));
                        moveList.add(move);
                    }

                    if (field % 2 == 1 && (row == 0 || row == 1) && board.isFieldFree(new Position(row + 1, field))) {
                        Move move = new Move();
                        move.setFrom(from);
                        move.setTo(new Position(row + 1, field));
                        moveList.add(move);
                    }

                    if (field % 2 == 1 && (row == 1 || row == 2) && board.isFieldFree(new Position(row - 1, field))) {
                        Move move = new Move();
                        move.setFrom(from);
                        move.setTo(new Position(row - 1, field));
                        moveList.add(move);
                    }

                }
            }
        }

        return moveList;
    }

    static public int getScore(Board board, Move move, ScorePoints scorePoints, int playerIndex, boolean printScore){

        int myOpenMorrises = getMyOpenMorrisList(board, playerIndex).size();
        int myClosedMorrises = getMyClosedMorrisList(board, playerIndex).size();
        boolean myNewClosedMorris = board.checkMorris(move.getTo());

        int myEnemysOpenMorrises = getMyEnemysOpenMorrisList(board, playerIndex).size();
        int myEnemysClosedMorrises = getMyEnemysClosedMorrisList(board, playerIndex).size();
        int myPossibleMoves = getAllPossibleMoves(board, playerIndex).size();


        int score = myOpenMorrises * scorePoints.getOwnOpenMorrisPoints()
                    + myClosedMorrises * scorePoints.getOwnClosedMorrisPoints()
                    + myEnemysOpenMorrises * scorePoints.getEnemyOpenMorrisPoints()
                    + myEnemysClosedMorrises * scorePoints.getEnemyClosedMorrisPoints()
                    + myPossibleMoves * scorePoints.getOwnPossibleMovesPoints();

        if (myNewClosedMorris){
            score += scorePoints.getOwnNewClosedMorrisPoints();
        }

        if (printScore) {
            System.out.println("Eigene offene Mühlen: " + myOpenMorrises);
            System.out.println("Eigene geschlossene Mühlen: " + myClosedMorrises);
            System.out.println("Neue geschlossene eigene Mühle: " + myNewClosedMorris);
            System.out.println("Gegnerische offene Mühlen: " + myEnemysOpenMorrises);
            System.out.println("Gegnerische geschlossene Mühlen: " + myEnemysClosedMorrises);
            System.out.println("Eigene Zugmöglichkeiten: " + myPossibleMoves);
            System.out.println("Score: " + score);
        }

        return score;

    }


}
