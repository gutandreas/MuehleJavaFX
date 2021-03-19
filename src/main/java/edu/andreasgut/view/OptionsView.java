package edu.andreasgut.view;

import edu.andreasgut.view.internet.MusicSwitchButton;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class OptionsView extends HBox {

    private MusicSwitchButton audioOnOffMusicSwitchButton;
    private Label audioOnLabel, audioOffLabel;
    private Button exitButton;
    private ViewManager viewManager;
    private HBox audioHBox;
    private boolean musicOn = true;

    public OptionsView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("optionView");
        this.getStyleClass().add("w3schools/toggleSwitch.css");



        audioOnOffMusicSwitchButton = new MusicSwitchButton(this.viewManager);
        audioOnLabel = new Label("Musik an");
        audioOffLabel = new Label("Musik aus");
        audioHBox = new HBox();
        audioHBox.getChildren().addAll(audioOffLabel, audioOnOffMusicSwitchButton, audioOnLabel);
        audioHBox.setAlignment(Pos.CENTER);
        audioHBox.setSpacing(5);



        exitButton = new Button();
        exitButton.setGraphic(new ImageView(new Image("edu/andreasgut/Images/ExitButton.png", 50, 25, true, true)));
        exitButton.setMinWidth(60);
        exitButton.setMinHeight(30);
        exitButton.getStyleClass().add("exitButton");

        this.getChildren().addAll(audioHBox, exitButton);


        /*audioStartStopToggleButton.setOnAction(action -> {
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
            });*/

        exitButton.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Wollen Sie das Spiel wirklich beenden?", ButtonType.CANCEL, ButtonType.YES);
            alert.setAlertType(Alert.AlertType.NONE);
            alert.setTitle("Spiel beenden");
            Optional<ButtonType> result = alert.showAndWait();
            if(!result.isPresent()){}
            else if(result.get() == ButtonType.YES){
                viewManager.getSoundManager().stopMusic();
                Platform.exit();}
            else if(result.get() == ButtonType.CANCEL) {} });

    }


    public MusicSwitchButton getAudioOnOffSwitchButton() {
        return audioOnOffMusicSwitchButton;
    }
}
