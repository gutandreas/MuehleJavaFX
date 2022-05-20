package edu.andreasgut.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartImageView extends ImageView {

    Image image;
    ViewManager viewManager;

    public StartImageView(ViewManager viewManager, int imageNumber) {
        this.viewManager = viewManager;
        switch (imageNumber) {
            case 1:
                image = new Image(getClass().getResource("/edu/andreasgut/Images/StartImage1.png").toString());
                break;
            case 2:
                image = new Image(getClass().getResource("/edu/andreasgut/Images/StartImage2.png").toString());
                break;
        }
        this.setImage(image);
        this.getStyleClass().add("smoothImage");
    }
}
