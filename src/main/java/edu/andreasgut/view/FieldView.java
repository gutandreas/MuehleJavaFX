package edu.andreasgut.view;

import com.sun.javafx.scene.paint.GradientUtils;
import edu.andreasgut.game.Computer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class FieldView extends AnchorPane {

    private ImageView imageView;
    private Image image;
    private ViewManager viewManager;
    private Image blackStoneImage, whiteStoneImage, player1StoneImage, player2StoneImage, emptyField;
    private GridPane fieldGridPane;
    private CoordinatesInRepresentation[][] translationArrayGraphicToRepresentation;
    private int[][] translationArrayRepresentationToIndex;

    private int player=0;
    private boolean phase1=true;
    private boolean running;

    public FieldView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("fieldview");
        initializeTranslationArray();
        imageView = new ImageView();
        image = new Image("edu/andreasgut/Images/Spielfeld.png", 600, 600, true, true);
        imageView.setImage(image);
        blackStoneImage = new Image("edu/andreasgut/Images/SpielsteinSchwarz.png",85,85,true,true);
        whiteStoneImage = new Image("edu/andreasgut/Images/SpielsteinWeiss.png",85,85,true,true);
        player1StoneImage = blackStoneImage;
        player2StoneImage = whiteStoneImage;
        emptyField = new Image("edu/andreasgut/Images/FullyTransparent.png");


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
                ImageView tempImageView = new ImageView(emptyField);
                fieldGridPane.add(tempImageView,row,column);}}

        this.getChildren().addAll(imageView,fieldGridPane);
    }

    public CoordinatesInRepresentation humanGraphicPut() {
        Object loopObject = new Object();
        updateViewMode();
        final Integer[] ring = new Integer[1];
        final int[] field = new int[1];


        for (Node n : fieldGridPane.getChildren()){
            n.setOnMouseClicked(click ->{
                ring[0] = translateToRing(n);
                field[0] = translateToField(n);

                System.out.println("Feld in Repräsentationsarray: " + ring[0] + "/" + field[0]);
                System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));


                switch (viewManager.getGame().getCurrentPlayerIndex()){
                    case 0:
                        ((ImageView)n).setImage(player1StoneImage);
                        break;
                    case 1:
                        ((ImageView)n).setImage(player2StoneImage);
                        break;
                }

                Platform.exitNestedEventLoop(loopObject, null);

            });}

        Platform.enterNestedEventLoop(loopObject);
        return new CoordinatesInRepresentation(ring[0], field[0]);




    }

    public void humanGraphicKill(){
        for (Node n : fieldGridPane.getChildren()){
            n.setOnMouseClicked(click ->{
                int ring = translateToRing(n);
                int field = translateToField(n);

                System.out.println("Feld in Repräsentationsarray: " + ring + "/" + field);
                System.out.println("Feld in Spielfeld: " + GridPane.getRowIndex(n) + "/" + GridPane.getColumnIndex(n));

                ((ImageView) n).setImage(emptyField);
            });}
    }

    public void computerGraphicPut(int ring, int field){

        ((ImageView) fieldGridPane.getChildren().get(translateToIndex(ring, field))).setImage(player2StoneImage);
    }

    private void updateViewMode(){
        if(viewManager.getGame().getRound()<18 && !(viewManager.getGame().getCurrentPlayer() instanceof Computer)){
            fieldGridPane.setOnMouseMoved(move ->{
                switch (viewManager.getGame().getCurrentPlayerIndex()){
                    case 0:
                        player1StoneImage = blackStoneImage;
                        if (phase1){
                            imageView.getScene().setCursor(new ImageCursor(blackStoneImage,
                                    blackStoneImage.getWidth()/2, blackStoneImage.getHeight()/2));
                        }
                        break;
                    case 1:
                        player2StoneImage = whiteStoneImage;
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

    private int translateToRing(Node node){
        return translationArrayGraphicToRepresentation[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)].getRing();
    }

    private int translateToField(Node node){
        return translationArrayGraphicToRepresentation[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)].getField();
    }

    private int translateToIndex(int ring, int field){
        return translationArrayRepresentationToIndex[ring][field];
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
