package edu.andreasgut.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class StartImageView extends ImageView {

    Image image;
    ViewManager viewManager;

    public StartImageView(ViewManager viewManager, int bildnummer) {
        this.viewManager = viewManager;
        switch (bildnummer){
            case 1:
                image = new Image("edu/andreasgut/Images/StartImage1.png");
                break;
            case 2:
                image = new Image("edu/andreasgut/Images/StartImage2.png");
                break;
        }
        this.setImage(image);
    }
}
