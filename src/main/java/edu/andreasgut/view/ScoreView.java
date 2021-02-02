package edu.andreasgut.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ScoreView extends VBox {

    private Label player0, player1;
    private final int SCOREDIMENSION = 300;
    private ViewManager viewManager;

    public ScoreView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.setPrefWidth(SCOREDIMENSION);
        this.setAlignment(Pos.CENTER);
        player0 = new Label("Spieler 1:");
        player1 = new Label("Spieler 2:");
        this.getChildren().addAll(player0,player1);
    }
}
