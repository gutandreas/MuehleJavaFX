package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.util.*;

public class ComputerPlayer extends Player {


    public ComputerPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }

    private LinkedList<BoardPutMoveKillScoreSet> playTree;
    GameTree gameTree = new GameTree();



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

        ScorePoints moveScorePoints = new ScorePoints(100, 70,20, 20, 30,35, 2, -50, -30, -20, -100, -15, -20, -2);

        gameTree.clearTree();


        for (Move move : Advisor.getAllPossibleMoves(board, playerIndex)){


            BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
            boardPutMoveKillScoreSet1.setMove(move);
            boardPutMoveKillScoreSet1.setLevel(1);
            gameTree.addSet(gameTree.root, boardPutMoveKillScoreSet1);

            Board clonedBoard = (Board) board.clone();
            clonedBoard.move(move, playerIndex);
            boardPutMoveKillScoreSet1.setBoard(clonedBoard);

            System.out.println();
            System.out.println(boardPutMoveKillScoreSet1.getMove());
            System.out.println(boardPutMoveKillScoreSet1.getBoard());
            boardPutMoveKillScoreSet1.setScore(Advisor.getMoveScore(clonedBoard, move, moveScorePoints, playerIndex, true));
            gameTree.addSet(gameTree.getRoot(), boardPutMoveKillScoreSet1);




                if (boardPutMoveKillScoreSet1.getBoard().checkMorris(boardPutMoveKillScoreSet1.getMove().getTo())){
                    for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard,playerIndex)){
                        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
                        boardPutMoveKillScoreSet2.setLevel(2);
                        Board clonedBoard2 = (Board) clonedBoard.clone();
                        clonedBoard2.clearStone(killPosition);

                        boardPutMoveKillScoreSet2.setBoard(clonedBoard2);
                        boardPutMoveKillScoreSet2.setKill(killPosition);
                        boardPutMoveKillScoreSet2.setScore(1000);
                        boardPutMoveKillScoreSet2.setScore(boardPutMoveKillScoreSet1.getScore() + Advisor.getKillScore(clonedBoard2, killPosition, moveScorePoints, playerIndex, false));
                        gameTree.addSet(boardPutMoveKillScoreSet1, boardPutMoveKillScoreSet2);
                }
            }}


        for (BoardPutMoveKillScoreSet set : gameTree.getLeaves()){

            System.out.println(set);
        }


        System.out.println();
        System.out.println("Gewinnerpfad:");
        Stack<BoardPutMoveKillScoreSet> winningPath = gameTree.getWinningPath();
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }


        System.out.println("Getätigter Zug: " + gameTree.getBestMove());
        return gameTree.getBestMove();

    }






    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {

        return gameTree.getKillLeafWithBestScore().getKill();




        /*BoardPutMoveKillScoreSet currentSet = playTree.getFirst();
        System.out.println("Maximaler Score: " + currentSet.getScore());

        while (currentSet.getKill() == null){
            currentSet = currentSet.getParent();}

        System.out.println("Gekillte " + currentSet.getKill());
        System.out.println("Erreichter Score: " + currentSet.getScore());

        return currentSet.getKill();*/

        /*

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
        }*/
    }


}
