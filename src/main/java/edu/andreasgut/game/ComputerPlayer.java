package edu.andreasgut.game;

import edu.andreasgut.view.ViewManager;

import java.util.*;

public class ComputerPlayer extends Player {

    GameTree gameTree = new GameTree();


    public ComputerPlayer(ViewManager viewManager, String name) {
        super(viewManager, name);
    }



    @Override
    Position put(Board board, int playerIndex) {

        gameTree.initializeRoot(board);

        ScorePoints putScorePoints = new ScorePoints(100, 5000,20, 20, 30,35, 2, -5000, -30, -200, -100, -50);

        recursivePutBfs(gameTree.getRoot(), putScorePoints, playerIndex, playerIndex, 3);



        //recursivePutDfs(gameTree.getRoot(), putScorePoints, playerIndex, playerIndex, 3);


        /*for (Position position : Advisor.getAllFreeFields(board)) {

            pretendPut(board, position, putScorePoints, gameTree.getRoot(), playerIndex, 1);


        }


       */

        System.out.println(gameTree);

        Stack<BoardPutMoveKillScoreSet> winningPath = gameTree.getPath(gameTree.getLeafWithBestScore());
        System.out.println("Gewinnerpfad:");
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }

        System.out.println("Gesetzter Stein: " + gameTree.getBestPut());
        return gameTree.getBestPut();
    }

    private void recursivePutBfs(BoardPutMoveKillScoreSet set, ScorePoints scorePoints, int scorePlayerIndex, int currentPlayerIndex, int levelLimit){

        if (set.getLevel()==levelLimit){
            return;
        }

        int tempCurrentPlayerIndex;

        if (set.getLevel()%2 == 0){
            tempCurrentPlayerIndex = scorePlayerIndex;
        }
        else {
            tempCurrentPlayerIndex = 1 - scorePlayerIndex;
        }

        for (Position freeField : Advisor.getAllFreeFields(set.getBoard())){
            pretendPut(set.getBoard(), freeField, scorePoints, set, scorePlayerIndex, tempCurrentPlayerIndex, set.getLevel()+1);
        }

        if (set.getLevel()%2 == 0){
            gameTree.keepOnlyBestChildren(set, 3);}
        else {
            gameTree.keepOnlyWorstChildren(set, 1);
        }


        for (BoardPutMoveKillScoreSet child : set.getChildren()){
            recursivePutBfs(child, scorePoints, scorePlayerIndex, tempCurrentPlayerIndex, levelLimit);
        }
    }

    private void recursivePutDfs(BoardPutMoveKillScoreSet set, ScorePoints scorePoints, int scorePlayerIndex, int currentPlayerIndex, int levelLimit){
        if (set.getLevel() == levelLimit){
            return;
        }

        int tempCurrentPlayerIndex;
        if (set.getLevel()%2 == 0){
            tempCurrentPlayerIndex = scorePlayerIndex;
        }
        else {
            tempCurrentPlayerIndex = 1 - scorePlayerIndex;
        }

        for (Position freeField : Advisor.getAllFreeFields(set.getBoard())){
            pretendPut(set.getBoard(), freeField, scorePoints, set, scorePlayerIndex, currentPlayerIndex, set.getLevel()+1);}

        for (BoardPutMoveKillScoreSet child : set.getChildren()){
            recursivePutDfs(child, scorePoints, scorePlayerIndex, currentPlayerIndex, levelLimit);
        }




    }

    private void pretendPut(Board board, Position put, ScorePoints scorePoints, BoardPutMoveKillScoreSet parent, int scorePlayerIndex, int currentPlayerIndex, int level){

        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
        boardPutMoveKillScoreSet1.setPut(put);
        boardPutMoveKillScoreSet1.setLevel(level);

        Board clonedBoard1 = (Board) board.clone();
        clonedBoard1.putStone(put, currentPlayerIndex);

        boardPutMoveKillScoreSet1.setScore(Advisor.getPutScore(clonedBoard1, put, scorePoints, scorePlayerIndex, false));
        boardPutMoveKillScoreSet1.setBoard(clonedBoard1);


        if (boardPutMoveKillScoreSet1.getBoard().checkMorris(boardPutMoveKillScoreSet1.getPut())){
            for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,currentPlayerIndex)){
                BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
                boardPutMoveKillScoreSet2.setLevel(level);
                Board clonedBoard2 = (Board) clonedBoard1.clone();
                clonedBoard2.clearStone(killPosition);

                boardPutMoveKillScoreSet2.setBoard(clonedBoard2);
                boardPutMoveKillScoreSet2.setKill(killPosition);
                boardPutMoveKillScoreSet2.setScore(boardPutMoveKillScoreSet1.getScore() + Advisor.getKillScore(clonedBoard2, killPosition, scorePoints, scorePlayerIndex, false));
                gameTree.addSet(parent, boardPutMoveKillScoreSet2);
            }
        }
        else {
            gameTree.addSet(parent, boardPutMoveKillScoreSet1);
            /*System.out.println();
            System.out.println(boardPutMoveKillScoreSet1.getPut());
            System.out.println(boardPutMoveKillScoreSet1.getBoard());*/
        }

    }


    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {

        ScorePoints scorePoints = new ScorePoints(300, 70,50, 50, 30,35, -200, -50, -30, -20, -100, -15);



        /*gameTree.clearTree();

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
        System.out.println();*/
        return gameTree.getBestMove();

    }


    private LinkedList<BoardPutMoveKillScoreSet> pretendMove(Board board, Move move, ScorePoints scorePoints, BoardPutMoveKillScoreSet parent, int playerIndex, int level){

        LinkedList<BoardPutMoveKillScoreSet> list = new LinkedList<>();
        BoardPutMoveKillScoreSet boardPutMoveKillScoreSet1 = new BoardPutMoveKillScoreSet();
        boardPutMoveKillScoreSet1.setMove(move);
        boardPutMoveKillScoreSet1.setLevel(level);

        Board clonedBoard1 = (Board) board.clone();


        /*System.out.println();
        System.out.println(boardPutMoveKillScoreSet1.getMove());
        System.out.println(boardPutMoveKillScoreSet1.getBoard());*/


       if (clonedBoard1.checkMorris(move.getTo())){
           for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1, playerIndex)){
           BoardPutMoveKillScoreSet boardPutMoveKillScoreSet2 = new BoardPutMoveKillScoreSet();
           boardPutMoveKillScoreSet2.setKill(killPosition);
           boardPutMoveKillScoreSet2.setLevel(level);

           Board clonedBoard2 = (Board) clonedBoard1.clone();
           clonedBoard2.clearStone(killPosition);
           boardPutMoveKillScoreSet2.setBoard(clonedBoard2);

           /*System.out.println();
           System.out.println(boardPutMoveKillScoreSet2.getMove());
           System.out.println(boardPutMoveKillScoreSet2.getBoard());*/
           boardPutMoveKillScoreSet2.setScore(Advisor.getMoveScore(clonedBoard2, move, scorePoints, playerIndex, false));
           gameTree.addSet(parent, boardPutMoveKillScoreSet2);
           list.add(boardPutMoveKillScoreSet2);}
       }
       else {
           list.add(boardPutMoveKillScoreSet1);
           boardPutMoveKillScoreSet1.setScore(Advisor.getMoveScore(clonedBoard1, move, scorePoints, playerIndex, false));
           clonedBoard1.move(move, playerIndex);
           boardPutMoveKillScoreSet1.setBoard(clonedBoard1);
           gameTree.addSet(parent, boardPutMoveKillScoreSet1);
       }

        return list;
    }



    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {

        return gameTree.getBestKill();

    }


}
