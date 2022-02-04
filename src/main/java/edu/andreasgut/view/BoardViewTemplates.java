package edu.andreasgut.view;

import edu.andreasgut.game.BoardImpl;
import edu.andreasgut.game.Board;
import edu.andreasgut.game.Position;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class BoardViewTemplates extends BoardView {

    Board board;

    public BoardViewTemplates(ViewManager viewManager, STONECOLOR player1Color, STONECOLOR player2Color) {
        super(viewManager, player1Color, player2Color, true);
        setupBoardPositions();
        this.board = new BoardImpl();
    }


    private void setupBoardPositions(){

        for (Node n : fieldGridPane.getChildren()) {
            if (((ImageView) n).getImage().equals(emptyField)) {
                n.setOnMouseClicked(click -> {
                    Position position = new Position(translateToPositionRing(n), translateToPositionField(n));

                    System.out.println("Feld in Repräsentationsarray: " + position);
                    System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));

                    switch (board.getNumberOnPosition(position)){
                        case 9:
                            board.putStone(position, 0);
                            graphicPut(position, 0, false);
                            break;
                        case 0:
                            board.putStone(position, 1);
                            graphicPut(position, 1, false);
                            break;
                        case 1:
                            board.removeStone(position);
                            graphicKill(position, false);
                    }


                });
            }
        }
    }

	public void updateStoneColors() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				Position p = new Position(i, j);
				int index = board.getNumberOnPosition(p);
				if (index == 0 || index == 1) {
					graphicPut(p, index, false);
				}
			}
		}
	}

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
