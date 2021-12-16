package edu.andreasgut.game;

import java.util.*;


public class GameTree {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    private GameTreeNode root;


    public GameTree() {
        root = new GameTreeNode();
    }

    public void initializeRoot(Board board){
        root.setBoard(board);
        root.getChildren().clear();
        root.setVisited(false);
    }


    public LinkedList<GameTreeNode> getLeaves() {

        LinkedList<GameTreeNode> leaves = new LinkedList<>();
        getLeavesRecursive(root, leaves);
        return leaves;

    }

    private void getLeavesRecursive(GameTreeNode currentNode, LinkedList<GameTreeNode> leaves){
        if (currentNode.getChildren().isEmpty() && !leaves.contains(currentNode)) {
            leaves.add(currentNode);
        } else {
            for (GameTreeNode child : currentNode.getChildren()) {
                getLeavesRecursive(child, leaves);
            }
        }
    }

    private GameTreeNode getBestChild(GameTreeNode node){
        int max = Integer.MIN_VALUE;
        GameTreeNode bestChild = null;

        for (GameTreeNode child : node.getChildren()){
            if (child.getInheritedScore() > max){
                max = child.getInheritedScore();
                bestChild = child;
            }
        }
        return bestChild;
    }

    private GameTreeNode getWorstChild(GameTreeNode node){
        int min = Integer.MAX_VALUE;
        GameTreeNode worstChild = null;

        for (GameTreeNode child : node.getChildren()){
            if (child.getInheritedScore() < min){
                min = child.getInheritedScore();
                worstChild = child;
            }
        }

        return worstChild;
    }

    public void evaluateGameTree(){

        Queue<GameTreeNode> queue = new LinkedList<>();

        for (GameTreeNode node : getLeaves()){
            node.setInheritedScore(node.getScore());
            queue.add(node);
        }

        evaluateGameTreeRecursive(queue);


    }

    public void evaluateGameTreeRecursive(Queue<GameTreeNode> queue){

        GameTreeNode tempNode = queue.poll();

        if (tempNode.getParent() == null){
            return;
        }

        GameTreeNode parent = tempNode.getParent();

        if (!parent.isVisited() && tempNode.getLevel()%2==1){
            parent.setInheritedScore(getBestChild(parent).getInheritedScore());
        }
        if (!parent.isVisited() && tempNode.getLevel()%2==0){
            parent.setInheritedScore(getWorstChild(parent).getInheritedScore());
        }

        parent.setVisited(true);
        queue.add(parent);
        evaluateGameTreeRecursive(queue);

    }

    public Position getBestPut(){

        evaluateGameTree();

        LinkedList<GameTreeNode> bestList = new LinkedList<>();

        for (GameTreeNode node : root.getChildren()){
            if (node.getInheritedScore() == root.getInheritedScore()){
                bestList.add(node);
            }
        }


        Random random = new Random();
        return bestList.get(random.nextInt(bestList.size())).getPut();
    }



    public Move getBestMove(){

        evaluateGameTree();

        LinkedList<GameTreeNode> bestList = new LinkedList<>();

        for (GameTreeNode node : root.getChildren()){
            if (node.getInheritedScore() == root.getInheritedScore()){
                bestList.add(node);
            }
        }

        Random random = new Random();
        return bestList.get(random.nextInt(bestList.size())).getMove();
    }

    public Position getBestKill(){

        //evaluateGameTree();

        LinkedList<GameTreeNode> bestList = new LinkedList<>();

        for (GameTreeNode node : root.getChildren()){
            if (node.getInheritedScore() == root.getInheritedScore()){
                bestList.add(node);
            }
        }

        Random random = new Random();
        return bestList.get(random.nextInt(bestList.size())).getKill();
    }

    public void keepOnlyWorstChildren(GameTreeNode parent, int numberOfChildren){
        parent.getChildren().sort(new Comparator<GameTreeNode>() {
            @Override
            public int compare(GameTreeNode o1, GameTreeNode o2) {
                if (o1.getScore() > o2.getScore()){
                    return 1;
                }
                if (o1.getScore() == o2.getScore()){
                    return 0;
                }
                else return -1;
            }
        });

        Iterator<GameTreeNode> iterator = parent.getChildren().iterator();

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

    public void keepOnlyBestChildren(GameTreeNode parent, int numberOfChildren){
        parent.getChildren().sort(new Comparator<GameTreeNode>() {
            @Override
            public int compare(GameTreeNode o1, GameTreeNode o2) {
                if (o1.getScore() > o2.getScore()){
                    return -1;
                }
                if (o1.getScore() == o2.getScore()){
                    return 0;
                }
                else return 1;
            }
        });

        Iterator<GameTreeNode> iterator = parent.getChildren().iterator();

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



    public void addNode(GameTreeNode parent, GameTreeNode child){
        parent.getChildren().add(child);
        child.setParent(parent);
    }

    public GameTreeNode getRoot() {
        return root;
    }


    @Override
    public String toString(){
        String string = "Gametree: \n \n";

        //return toStringRecursive(root, string);

        int counter1 = 0;

        for (GameTreeNode currentNode : root.getChildren()) {
            int counter2 = 0;
            string += ANSI_GREEN + "Level: " + currentNode.getLevel() + ", Pfad: " + ++counter1 + " \n";
            if (currentNode.getPut() != null){
                if (currentNode.getKill() != null){
                    string += "Put an " + currentNode.getPut() + " mit Kill an " + currentNode.getKill() + "\n";
                }
                else {
                    string += "Put an " + currentNode.getPut() + "\n";
                }
            }
            if (currentNode.getMove() != null){
                if (currentNode.getKill() != null){
                    string += currentNode.getMove() + " mit Kill an " + currentNode.getKill() + "\n";
                }
                else {
                    string += currentNode.getMove() + "\n";
                }
            }
            string += currentNode.getBoard();
            string += currentNode.getScoreDetails() + "\n \n" + ANSI_RESET;
            for (GameTreeNode currentNode2 : currentNode.getChildren()){
                int counter3 = 0;
                string += ANSI_YELLOW + "Level: " + currentNode2.getLevel() + ", Pfad: " + counter1 + "." + ++counter2 + " \n";
                if (currentNode2.getPut() != null){
                    if (currentNode2.getKill() != null){
                        string += "Put an " + currentNode2.getPut() + " mit Kill an " + currentNode2.getKill() + "\n";
                    }
                    else {
                        string += "Put an " + currentNode2.getPut() + "\n";
                    }
                }
                if (currentNode2.getMove() != null){
                    if (currentNode2.getKill() != null){
                        string += currentNode2.getMove() + " mit Kill an " + currentNode2.getKill() + "\n";
                    }
                    else {
                        string += currentNode2.getMove() + "\n";
                    }
                }
                string += currentNode2.getBoard();
                string += currentNode2.getScoreDetails() + "\n \n" + ANSI_RESET;
                for (GameTreeNode currentNode3 : currentNode2.getChildren()){
                    int counter4 = 0;
                    string += ANSI_BLUE + "Level: " + currentNode3.getLevel() + ", Pfad: " + counter1 + "." + counter2 + "." + ++counter3 + "\n";
                    if (currentNode3.getPut() != null){
                        if (currentNode3.getKill() != null){
                            string += "Put an " + currentNode3.getPut() + " mit Kill an " + currentNode3.getKill() + "\n";
                        }
                        else {
                            string += "Put an " + currentNode3.getPut() + "\n";
                        }
                    }
                    if (currentNode3.getMove() != null){
                        if (currentNode3.getKill() != null){
                            string += currentNode3.getMove() + " mit Kill an " + currentNode3.getKill() + "\n";
                        }
                        else {
                            string += currentNode3.getMove() + "\n";
                        }
                    }
                    string += currentNode3.getBoard();
                    string += currentNode3.getScoreDetails() + "\n \n" + ANSI_RESET;
                    for (GameTreeNode currentNode4 : currentNode3.getChildren()){
                        string += ANSI_PURPLE + "Level: " + currentNode4.getLevel() + ", Pfad: " + counter1 + "." + counter2 + "." + counter3 + "." + ++counter4 +  "\n";
                        if (currentNode4.getPut() != null){
                            if (currentNode4.getKill() != null){
                                string += "Put an " + currentNode4.getPut() + " mit Kill an " + currentNode4.getKill() + "\n";
                            }
                            else {
                                string += "Put an " + currentNode4.getPut() + "\n";
                            }
                        }
                        if (currentNode.getMove() != null){
                            if (currentNode4.getKill() != null){
                                string += currentNode4.getMove() + " mit Kill an " + currentNode4.getKill() + "\n";
                            }
                            else {
                                string += currentNode4.getMove() + "\n";
                            }
                        }
                        string += currentNode4.getBoard();
                        string += currentNode4.getScoreDetails() + "\n \n"  + ANSI_RESET;

            }}}}
        return string;
    }

    private String toStringRecursive(GameTreeNode node, String string){

        for (GameTreeNode currentSet : node.getChildren()) {
            string += "Level: " + currentSet.getLevel() + "\n";
            string += currentSet.getBoard();
            string += "Resultierender Score: " + currentSet.getScore() + "\n \n";
            toStringRecursive(currentSet, string);
        }

        return string;


        }

}
