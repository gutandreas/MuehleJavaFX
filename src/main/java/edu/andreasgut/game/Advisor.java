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

    static public LinkedList<Position> getAllFreeFields(Board board){
        LinkedList<Position> freeFields = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 8; field++) {

                if (board.isFieldFree(new Position(ring, field))){
                    freeFields.add(new Position(ring, field));
                }
            }
        }
        return freeFields;}


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

    static public boolean isThisStonePartOfMyEnemysOpenMorris(Board board, Position position, int ownPlayerIndex){
        int enemysIndex = 1-ownPlayerIndex;
        return isThisStonePartOfMyOpenMorris(board, position, enemysIndex);
    }

    static public boolean isThisPositionTheGapOfMyOpenMorris(Board board, Position position, int playerIndex){

        LinkedList<OpenMorris> openMorrisLinkedList = getMyOpenMorrisList(board, playerIndex);

        for (OpenMorris openMorris : openMorrisLinkedList){
            if (openMorris.getGapPosition().equals(position)){
                return true;
        }}

        return false;
    }

    static public boolean isThisStonePartOfMyOpenMorris(Board board, Position position, int playerIndex){

        LinkedList<OpenMorris> openMorrisLinkedList = getMyOpenMorrisList(board, playerIndex);

        for (OpenMorris openMorris : openMorrisLinkedList){
            if (openMorris.getFirstPosition().equals(position)
                || openMorris.getSecondPosition().equals(position)
                || openMorris.getThirdPosition().equals(position)){
                return true;
            }
        }

        return false;
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
            //System.out.println(morrisColor + "e offene Mühle erkannt");
        }
    }

    static public boolean positionBuildsTwoStonesTogetherWithFreeFieldBeside(Board board, Position position, int playerIndex){
        if (position.getField()%2==1){
            if (board.isThisMyStone(new Position((position.getRing()+1)%3, position.getField()), playerIndex)
                && board.isFieldFree(new Position((position.getRing()+2)%3, position.getField()))){
                return true;
            }

            if (board.isThisMyStone(new Position((position.getRing()+2)%3, position.getField()), playerIndex)
                    && board.isFieldFree(new Position((position.getRing()+1)%3, position.getField()))){
                return true;
            }
        }

        if (board.isThisMyStone(new Position(position.getRing(), (position.getField()+1)%8), playerIndex)
                && board.isFieldFree(new Position(position.getRing(), (position.getField()+2)%8))){
            return true;
        }
        if (board.isThisMyStone(new Position(position.getRing(), (position.getField()+7)%8), playerIndex)
                && board.isFieldFree(new Position(position.getRing(), (position.getField()+6)%8))){
            return true;
        }

        return false;
    }

    static public LinkedList<Position> getAllPossibleKills(Board board, int onwPlayerIndex){
        int enemysIndex = 1-onwPlayerIndex;
        LinkedList<Position> killList = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 8; field++) {

                Position position = new Position(ring, field);
                if (board.checkKill(position, enemysIndex)){
                    killList.add(position);
                }

            }}
        return killList;

    }



    static public LinkedList<Move> getAllPossibleMoves(Board board, int playerIndex) {

        LinkedList<Move> moveList = new LinkedList<>();

        //Jump Moves
        if (Advisor.countMyStones(board, playerIndex) == 3) {
            for (int row1 = 0; row1 < 3; row1++) {
                for (int field1 = 0; field1 < 8; field1++) {
                    Position from = new Position(row1, field1);

                    if (board.isThisMyStone(from, playerIndex)) {
                        for (int row2 = 0; row2 < 3; row2++) {
                            for (int field2 = 0; field2 < 8; field2++) {
                                Position to = new Position(row2, field2);
                                if (board.isFieldFree(to)){
                                    moveList.add(new Move(from, to));
                                }

                            }
                        }
                    }
                }
            }
        }

        //Regular Moves
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

    static public int getKillScore(Board board, Position kill, ScorePoints killScorePoints, int playerIndex, boolean printScore){

        boolean myNewOpenMorris = isThisPositionTheGapOfMyOpenMorris(board, kill, playerIndex);

        int score = 0;
        int myNewOpenMorrisTotal = 0;

        if (myNewOpenMorris){
            myNewOpenMorrisTotal = killScorePoints.getOwnNewOpenMorrisPoints();
            score += myNewOpenMorrisTotal;
        }



        if (printScore) {
            System.out.println("Neue offene eigene Mühle: " + myNewOpenMorris + " (" + myNewOpenMorrisTotal + ")");
            System.out.println("Score: " + score);
        }

        return 5;
    }
    static public int getMoveScore(Board board, Move move, ScorePoints scorePoints, int playerIndex, boolean printScore){

        int myOpenMorrises = getMyOpenMorrisList(board, playerIndex).size();
        int myClosedMorrises = getMyClosedMorrisList(board, playerIndex).size();

        boolean myNewClosedMorris = board.checkMorris(move.getTo());
        boolean myNewOpenMorris = Advisor.isThisStonePartOfMyOpenMorris(board, move.getTo(), playerIndex);


        int myPossibleMoves = getAllPossibleMoves(board, playerIndex).size();

        int myEnemysOpenMorrises = getMyEnemysOpenMorrisList(board, playerIndex).size();
        int myEnemysClosedMorrises = getMyEnemysClosedMorrisList(board, playerIndex).size();


        int myOpenMorrisesTotal = myOpenMorrises * scorePoints.getOwnOpenMorrisPoints();
        int myClosedMorrisesTotal = myClosedMorrises * scorePoints.getOwnClosedMorrisPoints();
        int myEnemysOpenMorrisesTotal = myEnemysOpenMorrises * scorePoints.getEnemyOpenMorrisPoints();
        int myEnemysClosedMorrisesTotal = myEnemysClosedMorrises * scorePoints.getEnemyClosedMorrisPoints();
        int myPossibleMovesTotal = myPossibleMoves * scorePoints.getOwnPossibleMovesPoints();
        int myNewClosedMorrisTotal = 0;
        int myNewOpenMorrisTotal = 0;

        int score = myOpenMorrisesTotal
                + myClosedMorrisesTotal
                + myEnemysOpenMorrisesTotal
                + myEnemysClosedMorrisesTotal
                + myPossibleMovesTotal;

        if (myNewClosedMorris){
            myNewClosedMorrisTotal = scorePoints.getOwnNewClosedMorrisPoints();
            score += myNewClosedMorrisTotal;
        }

        if (myNewOpenMorris){
            myNewOpenMorrisTotal = scorePoints.getOwnNewOpenMorrisPoints();
            score += myNewOpenMorrisTotal;
        }

        if (printScore) {
            System.out.println("Eigene offene Mühlen: " + myOpenMorrises + " (" + myOpenMorrisesTotal + ")");
            System.out.println("Eigene geschlossene Mühlen: " + myClosedMorrises + " (" + myClosedMorrisesTotal + ")");
            System.out.println("Neue geschlossene eigene Mühle: " + myNewClosedMorris + " (" + myNewClosedMorrisTotal + ")");
            System.out.println("Neue offene eigene Mühle: " + myNewOpenMorris + " (" + myNewOpenMorrisTotal + ")");
            System.out.println("Gegnerische offene Mühlen: " + myEnemysOpenMorrises + " (" + myEnemysOpenMorrisesTotal + ")");
            System.out.println("Gegnerische geschlossene Mühlen: " + myEnemysClosedMorrises + " (" + myEnemysClosedMorrisesTotal + ")");
            System.out.println("Eigene Zugmöglichkeiten: " + myPossibleMoves + " (" + myPossibleMovesTotal + ")");
            System.out.println("Score: " + score);
        }

        return score;

    }

    static public int getPutScore(Board board, Position put, ScorePoints scorePoints, int playerIndex, boolean printScore){

        int myOpenMorrises = getMyOpenMorrisList(board, playerIndex).size();
        int myClosedMorrises = getMyClosedMorrisList(board, playerIndex).size();

        boolean myNewClosedMorris = board.checkMorris(put);
        boolean myNewOpenMorris = Advisor.isThisStonePartOfMyOpenMorris(board, put, playerIndex);
        boolean myNewTwoStonesTogether = Advisor.positionBuildsTwoStonesTogetherWithFreeFieldBeside(board, put, playerIndex);

        int myPossibleMoves = getAllPossibleMoves(board, playerIndex).size();

        int myOpenMorrisesTotal = myOpenMorrises * scorePoints.getOwnOpenMorrisPoints();
        int myClosedMorrisesTotal = myClosedMorrises * scorePoints.getOwnClosedMorrisPoints();
        int myPossibleMovesTotal = myPossibleMoves * scorePoints.getOwnPossibleMovesPoints();
        int myNewClosedMorrisTotal = 0;
        int myNewOpenMorrisTotal = 0;
        int myNewTwoStonesTogetherTotal = 0;

        int score = myOpenMorrisesTotal + myClosedMorrisesTotal + myPossibleMovesTotal;

        if (myNewClosedMorris){
            myNewClosedMorrisTotal = scorePoints.getOwnNewClosedMorrisPoints();
            score += myNewClosedMorrisTotal;
        }

        if (myNewOpenMorris){
            myNewOpenMorrisTotal = scorePoints.getOwnNewOpenMorrisPoints();
            score += myNewOpenMorrisTotal;
        }

        if (myNewTwoStonesTogether){
            myNewTwoStonesTogetherTotal = scorePoints.getOwnTwoStonesTogetherPoints();
            score += myNewTwoStonesTogetherTotal;
        }

        if (printScore) {
            System.out.println("Eigene offene Mühlen: " + myOpenMorrises + " (" + myOpenMorrisesTotal + ")");
            System.out.println("Eigene geschlossene Mühlen: " + myClosedMorrises + " (" + myClosedMorrisesTotal + ")");
            System.out.println("Neue geschlossene eigene Mühle: " + myNewClosedMorris + " (" + myNewClosedMorrisTotal + ")");
            System.out.println("Neue offene eigene Mühle: " + myNewOpenMorris + " (" + myNewOpenMorrisTotal + ")");
            System.out.println("Neue zwei eigene Steine nebeneinander mit freiem Feld daneben: " + myNewTwoStonesTogether + " (" + myNewTwoStonesTogetherTotal + " )");
            System.out.println("Eigene Zugmöglichkeiten: " + myPossibleMoves + " (" + myPossibleMovesTotal + ")");
            System.out.println("Score: " + score);
        }

        return score;

    }




}
