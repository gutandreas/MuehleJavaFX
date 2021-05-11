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

        gameTree.clearTree();

        MoveScorePoints putScorePoints = new MoveScorePoints(100, 70,20, 20, 30,35, 2, -50, -30, -20, -100, -15, -20, -2);


        for (Position position : Advisor.getAllFreeFields(board)) {


            BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
            boardPutMoveKillScoreSet1.setPut(position);
            boardPutMoveKillScoreSet1.setLevel(1);
            gameTree.addSet(gameTree.root, boardPutMoveKillScoreSet1);

            Board clonedBoard = (Board) board.clone();
            clonedBoard.putStone(position, playerIndex);
            boardPutMoveKillScoreSet1.setBoard(clonedBoard);

            System.out.println();
            System.out.println(boardPutMoveKillScoreSet1.getPut());
            System.out.println(boardPutMoveKillScoreSet1.getBoard());
            boardPutMoveKillScoreSet1.setScore(Advisor.getPutScore(clonedBoard, position, putScorePoints, playerIndex, true));
            gameTree.addSet(gameTree.getRoot(), boardPutMoveKillScoreSet1);

            if (boardPutMoveKillScoreSet1.getBoard().checkMorris(boardPutMoveKillScoreSet1.getPut())){
                for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard,playerIndex)){
                    BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
                    boardPutMoveKillScoreSet2.setLevel(2);
                    Board clonedBoard2 = (Board) clonedBoard.clone();
                    clonedBoard2.clearStone(killPosition);

                    boardPutMoveKillScoreSet2.setBoard(clonedBoard2);
                    boardPutMoveKillScoreSet2.setKill(killPosition);
                    boardPutMoveKillScoreSet2.setScore(boardPutMoveKillScoreSet1.getScore() + Advisor.getKillScore(clonedBoard2, killPosition, putScorePoints, playerIndex, false));
                    gameTree.addSet(boardPutMoveKillScoreSet1, boardPutMoveKillScoreSet2);
                }
            }

        }

        for (BoardPutMoveKillScoreSet set : gameTree.getLeaves()){

            System.out.println(set);
        }


        System.out.println();
        System.out.println("Gewinnerpfad:");
        Stack<BoardPutMoveKillScoreSet> winningPath = gameTree.getPath(gameTree.getLeafWithBestScore());
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }


        System.out.println("Gesetzter Stein: " + gameTree.getBestPut());


        return gameTree.getBestPut();
    }


    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {

        MoveScorePoints moveScorePoints = new MoveScorePoints(300, 70,10, 10, 30,35, 2, -50, -30, -20, -100, -15, -20, -2);

        gameTree.clearTree();


        for (Move myMove1 : Advisor.getAllPossibleMoves(board, playerIndex)){

            // Eigener Zug 1
            LinkedList<BoardPutMoveKillScoreSet> list1 = pretendMove(board, myMove1, moveScorePoints, gameTree.getRoot(), playerIndex,1);

            for (BoardPutMoveKillScoreSet set1 : list1){

                // Gegnerischer Zug
                for (Move enemysMove : Advisor.getAllPossibleMoves(set1.getBoard(), 1-playerIndex)){
                    LinkedList<BoardPutMoveKillScoreSet> list2 = pretendMove(set1.getBoard(), enemysMove, moveScorePoints, set1, 1-playerIndex, 3);

                    gameTree.keepOnlyWorstChild(set1);

                    // Eigener Zug 2
                    for (BoardPutMoveKillScoreSet set2 : list2) {

                        for (Move myMove2 : Advisor.getAllPossibleMoves(set2.getBoard(), playerIndex)) {
                            pretendMove(set2.getBoard(), myMove2, moveScorePoints, set2, playerIndex, 5);
                        }
                    }
                }
            }
        }


        for (BoardPutMoveKillScoreSet set : gameTree.getLeaves()){

            System.out.println(set);
        }


        System.out.println();
        System.out.println("Gewinnerpfad:");
        Stack<BoardPutMoveKillScoreSet> winningPath = gameTree.getPath(gameTree.getLeafWithBestScore());
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }


        System.out.println("Getätigter Zug: " + gameTree.getBestMove());
        System.out.println();
        return gameTree.getBestMove();

    }

    private LinkedList<BoardPutMoveKillScoreSet> pretendMove(Board board, Move move, MoveScorePoints moveScorePoints, BoardPutMoveKillScoreSet parent, int playerIndex, int level){

        LinkedList<BoardPutMoveKillScoreSet> list = new LinkedList<>();
        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
        boardPutMoveKillScoreSet1.setMove(move);
        boardPutMoveKillScoreSet1.setLevel(level);

        Board clonedBoard1 = (Board) board.clone();
        clonedBoard1.move(move, playerIndex);
        boardPutMoveKillScoreSet1.setBoard(clonedBoard1);

        /*System.out.println();
        System.out.println(boardPutMoveKillScoreSet1.getMove());
        System.out.println(boardPutMoveKillScoreSet1.getBoard());*/
        boardPutMoveKillScoreSet1.setScore(Advisor.getMoveScore(clonedBoard1, move, moveScorePoints, playerIndex, false));
        gameTree.addSet(parent, boardPutMoveKillScoreSet1);
        list.add(boardPutMoveKillScoreSet1);

       if (clonedBoard1.checkMorris(move.getTo())){
           for (Position killPosition : Advisor.getAllPossibleKills(board, playerIndex)){
           BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
           boardPutMoveKillScoreSet2.setKill(killPosition);
           boardPutMoveKillScoreSet2.setLevel(level+1);

           Board clonedBoard2 = (Board) board.clone();
           clonedBoard2.clearStone(killPosition);
           boardPutMoveKillScoreSet2.setBoard(clonedBoard2);

           /*System.out.println();
           System.out.println(boardPutMoveKillScoreSet2.getMove());
           System.out.println(boardPutMoveKillScoreSet2.getBoard());*/
           boardPutMoveKillScoreSet2.setScore(parent.getScore() + Advisor.getKillScore(clonedBoard2, killPosition, moveScorePoints, playerIndex, false));
           gameTree.addSet(boardPutMoveKillScoreSet1, boardPutMoveKillScoreSet2);
           list.add(boardPutMoveKillScoreSet2);}
       }

        return list;
    }







    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {

        return gameTree.getKillLeafWithBestScore().getKill();

    }


}
