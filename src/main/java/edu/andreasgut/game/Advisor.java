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

    static public LinkedList<Line> getFreeLines(Board board){
        LinkedList<Line> lines = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 6; field++) {

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

    static public LinkedList<OpenMorris> getMyOpenMorrisList(Board board, int ownPlayerIndex) {
        int enemysIndex = 1-ownPlayerIndex;
        return getMyEnemysOpenMorrisList(board, enemysIndex);
    }

    static public LinkedList<OpenMorris> getMyEnemysOpenMorrisList(Board board, int ownPlayerIndex) {

        LinkedList<OpenMorris> openMorrisLinkedList = new LinkedList<>();

        for (int quarter = 0; quarter < 4; quarter++) {

            //Powerpoint rot
            for (int startRing = 0; startRing < 2; startRing++) {

                Position position1 = new Position(startRing, quarter * 2);
                Position position2 = new Position(startRing + 1, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 1) % 8);

                if (board.isThisMyEnemysStone(position1, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position2, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position3, ownPlayerIndex)
                        && board.isFieldFree(gapPosition)) {


                    ArrayList<Position> positions = new ArrayList<>();
                    positions.add(position1);
                    positions.add(position2);
                    positions.add(position3);
                    Collections.sort(positions);

                    openMorrisLinkedList.add(new OpenMorris(positions.get(0), positions.get(1), positions.get(2), gapPosition));
                    System.out.println("Rote offene Mühle erkannt");
                }
            }

            //Powerpoint blau
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, quarter * 2);
                Position position2 = new Position(startRing - 1, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 1) % 8);

                if (board.isThisMyEnemysStone(position1, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position2, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position3, ownPlayerIndex)
                        && board.isFieldFree(gapPosition)) {

                    ArrayList<Position> positions = new ArrayList<>();
                    positions.add(position1);
                    positions.add(position2);
                    positions.add(position3);
                    Collections.sort(positions);

                    openMorrisLinkedList.add(new OpenMorris(positions.get(0), positions.get(1), positions.get(2), gapPosition));
                    System.out.println("Blaue offene Mühle erkannt");
                }
            }

            //Powerpoint gelb
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2 + 7) % 8);
                Position position2 = new Position(startRing, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2) % 8);

                if (board.isThisMyEnemysStone(position1, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position2, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position3, ownPlayerIndex)
                        && board.isFieldFree(gapPosition)) {

                    ArrayList<Position> positions = new ArrayList<>();
                    positions.add(position1);
                    positions.add(position2);
                    positions.add(position3);
                    Collections.sort(positions);

                    openMorrisLinkedList.add(new OpenMorris(positions.get(0), positions.get(1), positions.get(2), gapPosition));
                    System.out.println("Gelbe offene Mühle erkannt");
                }
            }

            //Powerpoint pink
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2) % 8);
                Position position2 = new Position(startRing, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 3) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 2) % 8);

                if (board.isThisMyEnemysStone(position1, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position2, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position3, ownPlayerIndex)
                        && board.isFieldFree(gapPosition)) {

                    ArrayList<Position> positions = new ArrayList<>();
                    positions.add(position1);
                    positions.add(position2);
                    positions.add(position3);
                    Collections.sort(positions);

                    openMorrisLinkedList.add(new OpenMorris(positions.get(0), positions.get(1), positions.get(2), gapPosition));
                    System.out.println("Pinke offene Mühle erkannt");
                }
            }

            //Powerpoint grün
            for (int startRing = 0; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position position2 = new Position((startRing + 1) % 3, quarter * 2 + 1);
                Position position3 = new Position((startRing + 2) % 3, quarter * 2 + 1);
                Position gapPosition = new Position(startRing , quarter * 2 + 1);

                if (board.isThisMyEnemysStone(position1, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position2, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position3, ownPlayerIndex)
                        && board.isFieldFree(gapPosition)) {

                    ArrayList<Position> positions = new ArrayList<>();
                    positions.add(position1);
                    positions.add(position2);
                    positions.add(position3);
                    Collections.sort(positions);

                    openMorrisLinkedList.add(new OpenMorris(positions.get(0), positions.get(1), positions.get(2), gapPosition));
                    System.out.println("Grüne offene Mühle erkannt");
                }
            }

            //Powerpoint orange
            for (int startRing = 0; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2) % 8);
                Position position2 = new Position((startRing + 1) % 3, quarter * 2 + 1);
                Position position3 = new Position((startRing + 2) % 3, quarter * 2 + 1);
                Position gapPosition = new Position(startRing, quarter * 2 + 1);

                if (board.isThisMyEnemysStone(position1, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position2, ownPlayerIndex)
                        && board.isThisMyEnemysStone(position3, ownPlayerIndex)
                        && board.isFieldFree(gapPosition)) {

                    ArrayList<Position> positions = new ArrayList<>();
                    positions.add(position1);
                    positions.add(position2);
                    positions.add(position3);
                    Collections.sort(positions);

                    openMorrisLinkedList.add(new OpenMorris(positions.get(0), positions.get(1), positions.get(2), gapPosition));
                    System.out.println("Orange offene Mühle erkannt");
                }

            }
        }

        for (OpenMorris openMorris : openMorrisLinkedList) {
            System.out.println("Offene Mühle mit Pos 1 " + openMorris.getFirstPosition().getRing() + "/" + openMorris.getFirstPosition().getField());
            System.out.println("Offene Mühle mit Pos 2 " + openMorris.getSecondPosition().getRing() + "/" + openMorris.getSecondPosition().getField());
            System.out.println("Offene Mühle mit Pos 3 " + openMorris.getThirdPosition().getRing() + "/" + openMorris.getThirdPosition().getField());
            System.out.println("Offene Mühle mit Lücke " + openMorris.getGapPosition().getRing() + "/" + openMorris.getGapPosition().getField());
            System.out.println();
        }

        return openMorrisLinkedList;
    }



    static public LinkedList<Position[]> getAllPossibleMoves(Board board, int playerIndex) {

        Position from;
        Position to;
        LinkedList<Position[]> positionList = new LinkedList<>();

        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {


                from = new Position(row, field);
                if (board.isThisMyStone(from, playerIndex)) {
                    if (board.isFieldFree(new Position(row, (field + 1) % 8))) {
                        to = new Position(row, (field + 1) % 8);
                        Position[] positions = new Position[2];
                        positions[0] = from;
                        positions[1] = to;
                        positionList.add(positions);
                    }

                    if (board.isFieldFree(new Position(row, (field + 7) % 8))) {
                        to = new Position(row, (field + 7) % 8);
                        Position[] positions = new Position[2];
                        positions[0] = from;
                        positions[1] = to;
                        positionList.add(positions);
                    }

                    if (field % 2 == 1 && (row == 0 || row == 1) && board.isFieldFree(new Position(row + 1, field))) {
                        to = new Position(row + 1, field);
                        Position[] positions = new Position[2];
                        positions[0] = from;
                        positions[1] = to;
                        positionList.add(positions);
                    }

                    if (field % 2 == 1 && (row == 1 || row == 2) && board.isFieldFree(new Position(row - 1, field))) {
                        to = new Position(row - 1, field);
                        Position[] positions = new Position[2];
                        positions[0] = from;
                        positions[1] = to;
                        positionList.add(positions);
                    }

                }
            }
        }

        return positionList;

    }

}
