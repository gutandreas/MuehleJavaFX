package edu.andreasgut.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartView extends VBox {

    private final int STARTDIMENSION = 600;
    ViewManager viewManager;
    VBox vBox;
    HBox hBoxRadioButtons;
    ToggleGroup radioButtonGroup;
    RadioButton onePlayerRadioButton, twoPlayersRadioButton;
    TextField namePlayer1Textfield, namePlayer2Textfield;
    Label informationLabel;
    Button startButton;


    public StartView(ViewManager viewManager) {
        this.setPrefWidth(STARTDIMENSION);
        vBox = new VBox();
        hBoxRadioButtons = new HBox();
        informationLabel = new Label();
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
        vBox.getChildren().addAll(informationLabel, hBoxRadioButtons, namePlayer1Textfield,
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
            viewManager.getMainPane().setLeft(new ScoreView(viewManager));
            viewManager.getMainPane().getLeft().setVisible(true);
            viewManager.getMainPane().setCenter(new FieldView(viewManager));}

                else {informationLabel.setText("Es fehlen Eingaben, um das Spiel zu starten");}});

}}
