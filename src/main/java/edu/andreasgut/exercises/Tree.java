package edu.andreasgut.exercises;
import java.util.LinkedList;

public class Tree {

    private TreeNode rootNode;

    public Tree(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    // TODO: Implementiere die Methode so, dass ein Kindknoten an die passende Stelle im Baum eingefügt wird.
    //  Vergiss dabei nicht, dass nicht nur der Kind- mit dem Elternknoten verknüpft werden muss,
    //  sondern auch die umgekehrte Verknüpfung erstellt werden muss.
    public void addNode(TreeNode child, TreeNode parent){

    }

    // TODO: Implementiere die Methode so, dass ein Knoten an eine andere Stelle im Baum verschoben werden kann.
    public void moveNode(TreeNode child, TreeNode fromParent, TreeNode toParent){

    }

    // TODO: Implementiere die Methode so, dass sie die Anzahl Kindknoten, die direkt an einem Knoten hängen,
    //  zurückgibt.
    public int getNumberOfChildrenOf(TreeNode treeNode){

        int numberOfChildren = 0;

        return numberOfChildren;
    }

    // TODO: Implementiere die Methode so, dass sie die Anzahl Geschwisterknoten (Konten mit demselben Elternknoten)
    //  zurückgibt.
    public int getNumberOfSiblingsOf(TreeNode treeNode){

        int numberOfSiblings = 0;

        return numberOfSiblings;
    }

    // TODO (freiwillig): Implementiere die Methode so, dass sie zurückgibt, auf welcher Ebene im Baum sich ein Knoten
    //  befindet. Die Wurzel ist dabei die Ebene 1.
    public int getLevelOf(TreeNode treeNode){

        int level = 1;

        return level;
    }

    // Diese Methoden nicht verändern!
    public void printTree() {
        printTree(rootNode, "", true);
    }

    private void printTree(TreeNode treeNode, String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + treeNode.getScore());

        LinkedList<TreeNode> children = treeNode.getChildren();
        int size = children.size();

        for (int i = 0; i < size - 1; i++) {
            TreeNode child = children.get(i);
            printTree(child, prefix + (isTail ? "    " : "│   "), false);
        }

        if (size > 0) {
            TreeNode lastChild = children.get(size - 1);
            printTree(lastChild, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
