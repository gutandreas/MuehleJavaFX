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

        MoveScorePoints moveScorePoints = new MoveScorePoints(100, 70,20, 20, 30,35, 2, -50, -30, -20, -100, -15, -20, -2);

        gameTree.clearTree();


        for (Move myMove1 : Advisor.getAllPossibleMoves(board, playerIndex)){

            // Eigener Zug 1
            BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
            boardPutMoveKillScoreSet1.setMove(myMove1);
            boardPutMoveKillScoreSet1.setLevel(1);

            Board clonedBoard1 = (Board) board.clone();
            clonedBoard1.move(myMove1, playerIndex);
            boardPutMoveKillScoreSet1.setBoard(clonedBoard1);

            System.out.println();
            System.out.println(boardPutMoveKillScoreSet1.getMove());
            System.out.println(boardPutMoveKillScoreSet1.getBoard());
            boardPutMoveKillScoreSet1.setScore(Advisor.getMoveScore(clonedBoard1, myMove1, moveScorePoints, playerIndex, true));
            gameTree.addSet(gameTree.getRoot(), boardPutMoveKillScoreSet1);



                // Eigener Kill 1
                if (boardPutMoveKillScoreSet1.getBoard().checkMorris(boardPutMoveKillScoreSet1.getMove().getTo())){
                    for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,playerIndex)){
                        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
                        boardPutMoveKillScoreSet2.setLevel(2);
                        Board clonedBoard2 = (Board) clonedBoard1.clone();
                        clonedBoard2.clearStone(killPosition);

                        boardPutMoveKillScoreSet2.setBoard(clonedBoard2);
                        boardPutMoveKillScoreSet2.setKill(killPosition);
                        boardPutMoveKillScoreSet2.setScore(boardPutMoveKillScoreSet1.getScore() + Advisor.getKillScore(clonedBoard2, killPosition, moveScorePoints, playerIndex, false));
                        gameTree.addSet(boardPutMoveKillScoreSet1, boardPutMoveKillScoreSet2);
                }
            }



            // Gegnerischer Zug
            for (Move enemysMove : Advisor.getAllPossibleMoves(clonedBoard1, 1-playerIndex)){
                BoardPutMoveKillScoreSet boardPutMoveKillScoreSet3 = new BoardPutMoveKillScoreSet();
                boardPutMoveKillScoreSet3.setMove(enemysMove);
                boardPutMoveKillScoreSet3.setLevel(3);



                Board clonedBoard3 = (Board) board.clone();
                clonedBoard1.move(enemysMove, 1-playerIndex);
                boardPutMoveKillScoreSet3.setBoard(clonedBoard3);


                boardPutMoveKillScoreSet3.setScore(Advisor.getMoveScore(clonedBoard3, enemysMove, moveScorePoints, playerIndex, true));
                gameTree.addSet(boardPutMoveKillScoreSet1, boardPutMoveKillScoreSet3);
                gameTree.keepOnlyWorstChild(boardPutMoveKillScoreSet1);



                // Eigener Zug 2
                for (Move myMove2 : Advisor.getAllPossibleMoves(clonedBoard3, playerIndex)){
                    BoardPutMoveKillScoreSet boardPutMoveKillScoreSet4 = new BoardPutMoveKillScoreSet();
                    boardPutMoveKillScoreSet4.setMove(myMove2);
                    boardPutMoveKillScoreSet4.setLevel(4);

                    Board clonedBoard4 = (Board) clonedBoard3.clone();
                    clonedBoard4.move(myMove2, playerIndex);
                    boardPutMoveKillScoreSet4.setBoard(clonedBoard4);

                    System.out.println();
                    System.out.println(boardPutMoveKillScoreSet4.getMove());
                    System.out.println(boardPutMoveKillScoreSet4.getBoard());
                    boardPutMoveKillScoreSet4.setScore(Advisor.getMoveScore(clonedBoard4, myMove2, moveScorePoints, playerIndex, true));
                    gameTree.addSet(boardPutMoveKillScoreSet3, boardPutMoveKillScoreSet4);}





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
        return gameTree.getBestMove();

    }

    private void pretendMove(Board board, Move move, MoveScorePoints moveScorePoints, int playerIndex, int level){
        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet = new BoardPutMoveKillScoreSet();
        boardPutMoveKillScoreSet.setMove(move);
        boardPutMoveKillScoreSet.setLevel(level);

        Board clonedBoard = (Board) board.clone();
        clonedBoard.move(move, playerIndex);
        boardPutMoveKillScoreSet.setBoard(clonedBoard);

        System.out.println();
        System.out.println(boardPutMoveKillScoreSet.getMove());
        System.out.println(boardPutMoveKillScoreSet.getBoard());
        boardPutMoveKillScoreSet.setScore(Advisor.getMoveScore(clonedBoard, move, moveScorePoints, playerIndex, true));
        gameTree.addSet(gameTree.getRoot(), boardPutMoveKillScoreSet);
    }






    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {

        return gameTree.getKillLeafWithBestScore().getKill();

    }


}
