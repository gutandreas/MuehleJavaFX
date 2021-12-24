package edu.andreasgut.view;

import edu.andreasgut.sound.MUSIC;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Optional;

public class MainMenuBar extends MenuBar {

    private Menu datei, hilfe;
    private MenuItem neuStarten = new MenuItem("Neu starten");
    private MenuItem spielBeenden = new MenuItem("Spiel beenden");
    private MenuItem spielregeln = new MenuItem("Spielregeln");
    private MenuItem ueberDiesesSpiel = new MenuItem("Über dieses Spiel");
    private ViewManager viewManager;

    public MainMenuBar(ViewManager viewManager) {
        this.viewManager = viewManager;
        datei = new Menu("Datei");
        hilfe = new Menu("Hilfe");
        this.getMenus().addAll(datei,hilfe);
        datei.getItems().addAll(neuStarten, spielBeenden);
        neuStarten.setDisable(true);
        hilfe.getItems().addAll(spielregeln, ueberDiesesSpiel);

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

        spielregeln.setOnAction(click ->{
            Stage stage = new Stage();
            stage.setTitle("Spielregeln");
            AnchorPane anchorPane = new AnchorPane();
            Scene scene = new Scene(anchorPane, 400, 600);
            stage.setScene(scene);
            TableView tableView = new TableView();
            tableView.setPrefSize(400,600);
            anchorPane.getChildren().add(tableView);
            TableColumn title = new TableColumn();
            TableColumn description = new TableColumn();
            tableView.getColumns().addAll(title, description);
            ObservableList<Rule> rulesList = FXCollections.observableArrayList(Rule.getRules());
            title.setCellValueFactory(new PropertyValueFactory<Rule, String>("title"));
            description.setCellValueFactory(new PropertyValueFactory<Rule, String>("description"));
            tableView.setItems(rulesList);

            stage.show();
        });

        ueberDiesesSpiel.setOnAction(click ->{
            Stage stage = new Stage();
            stage.setTitle("Über dieses Spiel");
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
