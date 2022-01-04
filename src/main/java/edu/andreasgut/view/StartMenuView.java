package edu.andreasgut.view;

import edu.andreasgut.game.*;
import edu.andreasgut.online.WebsocketClient;
import edu.andreasgut.sound.MUSIC;
import edu.andreasgut.view.fxElements.SwitchButton;
import edu.andreasgut.view.fxElements.SelectColorButton;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class StartMenuView extends VBox {


    /*String ipAdress = "217.160.10.113";
    String port = "443";*/

    String ipAdress = "localhost";
    String port = "443";

    private final int STARTDIMENSION = 600;
    private int startRound = 0;
    ViewManager viewManager;
    VBox offlineVBox, onlineVBox, scoreVBoxComputerPut, scoreVBoxComputerMove, scoreVBoxEnemyPut, scoreVBoxEnemyMove;
    HBox hBoxRadioButtons, player1HBox, player2HBox, computerHBox, beginnerHBox, startGameHBox, computerBattleHBox, computerSettingBox, putPhaseHBox, movePhaseHBox, onlineButtonHBox, offlineButtonHBox, onlineSettingsHBox;
    ToggleGroup radioButtonGroup;
    RadioButton onePlayerRadioButton, twoPlayersRadioButton;
    TextField namePlayer1Textfield, namePlayer2Textfield, computerBattleTextfield, gameCodeTextfield, roundTextField;
    Label offlineInformationLabel, offlineTitleLabel, onlineInformationLabel, onlineTitleLabel, stonesColorLabel1, stonesColorLabel2, beginnerLabel1, beginnerLabel2, offlineLevelLabel, onlineLevelLabel, stoneColorComputerLabel, startGameLabel, joinGameLabel, ownComputerLabel, defaultComputerLabel;
    Button startButton, computerOnlineButton, offlineScorePointsButton, onlineScorePointsButton, startingPositionButton;
    SelectColorButton stonesBlackButton1, stonesWhiteButton1, stonesBlackButton2, stonesWhiteButton2, computerBlackButton, computerWhiteButton;
    SwitchButton beginnerSwitchButton, startOnlineGameSwitchButton, ownComputerPlayerSwitchButton;
    ImageView player1StonesImageView, player2StonesImageView;
    FieldViewStartingPosition startingPositionFieldView;
    STONECOLOR player1Color, player2Color;
    ChoiceBox offlineComputerLevelChoiceBox, onlineComputerLevelChoiceBox;
    PopOver scorePopOver, startingPositionPopOver;

    ScorePoints putPoints = new ScorePoints(3000, 1000,30, 200, 300,6, -3000, -1000, -30, -200, -300, -6);
    ScorePoints movePoints = new ScorePoints(2000, 300,250, 200, 300,3, -2000, -300, -250, -200, -300, -3);





    public StartMenuView(ViewManager viewManager) {



        this.viewManager = viewManager;
        this.setPrefWidth(STARTDIMENSION);
        offlineVBox = new VBox();
        onlineVBox = new VBox();
        startButton = new Button("Offlinespiel starten");
        startButton.setPrefWidth(185);
        computerOnlineButton = new Button("Computerbattle starten");
        computerOnlineButton.setPrefWidth(185);



        setupOfflineTitleAndWarning();
        setupOnlineSettings();
        setupRadioButtonsAndStartingPosition();
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
        onlineVBox.getChildren().addAll(onlineTitleLabel, onlineSettingsHBox, startGameHBox, computerBattleHBox, onlineButtonHBox);
        onlineVBox.setSpacing(20);
        this.getChildren().addAll(onlineVBox, offlineVBox);
        this.setAlignment(Pos.CENTER);

        setupRadioButtonAction();
        setupColorButtonAction();
        setupStartOnlineGameSwitchButton();
        setupStartButtonAction();
        setupComputerOnlineButtonAction();
        setupComputerBattleColorButtonAction();
        setupOnlineScorePointsButton();
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

    private void setupOnlineSettings(){

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

        onlineSettingsHBox = new HBox();
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

        ownComputerPlayerSwitchButton = new SwitchButton(viewManager);
        ownComputerLabel = new Label("Eigener Player");
        defaultComputerLabel = new Label("Standard");
        onlineLevelLabel = new Label("   Level: ");
        onlineComputerLevelChoiceBox = new ChoiceBox(FXCollections.observableArrayList("1","2","3"));
        onlineComputerLevelChoiceBox.getSelectionModel().select(2);
        onlineScorePointsButton = new Button("Score");



        onlineSettingsHBox.getChildren().addAll(gameCodeTextfield, ownComputerLabel, ownComputerPlayerSwitchButton, defaultComputerLabel, onlineLevelLabel, onlineComputerLevelChoiceBox, onlineScorePointsButton);
        onlineSettingsHBox.setAlignment(Pos.CENTER_LEFT);
        onlineSettingsHBox.setSpacing(10);
    }

    private void setupRadioButtonsAndStartingPosition(){
        hBoxRadioButtons = new HBox();
        radioButtonGroup = new ToggleGroup();
        onePlayerRadioButton = new RadioButton("Ein Spieler");
        onePlayerRadioButton.setToggleGroup(radioButtonGroup);
        twoPlayersRadioButton = new RadioButton("Zwei Spieler");
        twoPlayersRadioButton.setToggleGroup(radioButtonGroup);
        startingPositionButton = new Button("Ausgangslage");
        hBoxRadioButtons.getChildren().addAll(onePlayerRadioButton, twoPlayersRadioButton, startingPositionButton);
        hBoxRadioButtons.setSpacing(20);
        hBoxRadioButtons.setAlignment(Pos.CENTER_LEFT);
        radioButtonGroup.selectToggle(onePlayerRadioButton);

        startingPositionPopOver = new PopOver();
        Group root = new Group();
        HBox mainHBox = new HBox();
        startingPositionFieldView = new FieldViewStartingPosition(viewManager, STONECOLOR.BLACK, STONECOLOR.WHITE);



        VBox startingVBox = new VBox();
        HBox roundHBox = new HBox();
        roundHBox.setSpacing(10);
        roundHBox.setAlignment(Pos.CENTER_LEFT);
        Label roundLabel = new Label("Runde:");
        roundTextField = new TextField();
        roundTextField.setPromptText("0");
        roundTextField.getStyleClass().add("smallTextField");
        Pattern roundPattern = Pattern.compile("[0-9]?[0-9]?[0-9]?");
        TextFormatter<?> formatterRound = new TextFormatter<Object>(change -> {
            if (roundPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                //Verbotene Zeichen
                return null;
            }
        });
        HBox choiceHBox = new HBox();
        choiceHBox.setSpacing(10);
        choiceHBox.setAlignment(Pos.CENTER_LEFT);
        Label szenarioLabel = new Label("Szenario: ");
        ChoiceBox choiceBox = new ChoiceBox();
        Button loadButton = new Button("Laden");
        choiceBox.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        choiceBox.getSelectionModel().select(0);
        Label situationDescriptionLabel = new Label("Leeres Spielfeld");
        situationDescriptionLabel.setWrapText(true);


        loadButton.setOnAction(click -> {
            System.out.println(choiceBox.getValue());
            StartSituation startSituation = StartSituation.produceStartSituations()[Integer.parseInt(choiceBox.getValue().toString())];
            startingPositionFieldView.setBoard(startSituation.getBoard());
            roundTextField.setText("" + startSituation.getRound());
            situationDescriptionLabel.setText(startSituation.getDescription());

            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 8; j++){
                    if (startSituation.getBoard().getNumberOnPosition(i,j) != 9){
                        startingPositionFieldView.graphicPut(new Position(i,j), startSituation.getBoard().getNumberOnPosition(i,j), 0, false);}
                    else {
                        startingPositionFieldView.graphicKill(new Position(i,j), false);
                    }
                }
            }
        });





        choiceHBox.getChildren().addAll(szenarioLabel, choiceBox, loadButton);
        roundTextField.setTextFormatter(formatterRound);

        roundHBox.getChildren().addAll(roundLabel, roundTextField);
        startingVBox.getChildren().addAll(roundHBox, choiceHBox, situationDescriptionLabel);
        startingVBox.getStyleClass().add("startingVBox");
        mainHBox.getChildren().addAll(startingPositionFieldView, startingVBox);


        root.getChildren().addAll(mainHBox);
        startingPositionPopOver.setContentNode(root);
        mainHBox.getStyleClass().add("startingPositionPopOver");
        startingPositionButton.setOnAction(click -> {
            startingPositionPopOver.setAutoHide(true);
            startingPositionPopOver.setDetachedTitle("Startfeld einrichten:");
            startingPositionPopOver.setAutoFix(true);
            startingPositionPopOver.setHideOnEscape(true);
            startingPositionPopOver.setDetached(true);
            startingPositionPopOver.setArrowSize(0);
            startingPositionPopOver.show(viewManager.getMainStage());
            startingPositionFieldView.updateStoneColors();
        });
    }



    private void setupBeginnerSwitch(){
        beginnerHBox = new HBox();
        beginnerSwitchButton = new SwitchButton(viewManager);
        beginnerLabel1 = new Label("Beginner: Spieler 1");
        beginnerLabel2 = new Label("Computer");
        beginnerHBox.getChildren().addAll(beginnerLabel1, beginnerSwitchButton, beginnerLabel2);
        beginnerHBox.setAlignment(Pos.CENTER_LEFT);
        beginnerHBox.setSpacing(10);
        beginnerHBox.setPrefHeight(70);

    }

    private void setupComputerSettingBox(){
        computerSettingBox = new HBox();
        offlineScorePointsButton = new Button("Score");
        offlineComputerLevelChoiceBox = new ChoiceBox(FXCollections.observableArrayList("1","2","3"));
        offlineComputerLevelChoiceBox.getSelectionModel().select(2);
        offlineLevelLabel = new Label("Level: ");
        computerSettingBox.getChildren().addAll(offlineLevelLabel, offlineComputerLevelChoiceBox, offlineScorePointsButton);
        computerSettingBox.getStyleClass().add("computerSettingBox");

        scorePopOver = new PopOver();
        Group root = new Group();
        HBox mainHBox = new HBox();
        putPhaseHBox = new HBox();
        movePhaseHBox = new HBox();
        mainHBox.getStyleClass().add("scorePopOver");

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

            Pattern scorePattern = Pattern.compile("-?[0-9]*");
            TextFormatter<?> formatterComputerPut = new TextFormatter<Object>(change -> {
                if (scorePattern.matcher(change.getControlNewText()).matches()) {
                    return change;
                } else {
                    //Verbotene Zeichen
                    return null;
                }
            });
            TextFormatter<?> formatterEnemyPut = new TextFormatter<Object>(change -> {
                if (scorePattern.matcher(change.getControlNewText()).matches()) {
                    return change;
                } else {
                    //Verbotene Zeichen
                    return null;
                }
            });
            TextFormatter<?> formatterComputerMove = new TextFormatter<Object>(change -> {
                if (scorePattern.matcher(change.getControlNewText()).matches()) {
                    return change;
                } else {
                    //Verbotene Zeichen
                    return null;
                }
            });
            TextFormatter<?> formatterEnemyMove = new TextFormatter<Object>(change -> {
                if (scorePattern.matcher(change.getControlNewText()).matches()) {
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
                        if (textFieldComputerPut.getText().length() > 6) {
                            String s = textFieldComputerPut.getText().substring(0, 6);
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
                    });

            scoreVBoxEnemyPut.getChildren().addAll(labelEnemyPut, textFieldEnemyPut);

            Label labelComputerMove = new Label(labelTitles[i]);
            TextField textFieldComputerMove = new TextField();
            textFieldComputerMove.setTextFormatter(formatterComputerMove);
            textFieldComputerMove.setPromptText(String.valueOf(movePoints.getValueByIndex(i)));
            textFieldComputerMove.textProperty().addListener(change -> {
                        if (textFieldComputerMove.getText().length() > 6) {
                            String s = textFieldComputerMove.getText().substring(0, 6);
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
                    });
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
        scorePopOver.setContentNode(root);

        offlineScorePointsButton.setOnAction(click -> {

            scorePopOver.setAutoHide(true);
            scorePopOver.setDetachedTitle("Punkte für Ereignisse setzen:");
            scorePopOver.setAutoFix(true);
            scorePopOver.setHideOnEscape(true);
            scorePopOver.setDetached(true);
            scorePopOver.setArrowSize(0);
            scorePopOver.show(viewManager.getMainStage());
        });

    }

    private void setupStartGameSwitchButton(){
        startGameHBox = new HBox();
        startOnlineGameSwitchButton = new SwitchButton(viewManager);
        startGameLabel = new Label("Spiel eröffnen");
        joinGameLabel = new Label("beitreten");
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
            startingPositionFieldView.setStoneColors(STONECOLOR.BLACK);

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
            startingPositionFieldView.setStoneColors(STONECOLOR.WHITE);

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
            startingPositionFieldView.setStoneColors(STONECOLOR.WHITE);
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
            startingPositionFieldView.setStoneColors(STONECOLOR.BLACK);
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



    private void setupOnlineScorePointsButton(){
        onlineScorePointsButton.setOnAction(click -> {
            scorePopOver.setAutoHide(true);
            scorePopOver.setDetachedTitle("Punkte für Ereignisse setzen:");
            scorePopOver.setAutoFix(true);
            scorePopOver.setHideOnEscape(true);
            scorePopOver.setDetached(true);
            scorePopOver.setArrowSize(0);
            scorePopOver.show(viewManager.getMainStage());
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
        startButton.setOnAction(action -> {

            int tempRound;
            if (roundTextField.getText().length() > 0){
                tempRound = Integer.parseInt(roundTextField.getText());
            }
            else {
                tempRound = 0;
            }
            System.out.println("Startrunde: " + tempRound);


            //Ungültige Ausgangslage abfangen
            boolean lessThan3StonesInMovePhase = tempRound > 18
                    && (startingPositionFieldView.getBoard().countPlayersStones(0) < 3
                    || startingPositionFieldView.getBoard().countPlayersStones(1) < 3);

            boolean lessThan3StonesAfterPutPhase = tempRound <= 18
                    && (startingPositionFieldView.getBoard().countPlayersStones(0) + (18-tempRound)/2 < 3
                    || startingPositionFieldView.getBoard().countPlayersStones(1) + (18-tempRound)/2 < 3);

            boolean moreThan9Stones = startingPositionFieldView.getBoard().countPlayersStones(0) > 9
                    || startingPositionFieldView.getBoard().countPlayersStones(1) > 9;

            boolean moreThan9StonesAfterPutPhase = tempRound <= 18
                    && (startingPositionFieldView.getBoard().countPlayersStones(0) + Math.round(((double) 18-tempRound)/2) > 9
                    || startingPositionFieldView.getBoard().countPlayersStones(1) + Math.round(((double) 18-tempRound)/2) > 9);


            if (lessThan3StonesInMovePhase || lessThan3StonesAfterPutPhase || moreThan9Stones || moreThan9StonesAfterPutPhase){
                offlineInformationLabel.setText("Ungültige Ausgangslage");
                return;
            }


            if ((radioButtonGroup.getSelectedToggle().equals(onePlayerRadioButton) &&
                    namePlayer1Textfield.getText().length()>0)
                    || (radioButtonGroup.getSelectedToggle().equals(twoPlayersRadioButton) &&
                    namePlayer1Textfield.getText().length()>0 && namePlayer2Textfield.getText().length()>0)){

                viewManager.getAudioPlayer().chooseSound(MUSIC.PLAY_SOUND);
                if (!viewManager.getOptionsView().getAudioOnOffSwitchButton().getState()){
                    viewManager.getAudioPlayer().stopMusic();
                }


                if (twoPlayersRadioButton.isSelected()){
                    viewManager.setGame(new Game(viewManager,
                            new HumanPlayer(viewManager, namePlayer1Textfield.getText().toUpperCase(), true),
                            new HumanPlayer(viewManager, namePlayer2Textfield.getText().toUpperCase(), true),
                            startingPositionFieldView.getBoard(),
                            tempRound));
                }
                else {

                    editScorePoints();

                    viewManager.setGame(new Game(viewManager,
                            new HumanPlayer(viewManager, namePlayer1Textfield.getText().toUpperCase(), true),
                            beginnerSwitchButton.getState(), putPoints, movePoints, Integer.parseInt(offlineComputerLevelChoiceBox.getValue().toString()),
                            startingPositionFieldView.getBoard(), tempRound));
                }


                viewManager.createGameScene(new FieldViewPlay(viewManager, player1Color, player2Color, true),
                        new ScoreView(viewManager, player1Color, player2Color, viewManager.getGame().getRound()),
                        new LogView(viewManager, false));



                viewManager.changeToGameScene();
                viewManager.getOptionsView().enableRestartButton();
                viewManager.getMainMenuBar().enableNeuStarten();
                viewManager.getLogView().setStatusLabel(viewManager.getGame().getPlayer0().getName() + " startet das Spiel");


                if ((!beginnerSwitchButton.getState() && tempRound%2 == 0) || (beginnerSwitchButton.getState() && tempRound%2 == 1)) {
                    if (tempRound <= 18) {
                        ((FieldViewPlay) viewManager.getFieldView()).setPutCursor();
                    } else {
                        ((FieldViewPlay) viewManager.getFieldView()).setMoveCursor();
                    }
                }

                else {
                    viewManager.getGame().getCurrentPlayer().preparePutOrMove(viewManager);
                }



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

            editScorePoints();


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


                editScorePoints();
                //StandardComputerPlayer
                if (ownComputerPlayerSwitchButton.getState()){
                    computerPlayer = new StandardComputerPlayer(viewManager, computerBattleTextfield.getText().toUpperCase(), uuid, putPoints, movePoints, Integer.parseInt(onlineComputerLevelChoiceBox.getValue().toString()));
                }
                //CustomComputerPlayer
                else {
                    computerPlayer = new CustomComputerPlayer(viewManager, computerBattleTextfield.getText().toUpperCase(), uuid, putPoints, movePoints, Integer.parseInt(onlineComputerLevelChoiceBox.getValue().toString()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            System.out.println(response.statusCode());

            if (response.statusCode() == 417) {
                onlineInformationLabel.setText("Ungültiger Gamecode");
            }


            if (response.statusCode() == 200){

                viewManager.getAudioPlayer().chooseSound(MUSIC.PLAY_SOUND);
                if (!viewManager.getOptionsView().getAudioOnOffSwitchButton().getState()){
                    viewManager.getAudioPlayer().stopMusic();
                }

                Game game;

                //join
                if (startOnlineGameSwitchButton.getState()){
                    game = new Game(viewManager, new HumanPlayer(viewManager, onlinePlayerName, false), computerPlayer, gameCodeTextfield.getText(), startOnlineGameSwitchButton.getState());
                    viewManager.setGame(game);
                    viewManager.createGameScene(new FieldViewPlay(viewManager, onlinePlayerColor, computerColor, false),
                            new ScoreView(viewManager, onlinePlayerColor, computerColor, game.getRound()),
                            new LogView(viewManager, true));
                }
                //start
                else {
                    game = new Game(viewManager, computerPlayer, new HumanPlayer(viewManager, onlinePlayerName, false), gameCodeTextfield.getText(), startOnlineGameSwitchButton.getState());
                    viewManager.setGame(game);
                    viewManager.createGameScene(new FieldViewPlay(viewManager, computerColor, onlinePlayerColor, false),
                            new ScoreView(viewManager, computerColor, onlinePlayerColor, game.getRound()),
                            new LogView(viewManager, true));
                }

                viewManager.changeToGameScene();
                viewManager.getOptionsView().enableRestartButton();
                viewManager.getMainMenuBar().enableNeuStarten();
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




    private void editScorePoints(){
        int counterPut = 0;
        //computerPutpoints
        for (Node node : scoreVBoxComputerPut.getChildren()){
            if (node instanceof TextField){
                if (((TextField) node).getText().length()>0 && ((TextField) node).getText().charAt(((TextField) node).getText().length()-1) != '-'){
                    putPoints.setValueByIndex(counterPut, Integer.parseInt(((TextField) node).getText()));
                }
                counterPut++;
            }
        }

        //enemyPutpoints
        for (Node node : scoreVBoxEnemyPut.getChildren()){
            if (node instanceof TextField){

                if (((TextField) node).getText().length()>0 && ((TextField) node).getText().charAt(((TextField) node).getText().length()-1) != '-'){
                    putPoints.setValueByIndex(counterPut, Integer.parseInt(((TextField) node).getText()));
                }
                counterPut++;
            }
        }

        System.out.println(putPoints);

        int counterMove = 0;

        //computerMovePoints
        for (Node node : scoreVBoxComputerMove.getChildren()){
            if (node instanceof TextField){
                if (((TextField) node).getText().length()>0 && ((TextField) node).getText().charAt(((TextField) node).getText().length()-1) != '-'){
                    movePoints.setValueByIndex(counterMove, Integer.parseInt(((TextField) node).getText()));
                }
                counterMove++;
            }
        }

        //enemyMovePoints
        for (Node node : scoreVBoxEnemyMove.getChildren()){
            if (node instanceof TextField){
                if (((TextField) node).getText().length()>0 && ((TextField) node).getText().charAt(((TextField) node).getText().length()-1) != '-'){
                    movePoints.setValueByIndex(counterMove, Integer.parseInt(((TextField) node).getText()));
                }
                counterMove++;
            }
        }
        System.out.println(movePoints);
    }

}