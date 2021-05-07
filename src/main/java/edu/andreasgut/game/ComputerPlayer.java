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
        Stack<BoardPutMoveKillScoreSet> winningPath = gameTree.getWinningPath();
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }


        System.out.println("Getätigter Put: " + gameTree.getBestPut());


        return gameTree.getBestPut();
    }


    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {

        MoveScorePoints moveScorePoints = new MoveScorePoints(100, 70,20, 20, 30,35, 2, -50, -30, -20, -100, -15, -20, -2);

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

    }


}
