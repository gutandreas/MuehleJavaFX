import edu.andreasgut.exercises.NumberOfStones;
import edu.andreasgut.game.BoardImpl;
import edu.andreasgut.game.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class NumberOfStonesTest {

    @Test
    public void testNumberOfStones(){
        BoardImpl board = new BoardImpl();
        board.putStone(new Position(0,0), 0);
        board.putStone(new Position(2, 2), 0);
        board.putStone(new Position(2, 4), 0);
        board.putStone(new Position(2, 7), 0);
        board.putStone(new Position(0,1), 1);
        board.putStone(new Position(1, 2), 1);
        board.putStone(new Position(2, 6), 1);


        NumberOfStones numberOfStones = new NumberOfStones(board);

        Assertions.assertEquals(4, numberOfStones.numberOfStonesOf(0));
        Assertions.assertEquals(3, numberOfStones.numberOfStonesOf(1));
    }

}
