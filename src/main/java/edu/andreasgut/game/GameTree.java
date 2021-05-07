package edu.andreasgut.game;

import java.util.LinkedList;

public class GameTree {

    BoardPutMoveKillScoreSet root = new BoardPutMoveKillScoreSet();

    public BoardPutMoveKillScoreSet getLeafWithBestScore() {
        int max = Integer.MIN_VALUE;
        BoardPutMoveKillScoreSet bestLeaf = null;

        for (BoardPutMoveKillScoreSet set : getLeaves()){
            if (set.getScore() > max){
                max = set.getScore();
                bestLeaf = set;
            }
        }

        return bestLeaf;
    }

    public BoardPutMoveKillScoreSet getKillLeafWithBestScore() {
        int max = Integer.MIN_VALUE;
        BoardPutMoveKillScoreSet bestLeaf = null;

        for (BoardPutMoveKillScoreSet set : getLeaves()){
            if (set.getKill() != null && set.getScore() > max){
                max = set.getScore();
                bestLeaf = set;
            }
        }

        return bestLeaf;
    }

    public LinkedList<BoardPutMoveKillScoreSet> getLeaves() {

        LinkedList<BoardPutMoveKillScoreSet> leaves = new LinkedList<>();

        getLeavesRecursive(root, leaves);

        return leaves;

    }



    public Move getBestMove(){
        BoardPutMoveKillScoreSet currentSet = getLeafWithBestScore();

        while (currentSet.getParent() != root){
            currentSet = currentSet.getParent();
        }


        return currentSet.getMove();

    }




    private void getLeavesRecursive(BoardPutMoveKillScoreSet currentSet, LinkedList<BoardPutMoveKillScoreSet> leaves){
        if (currentSet.getChildren().isEmpty()) {
            leaves.add(currentSet);
        } else {
            for (BoardPutMoveKillScoreSet child : currentSet.getChildren()) {
                getLeavesRecursive(child, leaves);
            }
        }
    }

    public void addSet(BoardPutMoveKillScoreSet parent, BoardPutMoveKillScoreSet child){
        parent.getChildren().add(child);
        child.setParent(parent);
    }

    public BoardPutMoveKillScoreSet getRoot() {
        return root;
    }

    public void clearTree(){
        root.getChildren().clear();
    }
}
