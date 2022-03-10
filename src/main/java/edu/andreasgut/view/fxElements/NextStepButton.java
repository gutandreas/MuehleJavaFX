package edu.andreasgut.view.fxElements;

import edu.andreasgut.view.ViewManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class NextStepButton extends Button {


    private boolean put = true;
    private boolean move = false;
    private boolean kill = false;

    public NextStepButton(String text) {
        super(text);
    }

    public boolean isPut() {
        return put;
    }

    public void setPut(ViewManager viewManager) {
        put = true;
        changeTextTo("Stein setzen");
        move = false;
        kill = false;
        if (viewManager.getGame().isRoboterWatching()) {
            waitForRoboter(viewManager);
        } else {
            this.setDisable(false);
        }
    }

    public boolean isMove() {
        return move;

    }

    public void setMove(ViewManager viewManager) {
        move = true;
        changeTextTo("Stein bewegen");
        put = false;
        kill = false;
        if (viewManager.getGame().isRoboterWatching()) {
            waitForRoboter(viewManager);
        } else {
            this.setDisable(false);
        }
    }

    public boolean isKill() {
        return kill;
    }

    public void setKill(ViewManager viewManager) {
        kill = true;
        changeTextTo("Gegnerischen Stein entfernen");
        put = false;
        move = false;
        if (viewManager.getGame().isRoboterWatching() || viewManager.getGame().isRoboterPlaying()) {
            waitForRoboter(viewManager);
        } else {
            this.setDisable(false);
        }
    }

    private void waitForRoboter(ViewManager viewManager) {
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), actionEvent -> viewManager.getScoreView().setRoboterProgressBarToValue(0.2)),
                new KeyFrame(Duration.seconds(4), actionEvent -> viewManager.getScoreView().setRoboterProgressBarToValue(0.4)),
                new KeyFrame(Duration.seconds(6), actionEvent -> viewManager.getScoreView().setRoboterProgressBarToValue(0.6)),
                new KeyFrame(Duration.seconds(8), actionEvent -> viewManager.getScoreView().setRoboterProgressBarToValue(0.8)),

                new KeyFrame(Duration.seconds(10), actionEvent -> {
                    viewManager.getScoreView().setRoboterProgressBarToValue(1);
                    this.setDisable(false);
                }),
                new KeyFrame(Duration.seconds(11), actionEvent -> viewManager.getScoreView().setRoboterProgressBarToValue(0)));

        timeline.play();
    }

    private void changeTextTo(String text) {
        Platform.runLater(() -> this.setText(text));
    }
}
