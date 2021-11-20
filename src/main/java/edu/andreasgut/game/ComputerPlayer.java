package edu.andreasgut.game;

import edu.andreasgut.online.Messenger;
import edu.andreasgut.view.ViewManager;


import java.util.*;

public class ComputerPlayer extends Player {

    GameTree gameTree = new GameTree();
    boolean automaticTrigger;
    ScorePoints putPoints;
    ScorePoints movePoints;
    int levelLimit;


    public ComputerPlayer(ViewManager viewManager, String name, ScorePoints putPoints, ScorePoints movePoints, int levelLimit) {
        super(viewManager, name, true);
        automaticTrigger = true;
        this.putPoints = putPoints;
        this.movePoints = movePoints;
        this.levelLimit = levelLimit;
    }

    public ComputerPlayer(ViewManager viewManager, String name, String uuid) {
        super(viewManager, name, uuid);
        automaticTrigger = false;
    }


    Position put(Board board, int playerIndex) {

        gameTree.initializeRoot(board);

        //ScorePoints putScorePoints = new ScorePoints(4000, 1000,20, 200, 300,3, -2000, -1000, -30, -200, -100, -2);

        recursivePutBfs(gameTree.getRoot(), putPoints, playerIndex, playerIndex, levelLimit);

        System.out.println(gameTree);

        Stack<GameTreeNode> winningPath = gameTree.getPathToBestLeaf();
        System.out.println("Gewinnerpfad:");
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }

        System.out.println("Gesetzter Stein: " + gameTree.getBestPut());
        return gameTree.getBestPut();
    }

    private void recursivePutBfs(GameTreeNode set, ScorePoints scorePoints, int scorePlayerIndex, int currentPlayerIndex, int levelLimit){

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
            gameTree.keepOnlyBestChildren(set, 10);}
        else {
            gameTree.keepOnlyWorstChildren(set, 1);
        }


        for (GameTreeNode child : set.getChildren()){
            recursivePutBfs(child, scorePoints, scorePlayerIndex, tempCurrentPlayerIndex, levelLimit);
        }
    }


    private void pretendPut(Board board, Position put, ScorePoints scorePoints, GameTreeNode parent, int scorePlayerIndex, int currentPlayerIndex, int level){

        GameTreeNode gameTreeNode1 = new GameTreeNode();
        gameTreeNode1.setPut(put);
        gameTreeNode1.setLevel(level);

        Board clonedBoard1 = (Board) board.clone();
        clonedBoard1.putStone(put, currentPlayerIndex);

        gameTreeNode1.setBoard(clonedBoard1);
        gameTreeNode1.setScore(Advisor.getScore(gameTreeNode1, scorePoints, scorePlayerIndex, false));



        if (gameTreeNode1.getBoard().checkMorris(gameTreeNode1.getPut())){
            for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,currentPlayerIndex)){
                GameTreeNode gameTreeNode2 = new GameTreeNode();
                gameTreeNode2.setPut(put);
                gameTreeNode2.setLevel(level);
                gameTreeNode2.setKill(killPosition);

                Board clonedBoard2 = (Board) clonedBoard1.clone();
                clonedBoard2.clearStone(killPosition);

                gameTreeNode2.setBoard(clonedBoard2);
                gameTreeNode2.setScore(Advisor.getScore(gameTreeNode2, scorePoints, scorePlayerIndex, false));
                gameTree.addSet(parent, gameTreeNode2);
            }
        }
        else {
            gameTree.addSet(parent, gameTreeNode1);
        }

    }


    Move move(Board board, int playerIndex, boolean allowedToJump) {


        gameTree.initializeRoot(board);

        //ScorePoints moveScorePoints = new ScorePoints(1000, 300,300, 200, 30,3, -1000, -280, -300, -300, -300, -2);

        recursiveMoveBfs(gameTree.getRoot(), movePoints, playerIndex, playerIndex, levelLimit);

        //System.out.println(gameTree);

        Stack<GameTreeNode> winningPath = gameTree.getPathToBestLeaf();
        System.out.println("Gewinnerpfad:");
        while (!winningPath.isEmpty()){
            System.out.println(winningPath.pop());
        }

        System.out.println("Getätigter Zug: " + gameTree.getBestMove());
        return gameTree.getBestMove();

    }

    private void recursiveMoveBfs(GameTreeNode set, ScorePoints scorePoints, int scorePlayerIndex, int currentPlayerIndex, int levelLimit){

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

        for (Move move : Advisor.getAllPossibleMoves(set.getBoard(), tempCurrentPlayerIndex)){
            pretendMove(set.getBoard(), move, scorePoints, set, scorePlayerIndex, tempCurrentPlayerIndex, set.getLevel()+1);
        }

        if (set.getLevel()%2 == 0){
            gameTree.keepOnlyBestChildren(set, 3);}
        else {
            gameTree.keepOnlyWorstChildren(set, 1);
        }


        for (GameTreeNode child : set.getChildren()){
            recursiveMoveBfs(child, scorePoints, scorePlayerIndex, tempCurrentPlayerIndex, levelLimit);
        }
    }


    private void pretendMove(Board board, Move move, ScorePoints scorePoints, GameTreeNode parent, int scorePlayerIndex, int currentPlayerIndex, int level){

        GameTreeNode gameTreeNode1 = new GameTreeNode();
        gameTreeNode1.setMove(move);
        gameTreeNode1.setLevel(level);

        Board clonedBoard1 = (Board) board.clone();
        clonedBoard1.move(move, currentPlayerIndex);

        gameTreeNode1.setBoard(clonedBoard1);
        gameTreeNode1.setScore(Advisor.getScore(gameTreeNode1, scorePoints, scorePlayerIndex, false));



        if (gameTreeNode1.getBoard().checkMorris(gameTreeNode1.getMove().getTo())){
            for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,currentPlayerIndex)){
                GameTreeNode gameTreeNode2 = new GameTreeNode();
                gameTreeNode2.setMove(move);
                gameTreeNode2.setLevel(level);
                gameTreeNode2.setKill(killPosition);

                Board clonedBoard2 = (Board) clonedBoard1.clone();
                clonedBoard2.clearStone(killPosition);

                gameTreeNode2.setBoard(clonedBoard2);
                gameTreeNode2.setScore(Advisor.getScore(gameTreeNode2, scorePoints, scorePlayerIndex, false));
                gameTree.addSet(parent, gameTreeNode2);
            }
        }
        else {
            gameTree.addSet(parent, gameTreeNode1);
        }
    }



    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {

        return gameTree.getBestKill();

    }


    public void prepareKill(ViewManager viewManager) {
        if (automaticTrigger){
            triggerKill(viewManager);
        }
        else {
            viewManager.getLogView().getNextComputerStepButton().setKill();
        }
    }


    public void preparePutOrMove(ViewManager viewManager) {

        Game game = viewManager.getGame();

        if (automaticTrigger){
            if (game.isPutPhase()){
                triggerPut(viewManager);
            }
            else {
                triggerMove(viewManager);
            }
        }
        else {
            if (viewManager.getGame().isPutPhase()){
                viewManager.getLogView().getNextComputerStepButton().setPut();
            }
            else {
                viewManager.getLogView().getNextComputerStepButton().setMove();
            }
        }
    }

    public void triggerPut(ViewManager viewManager){
        Game game = viewManager.getGame();
        Position putPosition = put(game.getBoard(), game.getCurrentPlayerIndex());
        Messenger.sendPutMessage(viewManager, putPosition);
    }

    public void triggerMove(ViewManager viewManager){
        Game game = viewManager.getGame();
        boolean allowedToJump = game.getBoard().countPlayersStones(game.getCurrentPlayerIndex()) == 3;
        Move move = move(game.getBoard(), game.getCurrentPlayerIndex(), allowedToJump);
        Messenger.sendMoveMessage(viewManager, move);
    }

    public void triggerKill(ViewManager viewManager){
        Game game = viewManager.getGame();
        Position killPosition = kill(game.getBoard(), game.getCurrentPlayerIndex(), game.getOtherPlayerIndex());
        Messenger.sendKillMessage(viewManager, killPosition);
    }




}
