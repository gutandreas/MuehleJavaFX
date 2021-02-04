package edu.andreasgut.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.awt.*;

public class OptionsView extends HBox {

    private ToggleButton audioStartStop;
    private final int OPTIOHEIGHT = 200;
    private ViewManager viewManager;

    public OptionsView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.setAlignment(Pos.CENTER);
        this.setHeight(OPTIOHEIGHT);
        audioStartStop = new ToggleButton("Musik Aus");
        this.getChildren().addAll(audioStartStop);
        audioStartStop.setSelected(true);
        audioStartStop.setOnAction(action -> {
                if (!audioStartStop.isSelected()){
                viewManager.getSoundManager().stopMusic();
                System.out.println("Musik wird gestoppt");
                audioStartStop.setText("Musik Ein");}
                else {
                    viewManager.getSoundManager().getCurrentAudioclip().play();
                    System.out.println("Musik wird gestartet");
                    audioStartStop.setText("Musik Aus");
                }
            });


    }




}
