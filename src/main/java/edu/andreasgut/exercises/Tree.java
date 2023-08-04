package edu.andreasgut.exercises;
import java.util.LinkedList;

public class Tree {

    private TreeNode rootNode;

    public Tree(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public void addNode(TreeNode child, TreeNode parent){

    }

    public void moveNode(TreeNode fromParent, TreeNode toParent){

    }

    public int getNumberOfChildrenOf(TreeNode treeNode){

        int numberOfChildren = 0;

        return numberOfChildren;
    }

    public int getNumberOfSiblingsOf(TreeNode treeNode){
        int numberOfSiblings = 0;

        return numberOfSiblings;
    }

    public int getLevelOf(TreeNode treeNode){

        int level = 0;

        return level;
    }

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
