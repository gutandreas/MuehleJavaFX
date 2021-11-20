package edu.andreasgut.view;

import edu.andreasgut.game.ComputerPlayer;
import edu.andreasgut.game.Game;
import edu.andreasgut.game.HumanPlayer;
import edu.andreasgut.game.ScorePoints;
import edu.andreasgut.online.WebsocketClient;
import edu.andreasgut.sound.MUSIC;
import edu.andreasgut.view.fxElements.BeginnerSwitchButton;
import edu.andreasgut.view.fxElements.SelectColorButton;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.css.StyleClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.event.ChangeListener;

public class StartMenuView extends VBox {


    /*String ipAdress = "217.160.10.113";
    String port = "443";*/

    String ipAdress = "localhost";
    String port = "443";

    private final int STARTDIMENSION = 600;
    ViewManager viewManager;
    VBox offlineVBox, onlineVBox, scoreVBoxComputerPut, scoreVBoxComputerMove, scoreVBoxEnemyPut, scoreVBoxEnemyMove;
    HBox hBoxRadioButtons, player1HBox, player2HBox, computerHBox, beginnerHBox, startGameHBox, computerBattleHBox, computerSettingBox, putPhaseHBox, movePhaseHBox, onlineButtonHBox, offlineButtonHBox;
    ToggleGroup radioButtonGroup;
    RadioButton onePlayerRadioButton, twoPlayersRadioButton;
    TextField namePlayer1Textfield, namePlayer2Textfield, computerBattleTextfield, gameCodeTextfield;
    Label offlineInformationLabel, offlineTitleLabel, onlineInformationLabel, onlineTitleLabel, stonesColorLabel1, stonesColorLabel2, beginnerLabel1, beginnerLabel2, levelLabel, stoneColorComputerLabel, startGameLabel, joinGameLabel;
    Button startButton, computerOnlineButton, scorePointsButton;
    SelectColorButton stonesBlackButton1, stonesWhiteButton1, stonesBlackButton2, stonesWhiteButton2, computerBlackButton, computerWhiteButton;
    BeginnerSwitchButton beginnerSwitchButton, startOnlineGameSwitchButton;
    ImageView player1StonesImageView, player2StonesImageView;
    STONECOLOR player1Color, player2Color;
    ChoiceBox computerLevelChoiceBox;

    ScorePoints putPoints = new ScorePoints(4000, 1000,20, 200, 300,3, -2000, -1000, -30, -200, -100, -2);
    ScorePoints movePoints = new ScorePoints(1000, 300,300, 200, 30,3, 1000, 280, 300, 300, 300, 2);
    /*int[] defaultPutPoints = {4000, 1000,20, 200, 300,3, 2000, 1000, 30, 200, 100, 2};
    int[] defaultMovePoints = {1000, 300,300, 200, 30,3, 1000, 280, 300, 300, 300, 2};*/





    public StartMenuView(ViewManager viewManager) {

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        TextFormatter<?> formatter = new TextFormatter<Object>(change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                change.setText(change.getText().toUpperCase());
                return change;
            } else {
                //Verbotene Zeichen
                return null;
            }
        });

        this.viewManager = viewManager;
        this.setPrefWidth(STARTDIMENSION);
        offlineVBox = new VBox();
        onlineVBox = new VBox();
        startButton = new Button("Offlinespiel starten");
        startButton.setPrefWidth(185);
        computerOnlineButton = new Button("Computerbattle starten");
        computerOnlineButton.setPrefWidth(185);
        gameCodeTextfield = new TextField();
        gameCodeTextfield.setPromptText("Gamecode");
        gameCodeTextfield.setTextFormatter(formatter);
        gameCodeTextfield.textProperty().addListener(change -> {
                if (gameCodeTextfield.getText().length() > 10) {
                    String s = gameCodeTextfield.getText().substring(0, 10);
                    gameCodeTextfield.setText(s);
                }
            }
        );
        gameCodeTextfield.setMaxWidth(120);


        setupOfflineTitleAndWarning();
        setupRadioButtons();
        setupBeginnerSwitch();
        setupPlayerInformations();
        setupOnlineTitleAndWarning();
        setupStartGameSwitchButton();
        setupComputerBattleInformation();
        setupComputerSettingBox();

        computerHBox = new HBox();
        computerHBox.getChildren().addAll(beginnerHBox, computerSettingBox);

        onlineButtonHBox = new HBox();
        onlineButtonHBox.getChildren().addAll(computerOnlineButton, onlineInformationLabel);
        onlineButtonHBox.getStyleClass().add("buttonHBox");

        offlineButtonHBox = new HBox();
        offlineButtonHBox.getChildren().addAll(startButton, offlineInformationLabel);
        offlineButtonHBox.getStyleClass().add("buttonHBox");

        offlineVBox.getChildren().addAll(offlineTitleLabel, hBoxRadioButtons, player1HBox, computerHBox, offlineButtonHBox);
        offlineVBox.setSpacing(20);
        offlineVBox.setStyle("-fx-padding: 30 0 0 0");
        onlineVBox.getChildren().addAll(onlineTitleLabel, gameCodeTextfield, startGameHBox, computerBattleHBox, onlineButtonHBox);
        onlineVBox.setSpacing(20);
        this.getChildren().addAll(onlineVBox, offlineVBox);
        this.setAlignment(Pos.CENTER);

        setupRadioButtonAction();
        setupColorButtonAction();
        setupStartOnlineGameSwitchButton();
        setupStartButtonAction();
        setupComputerOnlineButtonAction();
        setupComputerBattleColorButtonAction();
    }

    private void setupOfflineTitleAndWarning(){
        offlineTitleLabel = new Label( "Offline spielen");
        offlineTitleLabel.getStyleClass().add("labelTitle");
        offlineInformationLabel = new Label();
        offlineInformationLabel.getStyleClass().add("labelWarning");
    }

    private void setupOnlineTitleAndWarning(){
        onlineTitleLabel = new Label("Computer Onlinebattle spielen");
        onlineTitleLabel.getStyleClass().add("labelTitle");
        onlineInformationLabel = new Label();
        onlineInformationLabel.getStyleClass().add("labelWarning");
    }

    private void setupRadioButtons(){
        hBoxRadioButtons = new HBox();
        radioButtonGroup = new ToggleGroup();
        onePlayerRadioButton = new RadioButton("Ein Spieler");
        onePlayerRadioButton.setToggleGroup(radioButtonGroup);
        twoPlayersRadioButton = new RadioButton("Zwei Spieler");
        twoPlayersRadioButton.setToggleGroup(radioButtonGroup);
        hBoxRadioButtons.getChildren().addAll(onePlayerRadioButton, twoPlayersRadioButton);
        hBoxRadioButtons.setSpacing(20);
        radioButtonGroup.selectToggle(onePlayerRadioButton);
    }

    private void setupBeginnerSwitch(){
        beginnerHBox = new HBox();
        beginnerSwitchButton = new BeginnerSwitchButton(viewManager);
        beginnerLabel1 = new Label("Beginner: Spieler 1");
        beginnerLabel2 = new Label("Computer");
        beginnerHBox.getChildren().addAll(beginnerLabel1, beginnerSwitchButton, beginnerLabel2);
        beginnerHBox.setAlignment(Pos.CENTER_LEFT);
        beginnerHBox.setSpacing(10);
        beginnerHBox.setPrefHeight(70);

    }

    private void setupComputerSettingBox(){
        computerSettingBox = new HBox();
        scorePointsButton = new Button("Score");
        computerLevelChoiceBox = new ChoiceBox(FXCollections.observableArrayList("1","2","3","4","5"));
        computerLevelChoiceBox.getSelectionModel().select(2);
        levelLabel = new Label("Level: ");
        computerSettingBox.getChildren().addAll(levelLabel, computerLevelChoiceBox, scorePointsButton);
        computerSettingBox.getStyleClass().add("computerSettingBox");

        PopOver popOver = new PopOver();
        Group root = new Group();
        HBox mainHBox = new HBox();
        putPhaseHBox = new HBox();
        movePhaseHBox = new HBox();
        mainHBox.getStyleClass().add("popOver");

        scoreVBoxComputerPut = new VBox();
        Label computerTitlePut = new Label("Computer");
        computerTitlePut.getStyleClass().add("scoreTitles");
        scoreVBoxComputerPut.getChildren().addAll(computerTitlePut);
        scoreVBoxEnemyPut = new VBox();
        Label enemyTitlePut = new Label("Gegenspieler");
        enemyTitlePut.getStyleClass().add("scoreTitles");
        scoreVBoxEnemyPut.getChildren().addAll(enemyTitlePut);

        scoreVBoxComputerMove = new VBox();
        Label computerTitleMove = new Label("Computer");
        computerTitlePut.getStyleClass().add("scoreTitles");
        scoreVBoxComputerMove.getChildren().addAll(computerTitleMove);
        scoreVBoxEnemyMove = new VBox();
        Label enemyTitleMove = new Label("Gegenspieler");
        enemyTitlePut.getStyleClass().add("scoreTitles");
        scoreVBoxEnemyMove.getChildren().addAll(enemyTitleMove);



        String[] labelTitles = {"Anzahl Steine:", "Geschlossene Mühle:", "Offene Mühle:", "2 nebeneinander:", "2 mit Lücke:", "Möglicher Zug:"};


        for (int i = 0; i < 6; i++){

            Pattern positivePattern = Pattern.compile("[0-9]*");
            Pattern negativePattern = Pattern.compile("-[0-9]*");
            TextFormatter<?> formatterComputerPut = new TextFormatter<Object>(change -> {
                if (positivePattern.matcher(change.getControlNewText()).matches()) {
                    return change;
                } else {
                    //Verbotene Zeichen
                    return null;
                }
            });
            TextFormatter<?> formatterEnemyPut = new TextFormatter<Object>(change -> {
                if (negativePattern.matcher(change.getControlNewText()).matches()) {
                    return change;
                } else {
                    //Verbotene Zeichen
                    return null;
                }
            });
            TextFormatter<?> formatterComputerMove = new TextFormatter<Object>(change -> {
                if (positivePattern.matcher(change.getControlNewText()).matches()) {
                    return change;
                } else {
                    //Verbotene Zeichen
                    return null;
                }
            });
            TextFormatter<?> formatterEnemyMove = new TextFormatter<Object>(change -> {
                if (negativePattern.matcher(change.getControlNewText()).matches()) {
                    return change;
                } else {
                    //Verbotene Zeichen
                    return null;
                }
            });


            Label labelComputerPut = new Label(labelTitles[i]);
            TextField textFieldComputerPut = new TextField();
            textFieldComputerPut.setPromptText(String.valueOf(putPoints.getValueByIndex(i)));
            textFieldComputerPut.setTextFormatter(formatterComputerPut);
            textFieldComputerPut.textProperty().addListener(change -> {
                        if (textFieldComputerPut.getText().length() > 5) {
                            String s = textFieldComputerPut.getText().substring(0, 5);
                            textFieldComputerPut.setText(s);
                        }
                    }
            );
            scoreVBoxComputerPut.getChildren().addAll(labelComputerPut, textFieldComputerPut);

            Label labelEnemyPut = new Label(labelTitles[i]);
            TextField textFieldEnemyPut = new TextField();
            textFieldEnemyPut.setPromptText(String.valueOf(putPoints.getValueByIndex(i+6)));
            textFieldEnemyPut.setTextFormatter(formatterEnemyPut);
            textFieldEnemyPut.textProperty().addListener(change -> {
                        if (textFieldEnemyPut.getText().length() > 6) {
                            String s = textFieldEnemyPut.getText().substring(0, 6);
                            textFieldEnemyPut.setText(s);
                        }
                    }
            );
            scoreVBoxEnemyPut.getChildren().addAll(labelEnemyPut, textFieldEnemyPut);

            Label labelComputerMove = new Label(labelTitles[i]);
            TextField textFieldComputerMove = new TextField();
            textFieldComputerMove.setTextFormatter(formatterComputerMove);
            textFieldComputerMove.setPromptText(String.valueOf(movePoints.getValueByIndex(i)));
            textFieldComputerMove.textProperty().addListener(change -> {
                        if (textFieldComputerMove.getText().length() > 5) {
                            String s = textFieldComputerMove.getText().substring(0, 5);
                            textFieldComputerMove.setText(s);
                        }
                    }
            );
            scoreVBoxComputerMove.getChildren().addAll(labelComputerMove, textFieldComputerMove);

            Label labelEnemyMove = new Label(labelTitles[i]);
            TextField textFieldEnemyMove = new TextField();
            textFieldEnemyMove.setTextFormatter(formatterEnemyMove);
            textFieldEnemyMove.setPromptText(String.valueOf(movePoints.getValueByIndex(i+6)));
            textFieldEnemyMove.textProperty().addListener(change -> {
                        if (textFieldEnemyMove.getText().length() > 6) {
                            String s = textFieldEnemyMove.getText().substring(0, 6);
                            textFieldEnemyMove.setText(s);
                        }
                    }
            );
            scoreVBoxEnemyMove.getChildren().addAll(labelEnemyMove, textFieldEnemyMove);

        }

        Label putPhaseTitleLabel = new Label("Setzphase");
        putPhaseHBox.getChildren().addAll(putPhaseTitleLabel, scoreVBoxComputerPut, scoreVBoxEnemyPut);
        putPhaseHBox.getStyleClass().add("scorePhaseHBox");

        Label movePhaseTitleLabel = new Label("Zugphase");
        movePhaseHBox.getChildren().addAll(movePhaseTitleLabel, scoreVBoxComputerMove, scoreVBoxEnemyMove);
        movePhaseHBox.getStyleClass().add("scorePhaseHBox");

        mainHBox.getChildren().addAll(putPhaseHBox, movePhaseHBox);

        root.getChildren().addAll(mainHBox);
        popOver.setContentNode(root);

        scorePointsButton.setOnAction(click -> {

            popOver.setAutoHide(true);
            popOver.setDetachedTitle("Punkte für Ereignisse setzen:");
            popOver.setAutoFix(true);
            popOver.setHideOnEscape(true);
            popOver.setDetached(true);
            popOver.setArrowSize(0);
            popOver.show(viewManager.getMainStage());
        });





    }

    private void setupStartGameSwitchButton(){
        startGameHBox = new HBox();
        startOnlineGameSwitchButton = new BeginnerSwitchButton(viewManager);
        startGameLabel = new Label("Spiel eröffnen");
        joinGameLabel = new Label("Einem Spiel beitreten");
        startGameHBox.getChildren().addAll(startGameLabel, startOnlineGameSwitchButton, joinGameLabel);
        startGameHBox.setAlignment(Pos.CENTER_LEFT);
        startGameHBox.setSpacing(10);
        startGameHBox.setPrefHeight(70);
    }

    private void setupComputerBattleInformation(){

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        TextFormatter<?> formatter = new TextFormatter<Object>(change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                change.setText(change.getText().toUpperCase());
                return change;
            } else {
                //Verbotene Zeichen
                return null;
            }
        });

        computerBattleTextfield = new TextField();
        computerBattleTextfield.setPromptText("Name des Computers");
        computerBattleTextfield.setTextFormatter(formatter);
        computerBattleTextfield.textProperty().addListener(change -> {
                    if (computerBattleTextfield.getText().length() > 15) {
                        String s = computerBattleTextfield.getText().substring(0, 15);
                        computerBattleTextfield.setText(s);
                    }
                }
        );
        computerBlackButton = new SelectColorButton(null, STONECOLOR.BLACK, true);
        computerWhiteButton = new SelectColorButton(null, STONECOLOR.WHITE, false);
        stoneColorComputerLabel = new Label("Steinfarbe: ");

        computerBattleHBox = new HBox();
        computerBattleHBox.getChildren().addAll(computerBattleTextfield, stoneColorComputerLabel, computerBlackButton, computerWhiteButton);
        computerBattleHBox.setSpacing(20);
        computerBattleHBox.setAlignment(Pos.CENTER_LEFT);

    }

    private void setupPlayerInformations(){

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        TextFormatter<?> formatter1 = new TextFormatter<Object>(change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                change.setText(change.getText().toUpperCase());
                return change;
            } else {
                //Verbotene Zeichen
                return null;
            }
        });

        TextFormatter<?> formatter2 = new TextFormatter<Object>(change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                change.setText(change.getText().toUpperCase());
                return change;
            } else {
                //Verbotene Zeichen
                return null;
            }
        });


        namePlayer1Textfield = new TextField();
        namePlayer1Textfield.setTextFormatter(formatter1);
        namePlayer1Textfield.setPromptText("Name Spieler 1");
        namePlayer1Textfield.textProperty().addListener(change -> {
                    if (namePlayer1Textfield.getText().length() > 15) {
                        String s = namePlayer1Textfield.getText().substring(0, 15);
                        namePlayer1Textfield.setText(s);
                    }
                }
        );
        stonesColorLabel1 = new Label("Steinfarbe: ");
        stonesBlackButton1 = new SelectColorButton(null, STONECOLOR.BLACK, true);
        stonesWhiteButton1 = new SelectColorButton(null, STONECOLOR.WHITE, false);
        player1Color = STONECOLOR.BLACK;

        player1HBox = new HBox();
        player1HBox.getChildren().addAll(namePlayer1Textfield, stonesColorLabel1, stonesBlackButton1, stonesWhiteButton1);
        player1HBox.setSpacing(20);
        player1HBox.setAlignment(Pos.CENTER_LEFT);

        namePlayer2Textfield = new TextField();
        namePlayer2Textfield.setTextFormatter(formatter2);
        namePlayer2Textfield.setPromptText("Name Spieler 2");
        namePlayer2Textfield.setVisible(true);
        namePlayer2Textfield.textProperty().addListener(change -> {
                    if (namePlayer2Textfield.getText().length() > 15) {
                        String s = namePlayer2Textfield.getText().substring(0, 15);
                        namePlayer2Textfield.setText(s);
                    }
                }
        );
        stonesColorLabel2 = new Label("Steinfarbe: ");
        stonesBlackButton2 = new SelectColorButton(null, STONECOLOR.BLACK, false);
        stonesWhiteButton2 = new SelectColorButton(null, STONECOLOR.WHITE, true);
        player2Color = STONECOLOR.WHITE;

        player2HBox = new HBox();
        player2HBox.getChildren().addAll(namePlayer2Textfield, stonesColorLabel2, stonesBlackButton2, stonesWhiteButton2);
        player2HBox.setSpacing(20);
        player2HBox.setAlignment(Pos.CENTER_LEFT);
        player2HBox.setPrefHeight(70);
    }

    private void setupColorButtonAction(){
        stonesBlackButton1.setOnAction(click -> {
            stonesBlackButton1.setSelected(true);
            stonesBlackButton1.getStyleClass().removeAll("selectColorButtonOff");
            stonesBlackButton1.getStyleClass().add("selectColorButtonOn");
            stonesWhiteButton1.setSelected(false);
            stonesWhiteButton1.getStyleClass().removeAll("selectColorButtonOn");
            stonesWhiteButton1.getStyleClass().add("selectColorButtonOff");
            stonesBlackButton2.setSelected(false);
            stonesBlackButton2.getStyleClass().removeAll("selectColorButtonOn");
            stonesBlackButton2.getStyleClass().add("selectColorButtonOff");
            stonesWhiteButton2.setSelected(true);
            stonesWhiteButton2.getStyleClass().removeAll("selectColorButtonOff");
            stonesWhiteButton2.getStyleClass().add("selectColorButtonOn");
            player1Color = STONECOLOR.BLACK;
            player2Color = STONECOLOR.WHITE;

        });

        stonesBlackButton2.setOnAction(click -> {
            stonesBlackButton2.setSelected(true);
            stonesBlackButton2.getStyleClass().removeAll("selectColorButtonOff");
            stonesBlackButton2.getStyleClass().add("selectColorButtonOn");
            stonesWhiteButton2.setSelected(false);
            stonesWhiteButton2.getStyleClass().removeAll("selectColorButtonOn");
            stonesWhiteButton2.getStyleClass().add("selectColorButtonOff");
            stonesBlackButton1.setSelected(false);
            stonesBlackButton1.getStyleClass().removeAll("selectColorButtonOn");
            stonesBlackButton1.getStyleClass().add("selectColorButtonOff");
            stonesWhiteButton1.setSelected(true);
            stonesWhiteButton1.getStyleClass().removeAll("selectColorButtonOff");
            stonesWhiteButton1.getStyleClass().add("selectColorButtonOn");
            player2Color = STONECOLOR.BLACK;
            player1Color = STONECOLOR.WHITE;

        });

        stonesWhiteButton1.setOnAction(click -> {
            stonesWhiteButton1.setSelected(true);
            stonesWhiteButton1.getStyleClass().removeAll("selectColorButtonOff");
            stonesWhiteButton1.getStyleClass().add("selectColorButtonOn");
            stonesBlackButton1.setSelected(false);
            stonesBlackButton1.getStyleClass().removeAll("selectColorButtonOn");
            stonesBlackButton1.getStyleClass().add("selectColorButtonOff");
            stonesBlackButton2.setSelected(true);
            stonesBlackButton2.getStyleClass().removeAll("selectColorButtonOff");
            stonesBlackButton2.getStyleClass().add("selectColorButtonOn");
            stonesWhiteButton2.setSelected(false);
            stonesWhiteButton2.getStyleClass().removeAll("selectColorButtonOn");
            stonesWhiteButton2.getStyleClass().add("selectColorButtonOff");
            player1Color = STONECOLOR.WHITE;
            player2Color = STONECOLOR.BLACK;
        });

        stonesWhiteButton2.setOnAction(click -> {
            stonesWhiteButton2.setSelected(true);
            stonesWhiteButton2.getStyleClass().removeAll("selectColorButtonOff");
            stonesWhiteButton2.getStyleClass().add("selectColorButtonOn");
            stonesBlackButton2.setSelected(false);
            stonesBlackButton2.getStyleClass().removeAll("selectColorButtonOn");
            stonesBlackButton2.getStyleClass().add("selectColorButtonOff");
            stonesBlackButton1.setSelected(true);
            stonesBlackButton1.getStyleClass().removeAll("selectColorButtonOff");
            stonesBlackButton1.getStyleClass().add("selectColorButtonOn");
            stonesWhiteButton1.setSelected(false);
            stonesWhiteButton1.getStyleClass().removeAll("selectColorButtonOn");
            stonesWhiteButton1.getStyleClass().add("selectColorButtonOff");
            player2Color = STONECOLOR.WHITE;
            player1Color = STONECOLOR.BLACK;
        });
    }

    private void setupStartOnlineGameSwitchButton(){
        startOnlineGameSwitchButton.setOnMousePressed(click -> {

            if (!startOnlineGameSwitchButton.getState()){
                stoneColorComputerLabel.setVisible(false);
                computerBlackButton.setVisible(false);
                computerWhiteButton.setVisible(false);
            }
            else {
                stoneColorComputerLabel.setVisible(true);
                computerBlackButton.setVisible(true);
                computerWhiteButton.setVisible(true);
            }

        });
    }

    private void setupComputerBattleColorButtonAction(){
        computerBlackButton.setOnAction(click -> {
            computerBlackButton.setSelected(true);
            computerBlackButton.getStyleClass().removeAll("selectColorButtonOff");
            computerBlackButton.getStyleClass().add("selectColorButtonOn");
            computerWhiteButton.setSelected(false);
            computerWhiteButton.getStyleClass().removeAll("selectColorButtonOn");
            computerWhiteButton.getStyleClass().add("selectColorButtonOff");
        });

        computerWhiteButton.setOnAction(click -> {
            computerWhiteButton.setSelected(true);
            computerWhiteButton.getStyleClass().removeAll("selectColorButtonOff");
            computerWhiteButton.getStyleClass().add("selectColorButtonOn");
            computerBlackButton.setSelected(false);
            computerBlackButton.getStyleClass().removeAll("selectColorButtonOn");
            computerBlackButton.getStyleClass().add("selectColorButtonOff");
        });


    }

    private void setupRadioButtonAction(){
        onePlayerRadioButton.setOnAction(action -> {
            offlineVBox.getChildren().add(3, computerHBox);
            offlineVBox.getChildren().remove(player2HBox);

        });
        twoPlayersRadioButton.setOnAction(action -> {
            offlineVBox.getChildren().remove(computerHBox);
            offlineVBox.getChildren().add(3, player2HBox);

        });
    }

    private void setupStartButtonAction(){
        startButton.setOnAction( action -> {

            if ((radioButtonGroup.getSelectedToggle().equals(onePlayerRadioButton) &&
                    namePlayer1Textfield.getText().length()>0)
                    || (radioButtonGroup.getSelectedToggle().equals(twoPlayersRadioButton) &&
                    namePlayer1Textfield.getText().length()>0 && namePlayer2Textfield.getText().length()>0)){

                viewManager.getSoundManager().chooseSound(MUSIC.PLAY_SOUND);
                if (!viewManager.getOptionsView().getAudioOnOffSwitchButton().getState()){
                    viewManager.getSoundManager().stopMusic();
                }


                if (twoPlayersRadioButton.isSelected()){
                    viewManager.setGame(new Game(viewManager,
                            new HumanPlayer(viewManager, namePlayer1Textfield.getText().toUpperCase(), true),
                            new HumanPlayer(viewManager, namePlayer2Textfield.getText().toUpperCase(), true)));
                }
                else {

                    int counterPut = 0;
                    //computerPutpoints
                    for (Node node : scoreVBoxComputerPut.getChildren()){
                        if (node instanceof TextField){
                            if (((TextField) node).getText().length()>0){
                                putPoints.setValueByIndex(counterPut, Integer.parseInt(((TextField) node).getText()));
                            }
                            counterPut++;
                        }
                    }

                    //enemyPutpoints
                    for (Node node : scoreVBoxEnemyPut.getChildren()){
                        if (node instanceof TextField){
                            if (((TextField) node).getText().length()>0){
                                putPoints.setValueByIndex(counterPut, Integer.parseInt(((TextField) node).getText()));
                            }
                            counterPut++;
                        }
                    }
                    System.out.println(putPoints);

                    int counterMove = 0;
                    //computerPutpoints
                    for (Node node : scoreVBoxComputerMove.getChildren()){
                        if (node instanceof TextField){
                            if (((TextField) node).getText().length()>0){
                                movePoints.setValueByIndex(counterMove, Integer.parseInt(((TextField) node).getText()));
                            }
                            counterMove++;
                        }
                    }

                    //enemyPutpoints
                    for (Node node : scoreVBoxEnemyMove.getChildren()){
                        if (node instanceof TextField){
                            if (((TextField) node).getText().length()>0){
                                movePoints.setValueByIndex(counterMove, Integer.parseInt(((TextField) node).getText()));
                            }
                            counterMove++;
                        }
                    }
                    System.out.println(movePoints);





                    viewManager.setGame(new Game(viewManager,
                            new HumanPlayer(viewManager, namePlayer1Textfield.getText().toUpperCase(), true),
                            beginnerSwitchButton.getState(), putPoints, movePoints, Integer.parseInt(computerLevelChoiceBox.getValue().toString())));
                }


                viewManager.createGameScene(new FieldView(viewManager, player1Color, player2Color, true),
                        new ScoreView(viewManager, player1Color, player2Color),
                        new LogView(viewManager, false));



                viewManager.changeToGameScene();
                if (beginnerSwitchButton.getState()){
                    viewManager.getGame().getCurrentPlayer().preparePutOrMove(viewManager);
                }

                viewManager.getFieldView().setPutCursor();






            }
            else {
                offlineInformationLabel.setText("Es fehlen Eingaben, um das Spiel zu starten");}});
    }


    private void setupComputerOnlineButtonAction(){
        computerOnlineButton.setOnAction( action -> {

            HttpClient client = HttpClient.newBuilder().build();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gameCode", gameCodeTextfield.getText());

            if (gameCodeTextfield.getText().length() == 0){
                onlineInformationLabel.setText("Der Gamecode fehlt");
                return;}

            if (computerBattleTextfield.getText().length() == 0){
                onlineInformationLabel.setText("Der Computername fehlt");
                return;}

            STONECOLOR computerColor;
            STONECOLOR onlinePlayerColor;
            if (computerBlackButton.isSelected()){
                computerColor = STONECOLOR.BLACK;
                onlinePlayerColor = STONECOLOR.WHITE;}
            else {
                computerColor = STONECOLOR.WHITE;
                onlinePlayerColor = STONECOLOR.BLACK;}

            jsonObject.put("player1Color", computerColor.toString());

            String urlAsString;

            //join
            if (startOnlineGameSwitchButton.getState()){
                urlAsString = "http://" + ipAdress + ":" + port + "/index/controller/menschVsMensch/join";
                jsonObject.put("player2Name", computerBattleTextfield.getText());}
            //start
            else {
                urlAsString = "http://" + ipAdress + ":" + port + "/index/controller/menschVsMensch/start";
                jsonObject.put("player1Name", computerBattleTextfield.getText());
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlAsString))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .build();

            HttpResponse<?> response = null;
            ComputerPlayer computerPlayer = null;
            String onlinePlayerName = "---";

            try {
                response = client.send(request,HttpResponse.BodyHandlers.ofString());

                String body = (String) response.body();
                System.out.println(body);
                JSONObject jsonResponseObject = new JSONObject(body);

                String uuid;

                if (response.statusCode() == 417){

                }

                //join
                if (startOnlineGameSwitchButton.getState()){
                    uuid = jsonResponseObject.getString("player2Uuid");
                    computerColor = STONECOLOR.valueOf(jsonResponseObject.getString("player2Color"));
                    onlinePlayerColor = computerColor == STONECOLOR.BLACK ? STONECOLOR.WHITE : STONECOLOR.BLACK;
                    onlinePlayerName = jsonResponseObject.getString("player1Name");
                }
                //start
                else {
                    uuid = jsonResponseObject.getString("player1Uuid");
                }

                computerPlayer = new ComputerPlayer(viewManager, computerBattleTextfield.getText().toUpperCase(), uuid);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e){
                onlineInformationLabel.setText("Ungültiger Gamecode");
            }

            System.out.println(response.statusCode());

            if (response.statusCode() == 200){

                viewManager.getSoundManager().chooseSound(MUSIC.PLAY_SOUND);
                if (!viewManager.getOptionsView().getAudioOnOffSwitchButton().getState()){
                    viewManager.getSoundManager().stopMusic();
                }

                Game game;

                //join
                if (startOnlineGameSwitchButton.getState()){
                    game = new Game(viewManager, new HumanPlayer(viewManager, onlinePlayerName, false), computerPlayer, gameCodeTextfield.getText(), startOnlineGameSwitchButton.getState());
                    viewManager.setGame(game);
                    viewManager.createGameScene(new FieldView(viewManager, onlinePlayerColor, computerColor, false),
                            new ScoreView(viewManager, onlinePlayerColor, computerColor),
                            new LogView(viewManager, true));
                }
                //start
                else {
                    game = new Game(viewManager, computerPlayer, new HumanPlayer(viewManager, onlinePlayerName, false), gameCodeTextfield.getText(), startOnlineGameSwitchButton.getState());
                    viewManager.setGame(game);
                    viewManager.createGameScene(new FieldView(viewManager, computerColor, onlinePlayerColor, false),
                            new ScoreView(viewManager, computerColor, onlinePlayerColor),
                            new LogView(viewManager, true));
                }

                viewManager.changeToGameScene();
                viewManager.getLogView().setStatusLabel(viewManager.getGame().getPlayer0().getName() + " startet das Spiel");
                viewManager.getScoreView().setGameCodeLabel(viewManager.getGame().getGameCode());

                try {
                    URI uri = new URI("ws://" + ipAdress + ":" + port + "/board");
                    WebsocketClient websocketClient = new WebsocketClient(uri, viewManager);
                    websocketClient.connect();
                    game.setWebsocketClient(websocketClient);

                } catch (URISyntaxException e) {
                    e.printStackTrace();}

            }


        });
    }
}