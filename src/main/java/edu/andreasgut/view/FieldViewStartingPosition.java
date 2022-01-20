package edu.andreasgut.view;

import edu.andreasgut.game.BoardImpl;
import edu.andreasgut.game.Board;
import edu.andreasgut.game.Position;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class FieldViewStartingPosition extends FieldView {

    Board board;

    public FieldViewStartingPosition(ViewManager viewManager, STONECOLOR player1Color, STONECOLOR player2Color) {
        super(viewManager, player1Color, player2Color, true);
        setupFields();
        this.board = new BoardImpl();
    }

    private void setupFields(){

        fieldGridPane.setGridLinesVisible(false);

        for (int row = 0; row < 7; row++) {
            fieldGridPane.addRow(row);
        }
        for (int column = 0; column < 7; column++) {
            fieldGridPane.addRow(column);
        }

        //Füllt Felder mit emptyField wenn in Repräsentation vorhanden, sonst mit forbiddenField
        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 7; column++) {
                if (translationArrayGraphicToRepresentation[row][column].getRing() != -1) {
                    ImageView tempImageView = new ImageView(emptyField);
                    fieldGridPane.add(tempImageView, row, column);
                } else {
                    ImageView tempImageView = new ImageView(forbiddenField);
                    fieldGridPane.add(tempImageView, row, column);
                }
            }
        }


        for (Node n : fieldGridPane.getChildren()) {
            if (((ImageView) n).getImage().equals(emptyField)) {
                n.setOnMouseClicked(click -> {
                    Position position = new Position(translateToRing(n), translateToField(n));

                    System.out.println("Feld in Repräsentationsarray: " + position);
                    System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));

                    switch (board.getNumberOnPosition(position.getRing(), position.getField())){
                        case 9:
                            board.putStone(position, 0);
                            graphicPut(position, 0, 0, false);
                            break;
                        case 0:
                            board.putStone(position, 1);
                            graphicPut(position, 1, 0, false);
                            break;
                        case 1:
                            board.removeStone(position);
                            graphicKill(position, false);
                    }


                });
            }
        }
    }

    public void updateStoneColors(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 8; j++){
                int index = board.getNumberOnPosition(i,j);
                if (index == 0 || index == 1){
                    graphicPut(new Position(i,j), index,0,false);}
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
