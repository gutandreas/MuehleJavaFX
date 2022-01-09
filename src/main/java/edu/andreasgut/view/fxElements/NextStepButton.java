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
        if (viewManager.getGame().isRoboterWatching()){
            final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), actionEvent -> {
                this.setDisable(false);}));
            timeline.play();
        }
        else {
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
        if (viewManager.getGame().isRoboterWatching()){
            final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), actionEvent ->
                    this.setDisable(false)));
            timeline.play();
        }
        else {
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
        if (viewManager.getGame().isRoboterWatching() || viewManager.getGame().isRoboterPlaying()){
            final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), actionEvent ->
                    this.setDisable(false)));
            timeline.play();
        }
        else {
            this.setDisable(false);
        }
    }

    private void changeTextTo(String text){
        Platform.runLater(() -> this.setText(text));
    }
}
