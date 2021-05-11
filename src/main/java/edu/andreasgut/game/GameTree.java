package edu.andreasgut.game;

import java.util.*;

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


    public LinkedList<BoardPutMoveKillScoreSet> getLeaves() {

        LinkedList<BoardPutMoveKillScoreSet> leaves = new LinkedList<>();

        getLeavesRecursive(root, leaves);

        return leaves;

    }

    private void getLeavesRecursive(BoardPutMoveKillScoreSet currentSet, LinkedList<BoardPutMoveKillScoreSet> leaves){
        if (currentSet.getChildren().isEmpty() && !leaves.contains(currentSet)) {
            leaves.add(currentSet);
        } else {
            for (BoardPutMoveKillScoreSet child : currentSet.getChildren()) {
                getLeavesRecursive(child, leaves);
            }
        }
    }

    public Position getBestPut(){
        BoardPutMoveKillScoreSet currentSet = getLeafWithBestScore();

        while (currentSet.getParent() != root){
            currentSet = currentSet.getParent();
        }


        return currentSet.getPut();

    }

    public Move getBestMove(){
        BoardPutMoveKillScoreSet currentSet = getLeafWithBestScore();

        while (currentSet.getParent() != root){
            currentSet = currentSet.getParent();
        }

        return currentSet.getMove();
    }

    public Position getBestKill(){
        BoardPutMoveKillScoreSet currentSet = getLeafWithBestScore();

        while (currentSet.getParent() != root){
            currentSet = currentSet.getParent();
        }

        int max = Integer.MIN_VALUE;
        BoardPutMoveKillScoreSet currentKill = new BoardPutMoveKillScoreSet();

        for (BoardPutMoveKillScoreSet set : currentSet.getChildren()){
            if (set.getScore() > max){
                max = set.getScore();
                currentKill = set;
            }
        }
        return currentKill.getKill();
    }

    public void keepOnlyWorstChildren(BoardPutMoveKillScoreSet parent, int numberOfChildren){
        parent.getChildren().sort(new Comparator<BoardPutMoveKillScoreSet>() {
            @Override
            public int compare(BoardPutMoveKillScoreSet o1, BoardPutMoveKillScoreSet o2) {
                if (o1.getScore() > o2.getScore()){
                    return 1;
                }
                else return -1;
            }
        });

        Iterator<BoardPutMoveKillScoreSet> iterator = parent.getChildren().iterator();

        if (parent.getChildren().size() > numberOfChildren) {
            for (int i = 0; i < numberOfChildren; i++) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }

    }

    public void keepOnlyBestChildren(BoardPutMoveKillScoreSet parent, int numberOfChildren){
        parent.getChildren().sort(new Comparator<BoardPutMoveKillScoreSet>() {
            @Override
            public int compare(BoardPutMoveKillScoreSet o1, BoardPutMoveKillScoreSet o2) {
                if (o1.getScore() > o2.getScore()){
                    return -1;
                }
                else return 1;
            }
        });

        Iterator<BoardPutMoveKillScoreSet> iterator = parent.getChildren().iterator();

        if (parent.getChildren().size() > numberOfChildren) {
            for (int i = 0; i < numberOfChildren; i++) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
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

    public Stack<BoardPutMoveKillScoreSet> getPath(BoardPutMoveKillScoreSet node){
        Stack<BoardPutMoveKillScoreSet> path = new Stack<>();
        BoardPutMoveKillScoreSet set = getLeafWithBestScore();

        while (set!=root){
            path.push(set);
            set = set.getParent();}

        return path;
    }

}
