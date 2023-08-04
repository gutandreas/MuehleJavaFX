import edu.andreasgut.exercises.Tree;
import edu.andreasgut.exercises.TreeNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TreeTest {

    @Test
    public void testAddNode(){
        TreeNode root = new TreeNode(5);
        Tree tree = new Tree(root);

        TreeNode nodeA = new TreeNode(10);
        TreeNode nodeB = new TreeNode(89);
        TreeNode nodeC = new TreeNode(100);
        TreeNode nodeD = new TreeNode(34);

        tree.addNode(nodeA, root);
        tree.addNode(nodeB, root);
        tree.addNode(nodeC, nodeB);
        tree.addNode(nodeD, nodeB);

        tree.printTree();

        Assertions.assertEquals(5, root.getChildren().get(0).getParent().getScore());
        Assertions.assertEquals(34, root.getChildren().get(1).getChildren().get(1).getScore());
    }

    @Test
    public void testMoveNode(){
        TreeNode root = new TreeNode(5);
        Tree tree = new Tree(root);

        TreeNode nodeA = new TreeNode(10);
        TreeNode nodeB = new TreeNode(89);
        TreeNode nodeC = new TreeNode(100);
        TreeNode nodeD = new TreeNode(34);

        tree.addNode(nodeA, root);
        tree.addNode(nodeB, root);
        tree.addNode(nodeC, nodeB);
        tree.addNode(nodeD, nodeB);

        tree.moveNode(nodeD, nodeB, nodeA);

        tree.printTree();

        Assertions.assertEquals(nodeD, root.getChildren().get(0).getChildren().get(0));
        Assertions.assertEquals(1, root.getChildren().get(1).getChildren().size());
    }

    @Test
    public void testNumberOfChildrenOf(){
        TreeNode root = new TreeNode(5);
        Tree tree = new Tree(root);

        TreeNode nodeA = new TreeNode(10);
        TreeNode nodeB = new TreeNode(89);
        TreeNode nodeC = new TreeNode(100);
        TreeNode nodeD = new TreeNode(34);

        tree.addNode(nodeA, root);
        tree.addNode(nodeB, root);
        tree.addNode(nodeC, root);
        tree.addNode(nodeD, nodeC);


        tree.printTree();

        Assertions.assertEquals(3, tree.getNumberOfChildrenOf(root));
        Assertions.assertEquals(1, tree.getNumberOfChildrenOf(nodeC));
    }

    @Test
    public void testNumberOfSiblingsOf(){
        TreeNode root = new TreeNode(5);
        Tree tree = new Tree(root);

        TreeNode nodeA = new TreeNode(10);
        TreeNode nodeB = new TreeNode(89);
        TreeNode nodeC = new TreeNode(100);
        TreeNode nodeD = new TreeNode(34);

        tree.addNode(nodeA, root);
        tree.addNode(nodeB, root);
        tree.addNode(nodeC, root);
        tree.addNode(nodeD, nodeC);


        tree.printTree();

        Assertions.assertEquals(2, tree.getNumberOfSiblingsOf(nodeA));
        Assertions.assertEquals(0, tree.getNumberOfChildrenOf(nodeD));
    }

    @Test
    public void testGetLevelOf(){
        TreeNode root = new TreeNode(5);
        Tree tree = new Tree(root);

        TreeNode nodeA = new TreeNode(10);
        TreeNode nodeB = new TreeNode(89);
        TreeNode nodeC = new TreeNode(100);
        TreeNode nodeD = new TreeNode(34);

        tree.addNode(nodeA, root);
        tree.addNode(nodeB, root);
        tree.addNode(nodeC, root);
        tree.addNode(nodeD, nodeC);

        tree.printTree();

        Assertions.assertEquals(1, tree.getLevelOf(root));
        Assertions.assertEquals(2, tree.getLevelOf(nodeA));
        Assertions.assertEquals(3, tree.getLevelOf(nodeD));
    }

}
