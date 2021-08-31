package edu.andreasgut.view;

import edu.andreasgut.game.ComputerPlayer;
import edu.andreasgut.game.Game;
import edu.andreasgut.game.HumanPlayer;
import edu.andreasgut.game.OnlinePlayer;
import edu.andreasgut.online.WebsocketClient;
import edu.andreasgut.sound.MUSIC;
import edu.andreasgut.view.fxElements.BeginnerSwitchButton;
import edu.andreasgut.view.fxElements.SelectColorButton;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public class StartMenuView extends VBox {

    private final int STARTDIMENSION = 600;
    ViewManager viewManager;
    VBox offlineVBox, onlineVBox;
    HBox hBoxRadioButtons, player1HBox, player2HBox, beginnerHBox, startGameHBox, computerBattleHBox;
    ToggleGroup radioButtonGroup;
    RadioButton onePlayerRadioButton, twoPlayersRadioButton;
    TextField namePlayer1Textfield, namePlayer2Textfield, computerBattleTextfield, gameCodeTextfield;
    Label offlineInformationLabel, offlineTitleLabel, onlineInformationLabel, onlineTitleLabel, stonesColorLabel1, stonesColorLabel2, beginnerLabel1, beginnerLabel2, stoneColorComputerLabel, startGameLabel, joinGameLabel;
    Button startButton, computerOnlineButton;
    SelectColorButton stonesBlackButton1, stonesWhiteButton1, stonesBlackButton2, stonesWhiteButton2, computerBlackButton, computerWhiteButton;
    BeginnerSwitchButton beginnerSwitchButton, startGameSwitchButton;
    ImageView player1StonesImageView, player2StonesImageView;
    STONECOLOR player1Color, player2Color;



    public StartMenuView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.setPrefWidth(STARTDIMENSION);
        offlineVBox = new VBox();
        onlineVBox = new VBox();
        startButton = new Button("Start");
        computerOnlineButton = new Button("Computerbattle starten");
        gameCodeTextfield = new TextField();
        gameCodeTextfield.setPromptText("Gamecode");


        setupOfflineTitleAndWarning();
        setupRadioButtons();
        setupBeginnerSwitch();
        setupPlayerInformations();
        setupOnlineTitleAndWarning();
        setupStartGameSwitchButton();
        setupComputerBattleInformation();

        offlineVBox.getChildren().addAll(offlineTitleLabel, hBoxRadioButtons, player1HBox, beginnerHBox, startButton, offlineInformationLabel);
        offlineVBox.setSpacing(20);
        offlineVBox.setStyle("-fx-padding: 5 0 0 0");
        onlineVBox.getChildren().addAll(onlineTitleLabel, gameCodeTextfield, startGameHBox, computerBattleHBox, computerOnlineButton, onlineInformationLabel);
        onlineVBox.setSpacing(20);
        this.getChildren().addAll(onlineVBox, offlineVBox);
        this.setAlignment(Pos.CENTER);

        setupRadioButtonAction();
        setupColorButtonAction();
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
        beginnerLabel1 = new Label("Spieler 1 beginnt");
        beginnerLabel2 = new Label("Computer beginnt");
        beginnerHBox.getChildren().addAll(beginnerLabel1, beginnerSwitchButton, beginnerLabel2);
        beginnerHBox.setAlignment(Pos.CENTER_LEFT);
        beginnerHBox.setSpacing(10);
        beginnerHBox.setPrefHeight(70);

    }

    private void setupStartGameSwitchButton(){
        startGameHBox = new HBox();
        startGameSwitchButton = new BeginnerSwitchButton(viewManager);
        startGameLabel = new Label("Spiel eröffnen");
        joinGameLabel = new Label("Einem Spiel beitreten");
        startGameHBox.getChildren().addAll(startGameLabel, startGameSwitchButton, joinGameLabel);
        startGameHBox.setAlignment(Pos.CENTER_LEFT);
        startGameHBox.setSpacing(10);
        startGameHBox.setPrefHeight(70);
    }

    private void setupComputerBattleInformation(){
        computerBattleTextfield = new TextField();
        computerBattleTextfield.setPromptText("Name des Computers");
        computerBlackButton = new SelectColorButton(null, STONECOLOR.BLACK, true);
        computerWhiteButton = new SelectColorButton(null, STONECOLOR.WHITE, false);
        stoneColorComputerLabel = new Label("Steinfarbe: ");

        computerBattleHBox = new HBox();
        computerBattleHBox.getChildren().addAll(computerBattleTextfield, stoneColorComputerLabel, computerBlackButton, computerWhiteButton);
        computerBattleHBox.setSpacing(20);
        computerBattleHBox.setAlignment(Pos.CENTER_LEFT);

    }

    private void setupPlayerInformations(){
        namePlayer1Textfield = new TextField();
        namePlayer1Textfield.setPromptText("Name Spieler 1");
        stonesColorLabel1 = new Label("Steinfarbe: ");
        stonesBlackButton1 = new SelectColorButton(null, STONECOLOR.BLACK, true);
        stonesWhiteButton1 = new SelectColorButton(null, STONECOLOR.WHITE, false);
        player1Color = STONECOLOR.BLACK;

        player1HBox = new HBox();
        player1HBox.getChildren().addAll(namePlayer1Textfield, stonesColorLabel1, stonesBlackButton1, stonesWhiteButton1);
        player1HBox.setSpacing(20);
        player1HBox.setAlignment(Pos.CENTER_LEFT);

        namePlayer2Textfield = new TextField();
        namePlayer2Textfield.setPromptText("Name Spieler 2");
        namePlayer2Textfield.setVisible(true);
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
            offlineVBox.getChildren().add(3, beginnerHBox);
            offlineVBox.getChildren().remove(player2HBox);

        });
        twoPlayersRadioButton.setOnAction(action -> {
            offlineVBox.getChildren().remove(beginnerHBox);
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
                            new HumanPlayer(viewManager, namePlayer1Textfield.getText().toUpperCase()),
                            new HumanPlayer(viewManager, namePlayer2Textfield.getText().toUpperCase())));
                }
                else {
                    viewManager.setGame(new Game(viewManager,
                            new HumanPlayer(viewManager, namePlayer1Textfield.getText().toUpperCase()),
                            beginnerSwitchButton.getState()));
                }


                viewManager.createGameScene(new FieldView(viewManager, player1Color, player2Color, true),
                        new ScoreView(viewManager, player1Color, player2Color),
                        new LogView(viewManager, false));

                /*viewManager.getScoreView().updatePlayerNames(viewManager.getGame().getPlayer0(),
                        viewManager.getGame().getPlayer1());*/

                viewManager.changeToGameScene();





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
                return;
            }

            if (computerBattleTextfield.getText().length() == 0){
                onlineInformationLabel.setText("Der Computername fehlt");
                return;
            }

            STONECOLOR computerColor;
            if (computerBlackButton.isSelected()){
                computerColor = STONECOLOR.BLACK;
            }
            else {
                computerColor = STONECOLOR.WHITE;
            }

            jsonObject.put("player1Color", computerColor.toString());

            String urlAsString;
            if (startGameSwitchButton.getState()){
                urlAsString = "http://localhost:8080/index/controller/menschVsMensch/join";
                jsonObject.put("player2Name", computerBattleTextfield.getText());

            }
            else {
                urlAsString = "http://localhost:8080/index/controller/menschVsMensch/start";
                jsonObject.put("player1Name", computerBattleTextfield.getText());
            }



            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlAsString))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .build();

            HttpResponse<?> response = null;
            ComputerPlayer computerPlayer = null;

            try {
                response = client.send(request,HttpResponse.BodyHandlers.ofString());

                String body = (String) response.body();
                JSONObject jsonResponseObject = new JSONObject(body);
                String uuid = jsonResponseObject.getString("player1Uuid");
                System.out.println(uuid);

                computerPlayer = new ComputerPlayer(viewManager, computerBattleTextfield.getText().toUpperCase(), uuid);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(response.statusCode());

            if (response.statusCode() == 200){



                viewManager.getSoundManager().chooseSound(MUSIC.PLAY_SOUND);
                if (!viewManager.getOptionsView().getAudioOnOffSwitchButton().getState()){
                    viewManager.getSoundManager().stopMusic();
                }

                viewManager.setGame(new Game(viewManager,
                        computerPlayer, new OnlinePlayer(viewManager, "Onlineplayer"), computerBattleTextfield.getText()));



                viewManager.createGameScene(new FieldView(viewManager, player1Color, player2Color, false),
                        new ScoreView(viewManager, player1Color, player2Color),
                        new LogView(viewManager, true));

                viewManager.changeToGameScene();




                try {
                    URI uri = new URI("ws://localhost:8080/board");
                    WebsocketClient websocketClient = new WebsocketClient(uri, viewManager);
                    websocketClient.connect();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


            }
            else {
                if (startGameSwitchButton.getState()){
                    onlineInformationLabel.setText("Dieses Game existiert noch nicht. Kontrollieren Sie den Gamecode.");
                }
                else {
                    onlineInformationLabel.setText("Der Gamecode existiert bereits. Wählen Sie einen anderen Gamecode.");
                }
            }

        });
    }
}