import edu.andreasgut.repetition.Tree;
import edu.andreasgut.repetition.TreeNode;
import edu.andreasgut.game.BoardImpl;
import edu.andreasgut.game.Position;
import edu.andreasgut.repetition.Repetition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class RepetitionTest {

    @Test
    public void testConvertArrayToLinkedList(){

        Position[] positions = new Position[4];
        positions[0] = new Position(0, 0);
        positions[1] = new Position(1, 3);
        positions[2] = new Position(2, 2);
        positions[3] = new Position(2, 7);

        Assertions.assertEquals(0, Repetition.convertArrayToLinkedList(positions).getFirst().getRing());
        Assertions.assertEquals(7, Repetition.convertArrayToLinkedList(positions).getLast().getField());
    }

    @Test
    public void testReverseListOrder(){

        LinkedList<Position> linkedList = new LinkedList<>();
        linkedList.add(new Position(0, 0));
        linkedList.add(new Position(1, 3));
        linkedList.add(new Position(2, 6));

        LinkedList<Position> reversedList = Repetition.reverseListOrder(linkedList);

        Assertions.assertEquals(2, reversedList.getFirst().getRing());
        Assertions.assertEquals(0, reversedList.getLast().getRing());
    }

    @Test
    public void testMirrorPositionAtTheCenter(){
        Position position1 = new Position(1, 3);
        Position position2 = new Position(1, 6);
        Position position3 = new Position(2, 4);

        Assertions.assertEquals(1, Repetition.mirrorPositionAtTheCenter(position1).getRing());
        Assertions.assertEquals(1, Repetition.mirrorPositionAtTheCenter(position2).getRing());
        Assertions.assertEquals(2, Repetition.mirrorPositionAtTheCenter(position3).getRing());

        Assertions.assertEquals(7, Repetition.mirrorPositionAtTheCenter(position1).getField());
        Assertions.assertEquals(2, Repetition.mirrorPositionAtTheCenter(position2).getField());
        Assertions.assertEquals(0, Repetition.mirrorPositionAtTheCenter(position3).getField());
    }

    @Test
    public void testGetAllPositionsOfPlayerWithIndex() {

        BoardImpl board = new BoardImpl();
        board.putStone(new Position(0, 0), 0);
        board.putStone(new Position(2, 2), 0);
        board.putStone(new Position(2, 4), 0);
        board.putStone(new Position(2, 7), 0);
        board.putStone(new Position(0, 1), 1);
        board.putStone(new Position(1, 2), 1);
        board.putStone(new Position(2, 6), 1);

        Assertions.assertEquals(4, Repetition.getAllPositionsOfPlayerWithIndex(0, board).size());
        Assertions.assertEquals(2, Repetition.getAllPositionsOfPlayerWithIndex(0, board).getLast().getRing());

    }

    @Test
    public void testSwapTwoNodes() {

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

        Repetition.swapTwoNodes(nodeA, nodeB);

        Assertions.assertEquals(0, nodeB.getChildren().size());
        Assertions.assertEquals(2, nodeA.getChildren().size());



    }

}
