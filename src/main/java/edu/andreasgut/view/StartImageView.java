package edu.andreasgut.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class StartImageView extends ImageView {

    Image image;
    ViewManager viewManager;
    private final int IMAGEWIDTH = 300;

    public StartImageView(ViewManager viewManager) {
        this.viewManager = viewManager;
        image = new Image("edu/andreasgut/Images/StartImage.png");
        this.setFitWidth(IMAGEWIDTH);
        this.setImage(image);
    }
}
