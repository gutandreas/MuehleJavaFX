package edu.andreasgut.view;

import edu.andreasgut.game.Game;
import edu.andreasgut.game.InvalidFieldException;
import edu.andreasgut.game.Player;
import edu.andreasgut.sound.AUDIO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartMenuView extends VBox {

    private final int STARTDIMENSION = 600;
    ViewManager viewManager;
    VBox vBox;
    HBox hBoxRadioButtons;
    ToggleGroup radioButtonGroup;
    RadioButton onePlayerRadioButton, twoPlayersRadioButton;
    TextField namePlayer1Textfield, namePlayer2Textfield;
    Label informationLabel, titleLabel;
    Button startButton;


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
        namePlayer2Textfield = new TextField();
        namePlayer2Textfield.setPromptText("Name Spieler 2");
        namePlayer2Textfield.setDisable(true);
        vBox.getChildren().addAll(titleLabel, informationLabel, hBoxRadioButtons, namePlayer1Textfield,
                namePlayer2Textfield, startButton);
        vBox.setSpacing(20);
        this.getChildren().addAll(vBox);
        this.setAlignment(Pos.CENTER);

        onePlayerRadioButton.setOnAction(action -> {
                namePlayer2Textfield.setDisable(true);
                namePlayer2Textfield.clear();});
        twoPlayersRadioButton.setOnAction(action -> {
                startButton.setDisable(false);
                namePlayer2Textfield.setDisable(false);});
        startButton.setOnAction( action -> {

            if ((radioButtonGroup.getSelectedToggle().equals(onePlayerRadioButton) &&
                    namePlayer1Textfield.getText().length()>0)
                    || (radioButtonGroup.getSelectedToggle().equals(twoPlayersRadioButton) &&
                    namePlayer1Textfield.getText().length()>0 && namePlayer2Textfield.getText().length()>0)){
            viewManager.changeToGameScene();

            viewManager.getSoundManager().chooseSound(AUDIO.PLAY_SOUND);


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