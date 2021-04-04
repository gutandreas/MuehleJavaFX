package edu.andreasgut.view;

import edu.andreasgut.game.Game;
import edu.andreasgut.game.HumanPlayer;
import edu.andreasgut.game.InvalidPutException;
import edu.andreasgut.sound.MUSIC;
import edu.andreasgut.view.fxElements.BeginnerSwitchButton;
import edu.andreasgut.view.fxElements.SelectColorButton;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartMenuView extends VBox {

    private final int STARTDIMENSION = 600;
    ViewManager viewManager;
    VBox vBox;
    HBox hBoxRadioButtons, player1HBox, player2HBox, beginnerHBox;
    ToggleGroup radioButtonGroup;
    RadioButton onePlayerRadioButton, twoPlayersRadioButton;
    TextField namePlayer1Textfield, namePlayer2Textfield;
    Label informationLabel, titleLabel, stonesColorLabel1, stonesColorLabel2, beginnerLabel1, beginnerLabel2;
    Button startButton;
    SelectColorButton stonesBlackButton1, stonesWhiteButton1, stonesBlackButton2, stonesWhiteButton2;
    BeginnerSwitchButton beginnerSwitchButton;
    ImageView player1StonesImageView, player2StonesImageView;
    STONECOLOR player1Color, player2Color;



    public StartMenuView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.setPrefWidth(STARTDIMENSION);
        vBox = new VBox();
        startButton = new Button("Start");

        setupTitleAndWarning();
        setupRadioButtons();
        setupBeginnerSwitch();
        setupPlayerInformations();

        vBox.getChildren().addAll(titleLabel, informationLabel, hBoxRadioButtons, beginnerHBox, player1HBox, player2HBox, startButton);
        vBox.setSpacing(20);
        this.getChildren().addAll(vBox);
        this.setAlignment(Pos.CENTER);

        setupRadioButtonAction();
        setupColorButtonAction();
        setupStartButtonAction();
    }

    private void setupTitleAndWarning(){
        titleLabel = new Label("Neues Spiel starten");
        titleLabel.getStyleClass().add("labelTitle");
        informationLabel = new Label();
        informationLabel.getStyleClass().add("labelWarning");
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
        beginnerLabel2 = new Label("Spieler 2 beginnt");
        beginnerHBox.getChildren().addAll(beginnerLabel1, beginnerSwitchButton, beginnerLabel2);
        beginnerHBox.setAlignment(Pos.CENTER_LEFT);
        beginnerHBox.setSpacing(10);

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
        namePlayer2Textfield.setVisible(false);
        stonesColorLabel2 = new Label("Steinfarbe: ");
        stonesColorLabel2.setVisible(false);
        stonesBlackButton2 = new SelectColorButton(null, STONECOLOR.BLACK, false);
        stonesBlackButton2.setVisible(false);
        stonesWhiteButton2 = new SelectColorButton(null, STONECOLOR.WHITE, true);
        stonesWhiteButton2.setVisible(false);
        player2Color = STONECOLOR.WHITE;

        player2HBox = new HBox();
        player2HBox.getChildren().addAll(namePlayer2Textfield, stonesColorLabel2, stonesBlackButton2, stonesWhiteButton2);
        player2HBox.setSpacing(20);
        player2HBox.setAlignment(Pos.CENTER_LEFT);
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

    private void setupRadioButtonAction(){
        onePlayerRadioButton.setOnAction(action -> {
            namePlayer2Textfield.setVisible(false);
            namePlayer2Textfield.clear();
            stonesColorLabel2.setVisible(false);
            stonesBlackButton2.setVisible(false);
            stonesWhiteButton2.setVisible(false);
        });
        twoPlayersRadioButton.setOnAction(action -> {
            namePlayer2Textfield.setVisible(true);
            stonesColorLabel2.setVisible(true);
            stonesBlackButton2.setVisible(true);
            stonesWhiteButton2.setVisible(true);
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
                            new HumanPlayer(viewManager, namePlayer1Textfield.getText().toUpperCase())));
                }

                viewManager.createGameScene(new FieldView(viewManager, player1Color, player2Color),
                        new ScoreView(viewManager, player1Color, player2Color),
                        new LogView(viewManager));

                /*viewManager.getScoreView().updatePlayerNames(viewManager.getGame().getPlayer0(),
                        viewManager.getGame().getPlayer1());*/

                viewManager.changeToGameScene();


                viewManager.getGame().play();



            }
            else {informationLabel.setText("Es fehlen Eingaben, um das Spiel zu starten");}});
    }
}