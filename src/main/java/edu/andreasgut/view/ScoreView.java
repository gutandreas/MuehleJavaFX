package edu.andreasgut.view;

import edu.andreasgut.game.Player;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScoreView extends VBox {

    private Label titleLabel, player1Label, player2Label, stonesPlayer1Label, stonesPlayer2Label, roundLabel;
    private ViewManager viewManager;

    public ScoreView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("scoreview");
        titleLabel = new Label("Spielstand");
        titleLabel.getStyleClass().add("labelTitle");
        roundLabel = new Label("Spielzug: 0");
        player1Label = new Label("");
        player1Label.getStyleClass().add("biglabel");
        stonesPlayer1Label = new Label("0");
        stonesPlayer2Label = new Label("0");
        player2Label = new Label("");
        player2Label.getStyleClass().add("biglabel");


        this.getChildren().addAll(titleLabel, roundLabel, player1Label, stonesPlayer1Label, player2Label, stonesPlayer2Label);
    }

    public void updatePlayerNames(Player player1, Player player2){
        player1Label.setText("Player 1: " + player1.getName());
        player2Label.setText("Player 2: " + player2.getName());
    }

    public void updateRound(int round){
        roundLabel.setText("Spielzug: " + round);
    }

    public Label getPlayer1Label() {
        return player1Label;
    }

    public Label getPlayer2Label() {
        return player2Label;
    }

}
