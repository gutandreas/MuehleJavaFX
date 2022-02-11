package edu.andreasgut.view;

import edu.andreasgut.sound.MUSIC;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Optional;

public class MainMenuBar extends MenuBar {

    private Menu datei, hilfe;
    private MenuItem neuStarten = new MenuItem("Neues Spiel starten");
    private MenuItem spielBeenden = new MenuItem("Programm beenden");
    private MenuItem spielregeln = new MenuItem("Spielregeln");
    private MenuItem ueberDiesesSpiel = new MenuItem("Über dieses Spiel");
    private ViewManager viewManager;

    public MainMenuBar(ViewManager viewManager) {
        this.viewManager = viewManager;
        datei = new Menu("Spiel");
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
            stage.setResizable(false);
            stage.setTitle("Spielregeln");
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getStyleClass().add("ruleView");
            Scene scene = new Scene(anchorPane, 700, 550);
            scene.getStylesheets().add("edu/andreasgut/style.css");
            stage.setScene(scene);
            TableView tableView = new TableView();
            tableView.setPrefSize(690,500);
            TableColumn title = new TableColumn();
            title.setText("Regel");
            title.setPrefWidth(120);
            title.getStyleClass().add("ruleTableView");
            TableColumn description = new TableColumn();
            description.setText("Beschreibung");
            description.setPrefWidth(450);
            TableColumn tags = new TableColumn();
            tags.setText("Stichworte");
            tags.setPrefWidth(100);
            tableView.getColumns().addAll(title, description, tags);
            ObservableList<Rule> rulesList = FXCollections.observableArrayList(Rule.getRules());
            title.setCellValueFactory(new PropertyValueFactory<Rule, String>("title"));
            description.setCellValueFactory(new PropertyValueFactory<Rule, String>("description"));
            tags.setCellValueFactory(new PropertyValueFactory<Rule, String>("tags"));
            tableView.setItems(rulesList);

            for (Object tableColumn : tableView.getColumns()) {


                ((TableColumn<Rule, String>) tableColumn).setSortable(false);
                ((TableColumn<Rule, String>) tableColumn).setResizable(false);
                ((TableColumn<Rule, String>) tableColumn).setCellFactory(tc -> {

                    TableCell<Rule, String> cell = new TableCell<>();
                    Text text = new Text();
                    cell.setGraphic(text);
                    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(((TableColumn<Rule, String>) tableColumn).widthProperty());
                    text.textProperty().bind(cell.itemProperty());
                    return cell;
                });
            }

            tableView.setSelectionModel(null);
            Label searchLabel = new Label("Regeln durchsuchen: ");
            TextField searchTextfield = new TextField();
            searchTextfield.setPromptText("Suchbegriff");
            HBox searchHBox = new HBox();
            searchHBox.setSpacing(15);
            searchHBox.setAlignment(Pos.CENTER_LEFT);
            searchHBox.getChildren().addAll(searchLabel, searchTextfield);

            searchTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
                String searchText = newValue;
                ObservableList<Rule> filteredRuleList = FXCollections.observableArrayList();
                for (Rule rule : Rule.getRules()){
                    if (rule.getTags().toUpperCase().contains(searchText.toUpperCase())
                            || rule.getTitle().toUpperCase(Locale.ROOT).contains(searchText.toUpperCase())
                            || rule.getDescription().toUpperCase().contains(searchText.toUpperCase())){
                        filteredRuleList.add(rule);
                    }
                }
                tableView.setItems(filteredRuleList);
            });

            VBox mainVBox = new VBox();
            mainVBox.getChildren().addAll(tableView, searchHBox);
            mainVBox.setPadding(new Insets(5,5,5,5));
            mainVBox.setSpacing(5);
            anchorPane.getChildren().addAll(mainVBox);
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
