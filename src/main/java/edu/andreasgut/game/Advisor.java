package edu.andreasgut.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Der Advisor hilft dabei, Spielsituationen zu analysieren. Er bietet eine Reihe von Methoden, mit denen ein Board
 * auf die gewünschten Kriterien untersucht werden kann.
 */
public class Advisor {

    /**
     * Gibt die Anzahl Steine eines Spielers zurück.
     *
     * @param board       Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param playerIndex Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @return liefert die Anzahl als Integer
     */
    static public int numberOfOwnStones(Board board, int playerIndex) {
        return board.numberOfStonesOf(playerIndex);
    }


    /**
     * Gibt die Anzahl Steine eines Gegenspielers zurück.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return liefert die Anzahl als Integer
     */
    static public int numberOfEnemysStones(Board board, int ownPlayerIndex) {
        int enemysIndex = 1 - ownPlayerIndex;
        return board.numberOfStonesOf(enemysIndex);
    }


    /**
     * Gibt eine Liste aller geschlossenen Mühlen des eigenen Spielers zurück.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return Liefert die Resultate als LinkedList
     */
    static public LinkedList<ClosedMorris> getMyClosedMorrises(Board board, int ownPlayerIndex) {

        LinkedList<ClosedMorris> closedMorrisLinkedList = new LinkedList<>();

        for (int quarter = 0; quarter < 4; quarter++) {

            for (int startRing = 0; startRing < 2; startRing++) {
                Position position1 = new Position(startRing, quarter * 2);
                Position position2 = new Position(startRing, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);

                addConstellationToListIfClosedMorris(board, ownPlayerIndex, closedMorrisLinkedList, position1, position2, position3);
            }

            Position position1 = new Position(0, quarter * 2 + 1);
            Position position2 = new Position(1, quarter * 2 + 1);
            Position position3 = new Position(2, quarter * 2 + 1);

            addConstellationToListIfClosedMorris(board, ownPlayerIndex, closedMorrisLinkedList, position1, position2, position3);
        }

        return closedMorrisLinkedList;
    }


    /**
     * Gibt eine Liste aller geschlossenen Mühlen des Gegenspielers zurück.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return Liefert die Resultate als LinkedList
     */
    static public LinkedList<ClosedMorris> getMyEnemysClosedMorrises(Board board, int ownPlayerIndex) {
        int enemysIndex = 1 - ownPlayerIndex;
        return getMyClosedMorrises(board, enemysIndex);
    }


    /**
     * Fügt eine Konstellation zur übergebenen Liste hinzu, falls es sich dabei um eine geschlossene Mühle handelt
     *
     * @param board                  Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param playerIndex            Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @param closedMorrisLinkedList Liste, welche die Mühlen enthält
     * @param position1              Teil der zu prüfenden Konstellation
     * @param position2              Teil der zu prüfenden Konstellation
     * @param position3              Teil der zu prüfenden Konstellation
     */
    static private void addConstellationToListIfClosedMorris(Board board, int playerIndex, LinkedList<ClosedMorris> closedMorrisLinkedList,
                                                             Position position1, Position position2, Position position3) {

        if (board.isThisMyStone(position1, playerIndex)
                && board.isThisMyStone(position2, playerIndex)
                && board.isThisMyStone(position3, playerIndex)) {

            ArrayList<Position> positions = new ArrayList<>();
            positions.add(position1);
            positions.add(position2);
            positions.add(position3);

            Collections.sort(positions);

            closedMorrisLinkedList.add(new ClosedMorris(board, positions.get(0), positions.get(1), positions.get(2)));
        }
    }


    /**
     * Gibt eine Liste aller offenen Mühlen des eigenen Spielers zurück.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return Liefert die Resultate als LinkedList
     */
    static public LinkedList<OpenMorris> getMyOpenMorrises(Board board, int ownPlayerIndex) {

        LinkedList<OpenMorris> openMorrisLinkedList = new LinkedList<>();

        for (int quarter = 0; quarter < 4; quarter++) {

            //Schriftliche Arbeit rot
            for (int startRing = 0; startRing < 2; startRing++) {

                Position position1 = new Position(startRing, quarter * 2);
                Position position2 = new Position(startRing + 1, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 1) % 8);

                addConstellationToListIfOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Rot");
            }

            //Schriftliche Arbeit blau
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, quarter * 2);
                Position position2 = new Position(startRing - 1, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 1) % 8);

                addConstellationToListIfOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Blau");
            }

            //Schriftliche Arbeit gelb
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2 + 7) % 8);
                Position position2 = new Position(startRing, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2) % 8);

                addConstellationToListIfOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Gelb");
            }

            //Schriftliche Arbeit pink
            for (int startRing = 1; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2) % 8);
                Position position2 = new Position(startRing, (quarter * 2 + 1) % 8);
                Position position3 = new Position(startRing, (quarter * 2 + 3) % 8);
                Position gapPosition = new Position(startRing, (quarter * 2 + 2) % 8);

                addConstellationToListIfOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Pink");
            }

            //Schriftliche Arbeit grün
            for (int startRing = 0; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2 + 2) % 8);
                Position position2 = new Position((startRing + 1) % 3, quarter * 2 + 1);
                Position position3 = new Position((startRing + 2) % 3, quarter * 2 + 1);
                Position gapPosition = new Position(startRing, quarter * 2 + 1);

                addConstellationToListIfOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Grün");
            }

            //Schriftliche Arbeit orange
            for (int startRing = 0; startRing < 3; startRing++) {

                Position position1 = new Position(startRing, (quarter * 2) % 8);
                Position position2 = new Position((startRing + 1) % 3, quarter * 2 + 1);
                Position position3 = new Position((startRing + 2) % 3, quarter * 2 + 1);
                Position gapPosition = new Position(startRing, quarter * 2 + 1);

                addConstellationToListIfOpenMorris(board, ownPlayerIndex, openMorrisLinkedList, position1, position2, position3, gapPosition, "Orang");

            }
        }

        return openMorrisLinkedList;
    }


    /**
     * Gibt eine Liste aller offenen Mühlen des Gegenpielers zurück.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return Liefert die Resultate als LinkedList
     */
    static public LinkedList<OpenMorris> getMyEnemysOpenMorrises(Board board, int ownPlayerIndex) {
        int enemysIndex = 1 - ownPlayerIndex;
        return getMyOpenMorrises(board, enemysIndex);
    }


    /**
     * Fügt eine Konstellation zur übergebenen Liste hinzu, falls es sich dabei um eine offene Mühle handelt.
     *
     * @param board                Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param playerIndex          Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @param openMorrisLinkedList Liste, welche die offenen Mühlen enthält
     * @param position1            Teil der zu prüfenden Konstellation
     * @param position2            Teil der zu prüfenden Konstellation
     * @param position3            Teil der zu prüfenden Konstellation
     */
    static private void addConstellationToListIfOpenMorris(Board board, int playerIndex, LinkedList<OpenMorris> openMorrisLinkedList,
                                                           Position position1, Position position2, Position position3,
                                                           Position gapPosition, String morrisColor) {

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


    /**
     * Gibt die Anzahl von zwei eigenen, nebeneinanderliegenden Steinen zurück, die an eine freie Position grenzen.
     * Die nebeneinanderliegenden Steine können durch das Besetzen der freien Position zu einer Mühle ergänzt werden.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @return Gibt die Anzahl als Integer zurück.
     */
    static public int numberOfMyTwoStonesTogetherWithFreePositionBeside(Board board, int ownPlayerIndex) {
        int counter = 0;

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 8; field++) {
                if (board.isThisMyStone(new Position(ring, field), ownPlayerIndex)
                        && positionBuildsTwoStonesTogetherWithFreePositionBeside(board, new Position(ring, field), ownPlayerIndex)) {
                    counter++;
                }
            }
        }
        return counter / 2; // jedes 2er-Pack wird doppelt gezählt
    }


    /**
     * Gibt die Anzahl von zwei fremden, nebeneinanderliegenden Steinen zurück, die an eine freie Position grenzen.
     * Die nebeneinanderliegenden Steine können durch das Besetzen der freien Position zu einer Mühle ergänzt werden.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @return Gibt die Anzahl als Integer zurück.
     */
    static public int numberOfMyEnemysTwoStonesTogetherWithFreePositionBeside(Board board, int ownPlayerIndex) {
        int enemyIndex = 1 - ownPlayerIndex;
        int counter = 0;

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 8; field++) {
                if (board.isThisMyEnemysStone(new Position(ring, field), ownPlayerIndex)
                        && positionBuildsTwoStonesTogetherWithFreePositionBeside(board, new Position(ring, field), enemyIndex)) {
                    counter++;
                }
            }
        }
        return counter / 2; // jedes 2er-Pack wird doppelt gezählt
    }


    /**
     * Prüft, ob eine Position Teil von zwei zusammengehörenden, nebeneinaderliegenden Steinen ist, die durch das Besetzen
     * der angrenzenden, freien Position zu einer Müle ergänzt werden können.
     *
     * @param board    Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param position Zu prüfende Position
     * @return Gibt true zurück, wenn die Position Teil davon ist, sonst false
     */
    static public boolean positionBuildsTwoStonesTogetherWithFreePositionBeside(Board board, Position position, int playerIndex) {
        if (position.getField() % 2 == 1) { // Ungerade Felder

            // Über Ringe hinweg
            if (position.getRing() == 0) {
                if (board.isThisMyStone(new Position((position.getRing() + 1), position.getField()), playerIndex)
                        && board.isFieldFree(new Position((position.getRing() + 2), position.getField()))) {
                    return true;
                }
            }

            if (position.getRing() == 1) {
                if (board.isThisMyStone(new Position((position.getRing() - 1), position.getField()), playerIndex)
                        && board.isFieldFree(new Position((position.getRing() + 1), position.getField()))) {
                    return true;
                }

                if (board.isThisMyStone(new Position((position.getRing() + 1), position.getField()), playerIndex)
                        && board.isFieldFree(new Position((position.getRing() - 1), position.getField()))) {
                    return true;
                }
            }

            if (position.getRing() == 2) {
                if (board.isThisMyStone(new Position((position.getRing() - 1), position.getField()), playerIndex)
                        && board.isFieldFree(new Position((position.getRing() - 2), position.getField()))) {
                    return true;
                }
            }

            if (board.isThisMyStone(new Position((position.getRing() + 2) % 3, position.getField()), playerIndex)
                    && board.isFieldFree(new Position((position.getRing() + 1) % 3, position.getField()))) {
                return true;
            }

            // Innerhalb von Ring
            if (board.isThisMyStone(new Position(position.getRing(), (position.getField() + 7) % 8), playerIndex)
                    && board.isFieldFree(new Position(position.getRing(), (position.getField() + 1) % 8))) {
                return true;
            }
            return board.isThisMyStone(new Position(position.getRing(), (position.getField() + 1) % 8), playerIndex)
                    && board.isFieldFree(new Position(position.getRing(), (position.getField() + 7) % 8));
        } else { // Gerade Felder (innerhalb von Ring)

            if (board.isThisMyStone(new Position(position.getRing(), (position.getField() + 1) % 8), playerIndex)
                    && board.isFieldFree(new Position(position.getRing(), (position.getField() + 2) % 8))) {
                return true;
            }
            return board.isThisMyStone(new Position(position.getRing(), (position.getField() + 7) % 8), playerIndex)
                    && board.isFieldFree(new Position(position.getRing(), (position.getField() + 6) % 8));
        }
    }


    /**
     * Gibt die Anzahl von zwei eigenen Steinen zurück, die eine freie Position umschliessen. Wird die freie Position
     * mit einem dazugehörigen Stein ergänzt, bildet sich eine geschlossene Mühle.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @return Gibt die Anzahl als integer zurück
     */
    static public int numberOfMyTwoStonesWithGap(Board board, int ownPlayerIndex) {
        int counter = 0;

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 8; field++) {
                if (board.isThisMyStone(new Position(ring, field), ownPlayerIndex)
                        && positionBuildsTwoStonesWithGap(board, new Position(ring, field), ownPlayerIndex)) {
                    counter++;
                }
            }
        }
        return counter / 2; // jedes 2er-Pack wird doppelt gezählt
    }


    /**
     * Gibt die Anzahl von zwei fremden Steinen zurück, die eine freie Position umschliessen. Wird die freie Position
     * mit einem dazugehörigen Stein ergänzt, bildet sich eine geschlossene Mühle.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return Gibt die Anzahl als integer zurück
     */
    static public int numberOfMyEnemysTwoStonesWithGap(Board board, int ownPlayerIndex) {
        int enemyIndex = 1 - ownPlayerIndex;
        int counter = 0;

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 8; field++) {
                if (board.isThisMyEnemysStone(new Position(ring, field), ownPlayerIndex)
                        && positionBuildsTwoStonesWithGap(board, new Position(ring, field), enemyIndex)) {
                    counter++;
                }
            }
        }
        return counter / 2; // jedes 2er-Pack wird doppelt gezählt
    }


    /**
     * Prüft, ob eine Position Teil von zwei zusammengehörenden, eine freie Position umschliessenden Steinen ist,
     * die durch das Besetzen der umschlossenen, freien Position zu einer Müle ergänzt werden können.
     *
     * @param board    Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param position Zu prüfende Position
     * @return Gibt true zurück, wenn die Position Teil davon ist, sonst false
     */
    static private boolean positionBuildsTwoStonesWithGap(Board board, Position position, int playerIndex) {
        if (position.getField() % 2 == 1 && position.getRing() == 0) {
            if (board.isFieldFree(new Position(position.getRing() + 1, position.getField()))
                    && board.isThisMyStone(new Position(position.getRing() + 2, position.getField()), playerIndex)) {
                return true;
            }
        }

        if (position.getField() % 2 == 1 && position.getRing() == 2) {
            if (board.isFieldFree(new Position(position.getRing() - 1, position.getField()))
                    && board.isThisMyStone(new Position(position.getRing() - 2, position.getField()), playerIndex)) {
                return true;
            }
        }

        if (position.getField() % 2 == 0) {
            if (board.isThisMyStone(new Position(position.getRing(), (position.getField() + 2) % 8), playerIndex)
                    && board.isFieldFree(new Position(position.getRing(), (position.getField() + 1) % 8))) {
                return true;
            }

            return board.isThisMyStone(new Position(position.getRing(), (position.getField() + 6) % 8), playerIndex)
                    && board.isFieldFree(new Position(position.getRing(), (position.getField() + 7) % 8));
        }

        return false;
    }


    /**
     * Gibt eine Liste alle möglichen Moves zurück, die vom Spieler mit dem übergebenen Index ausgeführt werden können.
     * Die Methode berücksichtigt selbstständig, ob der Spieler in der Zug- oder Sprungphase ist.
     *
     * @param board       Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param playerIndex Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @return Gibt das Resultat als LinkedList zurück
     */
    static public LinkedList<Move> getAllPossibleMoves(Board board, int playerIndex) {

        LinkedList<Move> moveList = new LinkedList<>();

        //Jump Moves
        if (numberOfOwnStones(board, playerIndex) == 3) {
            for (int row1 = 0; row1 < 3; row1++) {
                for (int field1 = 0; field1 < 8; field1++) {
                    Position from = new Position(row1, field1);

                    if (board.isThisMyStone(from, playerIndex)) {
                        for (int row2 = 0; row2 < 3; row2++) {
                            for (int field2 = 0; field2 < 8; field2++) {
                                Position to = new Position(row2, field2);
                                if (board.isFieldFree(to)) {
                                    moveList.add(new Move(from, to));
                                }

                            }
                        }
                    }
                }
            }
        }

        //Regular Moves
        else {
            for (int row = 0; row < 3; row++) {
                for (int field = 0; field < 8; field++) {

                    Position from = new Position(row, field);
                    if (board.isThisMyStone(from, playerIndex)) {
                        if (board.isFieldFree(new Position(row, (field + 1) % 8))) {
                            Move move = new Move(from, new Position(row, (field + 1) % 8));
                            moveList.add(move);
                        }

                        if (board.isFieldFree(new Position(row, (field + 7) % 8))) {
                            Move move = new Move(from, new Position(row, (field + 7) % 8));
                            moveList.add(move);
                        }

                        if (field % 2 == 1 && (row == 0 || row == 1) && board.isFieldFree(new Position(row + 1, field))) {
                            Move move = new Move(from, new Position(row + 1, field));
                            moveList.add(move);
                        }

                        if (field % 2 == 1 && (row == 1 || row == 2) && board.isFieldFree(new Position(row - 1, field))) {
                            Move move = new Move(from, new Position(row - 1, field));
                            moveList.add(move);
                        }

                    }
                }
            }
        }

        return moveList;
    }


    /**
     * Gibt eine Liste mit allen Positionen zurück, auf denen ein gegnerischer Stein liegt, der entfernt werden darf.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param onwPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return Gibt die Resultate als LinkedList zurück
     */
    static public LinkedList<Position> getAllPossibleKills(Board board, int onwPlayerIndex) {
        int enemysIndex = 1 - onwPlayerIndex;
        LinkedList<Position> killList = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 8; field++) {
                Position position = new Position(ring, field);
                if (board.isKillPossibleAt(position, enemysIndex)) {
                    killList.add(position);
                }
            }
        }

        return killList;
    }


    /**
     * Gibt eine Liste mit allen Linien zurück, die weder von eigenen, noch von fremden Steinen besetzt sind. Als
     * Linien gelten solche Brettbereiche, die zu einer Mühle gemacht werden können.
     *
     * @param board Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @return Gibt die Resultate als LinkedList zurück
     */
    static public LinkedList<Line> getFreeLines(Board board) {
        LinkedList<Line> lines = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 6; field += 2) {

                Position position1 = new Position(ring, field);
                Position position2 = new Position(ring, field + 1);
                Position position3 = new Position(ring, field + 2);

                if (board.isFieldFree(position1)
                        && board.isFieldFree(position2)
                        && board.isFieldFree(position3)) {
                    lines.add(new Line(position1, position2, position3));
                }
            }
        }

        for (int field = 1; field < 6; field += 2) {

            Position position1 = new Position(0, field);
            Position position2 = new Position(1, field);
            Position position3 = new Position(2, field);

            if (board.isFieldFree(position1)
                    && board.isFieldFree(position2)
                    && board.isFieldFree(position3)) {
                lines.add(new Line(position1, position2, position3));
            }
        }

        return lines;
    }


    /**
     * Gibt eine Liste mit allen Linien zurück, die keine fremden Steine enthalten. Als Linien gelten solche
     * Brettbereiche, die zu einer Mühle gemacht werden können.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return Gibt die Resultate als LinkedList zurück
     */
    static public LinkedList<Line> getLinesWithoutEnemysStones(Board board, int ownPlayerIndex) {
        LinkedList<Line> lines = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 6; field += 2) {

                Position position1 = new Position(ring, field);
                Position position2 = new Position(ring, field + 1);
                Position position3 = new Position(ring, field + 2);

                if (!board.isThisMyEnemysStone(position1, ownPlayerIndex)
                        && !board.isThisMyEnemysStone(position2, ownPlayerIndex)
                        && !board.isThisMyEnemysStone(position3, ownPlayerIndex)) {
                    lines.add(new Line(position1, position2, position3));
                }
            }
        }

        for (int field = 1; field < 6; field += 2) {

            Position position1 = new Position(0, field);
            Position position2 = new Position(1, field);
            Position position3 = new Position(2, field);

            if (!board.isThisMyEnemysStone(position1, ownPlayerIndex)
                    && !board.isThisMyEnemysStone(position2, ownPlayerIndex)
                    && !board.isThisMyEnemysStone(position3, ownPlayerIndex)) {
                lines.add(new Line(position1, position2, position3));
            }
        }

        return lines;
    }


    /**
     * Gibt eine Liste mit allen freien Positionen des Spielbretts zurück.
     *
     * @param board Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @return Gibt die Resultate als LinkedList zurück
     */
    static public LinkedList<Position> getFreePositions(Board board) {
        LinkedList<Position> freePositions = new LinkedList<>();

        for (int ring = 0; ring < 3; ring++) {
            for (int field = 0; field < 8; field++) {

                if (board.isFieldFree(new Position(ring, field))) {
                    freePositions.add(new Position(ring, field));
                }
            }
        }
        return freePositions;
    }


    /**
     * Prüft, ob eine Position die Lücke einer eigenen, offenen Mühle ist.
     *
     * @param board       Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param position    Zu prüfende Position
     * @param playerIndex Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @return
     */
    static public boolean isPositionGapOfMyOpenMorris(Board board, Position position, int playerIndex) {

        LinkedList<OpenMorris> openMorrisLinkedList = getMyOpenMorrises(board, playerIndex);

        for (OpenMorris openMorris : openMorrisLinkedList) {
            if (openMorris.getGapPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Prüft, ob eine Position die Lücke einer fremden, offenen Mühle ist.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param position       Zu prüfende Position
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return
     */
    static public boolean isPositionGapOfMyEnemysOpenMorris(Board board, Position position, int ownPlayerIndex) {

        LinkedList<OpenMorris> openMorrisLinkedList = getMyEnemysOpenMorrises(board, ownPlayerIndex);

        for (OpenMorris openMorris : openMorrisLinkedList) {
            if (openMorris.getGapPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Prüft, ob die übergebene Position Teil einer eigenen offenen Mühle ist.
     *
     * @param board       Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param position    Zu prüfende Position
     * @param playerIndex Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @return
     */
    static public boolean isPositionPartOfMyOpenMorris(Board board, Position position, int playerIndex) {

        LinkedList<OpenMorris> openMorrisLinkedList = getMyOpenMorrises(board, playerIndex);

        for (OpenMorris openMorris : openMorrisLinkedList) {
            if (openMorris.getFirstPosition().equals(position)
                    || openMorris.getSecondPosition().equals(position)
                    || openMorris.getThirdPosition().equals(position)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Prüft, ob die übergebene Position Teil einer fremden offenen Mühle ist.
     *
     * @param board          Enthält Methoden für den Zugriff auf das Brett und die Datenstruktur, die das Brett repräsentiert
     * @param position       Zu prüfende Position
     * @param ownPlayerIndex Entspricht dem eigenen Index in der PlayerArrayList der Klasse Game
     * @return
     */
    static public boolean isPositionPartOfMyEnemysOpenMorris(Board board, Position position, int ownPlayerIndex) {
        int enemysIndex = 1 - ownPlayerIndex;
        return isPositionPartOfMyOpenMorris(board, position, enemysIndex);
    }


    /**
     * Berechnet die Punktzahl eines Knotens im Spielbaum auf der Grundlage der übergebenen Scorepoints
     *
     * @param node        Knoten des Spielbaums
     * @param scorePoints Gewichtung der Konstellationen, die gezählt werden sollen
     * @param playerIndex Entspricht dem Index in der PlayerArrayList der Klasse Game
     * @return Gibt das Resultat als Integer zurück, der sowohl positiv als auch negativ sein kann
     */
    static public int getScore(GameTreeNode node, ScorePoints scorePoints, int playerIndex) {


        Board board = node.getBoard();

        int myOpenMorrises = getMyOpenMorrises(board, playerIndex).size();
        int myClosedMorrises = getMyClosedMorrises(board, playerIndex).size();
        int myNumberOfStones = numberOfOwnStones(board, playerIndex);
        int myNumberOfTwoStonesTogether = numberOfMyTwoStonesTogetherWithFreePositionBeside(board, playerIndex);
        int myNumberOfTwoStonesWithGap = numberOfMyTwoStonesWithGap(board, playerIndex);
        int myPossibleMoves = getAllPossibleMoves(board, playerIndex).size();

        int myOpenMorrisesPoints = myOpenMorrises * scorePoints.getOwnOpenMorrisPoints();
        int myClosedMorrisesPoints = myClosedMorrises * scorePoints.getOwnClosedMorrisPoints();
        int myNumberOfStonesPoints = myNumberOfStones * scorePoints.getOwnNumberOfStonesPoints();
        int myNumberOfTwoStonesTogetherPoints = myNumberOfTwoStonesTogether * scorePoints.getOwnTwoStonesTogetherPoints();
        int myNumberOfTwoStonesWithGapPoints = myNumberOfTwoStonesWithGap * scorePoints.getOwnTwoStonesWithGapPoints();
        int myPossibleMovesPoints = myPossibleMoves * scorePoints.getOwnPossibleMovesPoints();

        int myEnemysOpenMorrises = getMyEnemysOpenMorrises(board, playerIndex).size();
        int myEnemysClosedMorrises = getMyEnemysClosedMorrises(board, playerIndex).size();
        int myEnemysNumberOfStones = numberOfEnemysStones(board, playerIndex);
        int myEnemysNumberOfTwoStonesTogether = numberOfMyEnemysTwoStonesTogetherWithFreePositionBeside(board, playerIndex);
        int myEnemysNumberOfTwoStonesWithGap = numberOfMyEnemysTwoStonesWithGap(board, playerIndex);
        int myEnemysPossiblesMoves = getAllPossibleMoves(board, playerIndex).size();

        int myEnemysOpenMorrisesPoints = myEnemysOpenMorrises * scorePoints.getEnemyOpenMorrisPoints();
        int myEnemysClosedMorrisesPoints = myEnemysClosedMorrises * scorePoints.getEnemyClosedMorrisPoints();
        int myEnemysNumberOfStonesPoints = myEnemysNumberOfStones * scorePoints.getEnemyNumberOfStonesPoints();
        int myEnemysNumberOfTwoStonesTogetherPoints = myEnemysNumberOfTwoStonesTogether * scorePoints.getEnemyTwoStonesTogetherPoints();
        int myEnemysNumberOfTwoStonesWithGapPoints = myEnemysNumberOfTwoStonesWithGap * scorePoints.getEnemyTwoStonesWithGapPoints();
        int myEnemysPossiblesMovesPoints = myEnemysPossiblesMoves * scorePoints.getEnemyPossibleMovesPoints();

        int score = myOpenMorrisesPoints
                + myClosedMorrisesPoints
                + myNumberOfStonesPoints
                + myNumberOfTwoStonesTogetherPoints
                + myNumberOfTwoStonesWithGapPoints
                + myPossibleMovesPoints

                + myEnemysOpenMorrisesPoints
                + myEnemysClosedMorrisesPoints
                + myEnemysNumberOfStonesPoints
                + myEnemysNumberOfTwoStonesTogetherPoints
                + myEnemysNumberOfTwoStonesWithGapPoints
                + myEnemysPossiblesMovesPoints;


        node.setScoreDetails(
                "Eigene offene Mühlen: " + myOpenMorrises + " (" + myOpenMorrisesPoints + ")" + " \n" +
                        "Eigene geschlossene Mühlen: " + myClosedMorrises + " (" + myClosedMorrisesPoints + ")" + " \n" +
                        "Anzahl eigene Steine: " + myNumberOfStones + " (" + myNumberOfStonesPoints + ")" + " \n" +
                        "Anzahl zweier eigener Steine nebeneinander mit freiem Feld daneben: " + myNumberOfTwoStonesTogether + " (" + myNumberOfTwoStonesTogetherPoints + ")" + " \n" +
                        "Anzahl zweier eigener Steine mit freier Lücke dazwischen: " + myNumberOfTwoStonesWithGap + " (" + myNumberOfTwoStonesWithGapPoints + ")" + " \n" +
                        "Eigene Zugmöglichkeiten: " + myPossibleMoves + " (" + myPossibleMovesPoints + ")" + " \n" +
                        "Fremde offene Mühlen: " + myEnemysOpenMorrises + " (" + myEnemysOpenMorrisesPoints + ")" + " \n" +
                        "Fremde geschlossene Mühlen: " + myEnemysClosedMorrises + " (" + myEnemysClosedMorrisesPoints + ")" + " \n" +
                        "Anzahl fremde Steine: " + myEnemysNumberOfStones + " (" + myEnemysNumberOfStonesPoints + ")" + " \n" +
                        "Anzahl zweier fremder Steine nebeneinander mit freiem Feld daneben " + myEnemysNumberOfTwoStonesTogether + " (" + myEnemysNumberOfTwoStonesTogetherPoints + ")" + " \n" +
                        "Anzahl zweier fremder Steine mit freier Lücke dazwischen: " + myEnemysNumberOfTwoStonesWithGap + " (" + myEnemysNumberOfTwoStonesWithGapPoints + ")" + " \n" +
                        "Gegnerische Zugmöglichkeiten: " + myEnemysPossiblesMoves + " (" + myEnemysPossiblesMovesPoints + ")" + " \n" +
                        "Score: " + score);


        return score;

    }

}
