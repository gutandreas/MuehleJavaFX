package edu.andreasgut.game;

import java.util.LinkedList;
import java.util.List;

public class Board {

    final private int[][] array;
    private final Game game;

    public Board(Game game) {
        array = new int[3][8];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 8; j++){
                array[i][j] = 9;}
        }
        this.game = game;
    }


    protected Board(Board board){
        int[][] tempArray = new int[3][8];

        for (int i = 0; i < board.array.length; i++) {
            System.arraycopy(board.array[i], 0, tempArray[i], 0, board.array[0].length);
        }

        array = tempArray;
        this.game = board.game;
    }


    public int[][] getArray() {
        return array.clone();
    }


    public void putStone(Position position, int playerIndex) {
        array[position.getRing()][position.getField()] =  playerIndex;
    }


    public void move(Position from, Position to, int playerIndex) {
        putStone(to, playerIndex);
        clearStone(from);
    }


    public boolean checkPut(Position position){
        return isFieldFree(position);
    }


    public boolean checkMove(Position from, Position to, boolean allowedToJump){

        boolean destinationFree = isFieldFree(to);

        boolean destinationInRing = (from.getRing()==to.getRing() && Math.abs(from.getField()-to.getField())==1)
                || (from.getRing()==to.getRing() && Math.abs(from.getField()-to.getField())==7);

        boolean destinationBetweenRings = from.getField()%2==1 && from.getField()==to.getField()
                && Math.abs(from.getRing()-to.getRing())==1;

        return destinationFree && (destinationInRing || destinationBetweenRings || allowedToJump);
    }


    public boolean checkKill(Position position, int otherPlayerIndex){
        return array[position.getRing()][position.getField()] == otherPlayerIndex &&
                (!checkMorris(position) || countPlayersStones(otherPlayerIndex)==3);
    }


    public void clearStone(Position position) {
        array[position.getRing()][position.getField()] = 9;
    }


    public boolean checkMorris(Position position){
        boolean cornerField = position.getField()%2==0;
        boolean morris;
        int stone = array[position.getRing()][position.getField()];

        if(cornerField){
            morris = checkMorrisInRingFromCorner(position, stone);
        }
        else {
            morris = checkMorrisInRingFromCenter(position, stone) || checkMorrisBetweenRings(position, stone);
        }

        return morris;
    }


    private boolean checkMorrisInRingFromCorner(Position position, int playerIndex){

        boolean morrisUpwards = playerIndex == array[position.getRing()][(position.getField()+1)%8]
                && playerIndex == array[position.getRing()][(position.getField()+2)%8];
        boolean morrisDownwards = playerIndex == array[position.getRing()][(position.getField()+6)%8]
                && playerIndex == array[position.getRing()][(position.getField()+7)%8];

        return morrisUpwards || morrisDownwards;
    }


    private boolean checkMorrisInRingFromCenter(Position position, int playerIndex){
        return playerIndex == array[position.getRing()][(position.getField()+1)%8]
                && playerIndex == array[position.getRing()][(position.getField()+7)%8];
    }


    private boolean checkMorrisBetweenRings(Position position, int playerIndex){
        return playerIndex == array[(position.getRing()+1)%3][position.getField()]
                && playerIndex == array[(position.getRing()+2)%3][position.getField()];
    }


    public boolean checkIfAbleToMove(int playerIndex){
        return checkMovePossibilityInRing(playerIndex) || checkMovePossibilityBetweenRings(playerIndex)
                || countPlayersStones(playerIndex) == 3;
    }


    private boolean checkMovePossibilityBetweenRings(int playerIndex){
        for (int ring = 0; ring < 3; ring++){
            for (int field = 1; field < 8; field+=2){
                if (array[ring][field] == playerIndex){
                    switch (ring){
                        case 0:
                            if (array[ring+1][field] == 9) return true;
                            break;
                        case 1:
                            if (array[ring-1][field] == 9 || array[ring+1][field] == 9) return true;
                            break;
                        case 2:
                            if (array[ring-1][field] == 9) return true;
                    }
                }
            }
            }
        return false;
    }


    private boolean checkMovePossibilityInRing(int playerIndex){
        for (int ring = 0; ring < 3; ring++){
            for (int field = 0; field < 8; field++){
                if (array[ring][field] == playerIndex
                        && (array[ring][(field+1)%8] == 9 || array[ring][(field+7)%8] == 9)){
                    return true;
                }
                }
            }
        return false;
    }


    public boolean isThereStoneToKill(int otherPlayerIndex){
        for (int ring = 0; ring < 3; ring++){
            for (int field = 0; field < 8; field++){
                if (array[ring][field] == otherPlayerIndex && !checkMorris(new Position(ring,field))){
                    return true;
                }
            }
        }
        return false;
    }


    public int countPlayersStones(int playerIndex){
        int counter = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 8; j++){
                if (playerIndex == array[i][j]){
                    counter++;
                }
            }
        }
        return counter;
    }


    public boolean isFieldFree(Position position) {
        return array[position.getRing()][position.getField()] == 9;
    }


    public boolean isFieldOccupied(Position position){
        return !isFieldFree(position);
    }


    public boolean isThisMyStone(Position position, int ownPlayerIndex){
        return array[position.getRing()][position.getField()] == ownPlayerIndex;
    }


    public boolean isThisMyEnemysStone(Position position, int ownPlayerIndex){
        return isFieldOccupied(position) && !isThisMyStone(position, ownPlayerIndex);
    }

    public LinkedList<Position> getMyEnemysOpenMorrisList(int ownPlayerIndex){

        LinkedList<Position> positionList = new LinkedList<>();
        for (int quarter = 0; quarter < 4; quarter++) {

            //Powerpoint rot
            for (int startRing = 0; startRing < 2; startRing++) {
                if (isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                        && isThisMyEnemysStone(new Position(startRing + 1, (quarter * 2 + 1) % 8), ownPlayerIndex)
                        && isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 2) % 8), ownPlayerIndex)
                        && isFieldFree(new Position(startRing, (quarter * 2 + 1) % 8))) {

                    positionList.add(new Position(startRing, (quarter * 2 + 1) % 8));
                }}

            //Powerpoint blau
            for (int startRing = 1; startRing < 3; startRing++) {
                if (isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                        && isThisMyEnemysStone(new Position(startRing - 1, (quarter * 2 + 1) % 8), ownPlayerIndex)
                        && isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 2) % 8), ownPlayerIndex)
                        && isFieldFree(new Position(startRing, (quarter * 2 + 1) % 8))) {

                    positionList.add(new Position(startRing, (quarter * 2 + 1) % 8));
                }}

            //Powerpoint gelb
            for (int startRing = 1; startRing < 3; startRing++) {
                if (isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 7)% 8 ), ownPlayerIndex)
                        && isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 1)  % 8), ownPlayerIndex)
                        && isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 2) % 8), ownPlayerIndex)
                        && isFieldFree(new Position(startRing, (quarter * 2) % 8))) {

                    positionList.add(new Position(startRing, (quarter * 2) % 8));
                }}

            //Powerpoint pink
            for (int startRing = 1; startRing < 3; startRing++) {
                if (isThisMyEnemysStone(new Position(startRing, (quarter * 2)% 8 ), ownPlayerIndex)
                        && isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 1)  % 8), ownPlayerIndex)
                        && isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 3) % 8), ownPlayerIndex)
                        && isFieldFree(new Position(startRing, (quarter * 2 + 2) % 8))) {

                    positionList.add(new Position(startRing, (quarter * 2 + 2) % 8));
                }}

            //Powerpoint grün
            for (int startRing = 0; startRing < 3; startRing++) {
                switch (startRing) {
                    case 0: {
                        if ((isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 1) % 8), ownPlayerIndex)
                                || isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 7) % 8), ownPlayerIndex))
                            && isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                            && isThisMyEnemysStone(new Position(quarter * 2, quarter * 2), ownPlayerIndex)
                            && isFieldFree(new Position(startRing, quarter * 2))) {

                            positionList.add(new Position(startRing, quarter * 2));
                            break;}}
                    case 1: {
                        if (isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                            && (isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 1) % 8), ownPlayerIndex)
                                || isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 7) % 8), ownPlayerIndex))
                            && isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                            && isFieldFree(new Position(startRing, quarter * 2))) {

                            positionList.add(new Position(startRing, quarter * 2));
                            break;}}
                    case 3: {
                        if (isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                            && isThisMyEnemysStone(new Position(startRing, quarter * 2), ownPlayerIndex)
                            && (isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 1) % 8), ownPlayerIndex)
                                || isThisMyEnemysStone(new Position(startRing, (quarter * 2 + 7) % 8), ownPlayerIndex))
                            && isFieldFree(new Position(startRing, quarter * 2))) {

                            positionList.add(new Position(startRing, quarter * 2));
                            break;}}
                }}}

        for (Position position:positionList) {
            System.out.println("Offene Mühle mit Lücke in Ring " + position.getRing() +" auf Feld " + position.getField());

        }

        return positionList;
    }


    @Override
    public String toString(){
        String board = "";
        for (int i = 0; i <= 6; i++){
            board += printRow(i);}
        return board;
    }

    private String printRow(int row){
        String rowString ="";
        String space;
        switch (row){
            case 0:
                space = "    ";
                for (int i = 0; i < 3; i++){
                    rowString += array[row][i] + space;
                }
                rowString += "\n";
                break;
            case 1:
                space = "   ";
                rowString += " ";
                for (int i = 0; i < 3; i++){
                    rowString += array[row][i] + space;
                }
                rowString += "\n";
                break;
            case 2:
                space = "  ";
                rowString += "  ";
                for (int i = 0; i < 3; i++){
                    rowString += array[row][i] + space;
                }
                rowString += "\n";
                break;
            case 3:
                for (int i = 0; i < 3; i++){
                    rowString += array[i][7];
                }
                rowString += "     ";
                for (int i = 2; i >= 0; i--){
                    rowString += array[i][3];
                }
                rowString += "\n";
                break;
            case 4:
                space = "  ";
                rowString += "  ";
                for (int i = 6; i > 3; i--){
                    rowString += array[2][i] + space;
                }
                rowString += "\n";
                break;
            case 5:
                space = "   ";
                rowString += " ";
                for (int i = 6; i > 3; i--){
                    rowString += array[1][i] + space;
                }
                rowString += "\n";
                break;
            case 6:
                space = "    ";
                for (int i = 6; i > 3; i--){
                    rowString += array[0][i] + space;
                }
                rowString += "\n";
                break;
        }

    return rowString;}


    public Object clone(){
        return new Board(this);
    }}
