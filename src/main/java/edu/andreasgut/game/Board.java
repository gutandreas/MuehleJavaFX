package edu.andreasgut.game;

public class Board implements Cloneable{

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

    public void putStone(Position position, int playerIndex) throws InvalidPutException {
        if (isFieldOccupied(position)){
            throw new InvalidPutException("Dieses Feld ist besetzt");
        }
        array[position.getRing()][position.getField()] =  playerIndex;
    }

    public void move(Position from, Position to, int playerIndex, boolean allowedToJump) throws InvalidMoveException, InvalidPutException {
        if (array[from.getRing()][from.getField()]!= playerIndex){
            throw new InvalidMoveException("Kein bzw. nicht dein Stein");
        }

        if (allowedToJump){
            putStone(to, playerIndex);
            clear(from);
        }
        else {
            if (checkDestination(from, to)){
                putStone(to, playerIndex);
                clear(from);}
            else throw new InvalidMoveException("Kein möglicher Zielort");}
    }

    private void clear(Position position){
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

    private boolean checkMorrisInRingFromCorner(Position position, int stone){

        boolean morrisUpwards = stone == array[position.getRing()][(position.getField()+1)%8]
                && stone == array[position.getRing()][(position.getField()+2)%8];
        boolean morrisDownwards = stone == array[position.getRing()][(position.getField()+6)%8]
                && stone == array[position.getRing()][(position.getField()+7)%8];

        return morrisUpwards || morrisDownwards;
    }

    private boolean checkMorrisInRingFromCenter(Position position, int stone){
        return stone == array[position.getRing()][(position.getField()+1)%8]
                && stone == array[position.getRing()][(position.getField()+7)%8];
    }

    private boolean checkMorrisBetweenRings(Position position, int stone){
        return stone == array[(position.getRing()+1)%3][position.getField()]
                && stone == array[(position.getRing()+2)%3][position.getField()];
    }

    public boolean checkDestination(Position from, Position to){
        return ((from.getField()%2==1 && from.getField()==to.getField() && Math.abs(from.getRing()-to.getRing())==1)
                || (from.getRing()==to.getRing() && Math.abs((from.getField())-(to.getField()))==1)
                || (from.getRing()==to.getRing() && Math.abs((from.getField())-(to.getField()))==7));
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

    public void killStone(Position position, int otherPlayerIndex) throws InvalidKillException {
        if (array[position.getRing()][position.getField()] == otherPlayerIndex &&
                (checkKill(position) || countPlayersStones(otherPlayerIndex)==3)){
            clear(position);
        }
        else throw new InvalidKillException(
                "Auf diesem Feld befindet sich kein gegnerischer Stein oder er liegt in einer Mühle (3er-Reihe)");
    }

    public boolean isThereStoneToKill(int otherPlayerIndex){
        int stone = otherPlayerIndex;
        for (int ring = 0; ring < 3; ring++){
            for (int field = 0; field < 8; field++){
                if (array[ring][field] == stone && !isStoneInTriple(new Position(ring,field),stone)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isStoneInTriple(Position position, int stone){
        int fieldPosition = position.getField()%2;
        switch (fieldPosition){
            case 0:
                if((stone==array[position.getRing()][(position.getField()+1)%8] && stone==array[position.getRing()][(position.getField()+2)%8])
                    || (stone==array[position.getRing()][(position.getField()+7)%8] && stone==array[position.getRing()][(position.getField()+6)%8])){
                    return true;}
                    break;
            case 1:
                if((stone==array[position.getRing()][(position.getField()+7)%8] && stone==array[position.getRing()][(position.getField()+1)%8])
                        || (stone==array[(position.getRing()+1)%3][position.getField()] && stone==array[(position.getRing()+2)%3][position.getField()])){
                    return true;}
                    break;
        }
        return false;


    }

    public boolean checkKill(Position position){

        int stone = array[position.getRing()][position.getField()];
        return checkKillInRing(position, stone) && checkKillBetweenRing(position, stone);

    }

    private boolean checkKillInRing(Position position, int stone){
        boolean killOkay = true;
        int fieldPos = position.getField()%2;
        switch (fieldPos){
            case 0:
                if ((stone == array[position.getRing()][(position.getField()+1)%8] && stone == array[position.getRing()][(position.getField()+2)%8])
                    || (stone == array[position.getRing()][(position.getField()+7)%8] && stone == array[position.getRing()][(position.getField()+6)%8])){
                    killOkay = false;
                }
                break;
            case 1:
                if (stone == array[position.getRing()][(position.getField()+7)%8] && stone == array[position.getRing()][(position.getField()+1)%8]){
                    killOkay = false;
                }
                break;
        }

        return killOkay;
    }

    private boolean checkKillBetweenRing(Position position, int stone){
        return !(stone == array[(position.getRing()+1)%3][position.getField()] && stone == array[(position.getRing()+2)%3][position.getField()]);

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

    public boolean isFieldOccupied(Position position){
        return array[position.getRing()][position.getField()] != 9;
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
