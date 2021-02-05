package edu.andreasgut.view;

import com.sun.javafx.cursor.CursorType;
import javafx.css.converter.CursorConverter;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class FieldView extends AnchorPane {

    private ImageView imageView;
    private Image image;
    private final int FIELDDIMENSION = 600;
    private ViewManager viewManager;
    private Image cursorImageBlack, cursorImageWhite;

    private int player=1;
    private boolean phase1=false;

    public FieldView(ViewManager viewManager) {
        this.viewManager = viewManager;
        imageView = new ImageView();
        image = new Image("edu/andreasgut/Images/Spielfeld.png");
        cursorImageBlack = new Image("edu/andreasgut/Images/SpielsteinSchwarz.png",100,100,true,true);
        cursorImageWhite = new Image("edu/andreasgut/Images/SpielsteinWeiss.png",120,120,true,true);
        imageView.setFitHeight(FIELDDIMENSION);
        imageView.setFitWidth(FIELDDIMENSION);
        imageView.setImage(image);
        this.getChildren().add(imageView);
        imageView.setOnMouseEntered(action ->{

            if (player==0 && phase1){
            imageView.getScene().setCursor(new ImageCursor(cursorImageBlack,
                    cursorImageBlack.getWidth()/2, cursorImageBlack.getHeight()/2));
        }
            if (player==1 && phase1){imageView.getScene().setCursor(new ImageCursor(cursorImageWhite,
                    cursorImageWhite.getWidth()/2, cursorImageWhite.getHeight()/2));}});
        imageView.setOnMouseExited (action ->{
            imageView.getScene().setCursor(Cursor.DEFAULT);
        });
    }


}
