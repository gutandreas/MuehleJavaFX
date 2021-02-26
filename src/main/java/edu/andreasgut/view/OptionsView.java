package edu.andreasgut.view;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Optional;

public class OptionsView extends HBox {

    private ToggleButton audioStartStopToggleButton;
    private Button exitButton;
    private ViewManager viewManager;
    private boolean musicOn = true;

    public OptionsView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("optionView");

        audioStartStopToggleButton = new ToggleButton("Musik Ein");
        audioStartStopToggleButton.setSelected(true);
        audioStartStopToggleButton.getStyleClass().add("toggleButtonOn");
        audioStartStopToggleButton.setMinWidth(100);
        audioStartStopToggleButton.setMinHeight(50);

        exitButton = new Button();
        exitButton.setGraphic(new ImageView(new Image("edu/andreasgut/Images/ExitButton.png", 50, 25, true, true)));
        exitButton.setMinWidth(100);
        exitButton.setMinHeight(50);
        exitButton.getStyleClass().add("exitButton");

        this.getChildren().addAll(audioStartStopToggleButton, exitButton);

        audioStartStopToggleButton.setOnAction(action -> {
                if (!audioStartStopToggleButton.isSelected()){
                viewManager.getSoundManager().stopMusic();
                System.out.println("Musik wird gestoppt");
                audioStartStopToggleButton.setText("Musik Aus");
                audioStartStopToggleButton.getStyleClass().removeAll("toggleButtonOn");
                audioStartStopToggleButton.getStyleClass().add("toggleButtonOff");
                musicOn = false;
                }
                else {
                    viewManager.getSoundManager().getCurrentAudioclip().play();
                    System.out.println("Musik wird gestartet");
                    audioStartStopToggleButton.setText("Musik Ein");
                    audioStartStopToggleButton.getStyleClass().removeAll("toggleButtonOff");
                    audioStartStopToggleButton.getStyleClass().add("toggleButtonOn");
                    musicOn = true;
                }
            });

        exitButton.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Wollen Sie das Spiel wirklich beenden?", ButtonType.CANCEL, ButtonType.YES);
            alert.setAlertType(Alert.AlertType.NONE);
            alert.setTitle("Spiel beenden");
            Optional<ButtonType> result = alert.showAndWait();
            if(!result.isPresent()){}
            else if(result.get() == ButtonType.YES){
                Platform.exit();}
            else if(result.get() == ButtonType.CANCEL) {} });

    }

    public boolean isMusicOn() {
        return musicOn;
    }
}
