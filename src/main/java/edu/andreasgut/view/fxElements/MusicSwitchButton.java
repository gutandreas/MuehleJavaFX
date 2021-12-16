package edu.andreasgut.view.fxElements;

// https://stackoverflow.com/a/57290206 18. März 2021

import edu.andreasgut.view.ViewManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MusicSwitchButton extends StackPane {
    private final Rectangle back = new Rectangle(40, 5, Color.RED);
    private final Button button = new Button();
    private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #aa2020;";
    private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    private boolean state = true;

    private void init() {
        getChildren().addAll(back, button);
        setMinSize(50, 30);
        back.maxWidth(50);
        back.minWidth(50);
        back.maxHeight(30);
        back.minHeight(30);
        back.setArcHeight(back.getHeight());
        back.setArcWidth(back.getHeight());
        back.setFill(Color.valueOf("#80C49E"));
        Double r = 2.0;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_RIGHT);
        button.setMaxSize(15, 15);
        button.setMinSize(15, 15);
        button.setStyle(buttonStyleOn);
    }

    public MusicSwitchButton(ViewManager viewManager) {
        init();
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (state) {
                    button.setStyle(buttonStyleOff);
                    back.setFill(Color.valueOf("#F49090"));
                    setAlignment(button, Pos.CENTER_LEFT);
                    state = false;
                    viewManager.getAudioPlayer().stopMusic();

                } else {
                    button.setStyle(buttonStyleOn);
                    back.setFill(Color.valueOf("#80C49E"));
                    setAlignment(button, Pos.CENTER_RIGHT);
                    state = true;
                    viewManager.getAudioPlayer().continueMusic();
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }

    public boolean getState() {
        return state;
    }
}