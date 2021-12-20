package edu.andreasgut.view;

import edu.andreasgut.game.Board;
import edu.andreasgut.game.Position;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.robot.Robot;

public class FieldViewPlay extends FieldView{

    private ImageCursor player1StoneCursor, player2StoneCursor, player2HandCursor, player1HandCursor,
            player2killCursor, player1killCursor;

    public FieldViewPlay(ViewManager viewManager, STONECOLOR player1Color, STONECOLOR player2Color, boolean activateBoardFunctions) {
        super(viewManager, player1Color, player2Color, activateBoardFunctions);
        setupFields(activateBoardFunctions);
        Board board = viewManager.getGame().getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getNumberOnPosition(i, j) != 9) {
                    Position tempPosition = new Position(i, j);
                    graphicPut(tempPosition, board.getNumberOnPosition(i, j), 0, false);
                }
            }
        }
    }

    private void setupFields(boolean activateBoardFunctions){

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


        if (activateBoardFunctions){

            for (Node n : fieldGridPane.getChildren()) {
                if (((ImageView) n).getImage().equals(emptyField)) {
                    n.setOnMouseClicked(click -> {
                        Position position = new Position(translateToRing(n), translateToField(n));

                        System.out.println("Feld in Repräsentationsarray: " + position.getRing() + "/" + position.getField());
                        System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));

                        viewManager.getGame().nextStep(position);
                    });
                }
            }
        }
    }

    private void setupPlayerPutCursors(STONECOLOR player1Color, STONECOLOR player2Color){
        player1StoneCursor = new ImageCursor(new Image(player1Color.getPathStone(), 85, 85, true, true),42,42);
        player2StoneCursor = new ImageCursor(new Image(player2Color.getPathStone(), 85, 85, true, true), 42,42);

    }

    private void setupPlayerMoveCursors(STONECOLOR player1Color, STONECOLOR player2Color){
        player1HandCursor = new ImageCursor(new Image(player1Color.getPathMoveCursor(), 85, 85, true, true));
        player2HandCursor = new ImageCursor(new Image(player2Color.getPathMoveCursor(), 85, 85, true, true));
    }

    private void setupPlayerKillCursors(STONECOLOR player1Color, STONECOLOR player2Color){
        player1killCursor = new ImageCursor(new Image(player1Color.getPathKillCursor(), 85, 85, true, true));
        player2killCursor = new ImageCursor(new Image(player2Color.getPathKillCursor(), 85, 85, true, true));
    }

    public void setPutCursor(){
        setupPlayerPutCursors(player1Color,player2Color);
        choosePutCursor();
        fieldGridPane.setOnMouseEntered(enter ->{
            choosePutCursor();

        });
        moveMouseposition(10,10);
    }

    private void moveMouseposition(int dx, int dy){
        Robot robot = new Robot();
        robot.mouseMove(robot.getMouseX()+dx, robot.getMouseY()+dy);
    }

    private void choosePutCursor() {
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                imageView.getScene().setCursor(player1StoneCursor);
                break;
            case 1:
                imageView.getScene().setCursor(player2StoneCursor);
                break;}
    }



    public void setKillCursor(){
        setupPlayerKillCursors(player1Color,player2Color);
        chooseKillCursor();
        fieldGridPane.setOnMouseEntered(enter ->{
            chooseKillCursor();
        });
    }

    private void chooseKillCursor() {
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                imageView.getScene().setCursor(player1killCursor);
                break;
            case 1:
                imageView.getScene().setCursor(player2killCursor);
                break;}
    }

    synchronized public void setMoveCursor(){
        setupPlayerMoveCursors(player1Color,player2Color);
        chooseMoveCursor();
        fieldGridPane.setOnMouseEntered(enter -> {
            chooseMoveCursor();
        });

    }

    synchronized private void chooseMoveCursor(){
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                imageView.getScene().setCursor(player1HandCursor);
                break;
            case 1:
                imageView.getScene().setCursor(player2HandCursor);
                break;}
    }

}
