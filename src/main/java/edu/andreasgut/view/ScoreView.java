package edu.andreasgut.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScoreView extends VBox {

    private Label player1Label, player2Label;
    private final int SCOREDIMENSION = 300;
    private ViewManager viewManager;

    public ScoreView(ViewManager viewManager) {
        this.viewManager = viewManager;
        //this.setPrefWidth(SCOREDIMENSION);
        this.getStyleClass().add("scoreview");
        player1Label = new Label("Spieler 1:");
        player1Label.getStyleClass().add("biglabel");
        player2Label = new Label("Spieler 2:");
        player2Label.getStyleClass().add("biglabel");
        this.getChildren().addAll(player1Label, player2Label);
    }

    public Label getPlayer1Label() {
        return player1Label;
    }

    public Label getPlayer2Label() {
        return player2Label;
    }
}
