package edu.andreasgut.game;

import edu.andreasgut.communication.Messenger;
import edu.andreasgut.view.ViewManager;

public class StandardComputerPlayer extends ComputerPlayer{

    public StandardComputerPlayer(ViewManager viewManager, String name, ScorePoints putPoints, ScorePoints movePoints, int levelLimit) {
        super(viewManager, name, putPoints, movePoints, levelLimit);
    }

    public StandardComputerPlayer(ViewManager viewManager, String name, String uuid, ScorePoints putPoints, ScorePoints movePoints, int levelLimit) {
        super(viewManager, name, uuid, putPoints, movePoints, levelLimit);
    }

    @Override
    Position put(Board board, int playerIndex) {

        //gameTree.initializeRoot(board);
        gameTree = new GameTree(board);

        recursivePutBfs(gameTree.getRoot(), putPoints, movePoints, playerIndex, levelLimit);

        //System.out.println(gameTree);

        return gameTree.getBestPut();
    }

    private void recursivePutBfs(GameTreeNode node, ScorePoints putPoints, ScorePoints movePoints,
                                 int scorePlayerIndex, int levelLimit){

        if (node.getLevel()==levelLimit){
            return;
        }

        int tempCurrentPlayerIndex;

        if (node.getLevel()%2 == 0){
            tempCurrentPlayerIndex = scorePlayerIndex;
        }
        else {
            tempCurrentPlayerIndex = 1 - scorePlayerIndex;
        }

        for (Position freeField : Advisor.getFreePositions(node.getBoard())){
            pretendPut(node.getBoard(), freeField, putPoints, node, scorePlayerIndex, tempCurrentPlayerIndex, node.getLevel()+1);
        }

        if (node.getLevel()%2 == 0){
            gameTree.keepOnlyBestChildren(node, 20);}
        else {
            gameTree.keepOnlyWorstChildren(node, 20);
        }

        for (GameTreeNode child : node.getChildren()){
            if (viewManager.getGame().getRound() + child.getLevel() < 18){
                recursivePutBfs(child, putPoints, movePoints, scorePlayerIndex, levelLimit);
            }
            else {
                recursiveMoveBfs(child, movePoints, scorePlayerIndex, levelLimit);
            }

        }
    }


    private void pretendPut(Board board, Position put, ScorePoints scorePoints, GameTreeNode parent,
                            int scorePlayerIndex, int currentPlayerIndex, int level){

        GameTreeNode gameTreeNode1 = new GameTreeNode();
        gameTreeNode1.setPut(put);
        gameTreeNode1.setLevel(level);

        Board clonedBoard1 = board.clone();
        clonedBoard1.putStone(put, currentPlayerIndex);
        gameTreeNode1.setBoard(clonedBoard1);
        gameTreeNode1.setScore(Advisor.getScore(gameTreeNode1, scorePoints, scorePlayerIndex));

        if (gameTreeNode1.getBoard().isPositionPartOfMorris(gameTreeNode1.getPut())){
            for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,currentPlayerIndex)){
                GameTreeNode gameTreeNode2 = new GameTreeNode();
                gameTreeNode2.setPut(put);
                gameTreeNode2.setLevel(level);
                gameTreeNode2.setKill(killPosition);

                Board clonedBoard2 = clonedBoard1.clone();
                clonedBoard2.removeStone(killPosition);

                gameTreeNode2.setBoard(clonedBoard2);
                gameTreeNode2.setScore(Advisor.getScore(gameTreeNode2, scorePoints, scorePlayerIndex));
                gameTree.addNode(parent, gameTreeNode2);
            }
        }
        else {
            gameTree.addNode(parent, gameTreeNode1);
        }
    }

    @Override
    Move move(Board board, int playerIndex, boolean allowedToJump) {

        //gameTree.initializeRoot(board);
        gameTree = new GameTree(board);

        recursiveMoveBfs(gameTree.getRoot(), movePoints, playerIndex, levelLimit);

        //System.out.println(gameTree);

        return gameTree.getBestMove();

    }

    private void recursiveMoveBfs(GameTreeNode set, ScorePoints scorePoints, int scorePlayerIndex, int levelLimit){

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
            gameTree.keepOnlyBestChildren(set, 18);}
        else {
            gameTree.keepOnlyWorstChildren(set, 18);}


        for (GameTreeNode child : set.getChildren()){
            recursiveMoveBfs(child, scorePoints, scorePlayerIndex, levelLimit);
        }
    }


    private void pretendMove(Board board, Move move, ScorePoints scorePoints, GameTreeNode parent, int scorePlayerIndex, int currentPlayerIndex, int level){

        GameTreeNode gameTreeNode1 = new GameTreeNode();
        gameTreeNode1.setMove(move);
        gameTreeNode1.setLevel(level);

        Board clonedBoard1 = board.clone();
        clonedBoard1.moveStone(move.getFrom(), move.getTo(), currentPlayerIndex);

        gameTreeNode1.setBoard(clonedBoard1);
        gameTreeNode1.setScore(Advisor.getScore(gameTreeNode1, scorePoints, scorePlayerIndex));



        if (gameTreeNode1.getBoard().isPositionPartOfMorris(gameTreeNode1.getMove().getTo())){
            for (Position killPosition : Advisor.getAllPossibleKills(clonedBoard1,currentPlayerIndex)){
                GameTreeNode gameTreeNode2 = new GameTreeNode();
                gameTreeNode2.setMove(move);
                gameTreeNode2.setLevel(level);
                gameTreeNode2.setKill(killPosition);

                Board clonedBoard2 = clonedBoard1.clone();
                clonedBoard2.removeStone(killPosition);

                gameTreeNode2.setBoard(clonedBoard2);
                gameTreeNode2.setScore(Advisor.getScore(gameTreeNode2, scorePoints, scorePlayerIndex));
                gameTree.addNode(parent, gameTreeNode2);
            }
        }
        else {
            gameTree.addNode(parent, gameTreeNode1);
        }
    }


    @Override
    Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex) {
        return gameTree.getBestKill();
    }


    @Override
    public void prepareKill(ViewManager viewManager) {
        if (automaticTrigger){
            triggerKill(viewManager);
        }
        else {
            viewManager.getLogView().getNextComputerStepButton().setKill(viewManager);
        }
    }


    @Override
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
                viewManager.getLogView().getNextComputerStepButton().setPut(viewManager);
            }
            else {
                viewManager.getLogView().getNextComputerStepButton().setMove(viewManager);
            }
        }
    }

    @Override
    public void triggerPut(ViewManager viewManager){
        Game game = viewManager.getGame();
        Position putPosition = put(game.getBoard(), game.getCurrentPlayerIndex());
        Messenger.sendPutMessage(viewManager, putPosition);
    }

    @Override
    public void triggerMove(ViewManager viewManager){
        Game game = viewManager.getGame();
        boolean allowedToJump = game.getBoard().numberOfStonesOf(game.getCurrentPlayerIndex()) == 3;
        Move move = move(game.getBoard(), game.getCurrentPlayerIndex(), allowedToJump);
        Messenger.sendMoveMessage(viewManager, move);
    }

    @Override
    public void triggerKill(ViewManager viewManager){
        Game game = viewManager.getGame();
        Position killPosition = kill(game.getBoard(), game.getCurrentPlayerIndex(), game.getOtherPlayerIndex());
        Messenger.sendKillMessage(viewManager, killPosition);
    }
}
