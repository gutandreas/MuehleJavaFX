package edu.andreasgut.game;

public class Computer extends Player {


    public Computer(String name) {
        super(name);
    }

    //Besetzt den nächsten freien Stein
    public Position compPutStone(Board field) {
        Position position = new Position();
        int i;
        int j;

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (field.getArray()[i][j] == 9) {
                    position.setRing(i);
                    position.setField(j);;
                    return position;
                }
            }
        }
        return position;
    }

    //Entfernt den nächsten gegnerischen Stein
    public Position compKillStone(Board board) {
        Position position = new Position();
        int i;
        int j;

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 8; j++) {
                if (board.getArray()[i][j] == 0 && board.checkKill(new Position(i,j))) {
                    position.setRing(i);
                    position.setField(j);

                    return position;
                }
            }
        }
        return position;
    }

}
