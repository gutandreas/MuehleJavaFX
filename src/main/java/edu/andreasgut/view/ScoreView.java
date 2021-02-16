package edu.andreasgut.view;

import edu.andreasgut.game.Player;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScoreView extends VBox {

    private Label titleLabel, player1Label, player2Label, stonesPutPlayer1Label, stonesPutPlayer2Label, roundLabel,
            phaseLabel, stonesLostPlayer1Label, stonesLostPlayer2Label, stonesKilledPlayer1Label, stonesKilledPlayer2Label;
    private ViewManager viewManager;
    private VBox player1VBox, player2VBox;
    private int round = 0;
    private int stonesPut1 = 0;
    private int stonesPut2 = 0;
    private int stonesLost1 = 0;
    private int stonesLost2 = 0;
    private int stonesKilled1 = 0;
    private int stonesKilled2 = 0;


    public ScoreView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("scoreview");

        player1VBox = new VBox();
        player2VBox = new VBox();

        titleLabel = new Label("Spielstand");
        titleLabel.getStyleClass().add("labelTitle");
        phaseLabel = new Label("Phase: Steine setzen");
        phaseLabel.getStyleClass().add("biglabel");
        roundLabel = new Label("Spielzug: " + round);
        roundLabel.getStyleClass().add("biglabel");

        player1Label = new Label("");
        player1Label.getStyleClass().add("biglabel");
        stonesPutPlayer1Label = new Label("Steine gesetzt: " + stonesPut1);
        stonesPutPlayer1Label.getStyleClass().add("labelPut");
        stonesLostPlayer1Label = new Label( "Steine verloren: " + stonesLost1);
        stonesLostPlayer1Label.getStyleClass().add("labelLost");
        stonesKilledPlayer1Label = new Label("Steine gewonnen: " + stonesKilled1);
        stonesKilledPlayer1Label.getStyleClass().add("labelKilled");
        player1VBox.getChildren().addAll(player1Label,stonesPutPlayer1Label,stonesLostPlayer1Label, stonesKilledPlayer1Label);


        player2Label = new Label("");
        player2Label.getStyleClass().add("biglabel");
        stonesPutPlayer2Label = new Label("Steine gesetzt: " + stonesPut2);
        stonesPutPlayer2Label.getStyleClass().add("labelPut");
        stonesLostPlayer2Label = new Label( "Steine verloren: " + stonesLost2);
        stonesLostPlayer2Label.getStyleClass().add("labelLost");
        stonesKilledPlayer2Label = new Label("Steine gewonnen: " + stonesKilled2);
        stonesKilledPlayer2Label.getStyleClass().add("labelKilled");
        player2VBox.getChildren().addAll(player2Label,stonesPutPlayer2Label,stonesLostPlayer2Label, stonesKilledPlayer2Label);




        this.getChildren().addAll(titleLabel, phaseLabel, roundLabel, player1VBox, player2VBox);
    }

    public void updatePlayerNames(Player player1, Player player2){
        player1Label.setText("Player 1: " + player1.getName());
        player2Label.setText("Player 2: " + player2.getName());
    }

    public void updatePhase(String phase){
        phaseLabel.setText("Phase: " + phase);
    }

    public void increaseStonesPut(){
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                stonesPutPlayer1Label.setText("Steine gesetzt: " + ++stonesPut1);
                break;
            case 1:
                stonesPutPlayer2Label.setText("Steine gesetzt: " + ++stonesPut2);
                break;
        }
    }

    public void increaseStonesLost(){
        switch ((viewManager.getGame().getCurrentPlayerIndex()+1)%2){
            case 0:
                stonesLostPlayer1Label.setText("Steine verloren: " + ++stonesLost1);
                break;
            case 1:
                stonesLostPlayer2Label.setText("Steine verloren: " + ++stonesLost2);
                break;
        }
    }

    public void increaseStonesKilled(){
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                stonesKilledPlayer1Label.setText("Steine gewonnen: " + ++stonesKilled1);
                break;
            case 1:
                stonesKilledPlayer2Label.setText("Steine gewonnen: " + ++stonesKilled2);
                break;
        }
    }

    public void increaseRound(){
        roundLabel.setText("Spielzug: " + ++round);
    }

    public Label getPlayer1Label() {
        return player1Label;
    }

    public Label getPlayer2Label() {
        return player2Label;
    }


}
