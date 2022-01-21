package edu.andreasgut.game;

public class BoardImpl implements Board {
    final private int[][] array;
    private final Game game;

    public BoardImpl(Game game) {
        array = new int[3][8];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 8; j++){
                array[i][j] = 9;}
        }
        this.game = game;
    }

    public BoardImpl(){
        array = new int[3][8];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 8; j++){
                array[i][j] = 9;}
        }
        game = null;
    }


    protected BoardImpl(BoardImpl board){
        int[][] tempArray = new int[3][8];

        for (int i = 0; i < board.array.length; i++) {
            System.arraycopy(board.array[i], 0, tempArray[i], 0, board.array[0].length);
        }

        array = tempArray;
        this.game = board.game;
    }


    @Override
	public void putStone(Position position, int playerIndex) {
        array[position.getRing()][position.getField()] =  playerIndex;
    }


    @Override
	public void moveStone(Position from, Position to, int playerIndex) {
        putStone(to, playerIndex);
        removeStone(from);
    }


    @Override
	public boolean isValidPut(Position position){
        return isFieldFree(position);
    }


    @Override
    public boolean isValidMove(Position from, Position to, boolean allowedToJump) {

        boolean destinationFree = isFieldFree(to);

        boolean destinationInRing = (from.getRing()==to.getRing() && Math.abs(from.getField()-to.getField())==1)
                || (from.getRing()==to.getRing() && Math.abs(from.getField()-to.getField())==7);

        boolean destinationBetweenRings = from.getField()%2==1 && from.getField()==to.getField()
                && Math.abs(from.getRing()-to.getRing())==1;

        return destinationFree && (destinationInRing || destinationBetweenRings || allowedToJump);
    }


    @Override
	public boolean isKillPossibleAt(Position position, int otherPlayerIndex){
        return array[position.getRing()][position.getField()] == otherPlayerIndex &&
                (!isMorrisAt(position) || numberOfStonesOf(otherPlayerIndex)==3);
    }


    @Override
	public void removeStone(Position position) {
        array[position.getRing()][position.getField()] = 9;
    }


    @Override
	public boolean isMorrisAt(Position position){
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


    @Override
	public boolean canPlayerMove(int playerIndex){
        return checkMovePossibilityInRing(playerIndex) || checkMovePossibilityBetweenRings(playerIndex)
                || numberOfStonesOf(playerIndex) == 3;
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


    @Override
	public boolean canPlayerKill(int otherPlayerIndex){

        if (numberOfStonesOf(otherPlayerIndex) == 3 && game.movePhase){
            return true;
        }

        for (int ring = 0; ring < 3; ring++){
            for (int field = 0; field < 8; field++){
                if (array[ring][field] == otherPlayerIndex && !isMorrisAt(new Position(ring,field))){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
	public int numberOfStonesOf(int playerIndex){
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


    @Override
	public boolean isFieldFree(Position position) {
        return array[position.getRing()][position.getField()] == 9;
    }


    @Override
	public boolean isFieldOccupied(Position position){
        return !isFieldFree(position);
    }


    @Override
	public boolean isThisMyStone(Position position, int ownPlayerIndex){
        return array[position.getRing()][position.getField()] == ownPlayerIndex;
    }


    @Override
	public boolean isThisMyEnemysStone(Position position, int ownPlayerIndex){
        return isFieldOccupied(position) && !isThisMyStone(position, ownPlayerIndex);
    }

    @Override
	public int getNumberOnPosition(Position position) {
        return array[position.getRing()][position.getField()];
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


    @Override
	public Board clone(){
        return new BoardImpl(this);
    }

}
