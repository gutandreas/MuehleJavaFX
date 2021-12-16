package edu.andreasgut.view;

import edu.andreasgut.sound.MUSIC;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;

public class MainMenuBar extends MenuBar {

    private Menu datei, bearbeiten, hilfe;
    private MenuItem neuStarten = new MenuItem("Neu starten");
    private MenuItem spielBeenden = new MenuItem("Spiel beenden");
    private MenuItem ueberDiesesSpiel = new MenuItem("Über dieses Spiel");
    private ViewManager viewManager;

    public MainMenuBar(ViewManager viewManager) {
        this.viewManager = viewManager;
        datei = new Menu("Datei");
        bearbeiten = new Menu("Bearbeiten");
        hilfe = new Menu("Hilfe");
        this.getMenus().addAll(datei,bearbeiten,hilfe);
        datei.getItems().addAll(neuStarten, spielBeenden);
        neuStarten.setDisable(true);
        hilfe.getItems().addAll(ueberDiesesSpiel);

        neuStarten.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Wollen Sie wirklich zum Hautpmenü zurückkehren?", ButtonType.CANCEL, ButtonType.YES);
            alert.setAlertType(Alert.AlertType.NONE);
            alert.setTitle("Zum Hauptmenü");
            Optional<ButtonType> result = alert.showAndWait();
            if(!result.isPresent()){}
            else if(result.get() == ButtonType.YES){
                StartMenuView startMenuView = new StartMenuView(viewManager);
                startMenuView.getStyleClass().add("startMenuView");
                viewManager.setStartMenuView(startMenuView);
                viewManager.changeToStartScene();
                viewManager.getAudioPlayer().chooseSound(MUSIC.MENU_SOUND);
                if (!viewManager.getOptionsView().getAudioOnOffSwitchButton().getState()){
                    viewManager.getAudioPlayer().stopMusic();
                }
                viewManager.getOptionsView().disableRestartButton();
                disableNeuStarten();

            }
            else if(result.get() == ButtonType.CANCEL) {}

        });

        spielBeenden.setOnAction(click ->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Wollen Sie das Spiel wirklich beenden?", ButtonType.CANCEL, ButtonType.YES);
                alert.setAlertType(Alert.AlertType.NONE);
                alert.setTitle("Spiel beenden");
                Optional<ButtonType> result = alert.showAndWait();
                if(!result.isPresent()){}
                    else if(result.get() == ButtonType.YES){
                        viewManager.getAudioPlayer().stopMusic();
                        Platform.exit();}
                    else if(result.get() == ButtonType.CANCEL) {} });

        ueberDiesesSpiel.setOnAction(click ->{
                Stage stage = new Stage();
                AnchorPane anchorPane = new AnchorPane();
                Scene scene = new Scene(anchorPane, 400, 300);
                stage.setScene(scene);
                Image image = new Image("edu/andreasgut/images/About.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(400);
                imageView.setFitHeight(300);
                anchorPane.getChildren().add(imageView);
                stage.setResizable(false);
                stage.show();});



        }

        public void enableNeuStarten(){
            neuStarten.setDisable(false);
        }

        public void disableNeuStarten(){
            neuStarten.setDisable(true);
        }


}
