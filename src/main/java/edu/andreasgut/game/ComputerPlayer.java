package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.util.*;

public class ComputerPlayer extends Player {


    public ComputerPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }

    GameTree gameTree = new GameTree();



    @Override
    Position put(Board board, int playerIndex) {

        gameTree.clearTree();

        ScorePoints putScorePoints = new ScorePoints(100, 70,20, 20, 30,35, 2, -50, -30, -20, -100, -50, -50, -2);


        for (Position position : Advisor.getAllFreeFields(board)) {

            pretendPut(board, position, putScorePoints, gameTree.getRoot(), playerIndex, 1);


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

    private void pretendPut(Board board, Position put, ScorePoints scorePoints, BoardPutMoveKillScoreSet parent, int playerIndex, int level){

        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
        boardPutMoveKillScoreSet1.setPut(put);
        boardPutMoveKillScoreSet1.setLevel(level);
        gameTree.addSet(gameTree.root, boardPutMoveKillScoreSet1);

        Board clonedBoard1 = (Board) board.clone();
        clonedBoard1.putStone(put, playerIndex);
        boardPutMoveKillScoreSet1.setBoard(clonedBoard1);

        System.out.println();
        System.out.println(boardPutMoveKillScoreSet1.getPut());
        System.out.println(boardPutMoveKillScoreSet1.getBoard());
        boardPutMoveKillScoreSet1.setScore(Advisor.getPutScore(clonedBoard1, put, scorePoints, playerIndex, true));
        gameTree.addSet(parent, boardPutMoveKillScoreSet1);

        if (boardPutMoveKillScoreSet1.getBoard().checkMorris(boardPutMoveKillScoreSet1.getPut())){
            for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,playerIndex)){
                BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
                boardPutMoveKillScoreSet2.setLevel(level+1);
                Board clonedBoard2 = (Board) clonedBoard1.clone();
                clonedBoard2.clearStone(killPosition);

                boardPutMoveKillScoreSet2.setBoard(clonedBoard2);
                boardPutMoveKillScoreSet2.setKill(killPosition);
                boardPutMoveKillScoreSet2.setScore(boardPutMoveKillScoreSet1.getScore() + Advisor.getKillScore(clonedBoard2, killPosition, scorePoints, playerIndex, true));
                gameTree.addSet(boardPutMoveKillScoreSet1, boardPutMoveKillScoreSet2);
            }
        }

    }


    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {

        ScorePoints scorePoints = new ScorePoints(300, 70,50, 50, 30,35, 2, -50, -30, -20, -100, -15, -20, -2);
        gameTree.clearTree();

        for (Move myMove1 : Advisor.getAllPossibleMoves(board, playerIndex)){

            // Eigener Zug 1
            LinkedList<BoardPutMoveKillScoreSet> list1 = pretendMove(board, myMove1, scorePoints, gameTree.getRoot(), playerIndex,1);

            gameTree.keepOnlyBestChildren(gameTree.getRoot(), 3);

            for (BoardPutMoveKillScoreSet set1 : list1){

                // Gegnerischer Zug
                for (Move enemysMove : Advisor.getAllPossibleMoves(set1.getBoard(), 1-playerIndex)){
                    LinkedList<BoardPutMoveKillScoreSet> list2 = pretendMove(set1.getBoard(), enemysMove, scorePoints, set1, 1-playerIndex, 3);

                    gameTree.keepOnlyWorstChildren(set1,3);

                    // Eigener Zug 2
                    for (BoardPutMoveKillScoreSet set2 : list2) {

                        for (Move myMove2 : Advisor.getAllPossibleMoves(set2.getBoard(), playerIndex)) {
                            pretendMove(set2.getBoard(), myMove2, scorePoints, set2, playerIndex, 5);
                        }
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Gewinnerpfad:");
        Stack<BoardPutMoveKillScoreSet> winningPath = gameTree.getPath(gameTree.getLeafWithBestScore());

        while (!winningPath.isEmpty()){
            BoardPutMoveKillScoreSet currentSet = winningPath.pop();
            System.out.println(currentSet);
        }

        System.out.println("Getätigter Zug: " + gameTree.getBestMove());
        System.out.println();
        return gameTree.getBestMove();

    }

    private LinkedList<BoardPutMoveKillScoreSet> pretendMove(Board board, Move move, ScorePoints scorePoints, BoardPutMoveKillScoreSet parent, int playerIndex, int level){

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
        boardPutMoveKillScoreSet1.setScore(Advisor.getMoveScore(clonedBoard1, move, scorePoints, playerIndex, false));
        gameTree.addSet(parent, boardPutMoveKillScoreSet1);


       if (clonedBoard1.checkMorris(move.getTo())){
           for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1, playerIndex)){
           BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
           boardPutMoveKillScoreSet2.setKill(killPosition);
           boardPutMoveKillScoreSet2.setLevel(level+1);

           Board clonedBoard2 = (Board) clonedBoard1.clone();
           clonedBoard2.clearStone(killPosition);
           boardPutMoveKillScoreSet2.setBoard(clonedBoard2);

           /*System.out.println();
           System.out.println(boardPutMoveKillScoreSet2.getMove());
           System.out.println(boardPutMoveKillScoreSet2.getBoard());*/
           boardPutMoveKillScoreSet2.setScore(boardPutMoveKillScoreSet1.getScore() + Advisor.getKillScore(clonedBoard2, killPosition, scorePoints, playerIndex, false));
           gameTree.addSet(boardPutMoveKillScoreSet1, boardPutMoveKillScoreSet2);
           list.add(boardPutMoveKillScoreSet2);}
       }
       else {
           list.add(boardPutMoveKillScoreSet1);
       }

        return list;
    }



    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {

        return gameTree.getBestKill();

    }


}
