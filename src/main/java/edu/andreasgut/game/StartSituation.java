package edu.andreasgut.game;

public class StartSituation {

    private int round;
    private Board board;
    private String description;

    public StartSituation(int round, Board board, String description) {
        this.round = round;
        this.board = board;
        this.description = description;
    }

    public int getRound() {
        return round;
    }

    public Board getBoard() {
        return board;
    }

    public String getDescription() {
        return description;
    }

    public static StartSituation[] produceStartSituations(){
        StartSituation[] startSituations = new StartSituation[5];

        Board board0 = new BoardImpl();

        StartSituation startSituation0 = new StartSituation(0, board0,
                "Leeres Spielfeld: Die maximale Freiheit für Ihre Kreativität...");

        startSituations[0] = startSituation0;

        Board board1 = new BoardImpl();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                board1.putStone(new Position(i, (j+7)%8), j%2);
            }
        }

        StartSituation startSituation1 = new StartSituation(48, board1,
                "Sonnenuntergang: Der eine Spieler besitzt lauter Mühlen, die vom Gegenspieler aber " +
                        "sofort geblockt werden können. Kann sich der Spieler mit den Mühlen tatsächlich durchsetzen?");

        startSituations[1] = startSituation1;


        Board board2 = new BoardImpl();

        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                int index = 1;
                if (counter > 6){
                    index = 0;
                }
                board2.putStone(new Position(i, j+1), index);
                counter++;
            }
        }




        StartSituation startSituation2 = new StartSituation(30, board2,
                "Ring in Ring: Beide Spieler haben ihre Steine in der gleichen Anordnung. Es gibt nur einen" +
                        " kleinen Unterschied... ...der eine kann nach innen ausweichen. Hilft ihm das?");

        startSituations[2] = startSituation2;


        Board board3 = new BoardImpl();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                board3.putStone(new Position(i, j), j%2);
                counter++;
            }
        }

        board3.putStone(new Position(2, 1), 0);




        StartSituation startSituation3 = new StartSituation(31, board3,
                "Fast eingeklemmt: Ein Spieler ist fast blockiert, hat aber noch einen freien Stein in der " +
                        "Mitte. Kann er sich aus dieser ungünstigen Lage befreien?");

        startSituations[3] = startSituation3;

        return startSituations;
    }

}
