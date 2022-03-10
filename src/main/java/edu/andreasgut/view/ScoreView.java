package edu.andreasgut.view;

import edu.andreasgut.game.Board;
import edu.andreasgut.game.Position;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScoreView extends VBox {

    private final Label titleLabel;
    private Label player1Label;
    private Label player2Label;
    private Label stonesPutPlayer1Label;
    private Label stonesPutPlayer2Label;
    private final Label roundLabel;
    private final Label phaseLabel;
    private Label stonesLostPlayer1Label;
    private Label stonesLostPlayer2Label;
    private Label stonesKilledPlayer1Label;
    private Label stonesKilledPlayer2Label;
    private Label gameCodeLabel;
    private Label roboterConnectedLabel;
    private final ViewManager viewManager;
    private VBox player1VBox, player2VBox, player1LabelsVBox, player2LabelsVBox;
    private HBox player1HBox, player2HBox;
    private ImageView player1StonesImageView, player2StonesImageView;
    private ProgressBar roboterProgressBar;
    private int round = 0;
    private int stonesPut1 = 0;
    private int stonesPut2 = 0;
    private int stonesLost1 = 0;
    private int stonesLost2 = 0;
    private int stonesKilled1 = 0;
    private int stonesKilled2 = 0;


    public ScoreView(ViewManager viewManager, STONECOLOR player1Color, STONECOLOR player2Color, int startRound, boolean onlineGame) {
        this.viewManager = viewManager;
        this.getStyleClass().add("scoreview");

        titleLabel = new Label("Spielstand");
        titleLabel.getStyleClass().add("labelTitle");
        if (startRound <= 18) {
            phaseLabel = new Label("Phase: Steine setzen");
        } else {
            phaseLabel = new Label("Phase: Steine verschieben");
        }
        phaseLabel.getStyleClass().add("biglabel");
        roundLabel = new Label("Spielzug: " + round);
        roundLabel.getStyleClass().add("biglabel");

        setupPlayer1andPlayer2(player1Color, player2Color);

        if (onlineGame) {
            gameCodeLabel = new Label();
            gameCodeLabel.getStyleClass().add("labelGamecode");
            roboterConnectedLabel = new Label("Roboter verbunden");
            roboterConnectedLabel.getStyleClass().add("roboterDisconnectedLabel");
            roboterProgressBar = new ProgressBar();
            roboterProgressBar.getStyleClass().add("progressBar");
            roboterProgressBar.setProgress(0);
            this.getChildren().addAll(titleLabel, phaseLabel, roundLabel, player1VBox, player2VBox, gameCodeLabel, roboterConnectedLabel, roboterProgressBar);
        } else {
            this.getChildren().addAll(titleLabel, phaseLabel, roundLabel, player1VBox, player2VBox);
        }

        int round = viewManager.getGame().getRound();
        setRound(round);

        Board board = viewManager.getGame().getBoard();

        int stonesPlayer1 = 0;
        int stonesPlayer2 = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                Position p = new Position(i, j);
                if (board.getNumberOnPosition(p) == 0) {
                    stonesPlayer1++;
                }
                if (board.getNumberOnPosition(p) == 1) {
                    stonesPlayer2++;
                }
            }
        }

        if (round <= 18) {
            setStonesPut(0, round / 2);
            setStonesPut(1, round / 2);
            setStonesLost(0, round / 2 - stonesPlayer1);
            setStonesLost(1, round / 2 - stonesPlayer2);
            setStonesKilled(0, round / 2 - stonesPlayer2);
            setStonesKilled(1, round / 2 - stonesPlayer1);
        } else {
            setStonesPut(0, 9);
            setStonesPut(1, 9);
            setStonesLost(0, 9 - stonesPlayer1);
            setStonesLost(1, 9 - stonesPlayer2);
            setStonesKilled(0, 9 - stonesPlayer2);
            setStonesKilled(1, 9 - stonesPlayer1);
        }
    }

    private void setupPlayer1andPlayer2(STONECOLOR player1Color, STONECOLOR player2Color) {
        player1VBox = new VBox();
        player1Label = new Label("Player 1: " + viewManager.getGame().getPlayer0().getName());
        player1Label.getStyleClass().add("biglabel");
        stonesPutPlayer1Label = new Label("Steine gesetzt: " + stonesPut1);
        stonesPutPlayer1Label.getStyleClass().add("labelPut");
        stonesLostPlayer1Label = new Label("Steine verloren: " + stonesLost1);
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
        stonesLostPlayer2Label = new Label("Steine verloren: " + stonesLost2);
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

    public void updatePhase(String phase) {
        Platform.runLater(() ->
                phaseLabel.setText("Phase: " + phase));
    }

    public void setStonesPut(int playerIndex, int number) {
        switch (playerIndex) {
            case 0:
                stonesLost1 = number;
                Platform.runLater(() ->
                        stonesPutPlayer1Label.setText("Steine gesetzt: " + number));
                break;
            case 1:
                stonesLost2 = number;
                Platform.runLater(() ->
                        stonesPutPlayer2Label.setText("Steine gesetzt: " + number));
                break;
        }
    }

    synchronized public void increaseStonesPut() {

        switch (viewManager.getGame().getCurrentPlayerIndex()) {
            case 0:
                Platform.runLater(() ->
                        stonesPutPlayer1Label.setText("Steine gesetzt: " + ++stonesPut1));
                break;
            case 1:
                Platform.runLater(() ->
                        stonesPutPlayer2Label.setText("Steine gesetzt: " + ++stonesPut2));
                break;
        }
    }

    public void setStonesLost(int playerIndex, int number) {
        switch (playerIndex) {
            case 0:
                stonesLost1 = number;
                Platform.runLater(() ->
                        stonesLostPlayer1Label.setText("Steine verloren: " + number));
                break;
            case 1:
                stonesLost2 = number;
                Platform.runLater(() ->
                        stonesLostPlayer2Label.setText("Steine verloren: " + number));
                break;
        }
    }

    synchronized public void increaseStonesLost() {

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

    public void setStonesKilled(int playerIndex, int number) {
        switch (playerIndex) {
            case 0:
                stonesLost1 = number;
                Platform.runLater(() ->
                        stonesKilledPlayer1Label.setText("Steine gewonnen: " + number));
                break;
            case 1:
                stonesLost2 = number;
                Platform.runLater(() ->
                        stonesKilledPlayer2Label.setText("Steine gewonnen: " + number));
                break;
        }
    }

    synchronized public void increaseStonesKilled() {

        switch (viewManager.getGame().getCurrentPlayerIndex()) {
            case 0:
                Platform.runLater(() ->
                        stonesKilledPlayer1Label.setText("Steine gewonnen: " + ++stonesKilled1));
                break;
            case 1:
                Platform.runLater(() ->
                        stonesKilledPlayer2Label.setText("Steine gewonnen: " + ++stonesKilled2));
                break;
        }
    }

    public void increaseRound() {
        Platform.runLater(() -> roundLabel.setText("Spielzug: " + ++round));
    }

    public void setRound(int round) {
        this.round = round;
        Platform.runLater(() -> roundLabel.setText("Spielzug: " + round));
    }

    public void setGameCodeLabel(String text) {
        gameCodeLabel.setText("Gamecode: " + text);
    }

    public void updatePlayer2Label(String name) {
        player2Label.setText("Player 2: " + name);
    }

    public void acitvateRoboterConnectedLabel(boolean on) {
        if (on) {
            roboterConnectedLabel.getStyleClass().remove("roboterDisconnectedLabel");
            roboterConnectedLabel.getStyleClass().add("roboterConnectedLabel");
        } else {
            roboterConnectedLabel.getStyleClass().remove("roboterConnectedLabel");
            roboterConnectedLabel.getStyleClass().add("roboterDisconnectedLabel");
        }
    }

    public void setRoboterProgressBarToValue(double value) {
        roboterProgressBar.setProgress(value);
    }

}
