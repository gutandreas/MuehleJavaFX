package edu.andreasgut.view.fxElements;

//Quelle: https://gist.github.com/TheItachiUchiha/12e40a6f3af6e1eb6f75, Zugriff: 2021_02_21

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class ToggleSwitch extends HBox {

    private final Label label = new Label();
    private final Button button = new Button();

    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
    public SimpleBooleanProperty switchOnProperty() { return switchedOn; }

    private void init() {

        label.setText("Schwarz");

        getChildren().addAll(label, button);
        button.setOnAction((e) -> {
            switchedOn.set(!switchedOn.get());
        });
        label.setOnMouseClicked((e) -> {
            switchedOn.set(!switchedOn.get());
        });
        setStyle();
        bindProperties();
    }

    private void setStyle() {
        //Default Width
        setWidth(150);
        label.setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black; -fx-text-fill:white; -fx-background-radius: 4;");
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());
        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    }

    public ToggleSwitch() {
        init();
        switchedOn.addListener((a,b,c) -> {
            if (c) {
                label.setText("Weiss");
                setStyle("-fx-background-color: black; -fx-text-fill: white");
                label.toFront();
            }
            else {
                label.setText("Schwarz");
                setStyle("-fx-background-color: black;");
                button.toFront();
            }
        });
    }

    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn.set(switchedOn);
    }

    public boolean isSwitchedOn() {
        return switchedOn.get();
    }

    public SimpleBooleanProperty switchedOnProperty() {
        return switchedOn;
    }
}
