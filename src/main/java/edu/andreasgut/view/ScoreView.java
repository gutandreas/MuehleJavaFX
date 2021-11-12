package edu.andreasgut.view;

import edu.andreasgut.game.Player;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ScoreView extends VBox {

    private Label titleLabel, player1Label, player2Label, stonesPutPlayer1Label, stonesPutPlayer2Label, roundLabel,
            phaseLabel, stonesLostPlayer1Label, stonesLostPlayer2Label, stonesKilledPlayer1Label, stonesKilledPlayer2Label,
            gameCodeLabel;
    private ViewManager viewManager;
    private VBox player1VBox, player2VBox, player1LabelsVBox, player2LabelsVBox;
    private HBox player1HBox, player2HBox;
    private ImageView player1StonesImageView, player2StonesImageView;
    private int round = 0;
    private int stonesPut1 = 0;
    private int stonesPut2 = 0;
    private int stonesLost1 = 0;
    private int stonesLost2 = 0;
    private int stonesKilled1 = 0;
    private int stonesKilled2 = 0;


    public ScoreView(ViewManager viewManager, STONECOLOR player1Color, STONECOLOR player2Color) {
        this.viewManager = viewManager;
        this.getStyleClass().add("scoreview");

        titleLabel = new Label("Spielstand");
        titleLabel.getStyleClass().add("labelTitle");
        phaseLabel = new Label("Phase: Steine setzen");
        phaseLabel.getStyleClass().add("biglabel");
        roundLabel = new Label("Spielzug: " + round);
        roundLabel.getStyleClass().add("biglabel");

        setupPlayer1andPlayer2(player1Color, player2Color);

        if (viewManager.getGame().getGameCode() != null){
            gameCodeLabel = new Label();
            gameCodeLabel.getStyleClass().add("biglabel");
            this.getChildren().addAll(titleLabel, gameCodeLabel, phaseLabel, roundLabel, player1VBox, player2VBox);
        }
        else {
            this.getChildren().addAll(titleLabel, phaseLabel, roundLabel, player1VBox, player2VBox);
        }

    }

    private void setupPlayer1andPlayer2(STONECOLOR player1Color, STONECOLOR player2Color){
        player1VBox = new VBox();
        player1Label = new Label("Player 1: " + viewManager.getGame().getPlayer0().getName());
        player1Label.getStyleClass().add("biglabel");
        stonesPutPlayer1Label = new Label("Steine gesetzt: " + stonesPut1);
        stonesPutPlayer1Label.getStyleClass().add("labelPut");
        stonesLostPlayer1Label = new Label( "Steine verloren: " + stonesLost1);
        stonesLostPlayer1Label.getStyleClass().add("labelLost");
        stonesKilledPlayer1Label = new Label("Steine gewonnen: " + stonesKilled1);
        stonesKilledPlayer1Label.getStyleClass().add("labelKilled");
        player1LabelsVBox = new VBox();
        player1LabelsVBox.getChildren().addAll(stonesPutPlayer1Label, stonesLostPlayer1Label, stonesKilledPlayer1Label);
        player1StonesImageView = new ImageView(new Image(player1Color.getPathStone()));
        player1StonesImageView.setFitWidth(58);
        player1StonesImageView.setFitHeight(58);
        player1HBox = new HBox();
        player1HBox.getChildren().addAll(player1StonesImageView, player1LabelsVBox);
        player1VBox.getChildren().addAll(player1Label, player1HBox);

        player2VBox = new VBox();
        player2Label = new Label("Player 2: " + viewManager.getGame().getPlayer1().getName());
        player2Label.getStyleClass().add("biglabel");
        stonesPutPlayer2Label = new Label("Steine gesetzt: " + stonesPut2);
        stonesPutPlayer2Label.getStyleClass().add("labelPut");
        stonesLostPlayer2Label = new Label( "Steine verloren: " + stonesLost2);
        stonesLostPlayer2Label.getStyleClass().add("labelLost");
        stonesKilledPlayer2Label = new Label("Steine gewonnen: " + stonesKilled2);
        stonesKilledPlayer2Label.getStyleClass().add("labelKilled");
        player2LabelsVBox = new VBox();
        player2LabelsVBox.getChildren().addAll(stonesPutPlayer2Label, stonesLostPlayer2Label, stonesKilledPlayer2Label);
        player2StonesImageView = new ImageView(new Image(player2Color.getPathStone()));
        player2StonesImageView.setFitWidth(58);
        player2StonesImageView.setFitHeight(58);
        player2HBox = new HBox();
        player2HBox.getChildren().addAll(player2StonesImageView, player2LabelsVBox);
        player2VBox.getChildren().addAll(player2Label, player2HBox);
    }

    public void updatePlayerNames(Player player1, Player player2){
        player1Label.setText("Player 1: " + player1.getName());
        player2Label.setText("Player 2: " + player2.getName());
    }

    public void updatePhase(String phase){
        Platform.runLater(() ->
        phaseLabel.setText("Phase: " + phase));
    }

    synchronized public void increaseStonesPut(){

        switch (viewManager.getGame().getCurrentPlayerIndex()){
                    case 0:
                        Platform.runLater(() ->
                        stonesPutPlayer1Label.setText("Steine gesetzt: " + ++stonesPut1));
                        break;
                    case 1:
                        Platform.runLater(() ->
                        stonesPutPlayer2Label.setText("Steine gesetzt: " + ++stonesPut2));
                        break;}


    }

    synchronized public void increaseStonesLost(){

            switch ((viewManager.getGame().getOtherPlayerIndex())) {
                case 0:
                    Platform.runLater(() ->
                    stonesLostPlayer1Label.setText("Steine verloren: " + ++stonesLost1));
                    break;
                case 1:
                    Platform.runLater(() ->
                    stonesLostPlayer2Label.setText("Steine verloren: " + ++stonesLost2));
                    break;
            }
    }

    synchronized public void increaseStonesKilled(){

        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                Platform.runLater(() ->
                stonesKilledPlayer1Label.setText("Steine gewonnen: " + ++stonesKilled1));
                break;
            case 1:
                Platform.runLater(() ->
                stonesKilledPlayer2Label.setText("Steine gewonnen: " + ++stonesKilled2));
                break;}
    }

    public void increaseRound(){
        Platform.runLater(()-> roundLabel.setText("Spielzug: " + ++round));

    }


    public void setPlayerLabelEffects(){
        switch (viewManager.getGame().getCurrentPlayerIndex()){
            case 0:
                viewManager.getScoreView().getPlayer1Label().getStyleClass().add("labelCurrentPlayer");
                viewManager.getScoreView().getStonesKilledPlayer1Label().getStyleClass().add("labelCurrentPlayer");
                viewManager.getScoreView().getStonesLostPlayer1Label().getStyleClass().add("labelCurrentPlayer");
                viewManager.getScoreView().getStonesPutPlayer1Label().getStyleClass().add("labelCurrentPlayer");

                viewManager.getScoreView().getPlayer2Label().getStyleClass().add("labelOtherPlayer");
                viewManager.getScoreView().getStonesKilledPlayer2Label().getStyleClass().add("labelOtherPlayer");
                viewManager.getScoreView().getStonesLostPlayer2Label().getStyleClass().add("labelOtherPlayer");
                viewManager.getScoreView().getStonesPutPlayer2Label().getStyleClass().add("labelOtherPlayer");
                break;
            case 1:
                viewManager.getScoreView().getPlayer2Label().getStyleClass().add("labelCurrentPlayer");
                viewManager.getScoreView().getStonesKilledPlayer2Label().getStyleClass().add("labelCurrentPlayer");
                viewManager.getScoreView().getStonesLostPlayer2Label().getStyleClass().add("labelCurrentPlayer");
                viewManager.getScoreView().getStonesPutPlayer2Label().getStyleClass().add("labelCurrentPlayer");

                viewManager.getScoreView().getPlayer1Label().getStyleClass().add("labelOtherPlayer");
                viewManager.getScoreView().getStonesKilledPlayer1Label().getStyleClass().add("labelOtherPlayer");
                viewManager.getScoreView().getStonesLostPlayer1Label().getStyleClass().add("labelOtherPlayer");
                viewManager.getScoreView().getStonesPutPlayer1Label().getStyleClass().add("labelOtherPlayer");
                break;
        }
    }

    public void setGameCodeLabel(String text){
        gameCodeLabel.setText("Gamecode: " + text);
    }

    public Label getPlayer1Label() {
        return player1Label;
    }

    public Label getPlayer2Label() {
        return player2Label;
    }

    public Label getStonesPutPlayer1Label() {
        return stonesPutPlayer1Label;
    }

    public Label getStonesPutPlayer2Label() {
        return stonesPutPlayer2Label;
    }

    public Label getStonesLostPlayer1Label() {
        return stonesLostPlayer1Label;
    }

    public Label getStonesLostPlayer2Label() {
        return stonesLostPlayer2Label;
    }

    public Label getStonesKilledPlayer1Label() {
        return stonesKilledPlayer1Label;
    }

    public Label getStonesKilledPlayer2Label() {
        return stonesKilledPlayer2Label;
    }
}
