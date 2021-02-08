package edu.andreasgut.view;

import edu.andreasgut.game.Computer;
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
    private CoordinatesInRepresentation[][] translationArrayGraphicToRepresentation;
    private int[][] translationArrayRepresentationToIndex;

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

    public void humanGraphicPut() {

        updateViewMode();

        for (Node n : fieldGridPane.getChildren()){
        n.setOnMouseClicked(click ->{
            int ring = translationArrayGraphicToRepresentation[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)].getRing();
            int field = translationArrayGraphicToRepresentation[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)].getField();
            System.out.println("Feld in Repräsentationsarray: " + ring + "/" + field);
            System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));
            try {
                viewManager.getGame().getField().putStone(ring, field);
            } catch (InvalidFieldException e) {
                e.printStackTrace();
            }

            ((ImageView)n).setImage(currentStoneImage);

            viewManager.getGame().askForTriples();
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

    public void computerGraphicPut(int ring, int field){
           // fieldGridPane.getChildren().get()


        ((ImageView) fieldGridPane.getChildren().get(translationArrayRepresentationToIndex[ring][field])).setImage(currentStoneImage);
        viewManager.getGame().askForTriples();
        viewManager.getGame().increaseRound();
        viewManager.getGame().updateCurrentPlayer();
        viewManager.getScoreView().updateRound(viewManager.getGame().getRound());

        try {
            viewManager.getGame().play();
        } catch (InvalidFieldException e) {
            e.printStackTrace();
        }

    }

    private void updateViewMode(){
        if(viewManager.getGame().getRound()<18 && !(viewManager.getGame().getCurrentPlayer() instanceof Computer)){
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

        if(viewManager.getGame().getRound()<18 && (viewManager.getGame().getCurrentPlayer() instanceof Computer)){
            imageView.getScene().setCursor(Cursor.DEFAULT);
        }
    }

    private void initializeTranslationArray(){
        translationArrayGraphicToRepresentation = new CoordinatesInRepresentation[7][7];
        translationArrayGraphicToRepresentation[0][0] = new CoordinatesInRepresentation(0,0);
        translationArrayGraphicToRepresentation[0][3] = new CoordinatesInRepresentation(0,1);
        translationArrayGraphicToRepresentation[0][6] = new CoordinatesInRepresentation(0,2);
        translationArrayGraphicToRepresentation[3][6] = new CoordinatesInRepresentation(0,3);
        translationArrayGraphicToRepresentation[6][6] = new CoordinatesInRepresentation(0,4);
        translationArrayGraphicToRepresentation[6][3] = new CoordinatesInRepresentation(0,5);
        translationArrayGraphicToRepresentation[6][0] = new CoordinatesInRepresentation(0,6);
        translationArrayGraphicToRepresentation[3][0] = new CoordinatesInRepresentation(0,7);
        translationArrayGraphicToRepresentation[1][1] = new CoordinatesInRepresentation(1,0);
        translationArrayGraphicToRepresentation[1][3] = new CoordinatesInRepresentation(1,1);
        translationArrayGraphicToRepresentation[1][5] = new CoordinatesInRepresentation(1,2);
        translationArrayGraphicToRepresentation[3][5] = new CoordinatesInRepresentation(1,3);
        translationArrayGraphicToRepresentation[5][5] = new CoordinatesInRepresentation(1,4);
        translationArrayGraphicToRepresentation[5][3] = new CoordinatesInRepresentation(1,5);
        translationArrayGraphicToRepresentation[5][1] = new CoordinatesInRepresentation(1,6);
        translationArrayGraphicToRepresentation[3][1] = new CoordinatesInRepresentation(1,7);
        translationArrayGraphicToRepresentation[2][2] = new CoordinatesInRepresentation(2,0);
        translationArrayGraphicToRepresentation[2][3] = new CoordinatesInRepresentation(2,1);
        translationArrayGraphicToRepresentation[2][4] = new CoordinatesInRepresentation(2,2);
        translationArrayGraphicToRepresentation[3][4] = new CoordinatesInRepresentation(2,3);
        translationArrayGraphicToRepresentation[4][4] = new CoordinatesInRepresentation(2,4);
        translationArrayGraphicToRepresentation[4][3] = new CoordinatesInRepresentation(2,5);
        translationArrayGraphicToRepresentation[4][2] = new CoordinatesInRepresentation(2,6);
        translationArrayGraphicToRepresentation[3][2] = new CoordinatesInRepresentation(2,7);

        translationArrayRepresentationToIndex = new int[3][8];
        translationArrayRepresentationToIndex[0][0] = 0;
        translationArrayRepresentationToIndex[0][1] = 21;
        translationArrayRepresentationToIndex[0][2] = 42;
        translationArrayRepresentationToIndex[0][3] = 45;
        translationArrayRepresentationToIndex[0][4] = 48;
        translationArrayRepresentationToIndex[0][5] = 27;
        translationArrayRepresentationToIndex[0][6] = 6;
        translationArrayRepresentationToIndex[0][7] = 3;
        translationArrayRepresentationToIndex[1][0] = 8;
        translationArrayRepresentationToIndex[1][1] = 22;
        translationArrayRepresentationToIndex[1][2] = 36;
        translationArrayRepresentationToIndex[1][3] = 38;
        translationArrayRepresentationToIndex[1][4] = 40;
        translationArrayRepresentationToIndex[1][5] = 26;
        translationArrayRepresentationToIndex[1][6] = 12;
        translationArrayRepresentationToIndex[1][7] = 10;
        translationArrayRepresentationToIndex[2][0] = 16;
        translationArrayRepresentationToIndex[2][1] = 23;
        translationArrayRepresentationToIndex[2][2] = 30;
        translationArrayRepresentationToIndex[2][3] = 31;
        translationArrayRepresentationToIndex[2][4] = 32;
        translationArrayRepresentationToIndex[2][5] = 25;
        translationArrayRepresentationToIndex[2][6] = 18;
        translationArrayRepresentationToIndex[2][7] = 17;
    }
}
