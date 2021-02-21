package edu.andreasgut.view;

import edu.andreasgut.game.Game;
import edu.andreasgut.game.InvalidFieldException;
import edu.andreasgut.game.Player;
import edu.andreasgut.sound.MUSIC;
import edu.andreasgut.view.fxElements.ToggleSwitch;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartMenuView extends VBox {

    private final int STARTDIMENSION = 600;
    ViewManager viewManager;
    VBox vBox;
    HBox hBoxRadioButtons, player1HBox, player2HBox;
    ToggleGroup radioButtonGroup;
    RadioButton onePlayerRadioButton, twoPlayersRadioButton;
    TextField namePlayer1Textfield, namePlayer2Textfield;
    Label informationLabel, titleLabel, stonesColorLabel1, stonesColorLabel2;
    Button startButton, blackWhiteButton1, blackWhiteButton2;
    ToggleSwitch whiteBlackToggleSwitch1, whiteBlackToggleSwitch2;


    public StartMenuView(ViewManager viewManager) {
        this.setPrefWidth(STARTDIMENSION);
        vBox = new VBox();
        hBoxRadioButtons = new HBox();
        titleLabel = new Label("Neues Spiel starten");
        titleLabel.getStyleClass().add("labelTitle");
        informationLabel = new Label();
        informationLabel.getStyleClass().add("labelWarning");
        radioButtonGroup = new ToggleGroup();
        onePlayerRadioButton = new RadioButton("Ein Spieler");
        onePlayerRadioButton.setToggleGroup(radioButtonGroup);
        twoPlayersRadioButton = new RadioButton("Zwei Spieler");
        twoPlayersRadioButton.setToggleGroup(radioButtonGroup);
        hBoxRadioButtons.getChildren().addAll(onePlayerRadioButton, twoPlayersRadioButton);
        hBoxRadioButtons.setSpacing(20);
        radioButtonGroup.selectToggle(onePlayerRadioButton);
        startButton = new Button("Start");

        namePlayer1Textfield = new TextField();
        namePlayer1Textfield.setPromptText("Name Spieler 1");
        whiteBlackToggleSwitch1 = new ToggleSwitch();
        stonesColorLabel1 = new Label("Steinfarbe: ");
        player1HBox = new HBox();
        player1HBox.getChildren().addAll(namePlayer1Textfield, stonesColorLabel1, whiteBlackToggleSwitch1);
        player1HBox.setSpacing(20);
        player1HBox.setAlignment(Pos.CENTER_LEFT);

        namePlayer2Textfield = new TextField();
        namePlayer2Textfield.setPromptText("Name Spieler 2");
        namePlayer2Textfield.setDisable(true);
        whiteBlackToggleSwitch2 = new ToggleSwitch();
        whiteBlackToggleSwitch2.setSwitchedOn(true);
        whiteBlackToggleSwitch2.setDisable(true);
        stonesColorLabel2 = new Label("Steinfarbe: ");
        player2HBox = new HBox();
        player2HBox.getChildren().addAll(namePlayer2Textfield, stonesColorLabel2, whiteBlackToggleSwitch2);
        player2HBox.setSpacing(20);
        player2HBox.setAlignment(Pos.CENTER_LEFT);
        stonesColorLabel2 = new Label("Steinfarbe: ");

        vBox.getChildren().addAll(titleLabel, informationLabel, hBoxRadioButtons, player1HBox, player2HBox, startButton);
        vBox.setSpacing(20);
        this.getChildren().addAll(vBox);
        this.setAlignment(Pos.CENTER);

        whiteBlackToggleSwitch1.setSwitchedOn(whiteBlackToggleSwitch2.isSwitchedOn());

        onePlayerRadioButton.setOnAction(action -> {
                namePlayer2Textfield.setDisable(true);
                namePlayer2Textfield.clear();
                whiteBlackToggleSwitch2.setDisable(true);});
        twoPlayersRadioButton.setOnAction(action -> {
                startButton.setDisable(false);
                namePlayer2Textfield.setDisable(false);
                whiteBlackToggleSwitch2.setDisable(false);});

        startButton.setOnAction( action -> {

            if ((radioButtonGroup.getSelectedToggle().equals(onePlayerRadioButton) &&
                    namePlayer1Textfield.getText().length()>0)
                    || (radioButtonGroup.getSelectedToggle().equals(twoPlayersRadioButton) &&
                    namePlayer1Textfield.getText().length()>0 && namePlayer2Textfield.getText().length()>0)){
            viewManager.changeToGameScene();

            viewManager.getSoundManager().chooseSound(MUSIC.PLAY_SOUND);
            if (!viewManager.getOptionsView().isMusicOn()){
                viewManager.getSoundManager().stopMusic();
            }


                if (twoPlayersRadioButton.isSelected()){
                    viewManager.setGame(new Game(viewManager,
                            new Player(namePlayer1Textfield.getText().toUpperCase()),
                            new Player(namePlayer2Textfield.getText().toUpperCase())));
                }
                else {
                    viewManager.setGame(new Game(viewManager,
                            new Player(namePlayer1Textfield.getText().toUpperCase())));
                }

            viewManager.getScoreView().updatePlayerNames(viewManager.getGame().getPlayer0(),
                        viewManager.getGame().getPlayer1());

            try {
                viewManager.getGame().play();
            } catch (InvalidFieldException e) {
                e.printStackTrace();
            }

            }
            else {informationLabel.setText("Es fehlen Eingaben, um das Spiel zu starten");}});

}}