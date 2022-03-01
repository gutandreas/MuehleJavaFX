package edu.andreasgut.view;

import edu.andreasgut.communication.Messenger;
import edu.andreasgut.sound.MUSIC;
import edu.andreasgut.view.fxElements.MusicSwitchButton;
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
    private Button exitButton, restartButton;
    private ViewManager viewManager;
    private HBox audioHBox;

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
        exitButton.setGraphic(new ImageView(new Image("edu/andreasgut/images/ExitButton.png", 50, 25, true, true)));
        exitButton.setMinWidth(60);
        exitButton.setMinHeight(30);
        exitButton.getStyleClass().add("exitButton");

        restartButton = new Button();
        restartButton.setGraphic(new ImageView(new Image("edu/andreasgut/images/RestartButton.png", 50, 25, true, true)));
        restartButton.setMinWidth(60);
        restartButton.setMinHeight(30);
        restartButton.getStyleClass().add("restartButton");
        restartButton.setDisable(true);

        this.getChildren().addAll(audioHBox, restartButton, exitButton);


        exitButton.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Wollen Sie das Spiel wirklich beenden?", ButtonType.CANCEL, ButtonType.YES);
            alert.setAlertType(Alert.AlertType.NONE);
            alert.setTitle("Spiel beenden");
            Optional<ButtonType> result = alert.showAndWait();
            if(!result.isPresent()){}
            else if(result.get() == ButtonType.YES){
                if (viewManager.getGame() != null && viewManager.getGame().getWebsocketClient() != null){
                    Messenger.sendGiveUpMessage(viewManager);
                }
                viewManager.getAudioPlayer().stopMusic();
                Platform.exit();}
            else if(result.get() == ButtonType.CANCEL) {} });


        restartButton.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Wollen Sie wirklich zum Hautpmenü zurückkehren?", ButtonType.CANCEL, ButtonType.YES);
            alert.setAlertType(Alert.AlertType.NONE);
            alert.setTitle("Zum Hauptmenü");
            Optional<ButtonType> result = alert.showAndWait();
            if(!result.isPresent()){}
            else if(result.get() == ButtonType.YES){
                if (viewManager.getGame().getWebsocketClient() != null){
                    Messenger.sendGiveUpMessage(viewManager);
                }
                StartMenuView startMenuView = new StartMenuView(viewManager);
                startMenuView.getStyleClass().add("startMenuView");
                viewManager.setStartMenuView(startMenuView);
                viewManager.changeToStartScene();
                viewManager.getAudioPlayer().chooseSound(MUSIC.MENU_SOUND);
                if (!audioOnOffMusicSwitchButton.getState()){
                    viewManager.getAudioPlayer().stopMusic();
                }
                viewManager.getOptionsView().disableRestartButton();
                viewManager.getMainMenuBar().disableNeuStarten();

            }
            else if(result.get() == ButtonType.CANCEL) {}

        });

    }

    public MusicSwitchButton getAudioOnOffSwitchButton() {
        return audioOnOffMusicSwitchButton;
    }

    public void enableRestartButton(){
        restartButton.setDisable(false);
    }

    public void disableRestartButton(){
        restartButton.setDisable(true);
    }
}
