package edu.andreasgut.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScoreView extends VBox {

    private Label titleLabel, player1Label, player2Label, stonesPlayer1Label, stonesPlayer2Label;
    private ViewManager viewManager;

    public ScoreView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("scoreview");
        titleLabel = new Label("Spielstand");
        titleLabel.getStyleClass().add("labelTitle");
        player1Label = new Label("Spieler 1:");
        player1Label.getStyleClass().add("biglabel");
        stonesPlayer1Label = new Label("0");
        stonesPlayer2Label = new Label("0");
        player2Label = new Label("Spieler 2:");
        player2Label.getStyleClass().add("biglabel");


        this.getChildren().addAll(titleLabel, player1Label, stonesPlayer1Label, player2Label, stonesPlayer2Label);
    }

    public Label getPlayer1Label() {
        return player1Label;
    }

    public Label getPlayer2Label() {
        return player2Label;
    }


}
