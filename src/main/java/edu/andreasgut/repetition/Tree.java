package edu.andreasgut.repetition;
import java.util.LinkedList;

// Diese Klasse nicht verändern!
public class Tree {

    private TreeNode rootNode;

    public Tree(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public void addNode(TreeNode child, TreeNode parent){

        child.setParent(parent);
        parent.addChild(child);
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
