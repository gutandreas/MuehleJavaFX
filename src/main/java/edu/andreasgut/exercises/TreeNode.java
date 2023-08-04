package edu.andreasgut.exercises;

import java.util.LinkedList;

public class TreeNode {
    private int score;
    private TreeNode parent;
    private LinkedList<TreeNode> children;

    public TreeNode(int score) {
        this.score = score;
        this.children = new LinkedList<>();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public LinkedList<TreeNode> getChildren() {
        return children;
    }

    public void addChild(TreeNode child) {
        child.setParent(this);
        children.add(child);
    }

    public void removeChild(TreeNode child) {
        children.remove(child);
    }
}