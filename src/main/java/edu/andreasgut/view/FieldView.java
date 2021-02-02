package edu.andreasgut.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class FieldView extends AnchorPane {

    private ImageView imageView;
    private Image image;
    private final int FIELDDIMENSION = 600;
    private ViewManager viewManager;

    public FieldView(ViewManager viewManager) {
        this.viewManager = viewManager;
        imageView = new ImageView();
        image = new Image("edu/andreasgut/Images/Spielfeld.png");
        imageView.setFitHeight(FIELDDIMENSION);
        imageView.setFitWidth(FIELDDIMENSION);
        imageView.setImage(image);
        this.getChildren().add(imageView);
    }
}
