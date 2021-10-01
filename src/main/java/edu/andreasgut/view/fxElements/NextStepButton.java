package edu.andreasgut.view.fxElements;

import javafx.application.Platform;
import javafx.scene.control.Button;

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

    public void setPut() {
        put = true;
        changeTextTo("Stein setzen");
        move = false;
        kill = false;
        this.setDisable(false);
    }

    public boolean isMove() {
        return move;

    }

    public void setMove() {
        move = true;
        changeTextTo("Stein bewegen");
        put = false;
        kill = false;
        this.setDisable(false);

    }

    public boolean isKill() {
        return kill;
    }

    public void setKill() {
        kill = true;
        changeTextTo("Gegnerischen Stein entfernen");
        put = false;
        move = false;
        this.setDisable(false);
    }

    private void changeTextTo(String text){
        Platform.runLater(() -> this.setText(text));
    }
}
