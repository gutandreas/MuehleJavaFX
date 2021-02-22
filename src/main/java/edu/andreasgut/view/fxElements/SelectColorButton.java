package edu.andreasgut.view.fxElements;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class SelectColorButton extends ToggleButton {


    public SelectColorButton(String text, STONECOLOR stonecolor, boolean defaultSelected) {
        super(text, new ImageView(new Image(stonecolor.getPath(), 50, 50, true, true)));
        this.setSelected(defaultSelected);
        if (defaultSelected){
            this.getStyleClass().add("selectColorButtonOn");
        }
        else {
            this.getStyleClass().add("selectColorButtonOff");
        }
    }



}
