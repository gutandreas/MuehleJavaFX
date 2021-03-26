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

    public void putStone(Position position, int playerIndex) throws InvalidFieldException {
        if (isFieldOccupied(position)){
            throw new InvalidFieldException("Dieses Feld ist besetzt");
        }
        array[position.getRing()][position.getField()] =  playerIndex;
    }

    public void move(Position from, Position to, int playerIndex) throws InvalidMoveException, InvalidFieldException {
        if (array[from.getRing()][from.getField()]!= playerIndex){
            throw new InvalidMoveException("Kein bzw. nicht dein Stein");
        }

        if (checkIfJump(playerIndex)){
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

    public boolean checkIfJump(int playerIndex){
        return game.getCurrentPlayer().isAllowedToJump();
    }

    public boolean checkDestination(Position from, Position to){
        return ((from.getField()%2==1 && from.getField()==to.getField() && Math.abs(from.getRing()-to.getRing())==1)
                || (from.getRing()==to.getRing() && Math.abs((from.getField())-(to.getField()))==1)
                || (from.getRing()==to.getRing() && Math.abs((from.getField())-(to.getField()))==7));
    }

    public boolean checkTriple(Board oldField){
        return checkBetweenRings(oldField) || checkInRing(oldField);
    }



    private boolean checkBetweenRings(Board oldField){
        for (int field = 1; field <8;){
            for (int player = 0; player < 2; player++){
                if (array[0][field]==player && array[1][field]==player
                        && array[2][field]==player){
                    if  (!(array[0][field]== oldField.array[0][field] && array[1][field]==oldField.array[1][field]
                            && array[2][field]==oldField.array[2][field])){
                        return true;}
                }
            }
            field+=2;
        }
        return false;
    }


    private boolean checkInRing(Board oldField){

        for (int ring = 0; ring < 3; ring++){
            for (int field = 0; field <4; field++){
                for (int player = 0; player < 2; player++){
                    if (array[ring][field*2]==player && array[ring][field*2+1]==player
                            && array[ring][(field*2+2)%8]==player){
                        if (!(array[ring][field*2]==oldField.array[ring][field*2]
                                && array[ring][field*2+1]==oldField.array[ring][field*2+1]
                                && array[ring][(field*2+2)%8]==oldField.array[ring][(field*2+2)%8])){
                            return true;}}
                }
            }
        }
        return false;
    }

    public boolean checkIfAbleToMove(Player player){
        return checkMovePossibilityInRing() || checkMovePossibilityBetweenRings()
                || game.getCurrentPlayer().isAllowedToJump();
    }

    private boolean checkMovePossibilityBetweenRings(){
        for (int ring = 0; ring < 3; ring++){
            for (int field = 1; field < 8;){
                if (array[ring][field] == game.getCurrentPlayerIndex()){
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
                field+=2;
            }
            }
        return false;
    }


    private boolean checkMovePossibilityInRing(){
        for (int ring = 0; ring < 3; ring++){
            for (int field = 0; field < 8; field++){
                if (array[ring][field] == game.getCurrentPlayerIndex()
                        && (array[ring][(field+1)%8] == 9 || array[ring][(field+7)%8] == 9)){
                    return true;
                }
                }
            }
        return false;
    }

    public void killStone(Position position, int otherPlayerIndex) throws InvalidKillException {
        if (array[position.getRing()][position.getField()] == otherPlayerIndex &&
                (checkKill(position) || game.getOtherPlayer().isAllowedToJump())){
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


    public int numberOfStonesCurrentPlayer(){
        int counter = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 8; j++){
                if (game.getCurrentPlayerIndex() == array[i][j]){
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean isFieldOccupied(Position position){
        return array[position.getRing()][position.getField()] != 9;
    }


    public void printField(){
        for (int i = 0; i <= 6; i++){
            printRow(i);}
        System.out.println();
    }

    private void printRow(int row){
        String space;
        switch (row){
            case 0:
                space = "    ";
                for (int i = 0; i < 3; i++){
                    System.out.print(array[row][i]);
                    System.out.print(space);
                }
                System.out.println();
                break;
            case 1:
                space = "   ";
                System.out.print(" ");
                for (int i = 0; i < 3; i++){
                    System.out.print(array[row][i]);
                    System.out.print(space);
                }
                System.out.println();
                break;
            case 2:
                space = "  ";
                System.out.print("  ");
                for (int i = 0; i < 3; i++){
                    System.out.print(array[row][i]);
                    System.out.print(space);
                }
                System.out.println();
                break;
            case 3:
                for (int i = 0; i < 3; i++){
                    System.out.print(array[i][7]);
                }
                System.out.print("     ");
                for (int i = 2; i >= 0; i--){
                    System.out.print(array[i][3]);
                }
                System.out.println();
                break;
            case 4:
                space = "  ";
                System.out.print("  ");
                for (int i = 6; i > 3; i--){
                    System.out.print(array[2][i]);
                    System.out.print(space);
                }
                System.out.println();
                break;
            case 5:
                space = "   ";
                System.out.print(" ");
                for (int i = 6; i > 3; i--){
                    System.out.print(array[1][i]);
                    System.out.print(space);
                }
                System.out.println();
                break;
            case 6:
                space = "    ";
                System.out.print("");
                for (int i = 6; i > 3; i--){
                    System.out.print(array[0][i]);
                    System.out.print(space);
                }
                System.out.println();
                break;
        }}



    public Object clone(){
        return new Board(this);
    }}
