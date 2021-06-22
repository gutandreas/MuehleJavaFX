package edu.andreasgut.game;

import java.util.*;


public class GameTree {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private BoardPutMoveKillScoreSet root;


    public GameTree() {
        root = new BoardPutMoveKillScoreSet();
    }

    public void initializeRoot(Board board){
        root.setBoard(board);
        root.getChildren().clear();
    }

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

        return currentSet.getKill();
    }

    public void keepOnlyWorstChildren(BoardPutMoveKillScoreSet parent, int numberOfChildren){
        parent.getChildren().sort(new Comparator<BoardPutMoveKillScoreSet>() {
            @Override
            public int compare(BoardPutMoveKillScoreSet o1, BoardPutMoveKillScoreSet o2) {
                if (o1.getScore() > o2.getScore()){
                    return 1;
                }
                if (o1.getScore() == o2.getScore()){
                    return 0;
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
                if (o1.getScore() == o2.getScore()){
                    return 0;
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

    @Override
    public String toString(){
        String string = "Gametree: \n \n";

        //return toStringRecursive(root, string);

        for (BoardPutMoveKillScoreSet currentSet : root.getChildren()) {
            string += ANSI_GREEN + "Level: " + currentSet.getLevel() + "\n" ;
            string += currentSet.getBoard();
            string += currentSet.getScoreDetails() + "\n \n" + ANSI_RESET;
            for (BoardPutMoveKillScoreSet currentSet2 : currentSet.getChildren()){
                string += ANSI_YELLOW + "Level: " + currentSet2.getLevel() + "\n" ;
                string += currentSet2.getBoard();
                string += currentSet2.getScoreDetails() + "\n \n" + ANSI_RESET;
                for (BoardPutMoveKillScoreSet currentSet3 : currentSet2.getChildren()){
                    string += ANSI_BLUE + "Level: " + currentSet3.getLevel() + "\n" ;
                    string += currentSet3.getBoard();
                    string += currentSet3.getScoreDetails() + "\n \n" + ANSI_RESET;
                    for (BoardPutMoveKillScoreSet currentSet4 : currentSet3.getChildren()){
                        string += ANSI_PURPLE + "Level: " + currentSet4.getLevel() + "\n";
                        string += currentSet4.getBoard();
                        string += currentSet4.getScoreDetails() + "\n \n"  + ANSI_RESET;

            }}}}
        return string;
    }

    private String toStringRecursive(BoardPutMoveKillScoreSet set, String string){

        for (BoardPutMoveKillScoreSet currentSet : set.getChildren()) {
            string += "Level: " + currentSet.getLevel() + "\n";
            string += currentSet.getBoard();
            string += "Resultierender Score: " + currentSet.getScore() + "\n \n";
            toStringRecursive(currentSet, string);
        }

        return string;


        }

}
