package edu.andreasgut.view;

import edu.andreasgut.game.InvalidFieldException;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class FieldView extends AnchorPane {

    private ImageView imageView;
    private Image image;
    private ViewManager viewManager;
    private Image blackStoneImage, whiteStoneImage, currentStoneImage;
    private GridPane fieldGridPane;
    private CoordinatesInRepresentation[][] translationArray;

    private int player=0;
    private boolean phase1=true;

    public FieldView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("fieldview");
        initializeTranslationArray();
        imageView = new ImageView();
        image = new Image("edu/andreasgut/Images/Spielfeld.png", 600, 600, true, true);
        imageView.setImage(image);
        blackStoneImage = new Image("edu/andreasgut/Images/SpielsteinSchwarz.png",85,85,true,true);
        whiteStoneImage = new Image("edu/andreasgut/Images/SpielsteinWeiss.png",85,85,true,true);
        currentStoneImage = blackStoneImage;


        fieldGridPane = new GridPane();
        fieldGridPane.setPadding(new Insets(3));

        fieldGridPane.setGridLinesVisible(false);
        for (int row = 0; row < 7; row++){
            fieldGridPane.addRow(row);
        }
        for (int column = 0; column < 7; column++){
            fieldGridPane.addRow(column);
        }

        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 7; column++) {
                ImageView tempImageView = new ImageView(new Image("edu/andreasgut/Images/GreenTransparent.png"));
                fieldGridPane.add(tempImageView,row,column);}}

        this.getChildren().addAll(imageView,fieldGridPane);
    }

    public void avtivatePutMode() {

        updateViewMode();

        for (Node n : fieldGridPane.getChildren()){
        n.setOnMouseClicked(click ->{
            int ring = translationArray[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)].getRing();
            int field = translationArray[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)].getField();
            System.out.println("Feld in Repräsentationsarray: " + ring + "/" + field);
            System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));
            try {
                viewManager.getGame().getField().putStone(ring, field);
            } catch (InvalidFieldException e) {
                e.printStackTrace();
            }

            ((ImageView)n).setImage(currentStoneImage);

            viewManager.getGame().increaseRound();
            viewManager.getGame().updateCurrentPlayer();
            viewManager.getScoreView().updateRound(viewManager.getGame().getRound());

            try {
                viewManager.getGame().play();
            } catch (InvalidFieldException e) {
                e.printStackTrace();
            }

        });}
    }

    public void putComputerStone(int ring, int field){
        ((ImageView) fieldGridPane.getChildren().get(0)).setImage(currentStoneImage);
    }

    private void updateViewMode(){
        if(viewManager.getGame().getRound()<18){
            fieldGridPane.setOnMouseMoved(move ->{
                switch (viewManager.getGame().getCurrentPlayerIndex()){
                    case 0:
                        currentStoneImage = blackStoneImage;
                        if (phase1){
                            imageView.getScene().setCursor(new ImageCursor(blackStoneImage,
                                    blackStoneImage.getWidth()/2, blackStoneImage.getHeight()/2));
                        }
                        break;
                    case 1:
                        currentStoneImage = whiteStoneImage;
                        if (phase1){
                            imageView.getScene().setCursor(new ImageCursor(whiteStoneImage,
                                    whiteStoneImage.getWidth()/2, whiteStoneImage.getHeight()/2));
                        }
                        break;
                }});
            fieldGridPane.setOnMouseExited (action ->{
                imageView.getScene().setCursor(Cursor.DEFAULT);
            });
        }
    }

    private void initializeTranslationArray(){
        translationArray = new CoordinatesInRepresentation[7][7];
        translationArray[0][0] = new CoordinatesInRepresentation(0,0);
        translationArray[0][3] = new CoordinatesInRepresentation(0,1);
        translationArray[0][6] = new CoordinatesInRepresentation(0,2);
        translationArray[3][6] = new CoordinatesInRepresentation(0,3);
        translationArray[6][6] = new CoordinatesInRepresentation(0,4);
        translationArray[6][3] = new CoordinatesInRepresentation(0,5);
        translationArray[6][0] = new CoordinatesInRepresentation(0,6);
        translationArray[3][0] = new CoordinatesInRepresentation(0,7);
        translationArray[1][1] = new CoordinatesInRepresentation(1,0);
        translationArray[1][3] = new CoordinatesInRepresentation(1,1);
        translationArray[1][5] = new CoordinatesInRepresentation(1,2);
        translationArray[3][5] = new CoordinatesInRepresentation(1,3);
        translationArray[5][5] = new CoordinatesInRepresentation(1,4);
        translationArray[5][3] = new CoordinatesInRepresentation(1,5);
        translationArray[5][1] = new CoordinatesInRepresentation(1,6);
        translationArray[3][1] = new CoordinatesInRepresentation(1,7);
        translationArray[2][2] = new CoordinatesInRepresentation(2,0);
        translationArray[2][3] = new CoordinatesInRepresentation(2,1);
        translationArray[2][4] = new CoordinatesInRepresentation(2,2);
        translationArray[3][4] = new CoordinatesInRepresentation(2,3);
        translationArray[4][4] = new CoordinatesInRepresentation(2,4);
        translationArray[4][3] = new CoordinatesInRepresentation(2,5);
        translationArray[4][2] = new CoordinatesInRepresentation(2,6);
        translationArray[3][2] = new CoordinatesInRepresentation(2,7);
    }
}
