package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.util.*;
import java.util.stream.Collectors;

public class ComputerPlayer extends Player {


    public ComputerPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }



    @Override
    Position put(Board board, int playerIndex) {


        // 1. Priorität: Mühlen schliessen
        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {

                // bildet Mühle wenn 2 Steine über Ringe hinweg (egal ob mit oder ohne Lücke)
                if (board.isThisMyStone(new Position(row, field), playerIndex)
                        && field % 2 == 1
                        && board.isThisMyStone(new Position((row + 1) % 3, field), playerIndex)
                        && board.isFieldFree(new Position((row + 2) % 3, field))) {
                    System.out.println("Computerstrategie: bildet Mühle wenn 2 Steine über Ringe hinweg");
                    return new Position((row + 2) % 3, field);
                }

                // bildet Mühle wenn 2 Steine innerhalb von Ring nebeneinander
                if (board.isThisMyStone(new Position(row, field), playerIndex)
                        && board.isThisMyStone(new Position(row, (field + 1) % 8), playerIndex)) {

                    // setzt Stein vor der 2er-Reihe sofern frei
                    if ((field % 2) == 1 && board.isFieldFree(new Position(row, (field + 7) % 8))) {
                        System.out.println("Computerstrategie: setzt Stein vor der 2er-Reihe sofern frei");
                        return new Position(row, (field + 7) % 8);
                    }

                    // setzt Stein nach der 2er-Reihe sofern frei
                    if ((field % 2) == 0 && board.isFieldFree(new Position(row, (field + 2) % 8))) {
                        System.out.println("Computerstrategie: setzt Stein nach der 2er-Reihe sofern frei");
                        return new Position(row, (field + 2) % 8);
                    }
                }

                // bildet Mühle wenn 2 Steine mit Lücke innerhalb von Ring
                if (board.isThisMyStone(new Position(row, field), playerIndex)
                        && board.isThisMyStone(new Position(row, (field+2)%8), playerIndex)
                        && (field % 2) == 0
                        && board.isFieldFree(new Position(row, (field + 1) % 8))) {
                    System.out.println("Computerstrategie: bildet Mühle wenn 2 Steine mit Lücke innerhalb von Ring");
                    return new Position(row, (field + 1) % 8);
                }
            }
        }

        // 2. Priorität: Mühlen blocken
        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {

                // blockt wenn 2 Steine über Ringe hinweg (egal ob mit oder ohne Lücke)
                if (board.isThisMyEnemysStone(new Position(row, field), playerIndex)
                        && field%2==1
                        && board.isThisMyEnemysStone(new Position((row+1)%3, field), playerIndex)
                        && board.isFieldFree(new Position((row+2)%3, field))) {
                    System.out.println("Computerstrategie: blockt wenn 2 Steine über Ringe hinweg");
                    return new Position((row+2)%3, field);}

                // blockt wenn 2 Steine innerhalb von Ring nebeneinander
                if (board.isThisMyEnemysStone(new Position(row, field), playerIndex)
                        && board.isThisMyEnemysStone(new Position(row, (field+1)%8), playerIndex)){
                    // blockt vor der 2er-Reihe sofern frei
                    if ((field % 2) == 1 && board.isFieldFree(new Position(row, (field + 7) % 8 ))) {
                        System.out.println("Computerstrategie: blockt wenn 2 Steine innerhalb von Ring nebeneinander");
                        return new Position(row, (field + 7) % 8);
                    }
                    // blockt nach der 2er-Reihe sofern frei
                    if ((field % 2) == 0 && board.isFieldFree(new Position(row ,(field + 2) % 8))) {
                        System.out.println("Computerstrategie: blockt wenn 2 Steine innerhalb von Ring nebeneinander");
                        return new Position(row, (field + 2) % 8);
                    }
                }

                // blockt wenn 2 Steine mit Lücke innerhalb von Ring
                if (board.isThisMyEnemysStone(new Position(row, field), playerIndex)
                        && board.isThisMyEnemysStone(new Position(row, (field+2)%8), playerIndex)
                        &&(field % 2) == 0
                        && board.isFieldFree(new Position(row, (field + 1) % 8))) {
                    System.out.println("Computerstrategie: blockt wenn 2 Steine mit Lücke innerhalb von Ring");
                    return new Position(row, (field + 1) % 8);
                }
            }
        }

        // 3. Priorität: Wählt Ecke einer leeren Linie oder in Linie mit einem eigenen Stein
        for (int row = 0; row < 3; row++) {
            for (int field = 0; field < 8; field++) {

                if (board.isFieldFree(new Position(row, field)) && field%2==0) {

                    if (board.isFieldFree(new Position(row, (field + 1) % 8))
                            && (board.isFieldFree(new Position(row, (field + 2) % 8))
                            || board.isThisMyStone(new Position(row, (field + 2) % 8), playerIndex))) {
                        System.out.println("Computerstrategie: wählt Ecke einer leeren Linie oder in Linie mit einem eigenen Stein");
                        return new Position(row, field);
                    }

                    if (board.isFieldFree(new Position(row, (field + 7) % 8))
                            && (board.isFieldFree(new Position(row, (field + 6) % 8))
                            || board.isThisMyStone(new Position(row, (field + 6) % 8), playerIndex))) {
                        System.out.println("Computerstrategie: wählt Ecke einer leeren Linie oder in Linie mit einem eigenen Stein");
                        return new Position(row, field);
                    }
                }
            }}


        // 4. Priorität: wählt zufälliges leeres Feld
        while (true){
            Random random = new Random();
            Position tempPosition = new Position(random.nextInt(3),  random.nextInt(8));
            if (board.isFieldFree(tempPosition)){
                System.out.println("Computerstrategie: wählt zufälliges leeres Feld");
                return tempPosition;
            }
        }
    }


    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {
        Position[] positions = new Position[2];

        ScorePoints scorePoints = new ScorePoints(20,20, 100,80, 1,-150,-15, 20);

        LinkedList<BoardMoveKillScoreSet> setList = new LinkedList<>();
        for (Move move : Advisor.getAllPossibleMoves(board,playerIndex)) {

            BoardMoveKillScoreSet boardMoveKillScoreSet1 = new BoardMoveKillScoreSet();
            boardMoveKillScoreSet1.setMove(move);

            Board clonedBoard1 = (Board) board.clone();
            clonedBoard1.move(move, playerIndex);
            boardMoveKillScoreSet1.setBoard(clonedBoard1);

            boardMoveKillScoreSet1.setScore(Advisor.getScore(clonedBoard1, move, scorePoints, playerIndex, false));
            setList.add(boardMoveKillScoreSet1);
        }

        for (BoardMoveKillScoreSet boardMoveKillScoreSet1 : setList) {
            System.out.println("Möglicher Zug");
            System.out.println(boardMoveKillScoreSet1.getBoard());
            System.out.println(boardMoveKillScoreSet1.getMove());
            Advisor.getScore(boardMoveKillScoreSet1.getBoard(), boardMoveKillScoreSet1.getMove(), scorePoints, playerIndex, true);
            System.out.println();
            System.out.println();
        }

        setList.sort(new Comparator<BoardMoveKillScoreSet>() {
            @Override
            public int compare(BoardMoveKillScoreSet o1, BoardMoveKillScoreSet o2) {
                if (o1.getScore() == o2.getScore()) return 0;
                if (o1.getScore() > o2.getScore()) return -1;
                return 1;
            }
        });


        System.out.println("Getätigter Zug: " + setList.getFirst().getMove());
        return setList.getFirst().getMove();

    }






    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {
        Position position = null;

        // 1. Priorität: Killt Stein in 2er-Reihe
        for (int row = 0; row < 3; row++){
            for (int field = 0; field < 8; field++){

                position = new Position(row, field);

                if (board.isThisMyEnemysStone(position, ownPlayerIndex) && board.checkKill(position, otherPlayerIndex)){

                    // Ungerade Felder in Reihe 0 oder 1
                    if (field%2 == 1
                        && (row == 0 || row == 1)){
                        if (board.isThisMyEnemysStone(new Position(row, (field+1)%8), ownPlayerIndex)
                            || board.isThisMyEnemysStone(new Position(row, (field+7)%8), ownPlayerIndex)
                            || board.isThisMyEnemysStone(new Position(row+1, field), ownPlayerIndex)){
                            System.out.println("Computerstrategie: Stein aus 2er-Reihe entfernt");
                            return position;
                        }
                    }

                    // Ungerade Felder in Reihe 1 oder 2
                    if (field%2 == 1
                            && (row == 1 || row == 2)){
                        if (board.isThisMyEnemysStone(new Position(row, (field+1)%8), ownPlayerIndex)
                                || board.isThisMyEnemysStone(new Position(row, (field+7)%8), ownPlayerIndex)
                                || board.isThisMyEnemysStone(new Position(row-1, field), ownPlayerIndex)){
                            System.out.println("Computerstrategie: Stein aus 2er-Reihe entfernt");
                            return position;
                        }
                    }

                    // Gerade Felder
                    if (field%2 == 0
                            && (board.isThisMyEnemysStone(new Position(row, (field+1)%8), ownPlayerIndex)
                                || board.isThisMyEnemysStone(new Position(row, (field+7)%8), ownPlayerIndex))){
                        System.out.println("Computerstrategie: Stein aus 2er-Reihe entfernt");
                        return position;
                    }
                }
            }
        }

        // 2. Priorität: Killt zufälligen gegnerischen Stein
        while (true){
            Random random = new Random();
            position = new Position(random.nextInt(3), random.nextInt(8));
            if (board.checkKill(position, otherPlayerIndex)){
                System.out.println("Computerstrategie: Zufälligen Stein entfernt");
                return position;
            }
        }
    }
}
