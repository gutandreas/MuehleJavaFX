package edu.andreasgut.view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class FieldView extends AnchorPane {

    private ImageView imageView;
    private Image image;
    private ViewManager viewManager;
    private Image cursorImageBlack, cursorImageWhite;
    private GridPane fieldGridPane;

    private int player=0;
    private boolean phase1=true;

    public FieldView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("fieldview");
        imageView = new ImageView();
        image = new Image("edu/andreasgut/Images/Spielfeld.png", 600, 600, true, true);
        cursorImageBlack = new Image("edu/andreasgut/Images/SpielsteinSchwarz.png",85,85,true,true);
        cursorImageWhite = new Image("edu/andreasgut/Images/SpielsteinWeiss.png",85,85,true,true);
        imageView.setImage(image);


        fieldGridPane = new GridPane();
        fieldGridPane.setPadding(new Insets(3));
        fieldGridPane.setOnMouseEntered(action ->{

            if (player==0 && phase1){
                imageView.getScene().setCursor(new ImageCursor(cursorImageBlack,
                        cursorImageBlack.getWidth()/2, cursorImageBlack.getHeight()/2));
            }
            if (player==1 && phase1){imageView.getScene().setCursor(new ImageCursor(cursorImageWhite,
                    cursorImageWhite.getWidth()/2, cursorImageWhite.getHeight()/2));}});
        fieldGridPane.setOnMouseExited (action ->{
            imageView.getScene().setCursor(Cursor.DEFAULT);
        });
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
                fieldGridPane.add(tempImageView,row,column);
                tempImageView.setOnMouseClicked(click ->{
                    System.out.println(GridPane.getRowIndex(tempImageView) + " " + GridPane.getColumnIndex(tempImageView));
                    tempImageView.setImage(new Image("edu/andreasgut/Images/SpielsteinSchwarz.png", 85, 85, true, true));
                });

            }
        }

        this.getChildren().addAll(imageView,fieldGridPane);
    }


}
