import edu.andreasgut.exercises.Tree;
import edu.andreasgut.exercises.TreeNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TreeTest {

    @Test
    public void testAddNode(){
        TreeNode root = new TreeNode(0);
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

        Assertions.assertEquals(10, root.getChildren().get(0).getScore());

    }

}
